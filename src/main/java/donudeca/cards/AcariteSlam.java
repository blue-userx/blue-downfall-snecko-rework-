package donudeca.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static donudeca.DonuDecaMod.*;

public class AcariteSlam extends AbstractDonuDecaCard {
    public final static String ID = makeID("AcariteSlam");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public AcariteSlam() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 12;
        tags.add(SEARING);
        loadJokeCardImage(this, makeBetaCardPath(AcariteSlam.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}