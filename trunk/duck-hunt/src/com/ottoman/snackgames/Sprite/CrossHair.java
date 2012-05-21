package com.ottoman.snackgames.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CrossHair extends Actor {
    private Texture crosshair;
	private JoyStick joyStk;
	private int centX;
	private int centY;
	private int rad;

	private int xPos;
	private int yPos;
	public int upBound;public int loBound; 
	public int rtBound;public int ltBound; 
	
	
	public CrossHair(Texture texture, JoyStick js){
		crosshair = texture;
		joyStk = js;
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		centX = (int)((width / 2) + 10) ;
		centY = (int)((height / 2) + 10) ;
		rad = (int) (width / 2);
		upBound = Gdx.graphics.getHeight();loBound = 0; 
		rtBound = Gdx.graphics.getWidth();ltBound = 0; 

		xPos = (upBound - loBound) / 2;
		yPos = (rtBound - ltBound) / 2;
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		int xNew =  xPos + (int)(5 * joyStk.jX /100);
		int yNew =  yPos + (int)(5 * joyStk.jY /100);
		if(xNew>=ltBound && xNew<=rtBound)xPos = xNew;
		if(yNew>=loBound && yNew<=upBound)yPos = yNew;
		
		batch.draw(crosshair, xPos - rad, yPos - rad);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

}
