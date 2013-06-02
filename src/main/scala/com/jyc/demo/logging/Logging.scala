package com.jyc.demo.logging

import java.util.Date

sealed trait Logging {
  def doLog(message: String)
}

trait ConsoleLogging extends Logging {
  def doLog(message: String) {
    val now = new Date(System.currentTimeMillis())
    println("%s: %s".format(now, message))
  }
}

object ConsoleLogger extends ConsoleLogging {
  def performAndLog(action: => String): String = {
    val message = action
    doLog(message)
    message
  }
}
