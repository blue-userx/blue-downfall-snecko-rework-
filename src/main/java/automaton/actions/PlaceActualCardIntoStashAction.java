package automaton.actions;

import automaton.BronzeOrbStash;
import automaton.powers.BurnOutPower;
import awakenedOne.patches.OnCreateCardSubscriber;
import awakenedOne.powers.SongOfSorrowPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.util.Wiz.atb;


public class PlaceActualCardIntoStashAction extends AbstractGameAction {
    private final AbstractCard card;
    private final CardGroup source;
    private boolean skipWait;
    private boolean dontstash;
    private final boolean hadRetain;

    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;

    public PlaceActualCardIntoStashAction(AbstractCard card, CardGroup source) {
        this.card = card;
        this.source = source;
        this.actionType = ActionType.DAMAGE;
        skipWait = false;
        dontstash = false;
        hadRetain = card.retain;
    }

    public PlaceActualCardIntoStashAction(AbstractCard card) {
        this(card, null);
    }

    public PlaceActualCardIntoStashAction(AbstractCard card, CardGroup source, boolean skipWait) {
        this(card, source);
        this.skipWait = skipWait;
        dontstash = false;
    }

    public void update() {
        dontstash = false;
        if (source != null) {
            source.removeCard(card);
        }
        if (source == null) {
            OnCreateCardSubscriber.onCreateCard(card);
        }
        if (AbstractDungeon.player.hasPower(BurnOutPower.POWER_ID) && (card.type == AbstractCard.CardType.STATUS)) {
            AbstractDungeon.player.limbo.addToTop(card);
            atb(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
            AbstractDungeon.player.getPower(BurnOutPower.POWER_ID).onSpecificTrigger();
            dontstash = true;
        }
        if (!dontstash) {
            BronzeOrbStash.combatstashpile.addToRandomSpot(card);
        }
        this.isDone = true;
    }
}
