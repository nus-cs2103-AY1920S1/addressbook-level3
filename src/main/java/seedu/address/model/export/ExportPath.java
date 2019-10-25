//@@author LeowWB

package seedu.address.model.export;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.model.flashcard.FlashCard;

public abstract class ExportPath {
    public abstract Path getPath();
    public abstract String toAbsolutePathString();
    public abstract void export(List<FlashCard> list) throws IOException;
}
