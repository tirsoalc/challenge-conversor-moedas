package model;

public class CurrencyPair {
    private String baseCurrencyName;
    private String targetCurrencyName;
    private Double baseAmount;
    private Double conversionAmountResult;

    public CurrencyPair(String baseCurrencyName, String targetCurrencyName, Double baseAmount, Double conversionAmountResult) {
        this.baseCurrencyName = baseCurrencyName;
        this.targetCurrencyName = targetCurrencyName;
        this.baseAmount = baseAmount;
        this.conversionAmountResult = conversionAmountResult;
    }

    public String getBaseCurrencyName() {
        return baseCurrencyName;
    }

    public String getTargetCurrencyName() {
        return targetCurrencyName;
    }

    public Double getConversionAmountResult() {
        return conversionAmountResult;
    }

    @Override
    public String toString() {
        return "CurrencyPair{" +
                "baseCurrencyName='" + baseCurrencyName + '\'' +
                ", targetCurrencyName='" + targetCurrencyName + '\'' +
                ", baseAmount=" + baseAmount +
                ", conversionAmountResult=" + conversionAmountResult +
                '}';
    }
}
