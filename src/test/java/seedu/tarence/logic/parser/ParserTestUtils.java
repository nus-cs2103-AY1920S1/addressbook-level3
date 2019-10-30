package seedu.tarence.logic.parser;

import static seedu.tarence.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_SCORE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;


/**
 * Constants for parser tests.
 */
public class ParserTestUtils {

    public static final String VALID_ASSIGN_NAME = "Assignment 1";
    public static final String VALID_END_DATE = "10-10-11 1000";
    public static final String VALID_EVENT_NAME = "Some random valid event name";
    public static final int VALID_INDEX = 1;
    public static final String VALID_MOD_CODE = "ZA9876X";
    public static final int VALID_SCORE = 10;
    public static final String VALID_START_DATE = "10-10-10 1000";
    public static final String VALID_TUT_NAME = "Studio 99";

    public static final int INVALID_INDEX = 0;

    public static final String ASSIGN_NAME_DESC = " " + PREFIX_NAME + VALID_ASSIGN_NAME;
    public static final String END_DATE_DESC = " " + PREFIX_END_DATE + VALID_END_DATE;
    public static final String EVENT_NAME_DESC = " " + PREFIX_NAME + VALID_EVENT_NAME;
    public static final String INDEX_DESC = " " + PREFIX_INDEX + VALID_INDEX;
    public static final String MOD_CODE_DESC = " " + PREFIX_MODULE + VALID_MOD_CODE;
    public static final String SCORE_DESC = " " + PREFIX_SCORE + VALID_SCORE;
    public static final String START_DATE_DESC = " " + PREFIX_START_DATE + VALID_START_DATE;
    public static final String TUT_NAME_DESC = " " + PREFIX_TUTORIAL_NAME + VALID_TUT_NAME;

    public static final String INVALID_INDEX_DESC = " " + PREFIX_INDEX + INVALID_INDEX;
    public static final String INVALID_SCORE_DESC_NEGATIVE = " " + PREFIX_SCORE + "-1";
    public static final String INVALID_SCORE_DESC_NON_NUMBER = " " + PREFIX_SCORE + "abcde";
    public static final String INVALID_START_DATE_DESC = " " + PREFIX_START_DATE + "some random string";
}
