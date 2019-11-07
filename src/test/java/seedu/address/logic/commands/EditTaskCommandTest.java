package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.EditTaskCommandBuilder.OPTION_DESCRIPTION;
import static seedu.address.logic.commands.EditTaskCommandBuilder.OPTION_DUE_DATE_DATE_TIME;
import static seedu.address.logic.commands.EditTaskCommandBuilder.OPTION_TAGS;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.tasks.TaskSource;


public class EditTaskCommandTest {

    @Test
    void build_fullCommand_success() {
        String[] indexes = new String[]{"1", "2", "3"};
        String description = "description";
        String due = "11/11/1111 12:00";
        String[] tags = new String[]{"a", "b", "c"};

        assertDoesNotThrow(() -> {
            EditTaskCommand.newBuilder(null)
                    .acceptSentence(indexes[0])
                    .acceptSentence(indexes[1])
                    .acceptSentence(indexes[2])
                    .acceptSentence(OPTION_DESCRIPTION)
                    .acceptSentence(description)
                    .acceptSentence(OPTION_DUE_DATE_DATE_TIME)
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
        String[] indexes = new String[]{"1", "2", "3"};
        assertThrows(CommandException.class, () -> {

            ModelManager model = new ModelManager();
            model.addTasks(
                    TaskSource.newBuilder("a").build(),
                    TaskSource.newBuilder("b").build(),
                    TaskSource.newBuilder("c").build()
            );

            Command command = EditTaskCommand.newBuilder(model)
                    .acceptSentence(indexes[0])
                    .acceptSentence(indexes[1])
                    .acceptSentence(indexes[2])
                    .build();

            assertEquals(model.getTaskList().size(), 3);

            // TODO: Equality test
            command.execute();
            assertEquals(model.getTaskList().size(), 3);
        });
    }

    @Test
    void execute_editOneInvalidIndex_failed() {
        String[] indexes = new String[]{ "-1", "0", "1" };
        for (String index : indexes) {
            assertThrows(CommandException.class, () -> {
                ModelManager model = new ModelManager();
                assertEquals(model.getTaskList().size(), 0);

                Command command = EditTaskCommand.newBuilder(model)
                    .acceptSentence(index)
                    .build();

                command.execute();
            });
        }
    }

    @Test
    void execute_editMultipleInvalidIndex_failed() {
        String[] indexes = new String[]{"1", "2", "3"};
        assertThrows(CommandException.class, () -> {
            ModelManager model = new ModelManager();
            model.addTasks(
                    TaskSource.newBuilder("a").build(),
                    TaskSource.newBuilder("b").build()
            );
            assertEquals(model.getTaskList().size(), 2);

            Command command = EditTaskCommand.newBuilder(model)
                .acceptSentence(indexes[0])
                .acceptSentence(indexes[1])
                .acceptSentence(indexes[2])
                .build();

            command.execute();
        });
    }
}
