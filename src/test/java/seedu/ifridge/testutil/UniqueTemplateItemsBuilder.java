package seedu.ifridge.testutil;

import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * A utility class to help with building UniqueTemplateItems objects.
 * Example usage: <br>
 *     {@code TemplateList tl = new TemplateListBuilder().withTemplateItem("John", "300g").build();}
 */
public class UniqueTemplateItemsBuilder {
    private UniqueTemplateItems template;

    public UniqueTemplateItemsBuilder(Name name) {
        template = new UniqueTemplateItems(name);
    }

    public UniqueTemplateItemsBuilder(UniqueTemplateItems copy) {
        template = new UniqueTemplateItems(copy.getName());
        template.setTemplateItems(copy);
    }

    /**
     * Adds a new {@code TemplateItem} to the {@code UniqueTemplateItems} that we are building.
     */
    public UniqueTemplateItemsBuilder withTemplateItem(TemplateItem templateItem) {
        template.add(templateItem);
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
