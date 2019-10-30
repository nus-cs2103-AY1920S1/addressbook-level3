package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.Page;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class DiaryListPanel extends UiPart<Region> {
    private static final String FXML = "DiaryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DiaryListPanel.class);

    @FXML
    private ListView<Diary> diaryListView;

    @FXML
    private HBox pageViewPanel;

    @FXML
    private ListView<Page> pageListView;

    @FXML
    private Label diaryHeader;

    private ObservableList<Diary> diaryList;

    public DiaryListPanel(ObservableList<Diary> diaryList, int targetIndex) {
        super(FXML);

        this.diaryList = diaryList;
        diaryListView.setItems(diaryList);
        diaryListView.setCellFactory(listView -> new DiaryListViewCell());

        // Sets the name of the diary
        diaryHeader.setText(diaryList.get(targetIndex).getDiaryName().fullName);
        initializePageListView(targetIndex);
    }

    /**
     * Initialises Page ScrollPane Config
     * Gives the overview of pages in that specified diary
     */
    void initializePageListView(int targetIndex) {

        ObservableList<Page> pageList = diaryList.get(targetIndex).getPages();

        // Sets the name of the diary
        diaryHeader.setText(diaryList.get(targetIndex).getDiaryName().fullName);

        pageListView.setItems(pageList);
        pageListView.setCellFactory(listView -> new PageListViewCell());

    }


    /**
     * Display inner components within Diary Panel.
     * Make use of boolean variables to decide which components to show/hide.
     */
    private void showPanels(boolean isShowDiaryList, boolean isShowPageViewPanel) {
        diaryListView.setVisible(isShowDiaryList);
        diaryListView.setManaged(isShowDiaryList);

        pageViewPanel.setVisible(isShowPageViewPanel);
        pageViewPanel.setManaged(isShowPageViewPanel);
    }

    /**
     * Switch view within Diary Panel.
     */
    @FXML
    void handleSwitch(String type) {
        String[] typeArr = type.split("-", 2);
        switch (typeArr[0]) {
        case "all":
            showPanels(true, true);
            break;
        case "update":
            initializePageListView(Integer.parseInt(typeArr[1]));
            showPanels(true, true);
            break;
        default:
            throw new AssertionError("Something's Wrong! Invalid Diary Records page type!");
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Diary} using a {@code DiaryCard}.
     */
    class DiaryListViewCell extends ListCell<Diary> {

        @Override
        protected void updateItem(Diary diary, boolean empty) {
            super.updateItem(diary, empty);

            if (empty || diary == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DiaryCard(diary, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Page} using a {@code PageCard}.
     */
    class PageListViewCell extends ListCell<Page> {
        @Override
        protected void updateItem(Page page, boolean empty) {
            super.updateItem(page, empty);
            if (empty || page == null) {
                setGraphic(null);
                setText(null);;
            } else {
                setGraphic(new PageCard(page, getIndex() + 1).getRoot());
            }
        }


    }

}
