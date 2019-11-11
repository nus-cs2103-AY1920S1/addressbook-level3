package seedu.address.ui.question;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.stage.Stage;
import seedu.address.ui.QuestionListPanel;
import seedu.address.ui.SlideshowWindow;

public class QuestionFxmlTest {

    @Test
    public void initialiseQuestionListPanel_unableToFindFxmlFile_throwsExceptionInInitializerError() {
        assertThrows(ExceptionInInitializerError.class, ()
            -> new QuestionListPanel(FXCollections.observableArrayList(), false));
    }

    @Test
    public void initialiseSlideshowWindow_unableToFindFxmlFile_throwsExceptionInInitializerError() {
        assertThrows(ExceptionInInitializerError.class, ()
            -> new SlideshowWindow(new Stage(), null));
    }
}
