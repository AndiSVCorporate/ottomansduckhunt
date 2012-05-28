package com.ottoman.snackgames;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//public class duckHunt implements ApplicationListener  {
	public class duckHunt extends Game  {
	
		PlayScreen newGame = new PlayScreen(this);
        MainMenuScreen mainMenu = new MainMenuScreen(this);
		SpriteBatch                     spriteBatch;            // #6
	    BitmapFont font;
	    
	@Override
	public void create() {
		Settings.load();
		//duckSettings.load();
		Texture.setEnforcePotImages(false);
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




