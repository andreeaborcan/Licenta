package PathFinding;


import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import BuildingComponents.Door;
import BuildingComponents.Floor;
import BuildingComponents.Wall;

// TODO Dymanic Path Finder - cand ma misc se reactualizeaza... asta poate fi facut simplu,
// de fiecare data rulez algoritmul cand ma misc
// sau la fiecare randare?? -  asta nu cred ca e prea bine

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
    final int SCALE = 2, MAX = 500;
    int dx[] = {1, 0, -1, 0};
    int dy[] = {0, -1, 0, 1};

    public PathFinder() {

    }

    public void setPosition(int floor, int x, int y) {
        cFl = floor;
        cX = x;
        cY = y;
    }

    private IntPair getIntPoint(Vector3 v) {
        return new IntPair((int) v.x * SCALE, -(int) v.z * SCALE, (int) v.y / 100);
    }

    public void setPosition(Vector3 v) {
        setPosition((int) v.y / 100, (int) v.x * SCALE, -(int) v.z * SCALE);
    }

    private void addLine(Vector2f a, Vector2f b, int val, int floor) {
        int ax = (int)(a.x * SCALE);
        int ay = -(int)(a.y * SCALE);
        int bx = (int)(b.x * SCALE);
        int by = -(int)(b.y * SCALE);

        if (Math.abs(ax - bx) <= Math.abs(ay - by)) {
            if(ay > by) {
                int tmp = ax; ax = bx; bx = tmp;
                tmp = ay; ay = by; by = tmp;
            }
            double dx = 1. * (ax - bx) / Math.abs(ay - by), di = ax;
            for(int i = ay + val; i <= by - val; i++) {
                di += dx;
                if(val == -1)
                    mat[floor][(int) di + 1][i] = val;
                mat[floor][(int) di][i] = val;
                if((int) di - 1 > 0)
                    mat[floor][(int) di - 1][i] = val;
            }
        } else {
            if(ax > bx) {
                int tmp = ax; ax = bx; bx = tmp;
                    tmp = ay; ay = by; by = tmp;
            }
            double dy = 1. * (ay - by) / (ax - bx), dj = ay;
            for(int i = ax + val; i <= bx - val; i++) {
                dj += dy;
                if(val == -1)
                    mat[floor][i][(int) dj + 1] = val;
                mat[floor][i][(int) dj] = val;
                if((int) dj - 1 > 0 && val == -1)
                    mat[floor][i][(int) dj - 1] = val;
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
            for(Wall w : cFloor.getWallsUp())
                addLine(w.getPoint1(), w.getPoint2(), -1, i);

            for(Door w : cFloor.getDoors())
                addLine(w.getPoint1(), w.getPoint2(), 0, i);
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

    private boolean ok(IntPair to) {
        if(to.floor <0 || to.floor > 10)
            return false;
        if(to.y < 0 || to.y > MAX)
            return false;
        if(to.x < 0 || to.x > MAX)
            return false;
        return true;
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

        for(int i = 0; i < 60; i++) {
            for(int j = 0 ;j < 60; j++) {
                System.out.print(mat[Tfloor][i][j]);
            }
            System.out.println();
        }
        System.out.println("==============");
        ArrayList<Point> ret = new ArrayList<Point>();
//        ret.add(new Point(0, new Vector2f(13f, -3f)));
//        ret.add(new Point(0, new Vector2f(14f, -3f)));
//        ret.add(new Point(0, new Vector2f(15f, -3f)));
//        ret.add(new Point(0, new Vector2f(16f, -3f)));
//        ret.add(new Point(0, new Vector2f(17f, -3f)));
//        ret.add(new Point(0, new Vector2f(17f, -4f)));
//        ret.add(new Point(0, new Vector2f(17f, -5f)));
//        ret.add(new Point(0, new Vector2f(17f, -6f)));
//        ret.add(new Point(0, new Vector2f(17f, -7f)));
//        ret.add(new Point(0, new Vector2f(17f, -8f)));
//
//        if(true)
//            return ret;

        IntPair last = null;//new IntPair();

        Queue<IntPair>q = new LinkedList<IntPair>();
        System.out.println("I am at:     " + cFl + " " + cX + " " + cY);
        System.out.println("Wanna go at: " + Tfloor + " " + Tx + " " + Ty);
        q.add(new IntPair(cX, cY, cFl));

        while(!q.isEmpty()) {
            IntPair from = q.peek();
            q.remove();
//            System.out.println(from.x + " " + from.y);
            if(Tfloor == from.floor &&   dist(Tx, Ty, from.x, from.y) <= 3.0) {
                last = new IntPair(from.x, from.y, from.floor);
                break;
            }

            for(int i = 0; i < 4; i++) {
                IntPair to = new IntPair(from.x + dx[i], from.y + dy[i], from.floor);
                if(ok(to) &&!use[to.floor][to.x][to.y] && mat[to.floor][to.x][to.y] >= 0) {
                    ant[to.floor][to.x][to.y] = from;
                    use[to.floor][to.x][to.y] = true;
                    q.add(to);
                }
            }

            // TODO switch floor
        }

        while(last != null && (last.floor != cFl || last.x != cX || last.y != cY)) {
            ret.add(new Point(last.floor, new Vector2f(1f * last.x / SCALE, -1f * last.y / SCALE)));
            last = ant[last.floor][last.x][last.y];
        }
        return ret;
    }

    public ArrayList<Point> getPath(Point v) {

        return bfs(v.floor, (int)v.pos.x * SCALE, -(int)v.pos.y * SCALE);
    }
}
