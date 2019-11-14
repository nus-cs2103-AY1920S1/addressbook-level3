package seedu.address.ui.currency;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMBOL;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.currency.CancelEditCurrencyCommand;
import seedu.address.logic.commands.currency.DoneEditCurrencyCommand;
import seedu.address.logic.commands.currency.EditCurrencyFieldCommand;
import seedu.address.logic.commands.currency.SelectCurrencyCommand;
import seedu.address.model.Model;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.form.DoubleFormItem;
import seedu.address.ui.components.form.TextFormItem;
import seedu.address.ui.template.Page;

/**
 * {@code Page} for displaying and editing the customised currencies.
 */
public class CurrencyPage extends Page<AnchorPane> {

    protected static final String FXML = "currency/CurrencyPage.fxml";
    private static final String EXECUTE_COMMAND_FORMAT = EditCurrencyFieldCommand.COMMAND_WORD + " %1$s%2$s";

    private TextFormItem currencyNameFormItem;
    private DoubleFormItem currencyRateFormItem;
    private TextFormItem currencySymbolFormItem;

    @FXML
    private VBox formItemsPlaceholder;

    @FXML
    private ScrollPane currencyScrollPane;

    @FXML
    private Button addButton;


    public CurrencyPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        initFormWithModel();
        fillPage();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        VBox currencyCardsContainer = new VBox();
        List<CustomisedCurrency> currencies = model.getTravelPal().getCurrencies();
        List<Node> currencyCards = IntStream.range(0, currencies.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index -> {
                    if (index.getZeroBased() == 0) {
                        CurrencyCard currencyCard = new SelectedCurrencyCard(currencies.get(index.getZeroBased()),
                                index);
                        return currencyCard.getRoot();
                    } else {
                        CurrencyCard currencyCard = new UnselectedCurrencyCard(currencies.get(index.getZeroBased()),
                                index);
                        currencyCard.getRoot().addEventFilter(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        mainWindow.executeGuiCommand(SelectCurrencyCommand.COMMAND_WORD
                                                + " " + index.getOneBased());
                                    }
                                });
                        return currencyCard.getRoot();
                    }

                }).collect(Collectors.toList());
        currencyCardsContainer.getChildren().addAll(currencyCards);
        currencyScrollPane.setContent(currencyCardsContainer);

        EditCurrencyFieldCommand.EditCurrencyDescriptor currentEditDescriptor =
                model.getPageStatus().getEditCurrencyDescriptor();

        if (currentEditDescriptor == null) {
            return;
        }

        currentEditDescriptor.getName().ifPresent(name ->
                currencyNameFormItem.setValue(name.toString()));
        currentEditDescriptor.getSymbol().ifPresent(symbol ->
                currencySymbolFormItem.setValue(symbol.toString()));
        currentEditDescriptor.getRate().ifPresent(rate ->
                currencyRateFormItem.setValue(rate.getValue()));
    }

    /**
     * Initialises and fills up all the placeholders of this window.
     */
    private void initFormWithModel() {
        //Initialise with new display data
        currencyNameFormItem = new TextFormItem("Name of Currency : ", nameFormValue -> {
            mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_NAME, nameFormValue));
        });
        VBox symbolFormItem = new VBox();

        currencySymbolFormItem = new TextFormItem("Symbol of Currency : ", symbol -> {
            mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_SYMBOL, symbol));
        });
        symbolFormItem.getChildren().add(currencySymbolFormItem.getRoot());
        symbolFormItem.getChildren().add(new PresetSymbols(mainWindow).getRoot());
        currencyRateFormItem = new DoubleFormItem("SGD $1.00 = ", rate -> {
            mainWindow.executeGuiCommand(String.format(EXECUTE_COMMAND_FORMAT, PREFIX_RATE, rate));
        });

        fillPage(); //update and overwrite with existing edit descriptor

        formItemsPlaceholder.getChildren().addAll(
                currencyNameFormItem.getRoot(),
                symbolFormItem,
                currencyRateFormItem.getRoot());
    }

    @FXML
    private void handleEditCurrencyDone() {
        String commandText = DoneEditCurrencyCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }

    @FXML
    private void handleEditCancel() {
        String commandText = CancelEditCurrencyCommand.COMMAND_WORD;
        mainWindow.executeGuiCommand(commandText);
    }

}
