package com.ottoman.snackgames.Scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.ottoman.snackgames.Sprite.HuntActor;


public class HuntStage extends Stage {
    HuntActor flyDuck1;HuntActor flyDuck2;HuntActor flyDuck3; 

	public HuntStage(float width, float height, boolean stretch,
			SpriteBatch batch) {
		super(width, height, stretch, batch);
		// TODO Auto-generated constructor stub
		flyDuck1 = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, 64, 64);
		flyDuck1.x = 0;
		flyDuck1.y = 150;
		this.addActor(flyDuck1);

		flyDuck2 = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, 48, 48);
		flyDuck2.x = 0;
		flyDuck2.y = 200;
		this.addActor(flyDuck2);

		flyDuck3 = new HuntActor(new Texture(Gdx.files.internal("data/duck-fly.png")), 4, 3, 0.025f, true, 32, 32);
		flyDuck3.x = 0;
		flyDuck3.y = 250;
		this.addActor(flyDuck3);

	}
	
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		for(Actor act : this.getActors()){
			act.x++;
			if(act.x>Gdx.graphics.getWidth()){
				act.x = -act.width;
			}
		}
		super.draw();
	}
}
