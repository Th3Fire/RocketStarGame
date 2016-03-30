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


        Image bgImage = assets().getImage("images/bg.png");
        ImageLayer bg = PlayN.graphics().createImageLayer(bgImage);
        this.layer.add(bg);

        z = new Zealot(200f,200f);
        this.layer.add(z.layer());


    }

    @Override
    public void update(int delta) {
        super.update(delta);
        z.update(delta);
    }
}
