package donudeca.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static donudeca.DonuDecaMod.*;

public class Identify extends AbstractDonuDecaCard {
    public final static String ID = makeID("Identify");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Identify() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        this.baseMagicNumber = this.magicNumber = 1;
        tags.add(FLIP);
        this.cardsToPreview = new Weigh();
        loadJokeCardImage(this, makeBetaCardPath(Identify.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}