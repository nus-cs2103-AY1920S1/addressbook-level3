package com.typee.logic.commands;

import java.util.ArrayList;
import java.util.List;

import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;
import com.typee.model.engagement.Engagement;

/**
 * Command that generates a chosen single engagement into a pdf report format.
 */
public class PdfCommand extends Command {

    public static final String COMMAND_WORD = "pdf";

    public static final String MESSAGE_SUCCESS = "Engagement Report successfully generated.";

    public static final String MESSAGE_INDEX_INVALID = "Engagement list index you entered is not valid. "
            + "Please try again";

    private int engagementListIndex;

    public PdfCommand(int engagementListIndex) {
        this.engagementListIndex = engagementListIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Engagement> engagementList = new ArrayList<Engagement>(model.getFilteredEngagementList());
        boolean isIndexValid = validateEngagementListIndex(engagementList.size());

        if (isIndexValid) {
            //generate pdf
            Engagement engagement = engagementList.get(engagementListIndex);

            return new CommandResult(MESSAGE_SUCCESS);
        }
        throw new CommandException(MESSAGE_INDEX_INVALID);
    }

    /**
     * Validates if the index is in the boundary of the list size.
     */
    private boolean validateEngagementListIndex(int engagementListSize) {
        if (engagementListIndex > 0 && engagementListIndex <= engagementListSize) {
            return true;
        }
        return false;
    }
}
