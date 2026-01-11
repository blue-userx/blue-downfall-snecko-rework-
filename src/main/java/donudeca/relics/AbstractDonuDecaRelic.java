package donudeca.relics;

import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static donudeca.DonuDecaMod.makeRelicPath;
import static donudeca.DonuDecaMod.modID;

public abstract class AbstractDonuDecaRelic extends CustomRelic {
    public AbstractCard.CardColor color;

    public AbstractDonuDecaRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(setId, tier, sfx, null);
    }

    public AbstractDonuDecaRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(setId, TexLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + ".png")), tier, sfx);
        outlineImg = TexLoader.getTexture(makeRelicPath(setId.replace(modID + ":", "") + "Outline.png"));
        this.color = color;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}