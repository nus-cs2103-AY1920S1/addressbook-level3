package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.claim.Claim;
import seedu.address.model.commonvariables.Id;
import seedu.address.model.contact.Contact;

/**
 * Controller for a individual CheckContactWindow.fxml
 */
public class CheckContactWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(CheckContactWindow.class);
    private static final String FXML = "CheckContactWindow.fxml";

    private static Contact contact;
    private static ObservableList<Claim> claimList;

    @FXML
    private Label name;

    @FXML
    private Label number;

    @FXML
    private TableView<ClaimItem> claims;

    @FXML
    private TableColumn<ClaimItem, String> snCol;

    @FXML
    private TableColumn<ClaimItem, String> claimIdCol;

    @FXML
    private TableColumn<ClaimItem, String> dateCol;

    @FXML
    private TableColumn<ClaimItem, String> statusCol;

    @FXML
    private TableColumn<ClaimItem, String> descriptionCol;

    @FXML
    private TableColumn<ClaimItem, String> amountCol;

    @FXML
    private Stage root;

    @FXML
    private VBox box;

    /**
     * Creates a new Window.
     *
     * @param root Stage to use as the root of the IndividualClaimWindow.
     */
    public CheckContactWindow(Stage root, Contact contact, ObservableList<Claim> claimList) {
        super(FXML, root);
        this.contact = contact;
        this.claimList = claimList;
        name.setText("Name: " + contact.getName().toString());
        number.setText("Contact: " + contact.getPhone().toString());
        windowSetup();
        claims = new TableView<>();

        snCol.setCellValueFactory(new PropertyValueFactory<ClaimItem, String>("sn"));
        claimIdCol.setCellValueFactory(new PropertyValueFactory<ClaimItem, String>("claimId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<ClaimItem, String>("date"));
        statusCol.setCellValueFactory(new PropertyValueFactory<ClaimItem, String>("status"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<ClaimItem, String>("description"));
        amountCol.setCellValueFactory(new PropertyValueFactory<ClaimItem, String>("amount"));

        claims.getColumns().addAll(snCol, claimIdCol, dateCol, statusCol, descriptionCol, amountCol);

        ObservableList<ClaimItem> claimItems = getClaimItems();
        claims.setItems(claimItems);
    }

    /**
     * Creates a new IndividualContactWindow.
     */
    public CheckContactWindow(Contact contact, ObservableList<Claim> claimList) {
        this(new Stage(), contact, claimList);
    }

    /**
     * Sets up settings for the individual contact window
     */
    public void windowSetup() {
        box.setStyle("-fx-background-color:POWDERBLUE");
    }

    /**
     * Shows the IndividualContact window.
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
        logger.fine("Showing the individual contact card.");

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

    /**
     * Closes the window
     */
    @FXML
    private void closeAction() {
        root.close();
    }

    private ObservableList<ClaimItem> getClaimItems() {
        ObservableList<ClaimItem> lst = FXCollections.observableArrayList();
        int count = 1;
        for (Id id: contact.getClaims()) {
            Claim claim = findClaimById(id);
            if (claim != null) {
                ClaimItem item = new ClaimItem(count + "",
                        claim.getId().toString(),
                        claim.getDate().toString(),
                        claim.getStatus().toString(),
                        claim.getDescription().toString(),
                        claim.getAmount().toString());
                lst.add(item);
                count++;
            }
        }
        return lst;
    }

    private Claim findClaimById(Id id) {
        for (Claim claim: claimList) {
            if (claim.getId().equals(id)) {
                return claim;
            }
        }
        return null;
    }
}
