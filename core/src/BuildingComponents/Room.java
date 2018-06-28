package BuildingComponents;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

/**
 * Created by ada on 22.05.2018.
 */

public class Room {
    private String roomName;
    private ArrayList<Vector2f> vertices;
    private Vector2f roomCenter;
    private int floor;

    public Vector2f getRoomCenter() {
        return roomCenter;
    }

    public void setRoomCenter(Vector2f roomCenter) {
        this.roomCenter = roomCenter;
    }

    public Room() {
        this.vertices = new ArrayList<Vector2f>();
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setVertices(ArrayList<Vector2f> edges) {
        this.vertices = edges;
    }

    public String getRoomName() {

        return roomName;
    }

    public ArrayList<Vector2f> getVertices() {
        return vertices;
    }
}
