package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.util.ArrayList;

import ModelComponent.ModelCameraComponent;
import ModelComponent.ModelDoorComponent;
import ModelComponent.ModelFloorComponent;
import ModelComponent.ModelPathComponent;
import ModelComponent.ModelWallComponent;
import ModelComponent.ModelWallDownComponent;
import ModelComponent.ModelWallUpComponent;
import ModelComponent.ModelWindowComponent;

/**
 * Created by ada on 21.04.2018.
 */

public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private ModelBatch batch;
    private Environment environment;
    private ArrayList<Entity> path;

    public RenderSystem(ModelBatch batch, Environment environment) {
        this.batch = batch;
        this.environment = environment;
    }
    public void addedToEngine(Engine e) {
        entities = e.getEntitiesFor(Family.one(
                ModelWallComponent.class,
                ModelCameraComponent.class,
                ModelWindowComponent.class,
                ModelDoorComponent.class,
                ModelWallUpComponent.class,
                ModelFloorComponent.class,
                ModelWallDownComponent.class).get());
    }

    public void setPath(ArrayList<Entity> ae) {
        path = ae;
    }

    public void update(float delta) {
//        System.out.println(entities.size());
//        System.out.println("env is " + environment);
        for (int i = 0; i < entities.size(); i++) {
            ModelWindowComponent mod ;
            mod=entities.get(i).getComponent(ModelWindowComponent.class);
            if(mod != null && mod.instance != null)
                batch.render(mod.instance, environment);

            ModelWallComponent mod2= entities.get(i).getComponent(ModelWallComponent.class);
            if(mod2 != null)
                batch.render(mod2.instance, environment);

            ModelWallUpComponent mod3= entities.get(i).getComponent(ModelWallUpComponent.class);
            if(mod3 != null)
                batch.render(mod3.instance, environment);

            ModelWallDownComponent mod4= entities.get(i).getComponent(ModelWallDownComponent.class);
            if(mod4 != null) {
                batch.render(mod4.instance, environment);
            }
            ModelDoorComponent mod5= entities.get(i).getComponent(ModelDoorComponent.class);
            if(mod5 != null)
                batch.render(mod5.instance, environment);

            ModelCameraComponent mod6= entities.get(i).getComponent(ModelCameraComponent.class);
            if(mod6 != null)
                batch.render(mod6.instance, environment);


            ModelFloorComponent mod7= entities.get(i).getComponent(ModelFloorComponent.class);
            if(mod7 != null) {
                batch.render(mod7.instance, environment);
            }
        }

        if(path != null)
            for(Entity e : path) {
                ModelPathComponent mod = e.getComponent(ModelPathComponent.class);
                if(mod != null) {
                    batch.render(mod.instance, environment);
                }
            }
    }
}
