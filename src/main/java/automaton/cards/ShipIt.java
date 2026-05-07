package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShipIt extends AbstractBronzeCard {

    public final static String ID = makeID("ShipIt");

    // Attack card constants
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 2;

    public ShipIt() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("ShipIt.png"));
    }

    //public static int countCards() {
    //        int statusCount = 0;
    //        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
    //            if (c.type == CardType.STATUS) {
    //                statusCount++;
    //            }
    //        }
    //        return statusCount;
    //    }
    //
    //    public void applyPowers() {
    //        super.applyPowers();
    //
    //        if (AbstractDungeon.player != null) {
    //            this.rawDescription = cardStrings.DESCRIPTION;
    //
    //            int statusCount = 0;
    //            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
    //                if (c.type == CardType.STATUS) {
    //                    statusCount++;
    //                }
    //            }
    //
    //            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + statusCount + cardStrings.EXTENDED_DESCRIPTION[1];
    //
    //            this.initializeDescription();
    //        }
    //    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        atb(new DrawCardAction(auto));
    }

    @Override
    public void upp() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}
