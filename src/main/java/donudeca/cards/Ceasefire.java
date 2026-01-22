package donudeca.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static collector.util.Wiz.atb;
import static collector.util.Wiz.forAllMonstersLiving;
import static donudeca.DonuDecaMod.*;
import static hermit.util.Wiz.applyToSelf;

public class Ceasefire extends AbstractDonuDecaCard {
    public final static String ID = makeID("Ceasefire");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public Ceasefire() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
        this.baseMagicNumber = this.magicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath(Ceasefire.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        forAllMonstersLiving(q -> atb(new GainBlockAction(q, block)));
        applyToSelf(new DrawCardNextTurnPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}