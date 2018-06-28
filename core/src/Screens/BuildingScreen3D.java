package Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.mygdx.game.Building3D;
import com.mygdx.game.Licenta;

import java.util.ArrayList;

import BuildingComponents.Wall;

/**
 * Created by ada on 21.04.2018.
 */

public class BuildingScreen3D implements Screen {
    Licenta licenta;
    Building3D building3D;
    public BuildingScreen3D(Licenta licenta) {
        this.licenta = licenta;
        building3D = new Building3D(licenta);
//        licenta.instances = new ArrayList<ModelInstance>();
//        for(Wall w : building3D.entityFactory.getWalls()) {
//            licenta.instances.add(w.getModelInstance());
//        }
    }
    @Override
    public void render(float delta) {
        building3D.render(delta);
    }
    @Override
    public void resize(int width, int height) {
        building3D.resize(width, height);
    }
    @Override
    public void dispose() {
        building3D.dispose();
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
