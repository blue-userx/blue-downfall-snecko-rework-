package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.StashFromHandAction;
import automaton.actions.StashHandAction;
import automaton.powers.HardenedFormPower;
import basemod.BaseMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class HardenedForm extends AbstractBronzeCard {

    public final static String ID = makeID("HardenedForm");

    //stupid intellij stuff power, self, rare

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public HardenedForm() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("HardenedForm.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.player.hasPower("bronze:HardenedForm")) {
            applyToSelf(new HardenedFormPower(1));
        }
        this.addToBot(new StashHandAction());
    }

    public void upp() {
        this.isInnate = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}