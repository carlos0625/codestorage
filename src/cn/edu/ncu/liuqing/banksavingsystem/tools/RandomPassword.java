package cn.edu.ncu.liuqing.banksavingsystem.tools;

import java.util.Random;

/**
 * 生成8-18位随机数字或大小写字母的密码
 */
public class RandomPassword {
    private static Random ran = new Random();

    private static final int TYPE_UP_LETTER = 0;
    private static final int TYPE_LOWER_LETTER = 1;
    private static final int TYPE_DIGIT = 2;

    private static final String UP_LETTER = "abcdefghijklmnopqrstuvwxyz";
    private static final String LOWER_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGIT = "0123456789";

    public static String getRandomPassword(int min, int max) {
        StringBuilder password = new StringBuilder();
        int length = min + ran.nextInt(max - min) + 1;
        for (int i = 0; i < length; i++) {
            password.append(getRandomDigitOrLetter());
        }
        return password.toString();
    }

    private static char getRandomDigitOrLetter() {
        int type = ran.nextInt(3);
        switch (type) {
            case TYPE_UP_LETTER: return UP_LETTER.charAt(ran.nextInt(26));
            case TYPE_LOWER_LETTER: return LOWER_LETTER.charAt(ran.nextInt(26));
            case TYPE_DIGIT: return DIGIT.charAt(ran.nextInt(10));
            default: return '0';
        }
    }
}
