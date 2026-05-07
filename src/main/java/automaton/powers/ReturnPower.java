package automaton.powers;

import automaton.actions.PlaceActualCardIntoStashAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.atb;

public class ReturnPower extends AbstractAutomatonPower {
    public static final String NAME = "Return";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public ReturnPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
            if (card.type != AbstractCard.CardType.POWER) {
                this.flash();
            }
            if (AbstractDungeon.player.limbo.group.contains(card)) {
            atb(new PlaceActualCardIntoStashAction(card, AbstractDungeon.player.limbo, true));
            }
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
        }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }
}
