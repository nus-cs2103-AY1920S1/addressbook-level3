package seedu.address.testutil;

import seedu.address.model.food.Name;
import seedu.address.model.food.TemplateItem;
import seedu.address.model.food.UniqueTemplateItems;

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

    /**
     * Adds a new {@code TemplateItem} to the {@code UniqueTemplateItems} that we are building.
     */
    public UniqueTemplateItemsBuilder withTemplateItem(TemplateItem templateItem) {
        template.add(templateItem);
        return this;
    }

    public UniqueTemplateItems build() {
        return template;
    }

}
