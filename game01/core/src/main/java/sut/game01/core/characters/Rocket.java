package sut.game01.core.characters;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.GameplayScreen;
import sut.game01.core.GraGame;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;

public class Rocket {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;

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

    public Rocket(final World world,final float _x, final float _y){  //<<

        this.x = GameplayScreen.mouse_x; //<<
        this.y = GameplayScreen.mouse_y; //<<

        sprite = SpriteLoader.getSprite("images/rocket.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {

                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x,y + 3f);  //<<
                body = initPhysicsBody(world, GraGame.M_PER_PIXEL * GameplayScreen.mouse_x, GraGame.M_PER_PIXEL * GameplayScreen.mouse_y);  //<<
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
            sprite.layer().setTranslation(body.getPosition().x / GraGame.M_PER_PIXEL + 1,  //<<
                    body.getPosition().y / GraGame.M_PER_PIXEL);  //<<
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
        shape.setAsBox(33 * GraGame.M_PER_PIXEL/2,
                sprite.layer().height()*GraGame.M_PER_PIXEL / 2);
        GameplayScreen.bodies.put(body, "Rocket > " + GameplayScreen.checkR );
        GameplayScreen.checkR++;
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


    public void paint(Clock clock){
        if(!hasLoaded) return;

        sprite.layer().setTranslation(
                (GameplayScreen.mouse_x ),
                GameplayScreen.mouse_y );
        body.setTransform(new Vec2(GameplayScreen.mouse_x * GraGame.M_PER_PIXEL,GameplayScreen.mouse_y * GraGame.M_PER_PIXEL),0f);
        //sprite.layer().setRotation(body.getAngle());
    }  //<< end
}