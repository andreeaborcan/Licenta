package com.mygdx.game;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

import BuildingComponents.*;
import ModelComponent.ModelCameraComponent;
import ModelComponent.ModelDoorComponent;
import ModelComponent.ModelFloorComponent;
import ModelComponent.ModelPathComponent;
import ModelComponent.ModelWallComponent;
import ModelComponent.ModelWallDownComponent;
import ModelComponent.ModelWallUpComponent;
import ModelComponent.ModelWindowComponent;

/**
 * Created by ada on 21.04.2018.
 */

public class EntityFactory {
    private ArrayList<Wall> walls;
    private  ArrayList<Wall> wallsDown;
    private  ArrayList<Wall> wallsUp;
    private ArrayList<Door> doors;
    private ArrayList<Window> windows;
    public ArrayList<Vector2f> path;
    public ArrayList<Entity> WallsEntity;
    public ArrayList<Entity> WindowsEntity;
    public ArrayList<Entity> DoorsEntity;
    public ArrayList<Entity> WallsDownEntity;
    public ArrayList<Entity> WallsUpEntity;
    public ArrayList<Entity> pathEntity, floorEntity;
    public ArrayList<Floor> floors;
    public Entity visitor;
    private  Model playerModel;
    private Texture playerTexture;
    private  ModelBuilder modelBuilder;
    private  Model boxModel;
    private Vector2f centru;
    public int currentFloor, currentFloorIndex;
    public String floor;
    private ArrayList<String> roomNames;
    private ArrayList<Integer> floorNumbers;

    EntityFactory(String filename)
    {
        Gdx.app.log("DEBUGA","in entity, incercand sa parsez jsonu88l.");
        Parser pj=new Parser(filename);
        floors=pj.getFloors();
        floorNumbers=pj.getFloorNumbers();
        roomNames=pj.getRoomNames();
        currentFloor = floorNumbers.get(0);
        currentFloorIndex = floorNumbers.indexOf(currentFloor);
        initialize();
    }

    public ArrayList<Room> getRooms() {
        return null;
    }

    public void setFloor(int i){
        currentFloor = i;
        currentFloorIndex = floorNumbers.indexOf(i);
    }

    public void initialize()
    {
        centru=floors.get(0).getFloorCenter();

        walls = new ArrayList<Wall>();
        wallsUp = new ArrayList<Wall>();
        wallsDown = new ArrayList<Wall>();
        windows = new ArrayList<Window>();
        doors = new ArrayList<Door>();


        for(int i = 0; i < floors.size(); i++) {
            for(Wall w : floors.get(i).getWalls()) w.setFloor(i);
            for(Wall w : floors.get(i).getWallsUp()) w.setFloor(i);
            for(Wall w : floors.get(i).getWallsDown()) w.setFloor(i);
            for(Window w : floors.get(i).getWindows()) w.setFloor(i);
            for(Door d : floors.get(i).getDoors()) d.setFloor(i);

            walls.addAll(floors.get(i).getWalls());
            wallsDown.addAll(floors.get(i).getWallsDown());
            wallsUp.addAll(floors.get(i).getWallsUp());
            doors.addAll(floors.get(i).getDoors());
            windows.addAll(floors.get(i).getWindows());
        }

        this.WallsDownEntity=new ArrayList<Entity>();
        this.WallsEntity=new ArrayList<Entity>();
        this.WallsUpEntity=new ArrayList<Entity>();
        this.WindowsEntity=new ArrayList<Entity>();
        this.DoorsEntity=new ArrayList<Entity>();
        floorEntity = new ArrayList<Entity>();

        visitor=new Entity();
    }

    public ArrayList<Entity> createFloorEnts() {
        for(int i = 0; i < floors.size(); i++) {
            Entity e = new Entity();
            e.add(new ModelFloorComponent(i));
            floorEntity.add(e);
        }
        return floorEntity;
    }

