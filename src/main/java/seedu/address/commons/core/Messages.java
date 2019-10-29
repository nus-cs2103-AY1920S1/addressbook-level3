package seedu.address.commons.core;

import seedu.address.logic.commands.bio.BioCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    //---Common---
    public static final String MESSAGE_UNKNOWN_COMMAND = "Oops! Sorry, SugarMummy doesn't understand what this "
        + "command means :(";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Oops! The command you've entered appears to be in "
        + "an invalid format. \n%1$s";
    public static final String MESSAGE_INVALID_SUBARGUMENT_INDEX = "Oops! The index / indices provided in the "
        + "sub-arguments is/are invalid. Index / indices must be integers greater than or equal to 1.";
    public static final String MESSAGE_SUBARGUMENT_INDEX_OUT_OF_BOUNDS = "Oops! The index / indices provided in the "
        + "sub-arguments is/are out of bounds.";
    public static final String MESSAGE_INCONSISTENT_SUBARGUMENT_INDEX = "Oops! the use of index / indices provided in "
        + "the sub-arguments is/are inconsistent. (i.e. some prefixes of the same type have no indices while "
        + "others do)";
    public static final String MESSAGE_INVALID_PARAMETER = "Please enter correct input for %2$s!\n%1$s";
    public static final String MESSAGE_ARGUMENTS_MUST_BE_EMPTY = "Oops! This command cannot have any arguments.";

    //---Record---
    public static final String MESSAGE_INVALID_RECORD_DISPLAYED_INDEX = "The record index provided is invalid";
    public static final String MESSAGE_RECORDS_LISTED_OVERVIEW = "%1$d persons listed!";

    //---FoodList---
    public static final String MESSAGE_EMPTY_FOOD_LIST = "";

    //---Bio---
    public static final String MESSAGE_NO_BIO_FOUND = "It looks like you have not set your biography. :( "
        + "Your biography could be crucial information especially in times of an emergency. "
        + "Please enter the " + BioCommand.COMMAND_WORD + " command as soon as possible to set your biography, "
        + "alright?";

    //---Aesthetics---
    public static final String MESSAGE_UNABLE_TO_LOAD_IMAGE = "Oops! Unfortunately, I'm unable to load or access "
        + "the image via the specified path. Please check if the file at the given destination is correct.";
    public static final String MESSAGE_UNABLE_TO_LOAD_REFERENCES = "I'm unable to load the following references and "
        + "as such have removed them so they will not cause any errors in the program:\n";
    public static final String MESSAGE_BACKGROUND_COLOUR_NO_ARGS_REQUIREMENT = "Oops! For the background command, "
        + "arguments are only allowed for background images and not background colours. "
        + "Scroll to view details on usage.\n%1$s";
    public static final String MESSAGE_INVALID_BACKGROUND_SIZE = "Oops! The background size settings you entered "
        + "does not appear to match any known settings. Scroll to view usage details. \n%1$s";
    public static final String MESSAGE_INVALID_BACKGROUND_REPEAT = "Oops! The background repeat settings you entered "
        + "does not appear to match any known settings. Scroll to view usage details. \n%1$s";
}
