package automaton.cards;

import automaton.AutomatonMod;
import automaton.BronzeOrbStash;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Goto extends AbstractBronzeCard {

    public final static String ID = makeID("Goto");

    //stupid intellij stuff skill, self, basic

    public Goto() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 8;
        baseMagicNumber = magicNumber = 1;
        //thisEncodes();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Goto.png"));
    }

    public static void becomeFree() {
        for (AbstractCard q : AbstractDungeon.player.drawPile.group) {
            if (q instanceof Goto) {
                q.freeToPlayOnce = true;
            }
        }
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q instanceof Goto) {
                q.freeToPlayOnce = true;
            }
        }
        for (AbstractCard q : AbstractDungeon.player.discardPile.group) {
            if (q instanceof Goto) {
                q.freeToPlayOnce = true;
            }
        }
        for (AbstractCard q : BronzeOrbStash.combatstashpile.group) {
            if (q instanceof Goto) {
                q.freeToPlayOnce = true;
            }
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DrawCardAction(magicNumber));
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            applyToSelf(new DrawCardNextTurnPower(AbstractDungeon.player, magicNumber));
        }
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}