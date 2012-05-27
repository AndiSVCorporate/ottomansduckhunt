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
    Window menuWin;
    Window optionsWin;
    Window highScoresWin;
    Window yesNoWin;
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
		menuWin = makeDialogWin("Wellcome to Out of Season Duck Hunt", false, false);
		addMenuItem(menuWin, "New Hunt").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				game.setScreen(new PlayScreen(game));  
			}
	    });

		addMenuItem(menuWin, "Options").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				menuWin.visible = false;
				CheckBox chk = (CheckBox)optionsWin.getWidget("chkSound");
				chk.setChecked(Settings.soundEnabled);
				optionsWin.visible = true;  
			}
	    });

		addMenuItem(menuWin, "High Scores").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				for(int i=0;i<Settings.highscores.length;i++){
					((Label)highScoresWin.getWidget("highScore_" + i)).setText((i + 1) + " - " +Settings.highscores[i]);
				}
				menuWin.visible = false;
				highScoresWin.visible = true;
			}
	    });

		addMenuItem(menuWin, "Get Out").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				menuWin.visible = false;
				yesNoWin.visible = true;
				//Gdx.app.exit();  
			}
	    });

		optionsWin = makeDialogWin("Option Stuff", true, false);
		optionsWin.visible = false;
		optionsWin.row().fill().expandX();

        CheckBox chk = new CheckBox("Sound Enabled", skin.getStyle(CheckBoxStyle.class), "chkSound");
        optionsWin.row().fill().expandX();
        optionsWin.add(chk).colspan(4);
        

		addMenuItem(optionsWin, "Save").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				CheckBox chk = (CheckBox)optionsWin.getWidget("chkSound");
				Settings.soundEnabled = chk.isChecked();
				Settings.save();
				optionsWin.visible = false;  
				menuWin.visible = true;
			}
	    });
		
		addMenuItem(optionsWin, "< Turn Back").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				optionsWin.visible = false;
				menuWin.visible = true;
				//Gdx.app.exit();  
			}
	    });
		
		
		highScoresWin = makeDialogWin("High Scores", true, false);
		for(int i=0;i<Settings.highscores.length;i++){
			addMenuLabel(highScoresWin, "", "highScore_" + i);
		}
		
		highScoresWin.visible = false;
		addMenuItem(highScoresWin, "< Turn Back").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				highScoresWin.visible = false;
				menuWin.visible = true;
				//Gdx.app.exit();  
			}
	    });
		

	
		yesNoWin = makeDialogWin("Hey!", true, false);
		yesNoWin.visible = false;
		addMenuLabel(yesNoWin, "Are you sure to wanna exit the game hunter?", "yesNoLabel");
		yesNoWin.row().fill().expandX();
		TextButton NewBtn1 = new TextButton(skin);
		NewBtn1.setStyle(skin.getStyle(TextButtonStyle.class));
		NewBtn1.setText("Nope! Shoot More Ducks!");
		NewBtn1.pack();
		yesNoWin.add(NewBtn1).colspan(2);
		
		TextButton NewBtn2 = new TextButton(skin);
		NewBtn2.setStyle(skin.getStyle(TextButtonStyle.class));
		NewBtn2.setText("Yeah! Gotto go!");
		NewBtn2.pack();
		yesNoWin.add(NewBtn2).colspan(2);
		NewBtn1.setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				yesNoWin.visible = false;
				menuWin.visible = true;
				//Gdx.app.exit();  
			}
	    });
		NewBtn2.setClickListener(new ClickListener() {
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

	private TextButton addMenuItem(Window win, String ButtonText){
		TextButton NewBtn = new TextButton(skin);
		NewBtn.setStyle(skin.getStyle(TextButtonStyle.class));
		NewBtn.setText(ButtonText);
		NewBtn.pack();
		win.row().fill().expandX();
		win.add(NewBtn).colspan(4);
		return NewBtn;
	}
	
	private void addMenuLabel(Window win, String LabelText, String LabelName){
		Label lbl = new Label(LabelText, skin.getStyle(LabelStyle.class), LabelName);
		win.row().fill().expandX();
		win.add(lbl).colspan(4);
	}
	
	private Window makeDialogWin(String sHeader, boolean isModal, boolean isMovable){
		Window Win = new Window(sHeader, skin.getStyle(WindowStyle.class), "menuWin");
		Win.x = Gdx.graphics.getWidth() / 4;
		Win.y = Gdx.graphics.getHeight() / 4;
		Win.height = Gdx.graphics.getHeight() / 2;
		Win.width = Gdx.graphics.getWidth() / 2;
		Win.defaults().spaceBottom(10);
		Win.setModal(isModal);
		Win.setMovable(isMovable);
		return Win;
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
