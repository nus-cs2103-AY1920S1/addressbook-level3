//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
        requireAllNonNull(cards, documentPath);
        assert DocumentPath.isValid(documentPath.toString());
        assert cards.size() > 0;

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
        requireAllNonNull(card, doc);

        Question question = card.getQuestion();
        Answer answer = card.getAnswer();
        XWPFParagraph paragraph = doc.createParagraph();

        addRun(paragraph, question.toString(), true);
        addLineBreak(paragraph);
        addRun(paragraph, answer.toString(), false);
    }

    /**
     * Adds a run to the given XWPFParagraph, containing the specified text and with the specified boldface.
     *
     * @param paragraph Paragraph that we will add a run to.
     * @param text Text to add in paragraph
     * @param isBold Whether the run should be bold
     */
    private static void addRun(XWPFParagraph paragraph, String text, boolean isBold) {
        requireAllNonNull(paragraph, text);

        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setBold(isBold);
    }

    /**
     * Adds a line break to the given XWPFParagraph.
     *
     * @param paragraph XWPFParagraph to add line break to.
     */
    private static void addLineBreak(XWPFParagraph paragraph) {
        requireNonNull(paragraph);

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
        requireAllNonNull(doc, documentPath);
        assert DocumentPath.isValid(documentPath.toString());

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
