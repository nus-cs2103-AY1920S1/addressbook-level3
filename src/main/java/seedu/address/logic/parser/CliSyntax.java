package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    // ADDRESS BOOK PREFIX
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("ad/");
    // END OF ADDRESS BOOK PREFIX

    // EVENT PREFIX
    public static final Prefix PREFIX_EVENT = new Prefix("event/");
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("eventName/");
    public static final Prefix PREFIX_START_DATETIME = new Prefix("startDateTime/");
    public static final Prefix PREFIX_END_DATETIME = new Prefix("endDateTime/");
    public static final Prefix PREFIX_RECUR = new Prefix("recur/");
    public static final Prefix PREFIX_COLOR = new Prefix("color/");
    public static final Prefix PREFIX_GET_INDEX = new Prefix("indexOf/");
    // END OF EVENT PREFIX

    // QUESTION PREFIX
    public static final Prefix PREFIX_QUESTION = new Prefix("question/");
    public static final Prefix PREFIX_ANSWER = new Prefix("answer/");
    public static final Prefix PREFIX_TYPE = new Prefix("type/");
    public static final Prefix PREFIX_OPTIONA = new Prefix("a/");
    public static final Prefix PREFIX_OPTIONB = new Prefix("b/");
    public static final Prefix PREFIX_OPTIONC = new Prefix("c/");
    public static final Prefix PREFIX_OPTIOND = new Prefix("d/");
    public static final Prefix PREFIX_SLIDESHOW = new Prefix("slideshow");
    // END OF QUESTION PREFIX

    // MARK PREFIX
    public static final Prefix PREFIX_UNMARK = new Prefix("unmark");
    // END OF MARK PREFIX

    // STUDENT PREFIX
    public static final Prefix PREFIX_STUDENT = new Prefix("name/");
    public static final Prefix PREFIX_TAG = new Prefix("tag/");
    // END OF STUDENT PREFIX

    // TAG PREFIX
    public static final Prefix PREFIX_INDEX = new Prefix("index/");

    // NOTE PREFIX
    public static final Prefix PREFIX_NOTE = new Prefix("note/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("desc/");
    // END OF NOTE PREFIX

    //START OF STATISTICS PREFIX
    public static final Prefix PREFIX_METHOD = new Prefix("method/");
    public static final Prefix PREFIX_FILEPATH = new Prefix("file/");
    //END IF STATISTICS PREFIX

    public static final Prefix PREFIX_ADD = new Prefix("add");
    public static final Prefix PREFIX_EXPORT = new Prefix("export");
    public static final Prefix PREFIX_LIST = new Prefix("list");
    public static final Prefix PREFIX_DELETE = new Prefix("delete");

    public static final Prefix PREFIX_GROUP = new Prefix("group/");
    public static final Prefix PREFIX_GROUP_ID = new Prefix("groupID/");
    public static final Prefix PREFIX_STUDENT_NUMBER = new Prefix("studentNumber/");
    public static final Prefix PREFIX_GROUP_INDEX_NUMBER = new Prefix("groupIndexNumber/");

    public static final Prefix PREFIX_QUIZ = new Prefix("quiz/");
    public static final Prefix PREFIX_MODE_AUTO = new Prefix("auto/");
    public static final Prefix PREFIX_MODE_MANUAL = new Prefix("manual/");
    public static final Prefix PREFIX_QUIZ_ID = new Prefix("quizID/");
    public static final Prefix PREFIX_NUM_QUESTIONS = new Prefix("numQuestions/");
    public static final Prefix PREFIX_QUESTION_NUMBER = new Prefix("questionNumber/");
    public static final Prefix PREFIX_QUIZ_QUESTION_NUMBER = new Prefix("quizQuestionNumber/");

}
