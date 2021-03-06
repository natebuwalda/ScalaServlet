package com.jyc.demo.employee

import com.jyc.demo.common.Repository

trait EmployeeServiceContext {
  protected def listEmployeesFromRepo: Repository[Employee] => Unit => List[Employee] = {
    repo => unit => repo.findAll.toList
  }

  protected def addNewEmployeeToRepo:
    Repository[Employee] => Employee => Option[(String, Employee)] = {
    repo => employee => repo.add(employee)
  }
}

trait EmployeeService {
  def listEmployees: Unit => List[Employee]
  def addNewEmployee: Employee => Option[(String, Employee)]
}

object EmployeeMongoService {
  private var instance: Option[EmployeeMongoService] = None

  def apply(): EmployeeMongoService = {
    instance match {
      case Some(service) => service
      case None => {
        instance = Option(new EmployeeMongoService)
        instance.get
      }
    }
  }
}

class EmployeeMongoService extends EmployeeService with EmployeeServiceContext {
  def listEmployees = listEmployeesFromRepo(EmployeeMongoRepository())

  def addNewEmployee: Employee => Option[(String, Employee)] = {
    addNewEmployeeToRepo(EmployeeMongoRepository())
  }
}




