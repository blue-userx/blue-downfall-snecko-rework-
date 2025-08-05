package awakenedOne.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import guardian.orbs.StasisOrb;

import java.util.Iterator;
import java.util.UUID;

public class GrimoireAction extends AbstractGameAction {
    private static final float DURATION = 0.1F;
    private final DamageInfo info;
    private final UUID uuid;
    private final AbstractPlayer p;

    public GrimoireAction(AbstractCreature target, DamageInfo info, UUID targetUUID) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = DURATION;
        this.p = AbstractDungeon.player;
        this.uuid = targetUUID;
    }

    @Override
    public void update() {
        if (this.duration == DURATION) {
            if (this.target != null) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.FIRE));
                this.target.damage(this.info);
            }
            Iterator<AbstractCard> var1;
            for (var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); ) {
                AbstractCard c = var1.next();
                addToBot(new GrimoireSpecificCardAction(c, AbstractDungeon.player.limbo, true));
                addToBot(new GrimoireSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
                addToBot(new GrimoireSpecificCardAction(c, AbstractDungeon.player.hand, true));
                addToBot(new GrimoireSpecificCardAction(c, AbstractDungeon.player.discardPile, true));
            }

            for (AbstractOrb o : this.p.orbs) {
                if (o instanceof StasisOrb) {
                    if (((StasisOrb) o).stasisCard.uuid == this.uuid) {
                        this.p.orbs.remove(o);
                    }
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            this.tickDuration();
        }

    }
}