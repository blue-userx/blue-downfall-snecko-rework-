package automaton.powers;

import automaton.actions.AddToFuncAction;
import automaton.cardmods.CardEffectsCardMod;
import automaton.cards.FunctionCard;
import automaton.vfx.FineTuningEffect;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DefaultPower extends AbstractAutomatonPower implements NonStackablePower, OnCompilePower {
    public static final String NAME = "Default";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public DefaultPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
        canGoNegative = false;
        updateDescription();
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
            if (function instanceof FunctionCard) {
                for (int i = 0; i < amount; i++) {
                    //AbstractDungeon.effectList.add(new FineTuningEffect(function));
                    for (AbstractCardModifier m : CardModifierManager.getModifiers(function, CardEffectsCardMod.ID)) {
                        if (m instanceof CardEffectsCardMod) {
                            ((CardEffectsCardMod) m).stored().fineTunebutnomagic(false);
                        }
                    }
                }
            }
    }

    @Override
    public void updateDescription() {
        description = amount == 1 ? DESCRIPTIONS[0] : DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
