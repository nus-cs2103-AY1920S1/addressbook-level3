package dream.fcard.logic.respond;

import dream.fcard.model.State;

/**
 * The enums are composed of three properties:
 *  1) regex the input must match
 *  2) ResponseGroup(s) the enum belong to
 *  3) function processing input and state if input matches regex
 *
 *  Order in which the enums are declared is IMPORTANT, as top most enums
 *  are checked first before last, thus last enums should be more generic
 *  and higher should be more specific; thus you can see valid enums
 *  followed by error enums declared in that order often.
 */
public enum Responses {
    CREATE_NEW_DECK_WITH_NAME(
            "^((?i)create)\\s+((?i)deck/)\\s*\\S",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
            (i,s) -> {
                String deckName = i.split("(?i)deck/\\s*")[1];
                Dispatcher.accept(ConsumerSchema.CREATE_NEW_DECK_W_NAME, deckName);
                return true;
            }
    ),
    CREATE_NEW_DECK(
            "^((?i)create)\\s*$",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
            (i,s) -> {
                Dispatcher.accept(ConsumerSchema.CREATE_NEW_DECK, true);
                return true;
            }
    ),
    CREATE_ERROR(
            "^((?i)create)",
            new ResponseGroup[] {ResponseGroup.DEFAULT},
            (i,s) -> {
                //TODO dispatcher print invalid arguments given
                return true;
            }
    ),
    SEE_SPECIFIC_DECK(
            "^((?i)view)\\s+[0-9]+$",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
            (i,s) -> {
                int num = Integer.parseInt(i.split("^(?i)view\\s+")[1]);
                Dispatcher.accept(ConsumerSchema.SEE_SPECIFIC_DECK, num);
                return true;
            }
    ),
    SEE_SPECIFIC_DECK_ERROR(
            "^((?i)view)",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
            (i,s) -> {
                //TODO dispatcher print no numeric index given
                return true;
            }
    ),
    ADD_NEW_ROW_MCQ(
            "^((?i)add)\\s+((?i)option)$",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
            (i,s) -> {
                //TODO not implemented
                return true;
            }
    ),
    // DEFAULT GROUP ----------------------------------------------------------
    EXIT_CREATE(
            "^((?i)exit)\\s*$",
            new ResponseGroup[]{ResponseGroup.CREATE},
            (i,s) -> {
                Dispatcher.accept(ConsumerSchema.EXIT_CREATE, true);
                return true;
            }
    ),
    // CREATE GROUP -----------------------------------------------------------
    QUIT(
            "^((?i)quit)\\s*$",
            new ResponseGroup[]{ResponseGroup.MATCH_ALL},
            (i,s) -> {
                Dispatcher.accept(ConsumerSchema.QUIT_PROGRAM, true);
                return true;
            }
    ),
    // MATCH ALL GROUP --------------------------------------------------------
    ;

    private String regex;
    private ResponseGroup[] group;
    private ResponseFunc func;
    Responses(String r, ResponseGroup[] grp, ResponseFunc f) {
        regex = r;
        group = grp;
        func = f;
    }

    /**
     * Given a string and program state, if string matches regex
     * this enum will call its response function.
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

    /**
     * Given a ResponseGroup, determine if this Response belongs to it.
     * @param groupArg  ResponseGroup
     * @return          True, belongs to group
     */
    public boolean isInGroup(ResponseGroup groupArg) {
        for (ResponseGroup g : group) {
            if (g == groupArg) {
                return true;
            }
        }
        return false;
    }
}
