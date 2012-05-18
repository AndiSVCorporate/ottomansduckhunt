package com.ottoman.snackgames.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class JoyStick extends Actor {

	private Texture stick;
	boolean isTouched = false;
	float _x=0;float _y=0;
    BitmapFont font;

	public JoyStick(Texture texture){
		stick = texture;
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		this.touchable = true;
	}
	
	@Override
    public boolean touchDown(float x, float y, int pointer) {
		isTouched = true;_x = x;_y=y;
        return super.touchDown(x, y, pointer);
    }
	
	@Override 
	public void touchUp(float x, float y, int pointer) {
		isTouched = false;_x = x;_y=y;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
        batch.draw(stick, 10, 10);
        if(isTouched)
        batch.draw(stick, _x - 32, (Gdx.graphics.getHeight() - _y) - 32, 64, 64);
        font.draw(batch, "ulaaayn - :'( : " + _x + " : " + _y, 150, 150);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

}
