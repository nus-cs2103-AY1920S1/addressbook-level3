package guitests.guihandles;

import guitests.guihandles.panels.CustomerListPanelHandle;
import guitests.guihandles.panels.OrderListPanelHandle;
import guitests.guihandles.panels.PhoneListPanelHandle;
import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {
    private final CustomerListPanelHandle customerListPanel;
    private final PhoneListPanelHandle phoneListPanel;
    private final OrderListPanelHandle orderListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final TabPanelHandle tabPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        customerListPanel = new CustomerListPanelHandle(getChildNode(CustomerListPanelHandle.CUSTOMER_LIST_VIEW_ID));
        phoneListPanel = new PhoneListPanelHandle(getChildNode(PhoneListPanelHandle.PHONE_LIST_VIEW));
        orderListPanel = new OrderListPanelHandle(getChildNode(OrderListPanelHandle.ORDER_LIST_VIEW_ID));

        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        tabPanel = new TabPanelHandle(getChildNode(TabPanelHandle.TAB_PANEL_FIELD_ID));

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

    public TabPanelHandle getTabPanel() {
        return tabPanel;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public OrderListPanelHandle getOrderListPanel() {
        return orderListPanel;
    }
    public CustomerListPanelHandle getCustomerListPanel() {
        return customerListPanel;
    }

    public PhoneListPanelHandle getPhoneListPanel() {
        return phoneListPanel;
    }


}
