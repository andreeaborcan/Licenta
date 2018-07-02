
package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import org.lwjgl.util.vector.Vector2f;

import java.io.IOException;
import java.util.ArrayList;

import BuildingComponents.Door;
import BuildingComponents.Floor;
import BuildingComponents.Room;
import BuildingComponents.Wall;
import BuildingComponents.Window;
import UserInterface.MiniMapImageGenerator;

/**
 * @author ada
 */
public class Parser {

    public ArrayList<Floor> floors;
    private  ArrayList<Wall> walls;
    private  ArrayList<Wall> wallsDown;
    private  ArrayList<Wall> wallsUp;
    private ArrayList<Door> Doors;
    private ArrayList<Window> windows;
    public ArrayList<String> roomNames=new ArrayList<String>();
    public ArrayList<Integer> floorNumbers=new ArrayList<Integer>();
    private MiniMapImageGenerator miniMapImageGenerator;
    private Vector2f buildingCenter;
    private ArrayList<Room> rooms;

    private final int SCALE = 4;

    public Parser(String filename)
    {

        Gdx.app.setLogLevel(Application.LOG_ERROR);
        System.out.println("first stop in parsejson");
        try {
            Gdx.app.log("DEBUGA","am parsat jsonul!!!");
           floors=getFloors(filename);
        } catch (Exception e) {
            Gdx.app.log("DEBUGA","ceva e stricat!");
            e.printStackTrace();
        }

    }
    public MiniMapImageGenerator getMiniMapImageGenerator()
    {
        return miniMapImageGenerator;
    }


    private ArrayList<Floor> getFloors(String fileName) throws Exception, IOException {
        JsonReader json = new JsonReader();
        JsonValue base = json.parse(Gdx.files.internal(fileName));
        floors= new ArrayList(100);
        float x,y;
        for (JsonValue component : base.get("floors")) {
            Floor floor=new Floor();

            System.out.println(component.toString());
            floor.setFloorNb(component.getInt("number"));
            floorNumbers.add(floor.getFloorNb());
            floor.setRooms(getRooms(component));
            walls=getWalls(component);
            floor.setFloorCenter(calculateFloorCenter(walls));
            windows=getWindows(component);
            System.out.println("geamuri"+windows.size());
            Doors=getDoors(component);
            wallsDown=new ArrayList<Wall>();
            wallsUp=new ArrayList<Wall>();
            remakeWalls(walls,wallsDown,wallsUp,Doors,windows);
            floor.setWalls(walls);
            floor.setDoors(Doors);
            floor.setWallsDown(wallsDown);
            floor.setWallsUp(wallsUp);
            floor.setWindows(windows);
            floors.add(floor);
        }

        return floors;


    }
    private ArrayList<Wall> getWalls(JsonValue component) throws Exception, IOException {
        walls = new ArrayList(100);
        float x,y;
        for (JsonValue component2 : component.get("walls")) {
            Wall wall = new Wall();
            x=SCALE * (component2.getFloat("x1"));
            y=-SCALE * (component2.getFloat("y1"));
            Vector2f point1 = new Vector2f(x,y);

            x=SCALE * (component2.getFloat("x2"));
            y=-SCALE * (component2.getFloat("y2"));
            Vector2f point2 = new Vector2f(x,y);
            wall.setPoint1(point1);
            wall.setPoint2(point2);
            walls.add(wall);
        }

        return walls;

    }


    private ArrayList<Room> getRooms(JsonValue component) throws Exception, IOException {
        rooms= new ArrayList(100);
        float x,y;
        for (JsonValue component2 : component.get("rooms")) {
            Room room=new Room();
            System.out.println(component2.toString());
            room.setRoomName(component2.getString("name"));
            roomNames.add(room.getRoomName());
            for ( JsonValue componentRoom : component2.get("vertices")) {
                x=SCALE * (componentRoom.getFloat("x"));
                y = -SCALE * (componentRoom.getFloat("y"));
                Vector2f point = new Vector2f(x, y);
                System.out.println("Am ajuns in parsarea camerei si am pus urmatoarele puncte "+ point);
                room.getVertices().add(point);

            }

            rooms.add(room);
        }
        calculateRoomCenter(rooms);

        return rooms;

    }

