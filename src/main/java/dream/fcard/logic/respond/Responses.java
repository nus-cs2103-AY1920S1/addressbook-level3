package dream.fcard.logic.respond;

import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
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
    CREATE("(?i)^(create)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is CREATE");
        // String inputName = *name of deck to find*;
        // Deck newDeck = new Deck(inputName);
        // programState.getDecks().add(newDeck);
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
                // Will plan an implement soon (a bit busy now lol)
                return true; // capture is valid, end checking other commands
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
    public boolean call(String i, State s) {
        if (i.matches(regex)) {
            return func.funcCall(i, s);
        }
        return false;
    }
}
