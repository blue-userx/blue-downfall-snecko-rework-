package automaton.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

import static awakenedOne.util.Wiz.atb;

public class StashHandAction extends AbstractGameAction {

    public StashHandAction() {
    }



    public void update() {
        Iterator var1;
        AbstractCard c;
            var1 = AbstractDungeon.player.hand.group.iterator();
            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                atb(new PlaceActualCardIntoStashAction(c, AbstractDungeon.player.hand, true));
        }
        this.isDone = true;
    }

}
