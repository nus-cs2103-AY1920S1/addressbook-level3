package seedu.scheduler.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_PERSONAL_EMAIL_BOB;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_BOB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.person.DefaultValues;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.Emails;
import seedu.scheduler.model.person.Faculty;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Slot;


public class CsvReaderTest {
    private static final String VALID_INTERVIEWERLIST_FILEPATH = "src/test/data/ImportsTest/validInterviewerList.csv";
    private static final String VALID_INTERVIEWEELIST_FILEPATH = "src/test/data/ImportsTest/validIntervieweeList.csv";
    private static final String INVALID_INTERVIEWEELIST_FILEPATH =
            "src/test/data/ImportsTest/invalidIntervieweeList.csv";
    private static final String INVALID_INTERVIEWERLIST_FILEPATH =
            "src/test/data/ImportsTest/invalidInterviewerList.csv";
    private static final String SAMPLE_CORRECT_HEADER = "11/10/2019,Department A - Person A,Department B - Person B,"
            + "Department C - Person C,Department D - Person D,Department E - Person E,Department F - Person F";
    private static final String[] ALPHABETS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private static final String ROW_OF_INTERVIEWEE_DATA = "John Doe,john@u.nus.edu,john@hotmail.com,99999999,NUS,1,"
            + "publicity,\"09/10/2019 18:30-19:00, 10/10/2019 19:00-19:30, 11/10/2019 20:00-20:30\"";

    @Test
    public void constructor_invalidFilePath_throwException() {
        assertThrows(IOException.class, () -> new CsvReader("missing/file/path.csv").readInterviewees());
        assertThrows(NullPointerException.class, () -> new CsvReader(null).readInterviewees());
    }

