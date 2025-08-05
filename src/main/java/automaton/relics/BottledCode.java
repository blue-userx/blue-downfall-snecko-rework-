package automaton.relics;

import automaton.AutomatonMod;
import automaton.actions.AddToFuncAction;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.patches.BottledStasisPatch;

import java.util.function.Predicate;

public class BottledCode extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer>, OnRemoveCardFromMasterDeckRelic {
    public static final String ID = "bronze:BottledCode";
    public static final String IMG_PATH = "bottledCode.png";
    public static final String OUTLINE_IMG_PATH = "bottledCode.png";
    public AbstractCard card = null;
    private boolean cardSelected = true;

    public BottledCode() {
        super(ID, new Texture(AutomatonMod.makeRelicPath(IMG_PATH)), new Texture(AutomatonMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)),
                RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void onRemoveCardFromMasterDeck(AbstractCard var1) {
        if (this.card != null) {
            if (var1.uuid == card.uuid) {
                this.flash();
                this.grayscale = true;
                setDescriptionAfterLoading();
            }
        }
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {

        return BottledStasisPatch.inBottledCode::get;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractCard getCard() {
        return card.makeCopy();
    }

    @Override
    public Integer onSave() {
        return AbstractDungeon.player.masterDeck.group.indexOf(card);
    }

    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                BottledStasisPatch.inBottledCode.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }

    @Override
    public void onEquip() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
            if (c.hasTag(AutomatonMod.ENCODES)) tmp.addToTop(c);
        }
        AbstractDungeon.gridSelectScreen.open(tmp,
                1, DESCRIPTIONS[1] + name + ".",
                false, false, false, false);
    }

    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                BottledStasisPatch.inBottledCode.set(cardInDeck, false);
            }
        }
    }

    @Override
    public void update() {
        super.update();

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottledStasisPatch.inBottledCode.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));

            setDescriptionAfterLoading();
        }
    }

    public void setDescriptionAfterLoading() {

        boolean cardExists = false;

        if (cardSelected) {
            if (card != null) {
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.uuid == card.uuid) {
                        cardExists = true;
                        break;
                    }
                }
            }

            if (!cardExists) {
                tips.clear();
                this.description = this.DESCRIPTIONS[4];
                this.grayscale = true;
                initializeTips();
            }

            if (cardExists) {
                this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
                this.grayscale = false;
                tips.clear();
                tips.add(new PowerTip(name, description));
                initializeTips();
            }
        }
    }

//    @Override
//    public void onRemoveCardFromMasterDeck(AbstractCard var1){
//        if (var1.uuid == card.uuid) {
//            setDescriptionAfterLoading();
//        }
//    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledCode();
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.uuid == card.uuid) {
                AbstractDungeon.actionManager.addToTop(new AddToFuncAction(c, AbstractDungeon.player.drawPile));
                break;
            }
        }

    }

}