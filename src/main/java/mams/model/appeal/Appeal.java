package mams.model.appeal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import mams.commons.util.CollectionUtil;


public class Appeal {

    private final String appeal_ID;
    private final String appeal_type;
    private final String student_ID;
    private final String academic_year;
    private final int student_workload;
    private final String appeal_description;
    private final String previous_module;
    private final String new_module;
    private final String module_to_add;
    private final String module_to_drop;
    private final boolean resolved;
    private final String result;
    private final String remark;
    private boolean isModified;

    /**
     * Constructor for Appeal object when it is loaded from file
     * @param appeal_ID
     * @param appeal_type
     * @param student_ID
     * @param academic_year
     * @param student_workload
     * @param appeal_description
     * @param previous_module
     * @param new_module
     * @param module_to_add
     * @param module_to_drop
     * @param resolved
     * @param remark
     */
    public Appeal(String appeal_ID, String appeal_type, String student_ID, String academic_year,
                  int student_workload, String appeal_description, String previous_module,
                  String new_module, String module_to_add, String module_to_drop, boolean resolved, String remark) {

        CollectionUtil.requireAllNonNull(appeal_ID, appeal_type, student_ID, academic_year, student_workload, appeal_description, previous_module, new_module, module_to_add, module_to_drop, resolved, remark);
        this.appeal_ID = appeal_ID;
        this.appeal_type = appeal_type;
        this.student_ID = student_ID;
        this.academic_year = academic_year;
        this.student_workload = student_workload;
        this.appeal_description = appeal_description;
        this.previous_module = previous_module;
        this.new_module = new_module;
        this.module_to_add = module_to_add;
        this.module_to_drop = module_to_drop;
        this.resolved = resolved;
        this.result = "Pending";
        this.remark = remark;
        this.isModified = false; // to check if this is an already resolved appeal, since we are working with immutability.  Only appeals that are not modified will be shown/added to file

    }

    /**
     *  Constructor when an existing appeal is resolved
     * @param appeal_ID
     * @param appeal_type
     * @param student_ID
     * @param academic_year
     * @param student_workload
     * @param appeal_description
     * @param previous_module
     * @param new_module
     * @param module_to_add
     * @param module_to_drop
     * @param resolved
     * @param result
     * @param remark
     */
    public Appeal(String appeal_ID, String appeal_type, String student_ID, String academic_year,
                  int student_workload, String appeal_description, String previous_module,
                  String new_module, String module_to_add, String module_to_drop, boolean resolved, String result, String remark) {

        CollectionUtil.requireAllNonNull(appeal_ID, appeal_type, student_ID, academic_year, student_workload, appeal_description, previous_module, new_module, module_to_add, module_to_drop, resolved, result, remark);
        this.appeal_ID = appeal_ID;
        this.appeal_type = appeal_type;
        this.student_ID = student_ID;
        this.academic_year = academic_year;
        this.student_workload = student_workload;
        this.appeal_description = appeal_description;
        this.previous_module = previous_module;
        this.new_module = new_module;
        this.module_to_add = module_to_add;
        this.module_to_drop = module_to_drop;
        this.resolved = resolved;
        this.result = result;
        this.remark = remark;
        this.isModified = false; // to check if this is an already resolved appeal, since we are working with immutability.  Only appeals that are not modified will be shown/added to file

    }

    public String getAppealID() {
        return appeal_ID;
    }

    public String getAppealType() {
        return appeal_type;
    }

    public String getStudentID() {
        return student_ID;
    }

    public String getAcademicYear() {
        return academic_year;
    }

    public int getStudentWorkload() {
        return student_workload;
    }

    public String getAppealDescription() {
        return appeal_description;
    }

    public String getPreviousModule() {
        return previous_module;
    }

    public String getNewModule() {
        return new_module;
    }

    public String getModule_to_add() {
        return module_to_add;
    }

    public String getModule_to_drop() {
        return module_to_drop;
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

    public boolean isSameAppeal(Appeal otherAppeal){
        if(otherAppeal == this){
            return true;
        }

        return otherAppeal != null
                && otherAppeal.getAppealID().equals(getAppealID())
                && otherAppeal.getResult().equals(getResult())
                && (otherAppeal.getAppealType().equals(getAppealType()) || otherAppeal.getAppealDescription().equals(getAppealDescription()));
    }

    @Override
    public boolean equals(Object other) {
        if(other == this){
            return true;
        }

        if(!(other instanceof Appeal)){
            return false;
        }

        Appeal otherAppeal = (Appeal) other;
        return otherAppeal.getAppealID().equals(getAppealID())
                && otherAppeal.getAppealType().equals(getAppealType())
                && otherAppeal.getStudentID().equals(getStudentID())
                && otherAppeal.getAppealDescription().equals(getAppealDescription())
                && (otherAppeal.getStudentWorkload() == getStudentWorkload());
    }

    public int hashCode(){
        return Objects.hash(appeal_ID, appeal_type, student_ID, student_workload, appeal_description);
    }

    public String toString(){
        final StringBuilder builder = new StringBuilder();
        builder.append(getAppealID())
                .append(" Appeal type: ")
                .append(getAppealType())
                .append(" Student ID: ")
                .append(getStudentID())
                .append(" Academic year: ")
                .append(getAcademicYear())
                .append(" Student workload: ")
                .append(getStudentWorkload())
                .append(" Previous module: " )
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
                .append(" Remarks: " )
                .append(getRemark());
        return builder.toString();
    }

    public void setModified() {
        isModified = true;
    }

    public Appeal resolve(String action, String remark){
        Appeal appeal;
        if(action.equalsIgnoreCase("approve")){
            this.setModified();
            appeal = new Appeal(this.appeal_ID, this.appeal_type, this.student_ID, this.academic_year, this.student_workload, this.appeal_description, this.previous_module, this.new_module, this.module_to_add, this.module_to_drop, true, "approved", remark);
        } else {
            this.setModified();
            appeal = new Appeal(this.appeal_ID, this.appeal_type, this.student_ID, this.academic_year, this.student_workload, this.appeal_description, this.previous_module, this.new_module, this.module_to_add, this.module_to_drop, false, "rejected", remark);
        }
        return appeal;
    }
}
