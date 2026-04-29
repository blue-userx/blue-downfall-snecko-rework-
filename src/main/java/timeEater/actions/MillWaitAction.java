package timeEater.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

//vacant code thank you laugic go check out the vacant
public class MillWaitAction extends AbstractGameAction {
    public void update() {
        for (AbstractGameEffect effect : AbstractDungeon.topLevelEffects) {
            if (effect instanceof ShowCardAndMillEffect)
                return;
        }
        for (AbstractGameEffect effect : AbstractDungeon.effectList) {
            if (effect instanceof ShowCardAndMillEffect)
                return;
        }
        this.isDone = true;
    }
}
