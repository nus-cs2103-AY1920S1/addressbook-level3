package seedu.tarence.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.commons.util.JsonUtil;
import seedu.tarence.logic.parser.ParserUtil;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Event;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Jackson friendly version of a Module.
 */
@JsonRootName(value = "modules")
public class JsonAdaptedModule {

    // Identifiers to store the fields
    public static final String TUTORIAL_NAME = "tutorialName";
    public static final String TUTORIAL_DAY = "tutorialDayOfWeek";
    public static final String TUTORIAL_START_TIME = "tutorialStartTime";
    public static final String TUTORIAL_WEEKS = "tutorialWeeks";
    public static final String TUTORIAL_DURATION = "tutorialDuration";
    public static final String TUTORIAL_MODULE_CODE = "tutorialModuleCode";
    public static final String TUTORIAL_STUDENT_LIST = "tutorialStudentList";
    public static final String TUTORIAL_ATTENDANCE_LIST = "tutorialAttendanceList";
    public static final String TUTORIAL_ASSIGNMENT_LIST = "tutorialAssignmentList";
    public static final String TUTORIAL_EVENT_LIST = "tutorialEventList";

    public static final String STUDENT_NAME = "studentName";
    public static final String STUDENT_EMAIL = "studentEmail";
    public static final String STUDENT_MATRIC_NUMBER = "studentMatricNumber";
    public static final String STUDENT_NUSNET_ID = "studentNusnetId";
    public static final String STUDENT_MODULE_CODE = "studentModuleCode";
    public static final String STUDENT_TUTORIAL_NAME = "studentTutorialName";
    public static final String STUDENT_ATTENDANCE_STATUS = "studentAttendance";
    public static final String STUDENT_ASSIGNMENT_SCORE = "studentAssignmentScore";

    public static final String ASSIGNMENT_NAME = "assignmentName";
    public static final String ASSIGNMENT_MAX_SCORE = "assignmentMaxScore";
    public static final String ASSIGNMENT_START_DATE = "assignmentStartDate";
    public static final String ASSIGNMENT_END_DATE = "assignmentEndDate";
    public static final String ASSIGNMENT_STUDENT_LIST = "assignmentStudentList";
    public static final String ASSIGNMENT_NUMBER = "assignmentNumber-";

    // Error message Strings
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tutorial's %s field is missing!";
    public static final String INVALID_FIELD_MESSAGE_FORMAT = "Tutorial's %s field is invalid!";
    public static final String MISSING_GENERIC_FIELD = "Error in reading field! ";
    public static final String INVALID_FIELD = "Invalid field in %s";

    // Json fields
    // Implemented LinkedHashMap to preserve ordering.
    private LinkedHashMap<String, LinkedHashMap<String, String>> tutorialMap;
    private String moduleCode;
    private Date semesterStart;


    /**
     * Invoked during reading of the Json file.
     *
     * @param moduleName Json string representing the Module Name/Code.
     * @param tutorialMap Json string representing the Tutorial objects present in the Module.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleName,
                             @JsonProperty("tutorialMap") LinkedHashMap<String,
                                     LinkedHashMap<String, String>> tutorialMap) {
        this.moduleCode = moduleName;
        this.tutorialMap = tutorialMap;
    }

    /**
     * Invoked when saving the application.
     *
     * @param source Module object of the application.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModCode().toString();
        tutorialMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        semesterStart = source.getSemesterStart();

        //System.out.println("Semester start of module " + moduleCode + " is " + semesterStart);

        for (Tutorial t : source.getTutorials()) {
            LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<String, String>();

            // Obtain all the fields that defines a single Tutorial object.
            String tutorialName = t.getTutName().toString();
            String tutorialDayOfWeek = t.getTimeTable().getDay().toString();
            String tutorialStartTime = t.getTimeTable().getStartTime().toString();
            String tutorialWeeks = t.getTimeTable().getWeeks().toString();
            String tutorialDuration = t.getTimeTable().getDuration().toString();
            String studentListString = JsonUtil.studentListToString(t.getStudents());
            String tutorialModuleCode = t.getModCode().toString();
            String tutorialAttendanceString = JsonUtil.attendanceListToString(t.getAttendance());


            // Add into LinkedHashMap<String,String>, singleTutorialMap. Reading is order dependant
            singleTutorialMap.put(TUTORIAL_NAME, tutorialName);
            singleTutorialMap.put(TUTORIAL_DAY, tutorialDayOfWeek);
            singleTutorialMap.put(TUTORIAL_START_TIME, tutorialStartTime);
            singleTutorialMap.put(TUTORIAL_WEEKS, tutorialWeeks);
            singleTutorialMap.put(TUTORIAL_DURATION, tutorialDuration);
            singleTutorialMap.put(TUTORIAL_STUDENT_LIST, studentListString);
            singleTutorialMap.put(TUTORIAL_ATTENDANCE_LIST, tutorialAttendanceString);
            singleTutorialMap.put(TUTORIAL_MODULE_CODE, tutorialModuleCode);

            // Saving of assignment
            String tutorialAssignmentString = assignmentListToString(t.getAssignmentsForSaving());
            singleTutorialMap.put(TUTORIAL_ASSIGNMENT_LIST, tutorialAssignmentString);

            // Saving of event(s)
            String tutorialEventString = eventListToString(t.getEventLog());

            tutorialMap.put(tutorialName, singleTutorialMap);

        }
    }

    /**
     * Converts a tutorial's eventList ot a String
     *
     * @param eventLog
     * @return
     */
    public String eventListToString(List<Event> eventLog) {
        //System.out.println(eventLog.toString());
        return "";
    }

