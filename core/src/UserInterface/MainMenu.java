package UserInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.Building3D;

import java.util.ArrayList;

/**
 * Created by ada on 19.05.2018.
 */

public class MainMenu extends Table {
    private ExitButton exit;
    private MenuButton mb;
    Building3D myBuilding;
//    private Skin skin;
    public MainMenu(Skin skin, ArrayList<String> rooms, ArrayList<Integer> floors, Building3D building)
    {
        myBuilding = building;
//        skin = new Skin();
//        FileHandle fileHandle =
//                Gdx.files.internal("uiskin.json");
//        FileHandle atlasFile = fileHandle.sibling("uiskin.atlas");
//        if (atlasFile.exists()) {
//            skin.addRegions(new TextureAtlas(atlasFile));
//        }
//        skin.load(fileHandle);
//        BackgroundColor backgroundColor = new BackgroundColor("white.png");
//        backgroundColor.setColor(2, 179, 228, 255); // r, g, b, a

        Texture texture = new Texture(Gdx.files.internal("congruent_outline.png"));
        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        setDebug(true);
        setFillParent(true);
        center();
        padTop(100);
        padRight(100);
        padLeft(700);
        padBottom(300);
        pack();
//        setBackground(backgroundColor);
        float tableHeight=Gdx.graphics.getHeight()-400;
        float tableWidth=Gdx.graphics.getWidth()-807;
//        pack();
//        setBackground("default-round-large");

        Label lblChooseFloor=new Label("Alegeti etajul:  ",skin);
//        lblChooseFloor = new Label("Alegeti etajul: ", );
//        lblChooseFloor.setColor(Color.RED);
        lblChooseFloor.getStyle().background = new Image(texture).getDrawable();
        lblChooseFloor.setFontScale(4f);
        Object[] etaje = new Object[floors.size()];
        Object[] camere=new Object[rooms.size()];
        for (int i=0;i<rooms.size();i++)
        {
            Label l = new Label(rooms.get(i), skin);
            l.setFontScale(4f);
            camere[i] = l;
        }


        final SelectBox<Object> sb = new SelectBox<Object>(skin);
        for (int i=0;i<floors.size();i++)
            etaje[i] = ("Etaj " + floors.get(i).toString());
        sb.setItems(etaje);
        sb.getStyle().font.getData().setScale(4f);
//        for(Object l : sb.getItems()) {
//            ((Label) l).setFontScale(4f);
//        }
//        sb.getStyle().background = new Image(new Texture(Gdx.files.internal("congruent_outline.png"))).getDrawable();
        sb.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
//                int i= Integer.parseInt(((Label) sb.getSelected()).getText().toString());
                int i = sb.getSelectedIndex();
                System.out.println(i);
                System.out.println("camera is " + myBuilding.perspectiveCamera);
                PerspectiveCamera cam = myBuilding.perspectiveCamera;
                float yy = cam.position.y;
                while(yy > 99f) yy -= 100f;
                cam.position.set(cam.position.x, i*100f + yy, cam.position.z);
            }
        });


        final SelectBox<Object> sb2 = new SelectBox<Object>(skin);
        sb2.setItems(camere);
        sb2.getStyle().background = new Image(texture).getDrawable();
        sb2.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(((Label) sb2.getSelected()).getText().toString().equals("Exit"))
                {
                    Gdx.app.exit();
                }
                else
                    System.out.println(((Label) sb2.getSelected()).getText()+" hmmmm?");
            }
        });

        Label lblChooseRoom=new Label("Alegeti sala:  ",skin);
        lblChooseRoom.setFontScale(4f);
        Label lblExit=new Label("Exit  ",skin);
        lblExit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        System.out.println("DEBUGA"+ " Am ajuns in main menu");
        exit=new ExitButton();
        mb=new MenuButton(this);
        lblExit.setFontScale(4f);
//        setBounds(exit.getX(),exit.getY(),exit.getWidth()/4f,exit.getHeight()/4f);
//        add(exit);


        add(lblChooseFloor).width(tableWidth/2).height(tableHeight/3);
        add(sb).width(tableWidth/2).height(tableHeight/3);
        row();
        add(lblChooseRoom).width(tableWidth/2).height(tableHeight/3);
        add(sb2).width(tableWidth/2).height(tableHeight/3);
        row();
//        lblExit.setAlignment();
        add(lblExit).height(tableHeight/3).colspan(2).fill();
        row();
//        add(exit);
        setPosition(0,0);
        setVisible(false);
//        setBounds(10,10,200,200);
    }
}
