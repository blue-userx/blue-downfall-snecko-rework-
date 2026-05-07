package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.DrawCardFromStashAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Shell extends AbstractBronzeCard {

    public final static String ID = makeID("Shell");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 5;

    public Shell() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Shell.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            atb(new DrawCardFromStashAction());
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}