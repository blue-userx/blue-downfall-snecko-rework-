package theHexaghost.cards;

import champ.powers.GladiatorFormPower;
import champ.relics.RageAmulet;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.downfallMod;
import gremlin.relics.FragmentationGrenade;
import theHexaghost.HexaMod;
import theHexaghost.util.HexaPurpleTextInterface;

public class EtherStep extends AbstractHexaCard implements HexaPurpleTextInterface {
    public final static String ID = makeID("EtherStep");

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 3;

    public EtherStep() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "EtherStep.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        this.addToBot(new ExhaustAction(1, false));
        this.addToBot(new DrawCardAction(p, this.magicNumber));
    }

    public void afterlife() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m == null) return;
        this.calculateCardDamage(m);

        if (AbstractDungeon.player.hasRelic(FragmentationGrenade.ID)) {
            AbstractDungeon.player.getRelic(FragmentationGrenade.ID).flash();
            this.damage = this.damage + FragmentationGrenade.OOMPH;
        }

        if(AbstractDungeon.player.hasPower("Pen Nib") ){
            this.damage /= 2;
            dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
            this.damage *= 2;
        } else {
            dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        }

        if (AbstractDungeon.player.hasRelic(FragmentationGrenade.ID)) {
            AbstractDungeon.player.getRelic(FragmentationGrenade.ID).flash();
            this.damage = this.damage - FragmentationGrenade.OOMPH;
        }

        if (AbstractDungeon.player.hasPower(GladiatorFormPower.POWER_ID)) {
            GladiatorFormPower revengePower = (GladiatorFormPower) AbstractDungeon.player.getPower(GladiatorFormPower.POWER_ID);

            if (revengePower != null) {
                revengePower.onSpecificTriggerBranch();
            }
        }

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof RageAmulet) {
                ((RageAmulet) r).onSpecificTrigger();
            }
        }

        atb(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, VigorPower.POWER_ID));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    // to still show afterlife tooltip. because the format [purple]hexamod:afterlife[] doesnt get displayed correctly
    // we are only using [purple]afterlife[] here for easier text comprehension for new players, but doing this
    // means we dont have the keyword tooltip so we need to manually add it
    // but after I tried adding it in the constrcutor it turns out sometimes who knows why it wont be added
    // and this way seems to work
    @Override
    public void initializeDescription() {
        super.initializeDescription();
        String afterlife_name = downfallMod.keywords_and_proper_names.get("afterlife");
        this.keywords.add(afterlife_name);
    }
}
