package dream.fcard.logic.respond.commands;

/**
 * A Class that stores all the formats for the different CLI commands.
 */
public class HelpCommand extends Command {
    public static final String IMPORT = "Format: import filepath/FILEPATH.";
    public static final String EXPORT = "Format: export filepath/FILEPATH.";
    public static final String STATS = "Format: stats (for Application stats) OR stats deck/DECK_NAME"
            + "(for Deck stats).";
    public static final String CREATE = "Format: create deck/DECK_NAME";
    public static final String ADD = "Format: add deck/DECK_NAME [priority/PRIORITY_NAME] front/FRONT back/BACK"
            + "[choice/CHOICE]";
    public static final String DELETE = "Format: delete deck/DECK_NAME index/CARD_TO_BE_DELETED";
    public static final String EDIT = "Format: edit filepath/FILEPATH";
    public static final String TEST = "Format: test deck/DECK_NAME [duration/TIME_IN_SECONDS]";

    private static String[] allCommands = {IMPORT, EXPORT, STATS, CREATE, ADD, DELETE, EDIT, TEST};

    public static String[] getAllCommands() {
        return allCommands;
    }
}
