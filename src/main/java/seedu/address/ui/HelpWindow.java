package seedu.address.ui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditBorrowerCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InfoCommand;
import seedu.address.logic.commands.LoanCommand;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.RenewCommand;
import seedu.address.logic.commands.ReturnCommand;
import seedu.address.logic.commands.ServeCommand;
import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnregisterCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> implements Initializable {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private TableView<HelpTableEntry> helpTable;

    @FXML
    private TableColumn<HelpTableEntry, String> commandCol;

    @FXML
    private TableColumn<HelpTableEntry, String> usageCol;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Initialises the table of commands and usages within the HelpWindow.
     */
    public void initialize(URL url, ResourceBundle rb) {
        final ObservableList<HelpTableEntry> data = FXCollections.observableArrayList(
                new HelpTableEntry(AddCommand.COMMAND_WORD, AddCommand.MESSAGE_USAGE),
                new HelpTableEntry(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_USAGE),
                new HelpTableEntry(DeleteCommand.COMMAND_WORD, DeleteCommand.MESSAGE_USAGE),
                new HelpTableEntry(DoneCommand.COMMAND_WORD, DoneCommand.MESSAGE_USAGE),
                new HelpTableEntry(EditBorrowerCommand.COMMAND_WORD, EditBorrowerCommand.MESSAGE_USAGE),
                new HelpTableEntry(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_USAGE),
                new HelpTableEntry(FindCommand.COMMAND_WORD, FindCommand.MESSAGE_USAGE),
                new HelpTableEntry(HelpCommand.COMMAND_WORD, HelpCommand.MESSAGE_USAGE),
                new HelpTableEntry(InfoCommand.COMMAND_WORD, InfoCommand.MESSAGE_USAGE),
                new HelpTableEntry(LoanCommand.COMMAND_WORD, LoanCommand.MESSAGE_USAGE),
                new HelpTableEntry(PayCommand.COMMAND_WORD, PayCommand.MESSAGE_USAGE),
                new HelpTableEntry(RedoCommand.COMMAND_WORD, RedoCommand.MESSAGE_USAGE),
                new HelpTableEntry(RegisterCommand.COMMAND_WORD, RegisterCommand.MESSAGE_USAGE),
                new HelpTableEntry(RenewCommand.COMMAND_WORD, RenewCommand.MESSAGE_USAGE),
                new HelpTableEntry(ReturnCommand.COMMAND_WORD, ReturnCommand.MESSAGE_USAGE),
                new HelpTableEntry(ServeCommand.COMMAND_WORD, ServeCommand.MESSAGE_USAGE),
                new HelpTableEntry(SetCommand.COMMAND_WORD, SetCommand.MESSAGE_USAGE),
                new HelpTableEntry(UndoCommand.COMMAND_WORD, UndoCommand.MESSAGE_USAGE),
                new HelpTableEntry(UnregisterCommand.COMMAND_WORD, UnregisterCommand.MESSAGE_USAGE)
        );

        commandCol.setCellValueFactory(new PropertyValueFactory<>("command"));
        usageCol.setCellValueFactory(new PropertyValueFactory<>("usage"));

        helpTable.setItems(data);
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
