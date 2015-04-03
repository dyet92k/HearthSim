package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.card.spellcard.SpellTargetableCard;
import com.hearthsim.event.CharacterFilter;
import com.hearthsim.event.CharacterFilterTargetedSpell;
import com.hearthsim.event.effect.CardEffectOnResolveAoeInterface;
import com.hearthsim.event.effect.CardEffectCharacter;

public class FrostNova extends SpellCard implements CardEffectOnResolveAoeInterface {

    /**
     * This freeze all enemy minions
     */
    public FrostNova() {
        super();
    }

    @Override
    public CardEffectCharacter getAoeEffect() { return CardEffectCharacter.FREEZE; }

    @Override
    public CharacterFilter getAoeFilter() {
        return CharacterFilter.ENEMY_MINIONS;
    }
}
