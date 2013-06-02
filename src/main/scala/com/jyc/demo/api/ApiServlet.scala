package com.jyc.demo.api

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}
import org.codehaus.jackson.map.ObjectMapper
import scala.beans.BeanProperty

class ApiServlet extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val employee = Employee("Nate", "Buwalda", "Consultant", 34, 1000000.00)
    val mapper = new ObjectMapper()

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().println(mapper.writeValueAsString(employee));
  }

}

case class Employee(@BeanProperty firstName: String,
                    @BeanProperty lastName: String,
                    @BeanProperty title: String,
                    @BeanProperty age: Int,
                    @BeanProperty salary: Double)
