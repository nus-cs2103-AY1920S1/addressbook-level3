package seedu.address.ui.diary;

import static seedu.address.commons.util.AppUtil.getImage;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_CHOOSER;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
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

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.diary.CreateDiaryEntryCommand;
import seedu.address.logic.commands.diary.FlipDiaryCommand;
import seedu.address.logic.commands.diary.entry.DoneEditEntryTextCommand;
import seedu.address.logic.commands.diary.entry.EditEntryTextCommand;
import seedu.address.logic.commands.diary.entry.ShowTextEditorCommand;
import seedu.address.logic.commands.diary.gallery.AddPhotoCommand;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.DiaryEntryList;
import seedu.address.model.diary.EditDiaryEntryDescriptor;
import seedu.address.ui.MainWindow;
import seedu.address.ui.diary.entry.DiaryEntryDisplay;
import seedu.address.ui.diary.gallery.DiaryGallery;
import seedu.address.ui.template.Page;
import seedu.address.ui.template.PageWithSidebar;

/**
 * The {@link Page} for the diary feature of TravelPal.
 */
public class DiaryPage extends PageWithSidebar<BorderPane> {
    private static final String FXML = "diary/DiaryPage.fxml";

    private static final String DIARY_ENTRY_BACKGROUND_IMAGE = "/images/diaryEntryPaperTexture.jpg";

    private static final int BACKGROUND_REPEAT_LENGTH = 1000;

    private final Logger logger = LogsCenter.getLogger(DiaryPage.class);

    //Model
    private DiaryEntry currentEntry;
    private EditDiaryEntryDescriptor editDiaryEntryDescriptor;

    //Custom javaFX components
    private DiaryGallery diaryGallery;
    private DiaryEditBox diaryEntryEditBox;
    private DiaryEntryDisplay diaryEntryDisplay;

    private Button doneButton;
    private Button editButton;
    private Button addPhotoButton;

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
        initRightPlaceholderButtons();

        diaryGallery = new DiaryGallery();
        diaryEntryEditBox = new DiaryEditBox(editBoxText -> {
            mainWindow.executeGuiCommand(String.format("%1$s %2$s%3$s",
                    EditEntryTextCommand.COMMAND_WORD,
                    PREFIX_DESCRIPTION,
                    editBoxText));

            //Workaround for done button click handler not executing
            //occurring due to loss of focus event from edit box taking priority
            if (doneButton.isPressed()) {
                doneButton.onMouseClickedProperty().get().handle(null);
            }
        });
        diaryEntryDisplay = new DiaryEntryDisplay(diaryEntryEditBox.getObservableParagraphs());

        initDiaryEntryPlaceholder();

