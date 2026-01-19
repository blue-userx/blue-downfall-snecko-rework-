package donudeca.cards;

import automaton.vfx.FineTuningEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import donudeca.actions.UpgradeSpecificCardAction;
import hermit.util.Wiz;

import java.util.ArrayList;

import static donudeca.DonuDecaMod.makeID;

public class Tinker extends AbstractDonuDecaCard {
    public final static String ID = makeID("Defend");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public Tinker() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        ArrayList<AbstractCard> neighbors = new ArrayList<>();
        if (Wiz.hand().contains((this))) {
            int index = (Wiz.hand()).group.indexOf(this);
            if (index > 0)
                neighbors.add((Wiz.hand()).group.get(index - 1));
            if (index < Wiz.hand().size() - 1)
                neighbors.add((Wiz.hand()).group.get(index + 1));
        }
        for (AbstractCard card : neighbors) {
            if (card.canUpgrade()) {
                AbstractDungeon.effectList.add(new FineTuningEffect(card));
                addToBot(new UpgradeSpecificCardAction(card));
            }
        }
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}