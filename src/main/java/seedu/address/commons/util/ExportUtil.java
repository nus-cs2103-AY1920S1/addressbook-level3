//@@author LeowWB

package seedu.address.commons.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import seedu.address.model.FilePath;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;

/**
 * Utility class that handles exporting of FlashCards to an external file.
 */
public class ExportUtil {

    /**
     * Exports a List of FlashCards to a file at the given FilePath.
     *
     * @param cards List of FlashCards
     * @param filePath FilePath to export the FlashCards to
     * @throws IOException If an error arises in writing to the File.
     */
    public static void exportFlashCards(List<FlashCard> cards, FilePath filePath) throws IOException {
        XWPFDocument doc = new XWPFDocument();

        for (FlashCard card : cards) {
            Question question = card.getQuestion();
            Answer answer = card.getAnswer();
            XWPFParagraph paragraph = doc.createParagraph();

            addRun(paragraph, question.toString(), true);
            addLineBreak(paragraph);
            addRun(paragraph, answer.toString(), false);
        }

        try {
            writeDocumentToFile(doc, filePath);
        } catch (IOException e) {
            throw e;
        }
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
     * Writes a given XWPFDocument to a File at the given FilePath.
     *
     * @param doc XWPFDocument to write.
     * @param filePath File to write the document to.
     * @throws IOException If there is an error in writing to the File.
     */
    private static void writeDocumentToFile(XWPFDocument doc, FilePath filePath) throws IOException {
        try {
            FileOutputStream out = new FileOutputStream(filePath.toString());
            doc.write(out);
            out.close();
            doc.close();
        } catch (IOException e) {
            throw e;
        }
    }
}
