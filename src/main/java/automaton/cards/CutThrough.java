package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.PlaceActualCardIntoStashAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;
import static collector.util.Wiz.atb;

public class CutThrough extends AbstractBronzeCard {

    public final static String ID = makeID("CutThrough");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public CutThrough() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseAuto = auto = 1;
        //thisEncodes();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("CutThrough.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new ScryAction(magicNumber));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (!AbstractDungeon.player.drawPile.isEmpty())
                    addToTop(new PlaceActualCardIntoStashAction(AbstractDungeon.player.drawPile.getTopCard(), AbstractDungeon.player.drawPile, true));
                //Note: this technically lets you figure out what your top card is by checking draw pile before/after
            }
        });

    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            atb(new DrawCardAction(auto));
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
    }
}