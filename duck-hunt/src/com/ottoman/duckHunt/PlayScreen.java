package com.ottoman.duckHunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ottoman.duckHunt.Scene.HuntStage;
import com.ottoman.duckHunt.Sprite.HuntActor;

public class PlayScreen implements Screen {
    SpriteBatch                     spriteBatch;            // #6
    HuntActor flyDuck1;HuntActor flyDuck2;HuntActor flyDuck3; 
    HuntStage duckHuntStg ;
    BitmapFont font;
    
    duckHunt game; 

    public PlayScreen(duckHunt game){
        this.game = game;
    }

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
		for(Actor act : duckHuntStg.getActors()){
			act.x++;
		}
        duckHuntStg.draw();
        spriteBatch.begin();
        font.draw(spriteBatch, "laaan - :(", 10, 10);
        spriteBatch.end();
        
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		font = new BitmapFont();
		font.setColor(Color.WHITE);
        spriteBatch = new SpriteBatch();                                // #12
		duckHuntStg = new HuntStage(480, 320, true, spriteBatch);
		flyDuck1 = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, 64, 64);
		flyDuck1.x = 0;
		flyDuck1.y = 150;
		duckHuntStg.addActor(flyDuck1);

		flyDuck2 = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, 48, 48);
		flyDuck2.x = 0;
		flyDuck2.y = 200;
		duckHuntStg.addActor(flyDuck2);

		flyDuck3 = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, 32, 32);
		flyDuck3.x = 0;
		flyDuck3.y = 250;
		duckHuntStg.addActor(flyDuck3);
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
		if(duckHuntStg!=null)
		duckHuntStg.dispose();
	}

}