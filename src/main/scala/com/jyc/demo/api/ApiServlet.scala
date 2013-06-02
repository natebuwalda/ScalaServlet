package com.jyc.demo.api

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import com.jyc.demo.domain.Employee
import com.jyc.demo.logging.ConsoleLogger._
import Json._

class ApiServlet extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val employee = Employee("Nate", "Buwalda", "Consultant", 34, 1000000.00)

    val jsonResponse = performAndLog(toJson(employee))

    response.getWriter.println(jsonResponse)
    response.setContentType("application/json")
    response.setStatus(HttpServletResponse.SC_OK)
  }

}








