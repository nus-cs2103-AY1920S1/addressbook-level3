package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.AddTaskCommand.Builder.OPTION_DUE_DATE_TIME;
import static seedu.address.logic.commands.AddTaskCommand.Builder.OPTION_TAGS;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.tasks.TaskSource;

public class AddTaskCommandTest {
    @Test
    void build_allParameters_success() {
        String description = "description";
        String due = "11/11/1111 11:00";
        String[] tags = new String[] {"a", "b", "c"};
        assertDoesNotThrow(() -> AddTaskCommand.newBuilder(null)
            .acceptPhrase(description)
            .acceptPhrase(OPTION_DUE_DATE_TIME)
            .acceptPhrase(due)
            .acceptPhrase(OPTION_TAGS)
            .acceptPhrase(tags[0])
            .acceptPhrase(tags[1])
            .acceptPhrase(tags[2])
            .build());
    }

    @Test
    void execute_addOne_success() {
        String description = "description";

        ModelManager model = new ModelManager();
        assertEquals(0, model.getTasks().size());

        assertDoesNotThrow(() -> AddTaskCommand.newBuilder(model)
            .acceptPhrase(description)
            .build()
            .execute());

        List<TaskSource> modelTasks = model.getTasks();
        assertEquals(1, modelTasks.size());
        assertEquals(TaskSource.newBuilder(description).build(), modelTasks.get(0));
    }

    @Test
    void execute_addDuplicate_failure() {
        String description = "description";
        ModelManager model = new ModelManager();
        assertEquals(0, model.getTasks().size());

        assertDoesNotThrow(() -> AddTaskCommand.newBuilder(model)
            .acceptPhrase(description)
            .build()
            .execute());

        // Check size
        assertEquals(1, model.getTasks().size());

        assertThrows(CommandException.class, () -> AddTaskCommand.newBuilder(model)
            .acceptPhrase(description)
            .build()
            .execute());

        // Check size
        assertEquals(1, model.getTasks().size());
    }
}
