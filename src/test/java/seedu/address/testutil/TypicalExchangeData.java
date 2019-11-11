package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.model.exchangedata.ExchangeData;
import seedu.address.model.expense.Currency;
import seedu.address.model.expense.Date;
import seedu.address.model.rates.Rates;

/**
 * Returns the typical rates for currency exchange.
 */
public class TypicalExchangeData {

    public static ExchangeData getTypicalExchangeData() {
        Rates rates = new Rates();
        rates.addRate("CAD", 0.9631651648);
        rates.addRate("HKD", 5.764322831);
        rates.addRate("ISK", 91.6266526343);
        rates.addRate("PHP", 37.5228573308);
        rates.addRate("DKK", 4.9140301256);
        rates.addRate("HUF", 217.0492665921);
        rates.addRate("CZK", 16.8585147668);
        rates.addRate("GBP", 0.5652173913);
        rates.addRate("RON", 3.1299085707);
        rates.addRate("SEK", 7.0585410774);
        rates.addRate("IDR", 10335.5193054003);
        rates.addRate("INR", 52.0745905413);
        rates.addRate("BRL", 3.0295994212);
        rates.addRate("RUB", 46.791620075);
        rates.addRate("HRK", 4.8923238834);
        rates.addRate("JPY", 79.7803065185);
        rates.addRate("THB", 22.2350851806);
        rates.addRate("CHF", 0.7236729593);
        rates.addRate("EUR", 0.6577649148);
        rates.addRate("MYR", 3.0723541406);
        rates.addRate("BGN", 1.2864566204);
        rates.addRate("TRY", 4.2785634414);
        rates.addRate("CNY", 5.1979872394);
        rates.addRate("NOK", 6.6853910412);
        rates.addRate("NZD", 1.1466157995);
        rates.addRate("ZAR", 10.8420048675);
        rates.addRate("USD", 0.7349207393);
        rates.addRate("MXN", 14.0761691771);
        rates.addRate("SGD", 1.0);
        rates.addRate("AUD", 1.0682102217);
        rates.addRate("ILS", 2.5971189897);
        rates.addRate("KRW", 861.0405840952);
        rates.addRate("PLN", 2.8144445175);
        ExchangeData exchangeData = new ExchangeData(
            new Date(
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            ), new Currency("SGD"), rates);
        return exchangeData;
    }
}
