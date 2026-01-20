package donudeca.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static donudeca.DonuDecaMod.*;

public class Weigh extends AbstractDonuDecaCard {
    public final static String ID = makeID("Identify");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Weigh() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 6;
        this.baseMagicNumber = this.magicNumber = 1;
        tags.add(FLIP);
        this.cardsToPreview = new Identify();
        loadJokeCardImage(this, makeBetaCardPath(Weigh.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}