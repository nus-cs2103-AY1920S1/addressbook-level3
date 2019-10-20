package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.AddPolicyTagCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.logic.commands.DeletePolicyTagCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.SuggestionSwitchCommand;
import seedu.address.logic.commands.UnassignPolicyCommand;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    // Person prefix
    public static final Prefix PREFIX_NAME = new Prefix("n/", AddCommand.COMMAND_WORD,
            AddPolicyCommand.COMMAND_WORD, EditCommand.COMMAND_WORD, EditPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_NRIC = new Prefix("ic/", AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD);
    public static final Prefix PREFIX_PHONE = new Prefix("p/", AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD);
    public static final Prefix PREFIX_EMAIL = new Prefix("e/", AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD);
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/", AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD);
    public static final Prefix PREFIX_DATE_OF_BIRTH = new Prefix("dob/", AddCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD);


    public static final Prefix PREFIX_POLICY = new Prefix("pol/", AssignPolicyCommand.COMMAND_WORD,
            UnassignPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_TAG = new Prefix("t/", AddTagCommand.COMMAND_WORD,
            AddPolicyTagCommand.COMMAND_WORD, DeleteTagCommand.COMMAND_WORD, DeletePolicyTagCommand.COMMAND_WORD);

    // Policy prefix
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/", AddPolicyCommand.COMMAND_WORD,
            EditPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_COVERAGE = new Prefix("c/", AddPolicyCommand.COMMAND_WORD,
            EditPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_DAYS = new Prefix("days/", AddPolicyCommand.COMMAND_WORD,
            EditPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_MONTHS = new Prefix("months/", AddPolicyCommand.COMMAND_WORD,
            EditPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_YEARS = new Prefix("years/", AddPolicyCommand.COMMAND_WORD,
            EditPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_PRICE = new Prefix ("p/", AddPolicyCommand.COMMAND_WORD,
            EditPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_START_AGE = new Prefix ("sa/", AddPolicyCommand.COMMAND_WORD,
            EditPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_END_AGE = new Prefix("ea/", AddPolicyCommand.COMMAND_WORD,
            EditPolicyCommand.COMMAND_WORD);
    public static final Prefix PREFIX_CRITERIA = new Prefix("cr/", AddPolicyCommand.COMMAND_WORD,
            EditPolicyCommand.COMMAND_WORD);

    // To be used internally for suggestion command
    public static final Prefix PREFIX_COMMAND_WORD = new Prefix("command/");
    public static final Prefix PREFIX_ARGUMENTS = new Prefix("arguments/");

    // To be used by user to switch suggestions on and off
    public static final Prefix PREFIX_ON = new Prefix("ON/", SuggestionSwitchCommand.COMMAND_WORD);
    public static final Prefix PREFIX_OFF = new Prefix("OFF/", SuggestionSwitchCommand.COMMAND_WORD);

    // TODO: Add in alternative commands
    // Visual Representation prefix
    public static final Prefix PREFIX_INDICATOR = new Prefix("i/");

    public static final Prefix[] USER_PREFIXES = new Prefix[]{
        PREFIX_NAME, PREFIX_NRIC, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DATE_OF_BIRTH, PREFIX_POLICY,
        PREFIX_TAG, PREFIX_DESCRIPTION, PREFIX_COVERAGE, PREFIX_DAYS, PREFIX_MONTHS, PREFIX_YEARS, PREFIX_PRICE,
        PREFIX_START_AGE, PREFIX_END_AGE, PREFIX_CRITERIA, PREFIX_ON, PREFIX_OFF, PREFIX_INDICATOR
    };
}
