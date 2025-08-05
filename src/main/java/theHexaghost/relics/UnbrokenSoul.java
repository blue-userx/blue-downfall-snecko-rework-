package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.util.OnChargeSubscriber;
import downfall.util.TextureLoader;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class UnbrokenSoul extends CustomRelic implements OnChargeSubscriber {

    public static final String ID = HexaMod.makeID("UnbrokenSoul");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("UnbrokenSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("UnbrokenSoul.png"));

    public boolean activated = false;
    // mark of the ether

    //variables
    public static final int BLOCK = 4;

    public UnbrokenSoul() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(SpiritBrand.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(SpiritBrand.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(SpiritBrand.ID);
    }

    @Override
    public String getUpdatedDescription() {
        // Colorize the starter relic's name
        String name = new SpiritBrand().name;
        StringBuilder sb = new StringBuilder();
        if(Settings.language== Settings.GameLanguage.ZHS || Settings.language== Settings.GameLanguage.ZHT){
            sb.append("[#").append(HexaMod.placeholderColor.toString()).append("]").append(name).append("[]");

        }else {
            for (String word : name.split(" ")) {
                sb.append("[#").append(HexaMod.placeholderColor.toString()).append("]").append(word).append("[] ");
            }
            sb.setLength(sb.length() - 1);
            sb.append("[#").append(HexaMod.placeholderColor.toString()).append("]");

        }

        return DESCRIPTIONS[0] + sb + DESCRIPTIONS[1] + BLOCK + DESCRIPTIONS[2];
    }

//    @Override
//    public void atTurnStartPostDraw() {
//        activated = false;
//        beginPulse();
//    }

    @Override
    public void onCharge(AbstractGhostflame g) {
//        if (!activated) {
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, BLOCK));
//            addToBot(new DrawCardAction(1));
//            addToBot(new GainEnergyAction(1));
//            activated = true;
//            stopPulse();
//        }
    }
}
