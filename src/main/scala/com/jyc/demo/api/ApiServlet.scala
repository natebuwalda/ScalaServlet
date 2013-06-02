package com.jyc.demo.api

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import org.codehaus.jackson.map.ObjectMapper
import com.jyc.demo.domain.Employee
import java.util.Date

class ApiServlet extends HttpServlet with ConsoleLogging {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val employee = Employee("Nate", "Buwalda", "Consultant", 34, 1000000.00)
    val mapper = new ObjectMapper()

    val employeeJson = mapper.writeValueAsString(employee)
    doLog(employeeJson)

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().println(employeeJson);
  }

}

trait Logging {
  def doLog(message: String)
}

trait ConsoleLogging extends Logging {
  def doLog(message: String) {
    val now = new Date(System.currentTimeMillis())
    println("%s: %s".format(now, message))
  }
}