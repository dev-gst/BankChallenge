package br.com.compass.util.validation.parser;

import java.util.Locale;
import java.util.Objects;

public class LocaleData {

    private final char groupSeparator;
    private final char decimalSeparator;
    private final String decimalFormatPattern;
    private final Locale locale;

    private LocaleData(Builder builder) {
        this.groupSeparator = builder.groupSeparator;
        this.decimalSeparator = builder.decimalSeparator;
        this.decimalFormatPattern = builder.decimalFormatPattern;
        this.locale = builder.locale;
    }

    public char getGroupSeparator() {
        return groupSeparator;
    }

    public char getDecimalSeparator() {
        return decimalSeparator;
    }

    public String getDecimalFormatPattern() {
        return decimalFormatPattern;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        LocaleData that = (LocaleData) o;
        return groupSeparator == that.groupSeparator &&
                decimalSeparator == that.decimalSeparator &&
                Objects.equals(decimalFormatPattern, that.decimalFormatPattern) &&
                Objects.equals(locale, that.locale);
    }

    @Override
    public int hashCode() {
        int result = groupSeparator;
        result = 31 * result + decimalSeparator;
        result = 31 * result + Objects.hashCode(decimalFormatPattern);
        result = 31 * result + Objects.hashCode(locale);
        return result;
    }

    public static class Builder {

        private char groupSeparator;
        private char decimalSeparator;
        private String decimalFormatPattern;
        private Locale locale;

        private boolean isGroupSeparatorSet = false;
        private boolean isDecimalSeparatorSet = false;
        private boolean isDecimalFormatPatternSet = false;
        private boolean isLocaleSet = false;

        Builder withGroupSeparator(char groupSeparator) {
            this.groupSeparator = groupSeparator;
            isGroupSeparatorSet = true;
            return this;
        }

        Builder withDecimalSeparator(char decimalSeparator) {
            this.decimalSeparator = decimalSeparator;
            isDecimalSeparatorSet = true;
            return this;
        }

        Builder withDecimalFormatPattern(String decimalFormatPattern) {
            this.decimalFormatPattern = decimalFormatPattern;
            isDecimalFormatPatternSet = true;
            return this;
        }

        Builder withLocale(Locale locale) {
            this.locale = locale;
            isLocaleSet = true;
            return this;
        }

        LocaleData build() {
            validateBuilder();
            return new LocaleData(this);
        }

        private void validateBuilder() {
            if (!isGroupSeparatorSet) { throw new IllegalStateException("Group separator is required"); }
            if (!isDecimalSeparatorSet) { throw new IllegalStateException("Decimal separator is required"); }
            if (!isDecimalFormatPatternSet) { throw new IllegalStateException("Decimal format pattern is required"); }
            if (!isLocaleSet) { throw new IllegalStateException("Locale is required"); }
        }

    }

}
