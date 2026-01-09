package donudeca.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.megacrit.cardcrawl.unlock.UnlockTracker;
import donudeca.DonuDecaChar;
import hermit.util.TextureLoader;
import hermit.util.Wiz;

import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;
import static donudeca.DonuDecaMod.*;

public abstract class AbstractDonuDecaCard extends CustomCard {

    protected final CardStrings cardStrings;
    public String betaArtPath;

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    private boolean needsArtRefresh = false;

    public AbstractDonuDecaCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, DonuDecaChar.Enums.CONSTRUCTS_GORANGE);
    }

    public AbstractDonuDecaCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();

        if (textureImg.contains("ui/missing.png")) {
            if (CardLibrary.cards != null && !CardLibrary.cards.isEmpty()) {
            } else
                needsArtRefresh = true;
        }
    }

    public static String getCardTextureString(final String cardName, final CardType cardType) {
        String textureString = "donudecaResources/images/cards/" + cardName + ".png";
        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            textureString = "donudecaResources/images/cards/joke/" + cardName + ".png";
            h = Gdx.files.internal(textureString);
        }
        if (!h.exists()) {
            textureString = "donudecaResources/images/cards/programmerart/" + cardName + ".png";
            h = Gdx.files.internal(textureString);
        }
        if (!h.exists()) {
            textureString = "donudecaResources/images/ui/missing.png";
        }
        return textureString;
    }

    @Override
    protected Texture getPortraitImage() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.cardID, false)) {
            if (this.textureImg == null) {
                return null;
            } else {
                if (betaArtPath != null) {
                    int endingIndex = betaArtPath.lastIndexOf(".");
                    String newPath = betaArtPath.substring(0, endingIndex) + "_p" + betaArtPath.substring(endingIndex);
                    System.out.println("Finding texture: " + newPath);
                    Texture portraitTexture;
                    portraitTexture = TextureLoader.getTexture(newPath);
                    return portraitTexture;
                }
            }
        }
        return super.getPortraitImage();
    }

    //packmaster code
    public ArrayList<AbstractCard> getNeighbors() {
        ArrayList<AbstractCard> neighbors = new ArrayList<>();
        if (Wiz.hand().contains((AbstractCard)this)) {
            int index = (Wiz.hand()).group.indexOf(this);
            if (index > 0)
                neighbors.add((Wiz.hand()).group.get(index - 1));
            if (index < Wiz.hand().size() - 1)
                neighbors.add((Wiz.hand()).group.get(index + 1));
        }
        return neighbors;
    }

    //m10 robot code
    public boolean canSwap() {
        return this.hasTag(FLIP);
    }

    public void onSwapOut() {}

    public void onSwapIn() {}


    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    protected void uDesc() {
        this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded && !this.hasTag(SEARING)) {
            this.upgradeName();
            this.upp();
            if (this.cardStrings.UPGRADE_DESCRIPTION != null) {
                this.uDesc();
            }
        }
            if (this.hasTag(SEARING)){
                ++this.timesUpgraded;
                this.upgraded = true;
                this.name = cardStrings.NAME + "+" + this.timesUpgraded;
                this.upp();
                if (this.cardStrings.UPGRADE_DESCRIPTION != null) {
                    this.uDesc();
                }
            }
        }

    public abstract void upp();

    public void update() {
        super.update();
    }

    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard result = super.makeStatEquivalentCopy();
        if (result instanceof AbstractDonuDecaCard) {
            AbstractDonuDecaCard c = (AbstractDonuDecaCard) result;
            c.baseSecondDamage = c.secondDamage = baseSecondDamage;
            c.baseSecondMagic = c.secondMagic = baseSecondMagic;
        }
        return result;
    }

    // These shortcuts are specifically for cards. All other shortcuts that aren't specifically for cards can go in Wiz.
    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void allDmgTop(AbstractGameAction.AttackEffect fx) {
        att(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void altDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    protected void altDmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    private AbstractGameAction dmgRandomAction(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget, Consumer<AbstractMonster> effectBefore) {
        return actionify(() -> {
            AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (target != null) {
                calculateCardDamage(target);
                if (extraEffectToTarget != null)
                    extraEffectToTarget.accept(target);
                att(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
                if (effectBefore != null)
                    effectBefore.accept(target);
            }
        });
    }

    protected void dmgRandom(AbstractGameAction.AttackEffect fx) {
        dmgRandom(fx, null, null);
    }

    protected void dmgRandom(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget, Consumer<AbstractMonster> effectBefore) {
        atb(dmgRandomAction(fx, extraEffectToTarget, effectBefore));
    }

    protected void dmgRandomTop(AbstractGameAction.AttackEffect fx) {
        dmgRandomTop(fx, null, null);
    }

    protected void dmgRandomTop(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget, Consumer<AbstractMonster> effectBefore) {
        att(dmgRandomAction(fx, extraEffectToTarget, effectBefore));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected void blckTop() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public String cardArtCopy() {
        return null;
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return null;
    }
}
