package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;

public class UnleashSpirits extends AbstractHexaCard {

    public final static String ID = makeID("UnleashSpirits");

    private static final int DAMAGE = 10;

    public UnleashSpirits() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = 0;
        HexaMod.loadJokeCardImage(this, "UnleashSpirits.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageRandomEnemyAction(makeInfo(), AbstractGameAction.AttackEffect.FIRE));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
//                int i = 0;
//                for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
//                    if (c.isEthereal) {
//                        i++;
//                    }
//                }
                for (int q = 0; q < HexaMod.cards_exhausted_last_turn; q++) {
                    addToTop(new DamageRandomEnemyAction(makeInfo(), AbstractGameAction.AttackEffect.FIRE));
                }
            }
        });
    }

    public void applyPowers() {
        baseMagicNumber = magicNumber = HexaMod.cards_exhausted_last_turn + 1;
        super.applyPowers();
        this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

//    public int countCards(){
//        int count=0;
//        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
//            if (c.isEthereal) {
//                count++;
//            }
//        }
//        return count;
//    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}