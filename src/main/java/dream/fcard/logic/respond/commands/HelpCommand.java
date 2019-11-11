package dream.fcard.logic.respond.commands;

/**
 * A Class that stores all the formats for the different CLI commands.
 */
public class HelpCommand extends Command {
    public static final String IMPORT = "Format: import filepath/FILEPATH.";
    public static final String EXPORT = "Format: export filepath/FILEPATH.";
    public static final String STATS = "Format: stats (for Application stats) OR stats deck/DECK_NAME "
            + "(for Deck stats).";
    public static final String CREATE = "Format: create deck/DECK_NAME";
    public static final String ADD = "Format: add deck/DECK_NAME [priority/PRIORITY_NAME] front/FRONT back/BACK "
            + "[choice/CHOICE]";
    public static final String DELETE = "Format: delete deck/DECK_NAME [index/INDEX_OF_CARD_TO_BE_DELETED]";
    public static final String EDIT = "Format: edit deck/DECK_NAME index/INDEX [front/FRONT_TEXT] [back/BACK_TEXT] "
            + "[choiceIndex/CHOICE_INDEX] [choice/CHOICE_TEXT]";
    public static final String TEST = "Format: test deck/DECK_NAME [duration/TIME_IN_SECONDS]";
    public static final String UNDO = "Format: undo";
    public static final String REDO = "Format: redo";
    public static final String QUIT = "Format: quit";
    public static final String HELP = "Format: help OR help command/COMMAND";

    public static final String ALL_COMMANDS_STRING = "Commands: IMPORT, EXPORT, CREATE, ADD, DELETE, EDIT, "
            + "UNDO, REDO, TEST, STATS. Use 'help command/COMMAND_NAME' for more info.";


    private static String[] allCommands = {IMPORT, EXPORT, STATS, CREATE, ADD, DELETE, EDIT, TEST, UNDO, REDO, QUIT, HELP};

    public static String[] getAllCommands() {
        return allCommands;
    }

    /**
     * Identifies if the input matches a given help message.
     *
     * @param input User input of the command
     * @param curr The String of the current command.
     * @return A boolean representing if the input matches the current command.
     */
    public static boolean isCorrectMessage(String input, String curr) {
        String currCommand = curr.split(" ")[1];

        return currCommand.equalsIgnoreCase(input);
    }

    public static String generalHelp() {
        return ALL_COMMANDS_STRING;
    }
}
