package seedu.address.ui.diary;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.NavigationSidebarLeft;
import seedu.address.ui.components.NavigationSidebarRight;
import seedu.address.ui.template.PageWithSidebar;

public class DiaryPage extends PageWithSidebar<BorderPane> {
    private static final String FXML = "diary/DiaryPage.fxml";

    @FXML
    private VBox diaryTextPlaceholder;
    @FXML
    private VBox diaryRightPlaceholder;
    @FXML
    private ButtonBar dayIndexButtonBar;

    public DiaryPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        initPlaceholders();
    }

    private void initPlaceholders() {
        DiaryEntry currentEntry = model.getPageStatus().getDiaryEntry();
        if (currentEntry != null) {
            DiaryGallery diaryGallery = new DiaryGallery(currentEntry.getPhotoList());
            diaryRightPlaceholder.getChildren().add(diaryGallery.getRoot());
        }

        diaryTextPlaceholder.getChildren().add(new Label("No text"));
    }

    @Override
    public void fillPage() {

    }
}
