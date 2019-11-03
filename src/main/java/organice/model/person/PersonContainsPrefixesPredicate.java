package organice.model.person;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import organice.commons.util.StringUtil;
import organice.logic.parser.ArgumentMultimap;

/**
 * Tests that a {@code Person}'s prefixes matches any of the prefix-keyword pairs given.
 */
public class PersonContainsPrefixesPredicate implements Predicate<Person> {
    private final ArgumentMultimap argMultimap;

    public PersonContainsPrefixesPredicate(ArgumentMultimap argMultimap) {
        this.argMultimap = argMultimap;
    }

    @Override
    public boolean test(Person person) {
        // Get all prefixes. Test all prefixes. All predicates must pass for person to appear
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

        // Separate all comma delimited tissue types
        String tissues = tissueTypeKeywords.stream().reduce("", (prev, next) -> prev + " " + next)
                .replaceAll(",", " ").trim();
        tissueTypeKeywords = tissues.isEmpty() ? new ArrayList<>() : Arrays.asList(tissues.split("\\s+"));

        // Early return if argMultimap is empty
        if (nameKeywords.isEmpty() && nricKeywords.isEmpty() && phoneKeywords.isEmpty() && typeKeywords.isEmpty()
                && ageKeywords.isEmpty() && priorityKeywords.isEmpty() && bloodTypeKeywords.isEmpty()
                && doctorInChargeKeywords.isEmpty() && tissueTypeKeywords.isEmpty() && organExpiryDateKeywords.isEmpty()
                && organKeywords.isEmpty()) {
            return false;
        }

        return (nameKeywords.isEmpty() || matchAny(nameKeywords, person.getName().toString()))
                && (nricKeywords.isEmpty() || matchAny(nricKeywords, person.getNric().toString()))
                && (phoneKeywords.isEmpty() || matchAny(phoneKeywords, person.getPhone().toString()))
                && (typeKeywords.isEmpty() || matchAny(typeKeywords, person.getType().toString()))
                && (ageKeywords.isEmpty() || (person.getType().isPatient()
                    ? matchAny(ageKeywords, ((Patient) person).getAge().toString())
                    : person.getType().isDonor() && matchAny(ageKeywords, ((Donor) person).getAge().toString())))
                && (priorityKeywords.isEmpty() || person.getType().isPatient()
                    && matchAny(priorityKeywords, ((Patient) person).getPriority().toString()))
                && (bloodTypeKeywords.isEmpty() || (person.getType().isPatient()
                    ? matchAny(bloodTypeKeywords, ((Patient) person).getBloodType().toString())
                    : person.getType().isDonor()
                        && matchAny(bloodTypeKeywords, ((Donor) person).getBloodType().toString())))
                && (doctorInChargeKeywords.isEmpty() || person.getType().isPatient()
                    && matchAny(doctorInChargeKeywords, ((Patient) person).getDoctorInCharge().toString()))
                && (tissueTypeKeywords.isEmpty() || (person.getType().isPatient()
                    ? matchAll(tissueTypeKeywords, ((Patient) person).getTissueType().toString())
                    : person.getType().isDonor()
                        && matchAll(tissueTypeKeywords, ((Donor) person).getTissueType().toString())))
                && (organExpiryDateKeywords.isEmpty() || person.getType().isDonor()
                    && matchAny(organExpiryDateKeywords, ((Donor) person).getOrganExpiryDate().toString()))
                && (organKeywords.isEmpty() || (person.getType().isPatient()
                    ? matchAny(organKeywords, ((Patient) person).getOrgan().toString())
                    : person.getType().isDonor() && matchAny(organKeywords, ((Donor) person).getOrgan().toString())));
    }

    private boolean matchAny(List<String> prefixKeywords, String personAttribute) {
        return prefixKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsPhraseIgnoreCase(personAttribute, keyword));
    }

    private boolean matchAll(List<String> prefixKeywords, String personAttribute) {
        return prefixKeywords.stream()
                .allMatch(keyword -> StringUtil.containsPhraseIgnoreCase(personAttribute, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsPrefixesPredicate // instanceof handles nulls
                && argMultimap.equals(((PersonContainsPrefixesPredicate) other).argMultimap)); // state matchAny
    }

}
