package automaton.cards;

import automaton.AutomatonMod;
import champ.actions.ModifyMagicAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class Iterate extends AbstractBronzeCard {

    public final static String ID = makeID("Iterate");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public Iterate() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        //thisEncodes();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("Iterate.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        }
        atb(new ModifyMagicAction(this.uuid, 1));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}