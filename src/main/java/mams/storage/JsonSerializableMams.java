package mams.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import mams.commons.exceptions.IllegalValueException;
import mams.model.Mams;
import mams.model.ReadOnlyMams;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Student;


/**
 * An Immutable MAMS that is serializable to JSON format.
 */
@JsonRootName(value = "mams")
class JsonSerializableMams {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";
    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";
    public static final String MESSAGE_DUPLICATE_APPEALS = "Appeals list contains duplicate appeal(s)";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    private final List<JsonAdaptedAppeal> appeals = new ArrayList<>();

    private final List<JsonAdaptedModule> modules = new ArrayList<>();


    /**
     * Constructs a {@code JsonSerializableMams} with the given students and modules.
    */
    @JsonCreator
    public JsonSerializableMams(@JsonProperty("students") List<JsonAdaptedStudent> students,
                                @JsonProperty("modules") List<JsonAdaptedModule> modules,
                                @JsonProperty("appeals") List<JsonAdaptedAppeal> appeals) {
        this.modules.addAll(modules);
        this.students.addAll(students);
        this.appeals.addAll(appeals);
    }

    /**
     * Converts a given {@code ReadOnlyMams} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMams}.
     */
    public JsonSerializableMams(ReadOnlyMams source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).collect(Collectors.toList()));
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Mams} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Mams toModelType() throws IllegalValueException {
        Mams mams = new Mams();

        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (mams.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            mams.addModule(module);
        }

        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (mams.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            mams.addStudent(student);
        }
        for (JsonAdaptedAppeal jsonAdaptedAppeal: appeals) {
            Appeal appeal = jsonAdaptedAppeal.toModelType();
            if (mams.hasAppeal((appeal))) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            mams.addAppeal(appeal);
        }
        return mams;
    }

}
