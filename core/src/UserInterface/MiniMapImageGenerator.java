package UserInterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
//import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import BuildingComponents.Door;
import BuildingComponents.Wall;
import BuildingComponents.Window;


/**
 * Created by ada on 21.05.2018.
 */



public class MiniMapImageGenerator {


    int width, height;
    int x, y;
    BufferedImage bi;
    Graphics2D ig;

    public MiniMapImageGenerator() {
        width = height = 200;
        init();
    }

    public MiniMapImageGenerator(int w, int h) {
        width = w;
        height = h;
        init();
    }
    public MiniMapImageGenerator(ArrayList<Wall>w, ArrayList<Wall> wup, ArrayList<Wall> wdown, ArrayList<Window> wi, ArrayList<Door>d)
    {
        width = height = 400;
        init();
        addDoors(d);
        addWalls(w,wup,wdown);
        addWindows(wi);
        writeImage("minimap.png");
    }

    void init() {
        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        ig = bi.createGraphics();
        ig.setStroke(new BasicStroke(2));
        ig.setPaint(Color.white);
        ig.fillRect(0, 0, width, height);
    }

    void addWalls (ArrayList<Wall> w,ArrayList<Wall> wup,ArrayList<Wall> wdown) {
        float x1,x2,y1,y2;
        for (int i=0;i<w.size();i++)
        {
            x1=w.get(i).getPoint1().getX();
            x2=w.get(i).getPoint2().getX();
            y1=w.get(i).getPoint1().getY();
            y2=w.get(i).getPoint2().getY();
            ig.setColor(Color.GREEN);
            System.err.println("ok");
            ig.draw(new Line2D.Float(x1,y1,x2,y2));
        }
        for (int i=0;i<wup.size();i++)
        {
            x1=wup.get(i).getPoint1().getX();
            x2=wup.get(i).getPoint2().getX();
            y1=wup.get(i).getPoint1().getY();
            y2=wup.get(i).getPoint2().getY();
            ig.setColor(Color.GREEN);
            System.err.println("ok");
            ig.draw(new Line2D.Float(x1,y1,x2,y2));
        }
        for (int i=0;i<wdown.size();i++)
        {
            x1=wdown.get(i).getPoint1().getX();
            x2=wdown.get(i).getPoint2().getX();
            y1=wdown.get(i).getPoint1().getY();
            y2=wdown.get(i).getPoint2().getY();
            ig.setColor(Color.GREEN);
            System.err.println("ok");
            ig.draw(new Line2D.Float(x1,y1,x2,y2));
        }
    }
    void addDoors (ArrayList<Door> d) {
        float x1,x2,y1,y2;
        for (int i=0;i<d.size();i++)
        {
            x1=d.get(i).getPoint1().getX();
            x2=d.get(i).getPoint2().getX();
            y1=d.get(i).getPoint1().getY();
            y2=d.get(i).getPoint2().getY();
            ig.setColor(Color.BLACK);
            System.err.println("ok");
            ig.draw(new Line2D.Float(x1,y1,x2,y2));
        }

    }
    void addWindows (ArrayList<Window> w) {
        float x1,x2,y1,y2;
        for (int i=0;i<w.size();i++)
        {
            x1=w.get(i).getPoint1().getX();
            x2=w.get(i).getPoint2().getX();
            y1=w.get(i).getPoint1().getY();
            y2=w.get(i).getPoint2().getY();
            ig.setColor(Color.BLUE);
            System.err.println("ok");
            ig.draw(new Line2D.Float(x1,y1,x2,y2));
        }

    }

    void setMyLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }


    void writeImage(String outFile) {
        BufferedImage bi2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi2.createGraphics();
        g.drawImage(bi, null, 0, 0);
        g.setColor(Color.red);
        g.drawOval(x, y, 3, 3);
        try {
            ImageIO.write(bi2, "PNG", new File(outFile));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


}
