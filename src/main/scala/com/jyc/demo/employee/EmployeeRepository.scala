package com.jyc.demo.employee

import com.mongodb.casbah.Imports._
import com.jyc.demo.mongo.MongoContext
import com.jyc.demo.common.Repository
import com.mongodb

trait EmployeeRepository extends Repository[Employee]

object EmployeeMongoRepository {
  private var instance: Option[EmployeeMongoRepository] = None

  def apply(): EmployeeMongoRepository = {
    instance match {
      case Some(repo) => repo
      case None => {
        instance = Option(new EmployeeMongoRepository)
        instance.get
      }
    }
  }
}

class EmployeeMongoRepository extends EmployeeRepository {
  private val employeeCollection: MongoCollection = MongoContext.collection("employee")
  private val bsonToEmployee = (bson: MongoDBObject) => {
    Employee(bson.as[String]("firstName"),
             bson.as[String]("lastName"),
             bson.as[String]("title"),
             bson.as[Int]("age"),
             bson.as[Double]("salary"))
  }

  setupTestData

  private def setupTestData: Unit = {
    val testDocQuery = employeeCollection findOne (MongoDBObject("lastName" -> "Buwalda"))
    testDocQuery match {
      case Some(doc) => //do nothing, its already there
      case None => {
        employeeCollection += buildBsonObject(Employee("Nate", "Buwalda", "Consultant", 34, 123456.78))
      }
    }
  }

  def findAll: Iterable[Employee] = employeeCollection map (bsonToEmployee(_))

  def add(employee: Employee): Option[(String, Employee)] = {
    val result: mongodb.WriteResult = employeeCollection += buildBsonObject(employee)
    if (result.getLastError.ok()) {
      val docQuery = employeeCollection findOne (MongoDBObject("lastName" -> employee.lastName))
      docQuery match {
        case Some(doc) => Option((doc.as[ObjectId]("_id").toString, bsonToEmployee(doc)))
        case None => None
      }
    } else None
  }

  private def buildBsonObject(employee: Employee): DBObject = {
    import employee._
    MongoDBObject("firstName" -> firstName,
                  "lastName" -> lastName,
                  "title" -> title,
                  "age" -> age,
                  "salary" -> salary)
  }
}
