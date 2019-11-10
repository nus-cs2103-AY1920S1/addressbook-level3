//@@author SakuraBlossom
package seedu.address.storage.event;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAppointmentBook;

/**
 * A class to access AppointmentBook data stored as a json file on the hard disk.
 */
public class JsonAppointmentBookStorage implements AppointmentBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAppointmentBookStorage.class);

    @Override
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook(Path filePath, boolean areStaffMembers)
            throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAppointmentBook> jsonAppointmentBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAppointmentBook.class);
        if (!jsonAppointmentBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAppointmentBook.get().toModelType(areStaffMembers));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAppointmentBook(ReadOnlyAppointmentBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAppointmentBook(addressBook), filePath);
    }

}
