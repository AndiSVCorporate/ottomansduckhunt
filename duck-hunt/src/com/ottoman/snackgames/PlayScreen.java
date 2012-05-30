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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.ottoman.snackgames.Scene.HuntStage;
import com.ottoman.snackgames.Sprite.CrossHair;
import com.ottoman.snackgames.Sprite.JoyStick;

public class PlayScreen extends InputAdapter implements Screen {
    //general
    private duckHunt game;
    //graphics
    private int scrW = 0;
    private int scrH = 0;
    private SpriteBatch spriteBatch;
	private Texture backGrnd;
    //stage and inputs
    private HuntStage duckHuntStg ;
    private Stage ctrlStg;
    private Stage ui;
    private InputMultiplexer mplexer;
    //controller actors
    private JoyStick js;
    private CrossHair cxh;
    //indicator section
    BitmapFont font;
    private float timePassed = 0;
    private int gameDuration = 90;
    private float bulletArriveDelay = 0.1f;
    private float reloadDelay = 0.8f;
    private float fireMoment = 0;
    private boolean bulletArrived = true;
    //popups and ui
    private gamePopup pauseWin;
    private gamePopup optionsWin;
    private gamePopup highScoresWin;
    private gamePopup yesNoWin;
    private Skin skin;
    private Label pauseLabel;
    private TextButton resumeBtn;
    private ClickListener restartGame; 
    private ClickListener mainMenu; 
    //audio
    private Sound fire;   
    private Music forest;
    //game state
    private boolean isPaused = false;
    private boolean isTimesUp = false;

    
    public PlayScreen(duckHunt game){
        this.game = game;
    }

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);                                            // #14
		
        spriteBatch.begin();
        spriteBatch.draw(backGrnd, 0, 0, scrW, scrH);
        
		spriteBatch.end();
		
		if(!isTimesUp){
	        if(!isPaused){
	    		timePassed += Gdx.graphics.getDeltaTime();
	            duckHuntStg.draw();
	            ctrlStg.draw();
	          if(js.isBtnFired && fireMoment == 0){
	            //font.draw(spriteBatch, "fire : " + cxh.getXPos() + " - " + cxh.getYPos(), 100, 400);
	        	  fireMoment = timePassed;
	        	  bulletArrived=false;
	        	  if(Settings.soundEnabled) fire.play();
	            //duckHuntStg.fireEvent(cxh.getXPos(), cxh.getYPos());
	            js.isBtnFired = false;
	          }
	          if(timePassed - fireMoment>=bulletArriveDelay && !bulletArrived){
	        	  duckHuntStg.fireEvent(cxh.getXPos(), cxh.getYPos());
	        	  bulletArrived = true;
	          }
	          if(!js.isLoaded){
	        	  if(timePassed - fireMoment>=reloadDelay){
	        		  js.isLoaded = true;
		        	  fireMoment = 0;
	        	  }
	          }
	        }else{
	            ui.draw();
	        }
	        
	        drawScoreAndTimer();
			
	        if((int)timePassed==gameDuration){
				gameEndProcess();
			}
		}else{
			ui.draw();
		}
		//spriteBatch.begin();
		//font.draw(spriteBatch, "timePassed = " + timePassed +" -  fireMoment = " + fireMoment, 0, 32);
		//spriteBatch.end();
	}
	
	private void gameEndProcess(){
		forest.stop();
		pauseLabel.setText("Your Score is " + duckHuntStg.getHitScore());
		Settings.addScore(duckHuntStg.getHitScore());
		Settings.save();
		isTimesUp = true;
		duckHuntStg.isTimesUp = true;
		duckHuntStg.setPause(isPaused);
        pauseWin.visible = true;
        resumeBtn.visible = false;
        if(Settings.soundEnabled)
        	forest.stop();
	}
	
	private void drawScoreAndTimer(){
        int timeElapsed =gameDuration - (int)timePassed;
        int tmpMinute = (int)Math.floor(timeElapsed / 60);
        String stElapsed = (tmpMinute < 10)?("0" + tmpMinute) : tmpMinute + "";
        timeElapsed =(int) (timeElapsed - (tmpMinute * 60));
        stElapsed = stElapsed + ":" + ((timeElapsed<10)?"0"+timeElapsed:timeElapsed+""); 
        spriteBatch.begin();
    	font.setScale(1);
        font.draw(spriteBatch, "Score = " + duckHuntStg.getHitScore(), 5, scrH - 5);
        font.draw(spriteBatch, stElapsed, scrW - font.getBounds(stElapsed).width - 5, scrH - 5);
		spriteBatch.end();

	}
	
	@Override
	public boolean keyUp(int keycode) {
		//Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE){
			pauseOrResume();
		}
		return false;
	};
	
	private void pauseOrResume(){
		pauseLabel.setText("PAUSE");
    	isPaused = !isPaused;
    	duckHuntStg.setPause(isPaused);
        pauseWin.visible = isPaused;
    	resumeBtn.visible = isPaused;
        if(isPaused){
        	forest.pause();
        }else{
        	if(Settings.soundEnabled)
        	forest.play();
        }
	}
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	    scrW = Gdx.graphics.getWidth();
	    scrH = Gdx.graphics.getHeight();
	}
	

	@Override
	public void show() {
		// TODO Auto-generated method stub4
	    scrW = Gdx.graphics.getWidth();
	    scrH = Gdx.graphics.getHeight();
		//background texture declaration
		font = new BitmapFont(Gdx.files.internal("data/duck.fnt"), false);
		font.setColor(Color.WHITE);
        spriteBatch = new SpriteBatch();                                // #12
        backGrnd = new Texture(Gdx.files.internal("data/lake-view.jpg"));

		//sound and audio declarations
		fire = Gdx.audio.newSound(Gdx.files.internal("data/fire.mp3"));
		forest = Gdx.audio.newMusic(Gdx.files.internal("data/forest.mp3"));
		forest.setLooping(true);
		if(Settings.soundEnabled)
			forest.play();

		//Stage declarations
        duckHuntStg = new HuntStage(scrW, scrH, true, spriteBatch);
		ctrlStg = new Stage(scrW, scrH, true, spriteBatch);
		ui = new Stage(scrW, scrH, true, spriteBatch);
		
		//input multiplexer
		mplexer = new InputMultiplexer();
		
		//controller actors
		js = new JoyStick(new Texture(Gdx.files.internal("data/joystick.png")));
		cxh = new CrossHair(new Texture(Gdx.files.internal("data/target.png")), js);
		ctrlStg.addActor(cxh);
		ctrlStg.addActor(js);
		
		//window declarations
		preparePauseMenu();
		
		//set input processors
		Gdx.input.setCatchBackKey(true);
		mplexer.addProcessor(js);
		mplexer.addProcessor(this);
		mplexer.addProcessor(ui);
		Gdx.input.setInputProcessor(mplexer);
	}
	

	private void preparePauseMenu(){
		skin = new Skin(Gdx.files.internal("data/uiskin.json"), Gdx.files.internal("data/uiskin.png"));
		
		pauseWin = new gamePopup(skin);
		//("Pause", skin.getStyle(WindowStyle.class), "pauseWin");
		pauseWin.makeDialogWin("Pause", true, false);

		pauseWin.row().fill().expandX();
		pauseLabel = new Label("PAUSE", skin.getStyle(LabelStyle.class), "label");
		pauseWin.add(pauseLabel).colspan(4);
		pauseWin.visible = false;

		resumeBtn = pauseWin.addMenuButton("Resume Game");
		resumeBtn.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				pauseOrResume(); 
			}
	    });

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
