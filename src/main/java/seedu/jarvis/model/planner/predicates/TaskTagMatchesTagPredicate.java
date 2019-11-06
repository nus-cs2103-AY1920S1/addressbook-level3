package seedu.jarvis.model.planner.predicates;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.tasks.Task;

import java.util.function.Predicate;

/**
 * Tests that the tags of a {@code Task} matches the tag given
 */
//TODO test
public class TaskTagMatchesTagPredicate implements Predicate<Task> {
    private final Tag tag;

    public TaskTagMatchesTagPredicate(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Task task) {
        return task.getTags().contains(tag);
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof TaskTagMatchesTagPredicate
                && tag.equals(((TaskTagMatchesTagPredicate) other).tag));
    }
}
