package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.DrawCardFromStashAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class BitShift extends AbstractBronzeCard {
    public final static String ID = makeID("BitShift");

    public BitShift() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        //exhaust = true;
        baseBlock = 1;
        baseMagicNumber = magicNumber = 1;
        //this.tags.add(SneckoMod.BANNEDFORSNECKO);
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("BitShift.png"));
    }



    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DrawCardFromStashAction());
    }

    public void upp() {
        upgradeBlock(3);
    }
}