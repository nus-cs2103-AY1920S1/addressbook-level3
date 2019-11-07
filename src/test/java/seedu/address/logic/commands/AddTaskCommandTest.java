package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.logic.commands.AddTaskCommandBuilder.OPTION_DUE_DATE_TIME;
import static seedu.address.logic.commands.AddTaskCommandBuilder.OPTION_TAGS;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;

public class AddTaskCommandTest {
    @Test
    void build_fullCommand_success() {
        String description = "description";
        String due = "11/11/1111 12:00";
        String[] tags = new String[]{"a", "b", "c"};

        assertDoesNotThrow(() -> {
            AddTaskCommand.newBuilder(null)
                .acceptSentence(description)
                .acceptSentence(OPTION_DUE_DATE_TIME)
                .acceptSentence(due)
                .acceptSentence(OPTION_TAGS)
                .acceptSentence(tags[0])
                .acceptSentence(tags[1])
                .acceptSentence(tags[2])
                .build();
        });
    }

    @Test
    void execute_requiredCommand_success() {
        String description = "description";

        assertDoesNotThrow(() -> {
            // TODO: Create stub
            ModelManager model = new ModelManager();
            assertEquals(model.getTaskList().size(), 0);

            Command command = AddTaskCommand.newBuilder(model)
                    .acceptSentence(description)
                    .build();

            // TODO: Equality test
            command.execute();
            assertEquals(model.getTaskList().size(), 1);
        });
    }
}
