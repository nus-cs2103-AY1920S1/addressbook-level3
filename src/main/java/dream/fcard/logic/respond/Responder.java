package dream.fcard.logic.respond;

import dream.fcard.model.State;

/**
 * Interface to take in user input and execute program behaviour.
 */
public enum Responder {
    /*BYE("(?i)^b(ye)?\\s*", (i, state) -> {
        Printer.printString("Bye. Hope to see you again soon!");
        state.toExit = true;
        state.lastError = false;
        return true;
    }),*/
    HELP("(?i)^(help)?(\\s)*(command/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),
    IMPORT("(?i)^(import)?(\\s)+(filepath/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),
    EXPORT("(?i)^(export)?(\\s)+(filepath/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),
    STATS("(?i)^(stats)?(\\s)*(deck/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),
    VIEW("(?i)^(view)?(\\s)*(deck/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),
    CREATE("(?i)^(create)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),
    TEST("(?i)^(test)?(\\s)+(duration/[\\w\\p{Punct}]+)*(deck/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),
    EXIT("(?i)^(exit)?", (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),
    EDIT("(?i)^(edit)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(action/[\\w\\p{Punct}]+){1}(index/[\\w\\p{Punct}]+)?" +
            "(front/[\\w\\p{Punct}]+)?(back/[\\w\\p{Punct}]+)?(\\s)*",
            (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),
    RESPONDER_NAME("regex", (commandInput, programState) -> {
        // carry out command
        return true; // capture is valid, end checking other commands
        return false; // even though match, keep checking other commands match
    }),;

    private String regex;
    private ResponseFunc func;

    Responder(String r, ResponseFunc f) {
        regex = r;
        func = f;
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
