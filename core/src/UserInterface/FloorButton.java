package UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * Created by ada on 08.06.2018.
 */

public class FloorButton extends Actor {

    Sprite sprite = new Sprite(new Texture(Gdx.files.internal("exitButton.png")));
    boolean visible;
    private MainMenu menu;

    public FloorButton() {
        visible = true;
        Gdx.app.log("DEBUGA", "My actor has arrived!! ! x = " + sprite.getX() + "  w = " + sprite.getWidth());
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        sprite.setSize(sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);
        setPosition(0, Gdx.graphics.getHeight());
        addListener(new ActorGestureListener() {


            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("DEBUGA" + "apas pe menu");
                visible = !visible;
                setVisible(visible);
                Gdx.app.exit();

            }
        });
    }


}
