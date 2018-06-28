package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

import BuildingComponents.Door;
import BuildingComponents.Floor;
import BuildingComponents.Room;
import BuildingComponents.Wall;
import BuildingComponents.Window;
import UserInterface.Button3D;
import UserInterface.MainMenu;
import UserInterface.MenuButton;
import UserInterface.TouchController2D;
import UserInterface.TouchController3D;

/**
 * Created by ada on 21.05.2018.
 */

public class Building2D {

    private static final float FOV = 67F;
    private SpriteBatch spriteBatch;
    private Environment environment;
    private OrthographicCamera orthographicCamera;
    public Stage stage;
    private MenuButton a;
    private MainMenu menu;
    private Parser parser;
    private static ShapeRenderer shapeRenderer;
    private Button3D buton3D;
    private Vector2f buildCenter;
    public BitmapFont font;
    public TouchController2D touch;
    public GestureDetector gd;
    public ArrayList<Vector3> insertedWalls;
    private ArrayList<Floor> floors;
    private Skin skin;
    private ArrayList<String> roomNames;
    private ArrayList<Integer> floorNumbers;


    public Building2D(Licenta licenta) {
        buton3D=new Button3D(licenta);
        font=new BitmapFont();
        initSpriteBatch();
        insertedWalls=new ArrayList<Vector3>();
        shapeRenderer = new ShapeRenderer();
        ScreenViewport sw=new ScreenViewport();
        stage=new Stage(sw);

        initBuilding("var1.json");
        skin = new Skin();
        FileHandle fileHandle =
                Gdx.files.internal("uiskin.json");
        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
        menu=new MainMenu(skin,roomNames,floorNumbers,null);
        a=new MenuButton(menu);

        initPerspectiveCamera();
        addActors(stage);
        stage.setKeyboardFocus(a);
        initEnvironment();



    }


    private void initBuilding(String filename)
    {
        parser=new Parser(filename);
        floors=parser.getFloors();
        floorNumbers=parser.getFloorNumbers();
        roomNames=parser.getRoomNames();
        drawWalls(floors.get(0).getWalls());
        drawDoors(floors.get(0).getDoors());
        System.out.println(floors.size());
        drawWindows(floors.get(0).getWindows());

        buildCenter=floors.get(0).getFloorCenter();
    }
    private void addActors(Stage stage){
        stage.addActor(a);
        stage.addActor(menu);
        stage.addActor(buton3D);

    }
    private void initPerspectiveCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        orthographicCamera = new OrthographicCamera(100, 100 * (h / w));
        System.out.println("sunt in init camera si vreau sa vad care e centrul cladirii "+
                buildCenter);
        orthographicCamera.position.set(buildCenter.getX(),buildCenter.getY(),50);
        orthographicCamera.lookAt(buildCenter.getX(),buildCenter.getY(),0);
        //orthographicCamera.rotate(180);
        orthographicCamera.update();
        touch=new TouchController2D();
        touch.cam=orthographicCamera;
        gd=new GestureDetector(touch);
        gd.setLongPressSeconds(0.2f);
        Gdx.input.setInputProcessor(new InputMultiplexer(stage,gd)); // to add gd
    }

    private void initEnvironment() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,
                0.3f, 0.3f, 0.3f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
    }

    private void initSpriteBatch() {
        spriteBatch = new SpriteBatch();
    }

    /*The ModelBatch is one of the objects, which require disposing, hence we add it
    to the dispose function. */
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
    }

    /*With the camera set we can now fill in the resize function as well*/
    public void resize(int width, int height) {

        orthographicCamera.viewportWidth = 100;
        orthographicCamera.viewportHeight = 100*height/width;
        orthographicCamera.update();
    }

    //and set up the render function with the modelbatch
    public void render(float delta) {
        orthographicCamera.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        drawWalls(floors.get(0).getWalls());
        drawDoors(floors.get(0).getDoors());
        drawWindows(floors.get(0).getWindows());
        if (touch.getPandone()==-1) {
            insertedWalls.add(touch.getInitialpoint());
            insertedWalls.add(touch.getFinalpoint());
            touch.setPandone(0);
        }
        drawInsertedWalls(insertedWalls);
        drawRoomNames(floors.get(0).getRooms());

        stage.act(delta);
        stage.draw();
    }

    public static void drawInsertedWalls(ArrayList<Vector3> w)
    {
        Gdx.gl.glLineWidth(10);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (int i=0;i<w.size();i+=2)
        {
            shapeRenderer.line(w.get(i).x,w.get(i).y,w.get(i+1).x,w.get(i+1).y);
        }

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
    public void drawRoomNames(ArrayList<Room> w)
    {
        spriteBatch.begin();
        font.setColor(Color.WHITE);
        for (int i=0;i<w.size();i++)
        {
            font.getData().setScale(0.07f);
            font.draw(spriteBatch, w.get(i).getRoomName(), w.get(i).getRoomCenter().x,
                    w.get(i).getRoomCenter().y);
            System.out.println("numele camerei "+i+" "+w.get(i).getRoomName());
        }
        spriteBatch.end();


    }
    public static void drawWalls(ArrayList<Wall> w)
    {
        Gdx.gl.glLineWidth(10);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        for (int i=0;i<w.size();i++)
        {
            shapeRenderer.line(w.get(i).getPoint1().getX(),w.get(i).getPoint1().getY(),
                    w.get(i).getPoint2().getX(), w.get(i).getPoint2().getY());
        }

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
    public static void drawWindows(ArrayList<Window> w)
    {
        Gdx.gl.glLineWidth(10);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.SKY);
        for (int i=0;i<w.size();i++)
        {
            shapeRenderer.line(w.get(i).getPoint1().getX(),w.get(i).getPoint1().getY(),
                    w.get(i).getPoint2().getX(), w.get(i).getPoint2().getY());
        }

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
    public static void drawDoors(ArrayList<Door> w)
    {
        Gdx.gl.glLineWidth(10);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BROWN);
        for (int i=0;i<w.size();i++)
        {
            shapeRenderer.line(w.get(i).getPoint1().getX(),w.get(i).getPoint1().getY(),
                    w.get(i).getPoint2().getX(), w.get(i).getPoint2().getY());
        }

        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }


}
