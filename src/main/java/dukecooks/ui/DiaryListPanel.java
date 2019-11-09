package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.LogsCenter;
import dukecooks.commons.util.ImagePicker;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.Page;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Panel containing the diary feature.
 */
public class DiaryListPanel extends UiPart<Region> {

    private static final String FXML = "DiaryListPanel.fxml";
    private static Event event;

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
        if (!diaryList.isEmpty()) {
            diaryHeader.setText(diaryList.get(targetIndex).getDiaryName().fullName);
            initializePageListView(targetIndex);
        }
    }

    /**
     * Initialises Page List View Config
     * Gives the overview of pages in that specified diary
     */
    void initializePageListView(int targetIndex) {

        // Sets the name of the diary if diary list changes
        diaryList.addListener((ListChangeListener<Diary>) c -> {
            refreshPages(targetIndex);
        });

        refreshPages(targetIndex);
    }

    /**
     * Refreshes the page list view.
     */
    void refreshPages(int targetIndex) {

        if (!diaryList.isEmpty()) {
            pageListView.setVisible(true);
        }

        // Empty diaryList
        if (diaryList.isEmpty()) {
            diaryHeader.setText("You have no available diaries ...");
            pageListView.setVisible(false);
        } else if (targetIndex >= diaryList.size()) {
            // Show the last possible diary entry
            refreshPages(targetIndex - 1);
        } else {
            ObservableList<Page> pageList = diaryList.get(targetIndex).getPages();
            diaryHeader.setText(diaryList.get(targetIndex).getDiaryName().toString());
            pageListView.setItems(pageList);
            pageListView.setCellFactory(listView -> new PageListViewCell());
        }
    }

    /**
     * Initialises PageInputView
     * Renders the Page Input Form page
     */
    private void initializePageInputView() {
        // Auto-changes focus to Diary Name
        focusToDiaryName();
        clearAllInputFields();

        // Set up F2 to return focus to DiaryName TextField
        setAccelerator(KeyCombination.keyCombination("F2"), this::focusToDiaryName);

    }

    /**
     * Method that auto-focuses on the DiaryName TextField
     */
    private void focusToDiaryName() {
        Platform.runLater(()-> diaryNameTextField.requestFocus());
    }

    /**
     * Utility method that clear all inputs from the various text fields.
     */
    private void clearAllInputFields() {
        diaryNameTextField.clear();
        pageTitleTextField.clear();
        pageTypeTextField.clear();
        pageDescriptionTextField.clear();
        pageImageTextField.clear();
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
    public void handleSwitch(String type) {
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
            initializePageInputView();
            showPanels(true, false, true);
            break;
        default:
            throw new AssertionError("Something's Wrong! Invalid Diary Records page type!");
        }
    }

    /**
     * Executes an CreatePageCommand, based on the input provided in the GUI.
     */
    @FXML
    void executeInput() {
        String command = "create page";
        String diaryInput = " n/ " + diaryNameTextField.getText();
        String titleInput = " t/ " + pageTitleTextField.getText();
        String pageTypeInput = " tp/ " + pageTypeTextField.getText();
        String descInput = " desc/ " + pageDescriptionTextField.getText();
        String imageInput = " i/ " + pageImageTextField.getText();
        mainWindow.executeGuiCommand(command + diaryInput + titleInput
                + pageTypeInput + imageInput + descInput);
    }

    @FXML
    void handleImagePicker() {
        ImagePicker picker = new ImagePicker();
        pageImageTextField.setText(picker.getImageFile().getAbsolutePath());
    }

    @FXML
    void handleConfirmAction() {
        executeInput();
    }

    @FXML
    void handleCancelAction() {
        clearAllInputFields();
        event = Event.getInstance();
        event.set("diary", "all");
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

    /**
     * Sets an accelerator the application.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(KeyCombination keyCombination, Runnable executed) {
        getRoot().getScene().getAccelerators().put(keyCombination, executed);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         *
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                executed.run();
                event.consume();
            }
        });
    }

}
