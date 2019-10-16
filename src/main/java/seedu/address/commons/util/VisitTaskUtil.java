package seedu.address.commons.util;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.visittask.Detail;
import seedu.address.model.visittask.VisitTask;

/**
 * Helper class for Visit Todos
 */
public class VisitTaskUtil {
    /**
     * Creates a list of Visit Tasks from a patient object.
     */
    public static List<VisitTask> listFromPatient(Person patient) {
        return patient.getVisitTodos().stream()
                .map(visitTodo -> new VisitTask(visitTodo, new Detail(""), false))
                .collect(Collectors.toList());
    }
}
