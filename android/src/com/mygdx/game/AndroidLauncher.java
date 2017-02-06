package com.mygdx.game;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.TimeUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mygdx.game.GrumpyDemo;

public class AndroidLauncher extends AndroidApplication implements AdController {

	InterstitialAd interstitialAd;
	AdView bannerAd;
	private static final String  INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-5555192790757703/3493868870";
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-5555192790757703/1774313275";
	public long StartTime =0;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StartTime = TimeUtils.nanoTime();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		setupAds();
		View gameView = initializeForView(new GrumpyDemo(this),config);
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.addView(bannerAd,params);
		setContentView(layout);

	}

	public void setupAds(){
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);

		AdRequest.Builder builder = new AdRequest.Builder();
		AdRequest ad = builder.build();
		interstitialAd.loadAd(ad);


	}

	@Override
	public void showBannerAds() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				bannerAd.loadAd(ad);
			}
		});

	}

	@Override
	public void hideBannerAds() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});

	}

	@Override
	public void showInterstitialAds(final Runnable then) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if ( TimeUtils.timeSinceMillis(StartTime) > 15000){
					if (then != null)
			       	{
							interstitialAd.setAdListener(new AdListener() {
							@Override
							public void onAdClosed() {
								Gdx.app.postRunnable(then);
								AdRequest.Builder builder = new AdRequest.Builder();
								AdRequest ad = builder.build();
								interstitialAd.loadAd(ad);
							}
						});


					}
					interstitialAd.show();
					StartTime = 0;

				}

			}
		});

	}
}
