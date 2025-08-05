package awakenedOne.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.util.Wiz.atb;

public class FourthDimension extends AbstractAwakenedCard {
    public final static String ID = makeID(FourthDimension.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public FourthDimension() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.exhaust = true;
        baseMagicNumber = magicNumber = 3;
        this.tags.add(CardTags.HEALING);
        loadJokeCardImage(this, makeBetaCardPath(FourthDimension.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(1, FourthDimension.class.getSimpleName(), (cards) -> {
            for (AbstractCard q : cards) {
                AbstractCard q2 = q.makeStatEquivalentCopy();
                //q2.updateCost(-99);
                for (int i = 0; i < magicNumber; i++) {
                    AbstractCard card = q2.makeStatEquivalentCopy();
                    spellCards.add(card);
                }
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}