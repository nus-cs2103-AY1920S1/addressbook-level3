package seedu.address.model.visittodo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VisitTodoTest {

    public static final VisitTodo VALID_VISIT_TODO = new VisitTodo("\"Anything legitimate !@#$%^&*()_+{}:"
            + "\\\"<>?1234567890-=`~,./;'[]\\\\|/\"");
    public static final VisitTodo VALID_VISIT_TODO_SHORT = new VisitTodo("Bloop");


    @Test
    public void constructor() {
        assertThrows(NullPointerException.class, () -> new VisitTodo(null));
        assertThrows(IllegalArgumentException.class, () -> new VisitTodo(""));
        assertDoesNotThrow(() -> new VisitTodo("\"Anything legitimate !@#$%^&*()_+{}:"
                + "\\\"<>?1234567890-=`~,./;'[]\\\\|/\""));
    }
}
