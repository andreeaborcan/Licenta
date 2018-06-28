package BuildingComponents;


import org.lwjgl.util.vector.Vector2f;

/**
 * Created by ada on 21.04.2018.
 */

public class Door {
    private Vector2f point1, point2;
    private int floor;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Vector2f getPoint1() {
        return point1;
    }

    public void setPoint1(Vector2f point1) {
        this.point1 = point1;
    }

    public Vector2f getPoint2() {
        return point2;
    }

    public void setPoint2(Vector2f point2) {
        this.point2 = point2;
    }
}
