package donudeca.cards.cardvars;

import com.megacrit.cardcrawl.cards.AbstractCard;
import donudeca.cards.AbstractDonuDecaCard;

import static donudeca.DonuDecaMod.makeID;

public class SecondMagicNumber extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("m2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractDonuDecaCard) {
            return ((AbstractDonuDecaCard) card).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractDonuDecaCard) {
            return ((AbstractDonuDecaCard) card).secondMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractDonuDecaCard) {
            ((AbstractDonuDecaCard) card).isSecondMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractDonuDecaCard) {
            return ((AbstractDonuDecaCard) card).baseSecondMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractDonuDecaCard) {
            return ((AbstractDonuDecaCard) card).upgradedSecondMagic;
        }
        return false;
    }
}