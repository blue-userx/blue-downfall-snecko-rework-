package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static champ.ChampMod.loadJokeCardImage;

public class TornadoPunch extends AbstractChampCard {

    public final static String ID = makeID("TornadoPunch");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 2;

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 2;

    public TornadoPunch() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        isMultiDamage = true;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        postInit();
        loadJokeCardImage(this, "TornadoPunch.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //if (upgraded) techique();
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (dcombo()){
            Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while (var2.hasNext()) {
                AbstractMonster mo = (AbstractMonster) var2.next();
                if (!mo.isDeadOrEscaped()) {
                    blck();
                }
            }
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        //  tags.add(ChampMod.TECHNIQUE);
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}