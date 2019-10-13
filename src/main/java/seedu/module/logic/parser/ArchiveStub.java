package seedu.module.logic.parser;

import seedu.module.model.module.TrackedModule;

/**
 * Stub that simulates the creation of module object based on the found archived json module files.
 */
public class ArchiveStub {
    public static TrackedModule searchArchiveStub(String args) {
        return new TrackedModule(args, "stub", "stub");
    }
}
