package sut.game01.core;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.characters.Coin;
import sut.game01.core.characters.Rocket;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static playn.core.PlayN.*;

/**
 * Created by Wuttinunt on 07/04/2016.
 */
public class GameplayScreen extends Screen{
    private final ScreenStack ss;


    public static float M_PER_PIXEL = 1 / 26.666667f;

    //size of world
    private static int width = 24;  //640 px
    private static int height = 18; //480 px

    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;

    private Rocket rocket;
    private Coin coin;

    //private List<Rocket> zealotMap;

    //private HashMap<Body, String> bodies = new HashMap<Body, String>();

    public static HashMap<Body, String> bodies  = new HashMap<Body, String>();
    private List<Body> delete = new ArrayList<Body>();

    private List<Coin> coinMap;


    private int i = 0;
    private int score = 0;
    private String debugString = "";
    public static int checkR = -18;
    public static int checkC = -1;

    public GameplayScreen (final ScreenStack ss){
        this.ss = ss;

        Vec2 gravity = new Vec2(0.0f , 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        //coinMap = new ArrayList<Coin>();

        rocket = new Rocket(world,320f,200f);  //<<
        coin = new Coin(world,310f,210f);


    }

    @Override
    public void wasShown() {
        super.wasShown();


        Image bgImage = assets().getImage("images/bgGameplay2.png");
        ImageLayer bg = PlayN.graphics().createImageLayer(bgImage);
        this.layer.add(bg);

        Image life = assets().getImage("images/life.png");
        ImageLayer liftP = PlayN.graphics().createImageLayer(life);
        liftP.setTranslation(10,1);
        this.layer.add(liftP);

        Image shield = assets().getImage("images/shield.png");
        ImageLayer shieldP = PlayN.graphics().createImageLayer(shield);
        shieldP.setTranslation(225,1);
        this.layer.add(shieldP);

        final Image coins = assets().getImage("images/coins.png");
        ImageLayer coinsP = PlayN.graphics().createImageLayer(coins);
        coinsP.setTranslation(440,1);
        this.layer.add(coinsP);


/*
        PlayN.keyboard().setListener((new Keyboard.Adapter(){
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.UP){
                    //z.state = Zealot.State.Go;

                }else if(event.key() == Key.RIGHT){
                    //z.state = Zealot.State.RIGHT;
                   // z.x = z.x - 1f;

                }else if(event.key() == Key.LEFT){
                    //z.state = Zealot.State.IDLE;
                }
            }
        }));

        */



/*
        mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {
/*

                //Rocket ze = new Rocket(world,event.x(),event.y());  //<< start

                //zealotMap.add(ze);
*/
                //Coin cc = new Coin(world,event.x(),event.y());

                //coinMap.add(cc);

/*
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyType.STATIC;
                bodyDef.position = new Vec2(event.x() * M_PER_PIXEL,event.y() * M_PER_PIXEL);

                Body body = world.createBody(bodyDef);

                CircleShape shape = new CircleShape();
                shape.setRadius(0.4f);



                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 0.4f;
                fixtureDef.friction = 0.1f;
                fixtureDef.restitution = 1f;

                body.createFixture(fixtureDef);
                body.setLinearDamping(0.2f);



                bodies.put(coin.getBody(), "Ob_" + i);
                System.out.println(">>>>"+coin.getBody());
                i++;


            }
        }); */


        ////////// contact

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                System.out.println(bodies.get(a) + " and " + bodies.get(b));
                if(contact.getFixtureA().getBody() == coin.getBody() || contact.getFixtureB().getBody() == coin.getBody()){
                    score++;
                    debugString = bodies.get(a) + " contacted with " + bodies.get(b) + " score = " + score;
                    //b.setActive(false);
                    //delete.add(b);

                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });

        //////////////

        this.layer.add(rocket.layer());
        this.layer.add(coin.layer());




        keyboard().setListener((new Keyboard.Adapter(){
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.LEFT){
                    rocket.getBody().applyForce(new Vec2(-100f,0f),rocket.getBody().getPosition());
                }else if(event.key() == Key.RIGHT) {
                    rocket.getBody().applyForce(new Vec2(100f, -0f), rocket.getBody().getPosition());
                }else if(event.key() == Key.SPACE){
                    rocket.getBody().applyForce(new Vec2(0f, -1000f), rocket.getBody().getPosition());
                }else if(event.key() == Key.ESCAPE){
                    ss.remove(ss.top());
                    ss.remove(ss.top());
                }
            }
        }));


       // for(Rocket z: zealotMap){
        //    System.out.println("add");
        //    this.layer.add(z.layer());
       // }  //<< End

        //for(Coin c: coinMap){
        //    System.out.println("add");
        //    this.layer.add(c.layer());
        //}  //<< End

        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int)(width/GraGame.M_PER_PIXEL),
                    (int)(height/GraGame.M_PER_PIXEL));

            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(
                            DebugDraw.e_shapeBit |
                            DebugDraw.e_jointBit
                    // DebugDraw.e_aabbBit
            );
            debugDraw.setCamera(0,0,1f / GraGame.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0,height), new Vec2(width, height));  // ล่าง
        ground.createFixture(groundShape, 0.0f);

        Body ground2 = world.createBody(new BodyDef());
        EdgeShape groundShape2 = new EdgeShape();
        groundShape2.set(new Vec2(0,0), new Vec2(0, height));  //ซ้าย
        ground2.createFixture(groundShape2, 0.0f);

        Body ground3 = world.createBody(new BodyDef());
        EdgeShape groundShape3 = new EdgeShape();
        groundShape3.set(new Vec2(width,0), new Vec2(width, height));  //ขวา
        ground3.createFixture(groundShape3, 0.0f);

        Body ground4 = world.createBody(new BodyDef());
        EdgeShape groundShape4 = new EdgeShape();
        groundShape4.set(new Vec2(0,0), new Vec2(width, 0));  //บน
        ground4.createFixture(groundShape4, 0.0f);


    }

    @Override
    public void update(int delta) {
        super.update(delta);
        rocket.update(delta);  //<< start
        coin.update(delta);
        while (delete.size() > 0) world.destroyBody(delete.remove(0));
        //for(Rocket z: zealotMap){
        //    this.layer.add(z.layer());
        //    z.update(delta);
        //} //<< end

        //for(Coin c: coinMap){
        //    this.layer.add(c.layer());
        //    c.update(delta);
       // } //<< end
        world.step(0.033f, 10, 10);
    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        rocket.paint(clock); //<<  start
        coin.paint(clock);
        //for (Rocket z: zealotMap){
        //    z.paint(clock);
        //}  //<< end

       // for (Coin c: coinMap){
       //     c.paint(clock);
       //}  //<< end

        if(showDebugDraw) {
            debugDraw.getCanvas().clear();
            world.drawDebugData();
            debugDraw.getCanvas().setFillColor(Color.rgb(255,255,255));
            debugDraw.getCanvas().drawText(debugString,100,100);
        }
    }
}
