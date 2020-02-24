package org.mcrest.application.resources;

import org.mcrest.application.RestApplication;
import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class RestJaxRsApplication extends JaxRsApplication {

    public RestJaxRsApplication(Context context) {
        super(context);
        this.add(new RestApplication());
    }

}
