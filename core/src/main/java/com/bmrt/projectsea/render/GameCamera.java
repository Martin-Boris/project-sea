package com.bmrt.projectsea.render;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameCamera extends OrthographicCamera {

    private final int mapWidth;
    private final int mapHeight;

    public GameCamera(float width, float height, int mapWidth, int mapHeight) {
        super();
        setToOrtho(false, width, height);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public void update(float x, float y) {
        if (x - viewportWidth / 2 < 0) {
            position.x = viewportWidth / 2;
        } else if (x + viewportWidth / 2 > mapWidth) {
            position.x = mapWidth - viewportWidth / 2;
        } else {
            position.x = x;
        }
        if (y - viewportHeight / 2 < 0) {
            position.y = viewportHeight / 2;
        } else if (y + viewportHeight / 2 > mapHeight) {
            position.y = mapHeight - viewportHeight / 2;
        } else {
            position.y = y;
        }
        super.update();
    }
}
