package organice.testutil;

import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.Donor;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Phone;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * A utility class to build Donor objects.
 */
public class DonorBuilder extends PersonBuilder {
    public static final String DEFAULT_AGE = "60";
    public static final String DEFAULT_BLOOD_TYPE = "A";
    public static final String DEFAULT_TISSUE_TYPE = "1,2,3,4,5,6";
    public static final String DEFAULT_ORGAN = "kidney";
    public static final String DEFAULT_ORGAN_EXPIRY_DATE = "01-Jan-2020";

    private Age age;
    private BloodType bloodType;
    private TissueType tissueType;
    private Organ organ;
    private OrganExpiryDate organExpiryDate;

    public DonorBuilder() {
        super();
        age = new Age(DEFAULT_AGE);
        type = new Type("donor");
        bloodType = new BloodType(DEFAULT_BLOOD_TYPE);
        tissueType = new TissueType(DEFAULT_TISSUE_TYPE);
        organ = new Organ(DEFAULT_ORGAN);
        organExpiryDate = new OrganExpiryDate(DEFAULT_ORGAN_EXPIRY_DATE);
    }

    /**
     * Initialises the DonorBuilder with the data of {@code donorToCopy}
     */
    public DonorBuilder(Donor donorToCopy) {
        super(donorToCopy);
        age = donorToCopy.getAge();
        bloodType = donorToCopy.getBloodType();
        tissueType = donorToCopy.getTissueType();
        organ = donorToCopy.getOrgan();
        organExpiryDate = donorToCopy.getOrganExpiryDate();
    }

    /**
     * Sets the {@code Age} of the {@code Donor} we are building
     */
    public DonorBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Donor} that we are building.
     */
    @Override
    public DonorBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }
    /**
     * Sets the {@code Name} of the {@code Donor} that we are building.
     */
    @Override
    public DonorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Donor} that we are building.
     */
    @Override
    public DonorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code BloodType} of the {@code Donor} that we are building.
     */
    public DonorBuilder withBloodType(String bloodType) {
        this.bloodType = new BloodType(bloodType);
        return this;
    }

    /**
     * Sets the {@code TissueType} of the {@code Donor} that we are building.
     */
    public DonorBuilder withTissueType(String tissueType) {
        this.tissueType = new TissueType(tissueType);
        return this;
    }

    /**
     * Sets the {@code Organ} of the {@code Donor} that we are building.
     */
    public DonorBuilder withOrgan(String organ) {
        this.organ = new Organ(organ);
        return this;
    }

    /**
     * Sets the {@code OrganExpiryDate} of the {@code Donor} that we are building.
     */
    public DonorBuilder withOrganExpiryDate(String organExpiryDate) {
        this.organExpiryDate = new OrganExpiryDate(organExpiryDate);
        return this;
    }

    public Donor build() {
        return new Donor(type, nric, name, phone, age, bloodType, tissueType, organ, organExpiryDate);
    }
}
