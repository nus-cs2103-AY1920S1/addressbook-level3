package com.typee.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.typee.commons.util.PdfUtil;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;
import com.typee.model.engagement.Engagement;
import com.typee.model.person.Person;
import com.typee.model.report.Report;

/**
 * Command that generates a chosen single engagement into a pdf report format.
 */
public class PdfCommand extends Command {

    public static final String MESSAGE_USAGE = "pdf i/1 to/ Harry from/ Luna";

    public static final String COMMAND_WORD = "pdf";

    public static final String MESSAGE_SUCCESS = "Engagement Report successfully generated.";

    public static final String MESSAGE_INDEX_INVALID = "Engagement list index you entered is not valid. "
            + "Please try again";

    public static final String MESSAGE_DOCUMENT_INVALID = "Error in handling the pdf document. ";

    private int engagementListIndex;

    private Person to;

    private Person from;

    private Report report;

    public PdfCommand(int engagementListIndex, Person to, Person from) {
        this.engagementListIndex = engagementListIndex;
        this.to = to;
        this.from = from;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Engagement> engagementList = new ArrayList<Engagement>(model.getFilteredEngagementList());
        boolean isIndexValid = validateEngagementListIndex(engagementList.size());

        if (isIndexValid) {
            //generate pdf
            Engagement engagement = engagementList.get(engagementListIndex - 1);
            report = new Report(engagement, to, from);
            try {
                PdfUtil.generateReport(report);
            } catch (IOException | DocumentException e) {
                throw new CommandException(MESSAGE_DOCUMENT_INVALID + e.getMessage());
            }
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
