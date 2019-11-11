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

    public static final Prefix PREFIX_PRINT = new Prefix("print/");

    // EVENT PREFIX
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("eventName/");
    public static final Prefix PREFIX_START_DATETIME = new Prefix("startDateTime/");
    public static final Prefix PREFIX_END_DATETIME = new Prefix("endDateTime/");
    public static final Prefix PREFIX_RECUR = new Prefix("recur/");
    public static final Prefix PREFIX_COLOR = new Prefix("color/");
    public static final Prefix PREFIX_GET_INDEX = new Prefix("indexOf/");
    public static final Prefix PREFIX_VIEW = new Prefix("view");
    public static final Prefix PREFIX_DIRECTORY = new Prefix("directory/");
    public static final Prefix PREFIX_VIEW_MODE = new Prefix("scheduleMode/");
    public static final Prefix PREFIX_VIEW_DATE = new Prefix("targetDate/");
    public static final Prefix PREFIX_SCREENSHOT = new Prefix("screenshot");
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

    // NOTE PREFIX
    public static final Prefix PREFIX_NOTE = new Prefix("note/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("desc/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("priority/");
    public static final Prefix PREFIX_SORT = new Prefix("sort");
    // END OF NOTE PREFIX

    // TAG PREFIX
    public static final Prefix PREFIX_INDEX = new Prefix("index/");

    //START OF STATISTICS PREFIX
    public static final Prefix PREFIX_FILEPATH = new Prefix("file/");
    //END OF STATISTICS PREFIX

    public static final Prefix PREFIX_ADD = new Prefix("add");
    public static final Prefix PREFIX_EXPORT = new Prefix("export");
    public static final Prefix PREFIX_LIST = new Prefix("list");
    public static final Prefix PREFIX_DELETE = new Prefix("delete");
    public static final Prefix PREFIX_FIND = new Prefix("find/");

    public static final Prefix PREFIX_GROUP = new Prefix("group/");
    public static final Prefix PREFIX_GROUP_ID = new Prefix("groupID/");
    public static final Prefix PREFIX_STUDENT_NUMBER = new Prefix("studentNumber/");
    public static final Prefix PREFIX_GROUP_INDEX_NUMBER = new Prefix("groupIndexNumber/");

    public static final Prefix PREFIX_QUIZ = new Prefix("quiz/");
    public static final Prefix PREFIX_MODE_AUTO = new Prefix("auto");
    public static final Prefix PREFIX_MODE_MANUAL = new Prefix("manual");
    public static final Prefix PREFIX_QUIZ_ID = new Prefix("quizID/");
    public static final Prefix PREFIX_NUM_QUESTIONS = new Prefix("numQuestions/");
    public static final Prefix PREFIX_QUESTION_NUMBER = new Prefix("questionNumber/");
    public static final Prefix PREFIX_QUIZ_QUESTION_NUMBER = new Prefix("quizQuestionNumber/");
    public static final Prefix PREFIX_SHOW_QUESTIONS = new Prefix("showQuestions");
    public static final Prefix PREFIX_SHOW_ANSWERS = new Prefix("showAnswers");

}
