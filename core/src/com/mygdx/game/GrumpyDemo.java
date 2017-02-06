package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.States.AdCloseState;
import com.mygdx.game.States.GameOverState;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MenuState;

public class GrumpyDemo extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT= 800;

	public static final String TITLE = "Flappy Bird";
	private GameStateManager gsm;
	public static long AdStart = 0;
	public AdController adController;


	private SpriteBatch batch;

	public GrumpyDemo(AdController adController){
		this.adController = adController;
	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
		AdStart = TimeUtils.nanoTime();
		adController.showBannerAds();
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		if (TimeUtils.timeSinceMillis(AdStart)> 30000){
			adController.showInterstitialAds(new Runnable() {
				@Override
				public void run() {
					gsm.pop();
					gsm.push(new AdCloseState(gsm));
				}
			});

		}
	}

	@Override
	public void dispose () {
		batch.dispose();

	}
}
