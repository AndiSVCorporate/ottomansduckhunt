package com.ottoman.duckHunt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HuntActor extends Actor {
	Animation                       walkAnimation;          // #3
    TextureRegion[]                 walkFrames;             // #5
    TextureRegion                   currentFrame;           // #7
    private float stateTime;
    private boolean looping;
    public HuntActor(Texture texture, int rows, int cols, float frameDuration, boolean looping)
    {
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
            	walkFrames[index++] = tmp[i][j];
            }
        }
 
        width = tileWidth;
        height = tileHeight;
 
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
 
    
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = walkAnimation.getKeyFrame(stateTime, this.looping);
		
		batch.draw(currentFrame, x, y);
	}

	@Override
	public Actor hit(float x, float y) {
		return x > 0 && x < width && y > 0 && y < height ? this : null;
	}

}
