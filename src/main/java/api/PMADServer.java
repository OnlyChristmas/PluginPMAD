package api;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by xuany on 2017.4.4.
 */
public class PMADServer {
    public static void main(String[] args) throws Exception {
        Server pmadserver = new Server(8080);                     //Listen to the TCP/IP request
        WebAppContext ctx = new WebAppContext("src/main/web", "");
        ctx.addServlet(PMADdbConnect.class, "/handler");//configuration classPath
        ctx.addServlet(handler.class, "/task");


        pmadserver.setHandler(ctx);
        pmadserver.start();
        pmadserver.join();
    }
}
