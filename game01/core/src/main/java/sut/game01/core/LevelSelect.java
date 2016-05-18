package sut.game01.core;

/**
 * Created by Wuttinunt on 27/03/2016.
 */
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.*;

public class LevelSelect extends  Screen{


    private final ImageLayer bg;
    private final ImageLayer lvSelect;
    private final ImageLayer back_bt;
    private final ImageLayer store_bt;
    private final ImageLayer lv1_bt;
    private final ImageLayer lv1_bt_a;
    private final ImageLayer lv2_bt;
    private final ImageLayer lv2_bt_a;
    private final ImageLayer lv3_bt;
    private final ImageLayer lv3_bt_a;
    private final ImageLayer home_bt;


  //  private final ImageLayer musicOn_bt;
  //  private final ImageLayer info_bt;
   // private final ImageLayer logo_font;

    private final Store store;

    private final ScreenStack ss;
    private final GameplayScreen gameplayScreen;



    public LevelSelect (final ScreenStack ss){
        this.ss = ss;
        this.store = new Store(ss);
        this.gameplayScreen = new GameplayScreen(ss);


        Image bgImage = assets().getImage("images/bgHome.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image lvSelectImage = assets().getImage("images/level_select_panel.png");
        this.lvSelect = graphics().createImageLayer(lvSelectImage);
        lvSelect.setTranslation(-3,0);

        Image backBtImage = assets().getImage("images/back_bt.png");
        this.back_bt = graphics().createImageLayer(backBtImage);
        back_bt.setTranslation(150,390);

        Image storeBtImage = assets().getImage("images/store_bt.png");
        this.store_bt = graphics().createImageLayer(storeBtImage);
        store_bt.setTranslation(400,390);

        Image lv1BtImage = assets().getImage("images/lv1_bt.png");
        this.lv1_bt = graphics().createImageLayer(lv1BtImage);
        lv1_bt.setTranslation(170,150);

        Image lv1Bt_a_Image = assets().getImage("images/lv1_bt_a.png");
        this.lv1_bt_a = graphics().createImageLayer(lv1Bt_a_Image);
        lv1_bt_a.setTranslation(170,150);

        Image lv2BtImage = assets().getImage("images/lv2_bt.png");
        this.lv2_bt = graphics().createImageLayer(lv2BtImage);
        lv2_bt.setTranslation(270,150);

        Image lv2Bt_2_Image = assets().getImage("images/lv2_bt_a.png");
        this.lv2_bt_a = graphics().createImageLayer(lv2Bt_2_Image);
        lv2_bt_a.setTranslation(270,150);

        Image lv3BtImage = assets().getImage("images/lv3_bt.png");
        this.lv3_bt = graphics().createImageLayer(lv3BtImage);
        lv3_bt.setTranslation(370,150);

        Image lv3Bt_a_Image = assets().getImage("images/lv3_bt_a.png");
        this.lv3_bt_a = graphics().createImageLayer(lv3Bt_a_Image);
        lv3_bt_a.setTranslation(370,150);

        Image homeBtImage = assets().getImage("images/home_bt.png");
        this.home_bt = graphics().createImageLayer(homeBtImage);
        home_bt.setTranslation(280,390);


        lv1_bt_a.setVisible(false);
        lv2_bt_a.setVisible(false);
        lv3_bt_a.setVisible(false);
        back_bt.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {

                ss.remove(ss.top());
            }

        });

        store_bt.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {

                ss.push(new Store(ss));

            }

        });

        home_bt.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {

                ss.remove(ss.top());
            }
        });

        lv1_bt.addListener(new Mouse.LayerAdapter() {


            @Override
            public void onMouseOut(Mouse.MotionEvent event) {
                lv1_bt.setVisible(true);
                lv1_bt_a.setVisible(false);

            }

            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                lv1_bt.setVisible(false);
                lv1_bt_a.setVisible(true);

            }

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(new GameplayScreen(ss));
            }


        });

        lv1_bt_a.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                lv1_bt.setVisible(false);
                lv1_bt_a.setVisible(true);
            }

            @Override
            public void onMouseOut(Mouse.MotionEvent event) {
                lv1_bt.setVisible(true);
                lv1_bt_a.setVisible(false);
            }

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(new GameplayScreen(ss));
            }
        });

        lv2_bt.addListener(new Mouse.LayerAdapter() {


            @Override
            public void onMouseOut(Mouse.MotionEvent event) {
                lv2_bt.setVisible(true);
                lv2_bt_a.setVisible(false);

            }

            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                lv2_bt.setVisible(false);
                lv2_bt_a.setVisible(true);

            }

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                //ss.push(new GameplayScreen(ss));
            }


        });

        lv2_bt_a.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                lv2_bt.setVisible(false);
                lv2_bt_a.setVisible(true);
            }

            @Override
            public void onMouseOut(Mouse.MotionEvent event) {
                lv2_bt.setVisible(true);
                lv2_bt_a.setVisible(false);
            }

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                //ss.push(new GameplayScreen(ss));
            }
        });

        lv3_bt.addListener(new Mouse.LayerAdapter() {


            @Override
            public void onMouseOut(Mouse.MotionEvent event) {
                lv3_bt.setVisible(true);
                lv3_bt_a.setVisible(false);

            }

            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                lv3_bt.setVisible(false);
                lv3_bt_a.setVisible(true);

            }

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                //ss.push(new GameplayScreen(ss));
            }


        });

        lv3_bt_a.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                lv3_bt.setVisible(false);
                lv3_bt_a.setVisible(true);
            }

            @Override
            public void onMouseOut(Mouse.MotionEvent event) {
                lv3_bt.setVisible(true);
                lv3_bt_a.setVisible(false);
            }

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                //ss.push(new GameplayScreen(ss));
            }
        });

    }


    @Override
    public void wasShown() {
        super.wasShown();

        this.layer.add(bg);
        this.layer.add(lvSelect);
        this.layer.add(back_bt);
        this.layer.add(store_bt);
        this.layer.add(lv1_bt);
        this.layer.add(lv1_bt_a);
        this.layer.add(lv2_bt);
        this.layer.add(lv2_bt_a);
        this.layer.add(lv3_bt);
        this.layer.add(lv3_bt_a);


        //sthis.layer.add(home_bt);
      //  this.layer.add(info_bt);
     //   this.layer.add(logo_font);
        //this.layer.add(bBT);
    }
}
