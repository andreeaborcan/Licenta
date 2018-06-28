package UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.mygdx.game.Licenta;

import Screens.BuildingScreen3D;

/**
 * Created by ada on 21.05.2018.
 */

public class Button3D  extends Actor {
    ///in clasa asta ar trebui ca atunci cand apas pe minimap, sa se schimbe screen-ul cu screenul de la 2D!
    Sprite sprite=new Sprite(new Texture(Gdx.files.internal("3dButton.png")));
    boolean visible;
    private MainMenu menu;
    private Licenta l;

    public Button3D(Licenta licenta){
        l=licenta;
        visible=true;
        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        sprite.setSize(sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);
        setPosition(0,0);
        addListener(new ActorGestureListener() {


            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("DEBUGA" + "apas pe minimap");
                visible = !visible;
                setVisible(visible);
                l.setScreen(new BuildingScreen3D(l));


            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("DEBUGA", "touch done at (" + x + ", " + y + ")");
                visible=!visible;
                setVisible(visible);
            }
        });



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