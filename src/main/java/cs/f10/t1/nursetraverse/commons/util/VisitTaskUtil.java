package cs.f10.t1.nursetraverse.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visittask.Detail;
import cs.f10.t1.nursetraverse.model.visittask.VisitTask;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;

/**
 * Helper class for Visit Todos
 */
public class VisitTaskUtil {
    /**
     * Creates a list of Visit Tasks from a patient object.
     */
    public static List<VisitTask> listFromPatient(Patient patient) {
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
