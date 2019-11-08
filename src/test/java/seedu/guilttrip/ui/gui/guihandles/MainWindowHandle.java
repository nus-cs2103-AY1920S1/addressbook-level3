package seedu.guilttrip.ui.gui.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final EntryListPanelHandle entryListPanel;
    private final ExpenseListPanelHandle expenseListPanel;
    private final IncomeListPanelHandle incomeListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;

    public MainWindowHandle(Stage stage) {
        super(stage);

        entryListPanel = new EntryListPanelHandle(getChildNode(EntryListPanelHandle.ENTRY_LIST_VIEW_ID));
        expenseListPanel = new ExpenseListPanelHandle(getChildNode(ExpenseListPanelHandle.EXPENSE_LIST_VIEW_ID));
        incomeListPanel = new IncomeListPanelHandle(getChildNode(IncomeListPanelHandle.INCOME_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
    }

    public EntryListPanelHandle getEntryListPanel() {
        return entryListPanel;
    }

    public ExpenseListPanelHandle getExpenseListPanel() {
        return expenseListPanel;
    }

    public IncomeListPanelHandle getIncomeListPanel() {
        return incomeListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

}
