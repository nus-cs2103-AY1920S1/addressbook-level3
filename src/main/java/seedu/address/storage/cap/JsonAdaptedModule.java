package seedu.address.storage.cap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cap.person.Credit;
import seedu.address.model.cap.person.Description;
import seedu.address.model.cap.person.Faculty;
import seedu.address.model.cap.person.ModuleCode;
import seedu.address.model.cap.person.Title;
import seedu.address.model.common.Module;


/**
 * Jackson-friendly version of {@link ModuleCode}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String title;
    private final int credit;
    private final String faculty;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("module_code") String moduleCode, @JsonProperty("title") String title,
                             @JsonProperty("credit") int credit, @JsonProperty("faculty") String faculty,
                             @JsonProperty("faculty") String description) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.credit = credit;
        this.faculty = faculty;
        this.description = description;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().moduleCode;
        title = source.getTitle().title;
        credit = source.getCredit().getCredit();
        faculty = source.getFaculty().getFaculty();
        description = source.getDescription().getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @return a Module
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Module toModelType() throws IllegalValueException {

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidName(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelName = new ModuleCode(moduleCode);

        final Title modelTitle = new Title(title);
        final Description modelDescription = new Description(description);
        final Credit modelCredit = new Credit(credit);
        final Faculty modelFaculty = new Faculty(faculty);

        return new Module(modelName, modelTitle, modelDescription, modelCredit, modelFaculty);
    }

}
