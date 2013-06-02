package com.jyc.demo.base

trait Repository[T] {
  def findAll: Iterable[T]
}
