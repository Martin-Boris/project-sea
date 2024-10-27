package com.bmrt.projectsea.render.spell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bmrt.projectsea.GameTime;
import com.bmrt.projectsea.render.ShipActor;

import java.util.ArrayList;

public class SpellBarUI extends Table {
    public static final int SPELl_SPRITE_WIDTH = 48;
    public static final int SPELL_SPRITE_HEIGHT = 48;


    private final ArrayList<SpellButton> spells;


    public SpellBarUI(Texture canonSpell, BitmapFont font, ShipActor myShipActor) {
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
            SPELL_SPRITE_HEIGHT, (float) Gdx.graphics.getWidth() / 2 - SPELl_SPRITE_WIDTH - 1, 2, true);
        this.spells.add(canonShootPortButton);
        addActor(canonShootPortButton);

        canonShootPortButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (!((SpellButton) actor).isOnCooldown()) {
                    //TODO trigger action on domain
                    ((SpellButton) actor).setCooldownTriggerTime(GameTime.getCurrentTime());
                    myShipActor.triggerPortShoot();
                }
            }
        });

        /* STARBOARD CANON SHOOT SPELL BUTTON */
        ImageButton.ImageButtonStyle starboardStyle = new ImageButton.ImageButtonStyle();
        starboardStyle.imageUp = new TextureRegionDrawable(canonSpellStarboard);
        starboardStyle.imageDown = new TextureRegionDrawable(canonSpellStarboardDisable);
        starboardStyle.imageDisabled = new TextureRegionDrawable(canonSpellStarboardDisable);
        SpellButton canonShootStarboardButton = new SpellButton(starboardStyle, 2, "E", font, SPELl_SPRITE_WIDTH,
            SPELL_SPRITE_HEIGHT, (float) Gdx.graphics.getWidth() / 2 + 1, 2, true);
        this.spells.add(canonShootStarboardButton);
        addActor(canonShootStarboardButton);
        canonShootStarboardButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (!((SpellButton) actor).isOnCooldown()) {
                    //TODO trigger action on domain
                    ((SpellButton) actor).setCooldownTriggerTime(GameTime.getCurrentTime());
                    myShipActor.triggerStarboardShoot();
                }
            }
        });
    }

    public void update() {
        spells.forEach(SpellButton::update);
    }

    public void activateSpell() {
        spells.forEach(spellButton -> spellButton.setDisabled(false));
    }

    public void disableSpell() {
        spells.forEach(spellButton -> spellButton.setDisabled(true));
    }
}
