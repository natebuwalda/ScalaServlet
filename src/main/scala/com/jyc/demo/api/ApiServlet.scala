package com.jyc.demo.api

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.jyc.demo.logging.ConsoleLogger._
import com.jyc.demo.common.Json
import Json._
import org.apache.commons.io.IOUtils
import com.jyc.demo.employee.EmployeeController

class ApiServlet extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    writeResponse(response, performAndLog(toJson(EmployeeController().showEmployees)), okStatus)
  }

  override def doPost(request: HttpServletRequest, response: HttpServletResponse) {
    val newEmployeeJson = performAndLog(IOUtils.toString(request.getInputStream, request.getCharacterEncoding))
    EmployeeController().addEmployee(newEmployeeJson) match {
      case Left(msg) => writeResponse(response, performAndLog(toJson(msg)), okStatus)
      case Right(msg) => writeResponse(response, performAndLog(toJson(msg)), errorStatus)
    }
  }

  private def writeResponse(response: HttpServletResponse, jsonResponse: String, status: (HttpServletResponse) => Unit) {
    response.getWriter.println(jsonResponse)
    response.setContentType("application/json")
    status(response)
  }

  private def okStatus(response: HttpServletResponse) {
    response.setStatus(HttpServletResponse.SC_OK)
  }

  private def errorStatus(response: HttpServletResponse) {
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
  }
}










