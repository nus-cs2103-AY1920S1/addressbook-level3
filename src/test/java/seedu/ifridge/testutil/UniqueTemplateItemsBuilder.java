package seedu.ifridge.testutil;

import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_ITEM_CHEESE;
import static seedu.ifridge.logic.commands.templatelist.TemplateCommandTestUtil.TEMPLATE_ITEM_RICE;

import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * A utility class to help with building UniqueTemplateItems objects.
 * Example usage: <br>
 *     {@code TemplateList tl = new TemplateListBuilder().withTemplateItem("John", "300g").build();}
 */
public class UniqueTemplateItemsBuilder {

    private static Name DEFAULT_TEMPLATE_NAME = new Name("Apple Pie");

    private UniqueTemplateItems template;

    public UniqueTemplateItemsBuilder() {
        template = new UniqueTemplateItems(DEFAULT_TEMPLATE_NAME);
        template.add(TEMPLATE_ITEM_CHEESE);
        template.add(TEMPLATE_ITEM_RICE);
    }

    public UniqueTemplateItemsBuilder(UniqueTemplateItems copy) {
        template = new UniqueTemplateItems(copy.getName());
        template.setTemplateItems(copy);
    }

    /**
     * Sets the {@code Name} for the {@code UniqueTemplateItems} that we are building.
     */
    public UniqueTemplateItemsBuilder withName(Name name) {
        template = new UniqueTemplateItems(name);
        return this;
    }

    /**
     * Adds a new {@code TemplateItem} to the {@code UniqueTemplateItems} that we are building.
     */
    public UniqueTemplateItemsBuilder withTemplateItem(TemplateItem templateItem) {
        template.add(templateItem);
        return this;
    }

    /**
     * Adds all {@code TemplateItem} into the {@code UniqueTemplateItems} that we are building.
     */
    public UniqueTemplateItemsBuilder withTemplateItems(UniqueTemplateItems template) {
        template.setTemplateItems(template);
        return this;
    }

    /**
     * Replaces a template item at the specified index with the new template item.
     */
    public UniqueTemplateItems setTemplateItem(TemplateItem prevItem, TemplateItem newItem) {
        template.setTemplateItem(prevItem, newItem);
        return this.template;
    }

    public UniqueTemplateItems build() {
        return template;
    }

}
