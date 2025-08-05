package sneckomod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import expansioncontent.expansionContentMod;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class BabySneckoMuddleAction extends AbstractGameAction {

        @Override
        public void update () {
            ArrayList<AbstractCard> validCards = new ArrayList<>();

            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                System.out.println("DEBUG: Baby Snecko checking card: " + card);
                if ((card.costForTurn <= 0 || card.hasTag(SneckoMod.SNEKPROOF) || card.cost != card.costForTurn) || card.freeToPlay() || card.freeToPlayOnce) {
                    System.out.println("DEBUG: Card found invalid, costs: " + card.costForTurn);
                    continue;
                }
                System.out.println("DEBUG: Card found valid, costs: " + card.costForTurn);
                validCards.add(card);
            }

            if (!validCards.isEmpty()) {
                int cardsToPick = Math.min(2, validCards.size());
                ArrayList<AbstractCard> chosenCards = new ArrayList<>();

                for (int i = 0; i < cardsToPick; i++) {
                    AbstractCard randomCard = validCards.get(AbstractDungeon.relicRng.random(validCards.size() - 1));
                    System.out.println("DEBUG: Decided to Muddle: " + randomCard + " which costs: " + randomCard.costForTurn);
                    AbstractDungeon.actionManager.addToBottom(new MuddleAction(randomCard));
                    validCards.remove(randomCard);
                    chosenCards.add(randomCard);
                }
            }

            this.isDone = true;
        }
    }