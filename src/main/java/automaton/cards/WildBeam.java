package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.MopUpAction;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

import static automaton.AutomatonMod.makeBetaCardPath;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WildBeam extends AbstractBronzeCard {

    public final static String ID = makeID("WildBeam");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    public WildBeam() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("WildBeam.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SFXAction("ATTACK_WHIFF_2", 0.3F));
        atb(new SFXAction("ATTACK_FAST", 0.2F));
        atb(new VFXAction(new AnimatedSlashEffect(m.hb.cX, m.hb.cY - 30.0F * Settings.scale, 500.0F, 200.0F, 290.0F, 3.0F, Color.FOREST, Color.GREEN)));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        atb(new MopUpAction(m, damage, damageTypeForTurn));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}