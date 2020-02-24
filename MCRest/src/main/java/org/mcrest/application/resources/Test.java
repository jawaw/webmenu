package org.mcrest.application.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.mcrest.vo.ResponseVO;
import org.restlet.resource.ServerResource;

@Path("/test/")
@Produces("application/json")
public class Test extends ServerResource {
	
    @GET
    @Path("test")
    public ResponseVO test () {
        return ResponseVO.success("test成功");
    }

}
