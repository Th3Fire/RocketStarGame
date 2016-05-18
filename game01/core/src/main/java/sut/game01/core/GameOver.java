package sut.game01.core;

/**
 * Created by Wuttinunt on 27/03/2016.
 */
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class GameOver extends  Screen{
    private final ImageLayer bg;
    private final ImageLayer gameOver;
    private final ImageLayer menu_bt;
    private final ImageLayer reload_bt;

    private final LevelSelect levelselect;
    private final ScreenStack ss;


    public GameOver (final ScreenStack ss){
             this.ss = ss;
            this.levelselect = new LevelSelect(ss);

        Image bgImage = assets().getImage("images/gameoverBG.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image gameoverImage = assets().getImage("images/gameover.png");
        this.gameOver = graphics().createImageLayer(gameoverImage);
        gameOver.setTranslation(120,0);

        Image menuBtImage = assets().getImage("images/menu_bt.png");
        this.menu_bt = graphics().createImageLayer(menuBtImage);
        menu_bt.setTranslation(230,380);

        Image reloadBtImage = assets().getImage("images/reload_bt.png");
        this.reload_bt = graphics().createImageLayer(reloadBtImage);
        reload_bt.setTranslation(340,380);





        menu_bt.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {


                ss.remove(ss.top());
                ss.remove(ss.top());
                ss.remove(ss.top());

            }


        });


    }


    @Override
    public void wasShown() {
        super.wasShown();

        this.layer.add(bg);
        this.layer.add(gameOver);
        this.layer.add(menu_bt);
        this.layer.add(reload_bt);
        //this.layer.add(back_bt);

    }
}
