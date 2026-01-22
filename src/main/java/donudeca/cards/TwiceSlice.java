package donudeca.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import donudeca.effects.IronWaveEffectGreen;

import static donudeca.DonuDecaMod.*;

public class TwiceSlice extends AbstractDonuDecaCard {
    public final static String ID = makeID("IronDouble");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public TwiceSlice() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 5;
        tags.add(FLIP);
        this.cardsToPreview = new IronDouble();
        loadJokeCardImage(this, makeBetaCardPath(IronDouble.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}