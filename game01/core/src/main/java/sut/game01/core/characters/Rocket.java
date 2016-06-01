package sut.game01.core.characters;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.GameplayScreen;
import sut.game01.core.GameplayScreen2;
import sut.game01.core.GameplayScreen3;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;
import tripleplay.game.Screen;

public class Rocket extends Screen{

    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private World world;
    private int checkIndex = 1;

    public enum State {
        IDLE, Go, RIGHT
    };

    public State state = State.IDLE;

    private int e = 0;
    private int offset = 0; //chang Here

    //public float dx = 300f,dy = 400f;

    private float x; //<<
    private float y; //<<
    private Body body; //<<
    private Bullet bullet;


    public Rocket(final World world,final float _x, final float _y,int index){  //<<
        this.world = world;
        this.x = GameplayScreen.mouse_x; //<<
        this.y = GameplayScreen.mouse_y; //<<
        this.checkIndex = index;
        if(index == 1){

        }

        sprite = SpriteLoader.getSprite("images/rocket.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {

                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x,y + 3f);  //<<
                body = initPhysicsBody(world, GameplayScreen.M_PER_PIXEL * GameplayScreen.mouse_x, GameplayScreen.M_PER_PIXEL * GameplayScreen.mouse_y);  //<<
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

    public void update(int delta){
        if(hasLoaded == false) return;


        e += delta;
        if(e > 150) {
            switch (state){
                case IDLE: offset = 0; break;
                case Go: offset = 4; break;
                case RIGHT: offset = 8; break;
            }
            spriteIndex = offset + ((spriteIndex + 1)%4);
            sprite.setSprite(spriteIndex);
            sprite.layer().setTranslation(body.getPosition().x / GameplayScreen.M_PER_PIXEL + 1,  //<<
                    body.getPosition().y / GameplayScreen.M_PER_PIXEL);  //<<
            e = 0;
        }
        sprite.layer().setTranslation(x,y); //<<
    }

    private Body initPhysicsBody(World world, float x, float y){  //<< Start

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0, 0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(33 * GameplayScreen.M_PER_PIXEL/2,
                sprite.layer().height()*GameplayScreen.M_PER_PIXEL / 2);
        GameplayScreen.bodies.put(body, "Rocket");

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 100f;
        fixtureDef.restitution = 0f;
        body.setBullet(false);
        body.createFixture(fixtureDef);

       // body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }

    public Body getBody() {
        return body;
    }


    public void paint(Clock clock) {
        if (!hasLoaded) return;
        if (checkIndex == 1) {
            sprite.layer().setTranslation(
                    (GameplayScreen.mouse_x),
                    GameplayScreen.mouse_y);
            body.setTransform(new Vec2(GameplayScreen.mouse_x * GameplayScreen.M_PER_PIXEL, GameplayScreen.mouse_y * GameplayScreen.M_PER_PIXEL), 0f);
            //sprite.layer().setRotation(body.getAngle());
        }else if(checkIndex == 2){
            sprite.layer().setTranslation(
                    (GameplayScreen2.mouse_x),
                    GameplayScreen2.mouse_y);
            body.setTransform(new Vec2(GameplayScreen2.mouse_x * GameplayScreen2.M_PER_PIXEL, GameplayScreen2.mouse_y * GameplayScreen2.M_PER_PIXEL), 0f);
            //sprite.layer().setRotation(body.getAngle());
        }else if(checkIndex == 3){
            sprite.layer().setTranslation(
                    (GameplayScreen3.mouse_x),
                    GameplayScreen3.mouse_y);
            body.setTransform(new Vec2(GameplayScreen3.mouse_x * GameplayScreen3.M_PER_PIXEL, GameplayScreen3.mouse_y * GameplayScreen3.M_PER_PIXEL), 0f);
            //sprite.layer().setRotation(body.getAngle());
        }
    }
}