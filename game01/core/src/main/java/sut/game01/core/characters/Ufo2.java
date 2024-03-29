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
import sut.game01.core.GameplayScreen2;
import sut.game01.core.GameplayScreen3;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;

public class Ufo2 {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private boolean checkDestroy = false;
    private World world;


    public enum State {
        IDLE, DIE, RIGHT
    };

    public State state = State.IDLE;

    private int e = 0;
    private int offset = 0; //chang Here

    //public float dx = 300f,dy = 400f;

    private float x; //<<
    private float y; //<<
    private Body body; //<<
    private int _index = 1;

    public Ufo2(final World world, final float _x, final float _y,int index){  //<<

        this.x = _x; //<<
        this.y = _y; //<<
        this.world = world;
        this._index = index;

        sprite = SpriteLoader.getSprite("images/ufo.json");
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
                case DIE: offset = 6; break;
                case RIGHT: offset = 12; break;

            }
            if(checkDestroy){
                //world.destroyBody(body);
                System.out.println("world destroy work.");
            }

            if(state == State.DIE){

                if(spriteIndex == 11){
                    //sprite.layer().destroy();
                    //world.destroyBody(body);
                    sprite.layer().setVisible(false);

                }
            }

            spriteIndex = offset + ((spriteIndex + 1)%6);
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
        bodyDef.position = new Vec2(200/26.666667f, 200/26.666667f);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();

        if(_index == 1) {
            GameplayScreen.bodies.put(body, "ufo2_" );
            GameplayScreen.bodiesB.put(body, "ufo2");
            shape.setAsBox(30 * GameplayScreen.M_PER_PIXEL / 2, sprite.layer().height() * GameplayScreen.M_PER_PIXEL / 2);
        }else if(_index == 2){
            GameplayScreen2.bodies.put(body, "ufo2_" );
            GameplayScreen2.bodiesB.put(body, "ufo2");
            shape.setAsBox(30 * GameplayScreen2.M_PER_PIXEL / 2, sprite.layer().height() * GameplayScreen2.M_PER_PIXEL / 2);
        }else if(_index == 3){
            GameplayScreen3.bodies.put(body, "ufo2_");
            GameplayScreen3.bodiesB.put(body, "ufo2");
            shape.setAsBox(30 * GameplayScreen3.M_PER_PIXEL / 2, sprite.layer().height() * GameplayScreen3.M_PER_PIXEL / 2);
        }


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0f;
        fixtureDef.filter.groupIndex = -1;
        //body.setBullet(true);
        body.createFixture(fixtureDef);

        //body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }

    public Body getBody() {
        return body;
    }

    /*public void contact(Contact contact,String s,Ufo2 ufo2){

        checkDestroy = true;
        //GameplayScreen.deleteUfo2.add(ufo2);
        System.out.println("metheod contact work.");

    }
    */


    public void paint(Clock clock){
        if(!hasLoaded) return;

        sprite.layer().setTranslation(
                (body.getPosition().x / GameplayScreen.M_PER_PIXEL + 1),
                body.getPosition().y / GameplayScreen.M_PER_PIXEL);
        //sprite.layer().setRotation(body.getAngle());
    }  //<< end
}