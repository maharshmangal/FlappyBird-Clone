package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GrumpyDemo;

/**
 * Created by Kronos on 28-01-2017.
 */

public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private Music music;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, GrumpyDemo.WIDTH / 2, GrumpyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("mainmusic.mp3"));
        music.setVolume(0.8f);
        music.play();

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));

        }


    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        music.dispose();
        System.out.println("Menu State Disposed");
    }
}
