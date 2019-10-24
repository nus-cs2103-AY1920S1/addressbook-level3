package dream.fcard.logic.respond;

import java.util.ArrayList;

import dream.fcard.logic.exam.ExamRunner;
import dream.fcard.logic.respond.commands.CreateCommand;
import dream.fcard.logic.respond.commands.EditCommand;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.exceptions.DeckAlreadyExistsException;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.model.exceptions.InvalidInputException;

/**
 * Enum of regex and response function pairs used by Responder to evaluate input.
 */
enum Responses {
    HELP("(?i)^(help)?(\\s)*(command/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        // GUI.handleHelp();
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
            examRunner.runExam();
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
