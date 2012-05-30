package com.ottoman.snackgames.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HuntActor extends Actor {
	private Animation walkAnimation;          // #3
	private TextureRegion[] walkFrames;             // #5
    private TextureRegion currentFrame;           // #7
    private float stateTime;
    private boolean looping;
    private String _status;
    public boolean flip = true;
    private Rectangle[] duckRect = new Rectangle[2];    
    
    //ShapeRenderer shape = new ShapeRenderer();
    public HuntActor(Texture texture, int rows, int cols, float frameDuration, boolean looping)
    {
 
        int tileWidth = texture.getWidth() / cols;
        int tileHeight = texture.getHeight() / rows;
        contructCode(texture, rows, cols, frameDuration, looping, tileWidth, tileHeight);
        _status = "fly";
    }
    
    public HuntActor(Texture texture, int rows, int cols, float frameDuration, boolean looping, float iWidth, float iHeight)
    {
        contructCode(texture, rows, cols, frameDuration, looping, iWidth, iHeight);
        _status = "fly"; 
    }
    
    private void contructCode(Texture texture, int rows, int cols, float frameDuration, boolean looping, float iWidth, float iHeight){
this.looping = looping;
 
        int tileWidth = texture.getWidth() / cols;
        int tileHeight = texture.getHeight() / rows;
        TextureRegion[][] tmp = TextureRegion.split(texture, tileWidth, tileHeight);
        walkFrames = new TextureRegion[cols * rows];
 
       
        int index = 0;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
            	//if(flip) {tmp[i][j].flip(true, false);}else{tmp[i][j].flip(false, false);}
            	walkFrames[index++] = tmp[i][j];
            }
        }
 
        width = iWidth;
        height = iHeight;
        
        walkAnimation = new Animation(frameDuration, walkFrames);
        stateTime = 0f;    	
    }
    
    /**
     * Reset animation.
     *
     * You can use this to ensure the animation plays from the start again. It's
     * handy if you have one-shot animations like explosions but you are using
     * re-usable Sprites. You must reset the animation to ensure the animation
     * plays back again.
     */
    public void resetAnimation()
    {
        stateTime = 0;
    }
    /**
     * Check to see if animation finished.
     *
     * @param stateTime
     *
     * @return True if finished.
     */
    public boolean isAnimationFinished()
    {
        return walkAnimation.isAnimationFinished(stateTime);
    }
 
    public boolean checkHit(float bX, float bY){
    	//Circle cir = new Circle(new Vector2(this.x + (width / 2), this.y + (height / 2)), width/2);
    	//duckRect[0] =  new Rectangle(this.x, this.y + (height / 2), width / 2, height/4);
    	//duckRect[1] =  new Rectangle(this.x + (width / 2), this.y + (height / 4), width / 2, height/2);
    	if(duckRect[0].contains(bX, bY) || duckRect[1].contains(bX, bY)){
    	//if(bX>x && bX<x+width && bY>y && bY< y + height){
    		_status = "hit";
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public String getStatus(){
    	return _status;
    }
    
    public boolean updatePos(){
    	if(this.flip){
    		this.x++;
    		if(this.x>Gdx.graphics.getWidth()){
				//this.x = -this.width;
    			return false;
			}
    	}else{
    		this.x--;
    		if(this.x<-this.width){
				//this.x = Gdx.graphics.getWidth();
    			return false;
			}
    	}
    	return true;
    }
    
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		if(!flip){
			duckRect[0] =  new Rectangle(this.x, this.y + (height / 2), width / 3, height/4);
    		duckRect[1] =  new Rectangle(this.x + (width / 3), this.y + (height / 3), 2 * (width / 3), height/3);
		}else{
			duckRect[0] =  new Rectangle(this.x, this.y + (height / 3), (2 * width) / 3, (height) / 3);
	    	duckRect[1] =  new Rectangle(this.x + (2 * (width / 3)), this.y + (height / 2), width / 3, height/4);
		}
		if(_status == "fly"){
		  stateTime += Gdx.graphics.getDeltaTime();
		  currentFrame = walkAnimation.getKeyFrame(stateTime, this.looping);
		}
		TextureRegion tmpTr = new TextureRegion(currentFrame); 
		if(flip)
			tmpTr.flip(true, false);
		batch.draw(tmpTr, x, y, width, height);
		/*
		shape.begin(ShapeType.Rectangle);
		shape.rect(duckRect[0].x, duckRect[0].y, duckRect[0].width, duckRect[0].height);
		shape.rect(duckRect[1].x, duckRect[1].y, duckRect[1].width, duckRect[1].height);
		shape.end();
		*/
	}

	@Override
	public Actor hit(float x, float y) {
		return x > 0 && x < width && y > 0 && y < height ? this : null;
	}

}
