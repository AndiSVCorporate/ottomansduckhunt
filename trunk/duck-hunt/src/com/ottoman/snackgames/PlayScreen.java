package com.ottoman.snackgames;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
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
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.esotericsoftware.tablelayout.Cell;
import com.ottoman.snackgames.Scene.HuntStage;
import com.ottoman.snackgames.Sprite.CrossHair;
import com.ottoman.snackgames.Sprite.HuntActor;
import com.ottoman.snackgames.Sprite.JoyStick;

public class PlayScreen extends InputAdapter implements Screen {
    SpriteBatch                     spriteBatch;            // #6
    HuntStage duckHuntStg ;Stage ctrlStg;JoyStick js;
    CrossHair cxh;    BitmapFont font;
    InputMultiplexer mplexer;
    String fireMsg = "";
    duckHunt game; 

    public PlayScreen(duckHunt game){
        this.game = game;
    }

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
		
        duckHuntStg.draw();
        ctrlStg.draw();
        spriteBatch.begin();
        
        if(Gdx.input.isKeyPressed(Keys.BACK))
        	game.setScreen(game.mainMenu);
        /*
		for (int i = 1; i < 10; i++) {
            if (Gdx.input.isTouched(i) == false) continue;

            float x = Gdx.input.getX(i);
            float y = Gdx.graphics.getHeight() - Gdx.input.getY(i) - 1;
            font.draw(spriteBatch, fireMsg + " TouchX =" + x + ", TouchY = " + y, 100, 400);
    }*/
		//font.draw(spriteBatch, fireMsg, 100, 400);
        if(js.isBtnFired){
          font.draw(spriteBatch, "fire : " + cxh.getXPos() + " - " + cxh.getYPos(), 100, 400);
	  		for(Actor act : duckHuntStg.getActors()){
				
	  			if(act.hit(cxh.getXPos(), cxh.getYPos())!=null)
				act.remove();
				
	  		}
	
          js.isBtnFired = false;
        }
		spriteBatch.end();
        

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setCatchBackKey(true);
		font = new BitmapFont();
		font.setColor(Color.WHITE);
        spriteBatch = new SpriteBatch();                                // #12
		duckHuntStg = new HuntStage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, spriteBatch);
		ctrlStg = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, spriteBatch);
		
		js = new JoyStick(new Texture(Gdx.files.internal("data/joystick.png")));
		mplexer = new InputMultiplexer();
		
		
		//Gdx.input.setInputProcessor(js);
		
		cxh = new CrossHair(new Texture(Gdx.files.internal("data/target.png")), js);
		/*
		Button fireBtn = new Button(new TextureRegion(new Texture(Gdx.files.internal("data/target.png"))));
		fireBtn.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				// TODO Auto-generated method stub
				fireMsg = "fire : " + cxh.getXPos() + " - " + cxh.getYPos(); 
			}
	    });
	    
		fireBtn.x = 700;
		fireBtn.y = 128;
        */
		ctrlStg.addActor(cxh);
		ctrlStg.addActor(js);
		//ctrlStg.addActor(fireBtn);
		
		mplexer.addProcessor(js);
		//mplexer.addProcessor(ctrlStg);
		Gdx.input.setInputProcessor(mplexer);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.input.setCatchBackKey(false);
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
		if(ctrlStg!=null)
		ctrlStg.dispose();
	}

}
