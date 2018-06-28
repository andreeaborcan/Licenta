package UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import javax.xml.bind.annotation.XmlElementDecl;

import jdk.nashorn.internal.objects.Global;

/**
 * Created by ada on 12.04.2018.
 */

public class TouchController3D implements GestureDetector.GestureListener{
    boolean dragging;
    public boolean inBuilding;

    public PerspectiveCamera cam;
    public TouchController3D()
    {
        inBuilding=false;
    }

    @Override
    public boolean touchDown(float x,float y, int pointer, int button) {
//        cam.position.set(cam.position.x, cam.position.y+10f, cam.position.z);
//        // ignore if its not left mouse button or first touch pointer
//        if (button != Input.Buttons.LEFT || pointer > 0) return false;
//        if (x< Gdx.graphics.getWidth()/3f && y>Gdx.graphics.getHeight()/3f &&  y<Gdx.graphics.getHeight()*2/3f )//stanga mijloc
//
//            cam.position.set(cam.position.x+0.5f, cam.position.y, cam.position.z);
//        else
//        if (x> Gdx.graphics.getWidth()/3f && x< Gdx.graphics.getWidth()*2/3f && y>Gdx.graphics.getHeight()/3f &&  y<Gdx.graphics.getHeight()*2/3f )//mijloc
//
//            cam.position.set(cam.position.x, cam.position.y+0.5f, cam.position.z);
//        else
//        if ( x> Gdx.graphics.getWidth()*2/3f && y>Gdx.graphics.getHeight()/3f &&  y<Gdx.graphics.getHeight()*2/3f )//dreapta mijloc
//
//            cam.position.set(cam.position.x-0.5f, cam.position.y, cam.position.z);
//        else
//
////            cam.position.set(0,cam.position.y-5, 0);
////        cam.lookAt(x/10f, 0, y/10f);
//            dragging = true;
        return false;

    }



    @Override
    public boolean tap(float x, float y, int count, int button) {
//        Quaternion rot = null;
//        cam.view.getRotation(rot);

////        System.out.println(cam.direction);
//        if (count==2)
//            cam.rotate(15, 0f,(float) Math.toRadians(15f),0f);
//        if (count==3)
//            cam.rotate(45, 0f,(float) Math.toRadians(45f),0f);

//        cam.direction.rotate(0f, 15f,0f, 15f);

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        System.out.println("Heeeelp long press!!!");
        System.out.println("dir = " + cam.direction);
//        System.out.println("view = " + cam.view + "]");
        if (!inBuilding)
        {

//            cam.rotate(0,0,0,0);
//            cam.rotate(cam.up, 15);

//            cam.view.setToRotation(0, 0, 0, 0);
            cam.position.set(8f, cam.position.y - 28f, -11f);
            cam.fieldOfView = 50;
            cam.lookAt(5.5f,0f,-11f);
            cam.direction.set(0, 0, -1);
            cam.up.set(0, 1, 0);
//            cam.view.setToRotation(0, 0, 0, 0);
        }
        else
        {
            cam.position.set(7f, cam.position.y + 28f, -8f);
            cam.lookAt(7f, 0f, -8f);
//            cam.view.setToRotation(0, 0, 0, 0);
        }
        inBuilding=!inBuilding;
        cam.update();
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        System.out.println("am facut pan in gestureListener de la punctul x,y: "+x+" "+y+" la distanta "+deltaX+" "+deltaY);
        x= Gdx.graphics.getHeight()-x;
        y=Gdx.graphics.getWidth()-y;
        System.out.println("am facut pan in gestureListener de la punctul x,y: "+x+" "+y);

        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        System.out.println("am terminat de facut pan la punctul x, y "+x+" "+y);
        x= Gdx.graphics.getHeight()-x;
        y=Gdx.graphics.getWidth()-y;
        System.out.println("am terminat de facut pan la punctul x, y "+x+" "+y);

        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if (cam.position.y>3)
            cam.position.set(cam.position.x, cam.position.y-0.1f, cam.position.z);
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
