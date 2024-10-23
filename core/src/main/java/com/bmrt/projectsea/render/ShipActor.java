package com.bmrt.projectsea.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;

import static com.bmrt.projectsea.ProjectSeaMain.UNIT;

public class ShipActor extends Actor {

    private final static int SHIP_PIXEL_WIDTH = 32;
    private final static int SHIP_PIXEL_HEIGHT = 64;
    private final static float SHIP_TILES_WIDTH = UNIT * SHIP_PIXEL_WIDTH;
    private final static float OFF_SET_X = SHIP_TILES_WIDTH / 2;
    private final static float SHIP_TILES_HEIGHT = UNIT * SHIP_PIXEL_HEIGHT;
    private final static float OFF_SET_Y = SHIP_TILES_HEIGHT / 2;


    private final Ship ship;
    private final Texture shipTexture;
    private final Animation<TextureRegion> cruiseAnimation;
    private final TargetActor targetActor;
    private boolean target = false;
    private float stateTime = 0;

    public ShipActor(Ship ship, TargetActor targetActor) {
        this.ship = ship;
        this.targetActor = targetActor;
        shipTexture = new Texture(Gdx.files.internal("sprite/ship-cruise.png"));
        TextureRegion[][] ships = TextureRegion.split(shipTexture, SHIP_PIXEL_WIDTH, SHIP_PIXEL_HEIGHT);
        TextureRegion[] shipCruiseSheet = new TextureRegion[]{
            ships[0][0], ships[0][1], ships[0][2]
        };
        cruiseAnimation = new Animation<>(0.4f, shipCruiseSheet);

    }

    public void setTarget(boolean target) {
        this.target = target;
    }

    public void dispose() {
        shipTexture.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(cruiseAnimation.getKeyFrame(stateTime, true), getX(), getY(), getOriginX(), getOriginY(),
            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation()
        );
    }

    @Override
    public void act(float delta) {
        if (target) {
            targetActor.setPosition(
                ship.getPosition().getX() - TargetActor.OFF_SET_X,
                ship.getPosition().getY() - TargetActor.OFF_SET_Y
            );
            targetActor.setSize(TargetActor.SHIP_TILES_WIDTH, TargetActor.SHIP_TILES_HEIGHT);
        }
        setPosition(ship.getPosition().getX() - OFF_SET_X, ship.getPosition().getY() - OFF_SET_Y);
        setOrigin(OFF_SET_X, OFF_SET_Y);
        setWidth(SHIP_TILES_WIDTH);
        setHeight(SHIP_TILES_HEIGHT);
        setScale(1, 1);
        setRotation(getSpriteRotation(ship.getDirection()));
        super.act(delta);
        stateTime += delta;
    }

    private int getSpriteRotation(Direction direction) {
        switch (direction) {
            case LEFT:
                return 270;
            case TOP:
                return 180;
            case RIGHT:
                return 90;
            default:
                return 0;
        }
    }
}
