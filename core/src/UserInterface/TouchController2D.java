package UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by ada on 22.05.2018.
 */

public class TouchController2D implements GestureDetector.GestureListener{
    boolean panstarted;
    int pandone=0;
    private Vector3 initialpoint,finalpoint;
    public OrthographicCamera cam;

    @Override
    public boolean touchDown(float x,float y, int pointer, int button) {
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

//        System.out.println(cam.direction);
        if (count==2)
            cam.rotate(15, 0f,(float) Math.toRadians(15f),0f);
        if (count==3)
            cam.rotate(45, 0f,(float) Math.toRadians(45f),0f);

//        cam.direction.rotate(0f, 15f,0f, 15f);

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        System.out.println("Heeeelp long press!!!");
        System.out.println("dir = " + cam.direction);

        cam.update();
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    public int getPandone() {
        return pandone;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        pandone=0;


        if(!panstarted) {
            pandone++;
            Vector3 v = new Vector3(x, y, 0);
            cam.setToOrtho(true);
            cam.project(v);
            initialpoint = new Vector3(v.x,v.y,0);
            panstarted = true;
            System.out.println("am facut pan in gestureListener de la punctul x,y:" +
                    " "+initialpoint.x+" "+initialpoint.y);
        }

        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Vector3 v=new Vector3(x,y,0);
//        cam.setToOrtho(true);
        cam.project(v);
        finalpoint=new Vector3(v.x,v.y,0);
        panstarted=false;
        pandone=-1;
        System.out.println("am terminat de facut pan la punctul x, y "+finalpoint.x+" "+finalpoint.y);
        return true;
    }


    @Override
    public boolean zoom(float initialDistance, float distance) {
        if (cam.position.y>3)
            cam.position.set(cam.position.x, cam.position.y-0.1f, cam.position.z);
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1,
                         Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public Vector3 getInitialpoint() {
        return initialpoint;
    }

    public Vector3 getFinalpoint() {
        return finalpoint;
    }

    public void setPandone(int pandone) {
        this.pandone = pandone;
    }
}
