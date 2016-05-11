package sut.game01.core;

/**
 * Created by Wuttinunt on 27/03/2016.
 */
import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.*;


public class HomeGame extends Screen{

    private final ImageLayer play_bt;
    private final ImageLayer bg;
    private final ImageLayer setting_bt;
    private final ImageLayer sound_bt;
    private final ImageLayer musicOn_bt;
    private final ImageLayer info_bt;
    private final ImageLayer logo_font;
    private final ImageLayer gameovertest;

    private final LevelSelect lvselect;
    private final GameOver gameover;
    private final TestScreen test;
    private final ScreenStack ss;

    public HomeGame (final ScreenStack ss){


        this.ss = ss;
        this.lvselect = new LevelSelect(ss);
        this.gameover = new GameOver(ss);
        this.test = new TestScreen(ss);

        Image bgImage = assets().getImage("images/bgHome.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image playbtImage = assets().getImage("images/play_bt.png");
        this.play_bt = graphics().createImageLayer(playbtImage);
        play_bt.setTranslation(400,70);

        Image settingImage = assets().getImage("images/setting.png");
        this.setting_bt = graphics().createImageLayer(settingImage);
        setting_bt.setTranslation(-10,270);

        Image soundImage = assets().getImage("images/sound_bt.png");
        this.sound_bt = graphics().createImageLayer(soundImage);
        sound_bt.setTranslation(22,280);

        Image musicOnImage = assets().getImage("images/music_on.png");
        this.musicOn_bt = graphics().createImageLayer(musicOnImage);
        musicOn_bt.setTranslation(20,327);

        Image infoImage = assets().getImage("images/info_bt.png");
        this.info_bt = graphics().createImageLayer(infoImage);
        info_bt.setTranslation(20,378);

        Image logoImage = assets().getImage("images/logo.png");
        this.logo_font = graphics().createImageLayer(logoImage);
        logo_font.setTranslation(350,320);

        Image gameoverTest = assets().getImage("images/gameovertest.png");
        this.gameovertest = graphics().createImageLayer(gameoverTest);
        gameovertest.setTranslation(350,10);


        play_bt.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(lvselect);
            }


        });

        keyboard().setListener(new Keyboard.Adapter(){
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.ENTER ){
                    ss.push(new LevelSelect(ss));
                }
            }
        });



        gameovertest.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                ss.push(gameover);
            }


        });
    }







    @Override
    public void wasShown() {
        super.wasShown();




        this.layer.add(bg);
        this.layer.add(play_bt);
        this.layer.add(setting_bt);
        this.layer.add(sound_bt);
        this.layer.add(musicOn_bt);
        this.layer.add(info_bt);
        this.layer.add(logo_font);
        this.layer.add(gameovertest);
        //this.layer.add(bBT);
    }

}
