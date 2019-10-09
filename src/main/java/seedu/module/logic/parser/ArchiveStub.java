package seedu.module.logic.parser;

import seedu.module.model.module.Module;

/**
 * Stub that simulates the creation of module object based on the found archived json module files.
 */
public class ArchiveStub {
    public static Module searchArchiveStub(String args) {
        return new Module(args, "stub", "stub");
    }
}
