package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Description;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Title;

/**
 * Jackson-friendly version of {@link Module}.
 */
public class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String title;
    private final String description;
    private final String acadYear;
    private final List<JsonAdaptedSemester> semesterData = new ArrayList<>();
    /**
     * Constructs a {@code JsonAdaptedSchedule} with the given Schedule details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("title") String title,
                             @JsonProperty("description") String description,
                             @JsonProperty("acadYear") String acadYear,
                             @JsonProperty("semesterData") List<JsonAdaptedSemester> semesterData) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.acadYear = acadYear;
        if (semesterData != null) {
            this.semesterData.addAll(semesterData);
        }
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().toString();
        title = source.getTitle().toString();
        description = source.getDescription().toString();
        acadYear = source.getAcadYear().toString();
        semesterData.addAll(source.getSemesterData().stream()
                .map(JsonAdaptedSemester::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted schedule object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {
        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Title.class.getSimpleName()));
        }
        final Title modelModuleTitle = new Title(title);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        if (acadYear == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AcadYear.class.getSimpleName()));
        }
        final AcadYear modelAcadYear = new AcadYear(acadYear);

        final List<Semester> modelSemesterData = new ArrayList<>();
        for (JsonAdaptedSemester semester : semesterData) {
            modelSemesterData.add(semester.toModelType());
        }

        return new Module(modelModuleCode, modelModuleTitle, modelDescription, modelAcadYear, modelSemesterData);
    }
}
