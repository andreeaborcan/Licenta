package PathFinding;


import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import BuildingComponents.Door;
import BuildingComponents.Floor;
import BuildingComponents.Wall;

/**
 * Created by ada on 26.06.2018.
 */

public class PathFinder {

    /**
     * mat[i][j][k] -> i-th floor
     *              -> j, k coordinates
     *              -> value is (-1, 0, 1, 2, 3)
     *                  -> -1 blocked
     *                  -> 0 free
     *                  -> 1 stair up
     *                  -> 2 stair down
     *                  -> 3 stair up and down
     */
    int mat[][][] = new int[11][511][511];
    int cFl, cX, cY;

    int dx[] = {1, 0, -1, 0};
    int dy[] = {0, -1, 0, 1};

    PathFinder() {

    }

    public void setPosition(int floor, int x, int y) {
        cFl = floor;
        cX = x;
        cY = y;
    }

    private void addLine(Vector2f a, Vector2f b, int val, int floor) {
        int ax = (int)(a.x * 10);
        int ay = (int)(a.y * 10);
        int bx = (int)(b.x * 10);
        int by = (int)(b.y * 10);

        if (Math.abs(ax - bx) <= Math.abs(ay - by)) {

        } else {
            double dy = 1. * (ay - by) / Math.abs(ax - bx);
            double dj = Math.min(ay, by);
            for(int i = Math.min(ax, bx); i <= Math.max(ax, bx); i++) {
                dj += dy;
                mat[floor][i][(int) dj] = val;
            }
        }
    }

    public void init(ArrayList<Floor> floors) {
        // max 10 floors.

        for(int i = 0; i < floors.size(); i++) {
            Floor cFloor = floors.get(i);

            cFloor.getWalls();

            for(Wall w : cFloor.getWalls())
                addLine(w.getPoint1(), w.getPoint2(), -1, i);

            for(Door w : cFloor.getDoors())
                addLine(w.getPoint1(), w.getPoint2(), -1, i);
        }
    }

    class IntPair {
        // Ideally, name the class after whatever you're actually using
        // the int pairs *for.*
        int floor;
        int x;
        int y;
        IntPair() {}
        IntPair(int x, int y, int f) {this.x=x;this.y=y; floor = f;}
    }

    private double dist(int x1, int y1, int x2, int y2) {
        double X = x1 - x2;
        X *= X;
        double Y = y1 - y2;
        Y *= Y;
        return Math.sqrt(X + Y);
    }

    private ArrayList<Point> bfs(int Tfloor, int Tx, int Ty) {
        IntPair ant[][][] = new IntPair[11][511][511];
        boolean use[][][] = new boolean[11][511][511];
        ArrayList<Point> ret = new ArrayList<Point>();
        IntPair last = new IntPair();

        Queue<IntPair>q = new LinkedList<IntPair>();

        while(!q.isEmpty()) {
            IntPair from = q.peek();
            q.remove();

            if(Tfloor == from.floor && dist(Tx, Ty, from.x, from.y) <= 5.0) {
                last = new IntPair(from.floor, from.x, from.y);
            }

            for(int i = 0; i < 4; i++) {
                IntPair to = new IntPair(from.x + dx[i], from.y + dy[i], from.floor);
                if(!use[to.floor][to.x][to.y] && mat[to.floor][to.x][to.y] >= 0) {
                    ant[to.floor][to.x][to.y] = from;
                    use[to.floor][to.x][to.y] = true;
                    q.add(to);
                }
            }

            // TODO switch floor
        }

        while(last.floor != cFl && last.x != cX && last.y != cY) {
            ret.add(new Point(last.floor, new Vector2f(0.1f * last.x, 0.1f * last.y)));
            last = ant[last.floor][last.x][last.y];
        }


        return ret;
    }

    public ArrayList<Point> getPath(String roomName) {
        return bfs(0, 0, 0);
    }
}
