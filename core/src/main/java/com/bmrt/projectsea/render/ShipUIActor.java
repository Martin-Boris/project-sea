package com.bmrt.projectsea.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;

public class ShipUIActor extends Actor {

    private static final int HP_ASSET_WIDTH = 4;
    private static final int HP_ASSET_HEIGHT = 2;


    private final BitmapFont font;
    private final Ship ship;
    private final Viewport viewport;
    private final Vector2 tmp = new Vector2();
    private final GlyphLayout glyphLayout;
    private final NinePatch healthBarGreen;
    private final NinePatch healthBarYellow;
    private final NinePatch healthBarRed;

    public ShipUIActor(Ship ship, Viewport viewport, BitmapFont font, Texture healthBarTexture) {
        this.font = font;
        this.ship = ship;
        this.viewport = viewport;
        this.glyphLayout = new GlyphLayout(font, ship.getName());
        TextureRegion[][] healthBars = TextureRegion.split(healthBarTexture, HP_ASSET_WIDTH, HP_ASSET_HEIGHT);
        this.healthBarGreen = new NinePatch(healthBars[0][0], 0, 0, 0, 0);
        this.healthBarYellow = new NinePatch(healthBars[0][1], 0, 0, 0, 0);
        this.healthBarRed = new NinePatch(healthBars[0][2], 0, 0, 0, 0);
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (ship.isSunk()) {
            return;
        }
        tmp.x = ship.getPosition().getX();
        tmp.y = ship.getPosition().getY();
        viewport.project(tmp);
        float width = (ship.getHealthPoint() / Ship.MAX_HP) * 100;
        if (ship.getDirection().equals(Direction.TOP) || ship.getDirection().equals(Direction.BOT)) {
            font.draw(batch, glyphLayout, tmp.x - (glyphLayout.width / 2), tmp.y - 70);
            getHealthBar().draw(batch, tmp.x - width / 2, tmp.y - 90, width, 4);
        } else if (ship.getDirection().equals(Direction.LEFT)) {
            font.draw(batch, glyphLayout, tmp.x - (glyphLayout.width / 2) + 10, tmp.y - 40);
            getHealthBar().draw(batch, tmp.x + 10 - width / 2, tmp.y - 60, width, 4);
        } else if (ship.getDirection().equals(Direction.RIGHT)) {
            font.draw(batch, glyphLayout, tmp.x - (glyphLayout.width / 2) - 10, tmp.y - 40);
            getHealthBar().draw(batch, tmp.x - 10 - width / 2, tmp.y - 60, width, 4);
        }
    }

    private NinePatch getHealthBar() {
        if (ship.getPercentHp() >= 0.8f) {
            return healthBarGreen;
        }
        if (ship.getPercentHp() >= 0.4f) {
            return healthBarYellow;
        }
        return healthBarRed;
    }
}
