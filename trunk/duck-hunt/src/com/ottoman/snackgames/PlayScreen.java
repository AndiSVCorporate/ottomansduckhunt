package com.ottoman.snackgames;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.esotericsoftware.tablelayout.Cell;
import com.ottoman.snackgames.Scene.HuntStage;
import com.ottoman.snackgames.Sprite.CrossHair;
import com.ottoman.snackgames.Sprite.HuntActor;
import com.ottoman.snackgames.Sprite.JoyStick;

public class PlayScreen extends InputAdapter implements Screen {
    SpriteBatch                     spriteBatch;            // #6
    Texture backGrnd;
    HuntStage duckHuntStg ;Stage ctrlStg;Stage ui;
    JoyStick js;CrossHair cxh;BitmapFont font;
    InputMultiplexer mplexer;
    String fireMsg = "";
    duckHunt game; 
    private boolean isPaused = false;
    Window pauseWin;
    Skin skin;
    float timePassed = 0;
    int gameDuration = 90;
    Label pauseLabel;

    Sound fire;   Music forest;
    
    public PlayScreen(duckHunt game){
        this.game = game;
    }

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
		
        spriteBatch.begin();
        spriteBatch.draw(backGrnd, 0, 0);
        
		spriteBatch.end();

        if(Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE))
        	game.setScreen(game.mainMenu);
        /*
        if(Gdx.input.isKeyPressed(Keys.MENU)){
        	isPaused = !isPaused;
        	duckHuntStg.setPause(isPaused);
            pauseWin.visible = isPaused;
        }
        */
		//font.draw(spriteBatch, fireMsg, 100, 400);
        if(!isPaused){
    		timePassed += Gdx.graphics.getDeltaTime();
            duckHuntStg.draw();
            ctrlStg.draw();
          if(js.isBtnFired){
            //font.draw(spriteBatch, "fire : " + cxh.getXPos() + " - " + cxh.getYPos(), 100, 400);
        	  fire.play();
            duckHuntStg.fireEvent(cxh.getXPos(), cxh.getYPos());
            js.isBtnFired = false;
          }
        }else{
            //ctrlStg.draw();
            ui.draw();
            //spriteBatch.begin();
        	//font.setScale(4);
        	//font.draw(spriteBatch, "PAUSE", 128, Gdx.graphics.getHeight()/3);
    		//spriteBatch.end();
        }
        int timeElapsed =gameDuration - (int)timePassed;
        int tmpMinute = (int)Math.floor(timeElapsed / 60);
        String stElapsed = (tmpMinute < 10)?("0" + tmpMinute) : tmpMinute + "";
        timeElapsed =(int) (timeElapsed - (tmpMinute * 60));
        stElapsed = stElapsed + ":" + ((timeElapsed<10)?"0"+timeElapsed:timeElapsed+""); 
        		
        spriteBatch.begin();
    	font.setScale(1);
        font.draw(spriteBatch, "Score = " + duckHuntStg.getHitScore(), 10, 460);
        font.draw(spriteBatch, stElapsed, 650, 460);
		spriteBatch.end();
		if((int)timePassed==gameDuration){
			forest.stop();
			pauseLabel.setText("Your Score is " + duckHuntStg.getHitScore());
			Settings.addScore(duckHuntStg.getHitScore());
			Settings.save();
			isPaused = true;
			duckHuntStg.setPause(isPaused);
            pauseWin.visible = isPaused;
		}
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.MENU || keycode == Keys.P){
			pauseLabel.setText("PAUSE");
        	isPaused = !isPaused;
        	duckHuntStg.setPause(isPaused);
            pauseWin.visible = isPaused;
            if(isPaused)
            	forest.pause();
            else
            	forest.play();
		}
		return false;
	};

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub4
		fire = Gdx.audio.newSound(Gdx.files.internal("data/fire.mp3"));
		forest = Gdx.audio.newMusic(Gdx.files.internal("data/forest.mp3"));
		forest.setLooping(true);
		forest.play();
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"), Gdx.files.internal("data/uiskin.png"));
		pauseWin = new Window("Pause", skin.getStyle(WindowStyle.class), "pauseWin");
		pauseWin.x = Gdx.graphics.getWidth() / 4;
		pauseWin.y = Gdx.graphics.getHeight() / 4;
		pauseWin.height = Gdx.graphics.getHeight() / 2;
		pauseWin.width = Gdx.graphics.getWidth() / 2;
		pauseWin.defaults().spaceBottom(10);
		pauseWin.setModal(true);
		pauseWin.row().fill().expandX();
		pauseLabel = new Label("PAUSE", skin.getStyle(LabelStyle.class), "label");
		pauseWin.add(pauseLabel).colspan(4);
		
		//pauseWin.pack();
		
		pauseWin.visible = false;
		
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		font = new BitmapFont(Gdx.files.internal("data/duck.fnt"), false);
		font.setColor(Color.WHITE);
        spriteBatch = new SpriteBatch();                                // #12
        backGrnd = new Texture(Gdx.files.internal("data/lake-view.jpg"));
		duckHuntStg = new HuntStage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, spriteBatch);
		ctrlStg = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, spriteBatch);
		ui = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, spriteBatch);
		
		js = new JoyStick(new Texture(Gdx.files.internal("data/joystick.png")));
		mplexer = new InputMultiplexer();
		cxh = new CrossHair(new Texture(Gdx.files.internal("data/target.png")), js);
		ctrlStg.addActor(cxh);
		ctrlStg.addActor(js);
		ui.addActor(pauseWin);
		
		mplexer.addProcessor(js);
		mplexer.addProcessor(this);
		Gdx.input.setInputProcessor(mplexer);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		forest.stop();
		Gdx.input.setCatchBackKey(false);
		Gdx.input.setCatchMenuKey(false);
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
