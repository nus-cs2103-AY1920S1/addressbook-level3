//@@author LeowWB

package seedu.address.commons.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.util.FilePath;

public class ExportUtil {

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
