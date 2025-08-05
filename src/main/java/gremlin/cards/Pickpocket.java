package gremlin.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import gremlin.GremlinMod;
import gremlin.actions.ShackleAction;
import gremlin.actions.StealArtifactAction;

import static gremlin.GremlinMod.MAD_GREMLIN;

public class Pickpocket extends AbstractGremlinCard {
    public static final String ID = getID("Pickpocket");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/pickpocket.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;
    private static final int POWER = 10;
    private static final int MAGIC = 10;
    private static final int UPGRADE_BONUS = 4;

    public Pickpocket()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.baseDamage = POWER;
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
        setBackgrounds();
        GremlinMod.loadJokeCardImage(this, "Pickpocket.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage,
                this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                AbstractDungeon.player.gainGold(magicNumber);
                for (int i = 0; i < magicNumber; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(p, p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY, true));
                }
        }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
            upgradeMagicNumber(4);
        }
    }
}
