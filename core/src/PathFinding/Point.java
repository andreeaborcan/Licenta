package PathFinding;

import org.lwjgl.util.vector.Vector2f;

/**
 * Created by ada on 27.06.2018.
 */

public class Point {
    public int floor;
    public Vector2f pos;

    Point() {}
    public Point(int fl, Vector2f v) {
        floor = fl;
        pos = new Vector2f(v);
    }
}
