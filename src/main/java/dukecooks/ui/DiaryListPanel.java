package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.Page;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Panel containing the list of persons.
 */
public class DiaryListPanel extends UiPart<Region> {
    private static final String FXML = "DiaryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DiaryListPanel.class);

    @FXML
    private ListView<Diary> diaryListView;

    @FXML
    private StackPane pageInputPanel;

    @FXML
    private StackPane pageViewPanel;

    @FXML
    private FlowPane pageFlowPane;



    public DiaryListPanel(ObservableList<Diary> diaryList, int targetIndex) {
        super(FXML);
        diaryListView.setItems(diaryList);
        diaryListView.setCellFactory(listView -> new DiaryListViewCell());
        initializePageFlowPaneView(diaryList, targetIndex);
    }

    /**
     * Initialises Diary FlowPane Config, with a pageList
     * Gives the overview of pages in that specified diary
     */
    void initializePageFlowPaneView(ObservableList<Diary> diaryList, int targetIndex) {
        pageFlowPane.setHgap(5);
        pageFlowPane.setVgap(5);
        pageFlowPane.setPadding(new Insets(10, 10, 10, 10));

        ObservableList<Page> pageList = diaryList.get(targetIndex).getPages();

        // Creates a Page Card for Diary and adds to FlowPane
        int i = 0;
        for (Page page : pageList) {
            pageFlowPane.getChildren().add(new PageCard(page, i).getRoot());
            i++;
        }

        // add listener for new page changes
        pageList.addListener((ListChangeListener<Page>) c -> {
            pageFlowPane.getChildren().clear();
            initializePageFlowPaneView(diaryList, targetIndex);
        });
    }


    /**
     * Display inner components within Diary Panel.
     * Make use of boolean variables to decide which components to show/hide.
     */
    private void showPanels(boolean isShowDiaryList, boolean isShowPageInputPanel, boolean isShowPageViewPanel) {
        diaryListView.setVisible(isShowDiaryList);
        diaryListView.setManaged(isShowDiaryList);

        pageInputPanel.setVisible(isShowPageInputPanel);
        pageInputPanel.setManaged(isShowPageInputPanel);

        pageViewPanel.setVisible(isShowPageViewPanel);
        pageViewPanel.setManaged(isShowPageViewPanel);
    }

    /**
     * Switch view within Diary Panel.
     */
    @FXML
    void handleSwitch(String type) {
        switch (type) {
        case "all":
            showPanels(true, false, true);
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

}
