package dream.fcard.logic.respond;

import dream.fcard.model.State;

/**
 * Enum of regex and response function pairs used by Responder to evaluate input.
 */
enum Responses {
    HELP("(?i)^(help)?(\\s)*(command/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is HELP");
        return true; // capture is valid, end checking other commands
    }),
    IMPORT("(?i)^(import)?(\\s)+(filepath/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is IMPORT");
        return true; // capture is valid, end checking other commands
    }),
    EXPORT("(?i)^(export)?(\\s)+(filepath/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is EXPORT");
        return true; // capture is valid, end checking other commands
    }),
    STATS("(?i)^(stats)?(\\s)*(deck/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is STATS");
        return true; // capture is valid, end checking other commands
    }),
    VIEW("(?i)^(view)?(\\s)*(deck/[\\w\\p{Punct}]+)?(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is VIEW");
        return true; // capture is valid, end checking other commands
    }),
    CREATE("(?i)^(create)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(\\s)*", (commandInput, programState) -> {
        System.out.println("Current command is CREATE");
        return true; // capture is valid, end checking other commands
    }),
    TEST("(?i)^(test)?(\\s)+(duration/[\\w\\p{Punct}]+)*(deck/[\\w\\p{Punct}]+){1}(\\s)*", (
            commandInput, programState) -> {
        System.out.println("Current command is TEST");
        return true; // capture is valid, end checking other commands
    }),
    EXIT("(?i)^(exit)?", (commandInput, programState) -> {
        System.out.println("Current command is EXIT");
        return true; // capture is valid, end checking other commands
    }),
    EDIT("(?i)^(edit)?(\\s)+(deck/[\\w\\p{Punct}]+){1}(action/[\\w\\p{Punct}]+){1}(index/[\\w\\p{Punct}]+)?"
            + "(front/[\\w\\p{Punct}]+)?(back/[\\w\\p{Punct}]+)?(\\s)*", (
            commandInput, programState) -> {
        System.out.println("Current command is EDIT");
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
