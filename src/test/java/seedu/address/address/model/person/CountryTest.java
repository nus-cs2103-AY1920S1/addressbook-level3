package seedu.address.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.address.model.country.Country;

public class CountryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Country(null));
    }

    @Test
    public void isValidCountry() {
        //null country
        assertThrows(NullPointerException.class, () -> Country.isValidCountry(null));

        // invalid parts
        assertFalse(Country.isValidCountry("Korea")); //South Korea
        assertFalse(Country.isValidCountry("singapore")); //Singapore
        assertFalse(Country.isValidCountry("America")); //United States
        assertFalse(Country.isValidCountry("United States of America")); //extra characters

        // valid countries
        assertTrue(Country.isValidCountry(""));
        assertTrue(Country.isValidCountry("Afghanistan"));
        assertTrue(Country.isValidCountry("Åland Islands"));
        assertTrue(Country.isValidCountry("Albania"));
        assertTrue(Country.isValidCountry("Algeria"));
        assertTrue(Country.isValidCountry("American Samoa"));
        assertTrue(Country.isValidCountry("Andorra"));
        assertTrue(Country.isValidCountry("Angola"));
        assertTrue(Country.isValidCountry("Anguilla"));
        assertTrue(Country.isValidCountry("Antarctica"));
        assertTrue(Country.isValidCountry("Antigua & Barbuda"));
        assertTrue(Country.isValidCountry("Argentina"));
        assertTrue(Country.isValidCountry("Armenia"));
        assertTrue(Country.isValidCountry("Aruba"));
        assertTrue(Country.isValidCountry("Australia"));
        assertTrue(Country.isValidCountry("Austria"));
        assertTrue(Country.isValidCountry("Azerbaijan"));
        assertTrue(Country.isValidCountry("Bahamas"));
        assertTrue(Country.isValidCountry("Bahrain"));
        assertTrue(Country.isValidCountry("Bangladesh"));
        assertTrue(Country.isValidCountry("Barbados"));
        assertTrue(Country.isValidCountry("Belarus"));
        assertTrue(Country.isValidCountry("Belgium"));
        assertTrue(Country.isValidCountry("Belize"));
        assertTrue(Country.isValidCountry("Benin"));
        assertTrue(Country.isValidCountry("Bermuda"));
        assertTrue(Country.isValidCountry("Bhutan"));
        assertTrue(Country.isValidCountry("Bolivia"));
        assertTrue(Country.isValidCountry("Bosnia & Herzegovina"));
        assertTrue(Country.isValidCountry("Botswana"));
        assertTrue(Country.isValidCountry("Bouvet Island"));
        assertTrue(Country.isValidCountry("Brazil"));
        assertTrue(Country.isValidCountry("British Indian Ocean Territory"));
        assertTrue(Country.isValidCountry("British Virgin Islands"));
        assertTrue(Country.isValidCountry("Brunei"));
        assertTrue(Country.isValidCountry("Bulgaria"));
        assertTrue(Country.isValidCountry("Burkina Faso"));
        assertTrue(Country.isValidCountry("Burundi"));
        assertTrue(Country.isValidCountry("Cambodia"));
        assertTrue(Country.isValidCountry("Cameroon"));
        assertTrue(Country.isValidCountry("Canada"));
        assertTrue(Country.isValidCountry("Cape Verde"));
        assertTrue(Country.isValidCountry("Caribbean Netherlands"));
        assertTrue(Country.isValidCountry("Cayman Islands"));
        assertTrue(Country.isValidCountry("Central African Republic"));
        assertTrue(Country.isValidCountry("Chad"));
        assertTrue(Country.isValidCountry("Chile"));
        assertTrue(Country.isValidCountry("China"));
        assertTrue(Country.isValidCountry("Christmas Island"));
        assertTrue(Country.isValidCountry("Cocos (Keeling) Islands"));
        assertTrue(Country.isValidCountry("Colombia"));
        assertTrue(Country.isValidCountry("Comoros"));
        assertTrue(Country.isValidCountry("Congo - Brazzaville"));
        assertTrue(Country.isValidCountry("Congo - Kinshasa"));
        assertTrue(Country.isValidCountry("Cook Islands"));
        assertTrue(Country.isValidCountry("Costa Rica"));
        assertTrue(Country.isValidCountry("Côte d’Ivoire"));
        assertTrue(Country.isValidCountry("Croatia"));
        assertTrue(Country.isValidCountry("Cuba"));
        assertTrue(Country.isValidCountry("Curaçao"));
        assertTrue(Country.isValidCountry("Cyprus"));
        assertTrue(Country.isValidCountry("Czechia"));
        assertTrue(Country.isValidCountry("Denmark"));
        assertTrue(Country.isValidCountry("Djibouti"));
        assertTrue(Country.isValidCountry("Dominica"));
        assertTrue(Country.isValidCountry("Dominican Republic"));
        assertTrue(Country.isValidCountry("Ecuador"));
        assertTrue(Country.isValidCountry("Egypt"));
        assertTrue(Country.isValidCountry("El Salvador"));
        assertTrue(Country.isValidCountry("Equatorial Guinea"));
        assertTrue(Country.isValidCountry("Eritrea"));
        assertTrue(Country.isValidCountry("Estonia"));
        assertTrue(Country.isValidCountry("Ethiopia"));
        assertTrue(Country.isValidCountry("Falkland Islands"));
        assertTrue(Country.isValidCountry("Faroe Islands"));
        assertTrue(Country.isValidCountry("Fiji"));
        assertTrue(Country.isValidCountry("Finland"));
        assertTrue(Country.isValidCountry("France"));
        assertTrue(Country.isValidCountry("French Guiana"));
        assertTrue(Country.isValidCountry("French Polynesia"));
        assertTrue(Country.isValidCountry("French Southern Territories"));
        assertTrue(Country.isValidCountry("Gabon"));
        assertTrue(Country.isValidCountry("Gambia"));
        assertTrue(Country.isValidCountry("Georgia"));
        assertTrue(Country.isValidCountry("Germany"));
        assertTrue(Country.isValidCountry("Ghana"));
        assertTrue(Country.isValidCountry("Gibraltar"));
        assertTrue(Country.isValidCountry("Greece"));
        assertTrue(Country.isValidCountry("Greenland"));
        assertTrue(Country.isValidCountry("Grenada"));
        assertTrue(Country.isValidCountry("Guadeloupe"));
        assertTrue(Country.isValidCountry("Guam"));
        assertTrue(Country.isValidCountry("Guatemala"));
        assertTrue(Country.isValidCountry("Guernsey"));
        assertTrue(Country.isValidCountry("Guinea"));
        assertTrue(Country.isValidCountry("Guinea-Bissau"));
        assertTrue(Country.isValidCountry("Guyana"));
        assertTrue(Country.isValidCountry("Haiti"));
        assertTrue(Country.isValidCountry("Heard & McDonald Islands"));
        assertTrue(Country.isValidCountry("Honduras"));
        assertTrue(Country.isValidCountry("Hong Kong SAR China"));
        assertTrue(Country.isValidCountry("Hungary"));
        assertTrue(Country.isValidCountry("Iceland"));
        assertTrue(Country.isValidCountry("India"));
        assertTrue(Country.isValidCountry("Indonesia"));
        assertTrue(Country.isValidCountry("Iran"));
        assertTrue(Country.isValidCountry("Iraq"));
        assertTrue(Country.isValidCountry("Ireland"));
        assertTrue(Country.isValidCountry("Isle of Man"));
        assertTrue(Country.isValidCountry("Israel"));
        assertTrue(Country.isValidCountry("Italy"));
        assertTrue(Country.isValidCountry("Jamaica"));
        assertTrue(Country.isValidCountry("Japan"));
        assertTrue(Country.isValidCountry("Jersey"));
        assertTrue(Country.isValidCountry("Jordan"));
        assertTrue(Country.isValidCountry("Kazakhstan"));
        assertTrue(Country.isValidCountry("Kenya"));
        assertTrue(Country.isValidCountry("Kiribati"));
        assertTrue(Country.isValidCountry("Kuwait"));
        assertTrue(Country.isValidCountry("Kyrgyzstan"));
        assertTrue(Country.isValidCountry("Laos"));
        assertTrue(Country.isValidCountry("Latvia"));
        assertTrue(Country.isValidCountry("Lebanon"));
        assertTrue(Country.isValidCountry("Lesotho"));
        assertTrue(Country.isValidCountry("Liberia"));
        assertTrue(Country.isValidCountry("Libya"));
        assertTrue(Country.isValidCountry("Liechtenstein"));
        assertTrue(Country.isValidCountry("Lithuania"));
        assertTrue(Country.isValidCountry("Luxembourg"));
        assertTrue(Country.isValidCountry("Macau SAR China"));
        assertTrue(Country.isValidCountry("Macedonia"));
        assertTrue(Country.isValidCountry("Madagascar"));
        assertTrue(Country.isValidCountry("Malawi"));
        assertTrue(Country.isValidCountry("Malaysia"));
        assertTrue(Country.isValidCountry("Maldives"));
        assertTrue(Country.isValidCountry("Mali"));
        assertTrue(Country.isValidCountry("Malta"));
        assertTrue(Country.isValidCountry("Marshall Islands"));
        assertTrue(Country.isValidCountry("Martinique"));
        assertTrue(Country.isValidCountry("Mauritania"));
        assertTrue(Country.isValidCountry("Mauritius"));
        assertTrue(Country.isValidCountry("Mayotte"));
        assertTrue(Country.isValidCountry("Mexico"));
        assertTrue(Country.isValidCountry("Micronesia"));
        assertTrue(Country.isValidCountry("Moldova"));
        assertTrue(Country.isValidCountry("Monaco"));
        assertTrue(Country.isValidCountry("Mongolia"));
        assertTrue(Country.isValidCountry("Montenegro"));
        assertTrue(Country.isValidCountry("Montserrat"));
        assertTrue(Country.isValidCountry("Morocco"));
        assertTrue(Country.isValidCountry("Mozambique"));
        assertTrue(Country.isValidCountry("Myanmar (Burma)"));
        assertTrue(Country.isValidCountry("Namibia"));
        assertTrue(Country.isValidCountry("Nauru"));
        assertTrue(Country.isValidCountry("Nepal"));
        assertTrue(Country.isValidCountry("Netherlands"));
        assertTrue(Country.isValidCountry("New Caledonia"));
        assertTrue(Country.isValidCountry("New Zealand"));
        assertTrue(Country.isValidCountry("Nicaragua"));
        assertTrue(Country.isValidCountry("Niger"));
        assertTrue(Country.isValidCountry("Nigeria"));
        assertTrue(Country.isValidCountry("Niue"));
        assertTrue(Country.isValidCountry("Norfolk Island"));
        assertTrue(Country.isValidCountry("North Korea"));
        assertTrue(Country.isValidCountry("Northern Mariana Islands"));
        assertTrue(Country.isValidCountry("Norway"));
        assertTrue(Country.isValidCountry("Oman"));
        assertTrue(Country.isValidCountry("Pakistan"));
        assertTrue(Country.isValidCountry("Palau"));
        assertTrue(Country.isValidCountry("Palestinian Territories"));
        assertTrue(Country.isValidCountry("Panama"));
        assertTrue(Country.isValidCountry("Papua New Guinea"));
        assertTrue(Country.isValidCountry("Paraguay"));
        assertTrue(Country.isValidCountry("Peru"));
        assertTrue(Country.isValidCountry("Philippines"));
        assertTrue(Country.isValidCountry("Pitcairn Islands"));
        assertTrue(Country.isValidCountry("Poland"));
        assertTrue(Country.isValidCountry("Portugal"));
        assertTrue(Country.isValidCountry("Puerto Rico"));
        assertTrue(Country.isValidCountry("Qatar"));
        assertTrue(Country.isValidCountry("Réunion"));
        assertTrue(Country.isValidCountry("Romania"));
        assertTrue(Country.isValidCountry("Russia"));
        assertTrue(Country.isValidCountry("Rwanda"));
        assertTrue(Country.isValidCountry("Samoa"));
        assertTrue(Country.isValidCountry("San Marino"));
        assertTrue(Country.isValidCountry("São Tomé & Príncipe"));
        assertTrue(Country.isValidCountry("Saudi Arabia"));
        assertTrue(Country.isValidCountry("Senegal"));
        assertTrue(Country.isValidCountry("Serbia"));
        assertTrue(Country.isValidCountry("Seychelles"));
        assertTrue(Country.isValidCountry("Sierra Leone"));
        assertTrue(Country.isValidCountry("Singapore"));
        assertTrue(Country.isValidCountry("Sint Maarten"));
        assertTrue(Country.isValidCountry("Slovakia"));
        assertTrue(Country.isValidCountry("Slovenia"));
        assertTrue(Country.isValidCountry("Solomon Islands"));
        assertTrue(Country.isValidCountry("Somalia"));
        assertTrue(Country.isValidCountry("South Africa"));
        assertTrue(Country.isValidCountry("South Georgia & South Sandwich Islands"));
        assertTrue(Country.isValidCountry("South Korea"));
        assertTrue(Country.isValidCountry("South Sudan"));
        assertTrue(Country.isValidCountry("Spain"));
        assertTrue(Country.isValidCountry("Sri Lanka"));
        assertTrue(Country.isValidCountry("St. Barthélemy"));
        assertTrue(Country.isValidCountry("St. Helena"));
        assertTrue(Country.isValidCountry("St. Kitts & Nevis"));
        assertTrue(Country.isValidCountry("St. Lucia"));
        assertTrue(Country.isValidCountry("St. Martin"));
        assertTrue(Country.isValidCountry("St. Pierre & Miquelon"));
        assertTrue(Country.isValidCountry("St. Vincent & Grenadines"));
        assertTrue(Country.isValidCountry("Sudan"));
        assertTrue(Country.isValidCountry("Suriname"));
        assertTrue(Country.isValidCountry("Svalbard & Jan Mayen"));
        assertTrue(Country.isValidCountry("Swaziland"));
        assertTrue(Country.isValidCountry("Sweden"));
        assertTrue(Country.isValidCountry("Switzerland"));
        assertTrue(Country.isValidCountry("Syria"));
        assertTrue(Country.isValidCountry("Taiwan"));
        assertTrue(Country.isValidCountry("Tajikistan"));
        assertTrue(Country.isValidCountry("Tanzania"));
        assertTrue(Country.isValidCountry("Thailand"));
        assertTrue(Country.isValidCountry("Timor-Leste"));
        assertTrue(Country.isValidCountry("Togo"));
        assertTrue(Country.isValidCountry("Tokelau"));
        assertTrue(Country.isValidCountry("Tonga"));
        assertTrue(Country.isValidCountry("Trinidad & Tobago"));
        assertTrue(Country.isValidCountry("Tunisia"));
        assertTrue(Country.isValidCountry("Turkey"));
        assertTrue(Country.isValidCountry("Turkmenistan"));
        assertTrue(Country.isValidCountry("Turks & Caicos Islands"));
        assertTrue(Country.isValidCountry("Tuvalu"));
        assertTrue(Country.isValidCountry("U.S. Outlying Islands"));
        assertTrue(Country.isValidCountry("U.S. Virgin Islands"));
        assertTrue(Country.isValidCountry("Uganda"));
        assertTrue(Country.isValidCountry("Ukraine"));
        assertTrue(Country.isValidCountry("United Arab Emirates"));
        assertTrue(Country.isValidCountry("United Kingdom"));
        assertTrue(Country.isValidCountry("United States"));
        assertTrue(Country.isValidCountry("Uruguay"));
        assertTrue(Country.isValidCountry("Uzbekistan"));
        assertTrue(Country.isValidCountry("Vatican City"));
        assertTrue(Country.isValidCountry("Venezuela"));
        assertTrue(Country.isValidCountry("Vietnam"));
        assertTrue(Country.isValidCountry("Vanuatu"));
        assertTrue(Country.isValidCountry("Wallis & Futuna"));
        assertTrue(Country.isValidCountry("Western Sahara"));
        assertTrue(Country.isValidCountry("Yemen"));
        assertTrue(Country.isValidCountry("Zambia"));
        assertTrue(Country.isValidCountry("Zimbabwe"));
    }
}
