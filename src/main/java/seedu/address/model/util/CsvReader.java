package seedu.address.model.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.DefaultValues;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailType;
import seedu.address.model.person.Emails;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Slot;

/**
 * Helper class to read .csv files (Comma separated values).
 */
public class CsvReader {
    private String filePath;
    private Model model;

    /**
     * Constructor for CsvReader object to read from excel.
     * @param filePath Path of csv file
     */
    public CsvReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads from .csv file and returns a list of Interviewer objects.
     * @return ArrayList of Interviewers
     * @throws IOException if input file is not found or file is in incorrect format.
     */
    public ArrayList<Interviewer> readInterviewers() throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        String firstLine = csvReader.readLine();
        int numberOfDays = getValue(firstLine.split(",")[0]);
        int numberOfColumns = getValue(firstLine.split(",")[1]) + 1;
        if (numberOfColumns < 1) {
            ArrayList<Interviewer> emptyList = new ArrayList<>();
            return emptyList;
        }

        ArrayList<Interviewer> interviewers = new ArrayList<>();
        csvReader.readLine(); //removes next line

        for (int i = 0; i < numberOfDays; i++) {
            String date = null;
            String row;
            boolean firstEncounter = true;
            boolean firstRow = true;
            while ((row = csvReader.readLine()) != null) {
                String[] rowData = row.split(",", -1);
                if (rowData[0].equals("")) {
                    if (firstEncounter) {
                        firstEncounter = false;
                    } else {
                        break;
                    }
                } else if (firstRow) {
                    date = rowData[0];
                    if (i == 0) { //if this is the first table(day) being read
                        interviewers = getInterviewersFromHeader(rowData);
                    }
                    firstRow = false;
                } else {
                    updateInterviewersSlotsFromData(interviewers, rowData, date);
                }
            }
        }
        csvReader.close();
        return interviewers;
    }

    /**
     * Reads from .csv file and returns a list of Interviewee objects.
     * @return ArrayList of Interviewees
     * @throws IOException if input file is not found or file is in incorrect format.
     */
    public ArrayList<Interviewee> readInterviewees() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        ArrayList<Interviewee> interviewees = new ArrayList<>();
        csvReader.readLine(); //discard first line
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] rowData = row.split(",");
            Name name = new Name(rowData[0]);
            HashMap<EmailType, List<Email>> emails = new HashMap<>();
            ArrayList<Email> nusEmails = new ArrayList<>();
            ArrayList<Email> personalEmails = new ArrayList<>();
            nusEmails.add(new Email(rowData[1]));
            emails.put(EmailType.NUS, nusEmails);
            personalEmails.add(new Email(rowData[2]));
            emails.put(EmailType.PERSONAL, personalEmails);
            Emails allEmails = new Emails(emails);
            Phone phone = new Phone(rowData[3]);
            Faculty faculty = new Faculty(rowData[4]);
            Integer yearOfStudy = Integer.valueOf(rowData[5]);
            ArrayList<Department> choiceOfDepartments = new ArrayList<>();
            choiceOfDepartments.add(new Department(rowData[6]));
            List<Slot> availableTimeSlots = new ArrayList<>();
            for (int i = 7; i < rowData.length; i++) {
                String trimmedData = rowData[i].trim().replaceAll("\"", "");
                if (!trimmedData.equals("")) {
                    Slot slot = Slot.fromString(trimmedData);
                    availableTimeSlots.add(slot);
                }
            }
            Interviewee.IntervieweeBuilder builder =
                    new Interviewee.IntervieweeBuilder(name, phone, DefaultValues.DEFAULT_TAGS);
            builder.availableTimeslots(availableTimeSlots);
            builder.departmentChoices(choiceOfDepartments);
            builder.emails(allEmails);
            builder.yearOfStudy(yearOfStudy);
            builder.faculty(faculty);

            interviewees.add(builder.build());
        }
        return interviewees;

    }

    /**
     * Static method to get the integer value from a String in the format ("key = value").
     * @param element String in the format ("key = value")
     * @return int value
     */
    private static int getValue(String element) {
        String[] strings = element.split("=");
        String trimmedString = strings[1].trim();
        return Integer.parseInt(trimmedString);
    }

    /**
     * Checks if file exists.
     * @return boolean true if file exists, false otherwise
     */
    private boolean fileExists() {
        File file = new File(this.filePath);
        return file.exists();
    }

    /**
     * Gets the interviewer's name from a String.
     * @param cell String that contains interviewer's name
     * @return Interviewer's name
     */
    private static Name getInterviewerName(String cell) {
        String[] strings = cell.split("-");
        return new Name(strings[1].trim());
    }

    /**
     * Gets the interviewer's department from a String.
     * @param cell String that contains interviewer's department
     * @return Interviewer's department
     */
    private static Department getInterviewerDepartment(String cell) {
        String[] strings = cell.split("-");
        return new Department(strings[0].trim());
    }

    /**
     * Gets the Start time from a String in the format "hh:mm - hh:mm".
     * @param timeRange String that contains interviewer's department
     * @return String start time
     */
    private static String getStartTime(String timeRange) {
        String[] strings = timeRange.split("-");
        return strings[0].trim();
    }

    /**
     * Gets the End time from a String in the format "hh:mm - hh:mm".
     * @param timeRange String that contains interviewer's department
     * @return String End time
     */
    private static String getEndTime(String timeRange) {
        String[] strings = timeRange.split("-");
        return strings[1].trim();
    }

    /**
     * Generates and returns a list of interviewers with empty availabilities from a header row.
     * @param rowData Header row
     * @return ArrayList of interviewer objects
     */
    private static ArrayList<Interviewer> getInterviewersFromHeader(String[] rowData) {
        ArrayList<Interviewer> interviewers = new ArrayList<>();
        for (int j = 1; j < rowData.length; j++) {
            Name interviewerName = getInterviewerName(rowData[j]);
            Department interviewerDepartment = getInterviewerDepartment(rowData[j]);
            Interviewer.InterviewerBuilder interviewerBuilder =
                    new Interviewer.InterviewerBuilder(interviewerName,
                            DefaultValues.DEFAULT_PHONE, DefaultValues.DEFAULT_TAGS);
            interviewerBuilder.department(interviewerDepartment);
            interviewerBuilder.availabilities(new ArrayList<>());
            interviewers.add(interviewerBuilder.build());
        }
        return interviewers;
    }

    /**
     * Updates availabilities of interviewers.
     * @param interviewers List of interviewer objects
     * @param rowData Raw data of availabilities
     * @param date Date of availability
     */
    private static void updateInterviewersSlotsFromData(ArrayList<Interviewer> interviewers,
                                                                String[] rowData, String date) {
        String timing = rowData[0];
        String startTime = getStartTime(timing);
        String endTime = getEndTime(timing);
        Slot slot = new Slot(date, startTime, endTime);
        for (int j = 1; j < rowData.length; j++) {
            if (rowData[j].trim().equals("1")) {
                Interviewer currentInterviewer = interviewers.get(j - 1);
                List<Slot> currentSlots = currentInterviewer.getAvailabilities();
                currentSlots.add(slot);
                List<Slot> updatedSlots = cloneSlots(currentSlots);
                currentInterviewer.setAvailabilities(updatedSlots);
            }
        }
    }

    /**
     * Returns the deep copy of the list of slots given.
     * @param list the list of slots to be copied.
     * @return the deep copy of the list of slots given.
     */
    private static List<Slot> cloneSlots(List<Slot> list) {
        List<Slot> listClone = new ArrayList<>();
        for (Slot slot : list) {
            listClone.add(slot);
        }
        return listClone;
    }
}
