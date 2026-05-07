package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.PlaceActualCardIntoStashAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static automaton.AutomatonMod.makeBetaCardPath;

public class BronzeArmor extends AbstractBronzeCard {

    public final static String ID = makeID("BronzeArmor");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;

    public BronzeArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = 13;
        //thisEncodes();
        //tags.add(AutomatonMod.BAD_COMPILE);
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("BronzeArmor.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (int i = 0; i < magicNumber; i++) {
            atb(new PlaceActualCardIntoStashAction(new Slimed(), null, true));
        }
    }


    public void upp() {
        upgradeBlock(4);
    }
}