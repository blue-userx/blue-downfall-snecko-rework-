package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;
@Deprecated
@CardIgnore
public class UnknownUncommonPower extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownUncommonPower");

    public UnknownUncommonPower() {
        super(ID, CardType.POWER, CardRarity.SPECIAL);
        SneckoMod.loadJokeCardImage(this, "UnknownUncommonPower.png");
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.rarity == this.rarity && c.type == this.type;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownUncommonPowerReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerUncommonP;
    }
}
