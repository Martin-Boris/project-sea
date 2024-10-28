package com.bmrt.projectsea.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bmrt.projectsea.GameTime;
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
    private final Animation<TextureRegion> cruiseAnimation;
    private final ShootRendering portShootingRendering;
    private final ShootRendering starboardShootingRendering;

    public ShipActor(Ship ship, Texture shipTexture) {
        this.ship = ship;
        setOrigin(OFF_SET_X, OFF_SET_Y);
        setWidth(SHIP_TILES_WIDTH);
        setHeight(SHIP_TILES_HEIGHT);
        setScale(1, 1);

        TextureRegion[][] ships = TextureRegion.split(shipTexture, SHIP_PIXEL_WIDTH, SHIP_PIXEL_HEIGHT);

        TextureRegion[] shipCruiseSheet = new TextureRegion[]{
            ships[0][0], ships[0][1], ships[0][2]
        };
        cruiseAnimation = new Animation<>(0.4f, shipCruiseSheet);

        TextureRegion[] portShootingSheet = new TextureRegion[]{
            ships[1][0], ships[1][1], ships[1][2], ships[1][3], ships[1][4], ships[1][5]
        };
        portShootingRendering = new ShootRendering(false, 0, new Animation<>(0.1f, portShootingSheet));

        TextureRegion[] starboardShootingSheet = new TextureRegion[]{
            ships[2][0], ships[2][1], ships[2][2], ships[2][3], ships[2][4], ships[2][5]
        };
        starboardShootingRendering = new ShootRendering(false, 0, new Animation<>(0.1f, starboardShootingSheet));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (ship.isSunk()) {
            return;
        }
        if (portShootingRendering.isRunning()) {
            batch.draw(portShootingRendering.getFrame(), getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        if (starboardShootingRendering.isRunning()) {
            batch.draw(starboardShootingRendering.getFrame(), getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                getHeight(), getScaleX(), getScaleY(), getRotation());
        }
        batch.draw(cruiseAnimation.getKeyFrame(GameTime.getCurrentTime(), true), getX(), getY(), getOriginX(),
            getOriginY(),
            getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation()
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(ship.getPosition().getX() - OFF_SET_X, ship.getPosition().getY() - OFF_SET_Y);
        setRotation(getSpriteRotation(ship.getDirection()));
        portShootingRendering.update(delta);
        starboardShootingRendering.update(delta);
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

    public void triggerPortShoot() {
        portShootingRendering.start();
    }

    public void triggerStarboardShoot() {
        starboardShootingRendering.start();
    }

    public Ship getShip() {
        return ship;
    }
}
