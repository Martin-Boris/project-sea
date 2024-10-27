package com.bmrt.projectsea.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import static com.bmrt.projectsea.ProjectSeaMain.UNIT;

public class TargetActor extends Actor {

    public final static int SHIP_PIXEL_WIDTH = 48;
    public final static int SHIP_PIXEL_HEIGHT = 48;
    public final static float SHIP_TILES_WIDTH = UNIT * SHIP_PIXEL_WIDTH;
    public final static float OFF_SET_X = SHIP_TILES_WIDTH / 2;
    public final static float SHIP_TILES_HEIGHT = UNIT * SHIP_PIXEL_HEIGHT;
    public final static float OFF_SET_Y = SHIP_TILES_HEIGHT / 2;

    private final Texture targetTexture;

    private boolean visible;

    public TargetActor(Texture targetTexture) {
        this.targetTexture = targetTexture;
        this.visible = false;
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (visible) {
            batch.draw(targetTexture, getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
