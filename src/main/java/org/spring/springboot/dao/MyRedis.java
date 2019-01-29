package org.spring.springboot.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
/**
 * 使用本地redis库，简单测试java链接redis的使用方式，包括列表、字符串、集合等类型
 * @author Renqiang_cheng
 *
 **/
public class MyRedis {

    public static void main(String[] args) {
// connection();
// redisString();
        redisList();
// redisSet();

    }

    public static void redisSet(){
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.auth("123456"); // 设置密码
        Set<String> list = jedis.keys("*");
        Iterator<String> ite = list.iterator();
        while(ite.hasNext()){
            String key = ite.next();
            System.out.println("List of stored keys : " + key);
        }
    }

    public static void redisList(){
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.auth("123456"); // 设置密码
//        System.out.println("系统中删除redis，commonQuestionList节点数据: "+jedis.del("commonQuestionList"));
//        System.out.println("系统中删除redis，commonQuestionList节点数据: "+jedis.del("commonQuestionList"));

        List<String> list = jedis.lrange("commonQuestionList", 0, 20);
        for(int i = 0 ; i < list.size() ; i ++){
            System.out.println("Stroed string in redis : " + String.valueOf(list.get(i)));
        }
    }

    public static void redisString(){
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.set("nameIs", "renqiang");
        System.out.println("Stored string in redis : " + jedis.get("nameIs"));
    }

    public static void connection(){
        Jedis jedis = new Jedis("localhost");
        System.out.println(jedis.ping());
        Transaction t = jedis.multi();
        t.exec();
    }
}