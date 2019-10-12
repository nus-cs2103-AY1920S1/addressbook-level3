package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditDiaryDescriptor;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;

/**
 * A utility class to help with building EditDiaryDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditCommand.EditDiaryDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditCommand.EditDiaryDescriptor();
    }

    public EditPersonDescriptorBuilder(EditDiaryDescriptor descriptor) {
        this.descriptor = new EditDiaryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDiaryDescriptor} with fields containing {@code diary}'s details
     */
    public EditPersonDescriptorBuilder(Diary diary) {
        descriptor = new EditCommand.EditDiaryDescriptor();
        descriptor.setName(diary.getName());
    }

    /**
     * Sets the {@code Name} of the {@code EditDiaryDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditCommand.EditDiaryDescriptor build() {
        return descriptor;
    }
}