    // ===================================== Interviewers ==============================================
    @Test
    public void getInterviewersFromHeader_success() {
        ArrayList<Interviewer> interviewersFromCorrectSample =
                CsvReader.getInterviewersFromHeader(SAMPLE_CORRECT_HEADER.split(","));
        ArrayList<Interviewer> expectedInterviewers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Name interviewerName = new Name("Person " + ALPHABETS[i]);
            Department interviewerDepartment = new Department("Department " + ALPHABETS[i]);
            Interviewer.InterviewerBuilder interviewerBuilder =
                    new Interviewer.InterviewerBuilder(interviewerName,
                            DefaultValues.DEFAULT_PHONE, DefaultValues.DEFAULT_TAGS);
            interviewerBuilder.department(interviewerDepartment);
            interviewerBuilder.availabilities(new ArrayList<>());
            expectedInterviewers.add(interviewerBuilder.build());
        }
        assertEquals(expectedInterviewers, interviewersFromCorrectSample);
    }

    @Test
    public void readInterviewers_validInterviewerList_success() throws IOException {
        CsvReader reader = new CsvReader(VALID_INTERVIEWERLIST_FILEPATH);
        ArrayList<Interviewer> testOutput = reader.readInterviewers();

        ArrayList<Interviewer> expectedInterviewers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Name interviewerName = new Name("Person " + ALPHABETS[i]);
            Department interviewerDepartment = new Department("Department " + ALPHABETS[i]);
            Interviewer.InterviewerBuilder interviewerBuilder =
                    new Interviewer.InterviewerBuilder(interviewerName,
                            DefaultValues.DEFAULT_PHONE, DefaultValues.DEFAULT_TAGS);
            interviewerBuilder.department(interviewerDepartment);
            if (i == 0) {
                ArrayList<Slot> slots = new ArrayList<>();
                slots.add(Slot.fromString("10/10/2019 18:00-18:30"));
                interviewerBuilder.availabilities(slots);
            } else {
                interviewerBuilder.availabilities(new ArrayList<>());
            }
            expectedInterviewers.add(interviewerBuilder.build());
        }
        assertEquals(expectedInterviewers, testOutput);
    }

    @Test
    public void readInterviewers_invalidInterviewerList_throwsArrayIndexOutOfBoundsException() {
        CsvReader reader = new CsvReader(INVALID_INTERVIEWERLIST_FILEPATH);
        assertThrows(ArrayIndexOutOfBoundsException.class, reader::readInterviewers);
    }

    // ===================================== Interviewees ==============================================

    @Test
    public void readInterviewees_validIntervieweeList_success() throws IOException {
        CsvReader reader = new CsvReader(VALID_INTERVIEWEELIST_FILEPATH);
        ArrayList<Interviewee> testOutput = reader.readInterviewees();
        ArrayList<Interviewee> expectedInterviewees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Name name = new Name("John " + ALPHABETS[i]);
            Emails emails = new Emails(new HashMap<>());
            emails.addNusEmail(new Email("john" + ALPHABETS[i] + "@u.nus.edu"));
            emails.addPersonalEmail(new Email("john" + ALPHABETS[i] + "@hotmail.com"));
            Phone phone = new Phone("9999999" + i);
            Faculty faculty = new Faculty("NUS");
            Integer yearOfStudy = 1;
            ArrayList<Department> choiceOfDepartments = new ArrayList<>();
            choiceOfDepartments.add(new Department("publicity"));

            List<Slot> availableTimeSlots = new ArrayList<>();
            availableTimeSlots.add(Slot.fromString("09/10/2019 18:30-19:00"));
            availableTimeSlots.add(Slot.fromString("10/10/2019 19:00-19:30"));
            availableTimeSlots.add(Slot.fromString("11/10/2019 20:00-20:30"));

            Interviewee interviewee = new Interviewee.IntervieweeBuilder(name, phone, DefaultValues.DEFAULT_TAGS)
                    .availableTimeslots(availableTimeSlots)
                    .departmentChoices(choiceOfDepartments)
                    .emails(emails)
                    .yearOfStudy(yearOfStudy)
                    .faculty(faculty)
                    .build();
            expectedInterviewees.add(interviewee);
        }
        assertEquals(expectedInterviewees, testOutput);
    }

    @Test
    public void readInterviewees_invalidIntervieweeList_throwsArrayIndexOutOfBoundsException() {
        CsvReader reader = new CsvReader(INVALID_INTERVIEWEELIST_FILEPATH);
        assertThrows(ArrayIndexOutOfBoundsException.class, reader::readInterviewees);
    }

    // ===================================== Unit Tests ======================================================
    @Test
    public void getValue() {
        // Valid inputs
        assertEquals(3, CsvReader.getValue("value = 3"));
        assertEquals(2, CsvReader.getValue("= 2"));
        assertEquals(4, CsvReader.getValue("    =    4    "));
        assertEquals(-1, CsvReader.getValue("value = -1"));
        assertEquals(Integer.MAX_VALUE, CsvReader.getValue("value = 2147483647"));

        // Missing equals sign
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.getValue("")); // Blank string
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.getValue("    ")); // Spaces
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.getValue("value is 1")); // No equals

        // Improperly formatted inputs
        assertThrows(NumberFormatException.class, () -> CsvReader.getValue("value = 2147483648")); // Too large
        assertThrows(NumberFormatException.class, () -> CsvReader.getValue("key = value")); // Non-integer
        assertThrows(NumberFormatException.class, () -> CsvReader.getValue("key = 3.14")); // Floating point

        // Null input
        assertThrows(NullPointerException.class, () -> CsvReader.getValue(null));
    }

    @Test
    public void getInterviewerName() {
        // Valid inputs
        assertEquals(new Name(VALID_NAME_AMY), CsvReader.getInterviewerName("Test - " + VALID_NAME_AMY));
        assertEquals(new Name(VALID_NAME_BOB), CsvReader.getInterviewerName("-" + VALID_NAME_BOB));

        // Missing negative sign
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.getInterviewerName("")); // Blank string
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.getInterviewerName("    ")); // Spaces
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.getInterviewerName("no name")); // No "-"

        // Null input
        assertThrows(NullPointerException.class, () -> CsvReader.getInterviewerName(null));
    }

    @Test
    public void getInterviewerDepartment() {
        // Valid inputs
        assertEquals(new Department(VALID_DEPARTMENT_AMY),
                CsvReader.getInterviewerDepartment(VALID_DEPARTMENT_AMY + " - Test"));
        assertEquals(new Department(VALID_DEPARTMENT_BOB),
                CsvReader.getInterviewerDepartment(VALID_DEPARTMENT_BOB + "-"));

        // Null input
        assertThrows(NullPointerException.class, () -> CsvReader.getInterviewerDepartment(null));
    }

    @Test
    public void getStartTime() {
        // Valid inputs
        assertEquals("12:30", CsvReader.getStartTime(VALID_SLOT_AMY.split(" ")[1]));
        assertEquals("13:30", CsvReader.getStartTime(VALID_SLOT_BOB.split(" ")[1]));

        // The following are invalid times but can be parsed here (will be handled downstream)
        assertEquals("", CsvReader.getStartTime("")); // Empty string
        assertEquals("", CsvReader.getStartTime("    ")); // Spaces
        assertEquals("no slots", CsvReader.getStartTime("no slots")); // No "-"

        // Null input
        assertThrows(NullPointerException.class, () -> CsvReader.getStartTime(null));
    }

    @Test
    public void getEndTime() {
        // Valid inputs
        assertEquals("13:30", CsvReader.getEndTime(VALID_SLOT_AMY.split(" ")[1]));
        assertEquals("14:30", CsvReader.getEndTime(VALID_SLOT_BOB.split(" ")[1]));

        // Missing negative sign
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.getEndTime("")); // Blank string
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.getEndTime("    ")); // Spaces
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> CsvReader.getEndTime("no slots")); // No "-"

        // Null input
        assertThrows(NullPointerException.class, () -> CsvReader.getEndTime(null));
    }

    @Test
    public void getAllSlots() {
        // Valid inputs
        String[] row = new String[8];
        row[7] = "";
        assertEquals(new LinkedList<Slot>(), CsvReader.getAllSlots(row));

        row[7] = VALID_SLOT_AMY;
        assertEquals(Collections.singletonList(Slot.fromString(VALID_SLOT_AMY)), CsvReader.getAllSlots(row));

        row = new String[9];
        row[7] = VALID_SLOT_AMY;
        row[8] = VALID_SLOT_BOB;
        assertEquals(Arrays.asList(Slot.fromString(VALID_SLOT_AMY), Slot.fromString(VALID_SLOT_BOB)),
                CsvReader.getAllSlots(row));

        // Invalid inputs
        row[7] = "invalid";
        row[8] = "";
        String[] finalRow = row;
        assertThrows(IllegalArgumentException.class, () -> CsvReader.getAllSlots(finalRow));

        row[7] = null;
        row[8] = null;
        String[] finalRow1 = row;
        assertThrows(NullPointerException.class, () -> CsvReader.getAllSlots(finalRow1));

        // Valid simulated data
        List<Slot> testOutput = CsvReader.getAllSlots(ROW_OF_INTERVIEWEE_DATA.split(","));
        List<Slot> expectedSlots = new ArrayList<>();
        expectedSlots.add(Slot.fromString("09/10/2019 18:30-19:00"));
        expectedSlots.add(Slot.fromString("10/10/2019 19:00-19:30"));
        expectedSlots.add(Slot.fromString("11/10/2019 20:00-20:30"));
        assertEquals(expectedSlots, testOutput);
    }

    @Test
    public void getAllEmails() {
        // Valid inputs
        assertEquals(Collections.singletonList(new Email(VALID_PERSONAL_EMAIL_AMY)),
                CsvReader.getAllEmails(VALID_PERSONAL_EMAIL_AMY));
        assertEquals(Arrays.asList(new Email(VALID_PERSONAL_EMAIL_AMY), new Email(VALID_PERSONAL_EMAIL_BOB)),
                CsvReader.getAllEmails(VALID_PERSONAL_EMAIL_AMY + " " + VALID_PERSONAL_EMAIL_BOB));

        // Invalid inputs
        assertThrows(IllegalArgumentException.class, () -> CsvReader.getAllEmails(""));
        assertThrows(NullPointerException.class, () -> CsvReader.getAllEmails(null));
    }
}
