package seedu.module.model.module;

import java.util.List;
import java.util.Optional;

/**
 * Represents a Module.
 */
public interface Module {

    String getModuleCode();

    String getTitle();

    String getDescription();

    Optional<String> getPrerequisite();

    Optional<String> getPreclusion();

    SemesterDetailList getSemesterDetails();

    List<Integer> getListOfOfferedSemesters();

    /**
     * Returns true if both modules share the same module code.
     */
    default boolean isSameModule(Module other) {
        if (other == null) {
            return false;
        }

        return this.getModuleCode().equals(other.getModuleCode());
    }

    /**
     * Retruns true if the module is a tracked module.
     */
    default boolean isTracked() {
        return this instanceof Trackable;
    }
}
