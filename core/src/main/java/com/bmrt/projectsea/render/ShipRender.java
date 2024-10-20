package com.bmrt.projectsea.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Vector;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static com.bmrt.projectsea.ProjectSeaMain.UNIT;

public class ShipRender {

    private final Texture shipTexture;
    private final Texture targetTexture;
    private final TextureRegion targetTextureRegion;
    private final Animation<TextureRegion> cruiseAnimation;

    public ShipRender() {
        shipTexture = new Texture(Gdx.files.internal("sprite/ship-cruise.png"));
        int shipPixelWidth = 32;
        int shipPixelHeight = 64;
        TextureRegion[][] ships = TextureRegion.split(shipTexture, shipPixelWidth, shipPixelHeight);
        TextureRegion[] shipCruiseSheet = new TextureRegion[]{
            ships[0][0], ships[0][1], ships[0][2]
        };
        cruiseAnimation = new Animation<>(0.5f, shipCruiseSheet);

        targetTexture = new Texture(Gdx.files.internal("sprite/target.png"));
        targetTextureRegion = new TextureRegion(targetTexture,0,0,48,48);
    }

    public void dispose() {
        shipTexture.dispose();
        targetTexture.dispose();
    }

    public void draw(float stateTime, Batch orthoBatch, Vector shipPosition, Direction direction) {
        int shipPixelWidth = 32;
        float shipTilesWidth = UNIT * shipPixelWidth;
        int shipPixelHeight = 64;
        float shipTilesHeight = UNIT * shipPixelHeight;
        float offSetX = shipTilesWidth / 2;
        float offSetY = shipTilesHeight / 2;
        orthoBatch.draw(targetTexture,shipPosition.getX()-24*UNIT,shipPosition.getY()-24*UNIT, 48*UNIT,48*UNIT);
        TextureRegion currentShipFrame = cruiseAnimation.getKeyFrame(stateTime, true);
        orthoBatch.draw(currentShipFrame,
            shipPosition.getX() - offSetX,
            shipPosition.getY() - offSetY,
            offSetX,
            offSetY,
            shipTilesWidth,
            shipTilesHeight,
            1,
            1,
            getSpriteRotation(direction));
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
