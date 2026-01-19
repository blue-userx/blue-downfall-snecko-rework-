package donudeca.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class UpgradeSpecificCardAction extends AbstractGameAction {
    private final AbstractCard[] cards;

    public UpgradeSpecificCardAction(AbstractCard... cards) {
        this.cards = cards;
    }

    public void update() {
        for (AbstractCard card : this.cards) {
            if (card.canUpgrade()) {
                card.upgrade();
                card.superFlash();
                card.applyPowers();
            }
        }
        this.isDone = true;
    }
}
