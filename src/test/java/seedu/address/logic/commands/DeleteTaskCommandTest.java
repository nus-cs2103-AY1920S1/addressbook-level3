package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.DeleteTaskCommand.Builder.OPTION_TAGS;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.tasks.TaskSource;

public class DeleteTaskCommandTest {

    @Test
    void build_allParameters_success() {
        String[] indexes = new String[]{"0", "1", "2"};
        String[] tags = new String[]{"a", "b", "c"};

        assertDoesNotThrow(() -> DeleteTaskCommand.newBuilder(null)
            .acceptSentence(indexes[0])
            .acceptSentence(indexes[1])
            .acceptSentence(indexes[2])
            .acceptSentence(OPTION_TAGS)
            .acceptSentence(tags[0])
            .acceptSentence(tags[1])
            .acceptSentence(tags[2])
            .build());
    }

    @Test
    void execute_noParameters_failure() {
        ModelManager model = new ModelManager();

        model.setModelData(new ModelData(
            List.of(),
            List.of(
                TaskSource.newBuilder("a").build(),
                TaskSource.newBuilder("b").build(),
                TaskSource.newBuilder("c").build()
            )));

        assertEquals(3, model.getTasks().size());

        assertThrows(CommandException.class, () -> DeleteTaskCommand.newBuilder(model)
            .build()
            .execute());

        assertEquals(3, model.getTasks().size());
    }

    /* Delete by Index */

    @Test
    void execute_deleteIndexes_success() {
        String[] indexes = new String[]{"1", "2", "3"};

        List<TaskSource> tasks = List.of(
            TaskSource.newBuilder("a").build(),
            TaskSource.newBuilder("b").build(),
            TaskSource.newBuilder("c").build(),
            TaskSource.newBuilder("d").build(),
            TaskSource.newBuilder("e").build());

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(), tasks));
        assertEquals(5, model.getTasks().size());

        assertDoesNotThrow(() -> DeleteTaskCommand.newBuilder(model)
            .acceptSentence(indexes[0])
            .acceptSentence(indexes[1])
            .acceptSentence(indexes[2])
            .build()
            .execute());

        // Delete 'b', 'c', 'd'
        List <TaskSource> modelTasks = model.getTasks();
        assertEquals(2, modelTasks.size());
        assertEquals(tasks.get(0), modelTasks.get(0));
        assertEquals(tasks.get(4), modelTasks.get(1));
    }

    @Test
    void execute_deleteInvalidIndexes_failed() {
        String[] indexes = new String[]{"0", "1", "2"};
        List<TaskSource> tasks = List.of (
                TaskSource.newBuilder("a").build(),
                TaskSource.newBuilder("b").build());
        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(), tasks));
        assertEquals(2, model.getTasks().size());

        assertThrows(CommandException.class, () -> DeleteTaskCommand.newBuilder(model)
            .acceptSentence(indexes[0])
            .acceptSentence(indexes[1])
            .acceptSentence(indexes[2])
            .build()
            .execute());

        assertEquals(2, model.getTasks().size());
    }

    /* Delete by Tags */
    @Test
    void execute_deleteTags_success() {
        String[] tags = new String[]{ "3", "4" };

        List<TaskSource> tasks = List.of(
            TaskSource.newBuilder("a").setTags(List.of("1", "2", "3")).build(),
            TaskSource.newBuilder("b").setTags(List.of("2", "3", "4")).build(),
            TaskSource.newBuilder("c").setTags(List.of("3", "4", "5")).build(),
            TaskSource.newBuilder("d").setTags(List.of("4", "5", "6")).build(),
            TaskSource.newBuilder("e").setTags(List.of("5", "6", "7")).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(), tasks));
        assertEquals(5, model.getTasks().size());

        assertDoesNotThrow(() -> DeleteTaskCommand.newBuilder(model)
            .acceptSentence(OPTION_TAGS)
            .acceptSentence(tags[0])
            .acceptSentence(tags[1])
            .build()
            .execute());

        // Delete 'b' and 'c'
        List<TaskSource> modelTasks = model.getTasks();
        assertEquals(3, modelTasks.size());
        assertEquals(tasks.get(0), modelTasks.get(0));
        assertEquals(tasks.get(3), modelTasks.get(1));
        assertEquals(tasks.get(4), modelTasks.get(2));
    }

    /* Delete by Index and Tags */

    @Test
    void execute_deleteIndexesAndTags_success() {
        String[] indexes = new String[]{ "0", "1" };
        String[] tags = new String[]{ "3", "4" };
        List<TaskSource> tasks = List.of(
            TaskSource.newBuilder("a").setTags(List.of("1", "2", "3")).build(),
            TaskSource.newBuilder("b").setTags(List.of("2", "3", "4")).build(),
            TaskSource.newBuilder("c").setTags(List.of("3", "4", "5")).build(),
            TaskSource.newBuilder("d").setTags(List.of("4", "5", "6")).build(),
            TaskSource.newBuilder("e").setTags(List.of("5", "6", "7")).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(), tasks));
        assertEquals(5, model.getTasks().size());

        assertDoesNotThrow(() -> DeleteTaskCommand.newBuilder(model)
            .acceptSentence(indexes[0])
            .acceptSentence(indexes[1])
            .acceptSentence(OPTION_TAGS)
            .acceptSentence(tags[0])
            .acceptSentence(tags[1])
            .build()
            .execute());

        // Delete 'b'
        List<TaskSource> modelTasks = model.getTasks();
        assertEquals(4, modelTasks.size());
        assertEquals(tasks.get(0), modelTasks.get(0));
        assertEquals(tasks.get(2), modelTasks.get(1));
        assertEquals(tasks.get(3), modelTasks.get(2));
        assertEquals(tasks.get(4), modelTasks.get(3));
    }

    @Test
    void execute_deleteNoMatches_failure() {
        String[] indexes = new String[]{ "0", "1" };
        String[] tags = new String[]{ "5", "6" };

        List<TaskSource> tasks = List.of(
                TaskSource.newBuilder("a").setTags(List.of("1", "2", "3")).build(),
                TaskSource.newBuilder("b").setTags(List.of("2", "3", "4")).build(),
                TaskSource.newBuilder("c").setTags(List.of("3", "4", "5")).build(),
                TaskSource.newBuilder("d").setTags(List.of("4", "5", "6")).build(),
                TaskSource.newBuilder("e").setTags(List.of("5", "6", "7")).build()
        );

        ModelManager model = new ModelManager();
        model.setModelData(new ModelData(List.of(), tasks));
        assertEquals(5, model.getTasks().size());

        assertThrows(CommandException.class, () -> DeleteTaskCommand.newBuilder(model)
            .acceptSentence(indexes[0])
            .acceptSentence(indexes[1])
            .acceptSentence(OPTION_TAGS)
            .acceptSentence(tags[0])
            .acceptSentence(tags[1])
            .build()
            .execute());

        // Check size
        assertEquals(5, model.getTasks().size());
    }
}
