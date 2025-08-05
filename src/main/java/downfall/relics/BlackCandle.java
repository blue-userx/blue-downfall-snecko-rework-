package downfall.relics;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

import static downfall.patches.EvilModeCharacterSelect.evilMode;

public class BlackCandle extends CustomRelic {

    public static final String ID = downfallMod.makeID("BlackCandle");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/BlackCandle.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/BlackCandleOutline.png"));

    public BlackCandle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.CURSE) {
            if (card.cost == -2) {
                this.flash();
                this.addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1, EnumPatch.HERMIT_GHOSTFIRE));
                card.exhaust = true;
                action.exhaustCard = true;
            } else {
                if (card.exhaust && card.cost !=0 && card.freeToPlay() && card.cost !=-2) {
                    this.flash();
                    this.addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1, EnumPatch.HERMIT_GHOSTFIRE));
                    card.exhaust = true;
                    action.exhaustCard = true;
                }
            }
        }
    }

    public boolean canSpawn() {
       // return false;
        return (evilMode || (AbstractDungeon.player instanceof hermit));
    }

    public AbstractRelic makeCopy() {
        return new BlackCandle();
    }
}