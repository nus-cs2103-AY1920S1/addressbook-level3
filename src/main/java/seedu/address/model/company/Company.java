package seedu.address.model.company;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Represents a company or an organisation.
 */
public class Company {

    private static final Name DEFAULT_NAME = new Name("Deliveria Pte Ltd");
    private static final Address DEFAULT_ADDRESS = new Address("10 Kaki Bukit View, Singapore 630141");
    private static final Phone DEFAULT_PHONE = new Phone("67227631");
    private static final Phone DEFAULT_FAX = new Phone("67228822");
    private static final Email DEFAULT_EMAIL = new Email("delivery@deliveria.com.sg");
    private static final RegistrationNumber DEFAULT_REGISTRATION_NUMBER = new RegistrationNumber("201952689D");
    private static final Optional<GstRegistrationNumber> DEFAULT_GST_REGISTRATION_NUMBER =
            Optional.of(new GstRegistrationNumber("M263700219"));

    private Name name;
    private Address address;
    private Phone phone;
    private Phone fax;
    private Email email;
    private RegistrationNumber registrationNumber;
    private Optional<GstRegistrationNumber> gstRegistrationNumber;

    public Company() {
        this.name = DEFAULT_NAME;
        this.address = DEFAULT_ADDRESS;
        this.phone = DEFAULT_PHONE;
        this.fax = DEFAULT_FAX;
        this.email = DEFAULT_EMAIL;
        this.registrationNumber = DEFAULT_REGISTRATION_NUMBER;
        this.gstRegistrationNumber = DEFAULT_GST_REGISTRATION_NUMBER;
    }

    public Company(Name companyName, Address companyAddress, Phone companyPhone, Phone companyFax,
                   Email companyEmail, RegistrationNumber registrationNumber,
                   Optional<GstRegistrationNumber> gstRegistrationNumber) {
        this.name = companyName;
        this.address = companyAddress;
        this.phone = companyPhone;
        this.fax = companyFax;
        this.email = companyEmail;
        this.registrationNumber = registrationNumber;
        this.gstRegistrationNumber = gstRegistrationNumber;
    }

    public void setCompany(Company company) {
        setName(company.getName());
        setAddress(company.getAddress());
        setPhone(company.getPhone());
        setFax(company.getFax());
        setEmail(company.getEmail());
        setRegistrationNumber(company.getRegistrationNumber());
        setGstRegistrationNumber(company.getGstRegistrationNumber());
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Phone getPhone() {
        return phone;
    }

    public Phone getFax() {
        return fax;
    }

    public Email getEmail() {
        return email;
    }

    public RegistrationNumber getRegistrationNumber() {
        return registrationNumber;
    }

    public Optional<GstRegistrationNumber> getGstRegistrationNumber() {
        return gstRegistrationNumber;
    }

    public void setName(Name companyName) {
        this.name = companyName;
    }

    public void setAddress(Address companyAddress) {
        this.address = companyAddress;
    }

    public void setPhone(Phone companyPhone) {
        this.phone = companyPhone;
    }

    public void setFax(Phone companyFax) {
        this.fax = companyFax;
    }

    public void setEmail(Email companyEmail) {
        this.email = companyEmail;
    }

    public void setRegistrationNumber(RegistrationNumber registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setGstRegistrationNumber(Optional<GstRegistrationNumber> gstRegistrationNumber) {
        this.gstRegistrationNumber = gstRegistrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        return Objects.equals(getName(), company.getName())
                && Objects.equals(getAddress(), company.getAddress())
                && Objects.equals(getPhone(), company.getPhone())
                && Objects.equals(getFax(), company.getFax())
                && Objects.equals(getEmail(), company.getEmail())
                && Objects.equals(getRegistrationNumber(), company.getRegistrationNumber())
                && Objects.equals(getGstRegistrationNumber(), company.getGstRegistrationNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAddress(), getPhone(), getFax(), getEmail(),
                getRegistrationNumber(), getGstRegistrationNumber());
    }

    @Override
    public String toString() {
        StringBuilder companyStr = new StringBuilder();
        String gstRegistrationNumberPrint = getGstRegistrationNumber()
                .map(GstRegistrationNumber::toString)
                .orElse("-");
        return companyStr.append("Company Name: ")
                .append(getName())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tel No.: ")
                .append(getPhone())
                .append(" Fax No.: ")
                .append(getFax())
                .append(" Email: ")
                .append(getEmail())
                .append(" Co. Reg. No.: ")
                .append(getRegistrationNumber())
                .append(" GST Reg. No.: ")
                .append(gstRegistrationNumberPrint)
                .toString();
    }
}
