package com.jyc.demo.employee

import com.jyc.demo.base.Repository

trait EmployeeService {
  protected def listEmployeesFromRepo: Repository[Employee] => Unit => List[Employee] = {
    repo => unit => repo.findAll.toList
  }
}

object EmployeeMongoService extends EmployeeService {
  def listEmployees = listEmployeesFromRepo(EmployeeMongoRepository())
}




