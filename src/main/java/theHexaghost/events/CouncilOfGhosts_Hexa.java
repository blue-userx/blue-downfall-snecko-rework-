package theHexaghost.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theHexaghost.HexaMod;
import theHexaghost.cards.CouncilsJustice;

import java.util.ArrayList;
import java.util.List;

public class CouncilOfGhosts_Hexa extends AbstractImageEvent {
    public static final String ID = HexaMod.makeID("CouncilOfGhosts");
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }

    private CurScreen screen;

    private int hpLoss;

    private boolean accept = false;
    private List<String> cardsRemoved = new ArrayList<>();
    private List<String> cardsAdded = new ArrayList<>();
    private boolean higher_ascension = false;

    public CouncilOfGhosts_Hexa() {
        super(NAME, DESCRIPTIONS[0], "images/events/ghost.jpg");
        this.screen = CurScreen.INTRO;
        this.noCardsInRewards = true;

        this.hpLoss = MathUtils.ceil((float) AbstractDungeon.player.maxHealth * 0.65F);
        if (this.hpLoss >= AbstractDungeon.player.maxHealth) {
            this.hpLoss = AbstractDungeon.player.maxHealth - 1;
        }

        if(AbstractDungeon.ascensionLevel >= 15) higher_ascension = true;

        this.imageEventText.setDialogOption(OPTIONS[0] + this.hpLoss + OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2], true, new CouncilsJustice());
        this.imageEventText.setDialogOption(OPTIONS[3] + (higher_ascension?3:5) + OPTIONS[7], true, new Apparition());
        this.imageEventText.setDialogOption(OPTIONS[5]);

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.player.decreaseMaxHealth(this.hpLoss);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4], true);
                        this.imageEventText.updateDialogOption(1, OPTIONS[2], new CouncilsJustice());
                        this.imageEventText.updateDialogOption(2, OPTIONS[3] + (higher_ascension?3:5) + OPTIONS[7], new Apparition());
                        this.imageEventText.updateDialogOption(3, OPTIONS[6],true);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.accept = true;
                        return;
                    case 1:

                        this.imageEventText.updateDialogOption(1, OPTIONS[4], true);
                        this.imageEventText.updateDialogOption(3, OPTIONS[5]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        nukeStrikes();
                        for (int i = 0; i < 3; i++) {
                            AbstractCard n = new CouncilsJustice();
                            cardsAdded.add(n.cardID);
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(n, Settings.WIDTH * (AbstractDungeon.cardRng.random(0.25F,0.75F)), Settings.HEIGHT * (AbstractDungeon.cardRng.random(0.25F,0.75F))));
                        }
                        return;
                    case 2:
                        this.imageEventText.updateDialogOption(2, OPTIONS[4], true);
                        this.imageEventText.updateDialogOption(3, OPTIONS[5]);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        nukeDefends();
                        for (int i = 0; i < (higher_ascension?3:5); i++) {
                            AbstractCard n = new Apparition();
                            cardsAdded.add(n.cardID);
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(n, Settings.WIDTH * (AbstractDungeon.cardRng.random(0.25F,0.75F)), Settings.HEIGHT * (AbstractDungeon.cardRng.random(0.25F,0.75F))));
                        }
                        return;
                    case 3:
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[5]);
                        if (this.accept){
                            this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                            logMetric(ID, "Accepted", cardsAdded, cardsRemoved, null, null,
                                    null, null, null,
                                    0, 0, hpLoss, 0, 0, 0);
                        } else {
                            this.imageEventText.updateBodyText(DESCRIPTIONS[5]);
                            logMetricIgnored(ID);
                        }
                        this.screen = CurScreen.END;
                        return;
                }
            case END:
                this.openMap();
        }

    }

    private void nukeStrikes() {

        ArrayList<AbstractCard> strikes = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) {
                strikes.add(c);
            }
        }
        for (AbstractCard c : strikes){
            AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(c, com.megacrit.cardcrawl.core.Settings.WIDTH * (AbstractDungeon.cardRng.random(0.25F,0.75F)), com.megacrit.cardcrawl.core.Settings.HEIGHT * (AbstractDungeon.cardRng.random(0.25F,0.75F))));
            AbstractDungeon.player.masterDeck.removeCard(c);
            cardsRemoved.add(c.cardID);
        }
    }

    private void nukeDefends() {

        ArrayList<AbstractCard> defends = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.hasTag(AbstractCard.CardTags.STARTER_DEFEND)) {
                defends.add(c);
            }
        }
        for (AbstractCard c : defends){
            AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect(c, com.megacrit.cardcrawl.core.Settings.WIDTH * (AbstractDungeon.cardRng.random(0.25F,0.75F)), com.megacrit.cardcrawl.core.Settings.HEIGHT * (AbstractDungeon.cardRng.random(0.25F,0.75F))));
            AbstractDungeon.player.masterDeck.removeCard(c);
            cardsRemoved.add(c.cardID);

        }
    }


    private enum CurScreen {
            INTRO,
            END;

            CurScreen() {
            }
        }
    }
