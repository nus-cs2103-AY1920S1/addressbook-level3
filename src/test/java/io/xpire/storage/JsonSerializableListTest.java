package io.xpire.storage;

import static io.xpire.testutil.TypicalItems.CHOCOLATE;
import static io.xpire.testutil.TypicalItems.CORIANDER;
import static io.xpire.testutil.TypicalItems.FISH;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReplenishList;
import io.xpire.model.Xpire;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import javafx.collections.FXCollections;

public class JsonSerializableListTest {

    @Test
    public void toModelType_validItemLists_returnsLists() throws Exception {
        Xpire xpire = new Xpire();
        List<XpireItem> xpireItems = Arrays.asList(FISH, CORIANDER);
        xpire.setItems(FXCollections.observableArrayList(xpireItems));
        ReplenishList replenishList = new ReplenishList();
        replenishList.addItem(CHOCOLATE);
        ReadOnlyListView<? extends Item>[] lists = new ReadOnlyListView<?>[]{xpire, replenishList};
        JsonSerializableList readOnlyListView = new JsonSerializableList(lists);
        assertArrayEquals(lists, readOnlyListView.toModelType());
    }

    @Test
    public void toModelType_emptyItemLists_returnsLists() throws Exception {
        Xpire xpire = new Xpire();
        ReplenishList replenishList = new ReplenishList();
        ReadOnlyListView<? extends Item>[] lists = new ReadOnlyListView<?>[]{xpire, replenishList};
        JsonSerializableList readOnlyListView = new JsonSerializableList(lists);
        assertArrayEquals(lists, readOnlyListView.toModelType());
    }
}
