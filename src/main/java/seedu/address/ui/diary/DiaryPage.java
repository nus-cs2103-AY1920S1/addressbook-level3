package seedu.address.ui.diary;

import static seedu.address.commons.util.AppUtil.getImage;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_CHOOSER;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.diary.CreateDiaryEntryCommand;
import seedu.address.logic.commands.diary.DoneEditDiaryEntryCommand;
import seedu.address.logic.commands.diary.EditDiaryEntryCommand;
import seedu.address.logic.commands.diary.FlipDiaryCommand;
import seedu.address.logic.commands.diary.ShowTextEditorCommand;
import seedu.address.logic.commands.diary.gallery.AddPhotoCommand;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.DiaryEntryList;
import seedu.address.model.diary.EditDiaryEntryDescriptor;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.Page;
import seedu.address.ui.template.PageWithSidebar;

/**
 * The {@link Page} for the diary feature of TravelPal.
 */
public class DiaryPage extends PageWithSidebar<BorderPane> {
    private static final String FXML = "diary/DiaryPage.fxml";

    private static final String DIARY_ENTRY_BACKGROUND_IMAGE = "/images/diaryEntryPaperTexture.jpg";

    private static final int BACKGROUND_REPEAT_LENGTH = 800;

    //Model
    private DiaryEntry currentEntry;
    private EditDiaryEntryDescriptor editDiaryEntryDescriptor;

    //Custom javaFX components
    private DiaryGallery diaryGallery;
    private DiaryEditBox diaryEntryEditBox;
    private DiaryEntryDisplay diaryEntryDisplay;


    @FXML
    private StackPane diaryEntryPlaceholder;
    @FXML
    private Label dayIndexLabel;
    @FXML
    private StackPane diaryRightPlaceholder;
    @FXML
    private ButtonBar dayIndexButtonBar;
    @FXML
    private ButtonBar diaryRightButtonBar;

