package com.ottoman.snackgames.Sprite;

import javax.xml.transform.stax.StAXSource;

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
	public boolean isBtnFired = false;
	public boolean isLoaded = true;
	int _x=0;int _y=0;int rad;

	
	private BitmapFont font;
    
    public float jX = 0;
    public float jY = 0;

    private int offSet=32;
    private int pointindx=-1;

    private int scrW = 0;
    private int scrH = 0;
    
	public JoyStick(Texture texture){
		stick = texture;
		font = new BitmapFont();
		font.setColor(Color.WHITE);

		scrW = Gdx.graphics.getWidth();
	    scrH = Gdx.graphics.getHeight();
	    if(scrH<400){
			this.width = 2 * texture.getWidth() / 3;
			this.height = 2 * texture.getHeight() / 3;
	    }else{
			this.width = texture.getWidth();
			this.height = texture.getHeight();
	    }
		
		centX = (int)((width / 2) + offSet) ;
		centY = (int)((height / 2) + offSet) ;
		rad = (int) (width / 2);
		this.x = offSet;
		this.y = offSet;
	}
	
	
	@Override
	public boolean touchDown(float x, float y, int pointer) {
		return true;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		if(!isTouched){
		  _x = x;_y= scrH - y;
		  pointindx = pointer;
		  isTouched =_x > offSet && _x < width +offSet && _y > offSet && _y < height +offSet ? true : false;
		}
		if(!isBtnFired && isLoaded){
			int t_x = x;int t_y= scrH - y;
			isBtnFired = t_x > scrW - offSet - width / 2 && t_x < scrW - offSet && t_y > offSet && t_y < height / 2 + offSet ? true : false;
			isLoaded = !isBtnFired; 
		}
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		isBtnFired = false;
		if(isTouched && pointindx == pointer){
		  isTouched = false;
		  _x = x;_y = scrH - y;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		if(isTouched && pointindx == pointer){
		  _x = x;_y = scrH - y;
		}
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

        batch.draw(stick, offSet, offSet, width, height);
        batch.draw(stick, scrW - offSet - width / 2, offSet, width / 2, height / 2);
        if(isTouched){
        	int d = getDistance();
        	int coorX =_x;
        	int coorY =_y;
        	if(d>rad){
        		coorX =getBorderCoorX(d);coorY =getBorderCoorY(d);
        	}
    		batch.draw(stick, coorX - (width / 4), coorY - (height / 4), width / 2 , height / 2);
            jX = Math.round(((coorX - centX)*100)/rad);
            jY = Math.round(((coorY - centY) * 100)/rad);
        }else{
        	jX=0;jY=0;
        	batch.draw(stick, centX - (width / 4), centY - (height / 4), width / 2 , height / 2);
        }
        //font.draw(batch, "isloaded = "+ isLoaded +" fireStateTime = " + fireStateTime +" fireStateTime = " + fireStateTime, 150, 150);
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
