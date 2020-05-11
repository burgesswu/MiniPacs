package com.carlwu.minipacs.tools.sqlite;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sqlite帮助类，直接创建该类示例，并调用相应的借口即可对sqlite数据库进行操作
 *
 * 本类基于 sqlite jdbc v56
 *
 * @author haoqipeng
 */
public class SqliteTest {
    public static void main(String args[]) {
        try {
            SqliteHelper h = new SqliteHelper("testHelper1.db");
            // drop表栗子
            //h.executeUpdate("drop table if exists test;");
            // create表栗子
            //h.executeUpdate("create table test(name varchar(20));");

            // insert数据栗子
//            String UUID = CommonUtil.getUUID();
//            Map<String,Object> map = new HashMap<String,Object>();
//            map.put("UUID",UUID);
//            map.put("BIZ_NO",CommonUtil.Random(10));
//            map.put("CONTENT","举个例子");
//            map.put("CREATE_TIME",DateUtil.getNowTime());
//            h.executeInsert("NOTE",map);
            // UPDATE栗子
            StringBuffer updSql = new StringBuffer();
            updSql.append("UPDATE ");
            updSql.append("NOTE");
            updSql.append(" SET ");
            updSql.append(" CONTENT = '");
            updSql.append("改个栗子");;
            updSql.append("'");
            h.executeUpdate(updSql.toString());
            // 取list栗子
//            List<SqlLiteDemoResult> demoList = h.executeQueryList("select BIZ_NO as bizNo,CONTENT as content,CREATE_TIME as createTime from NOTE", SqlLiteDemoResult.class);
//            System.out.println("打印列表********👇");
//            for (SqlLiteDemoResult result:demoList) {
//                System.out.println(result.getBizNo());
//                System.out.println(result.getContent());
//                System.out.println(result.getCreateTime());
//            }
//            System.out.println("打印列表********👆");
            // 取单条栗子
//            SqlLiteDemoResult demoResult = h.executeQuery("select BIZ_NO as bizNo,CONTENT as content,CREATE_TIME as createTime from NOTE WHERE UUID = '" + UUID + "'", SqlLiteDemoResult.class);
//
//            System.out.println("打印单条********👇");
//            System.out.println(demoResult.getBizNo());
//            System.out.println(demoResult.getContent());
//            System.out.println(demoResult.getCreateTime());
            System.out.println("打印单条********👆");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}