package sneckomod.cards.unknowns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;

import java.util.ArrayList;
import java.util.function.Predicate;
@Deprecated
@CardIgnore
public class UnknownStrike extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownStrike");

    public UnknownStrike() {
        super(ID, CardType.ATTACK, CardRarity.SPECIAL);
        tags.add(CardTags.STRIKE);
        SneckoMod.loadJokeCardImage(this, "UnknownStrike.png");
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.hasTag(CardTags.STRIKE) && c.rarity != CardRarity.BASIC;
    }

    @Override
    public ArrayList<String> myList() {
        return AbstractUnknownCard.unknownStrikeReplacements;
    }

    @Override
    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerStrike;
    }
}
