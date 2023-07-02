package br.com.jeweb.vendas.util;

import org.tempuri.ISiteIncopa;
import org.tempuri.SiteIncopa;

import javax.xml.ws.soap.AddressingFeature;

public class WebServiceInstance {
    private static WebServiceInstance webServiceInstance;
    private static final SiteIncopa service = new SiteIncopa();
    private ISiteIncopa port;

    private WebServiceInstance() {
        port = service.getWSHttpBindingISiteIncopa(new AddressingFeature(true, true));
    }

    public static WebServiceInstance getInstance() {
        if (webServiceInstance == null) {
            webServiceInstance = new WebServiceInstance();
        }
        return webServiceInstance;
    }

    public ISiteIncopa getPort() {
        return port;
    }
}
