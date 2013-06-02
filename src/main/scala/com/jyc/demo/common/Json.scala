package com.jyc.demo.common

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper

object Json {
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def toJson[T](value: T): String = mapper.writeValueAsString(value)
  def fromJson[T](json: String, clazz: Class[T]): T = {
    mapper.readValue(json, clazz)
  }
}
