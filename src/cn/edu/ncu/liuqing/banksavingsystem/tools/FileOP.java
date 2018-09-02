package cn.edu.ncu.liuqing.banksavingsystem.tools;

import java.io.*;

/**
 * 文件读取
 */
public class FileOP {
    private static File file = new File("file/clerkNo.txt");

    public static void writeToFile(int a) {
        if (file.exists()) {
            try {
                file.delete();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
            dos.writeInt(a);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int readFromFile(){
        int a = 0;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))){
            a = dis.readInt();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
}
