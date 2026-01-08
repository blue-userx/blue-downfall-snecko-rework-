package donudeca.relics;

import donudeca.DonuDecaChar;

import static donudeca.DonuDecaMod.makeID;


public class TodoItem extends yourmod.relics.AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, DonuDecaChar.Enums.CONSTRUCTS_GORANGE);
    }
}
