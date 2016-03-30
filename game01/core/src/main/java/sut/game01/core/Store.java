package sut.game01.core;

/**
 * Created by Wuttinunt on 27/03/2016.
 */
import static playn.core.PlayN.*;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;

import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;

public class Store extends Screen{
    private final ImageLayer bg;
    private final ImageLayer lvSelect;
    private final ImageLayer back_bt;


    public Store (final ScreenStack ss){
        //     this.ss = ss;
        //    this.homegame = new HomeGame(ss);

        Image bgImage = assets().getImage("images/bgHome.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image storeImage = assets().getImage("images/store.png");
        this.lvSelect = graphics().createImageLayer(storeImage);
        lvSelect.setTranslation(120,0);

        Image backBtImage = assets().getImage("images/back_bt.png");
        this.back_bt = graphics().createImageLayer(backBtImage);
        back_bt.setTranslation(275,405);





        back_bt.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {

                ss.remove(ss.top());

            }


        });


    }


    @Override
    public void wasShown() {
        super.wasShown();

        this.layer.add(bg);
        this.layer.add(lvSelect);
        this.layer.add(back_bt);

    }
}
