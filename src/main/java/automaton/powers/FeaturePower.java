package automaton.powers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static champ.ChampMod.vigor;

public class FeaturePower extends AbstractAutomatonPower {
    public static final String NAME = "Feature";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public FeaturePower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        vigor(amount);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
