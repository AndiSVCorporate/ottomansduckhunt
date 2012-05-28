package com.ottoman.snackgames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;

public class gamePopup extends Window {
	
	private Window _previous;
	private Skin popSkin;
	private TextButton BtnYes;
	private TextButton BtnNo;
	private Label YesNoLabel;


	public gamePopup(String title, Skin skin, String name) {
		super(title, skin.getStyle(WindowStyle.class), name);
		// TODO Auto-generated constructor stub
		popSkin = skin;
	}

	
	public gamePopup(Skin skin) {
		super(skin);
		// TODO Auto-generated constructor stub
		popSkin = skin;
	}

	public TextButton getYesBtn(){
		return BtnYes;	
	}
	
	public TextButton getNoBtn(){
		return BtnNo;	
	}
	
	public Label getYesNoLabel(){
		return YesNoLabel;
	}
	
	public Skin getSkin(){
		return popSkin;
	}
	
	public void optionWindow(Window previousWin){
		_previous = previousWin;
		this.makeDialogWin("Option Stuff", true, false);
		this.visible = false;
		this.row().fill().expandX();

        CheckBox chk = new CheckBox("Sound Enabled", popSkin.getStyle(CheckBoxStyle.class), "chkSound");
        this.row().fill().expandX();
        this.add(chk).colspan(4);
        

		addMenuButton("Save").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				Window optionsWin = ((Window)actor.parent); 
				CheckBox chk = (CheckBox)optionsWin.getWidget("chkSound");
				Settings.soundEnabled = chk.isChecked();
				Settings.save();
				optionsWin.visible = false;  
				_previous.visible = true;
			}
	    });
		
		addMenuButton("< Turn Back").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				((Window)actor.parent).visible = false;
				_previous.visible = true;
				//Gdx.app.exit();  
			}
	    });
		

	}
	
	public void showOptionWindow(){
		_previous.visible = false;
		CheckBox chk = (CheckBox)this.getWidget("chkSound");
		chk.setChecked(Settings.soundEnabled);
		this.visible = true;  

	}
	
	
	public void highScoresWindow(Window previousWin){
		_previous = previousWin;
		this.makeDialogWin("High Scores", true, false);
		for(int i=0;i<Settings.highscores.length;i++){
			addMenuLabel( "", "highScore_" + i);
		}
		
		this.visible = false;
		addMenuButton("< Turn Back").setClickListener(new ClickListener() {
			@Override
			public void click(Actor actor, float x, float y) {
				((Window)actor.parent).visible = false;
				_previous.visible = true;
				//Gdx.app.exit();  
			}
	    });
	}	

	public void showHighScoresWindow(){
		for(int i=0;i<Settings.highscores.length;i++){
			((Label)this.getWidget("highScore_" + i)).setText((i + 1) + " - " +Settings.highscores[i]);
		}
		_previous.visible = false;
		this.visible = true;
	}
	

	public void yesNoDialog(Window previousWin, String title, String stBody, String yesText, String noText){
		_previous = previousWin;
		this.makeDialogWin(title, true, false);
        this.visible = false;
        YesNoLabel = this.addMenuLabel(stBody, "yesNoLabel");
		this.row().fill().expandX();
		BtnNo = new TextButton(noText, popSkin.getStyle(TextButtonStyle.class));
		BtnNo.pack();
		this.add(BtnNo).colspan(2);
		
		BtnYes = new TextButton(yesText, popSkin.getStyle(TextButtonStyle.class));
		BtnYes.pack();
		this.add(BtnYes).colspan(2);
		
	}
	
	
	public TextButton addMenuButton(String ButtonText){
		TextButton NewBtn = new TextButton(popSkin);
		NewBtn.setStyle(popSkin.getStyle(TextButtonStyle.class));
		NewBtn.setText(ButtonText);
		NewBtn.pack();
		this.row().fill().expandX();
		this.add(NewBtn).colspan(4);

		return NewBtn;
	}
	
	public void makeDialogWin(String sHeader, boolean isModal, boolean isMovable){
		this.setTitle(sHeader);
		this.x = Gdx.graphics.getWidth() / 4;
		this.y = Gdx.graphics.getHeight() / 4;
		this.height = Gdx.graphics.getHeight() / 2;
		this.width = Gdx.graphics.getWidth() / 2;
		this.defaults().spaceBottom(10);
		this.setModal(isModal);
		this.setMovable(isMovable);
	}
	
	
	public Label addMenuLabel(String LabelText, String LabelName){
		Label lbl = new Label(LabelText, popSkin.getStyle(LabelStyle.class), LabelName);
		this.row().fill().expandX();
		this.add(lbl).colspan(4);
		return lbl;
	}

	
	
}
