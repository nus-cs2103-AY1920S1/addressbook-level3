package seedu.address.ui.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.mapping.InvMemMapping;
import seedu.address.model.mapping.InvTasMapping;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.member.MemberName;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of inventories.
 * Called by {@code UserViewUpdate} when user executes {@code ListInventoriesCommand}.
 */
/*
public class InventoryListPanel extends UiPart<Region> {
    private static final String FXML = "InventoryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InventoryListPanel.class);

    @FXML
    private ListView<Inventory> InventoryListView;

    public InventoryListPanel(ObservableList<Inventory> inventoryList,
                              ObservableList<InvMemMapping> invMemMappings,
                              ObservableList<InvTasMapping> invTasMappings) {
        super(FXML);
        InventoryListView.setItems(inventoryList);
        InventoryListView.setCellFactory(listView -> new InventoryListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
/*
    class InventoryListViewCell extends ListCell<Inventory> {
        @Override
        protected void updateItem(Inventory inventory, boolean empty) {
            super.updateItem(inventory, empty);

            if (empty || inventory == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InventoryCard(inventory, getIndex() + 1).getRoot());
            }
        }
    }
}
*/

public class InventoryListPanel extends UiPart<Region> {
    private static final String FXML = "InventoryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InventoryListPanel.class);

    @FXML
    private ListView<IndivInventoryCard> inventoryListView;

    public InventoryListPanel(ObservableList<Inventory>invList, ObservableList<Member> memberList, ObservableList<Task> taskList,
                              ObservableList<InvMemMapping> invMemMappings, ObservableList<InvTasMapping>invTasMappings) {
        super(FXML);
        ObservableList<IndivInventoryCard> inventoryCards = FXCollections.observableArrayList();

        logger.info("Size of list to be printed: " + invList.size());
        for (int i = 0; i<invList.size(); i++) {
            Inventory invInvolved = invList.get(i);
            //int invIndex = invList.indexOf(invInvolved);

            Task invTask = new Task(new Name("No task attached"), TaskStatus.UNBEGUN, Collections.<Tag>emptySet());
            for (InvTasMapping mapping : invTasMappings) {
                if (mapping.getInventoryIndex() == i) {
                    invTask = taskList.get(mapping.getTaskIndex());
                }
            }

            Member invMem = new Member(new MemberName("No member attached"), new MemberId(""),
                                        Collections.<Tag>emptySet());
            for (InvMemMapping mapping : invMemMappings) {
                if (mapping.getInventoryIndex() == i) {
                    invMem = memberList.get(mapping.getMemberIndex());
                }
            }

            IndivInventoryCard invCard = new IndivInventoryCard(invInvolved, invMem, i+1, invTask);
            inventoryCards.add(invCard);
        }

        try{
            inventoryListView.setItems(inventoryCards);
            inventoryListView.setCellFactory(listView -> new InventoryListViewCell());
        } catch(Exception e) {
            logger.info("the exception is: " + e.getLocalizedMessage());
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class InventoryListViewCell extends ListCell<IndivInventoryCard> {
        @Override
        protected void updateItem(IndivInventoryCard invCard, boolean empty) {
            super.updateItem(invCard, empty);

            if (empty || invCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(invCard.getRoot());
            }
        }
    }

}
