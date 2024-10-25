package com.bmrt.projectsea.render;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bmrt.projectsea.domain.Direction;
import com.bmrt.projectsea.domain.Ship;

public class ShipUIActor extends Actor {

    private final BitmapFont font;
    private final Ship myShip;
    private final Viewport viewport;

    private final Vector2 tmp = new Vector2();

    private final GlyphLayout glyphLayout;


    public ShipUIActor(Ship myShip, Viewport viewport, BitmapFont font) {
        this.font = font;
        this.myShip = myShip;
        this.viewport = viewport;
        this.glyphLayout = new GlyphLayout(font, myShip.getName());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        tmp.x = myShip.getPosition().getX();
        tmp.y = myShip.getPosition().getY();
        viewport.project(tmp);
        if (myShip.getDirection().equals(Direction.TOP) || myShip.getDirection().equals(Direction.BOT)) {
            font.draw(batch, glyphLayout, tmp.x - (glyphLayout.width / 2), tmp.y - 70);
        } else if (myShip.getDirection().equals(Direction.LEFT)) {
            font.draw(batch, glyphLayout, tmp.x - (glyphLayout.width / 2) + 10, tmp.y - 40);
        } else if (myShip.getDirection().equals(Direction.RIGHT)) {
            font.draw(batch, glyphLayout, tmp.x - (glyphLayout.width / 2) - 10, tmp.y - 40);
        }
    }
}
