package sut.game01.core.characters;

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
        IDLE, RUN, ATK
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 0; //chang Here

    public Zealot(final  float x, final float y){
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
            spriteIndex = offset + ((spriteIndex + 1)%4);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }
}