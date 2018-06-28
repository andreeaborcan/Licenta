package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;

import Screens.BuildingScreen2D;
import Screens.BuildingScreen3D;

public class Licenta extends ApplicationAdapter{
	public static final float VIRTUAL_WIDTH = 960;//Gdx.graphics.getWidth();
	public static final float VIRTUAL_HEIGHT = 540;//Gdx.graphics.getHeight();
	Screen screen;
	public ArrayList<ModelInstance> instances;

	@Override
	public void create() {
		Gdx.app.log("DEBUGA","am ajuns aici!!!");
		setScreen(new BuildingScreen2D(this));
		Gdx.app.log("DEBUGA","facut building screen!!!");

	}
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
				GL20.GL_DEPTH_BUFFER_BIT);
//		Gdx.app.log("DEBUGA","rendeer meee!");
		screen.render(Gdx.graphics.getDeltaTime());
//		Gdx.app.log("DEBUGA","rendeer finish!");
	}
	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}
	public void setScreen(Screen screen) {
		if (this.screen != null) {
			this.screen.hide();
			this.screen.dispose();
		}
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(),
					Gdx.graphics.getHeight());
		}
	}

}
