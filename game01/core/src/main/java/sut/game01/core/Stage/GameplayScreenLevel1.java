package sut.game01.core.Stage;

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
import sut.game01.core.GameOver;
import sut.game01.core.ToolsG;
import sut.game01.core.characters.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static playn.core.PlayN.*;

/**
 * Created by Wuttinunt on 07/04/2016.
 */
public class GameplayScreenLevel1 extends Screen{
    private final ScreenStack ss;
    private ImageLayer bg;
    private ImageLayer liftP;
    private ImageLayer liftP2;
    private ImageLayer liftP3;
    ToolsG toolsG = new ToolsG();
    public static boolean CheckGetPosition = false;

    public static float M_PER_PIXEL = 1 / 26.666667f;

    //size of world
    private static int width = 24;  //640 px
    private static int height = 18; //480 px

    private static World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;

    private static Rocket rocket;
    //private Coin coin;
    private Ufo2 ufo2;
    private Heart heart;
    private Bullet bullet;
    private Ufo3 ufo3;
    static boolean checkOver = true;

    //private List<Rocket> zealotMap;

    public static HashMap<Body, String> bodiesB = new HashMap<Body, String>();

    public static HashMap<Body, String> bodies  = new HashMap<Body, String>();
    private List<Body> delete = new ArrayList<Body>();
    static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    static ArrayList<Bullet> deleteBullet = new ArrayList<Bullet>();

    private GroupLayer groupBullet = graphics().createGroupLayer();

    //private List<Coin> coinMap;


    private int i = 0;
    private int score = 0;
    public static int lifeTotal =3;
    private boolean checkLifeFull = false;

    private String debugString = "";
    private String strScore = "";
    public static int check = -1;
    public static int checkC = -1;
    public static float mouse_x = 0f;
    public static float mouse_y = 0f;

