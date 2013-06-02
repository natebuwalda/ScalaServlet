package com.jyc.demo.employee

import com.mongodb.casbah.Imports._
import com.jyc.demo.mongo.MongoContext
import com.jyc.demo.base.Repository

object EmployeeMongoRepository {
  def apply(): EmployeeMongoRepository = new EmployeeMongoRepository
}

class EmployeeMongoRepository extends Repository[Employee] {
  private val employeeCollection: MongoCollection = MongoContext.collection("employee")
  private val bsonToEmployee = (bson: MongoDBObject) => {
    Employee(bson.as[String]("firstName"),
      bson.as[String]("lastName"),
      bson.as[String]("title"),
      bson.as[Int]("age"),
      bson.as[Double]("salary"))
  }

  setupTestData

  def setupTestData: Unit = {
    val testDoc = employeeCollection findOne (MongoDBObject("lastName" -> "Buwalda"))
    testDoc match {
      case Some(doc) => //do nothing, its already there
      case None => {
        employeeCollection += MongoDBObject("firstName" -> "Nate",
          "lastName" -> "Buwalda",
          "title" -> "Consultant",
          "age" -> 34,
          "salary" -> 123456.78)
      }
    }
  }

  def findAll: Iterable[Employee] = employeeCollection map (bsonToEmployee(_))
}
