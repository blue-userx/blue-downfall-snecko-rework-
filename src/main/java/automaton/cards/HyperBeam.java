package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.HandFillErrorAction;
import automaton.actions.PlaceActualCardIntoStashAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import gremlin.actions.ShivPerCardPlayedAction;

import static automaton.AutomatonMod.makeBetaCardPath;

public class HyperBeam extends AbstractBronzeCard {

    public final static String ID = makeID("HyperBeam");

    //stupid intellij stuff attack, all_enemy, rare

    private static final int DAMAGE = 18;
    private static final int UPG_DAMAGE = 10;

    public HyperBeam() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        //selfRetain = true;
      //  exhaust = true;
        baseAuto = auto = 3;
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new VoidCard();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("HyperBeam.png"));
    }

    /*
    public void onRetained() {
        this.addToBot(new ReduceCostAction(this));
    }

     */

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((!monster.isDead) && (!monster.isDying)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber));
            }
        }
        //AbstractDungeon.actionManager.addToBottom(new HandFillErrorAction(true));
        //atb(new MakeTempCardInDiscardAction(new Slimed(), auto));
        for (int i = 0; i < magicNumber; i++) {
            atb(new PlaceActualCardIntoStashAction(new VoidCard(), null, true));
        }
    }

    public void upp() {
        upgradeDamage(6);
        upgradeMagicNumber(1);
    }
}