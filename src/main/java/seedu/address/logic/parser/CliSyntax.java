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
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    // END OF ADDRESS BOOK PREFIX

    // QUESTION PREFIX
    public static final Prefix PREFIX_QUESTION = new Prefix("question/");
    public static final Prefix PREFIX_ANSWER = new Prefix("answer/");
    public static final Prefix PREFIX_TYPE = new Prefix("type/");
    // END OF QUESTION PREFIX

    // STUDENT PREFIX
    public static final Prefix PREFIX_STUDENT = new Prefix("student/");
    // END OF STUDENT PREFIX

    public static final Prefix PREFIX_NOTE = new Prefix("note/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("desc/");

    public static final Prefix PREFIX_LIST = new Prefix("list");
    public static final Prefix PREFIX_DELETE = new Prefix("delete");

    public static final Prefix PREFIX_QUIZ = new Prefix("quiz/");
    public static final Prefix PREFIX_MODE_AUTO = new Prefix("auto/");
    public static final Prefix PREFIX_MODE_MANUAL = new Prefix("manual/");
    public static final Prefix PREFIX_QUIZ_ID = new Prefix("quizID/");
    public static final Prefix PREFIX_NUM_QUESTIONS = new Prefix("numQuestions/");
    public static final Prefix PREFIX_QUESTION_NUMBER = new Prefix("questionNumber/");
    public static final Prefix PREFIX_QUIZ_QUESTION_NUMBER = new Prefix("quizQuestionNumber/");

}
