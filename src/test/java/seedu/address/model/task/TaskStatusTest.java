package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskStatusTest {

    @Test
    public void statusDisplayName_shouldMatchExpectedDisplayName() {
        assertEquals(TaskStatus.UNBEGUN.getDisplayName(), "Not Started");
        assertEquals(TaskStatus.DOING.getDisplayName(), "In Progress");
        assertEquals(TaskStatus.DONE.getDisplayName(), "Completed");
    }

}
