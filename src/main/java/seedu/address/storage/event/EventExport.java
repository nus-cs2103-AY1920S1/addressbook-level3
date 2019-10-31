package seedu.address.storage.event;

import java.io.IOException;

import seedu.address.model.event.ReadOnlyVEvents;

/**
 * Interface for exporting events. Can be implemented for various file types
 */
public interface EventExport {
    String exportEvent(String targetDirectory, ReadOnlyVEvents eventRecord) throws IOException;
}
