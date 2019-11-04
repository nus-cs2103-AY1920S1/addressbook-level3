package dream.fcard.logic.respond;

import java.util.ArrayList;

import dream.fcard.gui.controllers.displays.test.TestDisplay;
import dream.fcard.gui.controllers.displays.test.TimedTestDisplay;
import dream.fcard.logic.exam.Exam;
import dream.fcard.logic.exam.ExamRunner;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.StateEnum;
import dream.fcard.model.StateHolder;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.util.RegexUtil;

/**
 * The enums are composed of three properties:
 * 1) regex the input must match
 * 2) ResponseGroup(s) the enum belong to
 * 3) function processing input and state if input matches regex
 * <p>
 * Order in which the enums are declared is IMPORTANT, as top most enums
 * are checked first before last, thus last enums should be more generic
 * and higher should be more specific; thus you can see valid enums
 * followed by error enums declared in that order often.
 * <p>
 * This class is to be used for all parsing, state mutation logic and dispatcher calls.
 * In no other class should they take the responsibility.
 */
public enum Responses {
    CREATE_NEW_DECK_WITH_NAME(
            "^((?i)create)\\s+((?i)deck/)\\s*\\S.*",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    String deckName = i.split("(?i)deck/\\s*")[1];
                    if (StateHolder.getState().hasDeckName(deckName) == -1) {
                        StateHolder.getState().addDeck(deckName);
                        Consumers.doTask(ConsumerSchema.RENDER_LIST, true);
                        Consumers.doTask(ConsumerSchema.SEE_SPECIFIC_DECK, StateHolder
                                .getState().getDecks().size());
                        try {
                            StorageManager.writeDeck(StateHolder.getState().getDeck(deckName));
                        } catch (DeckNotFoundException e) {
                            Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "I could not save your deck. I'll try"
                                    + " again when you shut me down.");
                        }

                    } else {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "That name is in use.");
                    }
                    return true;
                } //done
    ),
    CREATE_ERROR(
            "^((?i)create).*",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Error. Give me a deck name.");
                    return true;
                } //done
    ),
    SEE_SPECIFIC_DECK(
            "^((?i)view)\\s+[0-9]+$",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    int num = Integer.parseInt(i.split("^(?i)view\\s+")[1]);
                    Consumers.doTask(ConsumerSchema.SEE_SPECIFIC_DECK, num);
                    return true;
                } //done
    ),
    SEE_SPECIFIC_DECK_ERROR(
            "^((?i)view).*",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Error. Give me a deck number.");
                    return true;
                } //done
    ),
    PROCESS_INPUT_FRONT_BACK(
            RegexUtil.commandFormatRegex("", new String[]{"front/", "back/"}),
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    ArrayList<ArrayList<String>> res =
                            RegexUtil.parseCommandFormat("", new String[]{"front/", "back/"}, i);
                    if (res.get(0).size() > 0 && res.get(1).size() > 0) {
                        FrontBackCard card = new FrontBackCard(res.get(0).get(0), res.get(1).get(0));
                        StateHolder.getState().getCurrentDeck().addNewCard(card);
                        StorageManager.writeDeck(StateHolder.getState().getCurrentDeck());
                        // dispatch card to CreateDeckDisplay to be added to tempDeck
                        // make editing window dispatches
                    } else {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Error. Front/back fields cannot be blank.");
                    }
                    return true;
                } //todo
    ),
    // DEFAULT GROUP ----------------------------------------------------------
    START_TEST(
            RegexUtil.commandFormatRegex("test", new String[]{"deck/"}),
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    StateHolder.getState().setCurrState(StateEnum.TEST);
                    //pull out name of deck -> get stateholder to find the deck and get the correct subset
                    ArrayList<FlashCard> testArrayListOfCards =
                            StateHolder.getState().getDecks().get(0).getSubsetForTest();
                    ExamRunner.createExam(testArrayListOfCards, 10);
                    Exam exam = ExamRunner.getCurrentExam();
                    if (exam.getDuration() == 0) {
                        TestDisplay testDisplay = new TestDisplay(exam);
                        Consumers.doTask(ConsumerSchema.SWAP_DISPLAYS, testDisplay);
                    }
                    if (exam.getDuration() > 0) {
                        TimedTestDisplay timedTestDisplay = new TimedTestDisplay(exam);
                        Consumers.doTask(ConsumerSchema.SWAP_DISPLAYS, timedTestDisplay);
                    }
                    return true;
                } //todo
    ),
    START_TEST_ERROR(
            "^((?i)test).*",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You need to specify a deck.");
                    return true;
                } //todo
    ),
    // TEST GROUP ----------------------------------------------------------

    QUIT(
            "^((?i)quit)\\s*$",
            new ResponseGroup[]{ResponseGroup.MATCH_ALL},
                i -> {
                    Consumers.doTask(ConsumerSchema.QUIT_PROGRAM, true);
                    return false;
                } //done
    ),

    UNKNOWN(
            ".*",
            new ResponseGroup[]{ResponseGroup.MATCH_ALL},
                i -> {
                    Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "I did not understand that command.");
                    return true;
                } //done
    );

    // MATCH ALL GROUP --------------------------------------------------------

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
     * @return boolean if the string has matched
     */
    public boolean call(String i) {
        if (i.matches(regex)) {
            return func.funcCall(i);
        }
        return false;
    }

    /**
     * Given a ResponseGroup, determine if this Response belongs to it.
     *
     * @param groupArg ResponseGroup
     * @return True, belongs to group
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
