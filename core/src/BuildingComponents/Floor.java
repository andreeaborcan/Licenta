package BuildingComponents;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

import UserInterface.MiniMapImageGenerator;

/**
 * Created by ada on 05.06.2018.
 */

public class Floor {
    private int floorNb;
    private  ArrayList<Wall> walls;
    private  ArrayList<Wall> wallsDown;
    private  ArrayList<Wall> wallsUp;
    private ArrayList<Door> Doors;
    private ArrayList<Window> windows;
    private Vector2f floorCenter;
    private ArrayList<Room> rooms;

    public int getFloorNb() {
        return floorNb;
    }

    public void setFloorNb(int floorNb) {
        this.floorNb = floorNb;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
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
        return Doors;
    }

    public void setDoors(ArrayList<Door> doors) {
        Doors = doors;
    }

    public ArrayList<Window> getWindows() {
        return windows;
    }

    public void setWindows(ArrayList<Window> windows) {
        this.windows = windows;
    }

    public Vector2f getFloorCenter() {
        return floorCenter;
    }

    public void setFloorCenter(Vector2f floorCenter) {
        this.floorCenter = floorCenter;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
