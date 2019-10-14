package dream.fcard.logic.respond;

import dream.fcard.model.State;

/**
 * Enum of regex and response function pairs used by Responder to evaluate input.
 */
enum Responses {
    HELP("(?i)^(help)?(\\s)*(command/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is HELP");
        /*Print out "Available commands are:\n" +
                "Help [Command]\n" +
                "Import filepath/FILEPATH\n" +
                "Export filepath/FILEPATH\n" +
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
    IMPORT("(?i)^(import)?(\\s)+(filepath/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is IMPORT");
        //Deck newDeck = *get deck from filepath*;
        //programState.getDecks().add(newDeck);
        return true; // capture is valid, end checking other commands
    }),
    EXPORT("(?i)^(export)?(\\s)+(filepath/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is EXPORT");
        //Deck newDeck = programState.getDecks().get(*whichever deck*);
        //*Export newDeck to filepath*
        return true; // capture is valid, end checking other commands
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
    TEST("(?i)^(test)?(\\s)+(duration/[\\w\\p{Punct}]+)?(deck/[\\w\\p{Punct}]+){1}(\\s)*", (
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
    EXIT("(?i)^(exit)?", (commandInput, programState) -> {
        System.out.println("Current command is EXIT");
        // Exit from application
        return true; // capture is valid, end checking other commands
    }),
    EDIT("(?i)^(edit)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(action/[\\w\\p{Punct}]+){1}(index/[\\w\\p{Punct}]+)?"
            + "(front/[\\w\\p{Punct}]+)?(back/[\\w\\p{Punct}]+)?(\\s)*", (
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
