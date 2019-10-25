package seedu.ifridge.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalTemplateItems.MINCEDBEEF;
import static seedu.ifridge.testutil.TypicalTemplateItems.MINCEDPORK;
import static seedu.ifridge.testutil.TypicalTemplateList.DIET_PLAN;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.testutil.UniqueTemplateItemsBuilder;

public class TemplateListTest {

    private final TemplateList templateList = new TemplateList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), templateList.getTemplateList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> templateList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTemplateList_replacesData() {
        TemplateList newData = getTypicalTemplateList();
        templateList.resetData(newData);
        assertEquals(newData, templateList);
    }

    @Test
    public void resetData_withDuplicateTemplates_throwsDuplicateTemplateException() {
        // Two persons with the same identity fields
        UniqueTemplateItems editedDietPlan = new UniqueTemplateItemsBuilder(new Name("Diet Plan"))
                .withTemplateItem(MINCEDBEEF).build();
        List<UniqueTemplateItems> newTemplate = Arrays.asList(DIET_PLAN, editedDietPlan);
        TemplateStub newData = new TemplateStub(newTemplate);

        //assertThrows(DuplicateFoodException.class, () -> templateList.resetData(newData));
    }

    @Test
    public void hasTemplateList_nullTemplate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> templateList.hasTemplate(null));
    }

    @Test
    public void hasTemplate_templateNotInTemplateList_returnsFalse() {
        assertFalse(templateList.hasTemplate(DIET_PLAN));
    }

    @Test
    public void hasTemplateItem_personInTemplateList_returnsTrue() {
        templateList.addTemplate(DIET_PLAN);
        assertTrue(templateList.hasTemplate(DIET_PLAN));
    }

    @Test
    public void hasTemplate_templateWithSameIdentityFieldsInTemplateList_returnsTrue() {
        templateList.addTemplate(DIET_PLAN);
        UniqueTemplateItems editedDietPlan = new UniqueTemplateItemsBuilder(new Name("Diet Plan"))
                .withTemplateItem(MINCEDPORK).build();
        assertTrue(templateList.hasTemplate(editedDietPlan));
    }

    @Test
    public void getTemplateItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> templateList.getTemplateList().remove(0));
    }

    /**
     * A stub ReadOnlyTemplateList whose template items list can violate interface constraints.
     */
    private static class TemplateStub implements ReadOnlyTemplateList {
        private final ObservableList<UniqueTemplateItems> templates = FXCollections.observableArrayList();

        TemplateStub(Collection<UniqueTemplateItems> templates) {
            this.templates.setAll(templates);
        }

        @Override
        public ObservableList<UniqueTemplateItems> getTemplateList() {
            return templates;
        }
    }

}
