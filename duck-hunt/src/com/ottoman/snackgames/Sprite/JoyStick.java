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
	private int centX;
	private int centY;
	boolean isTouched = false;
	int _x=0;int _y=0;int rad;
	
    BitmapFont font;
    
    public float jX = 0;
    public float jY = 0;

	public JoyStick(Texture texture){
		stick = texture;
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		centX = (int)((width / 2) + 10) ;
		centY = (int)((height / 2) + 10) ;
		rad = (int) (width / 2);
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer) {
		return true;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		_x = x;_y=Gdx.graphics.getHeight() - y;
		
		isTouched =_x > 10 && _x < width +10 && _y > 10 && _y < height +10 ? true : false;
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		isTouched = false;
		_x = x;_y=Gdx.graphics.getHeight() - y;
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		_x = x;_y=Gdx.graphics.getHeight() - y;
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		//_x = x;_y=Gdx.graphics.getHeight() - y;
		return false;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
        batch.draw(stick, 10, 10, width, height);
        if(isTouched){
        	int d = getDistance();
        	int coorX =_x;
        	int coorY =_y;
        	if(d>rad){
        		coorX =getBorderCoorX(d);coorY =getBorderCoorY(d);
        	}
    		batch.draw(stick, coorX - 32, coorY - 32, 64, 64);
            jX = Math.round(((coorX - centX)*100)/rad);
            jY = Math.round(((coorY - centY) * 100)/rad);
            font.draw(batch, "jX = "+jX+" , jY = " + jY + "", 150, 150);
        	//font.draw(batch, "ulaaayn "+d+" = Kök((" + _x + " - " + centX + ")^2+ (" + _y +" - " + centY + ")^2)", 150, 150);
        }else{
        	jX=0;jY=0;
        	batch.draw(stick, centX - 32, centY - 32, 64, 64);
        }
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		isTouched = true;
		return x > 0 && x < width && y > 0 && y < height ? this : null;
	}

	
	private int getDistance(){
		return (int)Math.sqrt((double)((Math.pow((_x - centX), 2)) + Math.pow((_y - centY), 2)));
	}
	
	private int getBorderCoorX(int d){
		return (rad * ((_x - centX))/d) + centX;
	}
	
	private int getBorderCoorY(int d){
		return (rad * ((_y - centY))/d) + centY;
	}
	
}
