package com.jyc.demo.api

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.jyc.demo.domain.Employee
import com.jyc.demo.logging.ConsoleLogger._
import Json._
import scala.beans.BeanProperty
import org.apache.commons.io.IOUtils

class ApiServlet extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val employee = Employee("Nate", "Buwalda", "Consultant", 34, 1000000.00)

    writeResponse(response, performAndLog(toJson(employee)))
  }

  override def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    val newEmployeeJson = performAndLog(IOUtils.toString(request.getInputStream, request.getCharacterEncoding))
    val newEmployee = fromJson(newEmployeeJson, classOf[Employee])

    import newEmployee._
    writeResponse(response, performAndLog(toJson(ResultMessage("Added %s %s".format(firstName, lastName)))))
  }

  private def writeResponse(response: HttpServletResponse, jsonResponse: String) {
    response.getWriter.println(jsonResponse)
    response.setContentType("application/json")
    response.setStatus(HttpServletResponse.SC_OK)
  }
}

case class ResultMessage(@BeanProperty message: String)








