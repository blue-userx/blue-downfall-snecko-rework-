package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.StashFromHandAction;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Fortify extends AbstractBronzeCard {

    public final static String ID = makeID("Fortify");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 8;

    public Fortify() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = 8;
        baseMagicNumber = magicNumber = 2;
        //thisEncodes();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Fortify.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        this.addToBot(new StashFromHandAction(p, BaseMod.MAX_HAND_SIZE, true));
    }


    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
    }
}