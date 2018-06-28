package UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import sun.applet.Main;

/**
 * Created by ada on 09.05.2018.
 */

public class MenuButton extends Actor {

    Sprite sprite=new Sprite(new Texture(Gdx.files.internal("menu.jpg")));
    boolean visible;
    private MainMenu menu;

    public MenuButton(MainMenu m){
        visible=true;
        menu=m;
        Gdx.app.log("DEBUGA", "My actor has arrived!! ! x = " + sprite.getX() + "  w = " + sprite.getWidth());
        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);
        setPosition(Gdx.graphics.getWidth()-sprite.getWidth(),Gdx.graphics.getHeight()- sprite.getHeight());
        addListener(new ActorGestureListener(){
            @Override
            public boolean longPress(Actor actor, float x, float y) {
                MoveToAction ma=new MoveToAction();
                ma.setPosition(x,y);
                ma.setDuration(5f);
                MenuButton.this.addAction(ma);
                return true;
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                super.zoom(event, initialDistance, distance);
            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("DEBUGA"+"apas pe menu" );
                visible=!visible;
                setVisible(visible);
                menu.setVisible(!menu.isVisible());

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



    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
