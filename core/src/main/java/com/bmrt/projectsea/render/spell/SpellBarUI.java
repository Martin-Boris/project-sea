package com.bmrt.projectsea.render.spell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bmrt.projectsea.domain.ActionType;

import java.util.ArrayList;

public class SpellBarUI extends Table {
    public static final int SPELl_SPRITE_WIDTH = 48;
    public static final int SPELL_SPRITE_HEIGHT = 48;


    private final ArrayList<SpellButton> spells;

    public SpellBarUI(Texture canonSpell, BitmapFont font) {
        super();
        this.spells = new ArrayList<>();
        TextureRegion[][] spellsIcons = TextureRegion.split(canonSpell, SPELl_SPRITE_WIDTH, SPELL_SPRITE_HEIGHT);
        TextureRegion canonSpellPort = spellsIcons[0][0];
        TextureRegion canonSpellStarboard = spellsIcons[0][1];
        TextureRegion canonSpellPortDisable = spellsIcons[1][0];
        TextureRegion canonSpellStarboardDisable = spellsIcons[1][1];


        /* PORT CANON SHOOT SPELL BUTTON */
        ImageButton.ImageButtonStyle portStyle = new ImageButton.ImageButtonStyle();
        portStyle.imageUp = new TextureRegionDrawable(canonSpellPort);
        portStyle.imageDown = new TextureRegionDrawable(canonSpellPortDisable);
        portStyle.imageDisabled = new TextureRegionDrawable(canonSpellPortDisable);
        SpellButton canonShootPortButton = new SpellButton(portStyle, 2, "Q", font, SPELl_SPRITE_WIDTH,
            SPELL_SPRITE_HEIGHT, (float) Gdx.graphics.getWidth() / 2 - SPELl_SPRITE_WIDTH - 1, 2, true,
            ActionType.PORT_SHOOT);
        this.spells.add(canonShootPortButton);
        addActor(canonShootPortButton);

        /* STARBOARD CANON SHOOT SPELL BUTTON */
        ImageButton.ImageButtonStyle starboardStyle = new ImageButton.ImageButtonStyle();
        starboardStyle.imageUp = new TextureRegionDrawable(canonSpellStarboard);
        starboardStyle.imageDown = new TextureRegionDrawable(canonSpellStarboardDisable);
        starboardStyle.imageDisabled = new TextureRegionDrawable(canonSpellStarboardDisable);
        SpellButton canonShootStarboardButton = new SpellButton(starboardStyle, 2, "E", font, SPELl_SPRITE_WIDTH,
            SPELL_SPRITE_HEIGHT, (float) Gdx.graphics.getWidth() / 2 + 1, 2, true, ActionType.STARBOARD_SHOOT);
        this.spells.add(canonShootStarboardButton);
        addActor(canonShootStarboardButton);
    }

    public void update(boolean disableSpell) {
        for (SpellButton spell : spells) {
            spell.setDisabled(disableSpell);
            spell.update();
        }
    }

    public void triggerPortShoot() {
        spells.get(0).fire(new ChangeListener.ChangeEvent());
    }

    public void triggerStarboardShoot() {
        spells.get(1).fire(new ChangeListener.ChangeEvent());
    }
}
