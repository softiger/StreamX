package com.streamxhub.flink.test

import java.util.Properties

import com.streamxhub.flink.core.conf.ConfigConst._
import com.streamxhub.flink.core.util.{MySQLUtils}

object MySQLUtilTestApp {

  def main(args: Array[String]): Unit = {
    implicit val prop = new Properties()
    prop.put(KEY_MYSQL_INSTANCE,"test")
    prop.put(KEY_MYSQL_DRIVER,"com.mysql.jdbc.Driver")
    prop.put(KEY_MYSQL_URL,"jdbc:mysql://localhost:3306/test")
    prop.put(KEY_MYSQL_USER,"root")
    prop.put(KEY_MYSQL_PASSWORD,"123322242")
    val person = MySQLUtils.select("select * from person")
    println(person)


    val person1 = MySQLUtils.select("select * from person")
    println(person1)


  }

}

case class Person4(name:String,age:Int,address:String)
