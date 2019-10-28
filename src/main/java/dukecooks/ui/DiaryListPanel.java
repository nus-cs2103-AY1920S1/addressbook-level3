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



    public DiaryListPanel(ObservableList<Diary> diaryList) {
        super(FXML);
        diaryListView.setItems(diaryList);
        diaryListView.setCellFactory(listView -> new DiaryListViewCell());
        initializePageFlowPaneView(diaryList);
    }

    /**
     * Initialises FlowPane for pages
     * Gives the overview of pages
     */
    void initializePageFlowPaneView(ObservableList<Diary> diaryList) {
        pageFlowPane.setHgap(5);
        pageFlowPane.setVgap(5);
        pageFlowPane.setPadding(new Insets(10, 10, 10, 10));

        // Creates a Page Card for each Diary record and adds to FlowPane
        int i = 0;
        for (Diary diary : diaryList) {
            // Check if diary page list is empty
            if (!diary.getPages().isEmpty()) {
                Page currentPage = diary.getPages().get(i);
                pageFlowPane.getChildren().add(new PageCard(currentPage, i).getRoot());
                i++;
            } else {
                // Create empty page card?
            }
        }

        //add listener for new changes
        diaryList.addListener((ListChangeListener<Diary>) c -> {
            pageFlowPane.getChildren().clear();
            int x = 0;
            for (Diary r: diaryList) {
                // Check if diary page list is empty
                if (!r.getPages().isEmpty()) {
                    Page currentPage = r.getPages().get(x);
                    pageFlowPane.getChildren().add(new PageCard(currentPage, x).getRoot());
                    x++;
                } else {
                    // Create empty page card?
                }
            }
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
