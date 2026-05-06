package automaton.actions;

import automaton.BronzeOrbStash;
import basemod.helpers.CardModifierManager;
import collector.CollectorCollection;
import collector.cardmods.CollectedCardMod;
import collector.cards.collectibles.LuckyWick;
import collector.patches.CollectorBottleField;
import collector.relics.BottledCollectible;
import collector.relics.HolidayCoal;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.FrozenEye;
import guardian.relics.PickAxe;
import hermit.relics.BartenderGlass;
import theHexaghost.relics.CandleOfCauterizing;

import static collector.util.Wiz.att;

public class DrawCardFromStashAction extends AbstractGameAction {
    public DrawCardFromStashAction() {
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if (!BronzeOrbStash.combatstashpile.isEmpty()) {
            AbstractCard tar = BronzeOrbStash.combatstashpile.getRandomCard(AbstractDungeon.cardRandomRng);
            if(AbstractDungeon.player.hasRelic(FrozenEye.ID)) {
                tar = BronzeOrbStash.combatstashpile.getTopCard();
            }

            BronzeOrbStash.combatstashpile.removeCard(tar);
            AbstractDungeon.player.drawPile.addToTop(tar);
            att(new DrawCardAction(1));
        } else {
        //todo some kind of relic that lets you draw from the stash manually for stuff even if it's empty?
            //            if (AbstractDungeon.player.hasRelic(HolidayCoal.ID)) {
            //                AbstractDungeon.player.getRelic(HolidayCoal.ID).flash();
            //                AbstractCard tar = new LuckyWick();
            //                CardModifierManager.addModifier(tar, new CollectedCardMod());
            //                AbstractDungeon.player.drawPile.addToTop(tar);
            //                att(new DrawCardAction(1));
            //            }
        }
        this.isDone = true;
    }
}