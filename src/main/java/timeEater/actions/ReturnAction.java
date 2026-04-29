package timeEater.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

//more vacant code from laugic
public class ReturnAction extends AbstractGameAction {
    public static final String[] TEXT = AbstractPlayer.uiStrings.TEXT;

    private float startingDuration;

    public int amount;

    public boolean random;

    private AbstractCard ignoredCard;

    public ReturnAction(int amount) {
        this(amount, (AbstractCard)null);
    }

    public ReturnAction(int amount, AbstractCard ignoreCard) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.amount = amount;
        this.ignoredCard = ignoreCard;
    }

    public void update() {
        if (AbstractDungeon.player != null)
            while (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE && AbstractDungeon.player.discardPile.size() > 0 && this.amount > 0) {
                AbstractCard card = topPlayable(AbstractDungeon.player.discardPile);
                if (card != null) {
                    AbstractDungeon.player.discardPile.removeCard(card);
                    addToTop((AbstractGameAction)new VFXAction((AbstractCreature)AbstractDungeon.player, (AbstractGameEffect)new ShowCardAndMillEffect(card, AbstractDungeon.player.hand, AbstractDungeon.player.discardPile), Settings.ACTION_DUR_XFAST, true));
                }
                this.amount--;
            }
        this.isDone = true;
    }

    private AbstractCard topPlayable(CardGroup cardGroup) {
        AbstractCard card = null;
        if (cardGroup.group.size() > 0) {
            int i = 1;
            while (i <= cardGroup.group.size()) {
                if (((AbstractCard)cardGroup.group.get(cardGroup.group.size() - i)).cost > -2 && (this.ignoredCard == null || !((AbstractCard)cardGroup.group.get(cardGroup.group.size() - i)).equals(this.ignoredCard))) {
                    card = cardGroup.group.get(cardGroup.group.size() - i);
                    i = cardGroup.group.size();
                }
                i++;
            }
        }
        return card;
    }
}
