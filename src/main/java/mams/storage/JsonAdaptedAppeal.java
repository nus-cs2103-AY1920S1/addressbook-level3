package mams.storage;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import mams.commons.exceptions.IllegalValueException;

import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.MatricId;


public class JsonAdaptedAppeal {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appeal's %s is missing!";

    private final String appealId;
    private final String appealType;
    private final String studentId;
    private final String academicYear;
    private final int studentWorkload;
    private final String appealDescription;
    private final String previousModule;
    private final String newModule;
    private final String moduleToAdd;
    private final String moduleToDrop;
    private final boolean resolved;
    private final String result;
    private final String remark;

    /**
     * Constructs a {@code JsonAdaptedAppeal} with the given student details.
     */
    @JsonCreator
    //todo
    public JsonAdaptedAppeal(@JsonProperty("appealId") String appealId, @JsonProperty("appealType") String appealType,
                             @JsonProperty("studentId") String studentId, @JsonProperty("academicYear") String academicYear,
                             @JsonProperty("studentWorkload") int studentWorkload, @JsonProperty("appealDescription") String appealDescription,
                             @JsonProperty("previousModule") String previousModule, @JsonProperty("newModule") String newModule,
                             @JsonProperty("moduleToAdd") String moduleToAdd, @JsonProperty("moduleToDrop") String moduleToDrop,
                             @JsonProperty("resolved") boolean resolved, @JsonProperty("result") String result,
                             @JsonProperty("remark") String remark) {
        this.appealId = appealId;
        this.appealType = appealType;
        this.studentId = studentId;
        this.academicYear = academicYear;
        this.studentWorkload = studentWorkload;
        this.appealDescription = appealDescription;
        this.previousModule = previousModule;
        this.newModule = newModule;
        this.moduleToAdd = moduleToAdd;
        this.moduleToDrop = moduleToDrop;
        this.resolved = resolved;
        this.result = result;
        this.remark = remark;
    }

    /**
     * Converts a given {@code Appeal} into this class for Jackson use.
     */
    public JsonAdaptedAppeal(Appeal source) {
        appealId = source.getAppealId();
        appealType = source.getAppealType();
        studentId = source.getStudentId();
        academicYear = source.getAcademicYear();
        studentWorkload = source.getStudentWorkload();
        appealDescription = source.getAppealDescription();
        previousModule = source.getPreviousModule();
        newModule = source.getNewModule();
        moduleToAdd = source.getModule_to_add();
        moduleToDrop = source.getModule_to_drop();
        resolved = source.isResolved();
        result = source.getResult();
        remark = source.getRemark();
    }

    /**
     * Converts this Jackson-friendly adapted appeal object into the model's {@code Appeal} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Appeal toModelType() throws IllegalValueException {

        //Appeal Id
        if (appealId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Appeal_Id"));
        }
        if (!Appeal.isValidAppealId(appealId)) {
            throw new IllegalValueException(Appeal.MESSAGE_CONSTRAINTS_APPEAL_ID);
        }
        final String modelAppealId = appealId;

        //Appeal Type
        if (appealType == null) { //sessionId expected for Json data
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Appeal Type"));
        }
        if (!Appeal.isValidAppealType(appealType)) {
            throw new IllegalValueException(Appeal.MESSAGE_CONSTRAINTS_APPEAL_TYPE);
        }
        final String modelAppealType = appealType;

        //Student Id
        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Student ID"));
        }
        if (!MatricId.isValidMatricId(studentId)) {
            throw new IllegalValueException(MatricId.MESSAGE_CONSTRAINTS);
        }
        final String modelStudentId = studentId;

        //Academic Year
        if (academicYear == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Academic year"));
        }
        if (!Appeal.isValidAppealId(academicYear)) {
            throw new IllegalValueException(Appeal.MESSAGE_CONSTRAINTS_ACADEMIC_YEAR);
        }
        final String modelAcademicYear = academicYear;

        //Student workload
        final int modelStudentWorkload = studentWorkload;

        //Appeal description
        final String modelAppealDescription = appealDescription;

        //Previous module for swapping
        if (!Module.isValidModuleCode(previousModule)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_MODULE_CODE);
        }
        final String modelPreviousModule = previousModule;

        //New Module for swapping
        if (!Module.isValidModuleCode(newModule)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_MODULE_CODE);
        }
        final String modelNewModule = newModule;

        //Module requested to be added
        if (!Module.isValidModuleCode(moduleToAdd)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_MODULE_CODE);
        }
        final String modelModuleToAdd = moduleToAdd;

        //Module requested to be dropped
        if (!Module.isValidModuleCode(moduleToDrop)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS_MODULE_CODE);
        }
        final String modelModuleToDrop = moduleToDrop;

        //Is this appeal resolved
        final boolean modelIsResolved = resolved;

        //The outcome of the appeal
        final String modelResult = result;

        //Remark left by admin
        final String modelRemark = remark;

        return new Appeal(modelAppealId, modelAppealType, modelStudentId,
         modelAcademicYear, modelStudentWorkload, modelAppealDescription,
         modelPreviousModule, modelNewModule, modelModuleToAdd, modelModuleToDrop,
         modelIsResolved, modelResult, modelRemark);
    }


}
