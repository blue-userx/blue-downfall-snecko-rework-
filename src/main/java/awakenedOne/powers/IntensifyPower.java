package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IntensifyPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = IntensifyPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public IntensifyPower(int amount) {
        super(NAME, PowerType.BUFF, true, AbstractDungeon.player, null, amount);
    }

    //public void onUseCard(AbstractCard card, UseCardAction action) {
    //        if (!card.purgeOnUse && this.amount > 0 && (card instanceof AbstractSpellCard)) {
    //            this.flash();
    //            AbstractMonster m = null;
    //            if (action.target != null) {
    //                m = (AbstractMonster) action.target;
    //            }
    //
    //            AbstractCard tmp = card.makeSameInstanceOf();
    //            AbstractDungeon.player.limbo.addToBottom(tmp);
    //            tmp.current_x = card.current_x;
    //            tmp.current_y = card.current_y;
    //            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
    //            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
    //            if (m != null) {
    //                tmp.calculateCardDamage(m);
    //            }
    //
    //            tmp.purgeOnUse = true;
    //            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
    //            --this.amount;
    //            updateDescription();
    //            if (this.amount == 0) {
    //                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
    //            }
    //        }
    //
    //    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            --this.amount;
            updateDescription();
            if (this.amount == 0) {
                this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
            }
        }
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

}