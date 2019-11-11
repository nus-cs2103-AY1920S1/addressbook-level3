package seedu.weme.storage;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.records.Records;
import seedu.weme.model.records.RecordsManager;

/**
 * An Immutable Records that is serializable to JSON format.
 */
@JsonRootName(value = "records")
public class JsonSerializableRecords {


    private Set<String> pathRecords = new HashSet<>();
    private Set<String> descriptionRecords = new HashSet<>();
    private Set<String> tagRecords = new HashSet<>();
    private Set<String> nameRecords = new HashSet<>();
    private Set<String> textRecords = new HashSet<>();

    /**
     * Constructs a {@code JsonSerializableRecords} with the given records details.
     */
    @JsonCreator
    public JsonSerializableRecords(@JsonProperty("pathRecords") Set<String> pathRecords,
                                    @JsonProperty("descriptionRecords") Set<String> descriptionRecords,
                                    @JsonProperty("tagRecords") Set<String> tagRecords,
                                    @JsonProperty("nameRecords") Set<String> nameRecords,
                                   @JsonProperty("textRecords") Set<String> textRecords) {

        this.pathRecords.addAll(pathRecords);
        this.descriptionRecords.addAll(descriptionRecords);
        this.tagRecords.addAll(tagRecords);
        this.nameRecords.addAll(nameRecords);
        this.textRecords.addAll(textRecords);
    }

    /**
     * Converts a given {@code Records} into this class for Jackson use.
     */
    public JsonSerializableRecords(Records records) {
        pathRecords.addAll(records.getPaths());
        descriptionRecords.addAll(records.getDescriptions());
        tagRecords.addAll(records.getTags());
        nameRecords.addAll(records.getNames());
        textRecords.addAll(records.getTexts());
    }

    /**
     * Converts this serializable records object into the model's {@code Records} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the serializable records.
     */
    public Records toModelType() throws IllegalValueException {
        Set<String> pathRecords = new HashSet<>();
        Set<String> descriptionRecords = new HashSet<>();
        Set<String> tagRecords = new HashSet<>();
        Set<String> nameRecords = new HashSet<>();
        Set<String> textRecords = new HashSet<>();

        pathRecords.addAll(this.pathRecords);
        descriptionRecords.addAll(this.descriptionRecords);
        tagRecords.addAll(this.tagRecords);
        nameRecords.addAll(this.nameRecords);
        textRecords.addAll(this.textRecords);
        return new RecordsManager(pathRecords, descriptionRecords, tagRecords, nameRecords, textRecords);
    }

}
