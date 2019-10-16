package seedu.address.diaryfeature.diaryUI;


import javafx.scene.Scene;
import seedu.address.ui.Page;
import seedu.address.ui.PageType;

public class diaryPageHolder implements Page {
    private final static PageType diaryPage = PageType.DIARY;


    @Override
    public Scene getScene() {
        return diaryScene;
    }

    @Override
    public PageType getPageType() {
        return diaryPage;
    }
}
