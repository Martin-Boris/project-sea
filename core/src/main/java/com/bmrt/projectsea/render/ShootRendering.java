package com.bmrt.projectsea.render;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShootRendering {
    private final Animation<TextureRegion> shootAnimation;
    private boolean shooting;
    private float shootingStateTime;

    public ShootRendering(boolean shooting, float shootingStateTime, Animation<TextureRegion> shootAnimation) {
        this.shooting = shooting;
        this.shootingStateTime = shootingStateTime;
        this.shootAnimation = shootAnimation;
    }

    public boolean isRunning() {
        return shooting;
    }

    public float getShootingStateTime() {
        return shootingStateTime;
    }

    public TextureRegion getFrame() {
        TextureRegion keyFrame = shootAnimation.getKeyFrame(shootingStateTime, false);
        if (shootAnimation.isAnimationFinished(shootingStateTime)) {
            reset();
        }
        return keyFrame;
    }

    public void update(float deltaTime) {
        if (shooting) {
            shootingStateTime += deltaTime;
        }
    }

    public void reset() {
        this.shooting = false;
        this.shootingStateTime = 0;
    }

    public void start() {
        this.shooting = true;
        this.shootingStateTime = 0;
    }
}
