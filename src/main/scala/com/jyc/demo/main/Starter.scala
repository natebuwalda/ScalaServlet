package com.jyc.demo.main

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}
import com.jyc.demo.api.ApiServlet


object Starter extends App {
  val server = new Server(8080)

  val servletHolder = new ServletHolder(new ApiServlet())

  val context = new ServletContextHandler()
  context.setContextPath( "/" )
  context.addServlet(servletHolder, "/api/*")

  server.setHandler(context)
  server.start()
  server.join()

}