    public ArrayList<Entity> createPathEntity() {

        for(Vector2f v : path) {
            Entity e = new Entity();
            e.add(new ModelPathComponent(v, 0)); //TODO change floor
            pathEntity.add(e);
        }
        return pathEntity;
    }

    public Entity createVisitorEntity() {
        System.out.println("Adaug o camera-visitor comp");
        visitor.add(new ModelCameraComponent());
        System.out.println("in visitor am" + visitor.getComponents().size() + " elemente");
        return visitor;
    }

    public ArrayList<Entity> createWallsEntity() {
        Entity entity;
        System.out.println("Walls size is" + walls.size());
        for( int i=0;i<walls.size();i++)
        {
            entity = new Entity();
            entity.add(new ModelWallComponent(walls.get(i).getPoint1(),walls.get(i).getPoint2(), walls.get(i).getFloor()));
            WallsEntity.add(entity);

        }
       // System.out.println("Entity size is" + entity.getComponents().size());
        return WallsEntity;
    }

    public ArrayList<Entity> createWallsUpEntity( ) {
        Entity entity;
        ModelWallUpComponent modelComponent;
        for( int i=0;i<wallsUp.size();i++)
        {   entity = new Entity();
            modelComponent = new ModelWallUpComponent(wallsUp.get(i).getPoint1(),
                    wallsUp.get(i).getPoint2(), wallsUp.get(i).getFloor());
            entity.add(modelComponent);
            WallsUpEntity.add(entity);
        }
        return WallsUpEntity;
    }

    public ArrayList<Entity> createWallsDownEntity( ) {
        Entity entity;
        ModelWallDownComponent modelComponent;
        for( int i=0;i<wallsDown.size();i++)
        {
            entity = new Entity();
            modelComponent = new ModelWallDownComponent(wallsDown.get(i).getPoint1(),
                    wallsDown.get(i).getPoint2(), wallsDown.get(i).getFloor());
            entity.add(modelComponent);
            WallsDownEntity.add(entity);
        }
        return WallsDownEntity;
    }
    public ArrayList<Entity> createDoorsEntity( ) {
        Entity entity;
        ModelDoorComponent modelComponent;
        for( int i=0;i<doors.size();i++)
        {
            entity = new Entity();
            modelComponent = new ModelDoorComponent(doors.get(i).getPoint1(),
                    doors.get(i).getPoint2(), doors.get(i).getFloor());
            entity.add(modelComponent);
            DoorsEntity.add(entity);
        }
        return DoorsEntity;
    }
    public ArrayList<Entity> createWindowsEntity( ) {
        Entity entity;
        ModelWindowComponent modelComponent;
        for( int i=0;i<windows.size();i++)
        {
            entity = new Entity();
            modelComponent = new ModelWindowComponent(windows.get(i).getPoint1(),
                    windows.get(i).getPoint2(), windows.get(i).getFloor());
            entity.add(modelComponent);
            WindowsEntity.add(entity);
        }
        return WindowsEntity;
    }
    public ArrayList<Wall> getWalls() {
        return walls;
    }
    public ArrayList<String> getRoomNames() {
        return roomNames;
    }

    public ArrayList<Integer> getFloorNumbers() {
        return floorNumbers;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }

    public ArrayList<Wall> getWallsDown() {
        return wallsDown;
    }

    public void setWallsDown(ArrayList<Wall> wallsDown) {
        this.wallsDown = wallsDown;
    }

    public ArrayList<Wall> getWallsUp() {
        return wallsUp;
    }

    public void setWallsUp(ArrayList<Wall> wallsUp) {
        this.wallsUp = wallsUp;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public void setDoors(ArrayList<Door> doors) {
        this.doors = doors;
    }

    public ArrayList<Window> getWindows() {
        return windows;
    }

    public void setWindows(ArrayList<Window> windows) {
        this.windows = windows;
    }
    public Vector2f getBuildingCenter() {
        return centru;
    }

}
