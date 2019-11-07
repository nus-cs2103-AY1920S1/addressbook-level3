package dream.fcard.logic.respond;

import java.util.ArrayList;

import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.gui.controllers.cards.frontview.McqCard;
import dream.fcard.gui.controllers.displays.test.EndOfTestAlert;
import dream.fcard.gui.controllers.displays.test.TestDisplay;
import dream.fcard.gui.controllers.displays.test.TimedTestDisplay;
import dream.fcard.logic.exam.Exam;
import dream.fcard.logic.exam.ExamRunner;
import dream.fcard.logic.respond.commands.CreateCommand;
import dream.fcard.logic.respond.commands.HelpCommand;
import dream.fcard.logic.storage.StorageManager;
import dream.fcard.model.Deck;
import dream.fcard.model.StateEnum;
import dream.fcard.model.StateHolder;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.DuplicateInChoicesException;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.RegexUtil;
import javafx.scene.layout.AnchorPane;

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
    HELP_WITH_COMMAND(
            RegexUtil.commandFormatRegex("", new String[]{"command/"}),
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    LogsCenter.getLogger(Responses.class).info("COMMAND: HELP_WITH_COMMAND");


                    ArrayList<ArrayList<String>> res = RegexUtil.parseCommandFormat("help",
                            new String[]{"command/"},
                            i);

                    boolean validCommand = false;

                    for (String curr : HelpCommand.getAllCommands()) {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, curr);
                        validCommand = true;
                    }

                    if (!validCommand) {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Command supplied is not a valid command!"
                                + "Type 'help' for the UserGuide'.");
                    }
                    return true;
                }
    ),
    HELP(
            "^((?i)help)(\\s*)$",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {

                    LogsCenter.getLogger(Responses.class).info("COMMAND: HELP");


                    //TODO open a window to UserGuide.html (by Taha)
                    return true;
                }
    ),
    IMPORT (
            RegexUtil.commandFormatRegex("import", new String[]{"filepath/"}),
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {

                    return true; //if valid
                    //return false; //if not valid
                }
    ),
    IMPORT_ERROR(
            "^((?i)import).*",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {

                    return true;
                }
    ),
    EXPORT (
            RegexUtil.commandFormatRegex("export", new String[]{"filepath/"}),
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {

                    return true; //if valid
                    //return false; //if not valid
                }
    ),
    EXPORT_ERROR(
            "^((?i)export).*",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {

                    return true;
                }
    ),
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
    // ADD_CARD regex format: add deck/DECK_NAME [priority/PRIORITY_NAME] front/FRONT back/BACK [choice/CHOICE]
    // Only used for MCQ and FrontBack cards
    // Note that back for MCQ cards will be used for identifying the correct CHOICE
    ADD_CARD(
            RegexUtil.commandFormatRegex("add", new String[]{"deck/", "front/", "back/"}),
            new ResponseGroup[] {ResponseGroup.DEFAULT},
                i -> {
                    ArrayList<ArrayList<String>> res = RegexUtil.parseCommandFormat("add",
                            new String[]{"deck/", "priority/", "front/", "back/", "choice/"},
                            i);

                    // Checks if "deck/", "front/"  and "back/" are supplied.
                    if (res.get(0).size() == 0 || res.get(2).size() == 0 || res.get(3).size() == 0) {
                        return false;
                    }

                    try {
                        return CreateCommand.createMcqFrontBack(res, StateHolder.getState());
                    } catch (DuplicateInChoicesException dicExc) {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "There are duplicated choices!");
                        return true;
                    } catch (DeckNotFoundException dnfExc) {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, dnfExc.getMessage());
                        return true;
                    }
                }
    ),
    ADD_CARD_ERROR(
            "^((?i)(add).*)",
            new ResponseGroup[] {ResponseGroup.DEFAULT},
                i -> {
                    Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Add command is invalid! To see the correct"
                            + "format of the Add command, type 'help command/add'");
                    return true;
                }
    ),
    EDIT_CARD(
            RegexUtil.commandFormatRegex("edit", new String[]{
                "deck/",
                "index/",
                "front/",
                "back/",
                "choiceIndex/",
                "choice/"}),
            new ResponseGroup[] {ResponseGroup.DEFAULT},
                i -> {
                    ArrayList<ArrayList<String>> res = RegexUtil.parseCommandFormat("add",
                            new String[]{
                                "deck/",
                                "index/",
                                "front/",
                                "back/",
                                "choiceIndex/",
                                "choice/"},
                            i);
                    // Checks if "deck/" and "index" are supplied.
                    if (res.get(0).size() == 0 || res.get(1).size() == 0) {
                        return false;
                    }

                    // Checks if choiceIndex and choice are both given or both not given.
                    if ((res.get(4).size() == 0 && res.get(5).size() != 0)
                            || (res.get(4).size() != 0 && res.get(5).size() == 0)) {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Choice and ChoiceIndex must be supplied"
                                + "together or not supplied at all!");
                        return true;
                    }

                    // Checks if nothing is being edited
                    if (res.get(2).size() == 0 && res.get(3).size() == 0 && res.get(4).size() == 0
                            && res.get(5).size() == 0) {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "No field is supplied to be edited!");
                        return true;
                    }

                    // Todo: Actual implementation below
                    return true;
                }
    ),
    EDIT_CARD_ERROR(
            "^((?i)(edit).*)",
            new ResponseGroup[] {ResponseGroup.DEFAULT},
                i -> {
                    Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Edit command is invalid! To see the correct"
                            + "format of the Edit command, type 'help command/edit'");
                    return true;
                }
    ),
    DELETE_CARD(
            RegexUtil.commandFormatRegex("delete", new String[]{"deck/", "index/"}),
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    ArrayList<ArrayList<String>> res = RegexUtil.parseCommandFormat("delete",
                            new String[]{"deck/", "index/"},
                            i);

                    // Checks if "deck/" and "index/" are supplied.
                    if (res.get(0).size() == 0 || res.get(1).size() == 0) {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Delete command is invalid! To see the"
                                + "correct format of the Delete command, type 'help command/delete'");
                        return true;
                    }

                    return true; //if valid
                    //return false; //if not valid
                }
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
                }
    ),
    EXIT_CREATE(
            "^((?i)exit)\\s*$",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    StateHolder.getState().setCurrState(StateEnum.DEFAULT);
                    Consumers.doTask(ConsumerSchema.EXIT_CREATE, true);
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
    STATS(
            RegexUtil.commandFormatRegex("stats", new String[]{"deck/"}),
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    ArrayList<ArrayList<String>> res =
                            RegexUtil.parseCommandFormat("test", new String[]{"deck/"}, i);

                    //Checks if a deckName is supplied.
                    boolean hasDeckName = res.get(0).size() > 0;

                    if (res.get(0).size() > 1) {
                        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Only 1 Deck at a time, please!");
                        return true;
                    }

                    if (hasDeckName) {
                        // Todo: Show Stats for Deck @nattanyz
                        return true;
                    } else {
                        // todo: causes InvocationTargetException, due to regex PatternSyntaxException.
                        //try {
                        //    // show stats for the application
                        //    StatisticsWindow statisticsWindow = new StatisticsWindow();
                        //    Consumers.doTask(ConsumerSchema.OPEN_WINDOW, statisticsWindow);
                        //} catch (Exception e) {
                        //    e.printStackTrace();
                        //}
                        return true;
                    }
                }
    ),
    // Starts a test and enters Test Mode (Note to Shawn: rmb to change the StateEnum to TEST)
    TEST(
            RegexUtil.commandFormatRegex("test", new String[]{"deck/", "duration/"}),
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    StateHolder.getState().setCurrState(StateEnum.TEST);
                    ArrayList<ArrayList<String>> res =
                            RegexUtil.parseCommandFormat("test", new String[]{"deck/", "duration/"}, i);
                    // res.get(0) returns the ArrayList of Deck Names (should only have one)
                    String deckName = res.get(0).get(0);
                    // res.get(1) returns the ArrayList of duration in seconds (should have zero or one).
                    String durationString = res.get(1).get(0);
                    // Duration is a String.
                    try {
                        Deck initDeck = StateHolder.getState().getDeck(deckName);
                        if (initDeck.getNumberOfCards() == 0) {
                            EndOfTestAlert.display("Error", "You cannot start a test on an empty deck!");
                            StateHolder.getState().setCurrState(StateEnum.DEFAULT);
                            return false;
                        } else {
                            ArrayList<FlashCard> testDeck = initDeck.getSubsetForTest();
                            int duration = Integer.parseInt(durationString);
                            ExamRunner.createExam(testDeck, duration);
                            Exam currExam = ExamRunner.getCurrentExam();
                            if (currExam.getDuration() == 0) {
                                TestDisplay testDisplay = new TestDisplay(currExam);
                                Consumers.doTask(ConsumerSchema.SWAP_DISPLAYS, testDisplay);
                            }
                            if (currExam.getDuration() > 0) {
                                TimedTestDisplay timedTestDisplay = new TimedTestDisplay(currExam);
                                Consumers.doTask(ConsumerSchema.SWAP_DISPLAYS, timedTestDisplay);
                            }
                            return true;
                        }
                    } catch(DeckNotFoundException e){
                        e.printStackTrace();
                    }
                    return true;
                }
    ),

    START_TEST_ERROR(
            "^((?i)test).*",
            new ResponseGroup[]{ResponseGroup.DEFAULT},
                i -> {
                    Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Wrong Command.");
                    StateHolder.getState().setCurrState(StateEnum.DEFAULT);
                    return true;
                } //todo
    ),


    // DEFAULT GROUP ----------------------------------------------------------

    TEST_NEXT(
            "^((?i)next)\\s*",
            new ResponseGroup[]{
                ResponseGroup.TEST,
                ResponseGroup.TEST_FBCARD,
                ResponseGroup.TEST_FBCARD_BACK,
                ResponseGroup.TEST_JSJAVA,
                ResponseGroup.TEST_MCQ,
                ResponseGroup.TEST_MCQ_BACK},
                i -> {
                    Exam exam = ExamRunner.getCurrentExam();
                    boolean isEndOfDeck = exam.upIndex();
                    if (isEndOfDeck) {
                        Consumers.doTask("STOP_TIMELINE", true);
                        if (ExamRunner.getCurrentExam() != null) {
                            ExamRunner.terminateExam();
                        }
                    }
                    AnchorPane newCard = exam.getCardDisplayFront();
                    Consumers.doTask("SWAP_CARD_DISPLAY", newCard);
                    Consumers.doTask("UPDATE_TEST_STATE", exam.getCurrentCard());
                    return true;
                }
    ),
    TEST_PREV(
            "^((?i)prev(ious)?)\\s*",
            new ResponseGroup[]{
                ResponseGroup.TEST,
                ResponseGroup.TEST_FBCARD,
                ResponseGroup.TEST_FBCARD_BACK,
                ResponseGroup.TEST_JSJAVA,
                ResponseGroup.TEST_MCQ,
                ResponseGroup.TEST_MCQ_BACK},
                i -> {
                    Exam exam = ExamRunner.getCurrentExam();
                    exam.downIndex();
                    AnchorPane newCard = exam.getCardDisplayFront();
                    Consumers.doTask("SWAP_CARD_DISPLAY", newCard);
                    Consumers.doTask("UPDATE_TEST_STATE", exam.getCurrentCard());
                    return true;
                }
    ),
    // Needs to change StateEnum back to DEFAULT
    TEST_EXIT(
            "^((?i)exit)\\s*",
            new ResponseGroup[]{
                ResponseGroup.TEST,
                ResponseGroup.TEST_FBCARD,
                ResponseGroup.TEST_FBCARD_BACK,
                ResponseGroup.TEST_JSJAVA,
                ResponseGroup.TEST_MCQ,
                ResponseGroup.TEST_MCQ_BACK},
                i -> {
                    Consumers.doTask("STOP_TIMELINE", true);
                    if (ExamRunner.getCurrentExam() != null) {
                        ExamRunner.terminateExam();
                        ExamRunner.clearExam();
                    }
                    Consumers.doTask(ConsumerSchema.DISPLAY_DECKS, true);
                    Consumers.doTask(ConsumerSchema.CLEAR_MESSAGE, true);
                    StateHolder.getState().setCurrState(StateEnum.DEFAULT);
                    return true;
                }
    ),

    // TEST GROUP (can be used by other TEST StateEnums ------------------------

    FB_FRONT(
            "^((?i)front)\\s*",
            new ResponseGroup[]{
                    ResponseGroup.TEST_FBCARD,
                    ResponseGroup.TEST_FBCARD_BACK},
                i -> {
                    StateHolder.getState().setCurrState(StateEnum.TEST_FBCARD);
                    Exam exam = ExamRunner.getCurrentExam();
                    AnchorPane cardFront = exam.getCardDisplayFront();
                    Consumers.doTask("SWAP_CARD_DISPLAY", cardFront);
                    return true;
                }
    ),
    FB_BACK(
            "^((?i)back)\\s*",
            new ResponseGroup[]{ResponseGroup.TEST_FBCARD},
                i -> {
                    StateHolder.getState().setCurrState(StateEnum.TEST_FBCARD_BACK);
                    Exam exam = ExamRunner.getCurrentExam();
                    AnchorPane cardBack = exam.getCardDisplayBack();
                    Consumers.doTask("SWAP_CARD_DISPLAY", cardBack);
                    return true;
                }
    ),
    FB_CORRECT(
            "^((?i)correct)\\s*",
            new ResponseGroup[]{ResponseGroup.TEST_FBCARD_BACK},
                i -> {
                    Consumers.doTask("GET_SCORE", true);
                    Exam exam = ExamRunner.getCurrentExam();
                    exam.upIndex();
                    AnchorPane nextCardFront = exam.getCardDisplayFront();
                    Consumers.doTask("SWAP_CARD_DISPLAY", nextCardFront);
                    Consumers.doTask("UPDATE_TEST_STATE", exam.getCurrentCard());
                    return true;
                }
    ),
    FB_WRONG(
            "^((?i)wrong)\\s*",
            new ResponseGroup[]{ResponseGroup.TEST_FBCARD_BACK},
                i -> {
                    Consumers.doTask("GET_SCORE", false);
                    Exam exam = ExamRunner.getCurrentExam();
                    exam.upIndex();
                    AnchorPane nextCardFront = exam.getCardDisplayFront();
                    Consumers.doTask("SWAP_CARD_DISPLAY", nextCardFront);
                    Consumers.doTask("UPDATE_TEST_STATE", exam.getCurrentCard());
                    return true;
                }
    ),

    // TEST_FB GROUP ----------------------------------------------------------

    MCQ_PROCESS_INPUT(
            "^((?i)(\\d)+\\s*)",
            new ResponseGroup[]{ResponseGroup.TEST_MCQ},
                i -> {
                    LogsCenter.getLogger(i);
                    String[] inputArray = i.split(" ");
                    String choice = inputArray[0];
                    Exam exam = ExamRunner.getCurrentExam();
                    MultipleChoiceCard mcqCard = (MultipleChoiceCard) exam.getCurrentCard();
                    mcqCard.setUserAttempt(Integer.parseInt(choice));
                    try {
                        boolean isCorrect = mcqCard.evaluate(choice);
                        Consumers.doTask("GET_SCORE", isCorrect);
                    } catch (IndexNotFoundException e) {
                        Consumers.doTask("DISPLAY_MESSAGE", "Invalid Choice");
                    }
                    AnchorPane cardBack = exam.getCardDisplayBack();
                    Consumers.doTask("SWAP_CARD_DISPLAY", cardBack);
                    return true;
                }
    ),
    MCQ_FRONT(
            "^((?i)front)\\s*",
            new ResponseGroup[]{ResponseGroup.TEST_MCQ_BACK},
                i -> {
                    StateHolder.getState().setCurrState(StateEnum.TEST_MCQ);
                    Exam exam = ExamRunner.getCurrentExam();
                    AnchorPane cardFront = exam.getCardDisplayFront();
                    Consumers.doTask("SWAP_CARD_DISPLAY", cardFront);
                    return true;
                }
    ),

    // TEST_MCQ GROUP ----------------------------------------------------------

    JSJAVA_CODE(
            "^((?i)code)\\s*",
            new ResponseGroup[]{ResponseGroup.TEST_JSJAVA},
                i -> {
                    Exam exam = ExamRunner.getCurrentExam();
                    FlashCard card = exam.getCurrentCard();
                    if (card.getClass().getSimpleName().equals("JavascriptCard")) {
                        Consumers.doTask("LAUNCH_JS", true);
                    } else if (card.getClass().getSimpleName().equals("JavaCard")) {
                        Consumers.doTask("LAUNCH_JAVA", true);
                    }
                    return true;
                }
    ),

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
