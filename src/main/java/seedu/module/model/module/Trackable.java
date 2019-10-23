package seedu.module.model.module;

import java.util.List;

/**
 * Represents a Module.
 */
public interface Trackable extends Module {
    String getDeadline();
    List<Link> getLink();
}
