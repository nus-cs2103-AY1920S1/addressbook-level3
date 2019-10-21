package seedu.address.logic;

import java.nio.file.Path;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.WordBankStatistics;

/**
 * API of the Logic component
 */
public interface Logic extends UiLogicHelper {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getWordBank()
     */
    ReadOnlyWordBank getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Card> getFilteredPersonList();


    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<WordBank> getFilteredWordBankList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    void saveUpdatedWbStatistics(GameStatistics gameStats) throws CommandException;

    /**
     * This method should be called every time a game finishes to correctly update global statistics
     */
    void incrementPlay() throws CommandException;

    WordBankStatistics getActiveWordBankStatistics();

    WordBankStatisticsList getWordBankStatisticsList();

    GlobalStatistics getGlobalStatistics();

    ReadOnlyWordBank getActiveWordBank();

    long getTimeAllowedPerQuestion();


    FormattedHint getHintFormatFromCurrentGame();

    int getHintFormatSizeFromCurrentGame();

    boolean hintsAreEnabled();

    List<AutoFillAction> getMenuItems(String text);

    ModeEnum getMode();

    List<ModeEnum> getModes();

}
