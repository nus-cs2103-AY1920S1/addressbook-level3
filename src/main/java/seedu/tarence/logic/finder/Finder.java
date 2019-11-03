package seedu.tarence.logic.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Supports the autocorrect and autocomplete features of the application.
 * Provides methods for searching for objects similar to a user's input when an exact match is not found.
 * Uses fuzzy string matching to search for names with a high degree of similarity.
 * Also provides methods for searching for string values that complete a partial input.
 */
public class Finder {

    private Model model;

    // thresholds for similarity differ between parameters due to their varying expected lengths
    private int thresholdModCode = 80;
    private int thresholdTutName = 70;
    private int thresholdStudentName = 80;

    public Finder(Model model) {
        this.model = model;
    }

    /**
     * Searches for modules in the application with a {@code ModCode} similar to the given one.
     * @param modCode target module code to match against.
     * @return a {@code List} of similar {@code ModCode}s.
     */
    public List<ModCode> findSimilarModCodes (ModCode modCode) throws CommandException {
        List<ModCode> similarModCodes = new ArrayList<>();
        for (Module module : model.getFilteredModuleList()) {
            if (FuzzySearch.ratio(modCode.toString(), module.getModCode().toString()) > thresholdModCode) {
                similarModCodes.add(module.getModCode());
            }
        }
        return similarModCodes;
    }

    /**
     * Searches for tutorials in the application with a {@code TutName} similar to the given one.
     * @param tutName target tutorial name to match against.
     * @return a {@code List} of similar {@code TutName}s.
     */
    public List<TutName> findSimilarTutNames (TutName tutName) throws CommandException {
        List<TutName> similarTutNames = new ArrayList<>();
        for (Tutorial tutorial : model.getFilteredTutorialList()) {
            if (FuzzySearch.ratio(tutName.toString(), tutorial.getTutName().toString()) > thresholdTutName) {
                similarTutNames.add(tutorial.getTutName());
            }
        }
        return similarTutNames;
    }

    /**
     * Searches for students in the application with a {@code Name} similar to the given one.
     * @param name target name to match against.
     * @return a {@code List} of similar {@code Name}s.
     */
    public List<Name> findSimilarNames (Name name) throws CommandException {
        List<Name> similarNames = new ArrayList<>();
        for (Student student : model.getFilteredStudentList()) {
            if (FuzzySearch.ratio(name.toString(), student.getName().toString()) > thresholdStudentName) {
                similarNames.add(student.getName());
            }
        }
        return similarNames;
    }

    /**
     * Searches for emails in the application beginning with the current partial input.
     */
    public List<String> autocompleteEmail (String partialEmail) {
        return model.getFilteredStudentList().stream()
                .map(student -> student.getEmail().value)
                .filter(studentEmail -> studentEmail.toLowerCase().startsWith(partialEmail.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Searches for student names in the application beginning with the current partial input.
     */
    public List<String> autocompleteMatNo (String partialMatNo) {
        return model.getFilteredStudentList().stream()
                .filter(student -> student.getMatricNum().isPresent()
                        && student.getMatricNum().get().toString().toLowerCase().startsWith(partialMatNo.toLowerCase()))
                .map(student -> student.getMatricNum().get().toString())
                .collect(Collectors.toList());
    }

    /**
     * Searches for module codes in the application beginning with the current partial input.
     */
    public List<String> autocompleteModCode (String partialModCode) {
        return model.getFilteredModuleList().stream()
                .map(module -> module.getModCode().toString())
                .filter(moduleCode -> moduleCode.toLowerCase().startsWith(partialModCode.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Searches for emails in the application beginning with the current partial input.
     */
    public List<String> autocompleteName (String partialName) {
        return model.getFilteredStudentList().stream()
                .map(student -> student.getName().toString())
                .filter(studentName -> studentName.toLowerCase().startsWith(partialName.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Searches for student names in the application beginning with the current partial input.
     */
    public List<String> autocompleteNusId (String partialNusId) {
        return model.getFilteredStudentList().stream()
                .filter(student -> student.getNusnetId().isPresent()
                        && student.getNusnetId().get().toString().toLowerCase().startsWith(partialNusId.toLowerCase()))
                .map(student -> student.getNusnetId().get().toString())
                .collect(Collectors.toList());
    }

    /**
     * Searches for tutorial names in the application beginning with the current partial input.
     */
    public List<String> autocompleteTutName (String partialTutName) {
        return model.getFilteredTutorialList().stream()
                .map(tutorial -> tutorial.getTutName().toString())
                .filter(tutorialName -> tutorialName.toLowerCase().startsWith(partialTutName.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Searches for day names beginning with the current partial input.
     */
    public List<String> autocompleteDay (String partialDay) {
        return Stream.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
                .filter(day -> day.toLowerCase().startsWith(partialDay.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Gets all command classes within the {@code logic.commands} package, and searches for command words among them
     * that autocomplete the given partial input string.
     * @return
     */
    public List<String> autocompleteCommandWord (String partialCommand) {

        //        // for matching file names of Command classes, excluding the abstract Command.java itself
        //        Pattern commandClassPattern = Pattern.compile("[a-zA-Z]+Command\\.java");
        //        // for excluding all "Verified" commands as these are not standalone commands and have no command word
        //        Pattern verifiedCommandNamePattern = Pattern.compile("[a-zA-Z]+Verified");
        //        // list of other, non-standalone commands to not suggest to user
        //        List<String> excludedCommands = Arrays.asList("ConfirmNo", "ConfirmYes", "SelectSuggestion");
        //
        //        Path path = Paths.get("src/main/java/seedu/tarence/logic/commands");
        //        try {
        //            return Files.list(path)
        //                    .filter(currPath -> !Files.isDirectory(currPath))
        //                    .filter(file -> commandClassPattern.matcher(file.toString()).find())
        //                    .map(file -> file.getFileName().toString())
        //                    .map(file -> file.substring(0, file.indexOf("Command.java")))
        //                    .filter(file -> !verifiedCommandNamePattern.matcher(file).find())
        //                    .filter(file -> !excludedCommands.contains(file))
        //                    .filter(file -> file.toLowerCase().startsWith(partialCommand.strip().toLowerCase()))
        //                    .map(file -> file.substring(0, 1).toLowerCase() + file.substring(1))
        //                    .collect(Collectors.toList());
        //        } catch (IOException e) {
        //            System.out.println(e);
        //        }

        return Stream.of("addAssignment", "addEvent", "addModule", "addStudent", "addTutorial", "changeTab",
                "deleteAssignment", "deleteEvent", "deleteModule", "deleteStudent", "deleteTutorial",
                "displayAssignmentScore", "displayAttendance", "edit", "editEvent", "exit",
                "exportAttendance", "find", "help", "import", "list", "listEvents", "markAttendance",
                "setAssignmentScore", "setSemStart")
                .filter(command -> command.toLowerCase().startsWith(partialCommand.strip().toLowerCase()))
                .collect(Collectors.toList());

        //return excludedCommands;
    }

}
