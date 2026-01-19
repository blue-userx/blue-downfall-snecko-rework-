package donudeca.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import donudeca.actions.FutureProofingAction;
import donudeca.effects.ColoredBeamEffect;
import hermit.util.Wiz;

import static donudeca.DonuDecaMod.*;

public class FutureProofing extends AbstractDonuDecaCard {
    public final static String ID = makeID("FutureProofing");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public FutureProofing() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
        this.baseMagicNumber = this.magicNumber = 1;
        this.isMultiDamage = true;
        loadJokeCardImage(this, makeBetaCardPath(FutureProofing.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb((AbstractGameAction)new SFXAction("ATTACK_DEFECT_BEAM"));
        Color c = Color.GREEN.cpy();
        Color c2 = Color.LIME.cpy();
        Wiz.atb((AbstractGameAction)new VFXAction((AbstractGameEffect)new ColoredBeamEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractDungeon.player.flipHorizontal, c, c2), 0.4F));
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        Wiz.atb((AbstractGameAction)new FutureProofingAction(this.magicNumber));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}