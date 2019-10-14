package seedu.address.model.entity.worker;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;

/**
 * Tests that a {@code Entity}'s {@code Name} matches any of the keywords given.
 */
public class WorkerContainsAttributesKeywordsPredicate implements Predicate<Worker> {
    private final ArgumentMultimap argumentMultimap;

    public WorkerContainsAttributesKeywordsPredicate(ArgumentMultimap argumentMultimap) {
        this.argumentMultimap = argumentMultimap;
    }

    @Override
    public boolean test(Worker worker) {
        boolean workerMatch = workerMatch(argumentMultimap, worker, PREFIX_FIRST_NAME, PREFIX_LAST_NAME,
                PREFIX_PHONE, PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION,
                PREFIX_EMPLOYMENT_STATUS);
        return workerMatch;
    }

    /**
     * Returns true if any of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean workerMatch(ArgumentMultimap argumentMultimap, Worker worker, Prefix... prefixes) {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkerContainsAttributesKeywordsPredicate // instanceof handles nulls
                && argumentMultimap.equals(((WorkerContainsAttributesKeywordsPredicate) other).argumentMultimap)); // state check
    }

}
