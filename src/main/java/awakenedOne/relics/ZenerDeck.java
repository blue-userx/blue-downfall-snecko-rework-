package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.ESPSpell;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;
import static awakenedOne.util.Wiz.att;

public class ZenerDeck extends CustomRelic {

    //Zener Deck

    public static final String ID = AwakenedOneMod.makeID("ZenerDeck");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("ZenerDeck.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("ZenerDeck.png"));

    public ZenerDeck() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
        this.tips.add(new CardPowerTip(new ESPSpell()));
    }

    @Override
    public void atBattleStart() {
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        //actually conjuring it at the start of combat might be OP
        att(new ConjureAction(false, false, true, new ESPSpell()));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
