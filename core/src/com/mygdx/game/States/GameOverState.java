package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GrumpyDemo;
import com.mygdx.game.States.PlayState;

/**
 * Created by Kronos on 28-01-2017.
 */

public class GameOverState extends State {
    private Texture gameOver;
    private Texture gameOverBg;
    private Texture playAgainBtn;
    private Texture ground;
    private Vector2 groundPos1;
    private Music gameOverMusic;
    private BitmapFont totalScore;
    private String STRING;
    public PlayState playState;
    public Boolean AdStart = true;




    public GameOverState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, GrumpyDemo.WIDTH/2,GrumpyDemo.HEIGHT/2);

        gameOver = new Texture("gameover.png");
        gameOverBg =  new Texture ("bg.png");
        playAgainBtn = new Texture("playbtn.png");
        ground = new Texture("ground.png");
        AdStart = new Boolean(true);

        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("gameoversfx.ogg"));
        groundPos1 = new Vector2(cam.position.x -cam.viewportWidth/2, -30);
        totalScore =  new BitmapFont();
        STRING = new String();
        playState = new PlayState(gsm);
        gameOverMusic.play();
        gameOverMusic.setVolume(1.0f);



    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {

                gsm.set(new PlayState(gsm));
                gameOverMusic.stop();
                AdStart = false;

        }


    }

    @Override
    public void update(float dt) {
        handleInput();
        STRING = "SCORE: " + playState.getL();
        fontGenerator();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(gameOverBg,0,0);
        sb.draw(gameOver, cam.position.x-gameOver.getWidth()/2 , 5*(cam.position.y/3));
        sb.draw(ground,groundPos1.x,groundPos1.y);
        sb.draw(playAgainBtn,cam.position.x-playAgainBtn.getWidth()/2,2*(cam.position.y/3));
        totalScore.draw(sb,STRING,cam.position.x - gameOver.getWidth()/4 ,5*(cam.position.y/4));

        sb.end();


    }

    @Override
    public void dispose() {
        gameOver.dispose();
        gameOverBg.dispose();
        playAgainBtn.dispose();
        ground.dispose();
        totalScore.dispose();
        playState.dispose();

        System.out.println("Game Over State Disposed");

    }

    public void fontGenerator(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter= new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size=12;
        parameter.color= Color.GOLD;
        parameter.borderColor= Color.GOLDENROD;
        totalScore= generator.generateFont(parameter);
        totalScore.setUseIntegerPositions(false);
    }

    public Boolean getAdStart(){
        return AdStart;
    }


}
