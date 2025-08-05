package automaton.relics;

import automaton.AutomatonMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.util.TextureLoader;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class BronzeCore extends CustomRelic implements OnCompileRelic {

    public static final String ID = AutomatonMod.makeID("BronzeCore");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BronzeCore.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BronzeCore.png"));

    public BronzeCore() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    boolean activated = false;

    @Override
    public void atBattleStart() {
        activated = false;
        grayscale = false;
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (!activated && forGameplay) {
            flash();
            activated = true;
            grayscale = true;
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void onVictory() {
        activated = false;
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
