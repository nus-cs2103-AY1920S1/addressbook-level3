package seedu.address.commons.util;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.visit.Visit;

/**
 * Helper class for Visit Todos
 */
public class VisitUtil {
    /**
     * Get ongoing visit if exists. For Visit Commands that require an ongoing visit.
     */
    public static Visit verifyOngoingVisitExistsAndGet(Model model, String MESSAGE_NO_ONGOING_VISIT)
            throws CommandException {
        Optional<Visit> ongoingVisit = model.getOngoingVisit();
        if (ongoingVisit.isEmpty()) {
            throw new CommandException(MESSAGE_NO_ONGOING_VISIT);
        }
        return ongoingVisit.get();
    }
}
