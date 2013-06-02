package com.jyc.demo.mongo

import com.mongodb.casbah.Imports._

object MongoContext {
  private val mongoClient = MongoClient()("demo_servlet")

  def collection(collectionName: String): MongoCollection = mongoClient(collectionName)
}