    private void calculateRoomCenter(ArrayList<Room>w)
    {
        float sumX=0,sumY=0;
        ArrayList<Vector2f> v;
        int size;
        for (int i=0;i<w.size();i++)
        {
            sumX=sumY=0;
            v=w.get(i).getVertices();
            size=v.size();
            for (int j=0;j<size;j++) {
                sumX+=v.get(j).x;
                sumY+=v.get(j).y;
            }
            w.get(i).setRoomCenter(new Vector2f(sumX/size,sumY/size));

        }

    }

    private Vector2f calculateFloorCenter(ArrayList<Wall>w)
    {
        float sumX=0,sumY=0;
        int size=w.size();
        System.out.println("Am atatia pereti: "+size);
        for (int i=0;i<size;i++)
        {
            sumX=sumX+w.get(i).getPoint1().x+w.get(i).getPoint2().x;
            System.out.println("primul punct: "+w.get(i).getPoint1());
            System.out.println("al doilea punct: "+w.get(i).getPoint2());
            System.out.println("sumx: "+sumX);
            sumY+=w.get(i).getPoint1().y+w.get(i).getPoint2().y;

            System.out.println("sumy: "+sumY);

        }
        size*=2;
        System.out.println("Sumx si sumy sunt: "+sumX+" "+ sumY);
        sumX=sumX/size;
        sumY/=size;
        System.out.println("Sumx si sumy sunt: "+sumX+ sumY);
        return new Vector2f(sumX,sumY);
    }
    private ArrayList<Door> getDoors(JsonValue component) throws Exception, IOException {
        Doors = new ArrayList(100);

        for (JsonValue component2 : component.get("doors")) {
            Door Door = new Door();
            Vector2f point1 = new Vector2f(SCALE * (component2.getFloat("x1")), -SCALE * (component2.getFloat("y1")));
            Vector2f point2 = new Vector2f(SCALE * (component2.getFloat("x2")), -SCALE * (component2.getFloat("y2")));
            Door.setPoint1(point1);
            Door.setPoint2(point2);
            Doors.add(Door);
        }

        return Doors;

    }
    private ArrayList<Window> getWindows(JsonValue component) throws Exception, IOException {

        windows = new ArrayList(100);

        for (JsonValue component2 : component.get("windows")) {
            Window window = new Window();
            Vector2f point1 = new Vector2f(SCALE * (component2.getFloat("x1")), -SCALE * (component2.getFloat("y1")));
            Vector2f point2 = new Vector2f(SCALE * (component2.getFloat("x2")), -SCALE * (component2.getFloat("y2")));
            window.setPoint1(point1);
            window.setPoint2(point2);
            windows.add(window);
        }

        return windows;

    }
    private boolean pointOnLine(Vector2f point,Wall line)
    {
        float xa,xb,ya,yb;
        xa=line.getPoint1().getX();
        ya=line.getPoint1().getY();
        xb=line.getPoint2().getX();
        yb=line.getPoint2().getY();
        if ((point.getX()-xa)*(yb-ya)-(point.getY()-ya)*(xb-xa)<1e-1)
            return true;
        return false;

    }
    private float distance(Vector2f point1,Vector2f point2)
    {
        return (float) Math.sqrt((point1.getX()-point2.getX())*(point1.getX()-point2.getX())+(point1.getY()-point2.getY())*(point1.getY()-point2.getY()));
    }
    private ArrayList<Wall> remakeWalls(ArrayList<Wall> walls,ArrayList<Wall> wallsDown,ArrayList<Wall> wallsUp,ArrayList<Door> doors,ArrayList<Window> windows)
    {

        Gdx.app.log("DEBUGA","in parsejson remake walls");
        float aa,bb;
        for (int i = 0; i < doors.size(); i++) {
            for ( int j = 0 ; j < walls.size(); j++ )
            {
                aa=0;
                bb=0;
                if (pointOnLine(doors.get(i).getPoint1(),walls.get(j)) && pointOnLine(doors.get(i).getPoint2(),walls.get(j)))
                {
                    Wall a=new Wall();
                    if (distance(doors.get(i).getPoint1(),walls.get(j).getPoint1()) < distance(doors.get(i).getPoint2(),walls.get(j).getPoint1()))
                    {
                        aa=distance(doors.get(i).getPoint1(),walls.get(j).getPoint1());
                        a.setPoint1(walls.get(j).getPoint1());
                        a.setPoint2(doors.get(i).getPoint1());
                    }
                    else
                    {
                        aa=distance(doors.get(i).getPoint2(),walls.get(j).getPoint1());
                        a.setPoint1(walls.get(j).getPoint1());
                        a.setPoint2(doors.get(i).getPoint2());
                    }

                    Wall b=new Wall();
                    if (distance(doors.get(i).getPoint1(),walls.get(j).getPoint2()) < distance(doors.get(i).getPoint2(),walls.get(j).getPoint2()))
                    {
                        bb=distance(doors.get(i).getPoint1(),walls.get(j).getPoint2());
                        b.setPoint1(walls.get(j).getPoint2());
                        b.setPoint2(doors.get(i).getPoint1());
                    }
                    else
                    {
                        bb=distance(doors.get(i).getPoint2(),walls.get(j).getPoint2());
                        b.setPoint1(walls.get(j).getPoint2());
                        b.setPoint2(doors.get(i).getPoint2());
                    }
                    if(Math.abs(aa+bb+distance(doors.get(i).getPoint1(),doors.get(i).getPoint2())-distance(walls.get(j).getPoint1(),walls.get(j).getPoint2()))<1e-1)
                    {
                        if(aa != 0)
                            walls.add(a);
                        if(bb!=0)
                            walls.add(b);

                        walls.remove(j);
                    }
                }
            }
        }
        for (int i = 0; i < windows.size(); i++) {
            for ( int j = 0 ; j < walls.size(); j++ )
            {
                aa=0;
                bb=0;
                if (pointOnLine(windows.get(i).getPoint1(),walls.get(j)) && pointOnLine(windows.get(i).getPoint2(),walls.get(j)))
                {
                    Wall a=new Wall();
                    Wall down=new Wall();
                    Wall up=new Wall();

                    down.setPoint1(windows.get(i).getPoint1());
                    down.setPoint2(windows.get(i).getPoint2());
                    up.setPoint1(windows.get(i).getPoint1());
                    up.setPoint2(windows.get(i).getPoint2());

                    if (distance(windows.get(i).getPoint1(),walls.get(j).getPoint1()) < distance(windows.get(i).getPoint2(),walls.get(j).getPoint1()))
                    {
                        aa=distance(windows.get(i).getPoint1(),walls.get(j).getPoint1());
                        a.setPoint1(walls.get(j).getPoint1());
                        a.setPoint2(windows.get(i).getPoint1());
                    }
                    else
                    {
                        aa=distance(windows.get(i).getPoint2(),walls.get(j).getPoint1());
                        a.setPoint1(walls.get(j).getPoint1());
                        a.setPoint2(windows.get(i).getPoint2());
                    }

                    Wall b=new Wall();
                    if (distance(windows.get(i).getPoint1(),walls.get(j).getPoint2()) < distance(windows.get(i).getPoint2(),walls.get(j).getPoint2()))
                    {
                        bb=distance(windows.get(i).getPoint1(),walls.get(j).getPoint2());
                        b.setPoint1(walls.get(j).getPoint2());
                        b.setPoint2(windows.get(i).getPoint1());
                    }
                    else
                    {
                        bb=distance(windows.get(i).getPoint2(),walls.get(j).getPoint2());
                        b.setPoint1(walls.get(j).getPoint2());
                        b.setPoint2(windows.get(i).getPoint2());
                    }
                    if(Math.abs(aa+bb+distance(windows.get(i).getPoint1(),windows.get(i).getPoint2())-distance(walls.get(j).getPoint1(),walls.get(j).getPoint2()))<1e-1)
                    {
                        if(aa != 0)
                            walls.add(a);
                        if(bb!=0)
                            walls.add(b);
                        wallsDown.add(down);
                        wallsUp.add(up);
                        walls.remove(j);
                    }
                }
            }
        }

        return walls;
    }
    public ArrayList<Floor> getFloors() {
        return floors;
    }
    public ArrayList<String> getRoomNames() {
        return roomNames;
    }

    public ArrayList<Integer> getFloorNumbers() {
        return floorNumbers;
    }

//
//    public ArrayList<Room> getRooms() {
//        return rooms;
//    }
//    public ArrayList<Wall> getWalls()
//    {
//        return this.walls;
//    }
//    public ArrayList<Wall> getWallsDown()
//    {
//        return this.wallsDown;
//    }
//    public ArrayList<Wall> getWallsUp()
//    {
//        return this.wallsUp;
//    }
//    public ArrayList<Door> getDoors()
//    {
//        return this.Doors;
//    }
//    public ArrayList<Window> getWindows()
//    {
//        return this.windows;
//    }
//    public Vector2f getBuildingCenter(){return this.buildingCenter;}

}
