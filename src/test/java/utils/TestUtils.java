package utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestUtils {
    public static String encodeToBase64(String stringToEncode) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(stringToEncode.getBytes());
    }

    public static String decodeFromBase64(String encodedString) {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            return new String(base64Decoder.decodeBuffer(encodedString));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStringFromEntity(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        bufferedReader.lines().forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

}
