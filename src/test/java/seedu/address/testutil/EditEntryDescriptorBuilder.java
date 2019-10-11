//package seedu.address.testutil;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import seedu.address.logic.commands.EditCommand.EditEntryDescriptor;
//import seedu.address.model.person.Address;
//import seedu.address.model.person.Amount;
//import seedu.address.model.person.Email;
//import seedu.address.model.person.Description;
//import seedu.address.model.person.Entry;
//import seedu.address.model.person.Phone;
//import seedu.address.model.tag.Tag;
//
///**
// * A utility class to help with building EditPersonDescriptor objects.
// */
//public class EditEntryDescriptorBuilder {
//
//    private EditEntryDescriptor descriptor;
//
//    public EditEntryDescriptorBuilder() {
//        descriptor = new EditEntryDescriptor();
//    }
//
//    public EditEntryDescriptorBuilder(EditEntryDescriptor descriptor) {
//        this.descriptor = new EditEntryDescriptor(descriptor);
//    }
//
//    /**
//     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
//     */
//    public EditEntryDescriptorBuilder(Entry entry) {
//        descriptor = new EditEntryDescriptor();
//        descriptor.setDesc(entry.getDesc());
//        descriptor.setAmount(entry.getAmount());
////        descriptor.setPhone(person.getPhone());
////        descriptor.setEmail(person.getEmail());
////        descriptor.setAddress(person.getAddress());
//        descriptor.setTags(entry.getTags());
//    }
//
//    /**
//     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
//     */
//    public EditEntryDescriptorBuilder withDescription(String desc) {
//        descriptor.setDesc(new Description(desc));
//        return this;
//    }
//
//    public EditEntryDescriptorBuilder withAmount(double amt) {
//        descriptor.setAmount(new Amount(amt));
//        return this;
//    }
//
////    /**
////     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
////     */
////    public EditEntryDescriptorBuilder withPhone(String phone) {
////        descriptor.setPhone(new Phone(phone));
////        return this;
////    }
////
////    /**
////     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
////     */
////    public EditEntryDescriptorBuilder withEmail(String email) {
////        descriptor.setEmail(new Email(email));
////        return this;
////    }
////
////    /**
////     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
////     */
////    public EditEntryDescriptorBuilder withAddress(String address) {
////        descriptor.setAddress(new Address(address));
////        return this;
////    }
//
//    /**
//     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
//     * that we are building.
//     */
//    public EditEntryDescriptorBuilder withTags(String... tags) {
//        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
//        descriptor.setTags(tagSet);
//        return this;
//    }
//
//    public EditEntryDescriptor build() {
//        return descriptor;
//    }
//}
