package com.bmrt.projectsea.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.bmrt.projectsea.domain.Ship;

import static com.bmrt.projectsea.render.RenderAdapter.UNIT;

public class TargetActor extends Actor {

    public final static int SHIP_PIXEL_WIDTH = 48;
    public final static int SHIP_PIXEL_HEIGHT = 48;
    public final static float SHIP_TILES_WIDTH = UNIT * SHIP_PIXEL_WIDTH;
    public final static float OFF_SET_X = SHIP_TILES_WIDTH / 2;
    public final static float SHIP_TILES_HEIGHT = UNIT * SHIP_PIXEL_HEIGHT;
    public final static float OFF_SET_Y = SHIP_TILES_HEIGHT / 2;

    private final Texture targetTexture;

    private Ship ship;

    private boolean visible;

    public TargetActor(Texture targetTexture, Ship ship) {
        this.targetTexture = targetTexture;
        this.visible = false;
        this.ship = ship;
        setSize(SHIP_TILES_WIDTH, SHIP_TILES_HEIGHT);
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (visible) {
            batch.draw(targetTexture, getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void act(float delta) {
        if (visible) {
            setPosition(
                ship.getPosition().getX() - OFF_SET_X,
                ship.getPosition().getY() - OFF_SET_Y
            );
            if (ship.isSunk()) {
                visible = false;
                ship = null;
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setTarget(Ship ship) {
        this.visible = true;
        this.ship = ship;
    }

    public void removeTarget() {
        this.visible = false;
        this.ship = null;
    }

    public Ship getShip() {
        return ship;
    }
}