    public GameplayScreenLevel1(final ScreenStack ss){
        this.ss = ss;

        Vec2 gravity = new Vec2(0.0f , 0.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);




        //coinMap = new ArrayList<Coin>();

        rocket = new Rocket(world,320f,480f);  //<<
        //coin = new Coin(world,310f,210f);
        //ufo2 = new Ufo2(world,400f,100f);
        ufo3 = new Ufo3(world,370f,100f);
        heart = new Heart(world,100f,100f);

        Image bgImage = assets().getImage("images/bgGameplay2.png");
        bg = PlayN.graphics().createImageLayer(bgImage);

        Image life = assets().getImage("images/life_1.png");
        liftP = PlayN.graphics().createImageLayer(life);
        liftP.setTranslation(10,1);

        Image life2 = assets().getImage("images/life_1.png");
        liftP2 = PlayN.graphics().createImageLayer(life2);
        liftP2.setTranslation(50,1);

        Image life3 = assets().getImage("images/life_1.png");
        liftP3 = PlayN.graphics().createImageLayer(life3);
        liftP3.setTranslation(90,1);





    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(liftP);
        this.layer.add(liftP2);
        this.layer.add(liftP3);

        Image shield = assets().getImage("images/shield.png");
        ImageLayer shieldP = PlayN.graphics().createImageLayer(shield);
        shieldP.setTranslation(225,1);
        this.layer.add(shieldP);

        final Image coins = assets().getImage("images/coins.png");
        ImageLayer coinsP = PlayN.graphics().createImageLayer(coins);
        coinsP.setTranslation(440,1);
        this.layer.add(coinsP);

        this.layer.add(rocket.layer());
        //this.layer.add(coin.layer());
        //this.layer.add(ufo2.layer());
        this.layer.add(heart.layer());
        this.layer.add(ufo3.layer());
        this.layer.add(groupBullet);


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
        PlayN.mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseMove(Mouse.MotionEvent event) {
                mouse_x = event.x();
                mouse_y = event.y();

            }

            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                setBullet();
                //if (checkOver == false) {
                    //bullets.add(new Bullet(world, rocket.getBody().getPosition().x * 26.667f, rocket.getBody().getPosition().y * 26.667f + -30f));
                    //System.out.println("clicked.");
                    //check++;
                        //graphics().rootLayer().add(bullets.get(check).layer());
                        //bodies.put(bullets.get(check).getBody(), "bullet_" + check);

                        //System.out.println(bodies.get(bullets.get(check).getBody()));


                }
            //}
        });



        ////////////////////contact

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                System.out.println(bodies.get(a) + " contected  " + bodies.get(b));

                for(Bullet bullet: bullets){
                    if(b == bullet.getBody() && a == ufo3.getBody()){
                        //ufo3.layer().destroy();
                        delete.add(a);
                        delete.add(b);
                        deleteBullet.add(bullet);
                    }
                }
                if(bodiesB.get(b) == "bullet" && a == ufo3.getBody()){
                    //System.out.println("set delete " + bodies.get(a));
                    ufo3.state = Ufo3.State.DIE;
                    //bullet.state = Bullet.State.Go;
                    //delete.add(a);
                    //deleteBullet.add(b);
                    //a.setActive(false);
                    //b.setActive(false);

                    //a.setActive(false);
                    //delete.add(a);
                }
               /* if(a == heart.getBody() && bodiesB.get(b).equals("bullet")){
                    System.out.println("set delete " + bodiesB.get(b));
                    //b.setActive(false);
                    //Heart.state = Heart.State.Delete;
                    delete.add(b);


                }*/
                //System.out.println(bodiesR.get(a) + " contected  " + bodies.get(b));

                /*if(contact.getFixtureA().getBody() == rocket.getBody() && contact.getFixtureB().getBody() == coin.getBody()){
                    System.out.println(bodies.get(a) + " and " + bodies.get(b));
                    score++;
                    debugString = bodies.get(a) + " contacted with " + bodies.get(b) + " score = " + score;
                    strScore = ""+score;

                }*/
                else if(contact.getFixtureA().getBody() == rocket.getBody() && contact.getFixtureB().getBody() == ufo3.getBody()
                        || contact.getFixtureA().getBody() == ufo3.getBody() && contact.getFixtureB().getBody() == rocket.getBody()){
                    //System.out.println(bodies.get(a) + " contected  " + bodies.get(b));
                    delete.add(b);
                    ufo3.state = Ufo3.State.DIE;
                    if(lifeTotal <=3) {
                        if(lifeTotal == 3) {
                            liftP3.setVisible(false);
                            lifeTotal--;
                            checkLifeFull = false;
                        }else if (lifeTotal == 2){
                            liftP2.setVisible(false);
                            lifeTotal--;
                            checkLifeFull = false;
                        }else if (lifeTotal == 1){
                            liftP.setVisible(false);
                            lifeTotal--;
                            checkLifeFull = false;
                            if(lifeTotal == 0){
                                ss.push(new GameOver(ss));
                                lifeTotal = 3;
                                checkLifeFull = false;
                            }
                        }else if(lifeTotal == 0){
                            ss.push(new GameOver(ss));
                            lifeTotal = 3;
                            checkLifeFull = false;
                        }
                        System.out.println("life total = " + lifeTotal);

                    }

                }else if(contact.getFixtureA().getBody() == rocket.getBody() && contact.getFixtureB().getBody() == heart.getBody()
                         ){
                    //System.out.println(bodies.get(a) + " contected  " + bodies.get(b));
                    //System.out.println("life total = "+ lifeTotal);
                    //b.setActive(false);
                    delete.add(b);
                    if(lifeTotal <=3 && checkLifeFull == false){
                        if(lifeTotal == 1){
                            lifeTotal++;
                        }else if (lifeTotal == 2){
                            lifeTotal++;
                            if(lifeTotal == 3){
                                System.out.println("Life Max.");
                                checkLifeFull = true;

                            }

                        }else if (lifeTotal == 3){


                            System.out.println("Life Max.");

                        }
                        if(lifeTotal == 1){
                            liftP.setVisible(true);
                            liftP2.setVisible(false);
                            liftP3.setVisible(false);
                        }else if(lifeTotal == 2){
                            liftP.setVisible(true);
                            liftP2.setVisible(true);
                            liftP3.setVisible(false);
                        }else if (lifeTotal == 3){
                            liftP.setVisible(true);
                            liftP2.setVisible(true);
                            liftP3.setVisible(true);
                        }
                    }if(lifeTotal == 3 && checkLifeFull == true){
                        System.out.println("Life Max.");
                    }
                    System.out.println("life total = "+ lifeTotal);

                }
                // System.out.println(bodies.get(a).substring(0,5));
                /*if(bodies.get(a).substring(0,5).equals("bullet") ){
                    System.out.println("B >> << UFO_2");
                }*/
                else if(a == rocket.getBody() && bodiesB.get(b).equals("bullet")){
                    b.applyLinearImpulse( new Vec2(0f,-70f)  , b.getPosition() );
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


        //////////////end contact


        keyboard().setListener((new Keyboard.Adapter(){
            @Override
            public void onKeyDown(Keyboard.Event event) {
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
                    (int)(width/ GameplayScreenLevel1.M_PER_PIXEL),
                    (int)(height/ GameplayScreenLevel1.M_PER_PIXEL));

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
                     //DebugDraw.e_aabbBit
            );

            debugDraw.setCamera(0,0,1f / GameplayScreenLevel1.M_PER_PIXEL);
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
        //groundShape4.set(new Vec2(0,0), new Vec2(width, 0));  //บน
        //ground4.createFixture(groundShape4, 0.0f);

    }
public static void setBullet(){
    check++;
    bullets.add( new Bullet(world,rocket.getBody().getPosition().x*26.667f,rocket.getBody().getPosition().y*26.667f + -30f));
    //for (int i =0 ; i <= check ; i++){
        //  if (i < 1)
        //graphics().rootLayer().add(bullets.get(i).layer());
        //bodies.put(bullets.get(i).getBody(),"bullet_" + i);
        //System.out.println(bodies.get(bullets.get(i)));
   // }
}

    @Override
    public void update(int delta) {
        super.update(delta);
        rocket.update(delta);  //<< start
        //coin.update(delta);
        //ufo2.update(delta);
        ufo3.update(delta);
        heart.update(delta);


        for(Bullet bullet: bullets){
            bullet.update(delta);
        }
        for(Bullet bullet: bullets){
            groupBullet.add(bullet.layer());
        }




        while (deleteBullet.size() > 0) {

            deleteBullet.get(0).getBody().setActive(false);
            bullets.get(0).layer().destroy();
            bullets.remove(0);
            world.destroyBody(deleteBullet.remove(0).getBody());
            System.out.print("Deleted bullet");

        }

        while (delete.size() > 0) {
            delete.get(0).setActive(false);



            //layer.get(0).destroy();
            world.destroyBody(delete.remove(0));

        }
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
        //coin.paint(clock);
        //ufo2.paint(clock);
        ufo3.paint(clock);
        heart.paint(clock);
        //for (int i =0 ; i <=1 ; i++){
            //if (i < 1)
        for(Bullet bullet: bullets){
            bullet.paint(clock);
        }

        //System.out.println("mouse x = " + mouse_x);
        //System.out.println("mouse y = " + mouse_y);

        //if(rocket.getBody().getPosition().x != mouse_x){
          //  rocket.getBody().applyForce(new Vec2(5f, 0f), rocket.getBody().getPosition());
        //}
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
            //debugDraw.getCanvas().drawText(debugString,100,100);

        }
    }
}
