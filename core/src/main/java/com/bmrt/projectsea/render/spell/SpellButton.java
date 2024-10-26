package com.bmrt.projectsea.render.spell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.bmrt.projectsea.GameTime;

import static com.bmrt.projectsea.ProjectSeaMain.EPSILON;

public class SpellButton extends ImageButton {

    private final CooldownTimer cooldownTimer;
    private final float cooldown;

    private final String key;

    private final BitmapFont font;

    private float cooldownTriggerTime = -Float.MAX_VALUE;

    public SpellButton(ImageButtonStyle style, float cooldown, String key, BitmapFont font) {
        super(style);
        this.cooldown = cooldown;
        this.key = key;
        this.font = font;
        this.cooldownTimer = new CooldownTimer(true, 48, 48);
        cooldownTimer.setPosition(0, 0);
        cooldownTimer.setColor(Color.WHITE);

        addActor(this.cooldownTimer);
    }


    public boolean isOnCooldown() {
        return getRemainingCooldownTime() - EPSILON > 0;
    }

    public float getCooldownTriggerTime() {
        return cooldownTriggerTime;
    }

    public void setCooldownTriggerTime(float cooldownTriggerTime) {
        this.cooldownTriggerTime = cooldownTriggerTime;
    }

    public float getRemainingCooldownTime() {
        return Math.max(0, cooldown - (GameTime.getCurrentTime() - cooldownTriggerTime));
    }

    public float getRemainingCooldownPercentage() {
        if ((cooldown - (GameTime.getCurrentTime() - cooldownTriggerTime)) <= 0) {
            return 0.0f;
        }
        return (cooldown - (GameTime.getCurrentTime() - cooldownTriggerTime)) / cooldown;
    }

    public void update() {
        if (getRemainingCooldownPercentage() - EPSILON >= 0.0f) {
            cooldownTimer.setVisible(true);
            cooldownTimer.update(getRemainingCooldownPercentage());
        } else {
            cooldownTimer.setVisible(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, key, getX() + SpellBarUI.SPELl_SPRITE_WIDTH - 15, getY() + SpellBarUI.SPELL_SPRITE_HEIGHT - 5);
    }
}
