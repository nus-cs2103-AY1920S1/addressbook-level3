package dream.fcard.logic.respond;

import java.util.ArrayList;

import dream.fcard.logic.exam.ExamRunner;
import dream.fcard.logic.respond.commands.CreateCommand;
import dream.fcard.logic.respond.commands.EditCommand;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.exceptions.DeckAlreadyExistsException;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.model.exceptions.InvalidInputException;
import dream.fcard.util.FileReadWrite;

/**
 * Enum of regex and response function pairs used by Responder to evaluate input.
 */
enum Responses {
    HELP("(?i)^(help)?(\\s)*(command/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        // GUI.handleHelp();
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
        String[] commandsWithSpaces = commandInput.split(" ");
        ArrayList<String> commands = new ArrayList<>();
        for (String s : commandsWithSpaces) {
            if (s.length() > 0) {
                commands.add(s);
            }
        }
        try {
            if (commands.size() > 1) {
                // View deck
                String deckName = commands.get(1);
                Deck deck = programState.getDeck(deckName);

                // GUI.showDeck();
            } else {
                // View all decks
                ArrayList<Deck> allDecks = programState.getAllDecks();
                StringBuilder toPrint = new StringBuilder();
                for (Deck deck : allDecks) {
                    toPrint.append(deck.getName() + "\n");
                }

                // GUI.handleString or smt
            }
        } catch (DeckNotFoundException dnfExc) {
            System.out.println(dnfExc.getMessage());
        }
        return true; // capture is valid, end checking other commands
    }),
    CREATE("(?i)^(create)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        CreateCommand newCommand = new CreateCommand(commandInput, programState);
        try {
            return newCommand.funcCall();
        } catch (DeckAlreadyExistsException deaExc) {
            System.out.println(deaExc.getMessage());
        }
        return false;
    }),
    TEST("(?i)^(test)?(\\\\s)+(duration/[\\\\w\\\\p{Punct}]+)?(\\\\s)+(deck/[\\\\w\\\\p{Punct}]+){1}(\\\\s)*", (
            commandInput, programState) -> {
        // Requires implementation for timed tests.
        String deckName = commandInput.split("deck/")[1].strip();
        try {
            Deck examDeck = programState.getDeck(deckName);
            ExamRunner examRunner = new ExamRunner(examDeck);
            examRunner.initExam();
            return true; // capture is valid, end checking other commands
        } catch (DeckNotFoundException dnfExc) {
            System.out.println(dnfExc.getMessage());
            return true; // capture is valid, end checking other commands
        } catch (IndexNotFoundException infExc) {
            System.out.println(infExc.getMessage());
        }
        return false;
    }),
    EXIT("(?i)^(exit)?.", (commandInput, programState) -> {
        // GUI.handleExit();
        return true; // capture is valid, end checking other commands
    }),
    EDIT("(?i)^(edit)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(\\s)+(action/[\\w\\p{Punct}]+){1}(\\s)*((\\s)+"
            + "(index/[\\w\\p{Punct}]+){1}(\\s)*)?((\\s)+(front/[\\w\\p{Punct}]+){1}(\\s)*)?((\\s)+"
            + "(back/[\\w\\p{Punct}]+))?(\\s)*", (
            commandInput, programState) -> {
                EditCommand newCommand = new EditCommand(commandInput, programState);
                try {
                    return newCommand.funcCall(); // capture is valid, end checking other commands
                } catch (InvalidInputException iiExc) {
                    System.out.println(iiExc.getMessage());
                } catch (IndexNotFoundException infExc) {
                    System.out.println(infExc.getMessage());
                } catch (DeckNotFoundException dnfExc) {
                    System.out.println(dnfExc.getMessage());
                }
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
    public boolean call(String i, State s) {
        if (i.matches(regex)) {
            return func.funcCall(i, s);
        }
        return false;
    }
}