        fillButtonBar();
        addButtonBarListeners();
    }

    /**
     * Initialises the gui buttons used for the gallery or edit box, to be placed under {@code dayRightButtonBar}.
     */
    private void initRightPlaceholderButtons() {
        doneButton = new Button("Done");
        ButtonBar.setButtonData(doneButton, ButtonBar.ButtonData.LEFT);
        doneButton.setOnMouseClicked(ev -> mainWindow.executeGuiCommand(DoneEditEntryTextCommand.COMMAND_WORD));

        editButton = new Button("Edit");
        addPhotoButton = new Button("Add");

        ButtonBar.setButtonData(editButton, ButtonBar.ButtonData.LEFT);
        ButtonBar.setButtonData(addPhotoButton, ButtonBar.ButtonData.LEFT);

        editButton.setOnMouseClicked(buttonEvent -> mainWindow.executeGuiCommand(ShowTextEditorCommand.COMMAND_WORD));
        addPhotoButton.setOnMouseClicked(buttonEvent ->
                mainWindow.executeGuiCommand(AddPhotoCommand.COMMAND_WORD + " " + PREFIX_FILE_CHOOSER));
    }

    /**
     * Initialises the {@code diaryEntryPlaceholder}'s background image and adds the {@code diaryEntryDisplay}
     * as one of its children.
     */
    private void initDiaryEntryPlaceholder() {
        Background diaryEntryBackground = new Background(new BackgroundImage(
                getImage(DIARY_ENTRY_BACKGROUND_IMAGE),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BACKGROUND_REPEAT_LENGTH, BACKGROUND_REPEAT_LENGTH,
                        false, false, false, false)));
        diaryEntryPlaceholder.setBackground(diaryEntryBackground);

        diaryEntryPlaceholder.getChildren().add(diaryEntryDisplay.getRoot());
    }

    /**
     * Adds a listener to the sorted list of diary entries to update the {@code dayIndexButtonBar}
     * whenever a change is detected in the sorted list.
     * The method {@code fillButtonBar} is called to refresh the {@code dayIndexButtonBar}.
     */
    private void addButtonBarListeners() {
        model.getPageStatus()
                .getCurrentTripDiaryEntryList()
                .getDiaryEntrySortedList()
                .addListener((ListChangeListener<DiaryEntry>) change -> fillButtonBar());
    }

    /**
     * The callback method to run after command execution.
     * If no diary entry is currently being viewed, the method returns immediately.
     * Otherwise, the user interface is updated based on whether the user is using the {@code diaryEntryEditBox},
     * as indicated by the null status of {@code editDiaryEntryDescriptor} in the
     * {@link seedu.address.model.appstatus.PageStatus}.
     */
    @Override
    public void fillPage() {
        currentEntry = model.getPageStatus().getDiaryEntry();
        if (currentEntry == null) {
            logger.log(Level.INFO, "No diary entry being shown currently.");
            return;
        }

        dayIndexLabel.setText(String.format("Day %1$d", currentEntry.getDayNumber()));
        editDiaryEntryDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();
        if (editDiaryEntryDescriptor == null) {
            logger.log(Level.INFO, "User command executed while diary page is not in editing mode.");

            diaryEntryDisplay.setPhotoList(currentEntry.getPhotoList());
            diaryEntryEditBox.setText(currentEntry.getDiaryText());
            diaryGallery.setPhotoList(currentEntry.getPhotoList());
            swapRightToGallery();
        } else if (!diaryRightPlaceholder.getChildren().contains(diaryEntryEditBox.getRoot())) {
            logger.log(Level.INFO, "Diary page is switching to edit box mode.");

            swapRightToEditBox();
        } else {
            logger.log(Level.INFO, "User command executed in diary page while in editing mode.");

            diaryEntryEditBox.setText(editDiaryEntryDescriptor.getDiaryText());
        }
    }

    /**
     * Populates the {@code dayIndexButtonBar} with diary day navigation buttons using
     * the current {@link DiaryEntry}s contained in current {@link DiaryEntryList} of the trip.
     */
    private void fillButtonBar() {
        DiaryEntryList diaryEntryList = model.getPageStatus().getCurrentTripDiaryEntryList();
        dayIndexButtonBar.getButtons().clear();
        int nextDayToAdd = 1;

        for (DiaryEntry diaryEntry : diaryEntryList.getDiaryEntrySortedList()) {
            nextDayToAdd = Math.max(diaryEntry.getDayNumber(), nextDayToAdd);

            Button currentButton = new Button(String.valueOf(diaryEntry.getDayNumber()));
            ButtonBar.setButtonData(currentButton, ButtonBar.ButtonData.BIG_GAP);
            currentButton.setOnMouseClicked(buttonEvent -> {
                mainWindow.executeGuiCommand(String.format("%1$s %2$d",
                        FlipDiaryCommand.COMMAND_WORD,
                        diaryEntry.getDayNumber()));
            });

            dayIndexButtonBar.getButtons().add(currentButton);
        }
        nextDayToAdd++;

        addCreateDiaryEntryButton(nextDayToAdd);
    }

    /**
     * Adds the "+" button to create a new {@link DiaryEntry} to the {@code dayIndexButtonBar}.
     *
     * @param nextDayToAdd The number of the next day to add.
     */
    private void addCreateDiaryEntryButton(int nextDayToAdd) {
        Button addButton = new Button("+");
        ButtonBar.setButtonData(addButton, ButtonBar.ButtonData.RIGHT);
        addButton.setOnMouseClicked(buttonEvent -> {
            mainWindow.executeGuiCommand(String.format("%1$s %2$d",
                    CreateDiaryEntryCommand.COMMAND_WORD,
                    nextDayToAdd));
        });

        dayIndexButtonBar.getButtons().add(addButton);
    }

    /**
     * Fills the {@code diaryRightPlaceholder} {@link VBox} with the {@code diaryEntryEditBox},
     * removing the {@code diaryGallery}.
     * Also updates the {@code diaryRightButtonBar} with the buttons for the edit box.
     */
    private void swapRightToEditBox() {
        if (!diaryRightPlaceholder.getChildren().contains(diaryEntryEditBox.getRoot())) {
            ObservableList<Node> placeHolderChildren = diaryRightPlaceholder.getChildren();
            placeHolderChildren.remove(diaryGallery.getRoot());
            diaryEntryEditBox.setText(editDiaryEntryDescriptor.getDiaryText());
            placeHolderChildren.add(diaryEntryEditBox.getRoot());
            diaryEntryEditBox.requestFocus();

            fillEditBoxButtons();
        }
    }

    /**
     * Fills the {@code diaryRightButtonBar} with functional gui buttons for the edit box.
     */
    private void fillEditBoxButtons() {
        diaryRightButtonBar.getButtons().clear();
        diaryRightButtonBar.getButtons().add(doneButton);
    }

    /**
     * Fills the {@code diaryRightPlaceholder} {@link VBox} with the {@code diaryGallery},
     * removing the {@code diaryEntryEditBox}, if the {@code diaryGallery} is not already inside.
     * Also updates the {@code diaryRightButtonBar} with the buttons for the gallery.
     */
    private void swapRightToGallery() {
        if (!diaryRightPlaceholder.getChildren().contains(diaryGallery.getRoot())) {
            ObservableList<Node> placeHolderChildren = diaryRightPlaceholder.getChildren();
            placeHolderChildren.clear();
            placeHolderChildren.add(diaryGallery.getRoot());

            fillGalleryButtons();
        }
    }

    /**
     * Fills the {@code diaryRightButtonBar} with functional gui buttons for the gallery.
     */
    private void fillGalleryButtons() {
        diaryRightButtonBar.getButtons().clear();
        diaryRightButtonBar.getButtons().addAll(editButton, addPhotoButton);
    }
}
