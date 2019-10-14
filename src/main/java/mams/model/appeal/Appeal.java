package mams.model.appeal;

import java.util.Objects;

import mams.commons.util.CollectionUtil;

/**
 * Represents an appeal in appeal list
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appeal {

    public static final String MESSAGE_CONSTRAINTS_APPEAL_ID =
            "Appeals should start with 'C' followed by a 6 digit number";

    public static final String MESSAGE_CONSTRAINTS_APPEAL_TYPE =
            "Appeals should be either add module, remove module or increase workload";

    /**
     * "C" followed by strictly 6 digits.
     */
    public static final String VALIDATION_REGEX_APPEAL_CODE = "C\\d{6}$";

    /**
     * "AY" followed by 4 digit year
     */
    public static final String MESSAGE_CONSTRAINTS_ACADEMIC_YEAR =
            "Academic year should start with AY";
    //Identity fields
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
    private boolean isModified;


    /**
     * Returns true if a given string is a valid appeal Id.
     */
    public static boolean isValidAppealId(String test) {
        return test.matches(VALIDATION_REGEX_APPEAL_CODE);
    }

    /**
     * Returns true if given string is of correct type
     */
    public static boolean isValidAppealType(String test) {
        return test.equalsIgnoreCase("increase workload")
                || test.equalsIgnoreCase("add module")
                || test.equalsIgnoreCase("remove module");
    }

    /**
     * Constructor for Appeal object when it is loaded from file
     * @param appealId
     * @param appealType
     * @param studentId
     * @param academicYear
     * @param studentWorkload
     * @param appealDescription
     * @param previousModule
     * @param newModule
     * @param moduleToAdd
     * @param moduleToDrop
     * @param resolved
     * @param remark
     */
    public Appeal(String appealId,
                  String appealType,
                  String studentId,
                  String academicYear,
                  int studentWorkload,
                  String appealDescription,
                  String previousModule,
                  String newModule,
                  String moduleToAdd,
                  String moduleToDrop,
                  boolean resolved,
                  String remark) {

        CollectionUtil.requireAllNonNull(appealId, appealType, studentId, academicYear, appealDescription);
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
        this.result = "Pending";
        this.remark = remark;
        this.isModified = false; // to check if this is an already resolved appeal,
        // since we are working with immutability.
        //  Only appeals that are not modified will be shown/added to file

    }

    /**
     *  Constructor when an existing appeal is resolved
     * @param appealId
     * @param appealType
     * @param studentId
     * @param academicYear
     * @param studentWorkload
     * @param appealDescription
     * @param previousModule
     * @param newModule
     * @param moduleToAdd
     * @param moduleToDrop
     * @param resolved
     * @param result
     * @param remark
     */
    public Appeal(String appealId,
                  String appealType,
                  String studentId,
                  String academicYear,
                  int studentWorkload,
                  String appealDescription,
                  String previousModule,
                  String newModule, String moduleToAdd,
                  String moduleToDrop,
                  boolean resolved,
                  String result,
                  String remark) {

        CollectionUtil.requireAllNonNull(appealId, appealType, studentId, academicYear,
                studentWorkload, appealDescription);
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
        this.isModified = false; // to check if this is an already resolved appeal,
        // since we are working with immutability.
        //  Only appeals that are not modified will be shown/added to file

    }

    public String getAppealId() {
        return appealId;
    }

    public String getAppealType() {
        return appealType;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public int getStudentWorkload() {
        return studentWorkload;
    }

    public String getAppealDescription() {
        return appealDescription;
    }

    public String getPreviousModule() {
        return previousModule;
    }

    public String getNewModule() {
        return newModule;
    }

    public String getModule_to_add() {
        return moduleToAdd;
    }

    public String getModule_to_drop() {
        return moduleToDrop;
    }

    public boolean isResolved() {
        return resolved;
    }

    public String getResult() {
        return result;
    }

    public String getRemark() {
        return remark;
    }

    /**
     * Returns true if both appeals of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two appeals.
     */
    public boolean isSameAppeal(Appeal otherAppeal) {
        if (otherAppeal == this) {
            return true;
        }

        return otherAppeal != null
                && otherAppeal.getAppealId().equals(getAppealId())
                && otherAppeal.getResult().equals(getResult())
                && (otherAppeal.getAppealType().equals(getAppealType())
                || otherAppeal.getAppealDescription().equals(getAppealDescription()));
    }

    /**
     * Returns true if both appeals of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two appeals.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appeal)) {
            return false;
        }

        Appeal otherAppeal = (Appeal) other;
        return otherAppeal.getAppealId().equals(getAppealId())
                && otherAppeal.getAppealType().equals(getAppealType())
                && otherAppeal.getStudentId().equals(getStudentId())
                && otherAppeal.getAppealDescription().equals(getAppealDescription())
                && (otherAppeal.getStudentWorkload() == getStudentWorkload());
    }

    public int hashCode() {
        return Objects.hash(appealId, appealType, studentId, studentWorkload, appealDescription);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getAppealId())
                .append(" Appeal type: ")
                .append(getAppealType())
                .append(" Student ID: ")
                .append(getStudentId())
                .append(" Academic year: ")
                .append(getAcademicYear())
                .append(" Student workload: ")
                .append(getStudentWorkload())
                .append(" Previous module: ")
                .append(getPreviousModule())
                .append(" New module: ")
                .append(getNewModule())
                .append(" Module to add: ")
                .append(getModule_to_add())
                .append(" Module to drop ")
                .append(getModule_to_drop())
                .append(" Resolved?:")
                .append(isResolved())
                .append(" Result: ")
                .append(getResult())
                .append(" Remarks: ")
                .append(getRemark());
        return builder.toString();
    }

    public void setModified() {
        isModified = true;
    }

    /**
     * Resolves an appeal
     * @param action
     * @param remark
     * @returns a new appeal with same identities
     */
    public Appeal resolve(String action, String remark) {
        Appeal appeal;
        if (action.equalsIgnoreCase("approve")) {
            this.setModified();
            appeal = new Appeal(this.appealId, this.appealType, this.studentId, this.academicYear,
                    this.studentWorkload, this.appealDescription, this.previousModule, this.newModule,
                    this.moduleToAdd, this.moduleToDrop, true, "approved", remark);
        } else {
            this.setModified();
            appeal = new Appeal(this.appealId, this.appealType, this.studentId, this.academicYear,
                    this.studentWorkload, this.appealDescription, this.previousModule, this.newModule,
                    this.moduleToAdd, this.moduleToDrop, false, "rejected", remark);
        }
        return appeal;
    }


}
