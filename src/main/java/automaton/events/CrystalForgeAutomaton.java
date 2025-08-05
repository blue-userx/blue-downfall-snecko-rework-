package automaton.events;

import automaton.AutomatonMod;
import automaton.cards.SpaghettiCode;
import automaton.relics.BottledCode;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;

import java.util.ArrayList;

public class CrystalForgeAutomaton extends AbstractImageEvent {
    public static final String ID = "bronze:CrystalForge";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String INTRO;
    private static final String TRANSMUTE;
    private static final String REFORGE;
    private static final String COMBINE;
    private static final String LEAVE;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("bronze:CrystalForge");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO = DESCRIPTIONS[0];
        REFORGE = DESCRIPTIONS[2];
        COMBINE = DESCRIPTIONS[1];
        TRANSMUTE = DESCRIPTIONS[3];
        LEAVE = DESCRIPTIONS[4];
    }

    private int screenNum = 0;
    private boolean pickCardForHP = false;
    private boolean pickCardForTransmute = false;

    private ArrayList<AbstractCard> validCards;
    private ArrayList<AbstractCard> rareCards;

    public CrystalForgeAutomaton() {
        super(NAME, INTRO, GuardianMod.getResourcePath("/events/grimForge.jpg"));

        validCards = new ArrayList<>();
        rareCards = new ArrayList<>();


        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
            if (c.hasTag(AutomatonMod.ENCODES)) {
                validCards.add(c);
            }
        }

        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.rarity == AbstractCard.CardRarity.RARE) rareCards.add(c);
        }
        if (validCards.size() == 0) {
            this.imageEventText.setDialogOption(OPTIONS[5], true);

        } else {
            this.imageEventText.setDialogOption(OPTIONS[0], new BottledCode());

        }

        if (rareCards.size() == 0) {
            this.imageEventText.setDialogOption(OPTIONS[6], true);

        } else {
            this.imageEventText.setDialogOption(OPTIONS[2]);

        }

        this.imageEventText.setDialogOption(OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[4]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_FORGE");
        }

    }

    public void update() {
        super.update();
        if (pickCardForTransmute && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard cardGained = SpaghettiCode.getRandomEncode().makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cardGained, (float) (Settings.WIDTH * .3), (float) (Settings.HEIGHT / 2)));

            AbstractCard cardLost = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(cardLost, (float) (Settings.WIDTH * .7), (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(cardLost);

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            logMetricObtainCardAndLoseCard(ID, "Transmute", cardGained, cardLost);

        } else if (pickCardForHP && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) (Settings.WIDTH * .7), (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(c);

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            int maxHpUp = 10;
            AbstractDungeon.player.increaseMaxHp(maxHpUp, true);
            logMetricCardRemovalHealMaxHPUp(ID, "Reforge", c, AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth, maxHpUp);

            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);

        }


    }


    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.screenNum = 2;
                        //this.pickCardForSalvageGems = true;
                        this.imageEventText.updateBodyText(COMBINE);
                        BottledCode relic = new BottledCode();
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), relic);

                        AbstractDungeon.player.decreaseMaxHealth(10);
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        logMetricObtainRelicAndLoseMaxHP(ID, "Craft", relic, 10);
                        break;
                    case 1:
                        this.screenNum = 2;
                        this.pickCardForHP = true;
                        AbstractDungeon.gridSelectScreen.open(AutomatonMod.getRareCards(), 1, DESCRIPTIONS[5], false, false, false, false);
                        this.imageEventText.updateBodyText(REFORGE);

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);

                        break;
                    case 2:
                        this.screenNum = 2;
                        this.pickCardForTransmute = true;
                        this.imageEventText.updateBodyText(TRANSMUTE);
                        AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, DESCRIPTIONS[5], false, false, false, false);

                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.setDialogOption(OPTIONS[4]);

                        break;
                    case 3:
                        this.screenNum = 2;
                        this.imageEventText.clearAllDialogs();
                        this.imageEventText.updateBodyText(LEAVE);
                        this.imageEventText.setDialogOption(OPTIONS[4]);
                        logMetricIgnored(ID);

                        break;
                }


                break;
            default:
                this.openMap();
        }

    }
}
