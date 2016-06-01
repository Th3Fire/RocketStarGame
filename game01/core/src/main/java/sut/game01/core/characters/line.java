package sut.game01.core.characters;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.GameplayScreen;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;

public class line {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private int checkDestroy = 0;
    private World world;

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


    public line(final World world, final float _x, final float _y){  //<<

        this.x = _x; //<<
        this.y = _y; //<<
        this.world = world;

        sprite = SpriteLoader.getSprite("images/line.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {

                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x,y + 3f);  //<<
                body = initPhysicsBody(world, GameplayScreen.M_PER_PIXEL * x, GameplayScreen.M_PER_PIXEL * y);  //<<

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
        bodyDef.type = BodyType.STATIC;
        bodyDef.position = new Vec2(200/26.666667f, 200/26.666667f);
        Body body = world.createBody(bodyDef);
        GameplayScreen.bodies.put(body, "line");
        GameplayScreen.bodiesB.put(body, "line");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(640f,
                0.1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }

    public Body getBody() {
        return body;
    }

    public void contact (Contact contact, String s){

    }


    public void paint(Clock clock){
        if(!hasLoaded) return;

        sprite.layer().setTranslation(
                (body.getPosition().x / GameplayScreen.M_PER_PIXEL + 1),
                body.getPosition().y / GameplayScreen.M_PER_PIXEL);
        //sprite.layer().setRotation(body.getAngle());
    }  //<< end
}