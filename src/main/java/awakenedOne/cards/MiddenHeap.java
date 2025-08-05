package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class MiddenHeap extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(MiddenHeap.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public MiddenHeap() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(MiddenHeap.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new MultiGroupSelectAction(
                cardStrings.EXTENDED_DESCRIPTION[magicNumber == 1 ? 0 : 1],
                (cards, groups) -> {
                    Collections.reverse(cards);
                    cards.forEach(c -> att(new AbstractGameAction() {
                        public void update() {
                            isDone = true;
                            if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                                if (groups.get(c) == p.drawPile)
                                    p.drawPile.moveToDiscardPile(c);
                                p.createHandIsFullDialog();
                            } else
                                p.hand.moveToHand(c, groups.get(c));
                        }
                    }));
                },
                magicNumber, false, c -> c.type == CardType.STATUS || c.type == CardType.CURSE, CardGroup.CardGroupType.DRAW_PILE, CardGroup.CardGroupType.DISCARD_PILE
        ));
    }

    @Override
    public void upp() {
        upgradeBlock(1);
        upgradeMagicNumber(1);
    }
}