    public DiaryPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        currentEntry = model.getPageStatus().getDiaryEntry();
        editDiaryEntryDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();
        initPlaceholders();
    }

    /**
     * Initialises the javafx components of the page, to be placed inside the various placeholders.
     */
    private void initPlaceholders() {
        diaryGallery = new DiaryGallery();
        diaryEntryEditBox = new DiaryEditBox(editBoxText ->
                mainWindow.executeGuiCommand(EditDiaryEntryCommand.COMMAND_WORD + " "
                        + PREFIX_DESCRIPTION + editBoxText));
        //Set background
        Background diaryEntryBackground = new Background(new BackgroundImage(
                getImage(DIARY_ENTRY_BACKGROUND_IMAGE),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BACKGROUND_REPEAT_LENGTH, BACKGROUND_REPEAT_LENGTH,
                        false, false, false, false)));

        diaryEntryPlaceholder.setBackground(diaryEntryBackground);
        diaryEntryDisplay = new DiaryEntryDisplay(diaryEntryEditBox.getObservableParagraphs());
        diaryEntryPlaceholder.getChildren().add(diaryEntryDisplay.getRoot());
    }

    @Override
    public void fillPage() {
        currentEntry = model.getPageStatus().getDiaryEntry();
        if (currentEntry == null) {
            return;
        }

        dayIndexLabel.setText("Day " + currentEntry.getDayIndex().getOneBased());
        editDiaryEntryDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();
        if (editDiaryEntryDescriptor == null) {
            diaryEntryDisplay.setPhotoList(currentEntry.getPhotoList());
            diaryEntryEditBox.setText(currentEntry.getDiaryText());
            swapRightToGallery();
        } else if (!diaryRightPlaceholder.getChildren().contains(diaryEntryEditBox.getRoot())) {
            swapRightToEditBox();
        } else {
            diaryEntryEditBox.setText(editDiaryEntryDescriptor.getDiaryText());
        }

        fillButtonBar();
    }

    /**
     * Populates the {@code dayIndexButtonBar} with diary day navigation buttons using
     * the current {@link DiaryEntryList}.
     */
    private void fillButtonBar() {
        //Fill entry navigation bar
        DiaryEntryList diaryEntryList = model.getPageStatus().getTrip().getDiary().getDiaryEntryList();
        dayIndexButtonBar.getButtons().clear();
        int nextDayToAdd = 1;

        for (DiaryEntry diaryEntry : diaryEntryList.getReadOnlyDiaryEntries()) {
            nextDayToAdd = Math.max(diaryEntry.getDayIndex().getOneBased(), nextDayToAdd);
            Button b = new Button(diaryEntry.getDayIndex().getOneBased() + "");
            ButtonBar.setButtonData(b, ButtonBar.ButtonData.BIG_GAP);
            b.setOnMouseClicked(buttonEvent -> mainWindow.executeGuiCommand(
                    FlipDiaryCommand.COMMAND_WORD + " " + diaryEntry.getDayIndex().getOneBased()));

            dayIndexButtonBar.getButtons().add(b);
        }
        nextDayToAdd++;
        //Add + button
        Button addButton = new Button("+");
        ButtonBar.setButtonData(addButton, ButtonBar.ButtonData.RIGHT);
        int finalNextDayToAdd = nextDayToAdd;
        addButton.setOnMouseClicked(buttonEvent -> mainWindow.executeGuiCommand(
                CreateDiaryEntryCommand.COMMAND_WORD + " " + finalNextDayToAdd));
        dayIndexButtonBar.getButtons().add(addButton);

        //Fill bottom gallery / edit window bar depending on which is open
        if (editDiaryEntryDescriptor == null) {
            fillGalleryButtons();
        } else {
            fillEditBoxButtons();
        }
    }

    /**
     * Fills the {@code diaryRightButtonBar} with functional gui buttons for the edit box.
     */
    private void fillEditBoxButtons() {
        Button doneButton = new Button("Done");
        ButtonBar.setButtonData(doneButton, ButtonBar.ButtonData.LEFT);

        doneButton.setOnMouseClicked(buttonEvent ->
                mainWindow.executeGuiCommand(DoneEditDiaryEntryCommand.COMMAND_WORD));

        diaryRightButtonBar.getButtons().clear();
        diaryRightButtonBar.getButtons().add(doneButton);
    }

    /**
     * Fills the {@code diaryRightButtonBar} with functional gui buttons for the gallery.
     */
    private void fillGalleryButtons() {
        Button editButton = new Button("Edit");
        Button addPhotoButton = new Button("Add");

        ButtonBar.setButtonData(editButton, ButtonBar.ButtonData.LEFT);
        ButtonBar.setButtonData(addPhotoButton, ButtonBar.ButtonData.LEFT);

        editButton.setOnMouseClicked(buttonEvent -> mainWindow.executeGuiCommand(ShowTextEditorCommand.COMMAND_WORD));
        addPhotoButton.setOnMouseClicked(buttonEvent ->
                mainWindow.executeGuiCommand(AddPhotoCommand.COMMAND_WORD + " " + PREFIX_FILE_CHOOSER));

        diaryRightButtonBar.getButtons().clear();
        diaryRightButtonBar.getButtons().addAll(editButton, addPhotoButton);
    }

    /**
     * Fills the {@code diaryRightPlaceholder} {@link VBox} with the {@code diaryEntryEditBox},
     * removing the {@code diaryGallery}.
     */
    private void swapRightToEditBox() {
        ObservableList<Node> placeHolderChildren = diaryRightPlaceholder.getChildren();
        placeHolderChildren.remove(diaryGallery.getRoot());
        diaryEntryEditBox.setText(editDiaryEntryDescriptor.getDiaryText());
        placeHolderChildren.add(diaryEntryEditBox.getRoot());
        diaryEntryEditBox.requestFocus();
    }

    /**
     * Fills the {@code diaryRightPlaceholder} {@link VBox} with the {@code diaryGallery},
     * removing the {@code diaryEntryEditBox}, if the {@code diaryGallery} is not already inside.
     */
    private void swapRightToGallery() {
        if (!diaryRightPlaceholder.getChildren().contains(diaryGallery.getRoot())) {
            ObservableList<Node> placeHolderChildren = diaryRightPlaceholder.getChildren();
            placeHolderChildren.clear();
            diaryGallery.setPhotoList(currentEntry.getPhotoList());
            placeHolderChildren.add(diaryGallery.getRoot());
        }
    }
}
