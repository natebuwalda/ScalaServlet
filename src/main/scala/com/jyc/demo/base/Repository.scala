package com.jyc.demo.base

import com.jyc.demo.employee.Employee

trait Repository[T] {
  def add(employee: Employee): Option[(String, Employee)]
  def findAll: Iterable[T]
}
