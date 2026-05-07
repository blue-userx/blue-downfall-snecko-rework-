package automaton.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.actions.common.*;

public class HandFillErrorAction extends AbstractGameAction
{
    private final boolean upgraded;

    public HandFillErrorAction(boolean upgraded) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        this.isDone = true;
        int count = 999;
        --count;
        int availableSpace = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
        if (count > availableSpace) {
            count = availableSpace;
        }
        AbstractCard c = new Slimed();
        if (upgraded) {
            c.upgrade();
        }
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c, count));
    }

}

