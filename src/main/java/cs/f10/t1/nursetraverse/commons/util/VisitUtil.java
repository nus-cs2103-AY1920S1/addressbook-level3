package cs.f10.t1.nursetraverse.commons.util;

import java.util.Optional;

import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.visit.Visit;

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
        CollectionUtil.requireAllNonNull(model, messageNoOngoingVisit);
        Optional<Visit> ongoingVisit = model.getOngoingVisit();
        if (ongoingVisit.isEmpty()) {
            throw new CommandException(messageNoOngoingVisit);
        }
        return ongoingVisit.get();
    }
}
