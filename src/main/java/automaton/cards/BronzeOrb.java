package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.AddToFuncAction;
import automaton.cardmods.EncodeMod;
import automaton.powers.ReturnPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;
import guardian.vfx.BronzeOrbEffect;

import java.util.ArrayList;

import static automaton.AutomatonMod.makeBetaCardPath;

public class BronzeOrb extends AbstractBronzeCard {

    public final static String ID = makeID("BronzeOrb");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 3;

    public BronzeOrb() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;

        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("BronzeOrb.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BronzeOrbEffect(p, m), 0.5F));
      //  blck();
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        this.addToBot(new ApplyPowerAction(p, p, new ReturnPower(1), 1));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
      //  upgradeBlock(UPG_BLOCK);
    }
}