package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.EditTaskCommand.Builder.OPTION_DESCRIPTION;
import static seedu.address.logic.commands.EditTaskCommand.Builder.OPTION_DUE_DATE_DATE_TIME;
import static seedu.address.logic.commands.EditTaskCommand.Builder.OPTION_TAGS;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.tasks.TaskSource;

public class EditTaskCommandTest {

    @Test
    void build_allParameters_success() {
        String[] indexes = new String[]{"1", "2", "3"};
        String description = "description";
        String due = "11/11/1111 11:00";
        String[] tags = new String[]{"a", "b", "c"};
        assertDoesNotThrow(() -> EditTaskCommand.newBuilder(null)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(indexes[2])
            .acceptPhrase(OPTION_DESCRIPTION)
            .acceptPhrase(description)
            .acceptPhrase(OPTION_DUE_DATE_DATE_TIME)
            .acceptPhrase(due)
            .acceptPhrase(OPTION_TAGS)
            .acceptPhrase(tags[0])
            .acceptPhrase(tags[1])
            .acceptPhrase(tags[2])
            .build());
    }

    @Test
    void execute_notIndexes_failure() {
        String description = "description";

        ModelManager model = new ModelManager();
        List<TaskSource> tasks = List.of(
             TaskSource.newBuilder("a").build(),
             TaskSource.newBuilder("b").build(),
             TaskSource.newBuilder("c").build());

        model.setModelData(new ModelData(List.of(), tasks));
        assertEquals(3, model.getTasks().size());

        assertThrows(CommandException.class, () -> EditTaskCommand.newBuilder(model)
            .acceptPhrase(OPTION_DESCRIPTION)
            .acceptPhrase(description)
            .build()
            .execute());

        List<TaskSource> modelTasks = model.getTasks();
        assertEquals(3, modelTasks.size());
        assertEquals(tasks.get(0), modelTasks.get(0));
        assertEquals(tasks.get(1), modelTasks.get(1));
        assertEquals(tasks.get(2), modelTasks.get(2));
    }

    @Test
    void execute_noParameters_failure() {
        String[] indexes = new String[]{"0", "1", "2"};

        ModelManager model = new ModelManager();
        List<TaskSource> tasks = List.of(
                TaskSource.newBuilder("a").build(),
                TaskSource.newBuilder("b").build(),
                TaskSource.newBuilder("c").build());

        model.setModelData(new ModelData(List.of(), tasks));
        assertEquals(3, model.getTasks().size());

        assertThrows(CommandException.class, () -> EditTaskCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(indexes[2])
            .build()
            .execute());

        List<TaskSource> modelTasks = model.getTasks();
        assertEquals(3, modelTasks.size());
        assertEquals(tasks.get(0), modelTasks.get(0));
        assertEquals(tasks.get(1), modelTasks.get(1));
        assertEquals(tasks.get(2), modelTasks.get(2));
    }

    @Test
    void execute_editDescriptions_success() {
        String[] indexes = new String[]{"0", "1"};
        String description = "description";
        List<TaskSource> oldTasks = List.of (
            TaskSource.newBuilder("a").setTags(List.of("1")).build(),
            TaskSource.newBuilder("b").setTags(List.of("2")).build(),
            TaskSource.newBuilder("c").build()
        );

        List<TaskSource> newTasks = List.of(
                TaskSource.newBuilder(description).setTags(List.of("1")).build(),
                TaskSource.newBuilder(description).setTags(List.of("2")).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(), oldTasks));
        assertEquals(3, model.getTasks().size());

