package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.FutureProofingAction;
import automaton.vfx.PiercingShotEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhirlingStrike extends AbstractBronzeCard {

    public final static String ID = makeID("WhirlingStrike");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    public WhirlingStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;
        //thisEncodes();
        isMultiDamage = true;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("PiercingShot.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY", .75F, true));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new PiercingShotEffect(p.hb.cX, p.hb.cY, Settings.WIDTH + 100F, p.hb.cY), 0F));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        atb(new FutureProofingAction(magicNumber));

    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(1);
    }
}