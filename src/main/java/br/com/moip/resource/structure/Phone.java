package br.com.moip.resource.structure;

public class Phone {

    private static final String BRAZIL_CODE = "55";

    private String countryCode = BRAZIL_CODE;
    private String areaCode;
    private String number;

    public String getCountryCode() {
        return countryCode;
    }

    public Phone setCountryCode(String countryCode) {
        this.countryCode = countryCode;

        return this;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public Phone setAreaCode(String areaCode) {
        this.areaCode = areaCode;

        return this;
    }

    public String getNumber() {
        return number;
    }

    public Phone setNumber(String number) {
        this.number = number;

        return this;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "countryCode='" + countryCode + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}