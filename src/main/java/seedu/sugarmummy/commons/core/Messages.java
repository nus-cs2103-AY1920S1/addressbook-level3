package seedu.sugarmummy.commons.core;

import seedu.sugarmummy.logic.commands.biography.BioCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    //---Common---
    public static final String MESSAGE_UNKNOWN_COMMAND = "Oops! Sorry, SugarMummy doesn't understand what this "
            + "command means :(";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Oops! The command you've entered appears to be in "
            + "an invalid format. \n%1$s";
    public static final String MESSAGE_ARGUMENTS_MUST_BE_EMPTY = "Oops! This command cannot have any arguments.";
    public static final String MESSAGE_INVALID_PARAMETER = "Please enter correct input for %1$s!\n%2$s\n";

    //---Record---
    public static final String MESSAGE_INVALID_RECORD_DISPLAYED_INDEX = "The record index provided is invalid";

    //---Statistics---

    public static final String MESSAGE_INVALID_RECORD_TYPE = "System does not accommodate such a record type.";
    public static final String MESSAGE_INVALID_AVERAGE_TYPE = "System does not accommodate such a average type.";
    public static final String MESSAGE_POSSIBLE_RECORD_TYPE = "RECORD_TYPE is \"BLOODSUGAR\" or \"BMI\"";
    public static final String MESSAGE_POSSIBLE_AVERAGE_TYPE = "AVERAGE_TYPE is \"daily\", \"weekly\" or \"monthly\"";
    public static final String MESSAGE_POSSIBLE_COUNT = "COUNT takes integer value between 1 and 12 inclusive.";

    //---Bio---
    public static final String MESSAGE_NO_BIO_FOUND = "It looks like you have not set your biography. :( "
            + "Your biography could be crucial information especially in times of an emergency. "
            + "Please enter the " + BioCommand.COMMAND_WORD + " command as soon as possible to set your biography, "
            + "alright?";
    public static final String MESSAGE_ENSURE_ONLY_ONE_PREFIX_PLURAL = "Please ensure there is at most one prefix "
            + "each for the following prefixes that you keyed in: ";
    public static final String MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR = "Please ensure there is at most one prefix "
            + "for this prefix that you keyed in: ";
    public static final String MESSAGE_INVALID_SUBARGUMENT_INDEX = "Oops! The index / indices provided in the "
            + "sub-arguments is/are invalid. Index / indices must be integers greater than or equal to 1.";
    public static final String MESSAGE_SUBARGUMENT_INDEX_OUT_OF_BOUNDS = "Oops! The index / indices provided in the "
            + "sub-arguments is/are out of bounds.";
    public static final String MESSAGE_INCONSISTENT_SUBARGUMENT_INDEX = "Oops! the use of index / indices provided in "
            + "the sub-arguments is/are inconsistent. (i.e. some prefixes of the same type have no indices while "
            + "others do)";

    //---Image Referencing (both Bio and Aesthetics)---
    public static final String MESSAGE_UNABLE_TO_LOAD_IMAGE = "Oops! Unfortunately, I'm unable to load or access "
            + "the image via the specified path. Please check if the file at the given destination is correct.";
    public static final String MESSAGE_UNABLE_TO_LOAD_REFERENCES = "I'm unable to load the following references and "
            + "as such have removed them so they will not cause any errors in the program:\n";

    //---Aesthetics---
    public static final String MESSAGE_USE_ONLY_ONE_FONT_COLOUR_PREFIX = "Oops! You have used more than one "
            + "fontcolour prefix or types of fontcolour prefixes (eg. fontcolor). Please use at most one.";
    public static final String MESSAGE_USE_ONLY_ONE_BACKGROUND_PREFIX = "Oops! You have used more than one "
            + "background prefix. Please use at most one.";
    public static final String MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT = "Oops! For the background command, "
            + "background arguments are only allowed for background images and not background colours. "
            + "Scroll to view details on usage.\n%1$s";
    public static final String MESSAGE_INVALID_BACKGROUND_SIZE = "Oops! The background size settings you entered "
            + "does not appear to match any known settings. Scroll to view usage details. \n%1$s";
    public static final String MESSAGE_INVALID_BACKGROUND_REPEAT = "Oops! The background repeat settings you entered "
            + "does not appear to match any known settings. Scroll to view usage details. \n%1$s";

    //---Achievements---
    public static final String MESSAGE_ACHIEVEMENTS_ATTAINED_AND_LOST = "You have attained new achievement(s) and "
            + "lost one / some too! Key in the \'achvm\' command to view your updated list of achievements. =)";
    public static final String MESSAGE_ACHIEVEMENTS_ATTAINED = "You have attained new achievement(s)! Key in "
            + "the \'achvm\' command to view your updated list of achievements. =)";
    public static final String MESSAGE_ACHIEVEMENTS_LOST = "Oh no, you have lost one / some achievement(s) :( Key in "
            + "the \'achvm\' command to view your updated list of achievements.";

    //--Calendar--
    public static final String MESSAGE_INVALID_EVENT_ENDING_TIME = "Oops! The ending date time of an event should come"
            + " after starting date time.";
}
