package com.ottoman.snackgames.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class JoyStick extends Actor implements InputProcessor {

	private Texture stick;
	boolean isTouched = false;
	int _x=0;int _y=0;
    BitmapFont font;

	public JoyStick(Texture texture){
		stick = texture;
		font = new BitmapFont();
		font.setColor(Color.WHITE);
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		isTouched = true;
		_x = x;_y=y;
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		isTouched = false;
		_x = x;_y=y;
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		_x = x;_y=y;
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		_x = x;_y=y;
		return false;
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
