package com.ottoman.snackgames;


import java.awt.Checkbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;

public class MainMenuScreen implements Screen {
    SpriteBatch                     spriteBatch;            // #6
    //TextButton actNewBtn;
    gamePopup menuWin;
    gamePopup optionsWin;
    gamePopup highScoresWin;
    gamePopup yesNoWin;
    Stage menuStage ;
    BitmapFont font;
    Skin skin;
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
		Gdx.input.setCatchBackKey(true);
		spriteBatch = new SpriteBatch();                                // #12
		skin = new Skin(Gdx.files.internal("data/uiskin.json"), Gdx.files.internal("data/uiskin.png"));
		menuWin = new gamePopup(skin);
		menuWin.makeDialogWin("Wellcome to Out of Season Duck Hunt", false, false);
		menuWin.addMenuButton("New Hunt").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				game.setScreen(new PlayScreen(game));  
			}
	    });
		
		

		menuWin.addMenuButton("Options").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				optionsWin.showOptionWindow();  
			}
	    });

		menuWin.addMenuButton("High Scores").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				highScoresWin.showHighScoresWindow();
			}
	    });

		menuWin.addMenuButton("Get Out").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				menuWin.visible = false;
				yesNoWin.visible = true;
				//Gdx.app.exit();  
			}
	    });
		//optionWin
		optionsWin =new gamePopup(skin);
		optionsWin.optionWindow(menuWin);
				
		highScoresWin = new gamePopup(skin);
		highScoresWin.highScoresWindow(menuWin);
		

		yesNoWin = new gamePopup(skin);
		yesNoWin.yesNoDialog(menuWin, "Hey!", "Are you sure to wanna exit the game hunter?", "Yeah! Gotto go!", "Nope! Shoot More Ducks!");
		yesNoWin.visible = false;
		
		yesNoWin.getNoBtn().setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				yesNoWin.visible = false;
				menuWin.visible = true;
				//Gdx.app.exit();  
			}
	    });
		yesNoWin.getYesBtn().setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				Gdx.app.exit();  
			}
	    });
		
		
		menuStage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, spriteBatch);
		menuStage.addActor(yesNoWin);
		menuStage.addActor(optionsWin);
		menuStage.addActor(highScoresWin);
		menuStage.addActor(menuWin);

		Gdx.input.setInputProcessor(menuStage);
		
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
		if(menuStage!=null)
		menuStage.dispose();
	}

}
