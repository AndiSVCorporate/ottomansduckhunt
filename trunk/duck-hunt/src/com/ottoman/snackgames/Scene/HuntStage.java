package com.ottoman.snackgames.Scene;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ottoman.snackgames.Settings;
import com.ottoman.snackgames.Sprite.HuntActor;


public class HuntStage extends Stage {
	private ArrayList<HuntActor> ducks = new ArrayList<HuntActor>();
	private float stateTime=0;
	private boolean justCreated = false; 
	private int hitScore = 0;
    private boolean isPaused = false;	
    public boolean isTimesUp = false;	
    Sound quack = Gdx.audio.newSound(Gdx.files.internal("data/quack.mp3"));

	public HuntStage(float width, float height, boolean stretch, SpriteBatch batch) {
		super(width, height, stretch, batch);
		// TODO Auto-generated constructor stub
	}
	
	public void fireEvent(float bX, float bY){
		if(this.getActors().size()>0 && !isPaused){
		//for(Actor act : this.getActors()){
  			//HuntActor duck = (HuntActor)act;
		for(HuntActor duck : ducks){
			if(duck.getStatus()=="fly")
				if(duck.checkHit(bX, bY)){
					hitScore++;
  					break;
  				}
  	  		}
		}
	}
	
	public int getHitScore(){
		return hitScore;
	}
	
	private void addDuck(String direction, String distance){
		float yPos = 0;
		float dims = 128;
        Random r=new Random();
		if(distance == "far"){
			yPos = 400 + (5 * r.nextInt(13));
			dims = 64;
		}else if(distance == "medium"){
			yPos = 300 + (7 * r.nextInt(13));
			dims = 96;
		}else if(distance == "close"){
			yPos = 190 + (10 * r.nextInt(13));
			dims = 128;
		}
		HuntActor duck  = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, dims, dims);
		if(direction == "right"){
			duck.x = -dims;
			duck.flip = true;
		}else{
			duck.x = Gdx.graphics.getWidth();
			duck.flip = false;
		}
		duck.y = yPos;
		ducks.add(duck);
		this.addActor(ducks.get(ducks.size() - 1));
		
	}
	
	public void setPause(boolean setVal){
		isPaused = setVal;
	}
	
	private void addRandomDucks(){
		Random r=new Random();
		int duckCnt = 3 + r.nextInt(5);
		for(int i=0;i<duckCnt;i++){
			String direction = r.nextBoolean()?"right":"left";
			int tmpVal = r.nextInt(3);
			String distance = (tmpVal==0)?"close":((tmpVal==1)?"far":"medium");
			addDuck(direction, distance);
		}
		if(Settings.soundEnabled)
		quack.play();
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		if(!isTimesUp){
			stateTime +=  Gdx.graphics.getDeltaTime();
			if(!isPaused){
				if(stateTime % 8 < 1 && !justCreated){
					addRandomDucks();
					justCreated = true;
				}else if(stateTime % 8 >= 1){
					justCreated = false;
				}
				
				if(this.getActors().size()>0){
			//for(Actor act : this.getActors()){
				//HuntActor duck = (HuntActor)act;
					for(HuntActor duck : ducks){
						if(duck.getStatus()=="fly"){
							if(!duck.updatePos()){
								this.removeActor(duck);
								duck.remove();
							}
						}else{
							duck.y--;
							if(duck.y<=0){
								this.removeActor(duck);
								duck.remove();
							}
						}
					}
				}
			}
		}
		super.draw();
	}
	
	public void restartGame(){
		for(HuntActor duck : ducks){
			this.removeActor(duck);
			duck.remove();
		}
		isTimesUp = false;
		setPause(false);
		stateTime = 0;
		hitScore = 0;
	}
}
