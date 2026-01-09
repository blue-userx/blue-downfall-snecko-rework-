package donudeca.actions;

import awakenedOne.patches.MoonTalismanPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.TransformCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import donudeca.cards.AbstractDonuDecaCard;
import guardian.patches.BottledStasisPatch;
import sneckomod.patches.BottledD8Patch;


//m10 robot code
public class SwapCardsAction extends AbstractGameAction {
    private AbstractCard toReplace;

    private AbstractCard newCard;

    public SwapCardsAction(AbstractCard toReplace, AbstractCard newCard) {
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.duration = Settings.ACTION_DUR_MED;
        this.toReplace = toReplace;
        this.newCard = newCard;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int index = 0;
        boolean found = false;
        for (AbstractCard card : p.hand.group) {
            if (card == this.toReplace) {
                found = true;
                break;
            }
            index++;
        }
        if (found && this.toReplace != null) {
            if (this.toReplace instanceof AbstractDonuDecaCard && this.newCard instanceof AbstractDonuDecaCard) {
                ((AbstractDonuDecaCard)this.toReplace).onSwapOut();
                ((AbstractDonuDecaCard)this.newCard).onSwapIn();
            }
            this.newCard.cardsToPreview = this.toReplace.makeStatEquivalentCopy();
            this.newCard.applyPowers();
            this.newCard.cardsToPreview.applyPowers();


            if (((Boolean) BottledStasisPatch.inBottledStasis.get(this.toReplace)).booleanValue())
                BottledStasisPatch.inBottledStasis.set(this.newCard, Boolean.valueOf(true));

            if (((Boolean) BottledStasisPatch.inBottledCode.get(this.toReplace)).booleanValue())
                BottledStasisPatch.inBottledCode.set(this.newCard, Boolean.valueOf(true));

            if (((Boolean) BottledStasisPatch.inBottledAnomaly.get(this.toReplace)).booleanValue())
                BottledStasisPatch.inBottledAnomaly.set(this.newCard, Boolean.valueOf(true));

            if (((Boolean) BottledD8Patch.inD8.get(this.toReplace)).booleanValue())
                BottledD8Patch.inD8.set(this.newCard, Boolean.valueOf(true));

            if (((Boolean) BottledStasisPatch.inStasisEgg.get(this.toReplace)).booleanValue())
                BottledStasisPatch.inStasisEgg.set(this.newCard, Boolean.valueOf(true));

            if (((Boolean) MoonTalismanPatch.inBottleTalisman.get(this.toReplace)).booleanValue())
                MoonTalismanPatch.inBottleTalisman.set(this.newCard, Boolean.valueOf(true));

            if (AbstractDungeon.player.hoveredCard == this.toReplace)
                AbstractDungeon.player.releaseCard();
            AbstractDungeon.actionManager.cardQueue.removeIf(q -> (q.card == this.toReplace));
            addToTop(new UpdateAfterTransformAction(this.newCard));
            addToTop((AbstractGameAction)new TransformCardInHandAction(index, this.newCard));
        }
        this.isDone = true;
    }
}
