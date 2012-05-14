package com.ottoman.duckHunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.ottoman.duckHunt.Scene.HuntStage;
import com.ottoman.duckHunt.Sprite.HuntActor;

public class MainMenuScreen implements Screen {
    SpriteBatch                     spriteBatch;            // #6
    Button actNewBtn; 
    Stage menuStage ;
    BitmapFont font;
    
    duckHunt game; 

    public MainMenuScreen(duckHunt game){
        this.game = game;
    }
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
		
		menuStage.draw();
        
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		spriteBatch = new SpriteBatch();                                // #12
		menuStage = new Stage(480, 320, true);
		
		actNewBtn = new Button(new TextureRegion(new Texture(Gdx.files.internal("data/badlogic.jpg"))));
		actNewBtn.setClickListener(new ClickListener() {


			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(new PlayScreen(game));  
			}
	    });
		actNewBtn.x = 150;
		actNewBtn.y = 150;
		menuStage.addActor(actNewBtn);

		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		menuStage.dispose();
	}

}
