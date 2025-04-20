package com.rw.apps.starter.common.model;

public enum Country {
    NL("Netherlands (the)", Currency.EUR),
    AT("Austria", Currency.EUR),
    BE("Belgium", Currency.EUR),
    CY("Cyprus", Currency.EUR),
    CZ("Czech Republic", Currency.EUR),
    FR("France", Currency.EUR),
    DE("Germany", Currency.EUR),
    GR("Greece", Currency.EUR),
    HU("Hungary", Currency.EUR),
    IT("Italy", Currency.EUR),
    PT("Portugal", Currency.EUR),
    ES("Spain", Currency.EUR), // TODO all EU in alphabetical order
    // https://www.government.nl/topics/european-union/eu-eea-efta-and-schengen-area-countries
    //
    AU("Australia", Currency.AUD),
    US("United States (of America)", Currency.USD),
    //
    LB("Lebanon", Currency.USD),
    AE("United Arab Emirates", Currency.AED),
    SA("Saudi Arabia (Kingdom of)", Currency.SAR),
    JO("Jordan", Currency.JOD),
    ;

    private final String name;
    private final Currency currency;

    Country(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public Currency getCurrency() {
        return currency;
    }
}
