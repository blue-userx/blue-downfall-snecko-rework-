package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class CandleOfCauterizing extends CustomRelic {

    public static final String ID = HexaMod.makeID("CandleOfCauterizing");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("CandleOfCauterizing.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("CandleOfCauterizing.png"));

    //variables
    public static final int SOULBURN_BONUS_AMOUNT = 3;

    public CandleOfCauterizing() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }
    //red candle
//    @Override
//    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
//        if (damageAmount > 0 && target != AbstractDungeon.player && info.type == DamageInfo.DamageType.NORMAL) {
//            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new BurnPower(target, 2), 2, true));
//        }
//    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + SOULBURN_BONUS_AMOUNT + DESCRIPTIONS[1];
    }
}
