package msec.itu.lifiapp;

import java.util.Arrays;

/**
 * Created by martinosecchi on 08/01/17.
 */

public class TransmissionUtil {
    public static String STX = "010100110101010001011000";
    public static String ETX = "010001010101010001011000";
    public static String getBinary(String input){
        byte[] bytes = input.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return STX + binary.toString() + ETX;
    }
    public static String fromBinary(String input){
        StringBuilder sb = new StringBuilder(); // Some place to store the chars
        String[] chars = input.split("(?<=\\G.{8})");
        for (String s: chars) {
            sb.append((char) Integer.parseInt(s, 2));
        }
        return sb.toString();
    }
}
