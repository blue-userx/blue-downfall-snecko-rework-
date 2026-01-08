package donudeca.cards.cardvars;

import com.megacrit.cardcrawl.cards.AbstractCard;
import donudeca.cards.AbstractDonuDecaCard;

import static donudeca.DonuDecaMod.makeID;

public class SecondDamage extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("sd");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractDonuDecaCard) {
            return ((AbstractDonuDecaCard) card).isSecondDamageModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractDonuDecaCard) {
            ((AbstractDonuDecaCard) card).isSecondDamageModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractDonuDecaCard) {
            return ((AbstractDonuDecaCard) card).secondDamage;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractDonuDecaCard) {
            return ((AbstractDonuDecaCard) card).baseSecondDamage;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractDonuDecaCard) {
            return ((AbstractDonuDecaCard) card).upgradedSecondDamage;
        }
        return false;
    }
}