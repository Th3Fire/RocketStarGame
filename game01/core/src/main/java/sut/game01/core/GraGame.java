package sut.game01.core;


import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.characters.Rocket;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.util.ArrayList;
import java.util.List;

import static playn.core.PlayN.*;


public class GraGame extends Screen{
    private final ScreenStack ss;
    private ImageLayer bg;

    public static float M_PER_PIXEL = 1 / 26.666667f;

    //size of world
    private static int width = 24;  //640 px
    private static int height = 18; //480 px

    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;

    private Rocket zealot;

    private List<Rocket> zealotMap;


    public GraGame(final ScreenStack ss){
        this.ss = ss;


        Vec2 gravity = new Vec2(0.0f , 10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        zealotMap = new ArrayList<Rocket>();  //<<

        Image bgImage = assets().getImage("images/bg.png");
        bg = PlayN.graphics().createImageLayer(bgImage);
        //this.layer.add(bg);

        //Image image = assets().getImage("images/sound_bt.png");
        //ImageLayer test1 = graphics().createImageLayer(image);

        zealot = new Rocket(world,320f,200f);  //<<



    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bg);

        mouse().setListener(new Mouse.Adapter(){
            @Override
            public void onMouseUp(Mouse.ButtonEvent event) {

                Rocket ze = new Rocket(world,event.x(),event.y());  //<< start

                zealotMap.add(ze);





                //BodyDef bodyDef = new BodyDef();
                //bodyDef.type = BodyType.DYNAMIC;
                //bodyDef.position = new Vec2(event.x() * M_PER_PIXEL,event.y() * M_PER_PIXEL);

                //Body body = world.createBody(bodyDef);

                //CircleShape shape = new CircleShape();
                //shape.setRadius(0.4f);

                //FixtureDef fixtureDef = new FixtureDef();
                // fixtureDef.shape = test1;
                //fixtureDef.density = 0.4f;
                //fixtureDef.friction = 0.1f;
                //fixtureDef.restitution = 1f;

                //body.createFixture(fixtureDef);
                //body.setLinearDamping(0.2f);

            }
        });
        this.layer.add(zealot.layer());

        for(Rocket z: zealotMap){
            System.out.println("add");
            this.layer.add(z.layer());
        }  //<< End

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
        ground3.createFixture(groundShape4, 0.0f);

    }


    @Override
    public void update(int delta) {
        super.update(delta);
        zealot.update(delta);  //<< start
        for(Rocket z: zealotMap){
            this.layer.add(z.layer());
            z.update(delta);
        } //<< end
        world.step(0.033f, 10, 10);
    }


    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        zealot.paint(clock); //<<  start
        for (Rocket z: zealotMap){
            z.paint(clock);
        }  //<< end

        if(showDebugDraw) {
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }
}
