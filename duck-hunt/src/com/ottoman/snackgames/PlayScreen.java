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
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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
    private boolean isTimesUp = false;
    gamePopup pauseWin;
    gamePopup optionsWin;
    gamePopup highScoresWin;
    gamePopup yesNoWin;
    Skin skin;
    float timePassed = 0;
    int gameDuration = 90;
    Label pauseLabel;

    Sound fire;   Music forest;
    ClickListener restartGame; 
    ClickListener mainMenu; 
    
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
		
		if(!isTimesUp){
	        if(!isPaused){
	    		timePassed += Gdx.graphics.getDeltaTime();
	            duckHuntStg.draw();
	            ctrlStg.draw();
	          if(js.isBtnFired){
	            //font.draw(spriteBatch, "fire : " + cxh.getXPos() + " - " + cxh.getYPos(), 100, 400);
	        	  if(Settings.soundEnabled) fire.play();
	            duckHuntStg.fireEvent(cxh.getXPos(), cxh.getYPos());
	            js.isBtnFired = false;
	          }
	        }else{
	            ui.draw();
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
				isTimesUp = true;
				duckHuntStg.isTimesUp = true;
				duckHuntStg.setPause(isPaused);
	            pauseWin.visible = true;
	            if(Settings.soundEnabled)
	            	forest.stop();
			}
		}else{
			ui.draw();
		}
	}
	
	@Override
	public boolean keyUp(int keycode) {
		//Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE){
			pauseLabel.setText("PAUSE");
        	isPaused = !isPaused;
        	duckHuntStg.setPause(isPaused);
            pauseWin.visible = isPaused;
            if(isPaused){
            	forest.pause();
            }else{
            	if(Settings.soundEnabled)
            	forest.play();
            }
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
		if(Settings.soundEnabled)
		forest.play();
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"), Gdx.files.internal("data/uiskin.png"));
		
		
		Gdx.input.setCatchBackKey(true);

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
		preparePauseMenu();
		mplexer.addProcessor(js);
		mplexer.addProcessor(this);
		mplexer.addProcessor(ui);
		Gdx.input.setInputProcessor(mplexer);
	}

	private void preparePauseMenu(){
		pauseWin = new gamePopup(skin);
		//("Pause", skin.getStyle(WindowStyle.class), "pauseWin");
		pauseWin.makeDialogWin("Pause", true, false);

		pauseWin.row().fill().expandX();
		pauseLabel = new Label("PAUSE", skin.getStyle(LabelStyle.class), "label");
		pauseWin.add(pauseLabel).colspan(4);
		pauseWin.visible = false;

		pauseWin.addMenuButton("Restart Hunt").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				//game.setScreen(new PlayScreen(game));
				pauseWin.visible = false;
				if(isTimesUp){
					restartTheGame();
				}else{
					yesNoWin.getYesNoLabel().setText("Start all over? Is this what you want?");
					yesNoWin.getYesBtn().setText("Yeah! Sure!");
					yesNoWin.getNoBtn().setText("Na! Why i'm here?!");
					yesNoWin.visible = true;
					yesNoWin.getYesBtn().setClickListener(restartGame);
				}
			}
	    });

		pauseWin.addMenuButton("Options").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				optionsWin.showOptionWindow();  
			}
	    });

		pauseWin.addMenuButton( "High Scores").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				highScoresWin.showHighScoresWindow();
			}
	    });

		pauseWin.addMenuButton("Go Back Menu").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				pauseWin.visible = false;
				yesNoWin.getYesNoLabel().setText("Where you goin'?!");
				yesNoWin.getYesBtn().setText("Gotto go! I am tired! :/");
				yesNoWin.getNoBtn().setText("Oops! My bad! :)");
				yesNoWin.visible = true;
				yesNoWin.getYesBtn().setClickListener(mainMenu);
				//Gdx.app.exit();  
			}
	    });
		
		
		optionsWin =new gamePopup(skin);
		optionsWin.optionWindow(pauseWin);
				
		highScoresWin = new gamePopup(skin);
		highScoresWin.highScoresWindow(pauseWin);
		

		yesNoWin = new gamePopup(skin);
		yesNoWin.yesNoDialog(pauseWin, "Hey!", "Where you goin'?!", "Gotto go! I am tired! :/", "Oops! My bad! :)");
		yesNoWin.visible = false;
		
		yesNoWin.getNoBtn().setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				yesNoWin.visible = false;
				pauseWin.visible = true;
				//Gdx.app.exit();  
			}
	    });
		
		restartGame =new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				restartTheGame();
			}
	    };
	    
	    mainMenu=new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				game.setScreen(game.mainMenu);
			}
	    };
		
		
		ui.addActor(pauseWin);
		ui.addActor(optionsWin);
		ui.addActor(highScoresWin);
		ui.addActor(yesNoWin);
	}
	
	private void restartTheGame(){
		duckHuntStg.restartGame();
		yesNoWin.visible = false;
		pauseWin.visible = false;
		isPaused = false;
		isTimesUp = false;
		timePassed = 0;
		cxh.centerPosition();
        if(Settings.soundEnabled)
        	forest.play();
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		forest.stop();
		Gdx.input.setCatchBackKey(false);
		//Gdx.input.setCatchMenuKey(false);
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
