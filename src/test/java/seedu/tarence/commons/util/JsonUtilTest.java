package seedu.tarence.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Attendance;
import seedu.tarence.model.tutorial.Event;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Week;
import seedu.tarence.model.util.SampleDataUtil;
import seedu.tarence.storage.JsonAdaptedModule;
import seedu.tarence.testutil.SerializableTestClass;
import seedu.tarence.testutil.TestUtil;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    public static final String UNHANDLED_EXCEPTION_MESSAGE = "Testing results in unhandled EXCEPTION";
    public static final String INVALID_STUDENT_STRING_MISSING_STUDENT_NAME = "[studentEmail=e0035152@u.nus.edu.sg, "
            + "studentMatricNumber=OptionalA0155413M, studentNusnetId=OptionalE0031550, "
            + "studentModuleCode=CS1010S, studentTutorialName=Lab Session}]";
    public static final String INVALID_STUDENT_STRING_WRONG_ORDER_OF_FIELDS = "[studentName=Alice, "
            + "studentEmail=e0035152@u.nus.edu.sg, "
            + "studentMatricNumber=OptionalA0155413M, studentNusnetId=OptionalE0031550, "
            + "studentTutorialName=Lab Session, studentModuleCode=CS1010S}]";
    public static final String INVALID_TUTORIAL_STRING_MISSING_TUTORIAL_NAME = "{tutorialDayOfWeek=MONDAY, "
            + "studentListString=[], tutorialModuleCode=CS1010S, tutorialStartTime=12:00, tutorialDuration=PT2H, "
            + "tutorialWeeks=[1, 4, 7]}";
    public static final String INVALID_TUTORIAL_STRING_WRONG_ORDER_OF_FIELDS = "{tutorialName=Sectional, "
            + "tutorialDayOfWeek=MONDAY, "
            + "studentListString=[], tutorialModuleCode=CS1010S, tutorialStartTime=12:00, tutorialDuration=PT2H, "
            + "tutorialWeeks=[1, 4, 7]}";
    public static final String INVALID_STUDENT_ATTENDANCE_STRING_MISSING_STUDENT_ATTENDANCE = "studentName=Alice, "
            + "studentEmail=e0012952@gmail.com, studentMatricNumber=Optional[A0155413X], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, "
            + "studentTutorialName=Lab Session}";
    public static final String INVALID_STUDENT_ATTENDANCE_STRING_INCORRECT_ORDER_OF_FIELD = "studentName=Alice, "
            + "studentEmail=e0012952@gmail.com, studentMatricNumber=Optional[A0155413X], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, "
            + "studentAttendance=true, studentTutorialName=Lab Session }";
    public static final String INVALID_SINGLE_EVENT_STRING_WITH_MISSING_FIELD = "{eventNotName=Assignment A, "
            + "eventStartDate=Wed Oct 10 00:00:00 SGT 2012, eventEndDate=Thu Oct 10 23:59:00 SGT 2019}";
    public static final String INVALID_SINGLE_EVENT_STRING_WITH_INCORRECT_ORDER_OF_FIELD = "{eventName=Assignment A, "
            + " eventEndDate=Thu Oct 10 23:59:00 SGT 2019, eventStartDate=Wed Oct 10 00:00:00 SGT 2012,}";
    public static final String VALID_EVENT_STRING = "{eventNumber-1={eventName=Assignment A, "
            + "eventStartDate=Wed Oct 10 00:00:00 SGT 2012, eventEndDate=Thu Oct 10 23:59:00 SGT 2019}, "
            + "eventNumber-2={eventName=Assignment B, eventStartDate=Wed Oct 10 00:00:00 SGT 2012, "
            + "eventEndDate=Thu Oct 10 23:59:00 SGT 2019}}";
    public static final String INVALID_EVENT_STRING_WITH_INCORRECT_ORDERING = "{eventNumber-2={eventName=Assignment A, "
            + "eventStartDate=Wed Oct 10 00:00:00 SGT 2012, eventEndDate=Thu Oct 10 23:59:00 SGT 2019}, "
            + "eventNumber-1={eventName=Assignment B, eventStartDate=Wed Oct 10 00:00:00 SGT 2012, "
            + "eventEndDate=Thu Oct 10 23:59:00 SGT 2019}}";
    public static final String VALID_TUTORIAL_ASSIGNMENT_STRING = "{assignmentNumber-1={assignmentName=Assignment A, "
            + "assignmentMaxScore=100, assignmentStartDate=Wed Oct 10 00:00:00 SGT 2012, "
            + "assignmentEndDate=Thu Oct 10 23:59:00 SGT 2019, assignmentStudentList=[{studentName=Bob, "
            + "studentEmail=e0012952@gmail.com, studentMatricNumber=Optional[A0155413X], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, "
            + "studentTutorialName=Lab Session, studentAssignmentScore=-1}]}}";
    public static final String VALID_STUDENT_ASSIGNMENT_STRING = "studentName=Bob, studentEmail=e0012952@gmail.com, "
            + "studentMatricNumber=Optional[A0155413X], studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, "
            + "studentTutorialName=Lab Session, studentAssignmentScore=-1}";
    public static final String VALID_SINGLE_STUDENT_STRING = "[{studentName=Bob, studentEmail=e0012952@gmail.com, "
            + "studentMatricNumber=Optional[A0155413X], studentNusnetId=Optional[E0031550], "
            + "studentModuleCode=CS1010E, studentTutorialName=Lab Session}]";
    public static final String INVALID_SINGLE_STUDENT_STRING = "[{studentName=Bob, studentEmail=e0012952@gmail.com, "
            + "studentMatricNumber=Optional[A0155413X], studentNusnetId=Optional[E0031550], "
            + "studentModuleCode=CS1010E, @@@=Lab Session}]";
    public static final String VALID_STUDENT_LIST_STRING_WITH_TWO_STUDENTS = "\"[{studentName=Bob, "
            + "studentEmail=e0012952@gmail.com, studentMatricNumber=Optional[A0155413X], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, "
            + "studentTutorialName=Lab Session}],[{studentName=Prof Damith, "
            + "studentEmail=e7112352@u.nus.edu.sg, studentMatricNumber=Optional.empty, "
            + "studentNusnetId=Optional.empty, studentModuleCode=CS1010E, studentTutorialName=Lab Session}]\"";
    public static final String VALID_SINGLE_WEEK_TUTORIAL_ATTENDANCE_STRING = "[{studentName=Bob, "
            + "studentEmail=e0012952@gmail.com, "
            + "studentMatricNumber=Optional[A0155413X], studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, "
            + "studentTutorialName=Lab Session, studentAttendance=false}],[{studentName=Prof Damith, "
            + "studentEmail=e7112352@u.nus.edu.sg, studentMatricNumber=Optional.empty, studentNusnetId=Optional.empty, "
            + "studentModuleCode=CS1010E, studentTutorialName=Lab Session, studentAttendance=false}]";
    public static final String VALID_TUTORIAL_ATTENDANCE_STRING = "{1=[{studentName=Bob, "
            + "studentEmail=e0012952@gmail.com, studentMatricNumber=Optional[A0155413X], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, studentTutorialName=Lab Session, "
            + "studentAttendance=false}], 4=[{studentName=Bob, studentEmail=e0012952@gmail.com, "
            + "studentMatricNumber=Optional[A0155413X], studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, "
            + "studentTutorialName=Lab Session, studentAttendance=false}], 7=[{studentName=Bob, "
            + "studentEmail=e0012952@gmail.com, studentMatricNumber=Optional[A0155413X], "
            + "studentNusnetId=Optional[E0031550], studentModuleCode=CS1010E, studentTutorialName=Lab Session, "
            + "studentAttendance=false}]}";
    public static final String VALID_TUTORIAL_EVENT_STRING = "{eventNumber-1={eventName=Assignment A, "
            + "eventStartDate=Wed Oct 10 00:00:00 SGT 2012, eventEndDate=Thu Oct 10 23:59:00 SGT 2019}, "
            + "eventNumber-2={eventName=Assignment B, eventStartDate=Wed Oct 10 00:00:00 SGT 2012, "
            + "eventEndDate=Thu Oct 10 23:59:00 SGT 2019}}";
    public static final String INVALID_STUDENT_ASSIGNMENT_STRING_WITHOUT_ASSIGNMENT_SCORE =
            VALID_STUDENT_ASSIGNMENT_STRING.replace("studentAssignmentScore", "");


    private static final Path SERIALIZATION_FILE = TestUtil.getFilePathInSandboxFolder("serialize.json");

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        JsonUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = JsonUtil
                .deserializeObjectFromJsonFile(SERIALIZATION_FILE, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }

    @Test
    public void isValidStudentString_studentStringWithoutNameField_returnsFalse() {
        JsonAdaptedModule module = new JsonAdaptedModule(SampleDataUtil.getSampleModule());
        assertEquals(false, JsonUtil.isValidStudentString(INVALID_STUDENT_STRING_MISSING_STUDENT_NAME));
    }

    @Test
    public void isValidStudentString_studentStringInWrongOrder_returnsFalse() {
        JsonAdaptedModule module = new JsonAdaptedModule(SampleDataUtil.getSampleModule());
        assertEquals(false, seedu.tarence.commons.util.JsonUtil
                .isValidStudentString(INVALID_STUDENT_STRING_WRONG_ORDER_OF_FIELDS));
    }

    @Test
    public void isValidTutorialString_tutorialStringInWrongOrder_returnsFalse() {
        JsonAdaptedModule module = new JsonAdaptedModule(SampleDataUtil.getSampleModule());
        assertEquals(false, seedu.tarence.commons.util.JsonUtil
                .isValidTutorialString(INVALID_TUTORIAL_STRING_WRONG_ORDER_OF_FIELDS));
    }

    @Test
    public void isValidTutorialString_tutorialStringWithoutNameField_returnsFalse() {
        JsonAdaptedModule module = new JsonAdaptedModule(SampleDataUtil.getSampleModule());
        assertEquals(false, seedu.tarence.commons.util.JsonUtil
                .isValidTutorialString(INVALID_TUTORIAL_STRING_MISSING_TUTORIAL_NAME));
    }

    @Test
    public void isValidStudentWithAttendanceString_studentStringWithoutAttendanceField_returnsFalse() {
        assertEquals(false, seedu.tarence.commons.util.JsonUtil.isValidStudentWithAttendanceString(
                INVALID_STUDENT_ATTENDANCE_STRING_MISSING_STUDENT_ATTENDANCE));
    }

    @Test
    public void isValidStudentWithAttendanceString_studentStringWithWrongFieldOrder_returnsFalse() {
        assertEquals(false, seedu.tarence.commons.util.JsonUtil.isValidStudentWithAttendanceString(
                INVALID_STUDENT_ATTENDANCE_STRING_INCORRECT_ORDER_OF_FIELD));
    }

    @Test
    public void isValidSingleEventString_eventStringWithoutEventNameField_returnsFalse() {
        assertEquals(false, seedu.tarence.commons.util.JsonUtil.isValidSingleEventString(
                INVALID_SINGLE_EVENT_STRING_WITH_MISSING_FIELD));
    }

    @Test
    public void isValidSingleEventString_eventStringWithIncorrectFieldOrder_returnsFalse() {
        assertEquals(false, seedu.tarence.commons.util.JsonUtil.isValidSingleEventString(
                INVALID_SINGLE_EVENT_STRING_WITH_INCORRECT_ORDER_OF_FIELD));
    }

    @Test
    public void isValidTutorialMap_validTutorialMap_returnsTrue() {
        assertEquals(true, JsonUtil.isValidTutorialMap(seedu.tarence.testutil.JsonUtil.getValidTutorialMap()));
    }

    @Test
    public void isValidTutorialMap_invalidTutorialMapMissingWeeks_returnsFalse() {
        assertEquals(false, JsonUtil.isValidTutorialMap(
                seedu.tarence.testutil.JsonUtil.getInvalidTutorialMapWithoutWeeks()));
    }

    @Test
    public void isValidEventString_validEventString_returnsTrue() {
        assertEquals(true, JsonUtil.isValidEventString(2, VALID_EVENT_STRING));
    }

    @Test
    public void isValidEventString_invalidEventStringWithWrongOrder_returnsFalse() {
        assertEquals(false, JsonUtil.isValidEventString(2, INVALID_EVENT_STRING_WITH_INCORRECT_ORDERING));
    }

    @Test
    public void isValidStudentWithAssignmentString_validStudentAssignmentString_returnsTrue() {
        assertEquals(true, JsonUtil.isValidStudentWithAssignmentString(VALID_STUDENT_ASSIGNMENT_STRING));
    }

    @Test
    public void isValidStudentWithAssignmentString_invalidStudentAssignmentStringWithNoAssignmentScore_returnsFalse() {
        assertEquals(false,
                JsonUtil.isValidStudentWithAssignmentString(
                        INVALID_STUDENT_ASSIGNMENT_STRING_WITHOUT_ASSIGNMENT_SCORE));
    }

    @Test
    public void studentStringsToStudent_validArguments_returnsTrue() {
        Name name = new Name("Bob");
        Email email = new Email("bob@gmail.com");
        MatricNum matricNum = new MatricNum("A0012334X");
        Optional<MatricNum> optionalMatricNum = Optional.of(matricNum);
        NusnetId nusnetId = new NusnetId("E0031440");
        Optional<NusnetId> optionalNusnetId = Optional.of(nusnetId);
        ModCode modCode = new ModCode("CS2103");
        TutName tutName = new TutName("Lab 01");
        Student student = new Student(name, email, optionalMatricNum, optionalNusnetId, modCode, tutName);

        Student studentFromJsonUtil = JsonUtil.studentStringsToStudent("Bob",
                "bob@gmail.com", "Optional[A0012334X]", "Optional[E0031440]",
                "CS2103", "Lab 01");

        assertEquals(student, studentFromJsonUtil);
    }

    @Test
    public void studentStringToStudent_validArguments_returnsTrue() {
        try {
            Student studentFromJsonUtil = JsonUtil.studentStringToStudent(VALID_SINGLE_STUDENT_STRING);
            Name name = new Name("Bob");
            Email email = new Email("e0012952@gmail.com");
            MatricNum matricNum = new MatricNum("A0155413X");
            Optional<MatricNum> optionalMatricNum = Optional.of(matricNum);
            NusnetId nusnetId = new NusnetId("E0031550");
            Optional<NusnetId> optionalNusnetId = Optional.of(nusnetId);
            ModCode modCode = new ModCode("CS1010E");
            TutName tutName = new TutName("Lab Session");
            Student student = new Student(name, email, optionalMatricNum, optionalNusnetId, modCode, tutName);

            assertEquals(student.toString(), studentFromJsonUtil.toString());

        } catch (IllegalValueException e) {
            System.out.println(UNHANDLED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void studentStringToStudent_invalidFields_illegalValueExceptionThrown() {
        assertThrows(IllegalValueException.class, () -> JsonUtil.studentStringToStudent(INVALID_SINGLE_STUDENT_STRING));
    }

    @Test
    public void studentStringToList_studentStringWithTwoStudents_returnsTrue() {

        try {
            ArrayList<Student> studentFromJsonUtil = (ArrayList<Student>) JsonUtil.studentStringToList(
                    VALID_STUDENT_LIST_STRING_WITH_TWO_STUDENTS);

            Name name = new Name("Bob");
            Email email = new Email("e0012952@gmail.com");
            MatricNum matricNum = new MatricNum("A0155413X");
            Optional<MatricNum> optionalMatricNum = Optional.of(matricNum);
            NusnetId nusnetId = new NusnetId("E0031550");
            Optional<NusnetId> optionalNusnetId = Optional.of(nusnetId);
            ModCode modCode = new ModCode("CS1010E");
            TutName tutName = new TutName("Lab Session");
            Student firstStudent = new Student(name, email, optionalMatricNum, optionalNusnetId, modCode, tutName);

            name = new Name("Prof Damith");
            email = new Email("e7112352@u.nus.edu.sg");
            optionalMatricNum = Optional.empty();
            optionalNusnetId = Optional.empty();
            modCode = new ModCode("CS1010E");
            tutName = new TutName("Lab Session");
            Student secondStudent = new Student(name, email, optionalMatricNum, optionalNusnetId, modCode, tutName);

            List<Student> studentList = new ArrayList<>();
            studentList.add(firstStudent);
            studentList.add(secondStudent);

            assertEquals(studentList.toString(), studentFromJsonUtil.toString());

        } catch (IllegalValueException e) {
            System.out.println(UNHANDLED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void studentAttendanceStringToMap_validArguments_returnsTrue() {

        try {
            LinkedHashMap<Student, Boolean> jsonUtilMap =
                    (LinkedHashMap<Student, Boolean>) JsonUtil.studentAttendanceStringToMap(
                            VALID_SINGLE_WEEK_TUTORIAL_ATTENDANCE_STRING);

            Name name = new Name("Bob");
            Email email = new Email("e0012952@gmail.com");
            MatricNum matricNum = new MatricNum("A0155413X");
            Optional<MatricNum> optionalMatricNum = Optional.of(matricNum);
            NusnetId nusnetId = new NusnetId("E0031550");
            Optional<NusnetId> optionalNusnetId = Optional.of(nusnetId);
            ModCode modCode = new ModCode("CS1010E");
            TutName tutName = new TutName("Lab Session");
            Student firstStudent = new Student(name, email, optionalMatricNum, optionalNusnetId, modCode, tutName);

            name = new Name("Prof Damith");
            email = new Email("e7112352@u.nus.edu.sg");
            optionalMatricNum = Optional.empty();
            optionalNusnetId = Optional.empty();
            modCode = new ModCode("CS1010E");
            tutName = new TutName("Lab Session");
            Student secondStudent = new Student(name, email, optionalMatricNum, optionalNusnetId, modCode, tutName);

            LinkedHashMap<Student, Boolean> attendanceMap = new LinkedHashMap<>();
            attendanceMap.put(firstStudent, false);
            attendanceMap.put(secondStudent, false);

            assertEquals(attendanceMap.toString(), jsonUtilMap.toString());
        } catch (IllegalValueException e) {
            System.out.println(UNHANDLED_EXCEPTION_MESSAGE);
        }

    }

    @Test
    public void attendanceStringToAttendance_validArguments_returnsTrue() {

        try {
            Week weekOne = new Week(1);
            Week weekFour = new Week(4);
            Week weekSeven = new Week(7);
            Set<Week> listOfWeeks = new TreeSet<>();
            listOfWeeks.add(weekOne);
            listOfWeeks.add(weekFour);
            listOfWeeks.add(weekSeven);

            Name name = new Name("Bob");
            Email email = new Email("e0012952@gmail.com");
            MatricNum matricNum = new MatricNum("A0155413X");
            Optional<MatricNum> optionalMatricNum = Optional.of(matricNum);
            NusnetId nusnetId = new NusnetId("E0031550");
            Optional<NusnetId> optionalNusnetId = Optional.of(nusnetId);
            ModCode modCode = new ModCode("CS1010E");
            TutName tutName = new TutName("Lab Session");
            Student firstStudent = new Student(name, email, optionalMatricNum, optionalNusnetId, modCode, tutName);

            List<Student> studentList = new ArrayList<>();
            studentList.add(firstStudent);

            Attendance attendance = new Attendance(listOfWeeks, studentList);
            Attendance jsonAttendance = JsonUtil.attendanceStringToAttendance
                    (VALID_TUTORIAL_ATTENDANCE_STRING, listOfWeeks);

            assertEquals(attendance.toString(), jsonAttendance.toString());
        } catch (IllegalValueException e) {
            System.out.println(UNHANDLED_EXCEPTION_MESSAGE);
        }

    }

    @Test
    public void assignmentListToString_validArguments_returnsTrue() {
        try {
            Name name = new Name("Bob");
            Email email = new Email("e0012952@gmail.com");
            MatricNum matricNum = new MatricNum("A0155413X");
            Optional<MatricNum> optionalMatricNum = Optional.of(matricNum);
            NusnetId nusnetId = new NusnetId("E0031550");
            Optional<NusnetId> optionalNusnetId = Optional.of(nusnetId);
            ModCode modCode = new ModCode("CS1010E");
            TutName tutName = new TutName("Lab Session");
            Student firstStudent = new Student(name, email, optionalMatricNum, optionalNusnetId, modCode, tutName);

            Map<Student, Integer> firstAssignmentMap = new LinkedHashMap<>();
            firstAssignmentMap.put(firstStudent, -1);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date startDate = dateFormatter.parse("Wed Oct 10 00:00:00 SGT 2012");
            Date endDate = dateFormatter.parse("Thu Oct 10 23:59:00 SGT 2019");

            Assignment firstAssignment = new Assignment("Assignment A", 100, startDate, endDate);

            Map<Assignment, Map<Student, Integer>> assignmentStudentScoreMap = new LinkedHashMap<>();

            assignmentStudentScoreMap.put(firstAssignment, firstAssignmentMap);

            String assignmentString = JsonUtil.assignmentListToString(assignmentStudentScoreMap);

            assertEquals(assignmentString.length(), VALID_TUTORIAL_ASSIGNMENT_STRING.length());
        } catch (ParseException e) {
            System.out.println(UNHANDLED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void tutorialAssignmentStringToAssignment_validArguments_returnsTrue() {
        try {
            Name name = new Name("Bob");
            Email email = new Email("e0012952@gmail.com");
            MatricNum matricNum = new MatricNum("A0155413X");
            Optional<MatricNum> optionalMatricNum = Optional.of(matricNum);
            NusnetId nusnetId = new NusnetId("E0031550");
            Optional<NusnetId> optionalNusnetId = Optional.of(nusnetId);
            ModCode modCode = new ModCode("CS1010E");
            TutName tutName = new TutName("Lab Session");
            Student firstStudent = new Student(name, email, optionalMatricNum, optionalNusnetId, modCode, tutName);

            Map<Student, Integer> firstAssignmentMap = new LinkedHashMap<>();
            firstAssignmentMap.put(firstStudent, -1);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date startDate = dateFormatter.parse("Wed Oct 10 00:00:00 SGT 2012");
            Date endDate = dateFormatter.parse("Thu Oct 10 23:59:00 SGT 2019");

            Assignment firstAssignment = new Assignment("Assignment A", 100, startDate, endDate);

            Map<Assignment, Map<Student, Integer>> assignmentStudentScoreMap = new LinkedHashMap<>();

            assignmentStudentScoreMap.put(firstAssignment, firstAssignmentMap);

            Map<Assignment, Map<Student, Integer>> assignmentMapFromJsonUtil =
                    JsonUtil.tutorialAssignmentStringToAssignment(VALID_TUTORIAL_ASSIGNMENT_STRING);

            assertEquals(assignmentMapFromJsonUtil.toString(), assignmentStudentScoreMap.toString());
        } catch (IllegalValueException | ParseException e) {
            System.out.println(UNHANDLED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void eventListToString_validArguments_returnsTrue() {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date startDate = dateFormatter.parse("Wed Oct 10 00:00:00 SGT 2012");
            Date endDate = dateFormatter.parse("Thu Oct 10 23:59:00 SGT 2019");

            Event firstEvent = new Event("Assignment A", startDate, endDate);
            Event secondEvent = new Event("Assignment B", startDate, endDate);

            ArrayList<Event> listOfEvent = new ArrayList<>();

            listOfEvent.add(firstEvent);
            listOfEvent.add(secondEvent);

            String eventStringFromJsonUtil = JsonUtil.eventListToString(listOfEvent);

            assertEquals(eventStringFromJsonUtil.length(), VALID_TUTORIAL_EVENT_STRING.length());
        } catch (ParseException e) {
            System.out.println(UNHANDLED_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void tutorialEventStringToEventList_validArguments_returnsTrue() {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date startDate = dateFormatter.parse("Wed Oct 10 00:00:00 SGT 2012");
            Date endDate = dateFormatter.parse("Thu Oct 10 23:59:00 SGT 2019");

            Event firstEvent = new Event("Assignment A", startDate, endDate);
            Event secondEvent = new Event("Assignment B", startDate, endDate);

            ArrayList<Event> listOfEvent = new ArrayList<>();

            listOfEvent.add(firstEvent);
            listOfEvent.add(secondEvent);

            ArrayList<Event> eventListFromJsonUtil = (ArrayList<Event>)
                    JsonUtil.tutorialEventStringToEventList(VALID_TUTORIAL_EVENT_STRING);

            assertEquals(listOfEvent.toString(), eventListFromJsonUtil.toString());

        } catch (IllegalValueException | ParseException e) {
            System.out.println(UNHANDLED_EXCEPTION_MESSAGE);
        }

    }

    @Test
    public void isValidTutorialString_validArguments_returnsTrue() {
        String validTutorialString = JsonAdaptedModule.TUTORIAL_NAME + JsonAdaptedModule.TUTORIAL_DAY
                + JsonAdaptedModule.TUTORIAL_START_TIME + JsonAdaptedModule.TUTORIAL_WEEKS
                + JsonAdaptedModule.TUTORIAL_DURATION
                + JsonAdaptedModule.TUTORIAL_STUDENT_LIST + JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST
                + JsonAdaptedModule.TUTORIAL_MODULE_CODE;
        assertTrue(JsonUtil.isValidTutorialString(validTutorialString));
    }
}
