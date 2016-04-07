package sut.game01.core;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;
import sut.game01.core.characters.Zealot;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;

/**
 * Created by Wuttinunt on 07/04/2016.
 */
public class GameplayScreen extends Screen{

    public GameplayScreen (final ScreenStack ss){

    }
    private Zealot z;

    @Override
    public void wasShown() {
        super.wasShown();


        Image bgImage = assets().getImage("images/bgGameplay2.png");
        ImageLayer bg = PlayN.graphics().createImageLayer(bgImage);
        this.layer.add(bg);

        Image life = assets().getImage("images/life.png");
        ImageLayer liftP = PlayN.graphics().createImageLayer(life);
        liftP.setTranslation(10,1);
        this.layer.add(liftP);

        Image shield = assets().getImage("images/shield.png");
        ImageLayer shieldP = PlayN.graphics().createImageLayer(shield);
        shieldP.setTranslation(225,1);
        this.layer.add(shieldP);

        Image coins = assets().getImage("images/coins.png");
        ImageLayer coinsP = PlayN.graphics().createImageLayer(coins);
        coinsP.setTranslation(440,1);
        this.layer.add(coinsP);


        z = new Zealot(300f,430f);
        this.layer.add(z.layer());


    }

    @Override
    public void update(int delta) {
        super.update(delta);
        z.update(delta);
    }
}
