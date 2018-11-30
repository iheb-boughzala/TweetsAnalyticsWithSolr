package com.kata.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.sql._
import org.apache.log4j._

object TweetsByLang {

    
 def main(args: Array[String]) {
    // log level error 
    
     Logger.getLogger("org").setLevel(Level.ERROR)
    
    //  SparkSession 
    val spark = SparkSession
      .builder
      .appName("TweetsByLang")
      //.master("local[*]") spark-submit --master
      .config("spark.sql.warehouse.dir", "file:///C:/temp") // Windows bug in Spark 2.0.0; omit if not on Windows.
      .getOrCreate()
    
    import spark.implicits._
    
    val df_tweets= spark.read.json(args(1))
    df_tweets.printSchema()
    if(args(0).length()==2) 
      {
      val condition = "lang = '" + args(0) +"'"
      val result = df_tweets.filter(condition)
      result.write.mode("append").json(args(2))
      }
    else 
    { println("invalid language")
      spark.stop()
    }

   spark.stop()
  }
    
}
