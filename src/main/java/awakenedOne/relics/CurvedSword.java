package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class CurvedSword extends CustomRelic {

    //backflip scimmy
    //ninja relic but spells. tried to add a funky sounding name
    //the joke is that this is literally a "conjure blade"

    public static final String ID = AwakenedOneMod.makeID("CurvedSword");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("CurvedSword.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("CurvedSword.png"));

    public CurvedSword() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
        tips.add(new CardPowerTip(new Ceremony()));
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card instanceof AbstractSpellCard) {
            ++this.counter;
            if (this.counter % 4 == 0) {
                this.flash();
                this.counter = 0;
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.addToTop(new MakeTempCardInHandAction(new Ceremony(), 1, false));
            }
        }

    }

    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 4 + DESCRIPTIONS[1];
    }

}
