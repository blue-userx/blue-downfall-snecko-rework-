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

public class IronDouble extends AbstractDonuDecaCard {
    public final static String ID = makeID("IronDouble");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public IronDouble() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 5;
        this.baseBlock = 5;
        tags.add(FLIP);
        this.cardsToPreview = new TwiceSlice();
        loadJokeCardImage(this, makeBetaCardPath(IronDouble.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new WaitAction(0.1F));
        if (p != null && m != null) {
            this.addToBot(new VFXAction(new IronWaveEffectGreen(p.hb.cX, p.hb.cY, m.hb.cX), 0.5F));
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}