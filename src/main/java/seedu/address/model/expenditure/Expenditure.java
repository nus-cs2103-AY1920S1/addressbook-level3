package seedu.address.model.expenditure;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;

import java.util.Optional;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Generic abstraction of expenditure.
 */
public class Expenditure {

    // Compulsory fields
    private final Name name;
    private final Budget budget;

    /**
     * Constructs an {@code Expenditure}.
     */
    public Expenditure(Name name, Budget budget) {
        requireAllNonNull(name, budget);
        this.name = name;
        this.budget = budget;
    }


    public Name getName() {
        return name;
    }
    public Budget getBudget() {
        return budget;
    }
}
