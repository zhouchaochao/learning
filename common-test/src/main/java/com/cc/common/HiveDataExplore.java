package com.cc.common;

import com.sun.tools.javac.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href=mailto:zhouzhichao1024@gmail.com>zhouzhichao</a>
 * @desc: HiveDataExplore
 * @date: 8/4/22
 */

public class HiveDataExplore {

    private static final Logger LOGGER = LoggerFactory.getLogger(HiveDataExplore.class);

    public static void main(String[] args) {
        HiveDataExplore.finishOrderRewardData();
        //HiveDataExplore.printSql();
    }

    public static void printSql() {
        for (int i = 30; i >= 1; i-=1) {
            //10 - 0.5 - 0
            //System.out.println("when t_driver_reward.reward_amount >= " +i+" then " + i);
            //System.out.println("when t_dimension.ipk >= " + String.format("%.1f", (0.3F * i)) + " then '" + String.format("%.1f", (0.3F * i)) + "-" + String.format("%.1f", (0.3F * i + 0.2)) + "'");
        }

        int range = 30;
        for (int i = 420; i >= 0; i -= range) {
            System.out.println("when t_dimension.finish_order_count >= " + i + " then '" + i + "-" + (i+range) + "'");
        }
    }

    public static void finishOrderRewardData() {
        List<String> lines = getLines();
        for (int i = 0; i < lines.size() ; i++) {
            System.out.println(lines.get(i));
        }

        System.out.println("矩阵");

        Map<String,Map<String,String>> data = new HashMap<>();
        Set<String> columnIndexSet = new HashSet<>();

        for (String line : lines) {
            String[] columns = line.split("\t");
            Assert.check(columns.length==2);
            String lineIndex = columns[0];
            Map<String,String> lineData = new HashMap<>();
            data.put(lineIndex,lineData);

            String contactStr = columns[1];
            for (String item : contactStr.split("\\|")) {
                String[] kvArr = item.split("=");
                Assert.check(kvArr.length==2);
                String columnIndex = kvArr[0];
                String value = kvArr[1];
                columnIndexSet.add(columnIndex);
                lineData.put(columnIndex,value);
            }
        }

        List<String> lineIndexList = null;
        try {
            lineIndexList = data.keySet().stream().sorted(Comparator.comparing(s1 ->
                    Integer.valueOf(s1)
            )).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            lineIndexList = data.keySet().stream().sorted(Comparator.comparing(s1 ->
                    Double.valueOf(s1.split("-")[0])
            )).collect(Collectors.toList());
        }

        List<String> columnIndexList = null;
        try {
            columnIndexList = columnIndexSet.stream().sorted(Comparator.comparing(s1 ->
                    Integer.valueOf(s1.split("-")[0])
            )).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            columnIndexList = columnIndexSet.stream().sorted(Comparator.comparing(s1 ->
                    Double.valueOf(s1.split("-")[0])
            )).collect(Collectors.toList());
        }

        System.out.print("data\t");
        for (String columnIndex : columnIndexList) {
            System.out.print(columnIndex + "\t");
        }
        System.out.println();

        for (String lineIndex : lineIndexList) {
            System.out.print(lineIndex + "\t");
            Map<String,String> lineData = data.get(lineIndex);
            for (String columnIndex : columnIndexList) {
                System.out.print(lineData.getOrDefault(columnIndex,"0") + "\t");
            }
            System.out.println();
        }
    }

