package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.PlaceActualCardIntoStashAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Turbo extends AbstractBronzeCard {

    public final static String ID = makeID("Turbo");

    //stupid intellij stuff skill, self, uncommon

    public Turbo() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        this.cardsToPreview = new VoidCard();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Turbo.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(magicNumber));
        shuffleIn(new Slimed());
        atb(new MakeTempCardInDiscardAction(new Slimed(), 1));
        atb(new PlaceActualCardIntoStashAction(new Slimed(), null, true));
    }

    public void upp() {
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}