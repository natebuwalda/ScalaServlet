package com.jyc.demo.employee

import scala.beans.BeanProperty
import com.jyc.demo.common.Json._

object EmployeeController {
  private var instance: Option[EmployeeController] = None

  def apply(): EmployeeController = {
    instance match {
      case Some(controller) => controller
      case None => {
        instance = Option(new EmployeeController)
        instance.get
      }
    }
  }
}

class EmployeeController {
  private val employeeService = EmployeeMongoService()

  def showEmployees: List[Employee] = employeeService.listEmployees()

  def addEmployee(employeeContent: String): Either[ResultMessage, ErrorMessage] = {
    val insertResult = employeeService.addNewEmployee(fromJson(employeeContent, classOf[Employee]))
    insertResult match {
      case Some((id, doc)) => Left(EmployeeResultMessage("Added obj id %s".format(id), doc))
      case None => Right(EmployeeErrorMessage("insert failed"))
    }
  }
}

sealed trait ResultMessage {
  val message: String
}
case class EmployeeResultMessage(@BeanProperty message: String, @BeanProperty document: Employee) extends ResultMessage

sealed trait ErrorMessage extends ResultMessage
case class EmployeeErrorMessage(@BeanProperty message: String) extends ErrorMessage
