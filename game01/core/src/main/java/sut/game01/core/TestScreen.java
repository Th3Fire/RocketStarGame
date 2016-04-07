package sut.game01.core;

/**
 * Created by Wuttinunt on 27/03/2016.
 */
import playn.core.Image;
import playn.core.ImageLayer;
import sut.game01.core.characters.Zealot;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.PlayN;
import static playn.core.PlayN.*;


public class TestScreen extends Screen{

    public TestScreen (final ScreenStack ss){

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
        shieldP.setTranslation(240,1);
        this.layer.add(shieldP);

        Image coins = assets().getImage("images/coins.png");
        ImageLayer coinsP = PlayN.graphics().createImageLayer(coins);
        coinsP.setTranslation(460,1);
        this.layer.add(coinsP);

        Image lvps = assets().getImage("images/levelps.png");
        ImageLayer lvpsP = PlayN.graphics().createImageLayer(lvps);
        lvpsP.setTranslation(1,50);
        this.layer.add(lvpsP);


        z = new Zealot(300f,430f);
        this.layer.add(z.layer());


    }

    @Override
    public void update(int delta) {
        super.update(delta);
        z.update(delta);
    }
}
