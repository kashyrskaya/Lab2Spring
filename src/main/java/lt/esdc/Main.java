package lt.esdc;

import lt.esdc.config.WebConfig;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());

        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
        springContext.register(WebConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(springContext);

        Tomcat.addServlet(ctx, "dispatcher", dispatcherServlet).setLoadOnStartup(1);
        ctx.addServletMappingDecoded("/*", "dispatcher");

        System.out.println("Starting Embedded Tomcat on port 8080...");
        tomcat.start();

        tomcat.getServer().await();
    }
}