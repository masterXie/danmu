package com.master.danmu.utils;

import java.util.HashMap;
import java.util.Map;

public class STTUtil {

    /**
     * STT字符串转换为Map
     *
     * @param sttString
     * @return
     */
    public static Map<String, Object> toMap(String sttString) {
        Map<String, Object> map = new HashMap<>();

        String[] arrays = sttString.split("/");
        for (String s : arrays) {
            if (s == null || "".equals(s)) {
                continue;
            }
            String[] kvArrays = s.split("@=");
            String key = kvArrays[0];
            String value = null;
            if (kvArrays.length > 1) {
                value = kvArrays[1];
            }
            key = decrypt(key);                 //对key中的特殊字符解码
            value = decrypt(decrypt(value));    //对value中的特殊字符解码(目前最多2次解码)
            map.put(key, value);
        }
        return map;
    }

    /**
     * 解码key或value中的转码字符<br>
     * 如果给定的值为空，返回null<br>
     * (如果 key 或者 value 中含有字符‘/’，则使用‘@S’转义)<br>
     * (如果 key 或者 value 中含有字符‘@’，使用‘@A’转义)<br>
     *
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        if (str == null) {
            return str;
        }
        if (str.contains("@S") || str.contains("@A")) {
            str = str.replaceAll("@S", "/");
            str = str.replaceAll("@A", "@");
        }
        return str;
    }
}
