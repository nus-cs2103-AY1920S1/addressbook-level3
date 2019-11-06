package seedu.scheduler.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static final String INTERVIEWER_FILEPATH_SUCCESS = "src/test/data/ImportsTest/InterviewerTestData.csv";
    private static final String INTERVIEWEE_FILEPATH_SUCCESS = "src/test/data/ImportsTest/IntervieweeTestData.csv";
    private static final String SAMPLE_CORRECT_HEADER = "11/10/2019,Department A - Person A,Department B - Person B,"
            + "Department C - Person C,Department D - Person D,Department E - Person E,Department F - Person F";
    private static final String[] ALPHABETS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private static final String ROW_OF_INTERVIEWEE_DATA = "John Doe,john@u.nus.edu,john@hotmail.com,99999999,NUS,1,"
            + "publicity,\"09/10/2019 18:30-19:00, 10/10/2019 19:00-19:30, 11/10/2019 20:00-20:30\"";


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
    public void readInterviewers_success() throws IOException {
        CsvReader reader = new CsvReader(INTERVIEWER_FILEPATH_SUCCESS);
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

    // ===================================== Interviewees ==============================================

    @Test
    public void readInterviewee_success() throws IOException {
        CsvReader reader = new CsvReader(INTERVIEWEE_FILEPATH_SUCCESS);
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
    public void getAllSlots_success() {
        List<Slot> testOutput = CsvReader.getAllSlots(ROW_OF_INTERVIEWEE_DATA.split(","));
        List<Slot> expectedSlots = new ArrayList<>();
        expectedSlots.add(Slot.fromString("09/10/2019 18:30-19:00"));
        expectedSlots.add(Slot.fromString("10/10/2019 19:00-19:30"));
        expectedSlots.add(Slot.fromString("11/10/2019 20:00-20:30"));
        assertEquals(expectedSlots, testOutput);
    }
}
