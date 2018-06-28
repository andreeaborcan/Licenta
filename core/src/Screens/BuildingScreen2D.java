package Screens;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Building2D;
import com.mygdx.game.Licenta;

/**
 * Created by ada on 21.05.2018.
 */

public class BuildingScreen2D implements Screen {
    Licenta licenta;
    Building2D building;
    public BuildingScreen2D(Licenta licenta) {
        this.licenta = licenta;
        building = new Building2D(licenta);
    }
    @Override
    public void render(float delta) {
        building.render(delta);
    }
    @Override
    public void resize(int width, int height) {
        building.resize(width, height);
    }
    @Override
    public void dispose() {
        building.dispose();
    }

    @Override
    public void show() {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


}
