package sut.game01.core;

import playn.core.Game;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;


public class MyGame extends Game.Default {


    public static final int UPDATE_RATE = 25;
    private ScreenStack ss = new ScreenStack();
    protected  final Clock.Source clock = new Clock.Source(UPDATE_RATE);

    public MyGame(){
        super(UPDATE_RATE);
    }
  @Override
  public void init() {
    // create and add background image layer


      ss.push(new HomeGame(ss));

  }

  @Override
  public void update(int delta) {
      ss.update(delta);
  }

  @Override
  public void paint(float alpha) {
      clock.paint(alpha);
      ss.paint(clock);
    // the background automatically paints itself, so no need to do anything here!
  }
}
