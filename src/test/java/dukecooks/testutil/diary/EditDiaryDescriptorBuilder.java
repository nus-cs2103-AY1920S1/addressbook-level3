package dukecooks.testutil.diary;

import dukecooks.logic.commands.diary.EditDiaryCommand;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryName;

/**
 * A utility class to help with building EditDiaryDescriptor objects.
 */
public class EditDiaryDescriptorBuilder {

    private EditDiaryCommand.EditDiaryDescriptor descriptor;

    public EditDiaryDescriptorBuilder() {
        descriptor = new EditDiaryCommand.EditDiaryDescriptor();
    }

    public EditDiaryDescriptorBuilder(EditDiaryCommand.EditDiaryDescriptor descriptor) {
        this.descriptor = new EditDiaryCommand.EditDiaryDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditDiaryDescriptor} with fields containing {@code diary}'s details
     */
    public EditDiaryDescriptorBuilder(Diary diary) {
        descriptor = new EditDiaryCommand.EditDiaryDescriptor();
        descriptor.setDiaryName(diary.getDiaryName());;
    }

    /**
     * Sets the {@code DiaryName} of the {@code EditDiaryDescriptor} that we are building.
     */
    public EditDiaryDescriptorBuilder withName(String name) {
        descriptor.setDiaryName(new DiaryName(name));
        return this;
    }

    public EditDiaryCommand.EditDiaryDescriptor build() {
        return descriptor;
    }
}
