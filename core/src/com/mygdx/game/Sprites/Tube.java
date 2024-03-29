package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Kronos on 28-01-2017.
 */

public class Tube {
    public static final int TUBE_WIDTH =52;
    private static final int FLUCTUATION= 150;
    private static final int TUBE_GAP=80;
    private static final int LOWEST_OPENING= 120;
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBottomTube;
    private Rectangle boundsTop , boundsBottom;
    private Random rand;

    public Tube(float x)
    {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();
        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube = new Vector2(x, posTopTube.y -TUBE_GAP - bottomTube.getHeight());
        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(),topTube.getHeight()-4);
        boundsBottom = new Rectangle(posBottomTube.x, posBottomTube.y,bottomTube.getWidth(),bottomTube.getHeight()-4);


    }


    public Texture getTopTube() {return topTube; }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x,posTopTube.y);
        boundsBottom.setPosition(posBottomTube.x, posBottomTube.y);
    }

    public boolean collides(Rectangle player)
    {
        return player.overlaps(boundsTop)|| player.overlaps(boundsBottom);

    }

    public void dispose()
    {
        topTube.dispose();
        bottomTube.dispose();
    }



}
