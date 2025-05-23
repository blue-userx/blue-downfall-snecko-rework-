package gremlin.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.GremlinMod;
import gremlin.powers.BangPower;
import gremlin.powers.EncorePower;
import gremlin.powers.WizPower;

import static gremlin.GremlinMod.WIZARD_GREMLIN;
import static hermit.util.Wiz.removePower;

public class Encore extends AbstractGremlinCard {
    public static final String ID = getID("Encore");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/encore.png";

    private static final AbstractCard.CardType TYPE = CardType.POWER;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 1;

    private static final int WIZ = 3;

    private static final int MAGIC = 4;
    private static final int UPGRADE_BONUS = 2;

    public Encore()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.tags.add(WIZARD_GREMLIN);
        setBackgrounds();
        GremlinMod.loadJokeCardImage(this, "Encore.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        this.addToTop(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, BangPower.POWER_ID));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p,
                new EncorePower(p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new WizPower(p, WIZ), WIZ));
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS);
        }
    }
}

