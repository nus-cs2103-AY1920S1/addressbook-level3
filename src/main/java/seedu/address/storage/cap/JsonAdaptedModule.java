package seedu.address.storage.cap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.cap.parser.ParserUtil;
import seedu.address.model.cap.module.Credit;
import seedu.address.model.cap.module.Grade;
import seedu.address.model.cap.module.ModuleCode;
import seedu.address.model.cap.module.Semester;
import seedu.address.model.cap.module.Title;
import seedu.address.model.common.Module;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "There should not be null as inputs!";

    private final String moduleCode;
    private final String title;
    private final String semester;
    private final String credit;
    private final String grade;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode, @JsonProperty("title") String title,
                             @JsonProperty("semester") String semester,
                             @JsonProperty("credit") String credit,
                             @JsonProperty("grade") String grade) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.semester = semester;
        this.credit = credit;
        this.grade = grade;
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().moduleCode;
        title = source.getTitle().title;
        semester = source.getSemester().toString();
        credit = String.valueOf(source.getCredit().getCredit());
        grade = source.getGrade().getGrade();
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @return a Module
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Module toModelType() throws IllegalValueException {

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidName(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }

        if (title == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }

        if (semester == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Semester.class.getSimpleName()));
        }
        if (!Semester.isValidSemester(semester)) {
            throw new IllegalValueException(Semester.MESSAGE_CONSTRAINTS);
        }

        if (credit == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Credit.class.getSimpleName()));
        }
        if (!Credit.isValidCredit(credit)) {
            throw new IllegalValueException(Credit.MESSAGE_CONSTRAINTS);
        }
        if (credit == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Credit.class.getSimpleName()));
        }
        if (!Credit.isValidCredit(credit)) {
            throw new IllegalValueException(Credit.MESSAGE_CONSTRAINTS);
        }

        if (grade == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName()));
        }
        if (!Grade.isValidGrade(grade)) {
            throw new IllegalValueException(Grade.MESSAGE_CONSTRAINTS);
        }

        final ModuleCode modelName = new ModuleCode(moduleCode);
        final Title modelTitle = new Title(title);
        final Semester modelSemester = ParserUtil.parseSemester(semester);
        final Credit modelCredit = new Credit(Integer.parseInt(credit));
        final Grade modelGrade = new Grade(grade);

        return new Module(modelName, modelTitle, modelSemester,
                modelCredit, modelGrade);
    }

}