    public static List<String> getLines() {
        String str = "0-0.3\t0-9=34593|10-19=14108|20-29=11816|30-39=12475|40-49=11063|50-59=15515|60-69=11806|70-79=8756|80-89=7181|90-100=9998\n" +
                "0.3-0.5\t0-9=17|10-19=44|20-29=39|30-39=38|40-49=17|50-59=27|60-69=17|70-79=6|80-89=3|90-100=14\n" +
                "0.6-0.8\t0-9=18|10-19=40|20-29=54|30-39=51|40-49=32|50-59=34|60-69=21|70-79=11|80-89=14|90-100=26\n" +
                "0.9-1.1\t0-9=179|10-19=410|20-29=379|30-39=332|40-49=246|50-59=262|60-69=205|70-79=172|80-89=130|90-100=252\n" +
                "1.2-1.4\t0-9=1856|10-19=6193|20-29=6749|30-39=6289|40-49=5569|50-59=5566|60-69=5218|70-79=5011|80-89=5327|90-100=6699\n" +
                "1.5-1.7\t0-9=2545|10-19=10265|20-29=14711|30-39=16433|40-49=17830|50-59=20842|60-69=22528|70-79=23845|80-89=24369|90-100=24018\n" +
                "1.8-2.0\t0-9=1617|10-19=7900|20-29=12162|30-39=15244|40-49=16990|50-59=20326|60-69=21747|70-79=20572|80-89=18397|90-100=15638\n" +
                "2.1-2.3\t0-9=929|10-19=4867|20-29=7953|30-39=9658|40-49=10983|50-59=12281|60-69=11956|70-79=10187|80-89=8223|90-100=6199\n" +
                "2.4-2.6\t0-9=620|10-19=3017|20-29=4790|30-39=5663|40-49=5802|50-59=5938|60-69=5020|70-79=3889|80-89=2507|90-100=1656\n" +
                "2.7-2.9\t0-9=419|10-19=1964|20-29=2757|30-39=3268|40-49=3046|50-59=2907|60-69=2278|70-79=1498|80-89=967|90-100=679\n" +
                "3.0-3.2\t0-9=306|10-19=1342|20-29=1745|30-39=1882|40-49=1688|50-59=1487|60-69=1169|70-79=715|80-89=514|90-100=336\n" +
                "3.3-3.5\t0-9=206|10-19=906|20-29=1155|30-39=1160|40-49=1056|50-59=811|60-69=583|70-79=413|80-89=278|90-100=194\n" +
                "3.6-3.8\t0-9=166|10-19=597|20-29=798|30-39=735|40-49=616|50-59=528|60-69=356|70-79=259|80-89=179|90-100=142\n" +
                "3.9-4.1\t0-9=129|10-19=461|20-29=538|30-39=481|40-49=375|50-59=318|60-69=237|70-79=189|80-89=121|90-100=96\n" +
                "4.2-4.4\t0-9=88|10-19=331|20-29=371|30-39=386|40-49=262|50-59=266|60-69=162|70-79=121|80-89=81|90-100=75\n" +
                "4.5-4.7\t0-9=54|10-19=232|20-29=281|30-39=260|40-49=202|50-59=182|60-69=120|70-79=93|80-89=49|90-100=37\n" +
                "4.8-5.0\t0-9=64|10-19=210|20-29=215|30-39=215|40-49=144|50-59=134|60-69=88|70-79=53|80-89=43|90-100=41\n" +
                "5.1-5.3\t0-9=49|10-19=174|20-29=194|30-39=137|40-49=93|50-59=104|60-69=55|70-79=35|80-89=27|90-100=40\n" +
                "5.4-5.6\t0-9=35|10-19=123|20-29=165|30-39=121|40-49=114|50-59=67|60-69=59|70-79=41|80-89=27|90-100=23\n" +
                "5.7-5.9\t0-9=38|10-19=110|20-29=127|30-39=108|40-49=90|50-59=61|60-69=38|70-79=23|80-89=24|90-100=19\n" +
                "6.0-6.2\t0-9=23|10-19=101|20-29=99|30-39=88|40-49=69|50-59=47|60-69=30|70-79=31|80-89=14|90-100=19\n" +
                "6.3-6.5\t0-9=20|10-19=73|20-29=106|30-39=84|40-49=43|50-59=46|60-69=22|70-79=22|80-89=22|90-100=6\n" +
                "6.6-6.8\t0-9=15|10-19=78|20-29=75|30-39=73|40-49=36|50-59=34|60-69=33|70-79=12|80-89=10|90-100=17\n" +
                "6.9-7.1\t0-9=17|10-19=57|20-29=71|30-39=53|40-49=39|50-59=41|60-69=21|70-79=19|80-89=12|90-100=11\n" +
                "7.2-7.4\t0-9=20|10-19=48|20-29=48|30-39=52|40-49=28|50-59=37|60-69=19|70-79=12|80-89=12|90-100=8\n" +
                "7.5-7.7\t0-9=11|10-19=37|20-29=39|30-39=46|40-49=22|50-59=25|60-69=20|70-79=4|80-89=11|90-100=10\n" +
                "7.8-8.0\t0-9=8|10-19=43|20-29=40|30-39=35|40-49=25|50-59=18|60-69=14|70-79=13|80-89=6|90-100=7\n" +
                "8.1-8.3\t0-9=4|10-19=47|20-29=29|30-39=28|40-49=20|50-59=14|60-69=18|70-79=8|80-89=12|90-100=8\n" +
                "8.4-8.6\t0-9=6|10-19=36|20-29=23|30-39=26|40-49=23|50-59=14|60-69=8|70-79=4|80-89=2|90-100=6\n" +
                "8.7-8.9\t0-9=8|10-19=30|20-29=34|30-39=20|40-49=22|50-59=19|60-69=8|70-79=7|80-89=3|90-100=6\n" +
                "9\t0-9=94|10-19=313|20-29=351|30-39=277|40-49=194|50-59=187|60-69=103|70-79=71|80-89=55|90-100=71";
        List<String> lines = Arrays.stream(str.split("\n")).collect(Collectors.toList());
        return lines;
    }
}
