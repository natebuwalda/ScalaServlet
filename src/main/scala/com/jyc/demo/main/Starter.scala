package com.jyc.demo.main

import org.eclipse.jetty.server.{Handler, Server}
import org.eclipse.jetty.servlet.{ServletContextHandler, ServletHolder}
import com.jyc.demo.api.ApiServlet

object Starter extends ServerContext(8080) with App with ApiServletContext {
  start(context)
}

class ServerContext(port: Int) {
  private val server = new Server(port)

  def start(servletContext: Handler) {
    server.setHandler(servletContext)
    server.start()
    server.join()
  }
}

trait ApiServletContext {
  def context = {
    val servletHolder = new ServletHolder(new ApiServlet())
    val context = new ServletContextHandler()
    context.setContextPath( "/" )
    context.addServlet(servletHolder, "/api/*")
    context
  }
}
