package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.company.Company;
import seedu.address.model.company.GstRegistrationNumber;
import seedu.address.model.company.RegistrationNumber;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Jackson-friendly version of company information.
 */
public class JsonAdaptedCompany {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Company's %s field is missing!";

    private String name;
    private String address;
    private String phone;
    private String fax;
    private String email;
    private String registrationNumber;
    private String gstRegistrationNumber;

    /**
     * Converts a given {@code JsonAdaptedCompany} into this class for Jackson use.
     * Used for saving into JSON.
     */
    public JsonAdaptedCompany(Company company) {
        name = company.getName().fullName;
        address = company.getAddress().value;
        phone = company.getPhone().value;
        fax = company.getFax().value;
        email = company.getEmail().value;
        registrationNumber = company.getRegistrationNumber().getRegistrationNumber();

        Optional<GstRegistrationNumber> optionalGstRegistrationNumber = company.getGstRegistrationNumber();
        if (optionalGstRegistrationNumber.isPresent()) {
            gstRegistrationNumber = optionalGstRegistrationNumber.get().getGstRegistrationNumber();
        } else {
            gstRegistrationNumber = null;
        }
    }


    /**
     * Constructs a {@code JsonAdaptedCompany} with the given company details.
     * Used for loading from JSON.
     */
    public JsonAdaptedCompany(@JsonProperty("name") String name, @JsonProperty("address") String address,
                              @JsonProperty("phone") String phone, @JsonProperty("fax") String fax,
                              @JsonProperty("companyEmail") String email,
                              @JsonProperty("registrationNumber") String registrationNumber,
                              @JsonProperty("GstRegistrationNumber") String gstRegistrationNumber) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.registrationNumber = registrationNumber;
        this.gstRegistrationNumber = gstRegistrationNumber;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Company toModelType() throws IllegalValueException {
        //company name
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        //company phone
        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        //company fax
        if (fax == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Fax"));
        }
        if (!Phone.isValidPhone(fax)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelFax = new Phone(fax);

        //company address
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        //company email
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        //company registration no
        if (registrationNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RegistrationNumber.class.getSimpleName()));
        }
        if (!RegistrationNumber.isValidRegistrationNumber(registrationNumber)) {
            throw new IllegalValueException(RegistrationNumber.MESSAGE_CONSTRAINTS);
        }
        final RegistrationNumber modelRegistrationNumber = new RegistrationNumber(registrationNumber);

        //company gst registration no, can be null
        if (gstRegistrationNumber != null
                && !GstRegistrationNumber.isValidGstRegistrationNumber(gstRegistrationNumber)) {
            throw new IllegalValueException(GstRegistrationNumber.MESSAGE_CONSTRAINTS);
        }
        final Optional<GstRegistrationNumber> modelGstRegistrationNumber = (gstRegistrationNumber == null)
                ? Optional.empty()
                : Optional.of(new GstRegistrationNumber(gstRegistrationNumber));

        return new Company(modelName, modelAddress, modelPhone, modelFax, modelEmail,
                modelRegistrationNumber, modelGstRegistrationNumber);
    }
}
