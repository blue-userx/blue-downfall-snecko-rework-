package automaton.actions;

import automaton.BronzeOrbStash;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;


public class PlaceActualCardIntoStashAction extends AbstractGameAction {
    private final AbstractCard card;
    private final CardGroup source;
    private boolean skipWait;
    private final boolean hadRetain;

    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;

    public PlaceActualCardIntoStashAction(AbstractCard card, CardGroup source) {
        this.card = card;
        this.source = source;
        this.actionType = ActionType.DAMAGE;
        skipWait = false;
        hadRetain = card.retain;
        if (source != null && source.type == CardGroup.CardGroupType.HAND)
        {
            card.retain = true;
        }
    }

    public PlaceActualCardIntoStashAction(AbstractCard card) {
        this(card, null);
    }

    public PlaceActualCardIntoStashAction(AbstractCard card, CardGroup source, boolean skipWait) {
        this(card, source);
        this.skipWait = skipWait;
    }

    public void update() {
        source.removeCard(card);
        BronzeOrbStash.combatstashpile.addToRandomSpot(card);
        this.isDone = true;
    }
}
