package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.actions.PlaceActualCardIntoStashAction;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.cardmods.PropertiesMod;
import hermit.actions.HandSelectAction;
import hermit.util.Wiz;
import sneckomod.SneckoMod;

import static automaton.AutomatonMod.makeBetaCardPath;

public class FineTuning extends AbstractBronzeCard {

    public final static String ID = makeID("FineTuning");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ArmamentsAction");
    //stupid intellij stuff skill, self, rare

    private static final int MAGIC = 1;

    public FineTuning() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("FineTuning.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new HandSelectAction(1, (c) -> true, list -> {
            for (AbstractCard c : list)
            {
                c.upgrade();
                if (!c.selfRetain) {
                    CardModifierManager.addModifier(c, new PropertiesMod(PropertiesMod.supportedProperties.RETAIN, false));
                }
                addToTop(new PlaceActualCardIntoStashAction(c, AbstractDungeon.player.hand, true));
            }
        }, null, uiStrings.TEXT[0],false,false,false));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}