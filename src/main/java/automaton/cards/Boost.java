package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Boost extends AbstractBronzeCard {

    public final static String ID = makeID("Boost");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 5;
    private static final int MAGIC = 1;

    public Boost() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
        //thisEncodes();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Boost.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new StrengthPower(AbstractDungeon.player, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}