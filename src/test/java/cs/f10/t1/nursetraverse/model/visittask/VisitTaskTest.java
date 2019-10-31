package cs.f10.t1.nursetraverse.model.visittask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cs.f10.t1.nursetraverse.model.visittodo.VisitTodoTest;

public class VisitTaskTest {
    public static final Detail VALID_DETAIL = new Detail("");
    public static final VisitTask VALID_TASK = new VisitTask(VisitTodoTest.VALID_VISIT_TODO, VALID_DETAIL,
            true);
    public static final VisitTask VALID_TASK_UNDONE = new VisitTask(VisitTodoTest.VALID_VISIT_TODO, VALID_DETAIL,
            false);
    public static final Detail VALID_DETAIL_FILLED = new Detail("\"Anything legitimate !@#$%^&*()_+{}:"
            + "\\\"<>?1234567890-=`~,./;'[]\\\\|/\"");
    @Test
    public void equals() {
        assertTrue(() -> VALID_TASK.equals(VALID_TASK));
        assertFalse(() -> VALID_TASK.equals(VALID_DETAIL));
        assertFalse(() -> VALID_TASK.equals(VALID_TASK_UNDONE));
    }
    @Test
    public void testToString() {
        VisitTask task2 = new VisitTask(VisitTodoTest.VALID_VISIT_TODO, VALID_DETAIL, true);
        VisitTask task3 = new VisitTask(VisitTodoTest.VALID_VISIT_TODO, VALID_DETAIL, false);
        VisitTask task4 = new VisitTask(VisitTodoTest.VALID_VISIT_TODO, VALID_DETAIL_FILLED, true);
        VisitTask task5 = new VisitTask(VisitTodoTest.VALID_VISIT_TODO_SHORT, VALID_DETAIL_FILLED, true);
        assertEquals(VALID_TASK.toString(), task2.toString());
        assertNotEquals(VALID_TASK.toString(), task3.toString());
        assertNotEquals(VALID_TASK.toString(), task4.toString());
        assertNotEquals(VALID_TASK.toString(), task5.toString());
        assertNotEquals(VALID_TASK.toString(), task5.toString());
    }
}
