package com.jyc.demo.domain

import scala.beans.BeanProperty

case class Employee(@BeanProperty firstName: String,
                    @BeanProperty lastName: String,
                    @BeanProperty title: String,
                    @BeanProperty age: Int,
                    @BeanProperty salary: Double)
