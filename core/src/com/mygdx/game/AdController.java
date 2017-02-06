package com.mygdx.game;

/**
 * Created by Kronos on 28-01-2017.
 */

public interface AdController {
    public void showBannerAds();
    public void hideBannerAds();
    public void showInterstitialAds(Runnable then);


}
