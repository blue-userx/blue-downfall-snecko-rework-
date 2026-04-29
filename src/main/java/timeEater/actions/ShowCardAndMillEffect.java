package timeEater.actions;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


//this is vacant code from laugic
public class ShowCardAndMillEffect extends AbstractGameEffect {
    public static final float DURATION = Settings.ACTION_DUR_MED;

    private AbstractCard card;

    private CardGroup groupToGoTo;

    private CardGroup groupFrom;

    boolean running = false;

    public ShowCardAndMillEffect(AbstractCard card, CardGroup groupToGoTo) {
        this(card, groupToGoTo, AbstractDungeon.player.drawPile);
    }

    public ShowCardAndMillEffect(AbstractCard card, CardGroup groupToGoTo, CardGroup groupFrom) {
        this.duration = this.startingDuration = DURATION;
        this.card = card;
        this.groupToGoTo = groupToGoTo;
        this.groupFrom = groupFrom;
        card.drawScale = 0.1F;
        card.targetDrawScale = 0.6F;
        card.lighten(true);
        card.unfadeOut();
        card.unhover();
    }

    public void update() {
        if (!this.running)
            this.duration = this.startingDuration;
        if (this.duration == this.startingDuration) {
            boolean run = true;
            if (this.groupFrom == AbstractDungeon.player.discardPile) {
                if (this.card.current_x < Settings.WIDTH) {
                    run = false;
                } else {
                    this.card.target_x = Settings.scale * Settings.WIDTH - 100.0F - (int)(Math.random() * 200.0D) - this.card.hb.width;
                }
            } else {
                this.card.target_x = (100 + (int)(Math.random() * 200.0D));
            }
            if (run) {
                this.card.target_y = (100 + (int)(Math.random() * 200.0D));
                this.running = true;
                CardCrawlGame.sound.play("CARD_REJECT");
            }
        }
        if (this.running)
            Run();
    }

    private void Run() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < 0.0F) {
            this.isDone = true;
            if (this.groupToGoTo == AbstractDungeon.player.hand)
                if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                    this.groupToGoTo = AbstractDungeon.player.discardPile;
                    AbstractDungeon.player.discardPile.addToTop(this.card);
                } else {
                    AbstractDungeon.player.hand.addToTop(this.card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                    if (this.groupFrom == AbstractDungeon.player.drawPile)
                        PostRebound(this.card);
                }
            if (this.groupToGoTo == AbstractDungeon.player.discardPile) {
                this.card.shrink();
                (AbstractDungeon.getCurrRoom()).souls.discard(this.card, true);
            }
        }
    }

    private void PostRebound(AbstractCard card) {
        AbstractPlayer player = AbstractDungeon.player;
//        if (player != null && player.hasPower(ImmaterializePower.POWER_ID)) {
//            player.getPower(ImmaterializePower.POWER_ID).flash();
//            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new GainBlockAction((AbstractCreature)player, (player.getPower(ImmaterializePower.POWER_ID)).amount));
//        }
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone)
            this.card.render(sb);
    }

    public void dispose() {}
}
