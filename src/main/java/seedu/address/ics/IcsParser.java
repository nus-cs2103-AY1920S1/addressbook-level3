package seedu.address.ics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DateTime;
import seedu.address.model.events.EventSource;
import seedu.address.model.events.EventSourceBuilder;
import seedu.address.model.tasks.TaskSource;
import seedu.address.model.tasks.TaskSourceBuilder;

//@@author marcusteh1238
/***
 * Parses an ICS file to allow importing into Horo.
 */
public class IcsParser {

    private static final String FILE_DOES_NOT_EXIST = "Sorry, the file path you've specified is invalid!";
    private static final String FILE_CANNOT_BE_READ = "Sorry, the file specified cannot be read!";
    private static final String FILE_CANNOT_BE_FOUND = "Sorry, the file specified cannot be found!";
    private static final String INVALID_FILE_EXTENSION = "The file specified is not an ICS file!";
    private static final String FILE_IS_CORRUPTED = "The ICS file is corrupted!";
    private static final String TIMESTAMP_IS_INVALID = "The timestamp provided is invalid!";

    private static final Logger logger = LogsCenter.getLogger(IcsParser.class);

    private File icsFile;

    private IcsParser(String path) throws IcsException {
        this.icsFile = getIcsFile(path);
    }

    public static IcsParser getParser(String path) throws IcsException {
        return new IcsParser(path);
    }

