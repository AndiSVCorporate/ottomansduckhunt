package com.ottoman.snackgames;


import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.utils.Scaling;
import com.ottoman.snackgames.Scene.HuntStage;
import com.ottoman.snackgames.Sprite.HuntActor;

//public class duckHunt implements ApplicationListener  {
	public class duckHunt extends Game  {
	
		PlayScreen newGame = new PlayScreen(this);
        MainMenuScreen mainMenu = new MainMenuScreen(this);
		SpriteBatch                     spriteBatch;            // #6
	    
	    BitmapFont font;
	    
	@Override
	public void create() {
		Gdx.input.setCatchBackKey(true);
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		setScreen(mainMenu);
	}

	 

	
	@Override
	public void dispose() {
		newGame.dispose();
		mainMenu.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
        spriteBatch.begin();
        //font.draw(spriteBatch, "ulaaayn - :'(", 10, 10);
        spriteBatch.end();
        
        	

		super.render();

	}

	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
    
}