    /**
     * Returns the string representation of a Tutorial's Assignment(s).
     *
     * @param assignmentList Assignment List, obtained directly from Tutorial.
     * @return String.
     */
    public String assignmentListToString (Map<Assignment, Map<Student, Integer>> assignmentList) {

        LinkedHashMap<String, String> listOfAssignments = new LinkedHashMap<>();
        Integer assignmentCounter = 0;

        for (Assignment a : assignmentList.keySet()) {
            LinkedHashMap<String, String> assignmentMap = new LinkedHashMap<String, String>();
            String assignmentName = a.getAssignmentName();
            String assignmentMaxScore = Integer.toString(a.getMaxScore());
            String assignmentStartDate = a.getStartDate().toString();
            String assignmentEndDate = a.getEndDate().toString();

            // Serializes Students with their respective assignment scores
            Map<Student, Integer> studentScores = assignmentList.get(a);
            String assignmentStudentString = "[";
            for (Student s : studentScores.keySet()) {
                LinkedHashMap<String, String> studentMap = new LinkedHashMap<String, String>();
                studentMap.put(JsonAdaptedModule.STUDENT_NAME, s.getName().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_EMAIL, s.getEmail().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_MATRIC_NUMBER, s.getMatricNum().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_NUSNET_ID, s.getNusnetId().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_MODULE_CODE, s.getModCode().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_TUTORIAL_NAME, s.getTutName().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_ASSIGNMENT_SCORE, Integer.toString(studentScores.get(s)));
                assignmentStudentString = assignmentStudentString + studentMap.toString() + "],[";
            }
            // Case when there are no students.
            if (assignmentStudentString.equals("[")) {
                assignmentStudentString += "]";
            } else {
                // Remove the last square bracket
                assignmentStudentString = assignmentStudentString.substring(0, (assignmentStudentString.length() - 2));
            }

            assignmentMap.put(ASSIGNMENT_NAME, assignmentName);
            assignmentMap.put(ASSIGNMENT_MAX_SCORE, assignmentMaxScore);
            assignmentMap.put(ASSIGNMENT_START_DATE, assignmentStartDate);
            assignmentMap.put(ASSIGNMENT_END_DATE, assignmentEndDate);
            assignmentMap.put(ASSIGNMENT_STUDENT_LIST, assignmentStudentString);

            assignmentCounter++;
            listOfAssignments.put(ASSIGNMENT_NUMBER + assignmentCounter.toString(), assignmentMap.toString());
        }

        return listOfAssignments.toString();
    }

    /**
     * Invoked during reading of Json file.
     * Converts JsonAdaptedModule into a Module object.
     *
     * @return Module object.
     * @throws IllegalValueException when there is an error in reading one of the fields.
     */
    public Module toModelType() throws IllegalValueException {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        try {
            for (String tutorialName : tutorialMap.keySet()) {
                LinkedHashMap<String, String> singleTutorialMap = tutorialMap.get(tutorialName);
                // TODO: Check if singleTutorialMap is valid ie contains all the requires params
                Tutorial tutorialFromJson = JsonUtil.tutorialMapToTutorial(singleTutorialMap);
                tutorials.add(tutorialFromJson);
            }
        } catch (NullPointerException e) {
            String errorMessage = e.getClass().toString() + " " + e.getMessage();
            throw new IllegalValueException(errorMessage);
        }

        try {
            ModCode modCodeFromJson = ParserUtil.parseModCode(moduleCode);
            return new Module(modCodeFromJson, tutorials);
        } catch (ParseException e) {
            String errorMessage = String.format(INVALID_FIELD, Module.class.getSimpleName()) + " "
                    + moduleCode + " " + e.getMessage();
            throw new IllegalValueException(errorMessage);
        }

    }

    /**
     * Getter function for String moduleCode.
     *
     * @return String module code.
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Getter function for Linked Hashmap tutorialMap.
     *
     * @return Tutorial Linked HashMap.
     */
    public LinkedHashMap<String, LinkedHashMap<String, String>> getTutorialMap() {
        return tutorialMap;
    }
}

