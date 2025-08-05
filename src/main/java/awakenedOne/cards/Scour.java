package awakenedOne.cards;

import awakenedOne.powers.ManaburnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class Scour extends AbstractAwakenedCard {
    public final static String ID = makeID(Scour.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    //Manaburn

    //rework to pressure points

    public Scour() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 4;
        loadJokeCardImage(this, makeBetaCardPath(Scour.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        this.addToBot(new ApplyPowerAction(m, p, new ManaburnPower(m, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(2);
    }
}