package donudeca.cards;

import automaton.actions.EasyXCostAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LockOnPower;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static donudeca.DonuDecaMod.*;
import static hermit.util.Wiz.atb;

public class Execution extends AbstractDonuDecaCard {
    public final static String ID = makeID("Execution");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Execution() {
        super(ID, -1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 0;
        loadJokeCardImage(this, makeBetaCardPath(Execution.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            atb(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        dmg(m, AbstractGameAction.AttackEffect.NONE);

        atb(new EasyXCostAction(this, (effect, params) -> {
            this.addToBot(new ApplyPowerAction(m, p, new LockOnPower(m, effect + params[0]), effect + params[0]));
            return true;
        }, magicNumber));

    }

    @Override
    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}