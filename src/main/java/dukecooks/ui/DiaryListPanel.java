package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.logic.Logic;
import dukecooks.logic.commands.diary.AddPageCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.logic.parser.DukeCooksParser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.diary.AddPageCommandParser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.Page;
import dukecooks.model.diary.components.Title;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Panel containing the diary feature.
 */
public class DiaryListPanel extends UiPart<Region> {
    private static final String FXML = "DiaryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DiaryListPanel.class);

    @FXML
    private ListView<Diary> diaryListView;

    @FXML
    private HBox pageViewPanel;

    @FXML
    private HBox pageInputPanel;

    @FXML
    private TextField pageTitleTextField;

    @FXML
    private TextField pageTypeTextField;

    @FXML
    private TextField pageDescriptionTextField;

    @FXML
    private TextField pageImageTextField;

    @FXML
    private TextField diaryNameTextField;

    @FXML
    private ListView<Page> pageListView;

    @FXML
    private Label diaryHeader;

    private ObservableList<Diary> diaryList;
    private MainWindow mainWindow;

    public DiaryListPanel(ObservableList<Diary> diaryList, MainWindow mainWindow, int targetIndex) {
        super(FXML);

        this.diaryList = diaryList;
        this.mainWindow = mainWindow;
        diaryListView.setItems(diaryList);
        diaryListView.setCellFactory(listView -> new DiaryListViewCell());

        // Sets the name of the diary
        diaryHeader.setText(diaryList.get(targetIndex).getDiaryName().fullName);
        initializePageListView(targetIndex);
    }

    /**
     * Initialises Page List View Config
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
    private void showPanels(boolean isShowDiaryList, boolean isShowPageViewPanel, boolean isShowPageInputPanel) {
        diaryListView.setVisible(isShowDiaryList);
        diaryListView.setManaged(isShowDiaryList);

        pageViewPanel.setVisible(isShowPageViewPanel);
        pageViewPanel.setManaged(isShowPageViewPanel);

        pageInputPanel.setVisible(isShowPageInputPanel);
        pageInputPanel.setManaged(isShowPageInputPanel);
    }

    /**
     * Switch view within Diary Panel.
     */
    @FXML
    void handleSwitch(String type) {
        String[] typeArr = type.split("-", 2);
        switch (typeArr[0]) {
        case "all":
            showPanels(true, true, false);
            break;
        case "update":
            initializePageListView(Integer.parseInt(typeArr[1]));
            showPanels(true, true, false);
            break;
        case "pageInput":
            showPanels(true, false, true);
            break;
        default:
            throw new AssertionError("Something's Wrong! Invalid Diary Records page type!");
        }
    }

    @FXML
    void checkInput() throws ParseException, CommandException {
        String command = "add page";
        String diaryInput = " n/ " + diaryNameTextField.getText();
        String titleInput = " t/ " + pageTitleTextField.getText();
        String imageInput = " i/ " + pageImageTextField.getText();
        String descInput = " desc/ " + pageDescriptionTextField.getText();
        mainWindow.executeGuiCommand(command + diaryInput + titleInput + imageInput + descInput);
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
