package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    public static final String MESSAGE_INVALID_NOTE_DISPLAYED_INDEX = "The note index provided is invalid!";
    public static final String MESSAGE_NOTES_LISTED_OVERVIEW = "%1$d notes listed!";
    public static final String MESSAGE_MATCHING_NOTE_FOUND = "Note matching the title exists.";
    public static final String MESSAGE_NO_MATCHING_NOTE_FOUND = "No note with that title exists!";
    public static final String MESSAGE_INCORRECT_NOTE_FRAGMENT_FORMAT = "Incorrect tagging format for note "
            + "highlights! Use 'C/' rather than 'c/', and 'TAG/' rather than 'tag/'!";

    public static final String MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX = "The flashcard index provided is invalid";
    public static final String MESSAGE_NO_FLASHCARD_LOADED = "No flashcard has been loaded";
    public static final String MESSAGE_ANSWER_ALREADY_LOADED = "Answer has already been loaded";
    public static final String MESSAGE_WELCOME_STUDYBUDDYPRO = "Welcome to StudyBuddyPro!" + "\n"
            + "Please enter one of the following modes, using the following command : \n"
            + "Format: switch fc/notes/cs.\nExample usages:\n\t"
            + "switch fc -> to enter flashcard feature\n\t"
            + "switch notes -> to enter notes feature\n\t"
            + "switch cs -> to enter cheatsheet feature\n\t";

    public static final String MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX = "The cheatsheet index provided is invalid";
    public static final String MESSAGE_INVALID_CHEATSHEET_CONTENT_DISPLAYED_INDEX = "The content index "
            + "provided is invalid";
    public static final String MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_CHEATSHEET = "Are you sure you would like "
            + "to delete the following cheatsheet?";
    public static final String MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_FLASHCARD = "Are you sure you would like "
            + "to delete the following flashcard?";
    public static final String MESSAGE_ARE_YOU_SURE_WANT_TO_DELETE_NOTE = "Are you sure you would like "
            + "to delete the following note?";
    public static final String MESSAGE_CONFIRM_DELETE = "Please use `delete %d` again to confirm your deletion.";


    public static final String MESSAGE_NO_CHEATSHEET_LOADED = "No cheatsheet has been loaded";
    public static final String MESSAGE_INVALID_TAG_INDEX = "Sorry! The tag index provided is invalid!";

    public static final String MESSAGE_TAG_LIMIT_EXCEEDED = "Sorry! A StudyBuddyItem can have no more than 10 tags.";

    public static final String SPECIFY_MODE = "Please specify a mode to start with: Cheatsheet, Flashcard or Note.\n"
            + "Use 'switch' command to specify the mode.";

    public static final String ADD = "add";
    public static final String DELETE = "delete";
    public static final String EDIT = "edit";
    public static final String VIEW = "view";
    public static final String VIEW_RAW = "viewraw";
    public static final String LIST = "list";
    public static final String FILTER = "filter";
    public static final String FILTER_ALL = "filterall";
    public static final String CLEAR = "clear";

    public static final String TIMETRIAL = "timetrial";
    public static final String SHOW = "show";

    public static final String REMIND = "remind";

    public static final String COMMAND_SUMMARY = "GLOBAL COMMANDS (Can be executed in any mode)\n" +
            " - Switch : switch MODE\n" +
            " - Filter All : filterall tag/TAG…\u200B\n" +
            " - List tags : taglist\n" +
            " - Help : help\n" +
            " - List : list\n" +
            " - Exit : exit\n" +
            "\n" +
            "FLASHCARD COMMANDS\n" +
            " - Add : add q/QUESTION a/ANSWER t/TITLE [tag/TAG]…\u200B\n" +
            " - Delete : delete INDEX\n" +
            " - Filter : filter tag/TAG…\u200B\n" +
            " - Time Trial : timetrial TAG\n" +
            " - View : view INDEX\n" +
            " - List : list\n" +
            " - Show : show\n" +
            " - Remind : remind\n" +
            "\n" +
            "NOTE COMMANDS\n" +
            " - Add : add t/TITLE c/CONTENT tag/TAG…\u200B\n" +
            " - Delete : delete INDEX\n" +
            " - View : view INDEX\n" +
            " - Viewing a raw note : viewraw INDEX\n" +
            " - Filter : filter tag/TAG…\u200B\n" +
            " - List : list\n" +
            "\n" +
            "CHEATSHEET COMMANDS\n" +
            " - Add : add t/TITLE [tag/TAG]…\u200B\n" +
            " - Delete : delete INDEX\n" +
            " - Edit : edit INDEX t/TITLE tag/TAG…\u200B\n" +
            " - Show : show INDEX\n" +
            " - View : view INDEX\n" +
            " - Filter : filter tag/TAG…\u200B\n" +
            " - List : list";
}
