//@@author LeowWB

package seedu.address.model.export;

import java.nio.file.Path;

public abstract class ExportPath {
    public abstract Path getPath();
    public abstract String toAbsolutePathString();
}
