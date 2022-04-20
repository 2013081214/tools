package check;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 区间工具类
 * @author: lhb
 * @time: 2020/10/16 15:05
 */

public class SectionUtil {
    //最小值
    private String min_entity;
    //最大值
    private String max_entity;
    //左侧括号状态：false -开区间  true-- 闭区间
    private boolean left_sate = false;
    //右侧括号状态：false -开区间  true-- 闭区间
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
     * @description: 创建负区间((负无穷,X])
     * @param value 区间最大值
     * @param right_sate 区间开闭状态
     */
    public static SectionUtil creatFu(String value, boolean right_sate) {

        return new SectionUtil("", value, false, right_sate);
    }
    /**
     * @description: 创建正区间[X,正无穷))
     * @param value 区间最小值
     * @param left_sate 区间开闭状态
     */
    public static SectionUtil creatZheng(String value, boolean left_sate) {

        return new SectionUtil(value, "", left_sate, false);
    }
    /**
     * @description: 创建闭合区间（[X,Y]）
     * @param min   区间最小值
     * @param max   区间最大值
     * @param left_sate 区间左侧开闭状态
     * @param right_sate 区间右侧开闭状态
     * @return
     */
    public static SectionUtil creat(String min, boolean left_sate, String max, boolean right_sate) {

        return new SectionUtil(min, max, left_sate, right_sate);
    }
    /**
     * @description:  将实体类转换成区间集合
     * @param record  待转换的实体类
     * @return 转换后的区间集合类(不等于时转换后为2个区间,所以采用集合)
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
        //first_value为空表示为正无穷，second_value为空表示为负无穷
        if (isBlank(first_value) || isBlank(second_value)) {
            return 1;
        }
        return compareToValue(first_value,second_value);
    }
    //判断字符串是否为空
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
     * @param record 判断区间是否有重合
     * @return true-有重合  false -无重合
     * @description: 判断当前区间是否和指定区间重合
     */
    public boolean isChonghe(SectionUtil record) {
        String min_entity = record.getMin_entity();
        String max_entity = record.getMax_entity();
        boolean left_sate = record.isLeft_sate();
        boolean right_sate = record.isRight_sate();
        boolean left_isok = false;
        boolean right_isok = false;
        //重合条件,第一个区间最大值大于第二个区间最小值并且第一个区间的最小值小于第二个区间的最大值
        //注意传值顺序,第一个值为第一个区间的最大值(此处不能反)
        int first_result = compareTo(this.max_entity, min_entity);
        if ((first_result == 0 && this.right_sate && left_sate) || (first_result > 0)) {
            left_isok = true;
        }
        //注意传值顺序,第一个值为第二个区间的最大值(此处不能反)
        int second_result = compareTo(max_entity, this.min_entity);
        //此处本应该是second_result<0,但由于上一步参数传递时时反正传递，故此此处为second_result>0
        if ((second_result == 0 && this.left_sate && right_sate) || second_result > 0) {
            right_isok = true;
        }
        return left_isok && right_isok;
    }
    /**
     * @description:   比较集合中区间是否有重叠
     * @param list1 待比较集合1
     * @param list2 待比较集合2
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
    //比较大小
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
                //判断区间是否有交叉
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