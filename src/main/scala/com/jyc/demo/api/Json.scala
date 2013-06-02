package com.jyc.demo.api

import org.codehaus.jackson.map.ObjectMapper

object Json {
  val mapper = new ObjectMapper()

  def toJson[T](bean: T): String = mapper.writeValueAsString(bean)
  def fromJson[T](json: String, clazz: Class[T]): T = {
    mapper.readValue(json, clazz)
  }
}
