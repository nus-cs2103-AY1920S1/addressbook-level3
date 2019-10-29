package seedu.address.storage.event;

import java.io.IOException;

import seedu.address.model.event.ReadOnlyVEvents;

public interface EventExport {
    String exportEvent(String targetDirectory, ReadOnlyVEvents eventRecord) throws IOException;
}
