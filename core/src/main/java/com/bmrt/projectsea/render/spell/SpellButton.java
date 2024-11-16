package com.bmrt.projectsea.render.spell;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.bmrt.projectsea.domain.ActionType;
import com.bmrt.projectsea.domain.Cooldown;

public class SpellButton extends ImageButton {

    private final CooldownTimer cooldownTimer;
    private final Cooldown cooldown;
    private final String key;
    private final BitmapFont font;
    private final ActionType actionType;

    public SpellButton(ImageButtonStyle style, Cooldown cooldown, String key, BitmapFont font, int width, int height,
                       float x, float y, boolean disabled, ActionType actionType) {
        super(style);
        setWidth(width);
        setHeight(height);
        setScale(1, 1);
        setOrigin((float) width / 2, (float) height / 2);
        setPosition(x, y);
        setDisabled(disabled);
        this.cooldown = cooldown;
        this.key = key;
        this.font = font;
        this.cooldownTimer = new CooldownTimer(true, 48, 48);
        this.actionType = actionType;
        cooldownTimer.setPosition(0, 0);
        cooldownTimer.setColor(Color.WHITE);

        addActor(this.cooldownTimer);
    }


    public void update() {
        if (!cooldown.isReady()) {
            cooldownTimer.setVisible(true);
            cooldownTimer.update(cooldown.getRemainingCooldownPercentage());
        } else {
            cooldownTimer.setVisible(false);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.draw(batch, key, getX() + getWidth() - 15, getY() + getHeight() - 5);
    }

    public ActionType getActionType() {
        return actionType;
    }
}
