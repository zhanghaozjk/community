package com.hcven.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author zhanghao
 * @since 2019-05-25
 */
public class SerializeUtil {

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            // 序列化
            byteArrayOutputStream = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(byteArrayOutputStream);
            oos.writeObject(object);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream;
        try {
            // 反序列化
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }
}
