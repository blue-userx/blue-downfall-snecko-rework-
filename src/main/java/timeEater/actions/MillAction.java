package timeEater.actions;

import awakenedOne.cardmods.ConjureMod;
import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import sneckomod.SneckoMod;
import timeEater.cards.AbstractTimeCard;

import java.util.ArrayList;

//this is vacant code from laugic!!! go check out vacant mod!!!!!!!!!!!
public class MillAction extends AbstractGameAction {
    private float startingDuration;

    private int startAmount = 0;

    private int voidAmount;

    private int millNum;

    private int postReturnAmount = 0;

    private AbstractCard ignoredCard;

    private int numType = 0;

    private int typesRicocheted = 0;

    private boolean gainTemperanceForMill = false;

    private AbstractCard.CardType millUntil = null;

    private AbstractCard.CardType ricochetType = null;

    private ArrayList<AbstractGameAction> actions;

    private final SFXAction waka = new SFXAction("theVacant:waka");

    public MillAction(int numCards) {
        this.amount = numCards;
        this.voidAmount = 0;
        this.millNum = 0;
        this.startAmount = this.amount;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.ignoredCard = null;
        this.actions = new ArrayList<>();
    }

    public MillAction(int numCards, AbstractCard cardToIgnore) {
        this.amount = numCards;
        this.voidAmount = 0;
        this.millNum = 0;
        this.startAmount = this.amount;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.ignoredCard = cardToIgnore;
        this.actions = new ArrayList<>();
    }

    public MillAction(int numCards, AbstractCard.CardType ricochetType, AbstractCard cardToIgnore) {
        this.amount = numCards;
        this.voidAmount = 0;
        this.millNum = 0;
        this.startAmount = this.amount;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.ignoredCard = cardToIgnore;
        this.ricochetType = ricochetType;
        this.actions = new ArrayList<>();
    }

    public MillAction(int numCards, boolean temperance, int postReturnAmount, AbstractCard returnToIgnore) {
        this.amount = numCards;
        this.voidAmount = 0;
        this.millNum = 0;
        this.startAmount = this.amount;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.gainTemperanceForMill = temperance;
        this.postReturnAmount = postReturnAmount;
        this.ignoredCard = returnToIgnore;
        this.actions = new ArrayList<>();
    }

    public MillAction(AbstractCard.CardType millUntil, AbstractCard cardToIgnore) {
        this(millUntil, cardToIgnore, 1);
    }

    public MillAction(AbstractCard.CardType millUntil, AbstractCard cardToIgnore, int numType) {
        this.millUntil = millUntil;
        this.amount = 999;
        this.actions = new ArrayList<>();
        this.ignoredCard = cardToIgnore;
        this.numType = numType;
        this.typesRicocheted = 0;
    }

    public void update() {
        PreMill();
        while (this.amount > 0 && AbstractDungeon.player.drawPile.size() > 0) {
            ProcessMill();
            this.amount--;
        }
        PostMill();
        this.isDone = true;
    }

    private void PreMill() {}

    private void ProcessMill() {
        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
        this.millNum++;
        if (card != null && !card.equals(this.ignoredCard))
            CheckRicochet(card);
    }

    private void CheckRicochet(AbstractCard card) {
        //TODO add Bounce tag instead of using RNG tag
        if ((card instanceof AbstractTimeCard && ((AbstractTimeCard)card).hasTag(SneckoMod.RNG)) || GetSpecialRicochet(card)) {
            Ricochet(card);
        } else if (this.millUntil != null && card.type == this.millUntil) {
            Ricochet(card);
            this.typesRicocheted++;
            if (this.typesRicocheted >= this.numType)
                this.amount = 0;
        } else {
            MoveToDiscard(card);
            if (card instanceof AbstractTimeCard && ((AbstractTimeCard)card).postMillAction)
                ((AbstractTimeCard)card).PostMillAction();
        }
    }

    private void Ricochet(AbstractCard card) {
        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            MoveToDiscard(card);
            if (card instanceof AbstractTimeCard && ((AbstractTimeCard)card).postMillAction)
                ((AbstractTimeCard)card).PostMillAction();
            return;
        }
        AbstractDungeon.player.drawPile.removeCard(card);
        addToBot((AbstractGameAction)new VFXAction((AbstractCreature)AbstractDungeon.player, (AbstractGameEffect)new ShowCardAndMillEffect(card, AbstractDungeon.player.hand), Settings.ACTION_DUR_XFAST, true));
        if (card instanceof AbstractTimeCard && ((AbstractTimeCard)card).postMillAction)
            ((AbstractTimeCard)card).PostMillAction();
        ProcessPostMill(card, true);
    }

    private void MoveToDiscard(AbstractCard card) {
        AbstractDungeon.player.drawPile.removeCard(card);
        addToBot((AbstractGameAction)new VFXAction((AbstractCreature)AbstractDungeon.player, (AbstractGameEffect)new ShowCardAndMillEffect(card, AbstractDungeon.player.discardPile), Settings.ACTION_DUR_XFAST, true));
        AbstractDungeon.player.discardPile.addToTop(card);
        ProcessPostMill(card, false);
    }

    private void PostMill() {
        AbstractDungeon.player.hand.applyPowers();
        int bonusReturn = 0;
        addToBot(new MillWaitAction());
        if (this.postReturnAmount + bonusReturn > 0)
            addToBot(new ReturnAction(this.postReturnAmount + bonusReturn, this.ignoredCard));
    }

    private void ProcessPostMill(AbstractCard card, boolean ricocheted) {
        PostMillCard(card);
    }

    private void PostMillCard(AbstractCard card) {
        AbstractPlayer player = AbstractDungeon.player;
        if (player != null) {
            //todo add mill exhaust status/curse cards power
//            if (player.hasPower(CleanseSoulPower.POWER_ID))
//                if (card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
//                    this.actionType = AbstractGameAction.ActionType.EXHAUST;
//                    if (player.discardPile.contains(card)) {
//                        player.discardPile.moveToExhaustPile(card);
//                        if (!AbstractDungeon.actionManager.actions.contains(this.waka))
//                            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) this.waka);
//                        player.getPower(CleanseSoulPower.POWER_ID).flash();
//                        addToBot((AbstractGameAction) new ApplyPowerAction((AbstractCreature) player, (AbstractCreature) player, (AbstractPower) new TemperancePower((AbstractCreature) player, (AbstractCreature) player, (player.getPower(CleanseSoulPower.POWER_ID)).amount), (player.getPower(CleanseSoulPower.POWER_ID)).amount));
//                    }
//                }
        }
    }

    private boolean GetSpecialRicochet(AbstractCard card) {
        AbstractPlayer player = AbstractDungeon.player;
        //todo add bounce mod
        //if (CardModifierManager.hasModifier(card, ConjureMod.ID))
        //    return true;
        return false;
    }
}
