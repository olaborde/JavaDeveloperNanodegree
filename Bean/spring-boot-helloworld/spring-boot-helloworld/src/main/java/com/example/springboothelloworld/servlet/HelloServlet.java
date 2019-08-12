package com.example.springboothelloworld.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "helloServlet", urlPatterns = "/helloServlet")
public class HelloServlet extends HttpServlet {
    @Override
    protected void  doGet(HttpServletRequest request, HttpServletResponse response){
        System.err.println("Running hello servlet doGet method");

    }
}


