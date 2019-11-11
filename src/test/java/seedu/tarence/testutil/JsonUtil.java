package seedu.tarence.testutil;

import static seedu.tarence.storage.JsonAdaptedModule.TUTORIAL_ASSIGNMENT_LIST;
import static seedu.tarence.storage.JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST;
import static seedu.tarence.storage.JsonAdaptedModule.TUTORIAL_DAY;
import static seedu.tarence.storage.JsonAdaptedModule.TUTORIAL_DURATION;
import static seedu.tarence.storage.JsonAdaptedModule.TUTORIAL_MODULE_CODE;
import static seedu.tarence.storage.JsonAdaptedModule.TUTORIAL_NAME;
import static seedu.tarence.storage.JsonAdaptedModule.TUTORIAL_START_TIME;
import static seedu.tarence.storage.JsonAdaptedModule.TUTORIAL_STUDENT_LIST;
import static seedu.tarence.storage.JsonAdaptedModule.TUTORIAL_WEEKS;

import java.util.LinkedHashMap;

/**
 * A set of methods to assist in testing Json methods.
 */
public class JsonUtil {
    public static final String VALID_TUTORIAL_DAY = "MONDAY";
    public static final String VALID_TUTORIAL_DURATION = "PT2H";
    public static final String VALID_TUTORIAL_MODULE_CODE = "CS1010";
    public static final String VALID_TUTORIAL_NAME = "Sectional";
    public static final String VALID_TUTORIAL_START_TIME = "12:00";
    public static final String VALID_STUDENT_LIST = "[{studentName=Ellie Yee, studentEmail=e0035152@u.nus.edu.sg, "
            + "studentMatricNumber=Optional[A0155413M], studentNusnetId=Optional[E0031550], "
            + "studentModuleCode=CS1010S, studentTutorialName=Lab Session}],[{studentName=Prof Damith, "
            + "studentEmail=e0012352@u.nus.edu.sg, studentMatricNumber=Optional.empty, "
            + "studentNusnetId=Optional.empty, studentModuleCode=CS1010S, "
            + "studentTutorialName=Lab Session}],[{studentName=Hatziq, studentEmail=e0012352@u.nus.edu.sg, "
            + "studentMatricNumber=Optional.empty, studentNusnetId=Optional.empty, studentModuleCode=CS1010S, "
            + "studentTutorialName=Lab Session}]";
    public static final String VALID_TUTORIAL_WEEKS = "[1, 4, 7]";
    public static final String VALID_TUTORIAL_ATTENDANCE_LIST = "{1=[{studentName=Ellie Yee, "
            + "studentEmail=e0035152@u.nus.edu.sg, studentMatricNumber=Optional[A0155413M], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010S, studentTutorialName=Lab Session, "
            + "studentAttendance=true}],[{studentName=Prof Damith, studentEmail=e0012352@u.nus.edu.sg, "
            + "studentMatricNumber=Optional.empty, studentNusnetId=Optional.empty, studentModuleCode=CS1010S, "
            + "studentTutorialName=Lab Session, studentAttendance=false}], 4=[{studentName=Ellie Yee, "
            + "studentEmail=e0035152@u.nus.edu.sg, studentMatricNumber=Optional[A0155413M], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010S, studentTutorialName=Lab Session, "
            + "studentAttendance=false}],[{studentName=Prof Damith, studentEmail=e0012352@u.nus.edu.sg, "
            + "studentMatricNumber=Optional.empty, studentNusnetId=Optional.empty, studentModuleCode=CS1010S, "
            + "studentTutorialName=Lab Session, studentAttendance=false}], 7=[{studentName=Ellie Yee, "
            + "studentEmail=e0035152@u.nus.edu.sg, studentMatricNumber=Optional[A0155413M], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010S, studentTutorialName=Lab Session, "
            + "studentAttendance=true}],[{studentName=Prof Damith, studentEmail=e0012352@u.nus.edu.sg, "
            + "studentMatricNumber=Optional.empty, studentNusnetId=Optional.empty, studentModuleCode=CS1010S, "
            + "studentTutorialName=Lab Session, studentAttendance=true}]}";
    public static final String VALID_TUTORIAL_ASSIGNMENT_LIST = "{assignmentNumber-1={assignmentName=Assignment A, "
            + "assignmentMaxScore=100, assignmentStartDate=Wed Oct 10 00:00:00 SGT 2012, "
            + "assignmentEndDate=Thu Oct 10 23:59:00 SGT 2019, assignmentStudentList=[{studentName=Bob, "
            + "studentEmail=e0012952@gmail.com, studentMatricNumber=Optional[A0155413X], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, studentTutorialName=Lab Session, "
            + "studentAssignmentScore=-1}]}}";

    public static final String INVALID_TUTORIAL_DAY = "International Slippers Day";
    public static final String INVALID_TUTORIAL_DURATION = "120";
    public static final String INVALID_TUTORIAL_START_TIME = "25:00";
    public static final String INVALID_TUTORIAL_MODULE_CODE = "CS10101010EEE";
    public static final String INVALID_TUTORIAL_WEEKS = "[3O, 5O]";

