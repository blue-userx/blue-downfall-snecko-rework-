package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.StashFromHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static automaton.AutomatonMod.makeBetaCardPath;

public class DelayedSlice extends AbstractBronzeCard {

    public final static String ID = makeID("DelayedSlice");

    //stupid intellij stuff attack, all_enemy, common

    //Robo-Chop

    public DelayedSlice() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        baseDamage = 8;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("DelayedSlice.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        this.addToBot(new DrawCardAction(p, this.magicNumber));
        this.addToBot(new StashFromHandAction(p, 1, false));
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}