        assertDoesNotThrow(() -> EditTaskCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_DESCRIPTION)
            .acceptPhrase(description)
            .build()
            .execute());

        List<TaskSource> modelTasks = model.getTasks();
        assertEquals(3, modelTasks.size());
        assertEquals(newTasks.get(0), modelTasks.get(0));
        assertEquals(newTasks.get(1), modelTasks.get(1));
        assertEquals(oldTasks.get(2), modelTasks.get(2));
    }


    /* Due */
    @Test
    void execute_editDue_success() {
        String[] indexes = new String[]{"0", "1"};
        DateTime start = DateTime.now();
        DateTime due = start.plus(Duration.ofHours(-1));

        List<TaskSource> oldTasks = List.of(
            TaskSource.newBuilder("a").build(),
            TaskSource.newBuilder("b").build(),
            TaskSource.newBuilder("c").build()
        );

        List<TaskSource> newTasks = List.of(
            TaskSource.newBuilder("a").setDueDate(due).build(),
            TaskSource.newBuilder("b").setDueDate(due).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(), oldTasks));
        assertEquals(3, model.getTasks().size());

        assertDoesNotThrow(() -> EditTaskCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_DUE_DATE_DATE_TIME)
            .acceptPhrase(due.toUserString())
            .build()
            .execute());

        List<TaskSource> modelTasks = model.getTasks();
        assertEquals(3, modelTasks.size());
        assertEquals(newTasks.get(0), modelTasks.get(0));
        assertEquals(newTasks.get(1), modelTasks.get(1));
        assertEquals(oldTasks.get(2), modelTasks.get(2));
    }

    /* Tags */
    @Test
    void execute_editTags_success() {
        String[] indexes = new String[]{"0", "1"};
        List<String> tags = List.of("0", "1", "2");
        List<TaskSource> oldTasks = List.of(
                TaskSource.newBuilder("a").build(),
                TaskSource.newBuilder("b").build(),
                TaskSource.newBuilder("c").build()
        );

        List<TaskSource> newTasks = List.of(
            TaskSource.newBuilder("a").setTags(tags).build(),
            TaskSource.newBuilder("b").setTags(tags).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(), oldTasks));
        assertEquals(3, model.getTasks().size());

        assertDoesNotThrow(() -> EditTaskCommand.newBuilder(model)
                .acceptPhrase(indexes[0])
                .acceptPhrase(indexes[1])
                .acceptPhrase(OPTION_TAGS)
                .acceptPhrase(tags.get(0))
                .acceptPhrase(tags.get(1))
                .acceptPhrase(tags.get(2))
                .build()
                .execute()
        );

        List<TaskSource> modelTasks = model.getTasks();
        assertEquals(newTasks.get(0), modelTasks.get(0));
        assertEquals(newTasks.get(1), modelTasks.get(1));
        assertEquals(oldTasks.get(2), modelTasks.get(2));
    }

    @Test
    void execute_editMultipleInvalidIndexes_failed() {
        String[] indexes = new String[]{"0", "1", "2"};
        String description = "description";

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(),
                List.of(
                        TaskSource.newBuilder("a").build(),
                        TaskSource.newBuilder("b").build()
                )));
        assertEquals(2, model.getTasks().size());

        assertThrows(CommandException.class, () -> EditTaskCommand.newBuilder(model)
                .acceptPhrase(indexes[0])
                .acceptPhrase(indexes[1])
                .acceptPhrase(indexes[2])
                .acceptPhrase(OPTION_DESCRIPTION)
                .acceptPhrase(description)
                .build()
                .execute());

        assertEquals(2, model.getTasks().size());
    }

    /* Duplicates */
    @Test
    void execute_editDuplicate_failure() {
        String[] indexes = new String[]{"0", "1", "2"};
        String description = "description";

        List<TaskSource> tasks = List.of(
            TaskSource.newBuilder("a").build(),
            TaskSource.newBuilder("b").build(),
            TaskSource.newBuilder("c").build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(), tasks));
        assertEquals(3, model.getTasks().size());

        assertThrows(CommandException.class, () -> EditTaskCommand.newBuilder(model)
            .acceptPhrase(indexes[0])
            .acceptPhrase(indexes[1])
            .acceptPhrase(OPTION_DESCRIPTION)
            .acceptPhrase(description)
            .build()
            .execute());

        List<TaskSource> modelTasks = model.getTasks();
        assertEquals(3, modelTasks.size());
        assertEquals(tasks.get(0), modelTasks.get(0));
        assertEquals(tasks.get(1), modelTasks.get(1));
        assertEquals(tasks.get(2), modelTasks.get(2));
    }
}
