package com.lambdaschool.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class SchoolApplication
{

    public static void main(String[] args)
    {
        ApplicationContext ctx = SpringApplication.run(SchoolApplication.class, args);

        DispatcherServlet dispatcherServlet = (DispatcherServlet) ctx.getBean("dispatcherServlet");
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

}



//    heroku run echo $DATABASE_URL -a bps-school
//     ⬆ connect to postgress with information from this terminal prompt.