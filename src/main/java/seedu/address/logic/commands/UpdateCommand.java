package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FAX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GST_REGISTRATION_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGISTRATION_NUMBER;
import static seedu.address.model.company.GstRegistrationNumber.isEmptyRepresentation;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.company.Company;
import seedu.address.model.company.GstRegistrationNumber;
import seedu.address.model.company.RegistrationNumber;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Registers the company information for generating Delivery Order in PDF format.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the company information.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_FAX + "FAX] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_REGISTRATION_NUMBER + "REG. NO.] "
            + "[" + PREFIX_GST_REGISTRATION_NUMBER + "GST REG. NO.] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Deliveria Pte Ltd "
            + PREFIX_ADDRESS + "10 Kaki Bukit View, Singapore 630141 "
            + PREFIX_PHONE + "67227631 "
            + PREFIX_FAX + "67228822 "
            + PREFIX_EMAIL + "delivery@deliveria.com.sg "
            + PREFIX_REGISTRATION_NUMBER + "201952689D "
            + PREFIX_GST_REGISTRATION_NUMBER + "201952689D ";

    public static final String MESSAGE_NOTHING_IS_PROVIDED = "At least 1 field needs to be provided";

    public static final String MESSAGE_NOTHING_TO_EDIT = "At least 1 field needs to be different to edit. \n"
            + "Current: %1$s";
    public static final String MESSAGE_SUCCESS = "Company Information has been successfully updated. \n"
            + "Updated Company's Information: %1$s";

    private CompanyDescriptor editCompanyDescriptor;

    public UpdateCommand(CompanyDescriptor editCompanyDescriptor) {
        this.editCompanyDescriptor = new CompanyDescriptor(editCompanyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Company companyToEdit = model.getCompany();
        Company editedCompany = createEditedCompany(companyToEdit, editCompanyDescriptor);

        if (companyToEdit.equals(editedCompany)) {
            throw new CommandException(String.format(MESSAGE_NOTHING_TO_EDIT, companyToEdit));
        }

        model.setCompany(editedCompany);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedCompany));
    }

    /**
     * Creates and returns a {@code Company} with the details of {@code companyToEdit}
     * edited with {@code companyDescriptor}.
     */
    private static Company createEditedCompany(Company companyToEdit, CompanyDescriptor companyDescriptor) {
        assert companyToEdit != null;

        Name updatedName = companyDescriptor.getName().orElse(companyToEdit.getName());
        Address updatedAddress = companyDescriptor.getAddress().orElse(companyToEdit.getAddress());
        Phone updatedPhone = companyDescriptor.getPhone().orElse(companyToEdit.getPhone());
        Phone updatedFax = companyDescriptor.getFax().orElse(companyToEdit.getFax());
        Email updatedEmail = companyDescriptor.getEmail().orElse(companyToEdit.getEmail());
        RegistrationNumber updatedRegistrationNumber = companyDescriptor.getRegistrationNumber().orElse(
                companyToEdit.getRegistrationNumber());

        //if updated value is present, take updated or else take original value
        //if updated value is a "-", then assign empty.
        Optional<GstRegistrationNumber> gstRegistrationNumber = companyDescriptor.getGstRegistrationNumber();
        Optional<GstRegistrationNumber> updatedGstRegistrationNumber = gstRegistrationNumber
                .or(companyToEdit::getGstRegistrationNumber);

        Optional<GstRegistrationNumber> updatedGstRegNoRepresentation =
                getOptionalEmptyIfEmptyRepresentation(updatedGstRegistrationNumber);

        return new Company(updatedName, updatedAddress, updatedPhone, updatedFax, updatedEmail,
                updatedRegistrationNumber, updatedGstRegNoRepresentation);
    }

    /**
     * Checks if {@code GstRegistrationNumber} is a empty representation.
     * Returns {@code Optional.empty()} if it is, otherwise, return the same variable.
     */
    private static Optional<GstRegistrationNumber> getOptionalEmptyIfEmptyRepresentation(
            Optional<GstRegistrationNumber> optionalGstRegNo) {
        if (optionalGstRegNo.isPresent()) {
            String gstRegNo = optionalGstRegNo.get().getGstRegistrationNumber();
            if (isEmptyRepresentation(gstRegNo)) {
                return Optional.empty();
            }
        }

        return optionalGstRegNo;
    }

    /**
     * Stores the details to edit the {@link Company} with. Each non-empty field value will replace the
     * corresponding field value of the {@link Company};
     */
    public static class CompanyDescriptor {

        private Name name;
        private Address address;
        private Phone phone;
        private Phone fax;
        private Email email;
        private RegistrationNumber registrationNumber;
        private GstRegistrationNumber gstRegistrationNumber;

        public CompanyDescriptor() {

        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public CompanyDescriptor(CompanyDescriptor toCopy) {
            setName(toCopy.name);
            setAddress(toCopy.address);
            setPhone(toCopy.phone);
            setFax(toCopy.fax);
            setEmail(toCopy.email);
            setRegistrationNumber(toCopy.registrationNumber);
            setGstRegistrationNumber(toCopy.gstRegistrationNumber);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, address, phone, fax, email,
                    registrationNumber, gstRegistrationNumber);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getFax() {
            return Optional.ofNullable(fax);
        }

        public void setFax(Phone fax) {
            this.fax = fax;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<RegistrationNumber> getRegistrationNumber() {
            return Optional.ofNullable(registrationNumber);
        }

        public void setRegistrationNumber(RegistrationNumber registrationNumber) {
            this.registrationNumber = registrationNumber;
        }

        public Optional<GstRegistrationNumber> getGstRegistrationNumber() {
            return Optional.ofNullable(gstRegistrationNumber);
        }

        public void setGstRegistrationNumber(GstRegistrationNumber gstRegistrationNumber) {
            this.gstRegistrationNumber = gstRegistrationNumber;
        }
    }
}

