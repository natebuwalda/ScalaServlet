package com.jyc.demo.api

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.databind.ObjectMapper

object Json {
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def toJson[T](bean: T): String = mapper.writeValueAsString(bean)
  def fromJson[T](json: String, clazz: Class[T]): T = {
    mapper.readValue(json, clazz)
  }
}
