package automaton.powers;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.actions.StashFromHandAction;
import automaton.cards.AbstractBronzeCard;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import guardian.actions.PlaceCardsInHandIntoStasisAction;

import static automaton.FunctionHelper.WITH_DELIMITER;

public class CleanCodePower extends AbstractAutomatonPower {
    public static final String NAME = "CleanCode";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public CleanCodePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && !AbstractDungeon.player.hand.isEmpty()) {
            this.addToBot(new StashFromHandAction(this.owner, this.amount, true));}

    }
}
