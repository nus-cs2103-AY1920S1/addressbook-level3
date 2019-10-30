//@@author LeowWB

package seedu.address.model.export;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.model.flashcard.FlashCard;

/**
 * Abstract class from which all export paths inherit. A subclass of this would store the path to
 * a file, to which the {@code export} command would write.
 */
public abstract class ExportPath {
    public abstract Path getPath();
    public abstract String toAbsolutePathString();
    public abstract void export(List<FlashCard> list) throws IOException;
}
