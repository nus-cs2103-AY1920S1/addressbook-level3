//@@author LeowWB

package seedu.address.model.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;

/**
 * Utility class that handles exporting of FlashCards to an external document file.
 */
public class DocumentExportUtil {

    /**
     * Exports a List of FlashCards to a file at the given DocumentPath.
     *
     * @param cards List of FlashCards
     * @param documentPath DocumentPath to export the FlashCards to
     * @throws IOException If an error arises in writing to the File.
     */
    public static void exportFlashCardsToDocument(List<FlashCard> cards, DocumentPath documentPath) throws IOException {
        assert DocumentPath.isValid(documentPath.toString());

        XWPFDocument doc = new XWPFDocument();

        for (FlashCard card : cards) {
            addFlashCardToDocument(card, doc);
        }

        try {
            writeDocumentToFile(doc, documentPath);
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Adds a String representation of the given FlashCard to the given XWPFDocument.
     * Also handles related concerns (such as formatting and line breaks).
     *
     * @param card The FlashCard to add to the XWPFDocument.
     * @param doc The XWPFDocument to which the FlashCard will be added.
     */
    private static void addFlashCardToDocument(FlashCard card, XWPFDocument doc) {
        Question question = card.getQuestion();
        Answer answer = card.getAnswer();
        XWPFParagraph paragraph = doc.createParagraph();

        addRun(paragraph, question.toString(), true);
        addLineBreak(paragraph);
        addRun(paragraph, answer.toString(), false);
    }

    private static void addRun(XWPFParagraph paragraph, String text, boolean isBold) {
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(isBold);
    }

    private static void addLineBreak(XWPFParagraph paragraph) {
        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
    }

    /**
     * Writes a given XWPFDocument to a File at the given DocumentPath.
     *
     * @param doc XWPFDocument to write.
     * @param documentPath File to write the document to.
     * @throws IOException If there is an error in writing to the File.
     */
    private static void writeDocumentToFile(XWPFDocument doc, DocumentPath documentPath) throws IOException {
        try {
            FileOutputStream out = new FileOutputStream(documentPath.toString());
            doc.write(out);
            out.close();
            doc.close();
        } catch (IOException e) {
            throw e;
        }
    }
}
