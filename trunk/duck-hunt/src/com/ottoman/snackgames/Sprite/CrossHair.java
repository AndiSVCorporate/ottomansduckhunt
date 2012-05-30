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
	
    private int scrW = 0;
    private int scrH = 0;
	
	public CrossHair(Texture texture, JoyStick js){
		crosshair = texture;
		joyStk = js;

		scrW = Gdx.graphics.getWidth();
	    scrH = Gdx.graphics.getHeight();
	    if(scrH<400){
			this.width = texture.getWidth()/2;
			this.height = texture.getHeight()/2;
	    }else{
			this.width = texture.getWidth();
			this.height = texture.getHeight();
	    }
		centX = (int)((width / 2) + 10) ;
		centY = (int)((height / 2) + 10) ;
		rad = (int) (width / 2);
		upBound = scrH;loBound = 0; 
		rtBound = scrW;ltBound = 0; 

		centerPosition();
		
	}
	
	public void centerPosition(){
		xPos = (rtBound - ltBound) / 2;
		yPos = (upBound - loBound) / 2;		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		int xNew =  xPos + (int)(5 * joyStk.jX /100);
		int yNew =  yPos + (int)(5 * joyStk.jY /100);
		if(xNew>=ltBound && xNew<=rtBound)xPos = xNew;
		if(yNew>=loBound && yNew<=upBound)yPos = yNew;
		
		batch.draw(crosshair, xPos - rad, yPos - rad, width, height);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
}
