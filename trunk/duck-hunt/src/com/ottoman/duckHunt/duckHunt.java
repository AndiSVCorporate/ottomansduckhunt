package com.ottoman.duckHunt;

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

//public class duckHunt implements ApplicationListener  {
	public class duckHunt extends Game  {
	
	
    SpriteBatch                     spriteBatch;            // #6
    HuntActor flyDuck1;HuntActor flyDuck2;HuntActor flyDuck3; 
    HuntStage duckHuntStg ;
	
	@Override
	public void create() {
		
		
        spriteBatch = new SpriteBatch();                                // #12
		duckHuntStg = new HuntStage(480, 320, true, spriteBatch);
		flyDuck1 = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, 32, 32);
		flyDuck1.x = 100;
		flyDuck1.y = 100;
		duckHuntStg.addActor(flyDuck1);

		flyDuck2 = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, 48, 48);
		flyDuck2.x = 200;
		flyDuck2.y = 100;
		duckHuntStg.addActor(flyDuck2);

		flyDuck3 = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, 64, 64);
		flyDuck3.x = 300;
		flyDuck3.y = 100;
		duckHuntStg.addActor(flyDuck3);

	}

	 

	
	
	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		 Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
        
        // spriteBatch.begin();
        duckHuntStg.draw();
        // spriteBatch.end();

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




