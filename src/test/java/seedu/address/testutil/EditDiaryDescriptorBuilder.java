package seedu.address.testutil;

import seedu.address.logic.commands.EditDiaryCommand.EditDiaryDescriptor;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.DiaryName;

/**
 * A utility class to help with building EditDiaryDescriptor objects.
 */
public class EditDiaryDescriptorBuilder {

    private EditDiaryDescriptor descriptor;

    public EditDiaryDescriptorBuilder() {
        descriptor = new EditDiaryDescriptor();
    }

    public EditDiaryDescriptorBuilder(EditDiaryDescriptor descriptor) {
        this.descriptor = new EditDiaryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDiaryDescriptor} with fields containing {@code diary}'s details
     */
    public EditDiaryDescriptorBuilder(Diary diary) {
        descriptor = new EditDiaryDescriptor();
        descriptor.setDiaryName(diary.getDiaryName());;
    }

    /**
     * Sets the {@code DiaryName} of the {@code EditDiaryDescriptor} that we are building.
     */
    public EditDiaryDescriptorBuilder withName(String name) {
        descriptor.setDiaryName(new DiaryName(name));
        return this;
    }

    public EditDiaryDescriptor build() {
        return descriptor;
    }
}
