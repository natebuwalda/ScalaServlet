package com.jyc.demo.api

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.jyc.demo.logging.ConsoleLogger._
import Json._
import scala.beans.BeanProperty
import org.apache.commons.io.IOUtils
import com.jyc.demo.employee.{EmployeeMongoService, Employee}

class ApiServlet extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    writeResponse(response, performAndLog(toJson(EmployeeMongoService.listEmployees())))
  }

  override def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    val newEmployeeJson = performAndLog(IOUtils.toString(request.getInputStream, request.getCharacterEncoding))

    val insertResult = EmployeeMongoService.addNewEmployee(fromJson(newEmployeeJson, classOf[Employee]))
    insertResult match {
      case Some((id, doc)) => writeResponse(response,
        performAndLog(toJson(ResultMessage("Added obj id %s".format(id), doc))))
      case None => {
        response.getWriter.println("{ \"insert\" : \"failed\" }")
        response.setContentType("application/json")
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
      }
    }
  }

  private def writeResponse(response: HttpServletResponse, jsonResponse: String) {
    response.getWriter.println(jsonResponse)
    response.setContentType("application/json")
    response.setStatus(HttpServletResponse.SC_OK)
  }
}

case class ResultMessage(@BeanProperty message: String, @BeanProperty document: Employee)








