package cn.edu.ncu.liuqing.banksavingsystem.test;

import cn.edu.ncu.liuqing.banksavingsystem.connectDB.OperationsForDB;
import cn.edu.ncu.liuqing.banksavingsystem.controllers.operation.clerksubcontroller.RegisterController;
import cn.edu.ncu.liuqing.banksavingsystem.tools.FileOP;

import java.io.*;
import java.math.BigInteger;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  测试用类
 */
public class Test {
    public static void main(String[] args) {
        Date date = new Date();
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        Date date2 = new Date(date1.getTime());
        System.out.println(date2);
        System.out.println(date1);
        System.out.println(date.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateStart = df.parse(date.toString().substring(0,9));
            Date dateEnd = df.parse(new Date().toString().substring(0,9));
            long b = (dateEnd.getTime() - dateStart.getTime())/(1000 * 60 * 60 * 24);
            System.out.println(b);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
