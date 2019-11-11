package seedu.address.ui.inventory;

import java.util.function.Function;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import javafx.fxml.FXML;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;

import seedu.address.logic.Logic;
import seedu.address.model.Model;

import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.InventoryList;

import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

/**
 * {@code Page} class implementing the itinerary landing page.
 */
public class InventoryPage extends PageWithSidebar<AnchorPane> {
    private static final String FXML = "inventory/InventoryPage.fxml";


    @FXML
    private TableView<Inventory> tableView;

    @FXML
    private TableColumn<Inventory, String> indexColumn;

    @FXML
    private TableColumn<Inventory, String> nameColumn;

    @FXML
    private TableColumn<Inventory, Boolean> isDoneColumn;

    public InventoryPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);

        indexColumn.setCellFactory(col -> new TableCell<Inventory, String>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index + 1));
                }
            }
        });


        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.isDoneColumn.setCellFactory(CheckBoxTableCell.forTableColumn(isDoneColumn));

        Function<Inventory, ObservableValue<Boolean>> property = Inventory::getIsDoneProperty;

        this.isDoneColumn.setCellValueFactory(cellData -> property.apply(cellData.getValue()));

        fillPage();
    }

    @Override
    public void fillPage() {



        InventoryList inventoryList = model.getPageStatus().getTrip().getInventoryList();

        tableView.setItems(FXCollections.observableList(inventoryList.getList()));

        /*
        nameLabel.setText(model.getPageStatus().getTrip().getName().toString());
        startDateLabel.setText(model.getPageStatus().getTrip().getStartDate().toString());
        endDateLabel.setText(model.getPageStatus().getTrip().getEndDate().toString());
        //destinationLabel.setText(model.getPageStatus().getTrip().getDestination().toString());
        totalBudgetLabel.setText(model.getPageStatus().getTrip().getBudget().toString());

        List<Day> days = model.getPageStatus().getTrip().getDayList().internalUnmodifiableList;

        List<Node> dayButtons = IntStream.range(0, days.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index -> {
                    Button button = new Button();
                    button.setText(Integer.toString(index.getOneBased()));
                    button.addEventFilter(MouseEvent.MOUSE_CLICKED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    mainWindow.executeGuiCommand(EnterDayCommand.COMMAND_WORD
                                            + " " + index.getOneBased());
                                }
                            });
                    return button;
                }).collect(Collectors.toList());
        dayButtonsContainer.getChildren().clear();
        dayButtonsContainer.getChildren().addAll(FXCollections.observableArrayList(dayButtons));
         */
    }

}