    private File getIcsFile(String path) throws IcsException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IcsException(FILE_DOES_NOT_EXIST);
        } else if (!file.canRead()) {
            throw new IcsException(FILE_CANNOT_BE_READ);
        } else if (!file.toString().endsWith(".ics")) {
            throw new IcsException(INVALID_FILE_EXTENSION);
        }
        return file;
    }

    //@@author marcusteh1238
    /**
     * Parses the file provided in the file path and returns an ArrayList of EventSources.
     * @return An ArrayList of EventSources from the file.
     * @throws IcsException Thrown if the file cannot be found or read,
     * is not a proper Ics file, or if a description for an event in the file is empty.
     */
    public EventSource[] parseEvents() throws IcsException {
        logger.info("Parsing Events");
        String fileContent = getFileContent();
        return getEventsFromFile(fileContent);
    }

    //@@author marcusteh1238
    /**
     * Parses the file provided in the file path and returns an ArrayList of TaskSources.
     * @return An ArrayList of TaskSources from the file.
     * @throws IcsException Thrown if the file cannot be found or read,
     * is not a proper Ics file, or if a description for an event in the file is empty.
     */
    public TaskSource[] parseTasks() throws IcsException {
        logger.info("Parsing Tasks");
        String fileContent = getFileContent();
        return getTasksFromFile(fileContent);
    }

    //@@author marcusteh1238
    /**
     * Obtains the file content of the ics file specified in the filepath.
     * @return A single String of the whole file.
     * @throws IcsException Thrown if the file cannot be found or read.
     */
    private String getFileContent() throws IcsException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(icsFile));
            StringBuilder sb = new StringBuilder("");
            boolean first = true;
            while (br.ready()) {
                String line = br.readLine();
                if (first) {
                    sb.append(line);
                    first = false;
                } else {
                    sb.append("\n").append(line);
                }
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            throw new IcsException(FILE_CANNOT_BE_FOUND);
        } catch (IOException e) {
            throw new IcsException(FILE_CANNOT_BE_READ);
        }
    }

    //@@author marcusteh1238
    /**
     * Parses the Ics file for its Events only and returns an array of EventSource objects.
     * @param fileContent The contents of the Ics file.
     * @return An ArrayList of EventSources provided by the Ics file.
     * @throws IcsException If the file is not a proper Ics file, or if a description for an event is empty.
     */
    public EventSource[] getEventsFromFile(String fileContent) throws IcsException {
        ArrayList<EventSource> events = new ArrayList<>();
        Pattern pattern = Pattern.compile("BEGIN:VEVENT(.*?)END:VEVENT", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fileContent);
        int count = matcher.groupCount();
        while (matcher.find()) {
            String icsString = matcher.group(1);
            EventSource newEvent = parseSingleEvent(icsString);
            events.add(newEvent);
        }
        return eventSourceArray(events);
    }

    //@@author marcusteh1238
    /**
     * Parses the Ics file for its Tasks only and returns an array of TaskSource objects.
     * @param fileContent The contents of the Ics file.
     * @return An ArrayList of TaskSources provided by the Ics file.
     * @throws IcsException If the file is not a proper Ics file, or if a description for a task is empty.
     */
    public TaskSource[] getTasksFromFile(String fileContent) throws IcsException {
        ArrayList<TaskSource> tasks = new ArrayList<>();
        Pattern pattern = Pattern.compile("BEGIN:VTODO(.*?)END:VTODO", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fileContent);
        int count = matcher.groupCount();
        while (matcher.find()) {
            String icsString = matcher.group(1);
            TaskSource newTask = parseSingleTask(icsString);
            tasks.add(newTask);
        }
        return taskSourceArray(tasks);
    }

    //@@author marcusteh1238
    /**
     * Converts an ArrayList of EventSource objects into an array.
     * @param events the ArrayList of EventSource objects.
     * @return An array of EventSource objects.
     */
    private EventSource[] eventSourceArray(ArrayList<EventSource> events) {
        int size = events.size();
        EventSource[] array = new EventSource[size];
        for (int i = 0; i < size; i++) {
            array[i] = events.get(i);
        }
        return array;
    }

    //@@author marcusteh1238
    /**
     * Converts an ArrayList of TaskSource objects into an array.
     * @param tasks the ArrayList of EventSource objects.
     * @return An array of TaskSource objects.
     */
    private TaskSource[] taskSourceArray(ArrayList<TaskSource> tasks) {
        int size = tasks.size();
        TaskSource[] array = new TaskSource[size];
        for (int i = 0; i < size; i++) {
            array[i] = tasks.get(i);
        }
        return array;
    }

    //@@author marcusteh1238
    /**
     * Creates an EventSource object from the data provided in the ICS File.
     * Currently it will only parse the Start time and Description of the ICS VEvent.
     * @param segment A String that represents the Event object in the ICS File.
     * @return an EventSource object representing the data provided.
     * @throws IcsException Exception thrown when there was an issue while making the EventSource object.
     */
    public static EventSource parseSingleEvent(String segment) throws IcsException {
        String[] lines = segment.split("\\r?\\n");
        String description = "";
        DateTime eventStart = null;
        DateTime eventEnd = null;
        ArrayList<String> tags = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("SUMMARY:")) {
                description = line.replaceFirst("SUMMARY:", "");
                if (description.equals("")) {
                    description = "<empty>";
                }
            } else if (line.startsWith("DTSTART:")) {
                String timestamp = line.replaceFirst("DTSTART:", "");
                eventStart = timestamp.equals("")
                        ? DateTime.now()
                        : parseTimeStamp(timestamp);
            } else if (line.startsWith("DTEND:")) {
                String timestamp = line.replaceFirst("DTEND:", "");
                eventEnd = parseTimeStamp(timestamp);
            } else if (line.equals("END:VCALENDAR") && !line.equals(lines[lines.length - 1])) {
                throw new IcsException(FILE_IS_CORRUPTED);
            } else if (line.startsWith("DESCRIPTION:")) {
                tags.addAll(getTagsFromIcsDescription(line));
            }
        }
        if (description.equals("") || eventStart == null) {
            throw new IcsException(FILE_IS_CORRUPTED);
        }
        EventSourceBuilder builder = EventSource.newBuilder(description, eventStart);
        if (eventEnd != null) {
            builder.setEnd(eventEnd);
        }
        if (tags.size() != 0) {
            builder.setTags(tags);
        }
        return builder.build();
    }

    //@@author marcusteh1238
    /**
     * Creates an TaskSource object from the data provided in the ICS File.
     * @param segment A String that represents the Task object in the ICS File.
     * @return a TaskSource object representing the data provided.
     * @throws IcsException Exception thrown when there was an issue while making the TaskSource object.
     */
    public static TaskSource parseSingleTask(String segment) throws IcsException {
        String[] lines = segment.split("\\r?\\n");
        String description = "";
        DateTime taskStart = null;
        DateTime due = null;
        Duration duration = null;
        ArrayList<String> tags = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("SUMMARY:")) {
                description = line.replaceFirst("SUMMARY:", "");
                if (description.equals("")) {
                    description = "<empty>";
                }
            } else if (line.startsWith("DUE:")) {
                String timestamp = line.replaceFirst("DUE:", "");
                due = timestamp.equals("")
                        ? DateTime.now()
                        : parseTimeStamp(timestamp);
            } else if (line.startsWith("DURATION:")) {
                String durationString = line.replaceFirst("DURATION:", "");
                try {
                    duration = Duration.parse(durationString);
                } catch (DateTimeParseException e) {
                    throw new IcsException(FILE_IS_CORRUPTED);
                }
            } else if (line.startsWith("DTSTART:")) {
                String startString = line.replaceFirst("DURATION:", "");
                taskStart = startString.equals("")
                        ? DateTime.now()
                        : parseTimeStamp(startString);
            } else if (line.startsWith("DESCRIPTION:")) {
                tags.addAll(getTagsFromIcsDescription(line));
            }
        }

        if (description.equals("")) {
            throw new IcsException(FILE_IS_CORRUPTED);
        }

        TaskSourceBuilder builder = TaskSource.newBuilder(description);

        if (due == null && !(duration == null || taskStart == null)) {
            due = taskStart.plus(duration);
            builder.setDueDate(due);
        } else if (due != null) {
            builder.setDueDate(due);
        }

        if (tags.size() != 0) {
            builder.setTags(tags);
        }
        return builder.build();
    }

    //@@author marcusteh1238
    /**
     * Converts the timestamp from the format given in the ICS file to a DateTime object.
     * @param icsTimeStamp A timestamp in the default ICS file specification format.
     * @return A DateTime object representing the timestamp.
     * @throws IcsException Thrown when the timestamp provided is invalid.
     */
    public static DateTime parseTimeStamp(String icsTimeStamp) throws IcsException {
        try {
            return DateTime.fromIcsString(icsTimeStamp);
        } catch (ParseException e) {
            throw new IcsException(TIMESTAMP_IS_INVALID);
        }
    }

    //@@author marcusteh1238
    /**
     * Obtain a list of tags from the "DESCRIPTION" field of the ICS object.
     * @param icsDescription The Description of the ICS object.
     * @return An ArrayList of tags.
     */
    public static ArrayList<String> getTagsFromIcsDescription(String icsDescription) {
        Matcher matcher = Pattern.compile("\\[([^\\]]+)").matcher(icsDescription);
        ArrayList<String> tags = new ArrayList<>();
        int pos = -1;
        while (matcher.find(pos + 1)) {
            pos = matcher.start();
            tags.add(matcher.group(1));
        }
        return tags;
    }
}
