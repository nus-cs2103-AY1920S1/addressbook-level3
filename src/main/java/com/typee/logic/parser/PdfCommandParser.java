package com.typee.logic.parser;

import com.typee.logic.commands.PdfCommand;
import com.typee.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of the PdfCommand
 * and returns an  PdfCommand object for execution.
 */
public class PdfCommandParser implements Parser<PdfCommand> {
    public static final String INPUT_INVALID_NUMBER_FORMAT = "Input is in invalid number format. Please try again. ";
    @Override
    public PdfCommand parse(String userInput) throws ParseException {
        try {
            int engagementListIndex = Integer.valueOf(userInput.trim());
            return new PdfCommand(engagementListIndex);
        } catch (NumberFormatException e) {
            throw new ParseException(INPUT_INVALID_NUMBER_FORMAT + e.getMessage());
        }
    }
}
