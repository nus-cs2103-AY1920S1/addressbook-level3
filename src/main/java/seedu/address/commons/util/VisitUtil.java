package seedu.address.commons.util;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
     * Throws a CommandException if the ongoingVisit is empty.
     */
    public static Visit getOngoingVisitIfExists(Model model, String messageNoOngoingVisit)
            throws CommandException {
        requireAllNonNull(model, messageNoOngoingVisit);
        Optional<Visit> ongoingVisit = model.getOngoingVisit();
        if (ongoingVisit.isEmpty()) {
            throw new CommandException(messageNoOngoingVisit);
        }
        return ongoingVisit.get();
    }
}
