package sut.game01.core;

/**
 * Created by Wuttinunt on 27/03/2016.
 */

import playn.core.DebugDrawBox2D;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.util.Colors;

import java.io.*;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class GameWin extends  Screen{
    private final ImageLayer bg;
    private final ImageLayer gameOver;
    private final ImageLayer menu_bt;
    private final ImageLayer reload_bt;
    private final ImageLayer forward_bt;
    private int score = 0;
    private String strScore = "";
    private DebugDrawBox2D debugDraw;

    private final LevelSelect levelselect;
    private final ScreenStack ss;
    private ToolsG toolsG = new ToolsG();
    private int _index = 0;



    public GameWin(final ScreenStack ss,int index){
        this.ss = ss;
        this._index = index;
        this.levelselect = new LevelSelect(ss);


        Image bgImage = assets().getImage("images/gameoverBG.png");
        this.bg = graphics().createImageLayer(bgImage);

        Image gameoverImage = assets().getImage("images/level_clear.png");
        this.gameOver = graphics().createImageLayer(gameoverImage);
        gameOver.setTranslation(120,0);

        Image menuBtImage = assets().getImage("images/menu_bt.png");
        this.menu_bt = graphics().createImageLayer(menuBtImage);
        menu_bt.setTranslation(180,380);

        Image reloadBtImage = assets().getImage("images/reload_bt.png");
        this.reload_bt = graphics().createImageLayer(reloadBtImage);
        reload_bt.setTranslation(280,380);

        Image forwardBtImage = assets().getImage("images/forward_bt.png");
        this.forward_bt = graphics().createImageLayer(forwardBtImage);
        forward_bt.setTranslation(375,380);





        menu_bt.addListener(new Mouse.LayerAdapter() {

            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                int s = ss.size();
                System.out.println("stack size = "+s);
                switch (s){
                    case 3:
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        break;
                    case 4:
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        break;
                    case 5:
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        break;
                    case 6:
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        ss.remove(ss.top());
                        break;

                }

            }
        });

        forward_bt.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                int s = ss.size();
                System.out.println("stack size = "+s);
                System.out.println("index = " + _index);
                switch (_index){
                    case 1:
                        System.out.println("Next chapter = 2" );
                        ss.push(new GameplayScreen2(ss));
                        break;

                    case 2:
                        System.out.println("Next chapter = 3");
                        ss.push(new GameplayScreen3(ss));
                        break;

                }
            }
        });

        reload_bt.addListener(new Mouse.LayerAdapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
                int s = ss.size();
                System.out.println("stack size = "+s);
                System.out.println("index = " + _index);
                switch (_index){
                    case 1:
                        System.out.println("reload " + _index);
                        ss.push(new GameplayScreen(ss));
                        break;

                    case 2:
                        System.out.println("reload " + _index);
                        ss.push(new GameplayScreen2(ss));
                        break;
                    case 3:
                        System.out.println("reload " + _index);
                        ss.push(new GameplayScreen3(ss));
                        break;

                }
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
        this.layer.add(forward_bt);
        if(_index == 3){
            this.layer.add(forward_bt.setVisible(false));
            menu_bt.setTranslation(220,380);
            reload_bt.setTranslation(370,380);

        }
        try {
            InputStream in = new FileInputStream(new File("D:YourScore.txt"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            strScore = out.toString();
              //Prints the string content read from input stream
            reader.close();


        }catch (Exception e){
            System.out.println("Error while reading file line by line:" + e.getMessage());
        }
        //toolsG.genText(strScore,18, Colors.WHITE,320,200);
        this.layer.add(toolsG.genText(strScore,26, Colors.BLACK,300,210));

        //GameplayScreen.checkUfo = 0;
        //this.layer.add(back_bt);

    }

    @Override
    public void update(int delta) {
        super.update(delta);
        toolsG.update(delta);
    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        toolsG.paint(clock);


    }
}
