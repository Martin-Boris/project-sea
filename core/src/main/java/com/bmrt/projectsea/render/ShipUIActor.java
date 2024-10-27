package com.bmrt.projectsea.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;

public class ShipUIActor extends Actor {

    private final BitmapFont font;
    private final Ship ship;
    private final Viewport viewport;
    private final Vector2 tmp = new Vector2();
    private final GlyphLayout glyphLayout;
    private final NinePatch healthBar;


    public ShipUIActor(Ship ship, Viewport viewport, BitmapFont font, Texture healthBarTexture) {
        this.font = font;
        this.ship = ship;
        this.viewport = viewport;
        this.glyphLayout = new GlyphLayout(font, ship.getName());
        this.healthBar = new NinePatch(healthBarTexture, 0, 0, 4, 4);
        setTouchable(Touchable.disabled);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        tmp.x = ship.getPosition().getX();
        tmp.y = ship.getPosition().getY();
        viewport.project(tmp);
        float width = (ship.getHealthPoint() / Ship.MAX_HP) * 100;
        if (ship.getDirection().equals(Direction.TOP) || ship.getDirection().equals(Direction.BOT)) {
            font.draw(batch, glyphLayout, tmp.x - (glyphLayout.width / 2), tmp.y - 70);
            healthBar.draw(batch, tmp.x - width / 2, tmp.y - 90, width, 4);
        } else if (ship.getDirection().equals(Direction.LEFT)) {
            font.draw(batch, glyphLayout, tmp.x - (glyphLayout.width / 2) + 10, tmp.y - 40);
            healthBar.draw(batch, tmp.x + 10 - width / 2, tmp.y - 60, width, 4);
        } else if (ship.getDirection().equals(Direction.RIGHT)) {
            font.draw(batch, glyphLayout, tmp.x - (glyphLayout.width / 2) - 10, tmp.y - 40);
            healthBar.draw(batch, tmp.x - 10 - width / 2, tmp.y - 60, width, 4);
        }
    }
}
