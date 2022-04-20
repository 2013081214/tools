package check;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: ���乤����
 * @author: lhb
 * @time: 2020/10/16 15:05
 */

public class SectionUtil {
    //��Сֵ
    private String min_entity;
    //���ֵ
    private String max_entity;
    //�������״̬��false -������  true-- ������
    private boolean left_sate = false;
    //�Ҳ�����״̬��false -������  true-- ������
    private boolean right_sate = false;
    private SectionUtil() {

    }

    public SectionUtil(String min_entity, String max_entity, boolean left_sate, boolean right_sate) {
        this.min_entity = min_entity;
        this.max_entity = max_entity;
        this.left_sate = left_sate;
        this.right_sate = right_sate;
    }

    public String getMin_entity() {
        return min_entity;
    }

    public String getMax_entity() {
        return max_entity;
    }

    public boolean isLeft_sate() {
        return left_sate;
    }

    public boolean isRight_sate() {
        return right_sate;
    }

    /**
     * @description: ����������((������,X])
     * @param value �������ֵ
     * @param right_sate ���俪��״̬
     */
    public static SectionUtil creatFu(String value, boolean right_sate) {

        return new SectionUtil("", value, false, right_sate);
    }
    /**
     * @description: ����������[X,������))
     * @param value ������Сֵ
     * @param left_sate ���俪��״̬
     */
    public static SectionUtil creatZheng(String value, boolean left_sate) {

        return new SectionUtil(value, "", left_sate, false);
    }
    /**
     * @description: �����պ����䣨[X,Y]��
     * @param min   ������Сֵ
     * @param max   �������ֵ
     * @param left_sate ������࿪��״̬
     * @param right_sate �����Ҳ࿪��״̬
     * @return
     */
    public static SectionUtil creat(String min, boolean left_sate, String max, boolean right_sate) {

        return new SectionUtil(min, max, left_sate, right_sate);
    }
    /**
     * @description:  ��ʵ����ת�������伯��
     * @param record  ��ת����ʵ����
     * @return ת��������伯����(������ʱת����Ϊ2������,���Բ��ü���)
     */
    public static List<SectionUtil> getSections(ReqRespResearchProductQuestionnaireItem record) {
        List<SectionUtil> list = new ArrayList<>();
        String record_max = record.getMaxValue();
        String record_min = record.getMinValue();
        switch (record.getSymbol()) {
            case 1:
                list.add(creatZheng(record_max, false));
                break;
            case 2:
                list.add(creatFu(record_max, false));
                break;
            case 3:
                list.add(creat(record_max, true, record_max, true));
                break;
            case 4:
                list.add(creatFu(record_max, false));
                list.add(creatZheng(record_max, false));
                break;
            case 5:
                list.add(creatZheng(record_max, true));
                break;
            case 6:
                list.add(creatFu(record_max, true));
                break;
            case 7:
                list.add(creat(record_min, true, record_max, true));
                break;
            case 8:
                list.add(creat(record_min, true, record_max, false));
                break;
            case 9:
                list.add(creat(record_min, false, record_max, true));
                break;
            case 10:
                list.add(creat(record_min, false, record_max, false));
                break;
        }
        return list;
    }



    public int compareTo(String first_value, String second_value) {
        //first_valueΪ�ձ�ʾΪ�����second_valueΪ�ձ�ʾΪ������
        if (isBlank(first_value) || isBlank(second_value)) {
            return 1;
        }
        return compareToValue(first_value,second_value);
    }
    //�ж��ַ����Ƿ�Ϊ��
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param record �ж������Ƿ����غ�
     * @return true-���غ�  false -���غ�
     * @description: �жϵ�ǰ�����Ƿ��ָ�������غ�
     */
    public boolean isChonghe(SectionUtil record) {
        String min_entity = record.getMin_entity();
        String max_entity = record.getMax_entity();
        boolean left_sate = record.isLeft_sate();
        boolean right_sate = record.isRight_sate();
        boolean left_isok = false;
        boolean right_isok = false;
        //�غ�����,��һ���������ֵ���ڵڶ���������Сֵ���ҵ�һ���������СֵС�ڵڶ�����������ֵ
        //ע�⴫ֵ˳��,��һ��ֵΪ��һ����������ֵ(�˴����ܷ�)
        int first_result = compareTo(this.max_entity, min_entity);
        if ((first_result == 0 && this.right_sate && left_sate) || (first_result > 0)) {
            left_isok = true;
        }
        //ע�⴫ֵ˳��,��һ��ֵΪ�ڶ�����������ֵ(�˴����ܷ�)
        int second_result = compareTo(max_entity, this.min_entity);
        //�˴���Ӧ����second_result<0,��������һ����������ʱʱ�������ݣ��ʴ˴˴�Ϊsecond_result>0
        if ((second_result == 0 && this.left_sate && right_sate) || second_result > 0) {
            right_isok = true;
        }
        return left_isok && right_isok;
    }
    /**
     * @description:   �Ƚϼ����������Ƿ����ص�
     * @param list1 ���Ƚϼ���1
     * @param list2 ���Ƚϼ���2
     * @return
     */
    public static boolean isChonghe(List<SectionUtil> list1, List<SectionUtil> list2) {
        boolean chonghed = false;
        for (SectionUtil item1 : list1) {
            for (SectionUtil item2 : list2) {
                chonghed = item1.isChonghe(item2);
                if (chonghed) {
                    return true;
                }
            }
        }
        return chonghed;
    }
    //�Ƚϴ�С
    public static int compareToValue(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.compareTo(b2);
    }
    
    private static boolean compareSection(List<ReqRespResearchProductQuestionnaireItem> list) {
        for (int i = 0; i < list.size(); i++) {
            ReqRespResearchProductQuestionnaireItem record = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                ReqRespResearchProductQuestionnaireItem item = list.get(j);
                //�ж������Ƿ��н���
                List<SectionUtil> records = SectionUtil.getSections(record);
                List<SectionUtil> items = SectionUtil.getSections(item);
                boolean chonghe = SectionUtil.isChonghe(records, items);
                if (chonghe) {
                    return true;
                }
            }
        }
        return false;
    }
}