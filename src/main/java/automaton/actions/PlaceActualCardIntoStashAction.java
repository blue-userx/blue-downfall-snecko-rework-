package automaton.actions;

import automaton.BronzeOrbStash;
import automaton.powers.BurnOutPower;
import awakenedOne.patches.OnCreateCardSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

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
        if (source == null && (BronzeOrbStash.combatstashpile.size() < 5)) {
            OnCreateCardSubscriber.onCreateCard(card);
        }
        if (BronzeOrbStash.combatstashpile.size() < 5) {
            if (AbstractDungeon.player.hasPower(BurnOutPower.POWER_ID) && (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE)) {
                AbstractDungeon.player.limbo.addToTop(card);
                atb(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                AbstractDungeon.player.getPower(BurnOutPower.POWER_ID).onSpecificTrigger();
                dontstash = true;
            }
        }
        if (!dontstash && (BronzeOrbStash.combatstashpile.size() < 5)) {
            BronzeOrbStash.combatstashpile.addToRandomSpot(card);
        }

        if (!dontstash && (BronzeOrbStash.combatstashpile.size() > 5)) {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, CardCrawlGame.languagePack.getUIString("bronze:FullStash").TEXT[0], true));
            if (source == null) {
                AbstractDungeon.player.discardPile.addToTop(card);
            }
        }

        this.isDone = true;
    }
}
