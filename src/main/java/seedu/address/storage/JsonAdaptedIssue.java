package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Issue}.
 */
class JsonAdaptedIssue {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Issue's %s field is missing!";
    private static final PrefixType[] prefixTypes = PrefixType.values();

    private final String name;
    private final String description;
    private final IssueType issueType;
    private final Boolean isCompleted;
    private final PrefixType prefixType;

    private final int idNum;

    /**
     * Constructs a {@code JsonAdapterIssue} with the given participant details.
     */
    @JsonCreator
    public JsonAdaptedIssue(@JsonProperty("name") String name, @JsonProperty("description") String description,
                            @JsonProperty("issueType") IssueType issueType, @JsonProperty("isCompleted") Boolean isCompleted,
                            @JsonProperty("prefixType") PrefixType prefixType, @JsonProperty("idNum") int idNum) {
        this.name = name;
        this.description = description;
        this.issueType = issueType;
        this.isCompleted = isCompleted;
        this.prefixType = prefixType;
        this.idNum = idNum;
    }

    /**
     * Converts a given {@code Issue} into this class for Jackson use.
     */
    public JsonAdaptedIssue(Issue source) {
        name = source.getName().toString();
        description = source.getDescription();
        isCompleted = source.isCompleted();
        issueType = source.getType();
        prefixType = source.getId().getPrefix();
        idNum = source.getId().getNumber();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Issue} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Issue toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Description"));
        }
        final String modelDescription = description;

        if (isCompleted == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "IsCompleted"));
        }
        final String modelIsCompleted = isCompleted;

        if (issueType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, IssueType.class.getSimpleName()));
        }
        if (!IssueType.isValidIssueType(issueType)) {
            throw new IllegalValueException(IssueType.MESSAGE_CONSTRAINTS);
        }
        final IssueType modelIssueType = issueType;

        if (prefixType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PrefixType.class.getSimpleName()));
        }
        if (!PrefixType.isValidPrefixType(prefixType)) {
            throw new IllegalValueException(PrefixType.MESSAGE_CONSTRAINTS);
        }
        final PrefixType modelPrefixType = prefixType;

//        if (idNum == null) {
//            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ID Number in ID object");
//        }
        //Must find a way to validate idNum
        final int modelIdNum = idNum;
        final Id modelId = new Id(modelPrefixType, modelIdNum);

        return new Issue(modelName, modelId, modelDescription, modelIssueType, modelIsCompleted);
    }

}
