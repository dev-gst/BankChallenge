package br.com.compass.util.validation.parser;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class BigDecimalParser {

    private final static String DEFAULT_FORMAT_PATTERN = "#,##0.00";

    private final static String BRAZILIAN_NUMBER_REGEX = "(\\d+(\\.\\d{3})+(,\\d+)?)|(\\d+,[\\d,]*)";

    private final static Map<String, LocaleData> LOCALE_DATA_MAP = Map.of(
            "pt_BR", new LocaleData.Builder()
                    .withGroupSeparator('.')
                    .withDecimalSeparator(',')
                    .withDecimalFormatPattern(DEFAULT_FORMAT_PATTERN)
                    .withLocale(new Locale("pt", "BR"))
                    .build(),
            "en_US", new LocaleData.Builder()
                    .withGroupSeparator(',')
                    .withDecimalSeparator('.')
                    .withDecimalFormatPattern(DEFAULT_FORMAT_PATTERN)
                    .withLocale(new Locale("en", "US"))
                    .build()
    );

    public static BigDecimal of(String number) {
        if (number == null || number.isBlank() || !number.matches("^[\\d.,]+$")) {
            return null;
        }

        LocaleData localeData = identifyLocale(number);

        Optional<BigDecimal> parsedValue = tryToParse(number, localeData);
        return parsedValue.orElse(null);

    }

    private static Optional<BigDecimal> tryToParse(String number, LocaleData localeData) {
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(localeData.getLocale());
            symbols.setDecimalSeparator(localeData.getDecimalSeparator());
            symbols.setGroupingSeparator(localeData.getGroupSeparator());

            DecimalFormat decimalFormat = new DecimalFormat(localeData.getDecimalFormatPattern(), symbols);
            decimalFormat.setParseBigDecimal(true);

            BigDecimal parsedValue = (BigDecimal) decimalFormat.parse(number);

            return Optional.of(parsedValue);
        } catch (ParseException ignored) {
            return Optional.empty();
        }
    }

    private static LocaleData identifyLocale(String number) {
        if (number.matches(BRAZILIAN_NUMBER_REGEX)) {
            return LOCALE_DATA_MAP.get("pt_BR");
        }

        return LOCALE_DATA_MAP.get("en_US");
    }

}
