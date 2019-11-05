//package seedu.address.testutil;
//
//import seedu.address.model.GuiltTrip;
//import seedu.address.model.entry.Person;
//
///**
// * A utility class to help with building Addressbook objects.
// * Example usage: <br>
// *     {@code GuiltTrip ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
// */
//public class AddressBookBuilder {
//
//    private GuiltTrip addressBook;
//
//    public AddressBookBuilder() {
//        addressBook = new GuiltTrip();
//    }
//
//    public AddressBookBuilder(GuiltTrip addressBook) {
//        this.addressBook = addressBook;
//    }
//
//    /**
//     * Adds a new {@code Person} to the {@code GuiltTrip} that we are building.
//     */
//    public AddressBookBuilder withPerson(Person entry) {
//        addressBook.addPerson(entry);
//        return this;
//    }
//
//    public GuiltTrip build() {
//        return addressBook;
//    }
//}
