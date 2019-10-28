package seedu.module.model.module;

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
}
