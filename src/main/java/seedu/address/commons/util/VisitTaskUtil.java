package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.visittask.Detail;
import seedu.address.model.visittask.VisitTask;
import seedu.address.model.visittodo.VisitTodo;

/**
 * Helper class for Visit Todos
 */
public class VisitTaskUtil {
    /**
     * Creates a list of Visit Tasks from a patient object.
     */
    public static List<VisitTask> listFromPatient(Person patient) {
        requireNonNull(patient);
        return patient.getVisitTodos().stream()
                .map(VisitTaskUtil::newTaskFromTodo)
                .collect(Collectors.toList());
    }

    /**
     * Create a new VisitTask from a VisitTodo object.
     */
    public static VisitTask newTaskFromTodo(VisitTodo todo) {
        requireNonNull(todo);
        return new VisitTask(todo, new Detail(""), false);
    }
}
