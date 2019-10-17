package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ProjectDashboard;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Contains utility methods for populating {@code ProjectDashboard} with sample data.
 */
public class SampleTaskDataUtil {
    public static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Name("Review Budget"), TaskStatus.UNBEGUN, getTagSet("Finance"),
                LocalDateTime.now().plusWeeks(1)),
            new Task(new Name("Increase Funding"), TaskStatus.DOING,
                getTagSet("Finance", "Urgent"), LocalDateTime.now().plusWeeks(1)),
            new Task(new Name("Settle Claims"), TaskStatus.DOING, getTagSet("Finance")),
            new Task(new Name("Update Website"), TaskStatus.DONE, getTagSet("Branding"),
                LocalDateTime.now().plusWeeks(1)),
            new Task(new Name("Shirts for Freshman Open Day"), TaskStatus.DOING, getTagSet("Logistics")),
            new Task(new Name("Design Poster"), TaskStatus.UNBEGUN,
                getTagSet("Branding"), LocalDateTime.now().plusWeeks(7))
        };
    }

    public static ReadOnlyProjectDashboard getSampleProjectDashboard() {
        ProjectDashboard samplePd = new ProjectDashboard();
        for (Task sampleTask : getSampleTasks()) {
            samplePd.addTask(sampleTask);
        }
        return samplePd;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
