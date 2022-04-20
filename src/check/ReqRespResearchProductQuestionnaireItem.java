package check;
import java.io.Serializable;

public class ReqRespResearchProductQuestionnaireItem implements Serializable {
    private static final long serialVersionUID = -2850886454687095222L;
    private String minValue;
    private String maxValue;
    //"��Χ����:1-����/2-С��/3-����/4-������/5-���ڵ���/6-С�ڵ���/7-ȫ������/8-����ҿ�����/9-���ұ�����/10-ȫ������"
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