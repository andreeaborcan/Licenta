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

public class RotationButton extends Actor {
    Sprite sprite;
    boolean visible;
    int off;
    PerspectiveCamera pc;
    Engine engine;
    public RotationButton(final String filename, int offset, PerspectiveCamera p){
        off = offset;
        sprite=new Sprite(new Texture(Gdx.files.internal(filename)));
        visible=true;
        pc=p;
        sprite.setSize(sprite.getWidth()/3f,sprite.getHeight()/3f);
        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);
        setPosition((Gdx.graphics.getWidth()-sprite.getWidth())/2+offset,sprite.getHeight());
        addListener(new ActorGestureListener(){
            @Override
            public boolean longPress(Actor actor, float x, float y) {
                return true;
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                super.zoom(event, initialDistance, distance);
            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setVisible(!isVisible());
                if(off < 0) {
                    pc.rotate(15, 0f,(float) Math.toRadians(15f),0f);
                } else {
                    pc.rotate(-15, 0f,(float) Math.toRadians(15f),0f);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                setVisible(!isVisible());
            }
        });

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
