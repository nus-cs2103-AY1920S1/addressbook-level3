package seedu.address.model.entity.body;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentMultimap;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Entity}'s {@code Name} matches any of the keywords given.
 */
public class BodyContainsAttributesKeywordsPredicate implements Predicate<Body> {
    private final ArgumentMultimap argumentMultimap;

    public BodyContainsAttributesKeywordsPredicate(ArgumentMultimap argumentMultimap) {
        this.argumentMultimap = argumentMultimap;
    }

    @Override
    public boolean test(Body body) {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BodyContainsAttributesKeywordsPredicate // instanceof handles nulls
                && argumentMultimap.equals(((BodyContainsAttributesKeywordsPredicate) other).argumentMultimap)); // state check
    }

}
