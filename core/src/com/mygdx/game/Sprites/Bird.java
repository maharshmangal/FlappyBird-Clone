package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kronos on 28-01-2017.
 */

public class Bird {

    private static final int GRAVITY = -15;
    private static final int MOVEMENT =100;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture bird;
    private Animation birdAnimation;
    private Texture texture;

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public Bird(int x, int y)
    {
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("animation.png");
        birdAnimation=new Animation(new TextureRegion(texture),4 , 0.4f);
        bounds =new Rectangle(x,y,texture.getWidth()/4,texture.getHeight());
    }

    public void update(float dt)
    {
        birdAnimation.update(dt);
        if (position.y>0)
            velocity.add(0,GRAVITY,0);
        velocity.scl(dt);

        position.add(MOVEMENT *dt,velocity.y,0);
        if (position.y<0)
            position.y =0;
        velocity.scl(1/dt);
        bounds.setPosition(position.x,position.y);
    }

    public void jump()
    {
        velocity.y = 250;

    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void dispose()
    {
        texture.dispose();
    }



}
