package check;
import java.io.Serializable;

public class ReqRespResearchProductQuestionnaireItem implements Serializable {
    private static final long serialVersionUID = -2850886454687095222L;
    private String minValue;
    private String maxValue;
    //"范围符号:1-大于/2-小于/3-等于/4-不等于/5-大于等于/6-小于等于/7-全闭区间/8-左闭右开区间/9-左开右闭区间/10-全开区间"
    private Byte symbol;

    public ReqRespResearchProductQuestionnaireItem( String minValue, String maxValue, Byte symbol) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.symbol = symbol;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public Byte getSymbol() {
        return symbol;
    }

    public void setSymbol(Byte symbol) {
        this.symbol = symbol;
    }
}