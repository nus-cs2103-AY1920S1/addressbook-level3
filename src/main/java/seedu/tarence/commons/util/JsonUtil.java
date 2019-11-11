package seedu.tarence.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.logic.parser.ParserUtil;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Email;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.MatricNum;
import seedu.tarence.model.student.NusnetId;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Attendance;
import seedu.tarence.model.tutorial.Event;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;
import seedu.tarence.storage.JsonAdaptedModule;

/**
 * Converts a Java object instance to JSON and vice versa
 */
public class JsonUtil {

    private static final Logger logger = LogsCenter.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .registerModule(new SimpleModule("SimpleModule")
                    .addSerializer(Level.class, new ToStringSerializer())
                    .addDeserializer(Level.class, new LevelDeserializer(Level.class)));

    static <T> void serializeObjectToJsonFile(Path jsonFile, T objectToSerialize) throws IOException {
        FileUtil.writeToFile(jsonFile, toJsonString(objectToSerialize));
    }

    static <T> T deserializeObjectFromJsonFile(Path jsonFile, Class<T> classOfObjectToDeserialize)
            throws IOException {
        return fromJsonString(FileUtil.readFromFile(jsonFile), classOfObjectToDeserialize);
    }

    //============================ General Json Utility methods ========================================================

    /**
     * Returns the Json object from the given file or {@code Optional.empty()} object if the file is not found.
     * If any values are missing from the file, default values will be used, as long as the file is a valid json file.
     * @param filePath cannot be null.
     * @param classOfObjectToDeserialize Json file has to correspond to the structure in the class given here.
     * @throws DataConversionException if the file format is not as expected.
     */
    public static <T> Optional<T> readJsonFile(
            Path filePath, Class<T> classOfObjectToDeserialize) throws DataConversionException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Json file " + filePath + " not found");
            return Optional.empty();
        }

        T jsonFile;

        try {
            jsonFile = deserializeObjectFromJsonFile(filePath, classOfObjectToDeserialize);
        } catch (IOException e) {
            logger.warning("Error reading from jsonFile file " + filePath + ": " + e);
            throw new DataConversionException(e);
        }

        return Optional.of(jsonFile);
    }

    /**
     * Saves the Json object to the specified file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     * @param jsonFile cannot be null
     * @param filePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static <T> void saveJsonFile(T jsonFile, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(jsonFile);

        serializeObjectToJsonFile(filePath, jsonFile);
    }


    /**
     * Converts a given string representation of a JSON data to instance of a class
     * @param <T> The generic type to create an instance of
     * @return The instance of T with the specified values in the JSON string
     */
    public static <T> T fromJsonString(String json, Class<T> instanceClass) throws IOException {
        return objectMapper.readValue(json, instanceClass);
    }

    /**
     * Converts a given instance of a class into its JSON data string representation
     * @param instance The T object to be converted into the JSON string
     * @param <T> The generic type to create an instance of
     * @return JSON data representation of the given class instance, in string
     */
    public static <T> String toJsonString(T instance) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(instance);
    }

    /**
     * Returns the exact field of an identifier.
     * Pre-condition: Desired field must not be the last field of the string.
     *
     * @param identifier Desired field to extract.
     * @param nextIdentifier Subsequent identifier located after the desired identified.
     * @param sequence String that contains the fields and identifiers.
     * @return Exact String field of the identifier.
     */
    public static String extractField(String identifier, String nextIdentifier, String sequence) {
        return sequence.substring(sequence.indexOf(identifier)
                + identifier.length() + 1, sequence.indexOf(nextIdentifier) - 2).trim();
    }

    /**
     * Returns the last field of an identifier.
     *
     * @param identifier Last field to be extracted from a String sequence.
     * @param sequence String that contains the last field and identifier.
     * @return Exact String field of the identifier.
     */
    public static String extractLastField(String identifier, String sequence) {
        return sequence.substring(sequence.indexOf(identifier)
                + identifier.length() + 1).replace("}", "").trim();
    }

    /**
     * Generic function to construct a new TreeMap from HashMap
     *
     * @param hashMap hashMap
     * @param <K>
     * @param <V>
     * @return Treemap
     */
    public static <K, V> Map<K, V> convertToTreeMap(Map<K, V> hashMap) {

        Map<K, V> treeMap = hashMap
                // Get the entries from the hashMap
                .entrySet()

                // Convert the map into stream
                .stream()

                // Now collect the returned TreeMap
                .collect(
                        Collectors

                                // Using Collectors, collect the entries
                                // and convert it into TreeMap
                                .toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue, (
                                                oldValue, newValue) -> newValue, TreeMap::new));

        // Return the TreeMap
        return treeMap;
    }

    /**
     * Contains methods that retrieve logging level from serialized string.
     */
    private static class LevelDeserializer extends FromStringDeserializer<Level> {

        protected LevelDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        protected Level _deserialize(String value, DeserializationContext ctxt) {
            return getLoggingLevel(value);
        }

        /**
         * Gets the logging level that matches loggingLevelString
         * <p>
         * Returns null if there are no matches
         *
         */
        private Level getLoggingLevel(String loggingLevelString) {
            return Level.parse(loggingLevelString);
        }

        @Override
        public Class<Level> handledType() {
            return Level.class;
        }
    }


    //============================== Validity String-checker methods ===================================================


    /**
     * Checks if the tutorialString contains valid fields.
     *
     * @param tutorialString
     * @return Boolean.
     */
    public static Boolean isValidTutorialString(String tutorialString) {
        return (tutorialString.contains(JsonAdaptedModule.TUTORIAL_WEEKS)
                && tutorialString.contains(JsonAdaptedModule.TUTORIAL_DAY)
                && tutorialString.contains(JsonAdaptedModule.TUTORIAL_DURATION)
                && tutorialString.contains(JsonAdaptedModule.TUTORIAL_MODULE_CODE)
                && tutorialString.contains(JsonAdaptedModule.TUTORIAL_NAME)
                && tutorialString.contains(JsonAdaptedModule.TUTORIAL_START_TIME)
                && tutorialString.contains(JsonAdaptedModule.TUTORIAL_STUDENT_LIST)
                && tutorialString.contains(JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST)
                && (tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_NAME)
                < tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_DAY))
                && (tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_DAY)
                < tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_START_TIME))
                && (tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_START_TIME)
                < tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_WEEKS))
                && (tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_WEEKS)
                < tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_DURATION))
                && (tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_DURATION)
                < tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_STUDENT_LIST))
                && (tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_STUDENT_LIST)
                < tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST))
                && (tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST)
                < tutorialString.indexOf(JsonAdaptedModule.TUTORIAL_MODULE_CODE)));
    }

    /**
     * Checks if the tutorial Map contains the valid keys
     *
     * @param singleTutorialMap from Json file.
     * @return True hopefully
     */
    public static Boolean isValidTutorialMap(LinkedHashMap<String, String> singleTutorialMap) {

        return singleTutorialMap.containsKey(JsonAdaptedModule.TUTORIAL_NAME)
                && singleTutorialMap.containsKey(JsonAdaptedModule.TUTORIAL_DAY)
                && singleTutorialMap.containsKey(JsonAdaptedModule.TUTORIAL_START_TIME)
                && singleTutorialMap.containsKey(JsonAdaptedModule.TUTORIAL_WEEKS)
                && singleTutorialMap.containsKey(JsonAdaptedModule.TUTORIAL_DURATION)
                && singleTutorialMap.containsKey(JsonAdaptedModule.TUTORIAL_STUDENT_LIST)
                && singleTutorialMap.containsKey(JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST)
                && singleTutorialMap.containsKey(JsonAdaptedModule.TUTORIAL_MODULE_CODE)
                && singleTutorialMap.containsKey(JsonAdaptedModule.TUTORIAL_ASSIGNMENT_LIST);

    }

    /**
     * Checks if the studentString contains valid fields and the order correct.
     *
     * @param studentString String representing a Student from Json object.
     * @return Boolean.
     */
    public static Boolean isValidStudentString(String studentString) {
        return (studentString.contains(JsonAdaptedModule.STUDENT_EMAIL)
                && studentString.contains(JsonAdaptedModule.STUDENT_MATRIC_NUMBER)
                && studentString.contains(JsonAdaptedModule.STUDENT_MODULE_CODE)
                && studentString.contains(JsonAdaptedModule.STUDENT_NAME)
                && studentString.contains(JsonAdaptedModule.STUDENT_NUSNET_ID)
                && studentString.contains(JsonAdaptedModule.STUDENT_TUTORIAL_NAME)
                && (studentString.indexOf(JsonAdaptedModule.STUDENT_NAME)
                < studentString.indexOf(JsonAdaptedModule.STUDENT_EMAIL))
                && (studentString.indexOf(JsonAdaptedModule.STUDENT_EMAIL)
                < studentString.indexOf(JsonAdaptedModule.STUDENT_MATRIC_NUMBER))
                && (studentString.indexOf(JsonAdaptedModule.STUDENT_MATRIC_NUMBER)
                < studentString.indexOf(JsonAdaptedModule.STUDENT_NUSNET_ID))
                && (studentString.indexOf(JsonAdaptedModule.STUDENT_NUSNET_ID)
                < studentString.indexOf(JsonAdaptedModule.STUDENT_MODULE_CODE))
                && (studentString.indexOf(JsonAdaptedModule.STUDENT_MODULE_CODE)
                < studentString.indexOf(JsonAdaptedModule.STUDENT_TUTORIAL_NAME)));
    }

    /**
     * Checks if a studentAttendanceString is valid.
     *
     * @param studentWithAttendanceString String representing a Student, with attendance status, from Json object.
     * @return Boolean.
     */
    public static Boolean isValidStudentWithAttendanceString(String studentWithAttendanceString) {
        return (studentWithAttendanceString.contains(JsonAdaptedModule.STUDENT_EMAIL)
                && studentWithAttendanceString.contains(JsonAdaptedModule.STUDENT_MATRIC_NUMBER)
                && studentWithAttendanceString.contains(JsonAdaptedModule.STUDENT_MODULE_CODE)
                && studentWithAttendanceString.contains(JsonAdaptedModule.STUDENT_NAME)
                && studentWithAttendanceString.contains(JsonAdaptedModule.STUDENT_NUSNET_ID)
                && studentWithAttendanceString.contains(JsonAdaptedModule.STUDENT_TUTORIAL_NAME)
                && studentWithAttendanceString.contains(JsonAdaptedModule.STUDENT_ATTENDANCE_STATUS)
                && (studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_NAME)
                < studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_EMAIL))
                && (studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_EMAIL)
                < studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_MATRIC_NUMBER))
                && (studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_MATRIC_NUMBER)
                < studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_NUSNET_ID))
                && (studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_NUSNET_ID)
                < studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_MODULE_CODE))
                && (studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_MODULE_CODE)
                < studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_TUTORIAL_NAME))
                && (studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_TUTORIAL_NAME)
                < studentWithAttendanceString.indexOf(JsonAdaptedModule.STUDENT_ATTENDANCE_STATUS)));
    }

    /**
     * Checks if a single event String is valid.
     *
     * @param singleTutorialEventString String representing a single Event.
     * @return Boolean.
     */
    public static Boolean isValidSingleEventString(String singleTutorialEventString) {
        return (singleTutorialEventString.contains(JsonAdaptedModule.EVENT_NAME)
                && singleTutorialEventString.contains(JsonAdaptedModule.EVENT_START_DATE)
                && singleTutorialEventString.contains(JsonAdaptedModule.EVENT_END_DATE)
                && (singleTutorialEventString.indexOf(JsonAdaptedModule.EVENT_NAME)
                < singleTutorialEventString.indexOf(JsonAdaptedModule.EVENT_START_DATE))
                && (singleTutorialEventString.indexOf(JsonAdaptedModule.EVENT_START_DATE)
                < singleTutorialEventString.indexOf(JsonAdaptedModule.EVENT_END_DATE)));
    }

    /**
     * Checks if the whole event String (represents more than 1 event) is valid.
     *
     * @param numOfEvents Total number of events.
     * @param eventString String.
     * @return Boolean.
     */
    public static Boolean isValidEventString(int numOfEvents, String eventString) {
        LinkedHashMap<Integer, String> identifiers = new LinkedHashMap();

        for (int i = 1; i <= numOfEvents; i++) {
            String identifier = JsonAdaptedModule.EVENT_NUMBER + i;
            identifiers.put(i, identifier);
        }

        for (int i = 2; i <= numOfEvents; i++) {
            String firstIdentifier = identifiers.get(i - 1);
            String secondIdentifier = identifiers.get(i);
            // Checks for ordering of EVENT_NUMBER
            if (eventString.indexOf(firstIdentifier) > eventString.indexOf(secondIdentifier)) {
                return false;
            }
        }
        return true;

    }

    /**
     * Checks if a single Student String, with Assignment, is valid.
     *
     * @param studentAssignmentString String.
     * @return Boolean.
     */
    public static Boolean isValidStudentWithAssignmentString(String studentAssignmentString) {
        return (studentAssignmentString.contains(JsonAdaptedModule.STUDENT_EMAIL)
                && studentAssignmentString.contains(JsonAdaptedModule.STUDENT_MATRIC_NUMBER)
                && studentAssignmentString.contains(JsonAdaptedModule.STUDENT_MODULE_CODE)
                && studentAssignmentString.contains(JsonAdaptedModule.STUDENT_NAME)
                && studentAssignmentString.contains(JsonAdaptedModule.STUDENT_NUSNET_ID)
                && studentAssignmentString.contains(JsonAdaptedModule.STUDENT_TUTORIAL_NAME)
                && studentAssignmentString.contains(JsonAdaptedModule.STUDENT_ASSIGNMENT_SCORE)
                && (studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_NAME)
                < studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_EMAIL))
                && (studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_EMAIL)
                < studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_MATRIC_NUMBER))
                && (studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_MATRIC_NUMBER)
                < studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_NUSNET_ID))
                && (studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_NUSNET_ID)
                < studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_MODULE_CODE))
                && (studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_MODULE_CODE)
                < studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_TUTORIAL_NAME))
                && (studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_TUTORIAL_NAME)
                < studentAssignmentString.indexOf(JsonAdaptedModule.STUDENT_ASSIGNMENT_SCORE)));
    }


    //============================== From object to Json String methods ================================================

    /**
     * Converts a single Module to its LinkedHashMap representation.
     *
     * @param source Module.
     * @return LinkedHashMap
     */
    public static LinkedHashMap<String, LinkedHashMap<String, String>> moduleToHashMap(Module source) {

        LinkedHashMap<String, LinkedHashMap<String, String>> tutorialMap =
                new LinkedHashMap<String, LinkedHashMap<String, String>>();

        for (Tutorial t : source.getTutorials()) {
            LinkedHashMap<String, String> singleTutorialMap = new LinkedHashMap<String, String>();

            // Obtain all the fields that defines a single Tutorial object.
            String tutorialName = t.getTutName().toString();
            String tutorialDayOfWeek = t.getTimeTable().getDay().toString();
            String tutorialStartTime = t.getTimeTable().getStartTime().toString();
            String tutorialWeeks = t.getTimeTable().getWeeks().toString();
            String tutorialDuration = t.getTimeTable().getDuration().toString();
            String studentListString = studentListToString(t.getStudents());
            String tutorialModuleCode = t.getModCode().toString();
            String tutorialAttendanceString = attendanceListToString(t.getAttendance());
            String tutorialAssignmentString = assignmentListToString(t.getAssignmentsForSaving());
            String tutorialEventString = JsonUtil.eventListToString(t.getEventListForSaving());

            // Add into LinkedHashMap<String,String>, singleTutorialMap. Reading is order dependant
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_NAME, tutorialName);
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_DAY, tutorialDayOfWeek);
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_START_TIME, tutorialStartTime);
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_WEEKS, tutorialWeeks);
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_DURATION, tutorialDuration);
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_STUDENT_LIST, studentListString);
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST, tutorialAttendanceString);
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_MODULE_CODE, tutorialModuleCode);
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_ASSIGNMENT_LIST, tutorialAssignmentString);
            singleTutorialMap.put(JsonAdaptedModule.TUTORIAL_EVENT_LIST, tutorialEventString);

            tutorialMap.put(t.toString(), singleTutorialMap);
        }
        return tutorialMap;
    }

    /**
     * Invoked when saving an Attendance object.
     * Converts an Attendance object to String.
     *
     * @param attendance Attendance object.
     * @return String representation of Attendance object.
     */
    public static String attendanceListToString(Attendance attendance) {
        Map<Week, Map<Student, Boolean>> attendanceMap = attendance.getAttendance();
        LinkedHashMap<String, String> attendanceStringMap = new LinkedHashMap<String, String>();

        for (Week week : attendanceMap.keySet()) {
            Map<Student, Boolean> singleWeek = attendanceMap.get(week);

            // Each student is encompassed in [].
            String attendanceString = "[";
            for (Student s : singleWeek.keySet()) {
                LinkedHashMap<String, String> studentMap = new LinkedHashMap<String, String>();
                studentMap.put(JsonAdaptedModule.STUDENT_NAME, s.getName().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_EMAIL, s.getEmail().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_MATRIC_NUMBER, s.getMatricNum().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_NUSNET_ID, s.getNusnetId().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_MODULE_CODE, s.getModCode().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_TUTORIAL_NAME, s.getTutName().toString());
                studentMap.put(JsonAdaptedModule.STUDENT_ATTENDANCE_STATUS, singleWeek.get(s).toString());
                attendanceString = attendanceString + studentMap.toString() + "],[";
            }

            // Case when there are no students.
            if (attendanceString.equals("[")) {
                attendanceString += "]";
            } else {
                // Remove the last square bracket
                attendanceString = attendanceString.substring(0, (attendanceString.length() - 2));
            }

            // Mapping of weeks to studentStrings example:  {1=[{studentObe}],[{studentTwo}],
            //                                               2=[{studentOne}],[{studentTwo}]}
            //where '1' and '2' are the weeks and studentOne and studentTwo are the String representations of 2 Students
            attendanceStringMap.put(week.toString(), attendanceString);
        }
        return attendanceStringMap.toString();
    }

    /**
     * Converts a list of Students to a String.
     * Eg. [{studentName=Ellie Yee, studentEmail=e0035152@u.nus.edu.sg, studentMatricNumber=OptionalA0155413M,
     * studentNusnetId=OptionalE0031550, studentModuleCode=CS1010S, studentTutorialName=Lab Session}]
     *
     * @param studentList List of Student objects.
     * @return String representation of a Student List.
     */
    public static String studentListToString(List<Student> studentList) {
        String studentListString = "[";

        for (Student s : studentList) {
            LinkedHashMap<String, String> studentMap = new LinkedHashMap<String, String>();
            studentMap.put(JsonAdaptedModule.STUDENT_NAME, s.getName().toString());
            studentMap.put(JsonAdaptedModule.STUDENT_EMAIL, s.getEmail().toString());
            studentMap.put(JsonAdaptedModule.STUDENT_MATRIC_NUMBER, s.getMatricNum().toString());
            studentMap.put(JsonAdaptedModule.STUDENT_NUSNET_ID, s.getNusnetId().toString());
            studentMap.put(JsonAdaptedModule.STUDENT_MODULE_CODE, s.getModCode().toString());
            studentMap.put(JsonAdaptedModule.STUDENT_TUTORIAL_NAME, s.getTutName().toString());
            studentListString = studentListString + studentMap.toString() + "],[";
        }

        // Remove the last instance of "[,]" from studentListString
        if (studentList.size() != 0) {
            studentListString = studentListString.substring(0, (studentListString.length() - 2));
        } else {
            // There are no students in the list. studentListString is just "[]".
            studentListString = studentListString + "]";
        }
        return studentListString;
    }

    /**
     * Returns the string representation of a Tutorial's Assignment(s).
     *
     * @param assignmentList Assignment List, obtained directly from Tutorial.
     * @return String.
     */
    public static String assignmentListToString(Map<Assignment, Map<Student, Integer>> assignmentList) {

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

            assignmentMap.put(JsonAdaptedModule.ASSIGNMENT_NAME, assignmentName);
            assignmentMap.put(JsonAdaptedModule.ASSIGNMENT_MAX_SCORE, assignmentMaxScore);
            assignmentMap.put(JsonAdaptedModule.ASSIGNMENT_START_DATE, assignmentStartDate);
            assignmentMap.put(JsonAdaptedModule.ASSIGNMENT_END_DATE, assignmentEndDate);
            assignmentMap.put(JsonAdaptedModule.ASSIGNMENT_STUDENT_LIST, assignmentStudentString);

            assignmentCounter++;
            listOfAssignments.put(JsonAdaptedModule.ASSIGNMENT_NUMBER + assignmentCounter.toString(),
                    assignmentMap.toString());
        }

        return listOfAssignments.toString();
    }

    /**
     * Serializes an Event List to a Json-friendly string.
     *
     * @param eventList List of Events
     * @return String representation of an event.
     */
    public static String eventListToString(List<Event> eventList) {
        LinkedHashMap<String, String> eventHashMap = new LinkedHashMap<>();
        Integer eventCounter = 0;

        for (Event e : eventList) {
            eventCounter++;
            String eventName = e.eventName;
            String eventStartDate = e.startTime.toString();
            String eventEndDate = e.endTime.toString();

            LinkedHashMap<String, String> singleEventMap = new LinkedHashMap<>();
            singleEventMap.put(JsonAdaptedModule.EVENT_NAME, eventName);
            singleEventMap.put(JsonAdaptedModule.EVENT_START_DATE, eventStartDate);
            singleEventMap.put(JsonAdaptedModule.EVENT_END_DATE, eventEndDate);

            eventHashMap.put((JsonAdaptedModule.EVENT_NUMBER + eventCounter), singleEventMap.toString());
        }

        return eventHashMap.toString();
    }


    // ============================== Parsing student-Json-String to Student objects ===================================


    /**
     * Creates a Student Object with the given parsed String params.
     *
     * @param studentNameString Parsed student name string.
     * @param studentEmailString Parsed student email string.
     * @param studentMatricNumberString Parsed student matriculation number string.
     * @param studentNusnetIdString Parsed student NUSNET ID string.
     * @param studentModuleCodeString Parsed student module code string.
     * @param studentTutorialNameString Parsed student tutorial string.
     * @return Student object.
     */
    public static Student studentStringsToStudent(String studentNameString, String studentEmailString,
                                                  String studentMatricNumberString, String studentNusnetIdString,
                                                  String studentModuleCodeString, String studentTutorialNameString) {

        // Populates the fields needed to create a Student object.
        studentNameString = studentNameString.replace("[", "").replace("]", "");
        Name studentName = new Name(studentNameString);

        studentEmailString = studentEmailString.replace("[", "").replace("]", "");
        Email studentEmail = new Email(studentEmailString);

        Optional<MatricNum> studentMatricNumber = Optional.empty();
        if (studentMatricNumberString.contains("empty")) {
            studentMatricNumber = Optional.empty();
        } else {
            studentMatricNumberString = studentMatricNumberString.replace("Optional", "");
            studentMatricNumberString = studentMatricNumberString.replace("[", "").replace("]", "");
            studentMatricNumber = Optional.of(new MatricNum(studentMatricNumberString));
        }

        Optional<NusnetId> studentNusnetId = Optional.empty();
        if (studentNusnetIdString.contains("empty")) {
            studentNusnetId = Optional.empty();
        } else {
            studentNusnetIdString = studentNusnetIdString.replace("Optional", "");
            studentNusnetIdString = studentNusnetIdString.replace("[", "").replace("]", "");
            studentNusnetId = Optional.of(new NusnetId(studentNusnetIdString));
        }

        studentModuleCodeString = studentModuleCodeString.replace("[", "").replace("]", "");
        ModCode studentModuleCode = new ModCode(studentModuleCodeString);

        studentTutorialNameString = studentTutorialNameString.replace("[", "").replace("[", "");
        TutName studentTutorialName = new TutName(studentTutorialNameString);

        return new Student(studentName, studentEmail, studentMatricNumber, studentNusnetId,
                studentModuleCode, studentTutorialName);
    }

    /**
     * Converts a studentString, representing a single student, to a Student object.
     *
     * @param studentString String sequence representing a single Student.
     * @return Student object.
     */
    public static Student studentStringToStudent(String studentString) throws IllegalValueException {
        if (!isValidStudentString(studentString)) {
            throw new IllegalValueException(String.format(JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT,
                    Student.class.getSimpleName()));
        }

        // Extracts the correct Strings needed to create a Student object.
        String studentNameString = extractField(JsonAdaptedModule.STUDENT_NAME,
                JsonAdaptedModule.STUDENT_EMAIL, studentString);
        String studentEmailString = extractField(JsonAdaptedModule.STUDENT_EMAIL,
                JsonAdaptedModule.STUDENT_MATRIC_NUMBER, studentString);
        String studentMatricNumberString = extractField(JsonAdaptedModule.STUDENT_MATRIC_NUMBER,
                JsonAdaptedModule.STUDENT_NUSNET_ID, studentString);
        String studentNusnetIdString = extractField(JsonAdaptedModule.STUDENT_NUSNET_ID,
                JsonAdaptedModule.STUDENT_MODULE_CODE, studentString);
        String studentModuleCodeString = extractField(JsonAdaptedModule.STUDENT_MODULE_CODE,
                JsonAdaptedModule.STUDENT_TUTORIAL_NAME, studentString);
        String studentTutorialNameString = extractLastField(JsonAdaptedModule.STUDENT_TUTORIAL_NAME, studentString);

        Student studentFromJson = studentStringsToStudent(studentNameString, studentEmailString,
                studentMatricNumberString, studentNusnetIdString,
                studentModuleCodeString, studentTutorialNameString);

        return studentFromJson;
    }

    /**
     * Converts a student ListString to a list of Students.
     *
     * @param studentListString A String representing 0,1 or more Students.
     * @return List of Student objects.
     */
    public static List<Student> studentStringToList(String studentListString) throws IllegalValueException {
        List<Student> studentList = new ArrayList<Student>();
        if (studentListString.equals("[]")) {
            return studentList; //studentString is empty.
        }

        String[] students = studentListString.split("\\]\\,\\[");

        for (String s : students) {
            String studentString = s.replace("[", "").replace("]", "");
            Student studentFromJson = studentStringToStudent(studentString);
            studentList.add(studentFromJson);
        }
        return studentList;
    }


    //======================= Parsing attendance-Json-String to Attendance objects =====================================


    /**
     * Used to parse a studentString with attendance field, to a Map of Student objects and Booleans.
     *
     * @param studentAttendanceString A String representing students and their respective attendances for one week.
     * @return A Map of Student-Boolean, which will be used to construct the Attendance object.
     */
    public static Map<Student, Boolean> studentAttendanceStringToMap(String studentAttendanceString)
            throws IllegalValueException {
        Map<Student, Boolean> studentBooleanMap = new LinkedHashMap<Student, Boolean>();

        // Splits all the studentStrings to each individual student.
        String[] students = studentAttendanceString.split("\\]\\,\\[");
        for (String stringForOneStudent : students) {

            // Checks if the identifiers that represent a Student String is present
            // No error is thrown as some tutorials can be empty and have an empty list of students.
            if (isValidStudentWithAttendanceString(stringForOneStudent)) {
                // Removes all square brackets for each student string sequence.
                stringForOneStudent = (stringForOneStudent.replace("]", "").replace("[", ""));

                // Creates a Student Object & retrieves the Boolean Attendance status
                String studentNameString = extractField(JsonAdaptedModule.STUDENT_NAME,
                        JsonAdaptedModule.STUDENT_EMAIL, stringForOneStudent);

                String studentEmailString = extractField(JsonAdaptedModule.STUDENT_EMAIL,
                        JsonAdaptedModule.STUDENT_MATRIC_NUMBER, stringForOneStudent);

                String studentMatricNumberString = extractField(JsonAdaptedModule.STUDENT_MATRIC_NUMBER,
                        JsonAdaptedModule.STUDENT_NUSNET_ID,
                        stringForOneStudent);

                String studentNusnetIdString = extractField(JsonAdaptedModule.STUDENT_NUSNET_ID,
                        JsonAdaptedModule.STUDENT_MODULE_CODE,
                        stringForOneStudent);

                String studentModuleCodeString = extractField(JsonAdaptedModule.STUDENT_MODULE_CODE,
                        JsonAdaptedModule.STUDENT_TUTORIAL_NAME,
                        stringForOneStudent);

                String studentTutorialNameString = extractField(JsonAdaptedModule.STUDENT_TUTORIAL_NAME,
                        JsonAdaptedModule.STUDENT_ATTENDANCE_STATUS,
                        stringForOneStudent);

                Student studentFromAttendance = studentStringsToStudent(studentNameString, studentEmailString,
                        studentMatricNumberString, studentNusnetIdString,
                        studentModuleCodeString, studentTutorialNameString);

                String studentAttendanceStatus = extractLastField(JsonAdaptedModule.STUDENT_ATTENDANCE_STATUS,
                        stringForOneStudent);

                Boolean attendanceStatus = Boolean.parseBoolean(studentAttendanceStatus);

                studentBooleanMap.put(studentFromAttendance, attendanceStatus);
            }
        }
        return studentBooleanMap;
    }

    /**
     * Invoked during reading from Json String.
     *
     * Converts an attendanceString eg "[{studentName=Ellie Yee, studentEmail=e0035152@u.nus.edu.sg,
     * studentMatricNumber=Optional[A0155413M], studentNusnetId=Optional[E0031550],
     * studentModuleCode=CS1010S, studentTutorialName=Lab Session, studentAttendance=false}]"
     * to a Student-Boolean pair and then an Attendance object.
     *
     * @param attendanceString See above comments.
     * @param weeks Set of Weeks.
     * @return An attendance object.
     * @throws IllegalValueException when there is an error during parsing.
     */
    public static Attendance attendanceStringToAttendance(String attendanceString, Set<Week> weeks)
            throws IllegalValueException {
        Map<Week, Map<Student, Boolean>> attendance = new TreeMap<>();
        Week largestWeek = Collections.max(weeks);

        // Convert the set to an ordered arrayList of weeks,
        ArrayList<Week> arrayOfWeeks = new ArrayList<Week>();
        arrayOfWeeks.addAll(weeks);

        for (int i = 0; i < arrayOfWeeks.size(); i++) {
            Week currentWeek = arrayOfWeeks.get(i);
            Map<Student, Boolean> studentBooleanMap = new HashMap<Student, Boolean>();

            if (currentWeek != largestWeek) {
                Week nextWeek = arrayOfWeeks.get(i + 1);
                String studentAttendanceString = extractField(currentWeek.toString() + "=",
                        nextWeek.toString() + "=", attendanceString);
                studentBooleanMap = studentAttendanceStringToMap(studentAttendanceString);
            } else if (currentWeek == largestWeek) {
                String studentAttendanceString = extractLastField(currentWeek.toString() + "=",
                        attendanceString);
                studentBooleanMap = studentAttendanceStringToMap(studentAttendanceString);
            }
            attendance.put(currentWeek, studentBooleanMap);
        }

        return new Attendance(attendance);
    }


    //=============================== Parsing event-Json-String to Event object ========================================


    /**
     * Converts the String representation of Tutorial Events to a List of Events.
     *
     * @param tutorialEventString String representation of Tutorial Events
     * @return List of Events.
     * @throws IllegalValueException when there is an error during parsing.
     */
    public static List<Event> tutorialEventStringToEventList(String tutorialEventString) throws IllegalValueException {

        List <Event> eventList = new ArrayList<Event>();
        int numOfEvents = getTotalNumberOfEvents(tutorialEventString);

        if (numOfEvents == 0) {
            return eventList;
        }

        if (!isValidEventString(numOfEvents, tutorialEventString)) {
            throw new IllegalValueException("Tutorial Event String has invalid fields");
        }

        for (int i = 1; i < numOfEvents; i++) {

            String identifier = JsonAdaptedModule.EVENT_NUMBER + i;
            String nextIdentifier = JsonAdaptedModule.EVENT_NUMBER + (i + 1);

            String singleEventString = extractField(identifier, nextIdentifier, tutorialEventString);
            Event createdEvent = singleEventStringToEvent(singleEventString);

            eventList.add(createdEvent);

        }

        // Parse the final event string
        String finalIdentifier = JsonAdaptedModule.EVENT_NUMBER + numOfEvents;
        String finalSingleEventString = extractLastField(finalIdentifier, tutorialEventString);
        Event lastEvent = singleEventStringToEvent(finalSingleEventString);

        eventList.add(lastEvent);

        return eventList;
    }

    /**
     * Converts the String representation of a Single Event to an Event object.
     *
     * @param singleEventString String representing a single Event.
     * @return Event object.
     * @throws IllegalValueException when there is an error during parsing.
     */
    public static Event singleEventStringToEvent(String singleEventString) throws IllegalValueException {
        if (!isValidSingleEventString(singleEventString)) {
            throw new IllegalValueException("Missing fields in Tutorial's Event Json String");
        }

        singleEventString = singleEventString.replace("{", "").replace("}", "");

        String eventName = extractField(JsonAdaptedModule.EVENT_NAME,
                JsonAdaptedModule.EVENT_START_DATE, singleEventString);
        String eventStartDateString = extractField(JsonAdaptedModule.EVENT_START_DATE,
                JsonAdaptedModule.EVENT_END_DATE, singleEventString);
        String eventEndDateString = extractLastField(JsonAdaptedModule.EVENT_END_DATE, singleEventString);

        try {
            // Parse the event-strings to an Event object
            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date eventStartDate = dateFormatter.parse(eventStartDateString);
            Date eventEndDate = dateFormatter.parse(eventEndDateString);

            Event eventFromJson = new Event(eventName, eventStartDate, eventEndDate);
            return eventFromJson;

        } catch (java.text.ParseException e) {
            throw new IllegalValueException("Illegal value in assignment date: " + eventStartDateString
                    + " or " + eventEndDateString);
        }
    }

    public static int getTotalNumberOfEvents(String tutorialEventString) {
        int numOfEvents = 0;

        int index = 0;
        while (index != -1) {
            index = tutorialEventString.indexOf(JsonAdaptedModule.EVENT_NUMBER, index);
            if (index != -1) {
                index++;
                numOfEvents++;
            }
        }
        return numOfEvents;
    }


    //========================== Parsing assignment-Json-String to Assignment object ===================================


    /**
     * Converts a tutorial Assignment String (with embedded students and their scores) to a Map of Assignments to
     * Students & their respective scores.
     *
     * @param tutorialAssignmentString String from json file.
     * @return Map of Assignments to Students with scores.
     * @throws IllegalValueException throws if there is any parsing errors.
     */
    public static Map<Assignment, Map<Student, Integer>> tutorialAssignmentStringToAssignment(
            String tutorialAssignmentString) throws IllegalValueException {
        Map<Assignment, Map<Student, Integer>> assignmentMap = new TreeMap<>();
        int numOfAssignmentsToParse = getTotalNumberOfAssignments(tutorialAssignmentString);

        if (numOfAssignmentsToParse == 0) {
            //return null;
            // Since according to Tutorial constructor, null is used for the default Tutorial.
            return assignmentMap;
        }

        for (int i = 1; i < numOfAssignmentsToParse; i++) {

            String identifier = JsonAdaptedModule.ASSIGNMENT_NUMBER + i;
            String nextIdentifier = JsonAdaptedModule.ASSIGNMENT_NUMBER + (i + 1);

            String singleAssignmentString = extractField(identifier, nextIdentifier, tutorialAssignmentString);
            Assignment assignmentFromString = assignmentStringToAssignment(singleAssignmentString);

            // Create map of Students to their respective scores
            String studentAssignmentString = extractLastField(JsonAdaptedModule.ASSIGNMENT_STUDENT_LIST,
                    singleAssignmentString);
            Map<Student, Integer> studentIntegerMap = studentAssignmentStringToMap(studentAssignmentString);

            assignmentMap.put(assignmentFromString, studentIntegerMap);
        }

        // Parse the final assignment string
        String finalIdentifier = JsonAdaptedModule.ASSIGNMENT_NUMBER + numOfAssignmentsToParse;
        String finalAssignmentString = extractLastField(finalIdentifier, tutorialAssignmentString);
        Assignment assignmentFromString = assignmentStringToAssignment(finalAssignmentString);

        // Create map of Students to their respective scores
        String studentAssignmentString = extractLastField(JsonAdaptedModule.ASSIGNMENT_STUDENT_LIST,
                finalAssignmentString);
        Map <Student, Integer> studentIntegerMap = studentAssignmentStringToMap(studentAssignmentString);

        assignmentMap.put(assignmentFromString, studentIntegerMap);

        return assignmentMap;
    }

    /**
     * Returns a Map of Students to their respective scores from a Json String.
     *
     * @param studentAssignmentString Json String.
     * @return Map of Students to their scores.
     * @throws IllegalValueException thrown when parsing error.
     */
    public static Map<Student, Integer> studentAssignmentStringToMap (String studentAssignmentString)
            throws IllegalValueException {

        HashMap<Student, Integer> studentIntegerTreeMap = new HashMap<Student, Integer>();

        if (studentAssignmentString.equals("[]")) {
            // An assignment with no students inside
            return studentIntegerTreeMap;
        }

        String[] studentsString = studentAssignmentString.split("\\]\\,\\[");;
        for (String s : studentsString) {

            if (!isValidStudentWithAssignmentString(s)) {
                throw new IllegalValueException("Student assignment String has invalid fields");
            }

            String studentName = extractField(JsonAdaptedModule.STUDENT_NAME,
                    JsonAdaptedModule.STUDENT_EMAIL, s);

            String studentEmail = extractField(JsonAdaptedModule.STUDENT_EMAIL,
                    JsonAdaptedModule.STUDENT_MATRIC_NUMBER, s);

            String studentMatricNumber = extractField(JsonAdaptedModule.STUDENT_MATRIC_NUMBER,
                    JsonAdaptedModule.STUDENT_NUSNET_ID, s);

            String studentNusnetId = extractField(JsonAdaptedModule.STUDENT_NUSNET_ID,
                    JsonAdaptedModule.STUDENT_MODULE_CODE, s);

            String studentModuleCode = extractField(JsonAdaptedModule.STUDENT_MODULE_CODE,
                    JsonAdaptedModule.STUDENT_TUTORIAL_NAME, s);

            String studentTutorialName = extractField(JsonAdaptedModule.STUDENT_TUTORIAL_NAME,
                    JsonAdaptedModule.STUDENT_ASSIGNMENT_SCORE, s);

            String studentAssignmentScore = extractLastField(JsonAdaptedModule.STUDENT_ASSIGNMENT_SCORE, s);

            Student studentFromString = studentStringsToStudent(studentName, studentEmail, studentMatricNumber,
                    studentNusnetId, studentModuleCode, studentTutorialName);

            try {
                Integer studentScore = Integer.parseInt(studentAssignmentScore.replace("[", "").replace("]", ""));
                studentIntegerTreeMap.put(studentFromString, studentScore);

            } catch (NumberFormatException e) {
                throw new IllegalValueException("Invalid student assignment score in json: " + studentAssignmentScore);
            }
        }

        return studentIntegerTreeMap;
    }

    /**
     * Converts an assignment String to an Assignment object.
     *
     * @param assignmentString String from json.
     * @return Assignment object.
     * @throws IllegalValueException thrown when there is an error in parsing.
     */
    public static Assignment assignmentStringToAssignment(String assignmentString) throws IllegalValueException {
        // TODO: CHECK ORDER IS VALID
        String assignmentName = extractField(JsonAdaptedModule.ASSIGNMENT_NAME,
                JsonAdaptedModule.ASSIGNMENT_MAX_SCORE, assignmentString);

        String assignmentMaxScore = extractField(JsonAdaptedModule.ASSIGNMENT_MAX_SCORE,
                JsonAdaptedModule.ASSIGNMENT_START_DATE, assignmentString);

        String assignmentStartDate = extractField(JsonAdaptedModule.ASSIGNMENT_START_DATE,
                JsonAdaptedModule.ASSIGNMENT_END_DATE, assignmentString);

        String assignmentEndDate = extractField(JsonAdaptedModule.ASSIGNMENT_END_DATE,
                JsonAdaptedModule.ASSIGNMENT_STUDENT_LIST, assignmentString);


        // Parses the strings and return an Assignment object.
        try {
            Integer maxScore = Integer.parseInt(assignmentMaxScore);
            if (maxScore < 0) {
                throw new NumberFormatException();
            }
            SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
            Date startDate = dateFormatter.parse(assignmentStartDate);
            Date endDate = dateFormatter.parse(assignmentEndDate);
            Assignment assignmentFromString = new Assignment(assignmentName, maxScore, startDate, endDate);
            return assignmentFromString;
        } catch (java.text.ParseException e) {
            throw new IllegalValueException("Illegal value in assignment date: " + assignmentStartDate
                    + " or " + assignmentEndDate);
        } catch (NumberFormatException e) {
            throw new IllegalValueException("Illegal value in assignment max score: " + assignmentMaxScore);
        }
    }

    /**
     * Returns the total number of assignments present in the Json assignment string.
     *
     * @param tutorialAssignmentString Json string of assignments.
     * @return Number of assignments present.
     */
    public static int getTotalNumberOfAssignments(String tutorialAssignmentString) {
        int numOfAssignments = 0;

        int index = 0;
        while (index != -1) {
            index = tutorialAssignmentString.indexOf(JsonAdaptedModule.ASSIGNMENT_NUMBER, index);
            if (index != -1) {
                index++;
                numOfAssignments++;
            }
        }
        return numOfAssignments;
    }

    /**
     * Converts a tutorialString to a LinkedHashMap. Represents the components needed to construct a Tutorial object.
     *
     * @param tutorialString String.
     * @return LinkedHashMap.
     */
    public static LinkedHashMap<String, String> tutorialStringToMap(String tutorialString)
            throws IllegalValueException {
        if (!isValidTutorialString(tutorialString)) {
            throw new IllegalValueException("Tutorial string has invalid order or absence of fields");
        }

        LinkedHashMap<String, String> tutorialStringToMap = new LinkedHashMap<String, String>();
        // Extracts the correct Strings needed to populate the LinkedHashMap.
        // Relevant terms to extract are tutorialName, tutorialDayOfWeek, studentListString, tutorialModuleCode,
        // tutorialStartTime, tutorialDuration, tutorialWeeks.
        String tutorialNameFromTutorialString = extractField(JsonAdaptedModule.TUTORIAL_NAME,
                JsonAdaptedModule.TUTORIAL_DAY, tutorialString);
        String tutorialDayOfWeek = extractField(JsonAdaptedModule.TUTORIAL_DAY,
                JsonAdaptedModule.TUTORIAL_START_TIME, tutorialString);
        String tutorialStartTime = extractField(JsonAdaptedModule.TUTORIAL_START_TIME,
                JsonAdaptedModule.TUTORIAL_WEEKS, tutorialString);
        String tutorialWeeks = extractField(JsonAdaptedModule.TUTORIAL_WEEKS,
                JsonAdaptedModule.TUTORIAL_DURATION, tutorialString);
        String tutorialDuration = extractField(JsonAdaptedModule.TUTORIAL_DURATION,
                JsonAdaptedModule.TUTORIAL_STUDENT_LIST, tutorialString);
        String tutorialStudentList = extractField(JsonAdaptedModule.TUTORIAL_STUDENT_LIST,
                JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST, tutorialString);
        String tutorialAttendanceList = extractField(JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST,
                JsonAdaptedModule.TUTORIAL_MODULE_CODE, tutorialString);
        String tutorialModuleCode = extractLastField(JsonAdaptedModule.TUTORIAL_MODULE_CODE, tutorialString);

        // Places the extracted Strings into a HashMap
        tutorialStringToMap.put(JsonAdaptedModule.TUTORIAL_NAME, tutorialNameFromTutorialString);
        tutorialStringToMap.put(JsonAdaptedModule.TUTORIAL_DAY, tutorialDayOfWeek);
        tutorialStringToMap.put(JsonAdaptedModule.TUTORIAL_START_TIME, tutorialStartTime);
        tutorialStringToMap.put(JsonAdaptedModule.TUTORIAL_WEEKS, tutorialWeeks);
        tutorialStringToMap.put(JsonAdaptedModule.TUTORIAL_DURATION, tutorialDuration);
        tutorialStringToMap.put(JsonAdaptedModule.TUTORIAL_STUDENT_LIST, tutorialStudentList);
        tutorialStringToMap.put(JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST, tutorialAttendanceList);
        tutorialStringToMap.put(JsonAdaptedModule.TUTORIAL_MODULE_CODE, tutorialModuleCode);

        return tutorialStringToMap;
    }


    //==================== Parses a tutorial Map to a Tutorial object ==================================================


    /**
     * Invoked during reading of Json String.
     *
     * Returns a Tutorial Object given the TutorialMap constructed from Json.
     *
     * @param tutorialMap LinkedHashMap obtained after parsing Tutorial String.
     * @return Tutorial object.
     * @throws IllegalValueException when Tutorial components are unable to be parsed correctly from Strings.
     */
    public static Tutorial tutorialMapToTutorial(LinkedHashMap<String, String> tutorialMap)
            throws IllegalValueException {

        //TODO: CHECK ALL FIELDS ARE PRESENT
        try {
            List<Student> studentList = studentStringToList(tutorialMap.get(JsonAdaptedModule.TUTORIAL_STUDENT_LIST));
            TutName tutorialName = ParserUtil.parseTutorialName(tutorialMap.get(JsonAdaptedModule.TUTORIAL_NAME));
            DayOfWeek day = ParserUtil.parseDayOfWeek(tutorialMap.get(JsonAdaptedModule.TUTORIAL_DAY));
            Set<Week> weeks = ParserUtil.parseWeeks(tutorialMap.get(JsonAdaptedModule.TUTORIAL_WEEKS));
            ModCode modCode = ParserUtil.parseModCode(tutorialMap.get(JsonAdaptedModule.TUTORIAL_MODULE_CODE));
            Duration duration = Duration.parse(tutorialMap.get(JsonAdaptedModule.TUTORIAL_DURATION));
            LocalTime startTime = LocalTime.parse(tutorialMap.get(JsonAdaptedModule.TUTORIAL_START_TIME),
                    DateTimeFormatter.ISO_TIME);
            Attendance attendance = attendanceStringToAttendance(tutorialMap
                    .get(JsonAdaptedModule.TUTORIAL_ATTENDANCE_LIST), weeks);

            // Constructing Assignment(s) from String
            Map<Assignment, Map<Student, Integer>> listOfAssignments =
                    tutorialAssignmentStringToAssignment(tutorialMap.get(JsonAdaptedModule.TUTORIAL_ASSIGNMENT_LIST));

            // Constructing Event(s) from String
            String eventString = tutorialMap.get(JsonAdaptedModule.TUTORIAL_EVENT_LIST);
            List<Event> listOfEvents = tutorialEventStringToEventList(eventString);

            // With assignment and event list construction
            Tutorial t = new Tutorial(tutorialName, day, startTime, weeks, duration, studentList,
                   modCode, attendance, listOfAssignments);
            t.setEventList(listOfEvents);

            return t;

        } catch (ParseException | IllegalArgumentException e) {
            throw new IllegalValueException(JsonAdaptedModule.MISSING_GENERIC_FIELD + e.getMessage());
        } catch (DateTimeParseException e) {
            // Thrown by either Duration or LocalTime objects.
            String errorMessage = String.format(JsonAdaptedModule.INVALID_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName())
                    + " Or " + String.format(JsonAdaptedModule.INVALID_FIELD_MESSAGE_FORMAT,
                    LocalTime.class.getSimpleName());
            throw new IllegalValueException(errorMessage);
        }
    }

}
