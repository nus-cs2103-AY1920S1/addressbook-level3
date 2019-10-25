package seedu.ifridge.testutil;

import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * A utility class to help with building TemplateList objects.
 * Example usage: <br>
 *     {@code TemplateList tl = new TemplateListBuilder().withTemplateItem("John", "300g").build();}
 */
public class TemplateListBuilder {

    private TemplateList templateList;

    public TemplateListBuilder() {
        templateList = new TemplateList();
    }

    public TemplateListBuilder(TemplateList templateList) {
        this.templateList = templateList;
    }

    /**
     * Adds a new {@code TemplateItem} to the {@code TemplateList} that we are building.
     */
    public TemplateListBuilder withTemplateItem(UniqueTemplateItems template) {
        templateList.addTemplate(template);
        return this;
    }

    public TemplateList build() {
        return templateList;
    }
}
