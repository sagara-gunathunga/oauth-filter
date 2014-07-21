package org.sample.rest.greeting;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class GreetingService {

	@GET
	@Path("greeting/{msg}")
	public String greetingGet(@PathParam("msg") String msg) {
		return "Hello " + msg;

	}
	
	@POST
	@Path("greeting/{msg}")
	public String greetingPost(@PathParam("msg") String msg) {
		return "Hello " + msg;

	}


}
