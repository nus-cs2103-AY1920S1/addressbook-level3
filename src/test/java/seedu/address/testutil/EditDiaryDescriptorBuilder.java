package seedu.address.testutil;

import seedu.address.logic.commands.EditDiaryCommand;
import seedu.address.logic.commands.EditDiaryCommand.EditDiaryDescriptor;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.Name;

/**
 * A utility class to help with building EditDiaryDescriptor objects.
 */
public class EditDiaryDescriptorBuilder {

    private EditDiaryCommand.EditDiaryDescriptor descriptor;

    public EditDiaryDescriptorBuilder() {
        descriptor = new EditDiaryCommand.EditDiaryDescriptor();
    }

    public EditDiaryDescriptorBuilder(EditDiaryDescriptor descriptor) {
        this.descriptor = new EditDiaryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDiaryDescriptor} with fields containing {@code diary}'s details
     */
    public EditDiaryDescriptorBuilder(Diary diary) {
        descriptor = new EditDiaryCommand.EditDiaryDescriptor();
        descriptor.setName(diary.getName());
    }

    /**
     * Sets the {@code Name} of the {@code EditDiaryDescriptor} that we are building.
     */
    public EditDiaryDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditDiaryCommand.EditDiaryDescriptor build() {
        return descriptor;
    }
}
