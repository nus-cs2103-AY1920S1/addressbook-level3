//@@author LeowWB

package seedu.address.commons.util;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.util.FilePath;

public class ExportUtil {

    public static void exportFlashCards(List<FlashCard> cards, FilePath filePath) {
        XWPFDocument doc = new XWPFDocument();

        for (FlashCard card : cards) {
            Question question = card.getQuestion();
            Answer answer = card.getAnswer();

            XWPFParagraph paragraph = doc.createParagraph();

            XWPFRun questionRun = paragraph.createRun();
            questionRun.setText(question.toString());
            questionRun.setBold(true);

            XWPFRun answerRun = paragraph.createRun();
            answerRun.setText(answer.toString());
        }

        try {
            FileOutputStream out = new FileOutputStream(filePath.toString());
            doc.write(out);
            out.close();
            doc.close();
        }
        catch (Exception e) {

        }
    }
}
