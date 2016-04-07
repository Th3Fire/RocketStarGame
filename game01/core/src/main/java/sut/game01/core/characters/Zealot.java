package sut.game01.core.characters;

import playn.core.Key;
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.Layer;
import sut.game01.core.sprite.*;
/**
 * Created by Administrator on 30/3/2559.
 */
public class Zealot {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;

    public enum State {
        IDLE, Go, RIGHT
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 0; //chang Here

    public Zealot(final  float x, final float y){

        PlayN.keyboard().setListener((new Keyboard.Adapter(){
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.UP){

                        state = State.Go;
                        //case LEFT:state = State.ATK; break;
                        //case ATK:state = State.IDLE; break;

                }else if(event.key() == Key.RIGHT){
                        state = State.RIGHT;

                }else if(event.key() == Key.SPACE){
                        state = State.IDLE;
                }
            }
        }));


        sprite = SpriteLoader.getSprite("images/zealot.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x,y +13f);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!",cause);
            }
        });
    }
    public Layer layer(){
        return sprite.layer();
    }

    public void update(int detal){
        if(hasLoaded == false) return;

        e = e + detal;
        if(e > 150) {

            switch (state){
                case IDLE: offset = 0; break;
                case Go: offset = 4; break;
                case RIGHT: offset = 8; break;

            }

            spriteIndex = offset + ((spriteIndex + 1)%4);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }
}