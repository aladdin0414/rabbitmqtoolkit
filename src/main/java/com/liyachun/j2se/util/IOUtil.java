package com.liyachun.j2se.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

public class IOUtil {
	
	public static void writeText2Path(String path, String msg) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(msg.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static String readtxt(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String str = "";
            String r = br.readLine();
            while (r != null) {
                str += r;
                r = br.readLine();
            }
            br.close();
            return str;
        } catch (Exception e) {
            return "";
        }
    }
}