    public static LinkedHashMap<String, String> getValidMapOfSingleTutorial() {
        LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<String, String>();
        singleTutorialMap.put(TUTORIAL_NAME, VALID_TUTORIAL_NAME);
        singleTutorialMap.put(TUTORIAL_DAY, VALID_TUTORIAL_DAY);
        singleTutorialMap.put(TUTORIAL_START_TIME, VALID_TUTORIAL_START_TIME);
        singleTutorialMap.put(TUTORIAL_WEEKS, VALID_TUTORIAL_WEEKS);
        singleTutorialMap.put(TUTORIAL_DURATION, VALID_TUTORIAL_DURATION);
        singleTutorialMap.put(TUTORIAL_STUDENT_LIST, VALID_STUDENT_LIST);
        singleTutorialMap.put(TUTORIAL_ATTENDANCE_LIST, VALID_TUTORIAL_ATTENDANCE_LIST);
        singleTutorialMap.put(TUTORIAL_MODULE_CODE, VALID_TUTORIAL_MODULE_CODE);
        return singleTutorialMap;
    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>> getMapOfSingleTutorialWithInvalidTutorialDay() {
        LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<String, String>();
        singleTutorialMap.put(TUTORIAL_NAME, VALID_TUTORIAL_NAME);
        singleTutorialMap.put(TUTORIAL_DAY, INVALID_TUTORIAL_DAY);
        singleTutorialMap.put(TUTORIAL_START_TIME, VALID_TUTORIAL_START_TIME);
        singleTutorialMap.put(TUTORIAL_WEEKS, VALID_TUTORIAL_WEEKS);
        singleTutorialMap.put(TUTORIAL_DURATION, VALID_TUTORIAL_DURATION);
        singleTutorialMap.put(TUTORIAL_STUDENT_LIST, VALID_STUDENT_LIST);
        singleTutorialMap.put(TUTORIAL_ATTENDANCE_LIST, VALID_TUTORIAL_ATTENDANCE_LIST);
        singleTutorialMap.put(TUTORIAL_MODULE_CODE, VALID_TUTORIAL_MODULE_CODE);


        LinkedHashMap<String, LinkedHashMap<String, String>> differentTutorialsMap;
        differentTutorialsMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        differentTutorialsMap.put(VALID_TUTORIAL_NAME, singleTutorialMap);
        return differentTutorialsMap;
    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>>
                    getMapOfSingleTutorialWithInvalidTutorialDuration() {

        LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<String, String>();
        singleTutorialMap.put(TUTORIAL_NAME, VALID_TUTORIAL_NAME);
        singleTutorialMap.put(TUTORIAL_DAY, VALID_TUTORIAL_DAY);
        singleTutorialMap.put(TUTORIAL_START_TIME, VALID_TUTORIAL_START_TIME);
        singleTutorialMap.put(TUTORIAL_WEEKS, VALID_TUTORIAL_WEEKS);
        singleTutorialMap.put(TUTORIAL_DURATION, INVALID_TUTORIAL_DURATION);
        singleTutorialMap.put(TUTORIAL_STUDENT_LIST, VALID_STUDENT_LIST);
        singleTutorialMap.put(TUTORIAL_ATTENDANCE_LIST, VALID_TUTORIAL_ATTENDANCE_LIST);
        singleTutorialMap.put(TUTORIAL_MODULE_CODE, VALID_TUTORIAL_MODULE_CODE);

        LinkedHashMap<String, LinkedHashMap<String, String>> differentTutorialsMap;
        differentTutorialsMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        differentTutorialsMap.put(VALID_TUTORIAL_NAME, singleTutorialMap);
        return differentTutorialsMap;
    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>>
                    getMapOfSingleTutorialWithInvalidTutorialStartTime() {

        LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<String, String>();
        singleTutorialMap.put(TUTORIAL_NAME, VALID_TUTORIAL_NAME);
        singleTutorialMap.put(TUTORIAL_DAY, VALID_TUTORIAL_DAY);
        singleTutorialMap.put(TUTORIAL_START_TIME, INVALID_TUTORIAL_START_TIME);
        singleTutorialMap.put(TUTORIAL_WEEKS, VALID_TUTORIAL_WEEKS);
        singleTutorialMap.put(TUTORIAL_DURATION, VALID_TUTORIAL_DURATION);
        singleTutorialMap.put(TUTORIAL_STUDENT_LIST, VALID_STUDENT_LIST);
        singleTutorialMap.put(TUTORIAL_ATTENDANCE_LIST, VALID_TUTORIAL_ATTENDANCE_LIST);
        singleTutorialMap.put(TUTORIAL_MODULE_CODE, VALID_TUTORIAL_MODULE_CODE);

        LinkedHashMap<String, LinkedHashMap<String, String>> differentTutorialsMap;
        differentTutorialsMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        differentTutorialsMap.put(VALID_TUTORIAL_NAME, singleTutorialMap);
        return differentTutorialsMap;
    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>>
                    getMapOfSingleTutorialWithInvalidTutorialWeeks() {

        LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<String, String>();
        singleTutorialMap.put(TUTORIAL_NAME, VALID_TUTORIAL_NAME);
        singleTutorialMap.put(TUTORIAL_DAY, VALID_TUTORIAL_DAY);
        singleTutorialMap.put(TUTORIAL_START_TIME, VALID_TUTORIAL_START_TIME);
        singleTutorialMap.put(TUTORIAL_WEEKS, INVALID_TUTORIAL_WEEKS);
        singleTutorialMap.put(TUTORIAL_DURATION, VALID_TUTORIAL_DURATION);
        singleTutorialMap.put(TUTORIAL_STUDENT_LIST, VALID_STUDENT_LIST);
        singleTutorialMap.put(TUTORIAL_ATTENDANCE_LIST, VALID_TUTORIAL_ATTENDANCE_LIST);
        singleTutorialMap.put(TUTORIAL_MODULE_CODE, VALID_TUTORIAL_MODULE_CODE);

        LinkedHashMap<String, LinkedHashMap<String, String>> differentTutorialsMap;
        differentTutorialsMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        differentTutorialsMap.put(VALID_TUTORIAL_NAME, singleTutorialMap);
        return differentTutorialsMap;
    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>> getMapOfSingleTutorialWithInvalidModuleCode() {
        LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<String, String>();
        singleTutorialMap.put(TUTORIAL_NAME, VALID_TUTORIAL_NAME);
        singleTutorialMap.put(TUTORIAL_DAY, VALID_TUTORIAL_DAY);
        singleTutorialMap.put(TUTORIAL_START_TIME, VALID_TUTORIAL_START_TIME);
        singleTutorialMap.put(TUTORIAL_WEEKS, VALID_TUTORIAL_WEEKS);
        singleTutorialMap.put(TUTORIAL_DURATION, VALID_TUTORIAL_DURATION);
        singleTutorialMap.put(TUTORIAL_STUDENT_LIST, VALID_STUDENT_LIST);
        singleTutorialMap.put(TUTORIAL_ATTENDANCE_LIST, VALID_TUTORIAL_ATTENDANCE_LIST);
        singleTutorialMap.put(TUTORIAL_MODULE_CODE, INVALID_TUTORIAL_MODULE_CODE);

        LinkedHashMap<String, LinkedHashMap<String, String>> differentTutorialsMap;
        differentTutorialsMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        differentTutorialsMap.put(VALID_TUTORIAL_NAME, singleTutorialMap);
        return differentTutorialsMap;
    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>> getValidMapOfDifferentTutorials() {
        LinkedHashMap<String, LinkedHashMap<String, String>> differentTutorialsMap;
        differentTutorialsMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        differentTutorialsMap.put(VALID_TUTORIAL_NAME, getValidMapOfSingleTutorial());
        return differentTutorialsMap;
    }

    public static LinkedHashMap<String, String> getValidTutorialMap() {
        LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<>();

        singleTutorialMap.put(TUTORIAL_NAME, VALID_TUTORIAL_NAME);
        singleTutorialMap.put(TUTORIAL_DAY, VALID_TUTORIAL_DAY);
        singleTutorialMap.put(TUTORIAL_START_TIME, VALID_TUTORIAL_START_TIME);
        singleTutorialMap.put(TUTORIAL_WEEKS, VALID_TUTORIAL_WEEKS);
        singleTutorialMap.put(TUTORIAL_DURATION, VALID_TUTORIAL_DURATION);
        singleTutorialMap.put(TUTORIAL_STUDENT_LIST, VALID_STUDENT_LIST);
        singleTutorialMap.put(TUTORIAL_ATTENDANCE_LIST, VALID_TUTORIAL_ATTENDANCE_LIST);
        singleTutorialMap.put(TUTORIAL_MODULE_CODE, VALID_TUTORIAL_MODULE_CODE);
        singleTutorialMap.put(TUTORIAL_ASSIGNMENT_LIST, VALID_TUTORIAL_ASSIGNMENT_LIST);

        return singleTutorialMap;
    }

    public static LinkedHashMap<String, String> getInvalidTutorialMapWithoutWeeks() {
        LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<>();

        singleTutorialMap.put(TUTORIAL_NAME, VALID_TUTORIAL_NAME);
        singleTutorialMap.put(TUTORIAL_DAY, VALID_TUTORIAL_DAY);
        singleTutorialMap.put(TUTORIAL_START_TIME, VALID_TUTORIAL_START_TIME);
        singleTutorialMap.put(TUTORIAL_DURATION, VALID_TUTORIAL_DURATION);
        singleTutorialMap.put(TUTORIAL_STUDENT_LIST, VALID_STUDENT_LIST);
        singleTutorialMap.put(TUTORIAL_ATTENDANCE_LIST, VALID_TUTORIAL_ATTENDANCE_LIST);
        singleTutorialMap.put(TUTORIAL_MODULE_CODE, VALID_TUTORIAL_MODULE_CODE);
        singleTutorialMap.put(TUTORIAL_ASSIGNMENT_LIST, VALID_TUTORIAL_ASSIGNMENT_LIST);

        return singleTutorialMap;
    }

}
