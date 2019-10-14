package seedu.address.ui.diary;

import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.diary.DoneEditDiaryEntryCommand;
import seedu.address.logic.commands.diary.EditDiaryEntryCommand;
import seedu.address.logic.commands.diary.FlipDiaryCommand;
import seedu.address.logic.commands.diary.gallery.AddPhotoCommand;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.DiaryEntryList;
import seedu.address.model.diary.EditDiaryEntryDescriptor;
import seedu.address.model.diary.photo.PhotoList;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

public class DiaryPage extends PageWithSidebar<BorderPane> {
    private static final String FXML = "diary/DiaryPage.fxml";

    //Model
    private DiaryEntry currentEntry;
    private EditDiaryEntryDescriptor editDiaryEntryDescriptor;

    //Custom javaFX components
    private DiaryGallery diaryGallery;
    private DiaryEditBox diaryEntryEditbox;
    private DiaryTextFlow diaryTextFlow;

    @FXML
    private ScrollPane diaryTextScrollPane;
    @FXML
    private VBox diaryTextPlaceholder;
    @FXML
    private Label dayIndexLabel;
    @FXML
    private VBox diaryRightPlaceholder;
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

    private void initPlaceholders() {
        diaryGallery = new DiaryGallery(currentEntry == null ? new PhotoList() : currentEntry.getPhotoList());
        diaryEntryEditbox = new DiaryEditBox(editBoxText ->
                mainWindow.executeGuiCommand(EditDiaryEntryCommand.COMMAND_WORD + " " + editBoxText));
        diaryTextFlow = new DiaryTextFlow(currentEntry == null ? "" : currentEntry.getDiaryText());
        //bind widths and heights
        diaryTextPlaceholder.prefWidthProperty().bind(diaryTextScrollPane.widthProperty());
        diaryTextFlow.getRoot().prefWidthProperty().bind(diaryTextPlaceholder.widthProperty());
        diaryTextFlow.getRoot().minWidthProperty().bind(diaryTextPlaceholder.heightProperty());
        diaryGallery.getRoot().prefHeightProperty().bind(diaryRightPlaceholder.heightProperty());
        diaryEntryEditbox.getRoot().prefHeightProperty().bind(diaryRightPlaceholder.heightProperty());
        //add diaryTextFlow with width binded
        diaryTextPlaceholder.getChildren().add(diaryTextFlow.getRoot());
    }

    @Override
    public void fillPage() {
        currentEntry = model.getPageStatus().getDiaryEntry();
        if (currentEntry == null) {
            return;
        }

        dayIndexLabel.setText(currentEntry.getDayIndex().getOneBased() + "");
        diaryTextFlow.setText(currentEntry.getDiaryText());

        editDiaryEntryDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();

        if (editDiaryEntryDescriptor == null) {
            fillWithGallery();
        } else {
            fillWithEditbox();
        }

        fillButtonBar();
    }

    private void fillButtonBar() {
        //Fill entry navigation bar
        DiaryEntryList diaryEntryList = model.getPageStatus().getTrip().getDiary().getDiaryEntryList();
        dayIndexButtonBar.getButtons().clear();
        dayIndexButtonBar.getButtons().addAll(
                diaryEntryList.getReadOnlyDiaryEntries().stream()
                        .map(diaryEntry -> {
                            Button b = new Button(diaryEntry.getDayIndex().getOneBased() + "");
                            b.setOnMouseClicked(buttonEvent -> mainWindow.executeGuiCommand(
                                    FlipDiaryCommand.COMMAND_WORD + " " + diaryEntry.getDayIndex().getOneBased()));

                            return b;
                        })
                        .collect(Collectors.toList())
        );
        //Fill bottom gallery / edit window bar depending on which is open
        if (editDiaryEntryDescriptor == null) {
            fillGalleryButtons();
        } else {
            fillEditBoxButtons();
        }
    }

    private void fillEditBoxButtons() {
        Button doneButton = new Button("Done");
        doneButton.setAlignment(Pos.CENTER);

        doneButton.setOnMouseClicked(buttonEvent -> mainWindow.executeGuiCommand(DoneEditDiaryEntryCommand.COMMAND_WORD));

        diaryRightButtonBar.getButtons().clear();
        diaryRightButtonBar.getButtons().add(doneButton);
    }

    private void fillGalleryButtons() {
        Button editButton = new Button("Edit");
        Button addPhotoButton = new Button("Add");
        editButton.setAlignment(Pos.CENTER);
        addPhotoButton.setAlignment(Pos.CENTER);

        editButton.setOnMouseClicked(buttonEvent -> mainWindow.executeGuiCommand(EditDiaryEntryCommand.COMMAND_WORD));
        //addPhotoButton.setOnMouseClicked(buttonEvent -> mainWindow.executeGuiCommand(AddPhotoCommand.COMMAND_WORD));

        diaryRightButtonBar.getButtons().clear();
        diaryRightButtonBar.getButtons().addAll(editButton, addPhotoButton);
    }

    private void fillWithEditbox() {
        ObservableList<Node> placeHolderChildren = diaryRightPlaceholder.getChildren();

        placeHolderChildren.remove(diaryGallery.getRoot());
        if (!placeHolderChildren.contains(diaryEntryEditbox.getRoot())) {
            diaryEntryEditbox.setText(editDiaryEntryDescriptor.getDiaryText());
            placeHolderChildren.add(diaryEntryEditbox.getRoot());
        }
    }

    private void fillWithGallery() {
        ObservableList<Node> placeHolderChildren = diaryRightPlaceholder.getChildren();
        placeHolderChildren.clear();
        diaryGallery.setPhotoList(currentEntry.getPhotoList());
        placeHolderChildren.add(diaryGallery.getRoot());
    }

    @FXML
    private void handleEdit() {
        mainWindow.executeGuiCommand(EditDiaryEntryCommand.COMMAND_WORD + " " + diaryEntryEditbox.getText());
    }
}
