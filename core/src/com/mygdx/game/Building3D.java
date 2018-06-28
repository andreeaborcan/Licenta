package com.mygdx.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;

import ModelComponent.ModelCameraComponent;
import UserInterface.DirectionButton;
import UserInterface.MainMenu;
import UserInterface.MenuButton;
import UserInterface.MiniMap;
import UserInterface.TouchController3D;

/**
 * Created by ada on 21.04.2018.
 */

public class Building3D {

    private static final float FOV = 67F;
    public boolean floorChanged = false;
    int newFloor;
    private ModelBatch modelBatch;
    private Environment environment;
    public PerspectiveCamera perspectiveCamera;
    private Engine engine;
    public EntityFactory entityFactory;
    public TouchController3D touch;
    public GestureDetector gd;
    public Stage stage;
    private MenuButton a;
    private DirectionButton up,down,right,left;
    private MainMenu menu;
    private MiniMap miniMap;
    private Vector2f buildCenter;
    private Skin skin;

    private ArrayList<String> roomNames;
    private ArrayList<Integer> floorNumbers;
    private Licenta lic;

    void initStage() {
        ScreenViewport sw=new ScreenViewport();
        stage=new Stage(sw);
        initBuilding();
        menu=new MainMenu(skin,roomNames,floorNumbers, this);

        a=new MenuButton(menu);
        miniMap=new MiniMap(lic);
        initPerspectiveCamera();
        initDirButtons();
        addActors(stage);
        stage.setKeyboardFocus(a);
    }

    public Building3D(Licenta licenta) {

        skin = new Skin();
        FileHandle fileHandle =
                Gdx.files.internal("uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);

        entityFactory =new EntityFactory("var1.json");
        lic = licenta;
        initStage();

    }
    private void addEntsToEngine(ArrayList<Entity> a) {
        for (int i=0;i<a.size();i++) {
            this.engine.addEntity(a.get(i));
        }
    }
    private void addEntityToEngine(Entity a) {
        this.engine.addEntity(a);
        System.out.println("Am adaugat entity-ul pt camera");
    }
    private void initDirButtons()
    {
        up=new DirectionButton("up.png",0, (float) 2,
                perspectiveCamera,1f, 1f, engine);
        down=new DirectionButton("down.png",0, -up.getSprite().getHeight(),
                perspectiveCamera,-1f,-1f, engine);

        left=new DirectionButton("left.png",-up.getSprite().getWidth(),
                (-up.getSprite().getHeight())/2,perspectiveCamera,1f,-1f, engine);
        right=new DirectionButton("right.png",up.getSprite().getWidth(),
                -up.getSprite().getHeight()/2,perspectiveCamera,-1f,1f, engine);

    }
    private void initBuilding()
    {
        engine = new Engine();
        entityFactory.setFloor(0);
        buildCenter= entityFactory.getBuildingCenter();
        addEntsToEngine(entityFactory.createWallsEntity());
        addEntsToEngine(entityFactory.createDoorsEntity());
        addEntsToEngine(entityFactory.createWallsDownEntity());
        addEntsToEngine(entityFactory.createWallsUpEntity());
        addEntsToEngine(entityFactory.createWindowsEntity());
        addEntsToEngine(entityFactory.createFloorEnts());
        Entity visitor = entityFactory.createVisitorEntity();

        roomNames= entityFactory.getRoomNames();
        floorNumbers= entityFactory.getFloorNumbers();
        System.out.println("before " + engine.getEntities().size());
        addEntityToEngine(visitor);
        System.out.println("after " + engine.getEntities().size());
        System.out.println("visitor has " + visitor.getComponents().get(0).getClass());
        System.out.println("modelcamera comps este egal cu - " + engine.getEntitiesFor(Family.one(ModelCameraComponent.class).get()).size());
        System.out.println("ultimul are clasa: " + engine.getEntities().get(engine.getEntities().size() - 1).getComponents().get(0).getClass());

        initModelBatch();
        initEnvironment();
        engine.addSystem(new RenderSystem(modelBatch, environment));
    }
    private void addActors(Stage stage){
        stage.addActor(a);
        stage.addActor(up);
        stage.addActor(down);
        stage.addActor(left);
        stage.addActor(right);
        stage.addActor(menu);
        stage.addActor(miniMap);

    }
    private void initPerspectiveCamera() {
        perspectiveCamera = new PerspectiveCamera(FOV,
                Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        ///////////// aici va trebui sa folosesti functiile de gasire a mijlocului cladirii!
        perspectiveCamera.position.set(buildCenter.getX(), 30f, buildCenter.getY());
        perspectiveCamera.lookAt(buildCenter.getX(), 0, buildCenter.getY());
//        perspectiveCamera.position.set(0,20,0);
//        perspectiveCamera.lookAt(0,0,0);
        perspectiveCamera.fieldOfView = 100f;
        perspectiveCamera.near = 1f;
        perspectiveCamera.far = 300f;
        perspectiveCamera.update();
        touch=new TouchController3D();
        touch.cam=perspectiveCamera;
        gd=new GestureDetector(touch);
        gd.setLongPressSeconds(0.2f);


//        engine.getEntities().get(engine.getEntities().size() - 1).getComponent(
//                ModelCameraComponent.class).setPosition(new Vector3f(perspectiveCamera.position.x,
//                perspectiveCamera.position.y,perspectiveCamera.position.z));

        engine.getEntitiesFor(Family.one(ModelCameraComponent.class).get()).get(0).getComponent(
                ModelCameraComponent.class).setPosition(new Vector3f(perspectiveCamera.position.x,
                perspectiveCamera.position.y,perspectiveCamera.position.z));

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, gd)); // to add gd

            }

    private void initEnvironment() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,
                0.3f, 0.3f, 0.3f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    private void initModelBatch() {
        modelBatch = new ModelBatch();
    }

    /*The ModelBatch is one of the objects, which require disposing, hence we add it
    to the dispose function. */
    public void dispose() {
        modelBatch.dispose();
    }

    /*With the camera set we can now fill in the resize function as well*/
    public void resize(int width, int height) {

        perspectiveCamera.viewportHeight = height;
        perspectiveCamera.viewportWidth = width;
    }

    //and set up the render function with the modelbatch
    public void render(float delta) {
//        if(floorChanged){
//            initStage();
////            initBuilding();
//            System.out.println("modelcamera comps " + this.engine.getEntitiesFor(Family.all(ModelCameraComponent.class).get()).size());
//            floorChanged = false;
////            initPerspectiveCamera();
////            initDirButtons();
//        }

        touch.cam = perspectiveCamera;
        perspectiveCamera.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(perspectiveCamera);
        engine.update(delta);
        modelBatch.end();
        stage.act(delta);
        stage.draw();
    }
}
