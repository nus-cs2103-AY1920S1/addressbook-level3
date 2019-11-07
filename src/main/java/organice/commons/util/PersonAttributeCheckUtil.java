package organice.commons.util;

import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;

import organice.logic.parser.ArgumentMultimap;
import organice.model.person.Age;
import organice.model.person.BloodType;
import organice.model.person.DoctorInCharge;
import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Organ;
import organice.model.person.OrganExpiryDate;
import organice.model.person.Phone;
import organice.model.person.Priority;
import organice.model.person.TissueType;
import organice.model.person.Type;

/**
 * Helper functions for checking properties or validity of Person and Person child class attributes.
 */
public class PersonAttributeCheckUtil {

    /**
     * Checks that the values in the given {@code argMultimap} are valid.
     */
    public static void checkValidityAttributes(ArgumentMultimap argMultimap) {
        // attempt to create the attribute from values in multimap
        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> nricKeywords = argMultimap.getAllValues(PREFIX_NRIC);
        List<String> phoneKeywords = argMultimap.getAllValues(PREFIX_PHONE);
        List<String> typeKeywords = argMultimap.getAllValues(PREFIX_TYPE);
        List<String> ageKeywords = argMultimap.getAllValues(PREFIX_AGE);
        List<String> priorityKeywords = argMultimap.getAllValues(PREFIX_PRIORITY);
        List<String> bloodTypeKeywords = argMultimap.getAllValues(PREFIX_BLOOD_TYPE);
        List<String> doctorInChargeKeywords = argMultimap.getAllValues(PREFIX_DOCTOR_IN_CHARGE);
        List<String> tissueTypeKeywords = argMultimap.getAllValues(PREFIX_TISSUE_TYPE);
        List<String> organExpiryDateKeywords = argMultimap.getAllValues(PREFIX_ORGAN_EXPIRY_DATE);
        List<String> organKeywords = argMultimap.getAllValues(PREFIX_ORGAN);

        // Attempt to create every single type of object. Relies on that object's constructor to throw exception
        nameKeywords.forEach(Name::new);
        nricKeywords.forEach(Nric::new);
        phoneKeywords.forEach(Phone::new);
        typeKeywords.forEach(Type::new);
        ageKeywords.forEach(Age::new);
        priorityKeywords.forEach(Priority::new);
        bloodTypeKeywords.forEach(BloodType::new);
        doctorInChargeKeywords.forEach(DoctorInCharge::new);
        tissueTypeKeywords.forEach(TissueType::new);
        organExpiryDateKeywords.forEach(OrganExpiryDate::new);
        organKeywords.forEach(Organ::new);
    }

}
