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
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Mouse;


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
        shieldP.setTranslation(215,1);
        this.layer.add(shieldP);

        Image coins = assets().getImage("images/coins.png");
        ImageLayer coinsP = PlayN.graphics().createImageLayer(coins);
        coinsP.setTranslation(410,1);
        this.layer.add(coinsP);

        Image lvps = assets().getImage("images/levelps.png");
        ImageLayer lvpsP = PlayN.graphics().createImageLayer(lvps);
        lvpsP.setTranslation(1,50);
        this.layer.add(lvpsP);

        Image pause_bt = assets().getImage("images/pause_bt.png");
        ImageLayer pauseBt = PlayN.graphics().createImageLayer(pause_bt);
        pauseBt.setTranslation(590,1);
        this.layer.add(pauseBt);

        Image pause_dialog = assets().getImage("images/pause_bt.png");
        ImageLayer pauseDialog = PlayN.graphics().createImageLayer(pause_dialog);
        pauseDialog.setTranslation(400,200);


        pauseBt.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {



            }


        });


        z = new Zealot(300f,430f);
        this.layer.add(z.layer());


    }

    @Override
    public void update(int delta) {
        super.update(delta);
        z.update(delta);
    }
}
