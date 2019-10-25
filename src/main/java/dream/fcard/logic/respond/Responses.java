package dream.fcard.logic.respond;

import java.util.logging.Logger;

import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.logic.respond.exception.DuplicateFoundException;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.util.FileReadWrite;

/**
 * Enum of regex and response function pairs used by Responder to evaluate input.
 */
enum Responses {
    HELP("(?i)^(help)?(\\s)*(command/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is HELP");
        /*Print out "Available commands are:\n" +
                "Help [Command]\n" +
                "Import FILEPATH\n" +
                "Root DIRECTORY_PATH" +
                "Export deck/ DECK_NAME path/ DIRECTORY_PATH\n" +
                "Stats [deck/DECK_NAME]\n" +
                "View [deck/DECK_NAME]\n" +
                "Create deck/DECK_NAME\n" +
                "Test [duration/TIME_LIMIT_ALLOWED] deck/DECK_NAME\n" +
                "Exit\n" +
                "Edit deck/DECK_NAME action/DESIRED_ACTION [index/CARD_INDEX] [front/NEW_FRONT_TEXT]" +
                "[back/NEW_BACK_TEXT]\n";
         */
        return true; // capture is valid, end checking other commands
    }),
    ROOT("(?i)^r(oot)?(\\s)+.+", (commandInput, programState) -> {
        String path = FileReadWrite.normalizePath(commandInput.split(" ")[1].trim());
        if (FileReadWrite.pathValidDirectory(path)) {
            StorageManager.provideRoot(path);
            programState.reloadAllDecks(StorageManager.loadDecks());
            System.out.println("Successfully changed root");
        } else {
            System.out.println("argument is not a valid directory");
        }
        return true;
    }),
    ROOT_NO_PATH("(?i)^r(oot)?(\\s)*", (commandInput, programState) -> {
        System.out.println("No directory specified, e.g. root ~/Desktop");
        return true;
    }),
    IMPORT("(?i)^i(mport)?(\\s)+.+", (commandInput, programState) -> {
        System.out.println("Current command is IMPORT");

        String path = commandInput.split(" ")[1].trim();

        Deck deck = StorageManager.loadDeck(path);
        if (deck != null) {
            StorageManager.writeDeck(deck);
            programState.addDeck(deck);
            System.out.println("Successfully added " + path);
        } else {
            System.out.println("File does not exist, or file does not match schema for a deck");
        }
        return true;
    }),
    IMPORT_NO_PATH("(?i)^i(mport)?(\\s)*", (commandInput, programState) -> {
        System.out.println("No path specified, e.g. import ~/Desktop/file.json");
        return true;
    }),
    EXPORT("(?i)^e(xport)?(\\s)+deck/(\\s)*.+(\\s)+path/(\\s)*.+", (commandInput, programState) -> {
        System.out.println("Current command is EXPORT");

        String[] parts = commandInput.split("deck/")[1].split("path/");
        String deckName = parts[0].trim();
        String pathName = parts[1].trim();

        try {
            Deck d = programState.getDeck(deckName);
            FileReadWrite.write(FileReadWrite.resolve(pathName, "./" + d.getName() + ".json"), d.toJson().toString());
        } catch (DeckNotFoundException e) {
            System.out.println("Deck does not exist");
        }

        return true;
    }),
    EXPORT_NO_PATH("(?i)^e(xport)?(\\s)+deck/(\\s)*.+", (commandInput, programState) -> {
        System.out.println("No path specified, e.g. export deck/ deckName path/ ~/Desktop");
        return true;
    }),
    EXPORT_NO_DECK("(?i)^e(xport)?.*", (commandInput, programState) -> {
        System.out.println("No deck specified, e.g. export deck/ deckName path/ ~/Desktop");
        return true;
    }),
    STATS("(?i)^(stats)?(\\s)*(deck/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is STATS");
        // ArrayList<Deck> allDecks = programState.getDecks();
        // String inputName = *name of deck to find*;
        // for (Deck curr : allDecks) {
        //      if(curr.getName().equals(inputName) {
        //         System.out.println(curr.getStats());
        //      }
        // }
        return true; // capture is valid, end checking other commands
    }),
    VIEW("(?i)^(view)?(\\s)*(deck/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is VIEW");
        // ArrayList<Deck> allDecks = programState.getDecks();
        //  String inputName = *name of deck to find*;
        //  for (Deck curr : allDecks) {
        //      if(curr.getName().equals(inputName) {
        //          curr.viewDeck();
        //      }
        //  }

        return true; // capture is valid, end checking other commands
    }),

    CREATE_DECK("(?i)^(create)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is CREATE");

        // Split according to numerous white spaces in between
        String[] userInputFields = commandInput.split("\\s+");

        // Extract user input field
        String deckName = userInputFields[1].replaceFirst("deck/", "");

        boolean isDeckExist = true;

        // Check for deck that exist in state with same name
        try {
            Deck deck = programState.getDeck(deckName);
        } catch (DeckNotFoundException d) {
            isDeckExist = false;
            Deck newDeck = new Deck(deckName);
            programState.addDeck(newDeck);
        }

        if (isDeckExist) {
            throw new DuplicateFoundException("Deck with same name exist in FlashCard Pro already - " + deckName);
        }

        return true; // capture is valid, end checking other commands
    }),

    // expanded create to take in
    // compulsory deck
    // compulsory set of front/ back/
    // only once each
    DECK_CREATE_REG_CARD("(?i)^(create)?(\\s)+"
            + "(deck/[\\w\\p{Punct}]+){1}(\\s)*"
            + "(front/[\\w\\p{Punct}]+){1}(\\s)*"
            + "(back/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {

                System.out.println("Current command is DECK_CREATE_REG_CARD");

                // Split according to numerous white spaces in between
                String[] userInputFields = commandInput.split("\\s+");

                // Extract user input field
                String deckName = userInputFields[1].replaceFirst("deck/", "");
                String front = userInputFields[2].replaceFirst("front/", "");
                String back = userInputFields[3].replaceFirst("back/", "");

                // Check if deck by the name exist
                try {
                    Deck deck = programState.getDeck(deckName);
                    deck.addNewCard(new FrontBackCard(front, back));
                } catch (DeckNotFoundException d) {
                    // Throw exception to responder
                    throw new DeckNotFoundException(d.getMessage());
                }

                System.out.println(deckName + " " + front + " " + back);

                return true; // capture is valid, end checking other commands
            }),

    TEST("(?i)^(test)?(\\\\s)+(duration/[\\\\w\\\\p{Punct}]+)?(\\\\s)+(deck/[\\\\w\\\\p{Punct}]+){1}(\\\\s)*", (
            commandInput, programState) -> {
        System.out.println("Current command is TEST");
        // ArrayList<Deck> allDecks = programState.getDecks();
        // String inputName = *name of deck to find*;
        // Deck testDeck;
        // for (Deck curr : allDecks) {
        //      if(curr.getName().equals(inputName) {
        //          testDeck = curr;
        //      }
        // }
        // *Initiate test with Test Deck*
        return true; // capture is valid, end checking other commands
    }),
    EXIT("(?i)^(exit)?.", (commandInput, programState) -> {
        System.out.println("Current command is EXIT");
        // Exit from application
        return true; // capture is valid, end checking other commands
    }),
    EDIT("(?i)^(edit)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(\\s)+(action/[\\w\\p{Punct}]+){1}(\\s)*((\\s)+"
            + "(index/[\\w\\p{Punct}]+){1}(\\s)*)?((\\s)+(front/[\\w\\p{Punct}]+){1}(\\s)*)?((\\s)+"
            + "(back/[\\w\\p{Punct}]+))?(\\s)*", (
            commandInput, programState) -> {
                System.out.println("Current command is EDIT");
                return true; // capture is valid, end checking other commands
            }),
    UNKNOWN(".*", (commandInput, programState) -> {
        System.out.println("Sorry, I don't know what is this command.");

        LogsCenter.getLogger(Responses.class).warning("Unknown command entered.");
        return false;
    });

    private String regex;
    private ResponseFunc func;

    Responses(String r, ResponseFunc f) {
        this.regex = r;
        this.func = f;
    }

    /**
     * Given a string and program state, if string matches regex this enum will call its response
     * function.
     *
     * @param i input string
     * @param s state object
     * @return boolean if the string has matched
     */
    public boolean call(String i, State s) throws Exception {
        try {
            if (i.matches(regex)) {
                return func.funcCall(i, s);
            }
        } catch (DeckNotFoundException | DuplicateFoundException d) {
            throw new Exception(d.getMessage());
        }
        return false;
    }
}
