package seedu.address.model.pdfmanager.exceptions;

/**
 * Represents an error that relates to a PDF creation and saving.
 */
public class PdfNoTaskToDisplayException extends RuntimeException {
    public PdfNoTaskToDisplayException(String message) {
        super(message);
    }
}
