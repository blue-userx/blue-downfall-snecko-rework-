package automaton.patches;

import automaton.AutomatonChar;
import automaton.BronzeOrbStash;
import automaton.actions.DrawCardFromStashAction;
import automaton.powers.OptimizePower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static collector.util.Wiz.atb;

public class DrawFromStash {

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnPreDrawCards"
    )
    public static class AbstractPlayerApplyStartOfTurnPostDrawRelicsPatch {
        public static void Prefix(AbstractPlayer __instance) {
            if (AbstractDungeon.player.chosenClass.equals(AutomatonChar.Enums.THE_AUTOMATON) || !BronzeOrbStash.stashpile.isEmpty()) {
                atb(new DrawCardFromStashAction());
                //TODO increased stash draw power
               if (AbstractDungeon.player.hasPower(OptimizePower.POWER_ID)) {
                    for (int i = 0; i < AbstractDungeon.player.getPower(OptimizePower.POWER_ID).amount; i++) {
                        atb(new DrawCardFromStashAction());
                    }
                }
            }
        }
    }
}
