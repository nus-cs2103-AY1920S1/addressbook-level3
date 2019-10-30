package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DataBook;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Phone;

/**
 * A utility class containing a list of {@code Phone} objects to be used in tests.
 */
public class TypicalPhones {

    public static final Phone IPHONEONE = new PhoneBuilder().withIdentityNumber("111111111111111")
            .withSerialNumber("A342432").withName("iPhone 11").withBrand("iPhone")
            .withCapacity(Capacity.SIZE_32GB).withColour("White").withCost("$500").withTags("Old").build();

    public static final Phone IPHONETWO = new PhoneBuilder().withIdentityNumber("111111111122222")
            .withSerialNumber("A5864").withName("iPhone X").withBrand("iPhone")
            .withCapacity(Capacity.SIZE_64GB).withColour("Green").withCost("$300").withTags("Spoilt").build();

    public static final Phone ANDROIDONE = new PhoneBuilder().withIdentityNumber("111143242133111")
            .withSerialNumber("432").withName("Samsung Galaxy 9").withBrand("Samsung")
            .withCapacity(Capacity.SIZE_32GB).withColour("Black").withCost("$300").withTags("New").build();

    public static final Phone ANDROIDTWO = new PhoneBuilder().withIdentityNumber("222222222222222")
            .withSerialNumber("603").withName("Samsung Galaxy 10").withBrand("Samsung")
            .withCapacity(Capacity.SIZE_1024GB).withColour("Pink").withCost("$730").withTags("Fresh").build();

    public static final Phone IPHONEXR = new PhoneBuilder().withIdentityNumber("449682916074069")
            .withSerialNumber("182n8x81").withName("iPhone XR").withBrand("Apple")
            .withCapacity(Capacity.SIZE_128GB).withColour("Black").withCost("$1000").withTags("Used").build();

    public static final Phone IPHONEPRO11 = new PhoneBuilder().withIdentityNumber("013373005371667")
            .withSerialNumber("1d27s9az").withName("iPhone Pro 11").withBrand("Apple")
            .withCapacity(Capacity.SIZE_128GB).withColour("Purple").withCost("$1649").withTags("New")
            .withTags("Bestseller").build();

    public static final Phone IPHONE_JSON_TEST = new PhoneBuilder().withIdentityNumber("111111111111111")
            .withSerialNumber("A342432").withName("iPhone 11").withBrand("iPhone")
            .withCapacity(Capacity.SIZE_32GB).withColour("White").withCost("$5000").withTags("used").build();

    /**
     * Returns a {@code DataBook} with all the typical phones.
     */
    public static DataBook<Phone> getTypicalPhoneBook() {
        DataBook<Phone> pb = new DataBook<>();
        for (Phone p: getTypicalPhones()) {
            pb.add(p);
        }
        return pb;
    }

    public static List<Phone> getTypicalPhones() {
        return new ArrayList<>(Arrays.asList(IPHONEONE, IPHONETWO, IPHONEXR, ANDROIDONE, ANDROIDTWO));
    }
}
