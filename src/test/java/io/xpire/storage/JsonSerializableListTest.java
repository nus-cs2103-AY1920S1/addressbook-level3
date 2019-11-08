package io.xpire.storage;

import static io.xpire.testutil.TypicalItems.*;
import static io.xpire.testutil.TypicalItemsFields.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReplenishList;
import io.xpire.model.Xpire;
import io.xpire.model.item.*;
import io.xpire.model.tag.Tag;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.Test;

public class JsonSerializableListTest {

    private static final List<JsonAdaptedTag> VALID_TAGS = new ArrayList<>() {{
        add(new JsonAdaptedTag(new Tag(VALID_TAG_FRIDGE)));
    }};

    @Test
    public void toModelType_validItemLists_returnsLists() throws Exception {
        Xpire xpire = new Xpire();
        List<XpireItem> xpireItems = Arrays.asList(JELLY, CORIANDER);
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
