package com.cc.java;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 大端小端测试
 *
 * java默认是大端
 *
 * 举例：
 * 数值1*1024*1024 + 2*1024 + 3 Java内存中表示为：00000000 00000001 00000010 00000011
 * 大端：最左是高位  表示：00000000 00000001 00000010 00000011
 * 小端：最左是低位  表示：00000011 00000010 00000001 00000000
 *
 * Description: BigEndiaLittleEndiaTest
 * Date:  2021/7/15
 *
 * @author <a href=mailto:qiao.zzc@autonavi.com>yuhu</a>
 */

public class BigEndiaLittleEndiaTest {

    private static final Logger logger = LoggerFactory.getLogger(BigEndiaLittleEndiaTest.class);

    public static void main(String[] args) {
        testLittleEndia();
        testBigEndia();
        orderTest();
    }

    /**
     * 小端。低位index的byte是低位。最左边的数最小
     */
    public static void testLittleEndia(){

        int intValue = 132400000;
        String hex16 = intToLittleEndiaHex16(intValue);
        System.out.println(intValue + " -> " + hex16);
        System.out.println("大端：" + String.format("%x", intValue));
        System.out.println("还原：" + littleEndiaHex16toInt(hex16));

        intValue = 45140000;
        hex16 = intToLittleEndiaHex16(intValue);
        System.out.println(intValue + " -> " + hex16);
        System.out.println("大端：" + String.format("%x", intValue));
        System.out.println("还原：" + littleEndiaHex16toInt(hex16));
    }

    /**
     * 大端。低位index的byte是高位。最左边的数最大。日常的十进制表示就是这样。
     * java默认是大端
     */
    public static void testBigEndia(){
        int lon = 132400000;
        String lonHex16 = intToBigEndiaHex16(lon);
        System.out.println(lonHex16);
        int lonValue = bigEndiaHex16toInt(lonHex16);
        System.out.println(lonValue);

        int lat = 45140000;
        lat = 100000;
        String latHex16 = intToBigEndiaHex16(lat);
        System.out.println(latHex16);
        int latValue = bigEndiaHex16toInt(latHex16);
        System.out.println(latValue);
    }

    public static void orderTest() {
        int x = 0x01020304;
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[4]);
        byteBuffer.asIntBuffer().put(x);
        String before = Arrays.toString(byteBuffer.array());
        System.out.println("默认字节序：" + byteBuffer.order().toString() + "," + "内存数据：" + before);
        System.out.println("数值是一样：" + byteBuffer.getInt());

        byteBuffer = ByteBuffer.wrap(new byte[4]);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.asIntBuffer().put(x);
        String after = Arrays.toString(byteBuffer.array());
        System.out.println("小端字节序：" + byteBuffer.order().toString() + "," + "内存数据：" + after);
        System.out.println("数值是一样：" + byteBuffer.getInt());
    }

    /**
     * int 转小端十六进制
     *
     * @param v
     * @return
     */
    public static String intToLittleEndiaHex16(int v){
        int i4 = v >> 24;
        int i3 = ((v << 8) >> 24);
        int i2 = ((v << 16) >> 24);
        int i1 = ((v << 24) >> 24);

        String hex16  = String.format("%02x", (byte)i1) + String.format("%02x", (byte)i2) + String.format("%02x", (byte)i3) + String.format("%02x", (byte)i4);
        return hex16;
    }

    /**
     * 小端十六进制转 int
     *
     * @param hex16
     * @return
     */
    public static int littleEndiaHex16toInt(String hex16){
        int nLength = hex16.length() / 2;

        //小端表示的byte数组
        byte[] b = new byte[nLength];
        for (int i = 0; i < nLength; i++) {
            String n = hex16.substring(2 * i, 2 * i + 2);
            b[i] = (byte)Integer.parseInt(n, 16);
            //System.out.println(n + " " + b[i] + " -> " + Convert.convertbytetoint(b[i]));
        }

        System.out.println("小端表示的int:" + getIntFromLittleEndian(b));

        int r = ((b[3] & 0xff) << 24) + ((b[2] & 0xff) << 16) + ((b[1]& 0xff) << 8) + ((b[0]& 0xff) << 0);
        return r;
    }

    public static String intToBigEndiaHex16(int v){
        return String.format("%x", v);
    }

    public static int bigEndiaHex16toInt(String hex16){
        return Integer.parseInt(hex16,16);
    }

    /**
     * 获取小端表示的short数值
     *
     * @param bytes
     * @return
     */
    public static short getshortFromLittleEndian(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getShort();
    }

    /**
     * 获取小端表示的int数值
     *
     * @param bytes
     * @return
     */
    public static int getIntFromLittleEndian(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getInt();
    }

    /**
     * 获取小端表示的long数值
     *
     * @param bytes
     * @return
     */
    public static long getLongFromLittleEndian(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getLong();
    }
}
