package donudeca.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.BarrageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.vfx.SmallLaserEffectColored;

import static donudeca.DonuDecaMod.*;

public class BarrageDonu extends AbstractDonuDecaCard {
    public final static String ID = makeID("BarrageDonu");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public BarrageDonu() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
        loadJokeCardImage(this, makeBetaCardPath(BarrageDonu.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new BarrageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}