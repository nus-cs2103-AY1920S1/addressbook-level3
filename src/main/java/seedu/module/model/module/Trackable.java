package seedu.module.model.module;

import java.util.List;

/**
 * Represents a Module.
 */
public interface Trackable extends Module {
    String getDeadline();
    String getDeadlineTask(int i);
    List<Deadline> getDeadlineList();
    List<Link> getLink();
}
