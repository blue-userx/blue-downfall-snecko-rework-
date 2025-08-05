package champ.cards;

import champ.ChampMod;
import champ.powers.EntangleNextTurnPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class SwordThrow extends AbstractChampCard {
    public final static String ID = makeID("SwordThrow");

    public SwordThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.COMBOBERSERKER);
        tags.add(ChampMod.COMBO);
        postInit();
        loadJokeCardImage(this, "SwordThrow.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) dmg(m, AbstractGameAction.AttackEffect.SMASH);
        if (!bcombo()) applyToSelf(new EntangleNextTurnPower(1));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : Color.RED.cpy();
    }

    public void upp() {
        upgradeDamage(4);
    }
}