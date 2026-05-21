package automaton.cards;

import automaton.AutomatonMod;
import automaton.BronzeOrbStash;
import automaton.actions.StashFromHandAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import guardian.actions.PlaceCardsInHandIntoStasisAction;
import utilityClasses.Later.LaterAction;

import static automaton.AutomatonMod.makeBetaCardPath;
import static collector.util.Wiz.makeInHandTop;

public class DelayedGuard extends AbstractBronzeCard {

    public final static String ID = makeID("DelayedGuard");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 4;

    public DelayedGuard() {
        super(ID, 2, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = BLOCK;
        //thisEncodes();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("DelayedGuard.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.addToBot(new StashFromHandAction(p, 1, false));
        //makeInHand(new Slimed());
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}