package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GrumpyDemo;
import com.mygdx.game.Sprites.Bird;
import com.mygdx.game.Sprites.Tube;

/**
 * Created by Kronos on 28-01-2017.
 */

public class PlayState extends State {
    private static final int TUBE_SPACING = 75;
    private static final int TUBE_COUNT = 4;


    private Bird bird;
    private Texture actualGamebg;
    private Tube tube ;
    private Texture ground;
    private Vector2 groundPos1,groundPos2;
    private static final int HIGHEST_GROUND_LIMIT = -30;
    private Array<Tube> tubes;
    private int k;
    long startTime=0;
    private Music mainMusic;
    private Music scoreIncrease;
    private Music wingFlap;
    public BitmapFont font24;
    public String SCORE;
    public int l;
    public long gameOverStart=0;






    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(0,300);
        actualGamebg = new Texture("bg.png");
        cam.setToOrtho(false, GrumpyDemo.WIDTH/2,GrumpyDemo.HEIGHT/2);

        tubes =new Array<Tube>();
        ground = new Texture("ground.png");
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("mainmusic.mp3"));
        scoreIncrease = Gdx.audio.newMusic(Gdx.files.internal("smw_coin.ogg"));
        wingFlap = Gdx.audio.newMusic(Gdx.files.internal("sfx_wing.ogg"));

        font24= new BitmapFont();
        SCORE = new String();
        fontGenerator();
        groundPos1 = new Vector2(cam.position.x -cam.viewportWidth/2, HIGHEST_GROUND_LIMIT);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth/2) + ground.getWidth(),HIGHEST_GROUND_LIMIT);
        startTime = TimeUtils.nanoTime();
        gameOverStart = TimeUtils.millis();

        for(int i=1 ; i<=TUBE_COUNT; i++)
        {

            tubes.add(new Tube(i* (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
        mainMusic.play();
        mainMusic.setVolume(0.8f);
        mainMusic.setLooping(true);

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            bird.jump();
        wingFlap.setLooping(false);
        wingFlap.play();
        wingFlap.setVolume(0.1f);
    }




    @Override
    public void update(float dt) {
        handleInput();

        updateGround();

        bird.update(dt);
        if (TimeUtils.timeSinceNanos(startTime) > 1400000000)
        {
            Score();
            startTime = TimeUtils.nanoTime();
        }



        SCORE = String.valueOf(k);





        for(int i =0 ; i< tubes.size;i++)
        {
            Tube tube= tubes.get(i);

            if (cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x + tube.getTopTube().getWidth())
            {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) *TUBE_COUNT));
            }
            if(tube.collides(bird.getBounds()))
            {

                    cam.position.x = bird.getPosition().x;
                    mainMusic.stop();

                    gsm.set(new GameOverState(gsm));
                    l = k;

            }

            else
                cam.position.x = bird.getPosition().x +80;

        }
        if (bird.getPosition().y <= ground.getHeight()){

            gsm.set(new GameOverState(gsm));
           mainMusic.stop();

            l = k;

        }




        cam.update();

    }



    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(actualGamebg, cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x , bird.getPosition().y);
        for(Tube tube: tubes) {

            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        sb.draw(ground,groundPos1.x,groundPos1.y);
        sb.draw(ground,groundPos2.x,groundPos2.y);

        font24.draw(sb,SCORE,cam.position.x -2,cam.position.y + 15);
        sb.end();

    }

    /**
     * spritebatches must be drawn in order .The one at the bottommost acts as the top layer.
     */

    @Override
    public void dispose() {
        actualGamebg.dispose();
        bird.dispose();
        font24.dispose();
        for(Tube tube: tubes)
        {
            tube.dispose();
        }
        ground.dispose();
        mainMusic.dispose();
        scoreIncrease.dispose();
        wingFlap.dispose();


        System.out.println("Play State Disposed");

    }




    private void updateGround()
    {
        if (cam.position.x-(cam.viewportWidth/2) > groundPos1.x + ground.getWidth())
        {
            groundPos1.add(ground.getWidth()*2,0);
        }
        if (cam.position.x-(cam.viewportWidth/2) > groundPos2.x + ground.getWidth())
        {
            groundPos2.add(ground.getWidth()*2,0);
        }
    }


    public void Score()
    {
        k++;
        scoreIncrease.play();
        scoreIncrease.setVolume(0.3f);

    }
    public int getL(){
        return l;
    }


    public void fontGenerator(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter= new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size=12;
        parameter.color= Color.GOLD;
        parameter.borderColor= Color.GOLDENROD;
        font24= generator.generateFont(parameter);
        font24.setUseIntegerPositions(false);
    }


}

