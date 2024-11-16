package com.bmrt.projectsea.render.spell;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.bmrt.projectsea.domain.ActionType;
import com.bmrt.projectsea.domain.GameInstance;

public class ChangeSpellButtonListener extends ChangeListener {

    private final GameInstance gameInstance;

    public ChangeSpellButtonListener(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        if (((SpellButton) actor).getActionType().equals(ActionType.PORT_SHOOT)) {
            gameInstance.triggerPortShoot();
        } else if (((SpellButton) actor).getActionType().equals(ActionType.STARBOARD_SHOOT)) {
            gameInstance.triggerStarboardShoot();
        }
    }
}
