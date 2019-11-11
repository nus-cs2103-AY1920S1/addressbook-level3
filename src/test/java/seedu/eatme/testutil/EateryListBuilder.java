package seedu.eatme.testutil;

import seedu.eatme.model.EateryList;
import seedu.eatme.model.eatery.Eatery;

/**
 * A utility class to help with building EateryList objects.
 * Example usage: <br>
 *     {@code EateryList el = new EateryListBuilder().withEatery("John", "Doe").build();}
 */
public class EateryListBuilder {

    private EateryList eateryList;

    public EateryListBuilder() {
        eateryList = new EateryList();
    }

    public EateryListBuilder(EateryList eateryList) {
        this.eateryList = eateryList;
    }

    /**
     * Adds a new {@code Eatery} to the {@code EateryList} that we are building.
     */
    public EateryListBuilder withEatery(Eatery eatery) {
        eateryList.addEatery(eatery);
        return this;
    }

    public EateryList build() {
        return eateryList;
    }
}
