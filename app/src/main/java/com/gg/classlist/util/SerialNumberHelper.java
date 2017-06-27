package com.gg.classlist.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class SerialNumberHelper {
    //path=/storage/sdcard0/Android/data/com.tencent.devicedemo/cache
    final static String SERIAL_NUMBER="/storage/sdcard0/Android/data/com.tencent.devicedemo/cache/serialNumber.txt";
    private Context context;

    public SerialNumberHelper() {
    }

    public SerialNumberHelper(Context context) {
        super();
        this.context = context;
    }

    public void save2File(String text) {
        try {
            FileOutputStream output = new FileOutputStream(SERIAL_NUMBER);
            output.write(text.getBytes("utf-8"));
            //关闭流
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public String read4File() {
        StringBuilder sb = new StringBuilder("");
        try {
            FileInputStream input = new FileInputStream(SERIAL_NUMBER);
            byte[] temp = new byte[1024];
            int len = 0;
            while ((len = input.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}