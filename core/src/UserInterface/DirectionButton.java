package UserInterface;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Vector;

import ModelComponent.ModelCameraComponent;
import ModelComponent.ModelComponent;
import ModelComponent.ModelDoorComponent;
import ModelComponent.ModelWallComponent;
import ModelComponent.ModelWallDownComponent;
import ModelComponent.ModelWallUpComponent;
import ModelComponent.ModelWindowComponent;

/**
 * Created by ada on 15.05.2018.
 */

public class DirectionButton extends Actor {
    Sprite sprite;
    boolean visible;
    float camx,camy;
    PerspectiveCamera pc;
    Engine engine;
    public DirectionButton(final String filename, float posx, float posy, PerspectiveCamera p, float cx, float cy, Engine e){
        engine = e;
        sprite=new Sprite(new Texture(Gdx.files.internal(filename)));
        visible=true;
        pc=p;
        this.camx=cx;
        this.camy=cy;
        sprite.setSize(sprite.getWidth()/4.5f,sprite.getHeight()/4.5f);
        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);
        setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2+posx,sprite.getHeight()+posy);
        addListener(new ActorGestureListener(){
            @Override
            public boolean longPress(Actor actor, float x, float y) {
                MoveToAction ma=new MoveToAction();
                ma.setPosition(x,y);
                ma.setDuration(5f);
                DirectionButton.this.addAction(ma);
                return true;
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                super.zoom(event, initialDistance, distance);
            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button touched is " + filename);
                visible=!visible;
                setVisible(visible);
                System.out.println("camera:: " + pc.position.x + " " + pc.position.y);
                System.out.println("camera direction:: " + pc.direction.x + " " + pc.direction.y + " " + pc.direction.z);
                pc.normalizeUp();
                float dx = pc.direction.x;
                float dz = pc.direction.z;
                if(dx == 0 && dz == 0) {
                    dx = pc.up.x;
                    dz = pc.up.z;
                }
                double targetX, targetZ;
                dx /= 2;
                dz /= 2;
                if(camx * camy < 0 ) {
//                    float yy = pc.position.y;
//                    while(yy > 99f) yy -= 100f;
//                    if(yy < 20f) {
//                        if(camy == -1)
//                            pc.rotate(15, 0f,(float) Math.toRadians(15f),0f);
//                        else
//                            pc.rotate(-15, 0f,(float) Math.toRadians(15f),0f);
//                        return;
//                    }
                    targetX = pc.position.x + camx * dz;
                    targetZ = pc.position.z + camy * dx;

                }
                else {
                    targetX = pc.position.x + camx * dx;
                    targetZ = pc.position.z + camy * dz;
                }

                ImmutableArray<Entity> ae = engine.getEntitiesFor(Family.one(
                        ModelWallComponent.class,
                        ModelWindowComponent.class,
                        ModelWallUpComponent.class,
                        ModelWallDownComponent.class).get());

                ImmutableArray<Entity> tmp = engine.getEntitiesFor(Family.one(
                        ModelCameraComponent.class).get());

                Entity camEntity = tmp.get(0);
                ModelCameraComponent camModel = (ModelCameraComponent) camEntity.getComponents().get(0);

                ArrayList<ModelComponent> chk = new ArrayList<ModelComponent>();
                for(Entity e : ae) {
                    chk.add( (ModelComponent) e.getComponents().get(0));
                }

                BoundingBox camBox = new BoundingBox();
                camModel.instance.calculateBoundingBox(camBox);
                System.out.println("inaltimea e "+ camBox.getHeight());
                Vector3 oldpos = new Vector3(pc.position.x, pc.position.y, pc.position.z);
                System.out.println("old position " + oldpos);
                pc.position.set((float) targetX, pc.position.y, (float) targetZ);
                camModel.setPosition(new Vector3f( (float) targetX, pc.position.y,(float) targetZ));
                System.out.println("new position" + pc.position);
                camBox.mul(camModel.instance.transform);

                System.out.println(chk.size());
                int i = 1;
                boolean hasCollision = false;
                for(ModelComponent elem : chk) {
                    BoundingBox wall = new BoundingBox();
                    elem.instance.calculateBoundingBox(wall);
                    wall.mul(elem.instance.transform);
                    hasCollision |= camBox.intersects(wall);
                    i++;
                }
                if(hasCollision) {
                    System.out.println("Nu ma misc!!!!! Esti prooost?");
                    pc.position.set(oldpos);
                    camModel.setPosition(new Vector3f(oldpos.x, oldpos.y, oldpos.z));
                }
//                System.out.println("camera:: " + pc.position.x + " " + pc.position.y);
//                pc.lookAt(pc.position.x+camx, 0f, pc.position.z+camy);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUGA", "touch done at (" + x + ", " + y + ")");
                visible=!visible;
                setVisible(visible);
            }
        });

//        addListener(new InputListener() {
//            @Override
//            public boolean scrolled(InputEvent event, float x, float y, int amount) {
//                return super.scrolled(event, x, y, amount);
//            }
//
//            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//                Gdx.app.log("DEBUGA", "touch started at (" + x + ", " + y + ")");
//                visible=!visible;
//                setVisible(visible);
//
//                return true;
//            }
//
//            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                    Gdx.app.log("DEBUGA", "touch done at (" + x + ", " + y + ")");
//                visible=!visible;
//                setVisible(visible);
//            }
//
//            @Override
//            public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                super.touchDragged(event, x, y, pointer);
//            }
//        });

    }

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(),getY());
        super.positionChanged();
    }
    public Sprite getSprite(){
        return sprite;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
