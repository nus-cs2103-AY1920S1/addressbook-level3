package seedu.billboard.storage;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.model.ArchiveWrapper;
import seedu.billboard.model.ReadOnlyArchiveWrapper;
import seedu.billboard.model.archive.Archive;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable Archive that is serializable to JSON format.
 */
@JsonRootName(value = "archives")
class JsonSerializableArchiveWrapper {

    private final List<JsonAdaptedArchive> archives = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableArchiveWrapper} with the given archives.
     */
    @JsonCreator
    public JsonSerializableArchiveWrapper(@JsonProperty("archives") List<JsonAdaptedArchive> archives) {
        this.archives.addAll(archives);
    }

    public JsonSerializableArchiveWrapper(ReadOnlyArchiveWrapper source) {
        List<Archive> archiveList = source.getArchiveList();
        for (Archive archive : archiveList) {
            String archiveName = archive.getArchiveName();
            List<JsonAdaptedExpense> adaptedExpenses = source.getArchiveExpenses(archiveName)
                    .stream().map(JsonAdaptedExpense::new).collect(Collectors.toList());
            archives.add(new JsonAdaptedArchive(archiveName, adaptedExpenses));
        }
    }

    /**
     * Converts this ArchiveWrapper into the model's {@code ArchiveWrapper} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ArchiveWrapper toModelType() throws IllegalValueException {
        ArchiveWrapper archiveWrapper = new ArchiveWrapper();
        for(JsonAdaptedArchive jsonAdaptedArchive : archives) {
            Archive archive = jsonAdaptedArchive.toModelType();
            archiveWrapper.addArchive(archive);
        }

        return archiveWrapper;
    }
}
