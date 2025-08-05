package downfall.patches;

import awakenedOne.AwakenedOneMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

import static theHexaghost.GhostflameHelper.activeGhostFlame;
import static theHexaghost.HexaMod.renderFlames;

@SpirePatch(
        clz = CardGroup.class,
        method = "triggerOnOtherCardPlayed"
)
public class GlobalOnCardUsePatch {
    public static void Prefix(CardGroup __instance, AbstractCard abstractCard) {

        if(activeGhostFlame == null) return;
        if (!activeGhostFlame.charged && renderFlames && activeGhostFlame.advanceOnCardUse)
            activeGhostFlame.advanceTrigger(abstractCard);

//        if (abstractCard.hasTag(expansionContentMod.STUDY)) {
//            downfallMod.playedBossCardThisTurn = true;
//        }

    }
}