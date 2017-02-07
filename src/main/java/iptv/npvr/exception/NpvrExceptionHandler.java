package iptv.npvr.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 
 * @author bhawani.singh
 *
 */
@Provider
public class NpvrExceptionHandler implements ExceptionMapper<NpvrException> {
	@Override
	public Response toResponse(NpvrException exception) {
		
		ErrorMessage errorMessage = new ErrorMessage();		
		setHttpStatus(exception, errorMessage);
		errorMessage.setCode(exception.getCode());
		errorMessage.setMessage(exception.getMessage());
		errorMessage.setStatus(500);
		StringWriter errorStackTrace = new StringWriter();
		exception.printStackTrace(new PrintWriter(errorStackTrace));
				
		
		return Response.status(errorMessage.getStatus())
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();
		
		//return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}
	
	private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {
		if(ex instanceof WebApplicationException ) {
			errorMessage.setStatus(((WebApplicationException)ex).getResponse().getStatus());
		} else {
			errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); //defaults to internal server error 500
		}
	}
	
	/*@Override
	public Response toResponse(NpvrException ex)
	{
		//For simplicity I am preparing error xml by hand.
		//Ideally we should create an ErrorResponse class to hold the error info.
		String msg = ex.getMessage();
		String internalError = ex.getInternalErrorMessage();
		StringBuilder response = new StringBuilder("<response>");
		response.append("<status>failed</status>");
		response.append("<message>"+msg+"</message>");
		response.append("<internalError>"+internalError+"</internalError>");
		response.append("</response>");
		return Response.serverError().entity(response.toString()).build();
	}*/
	
}
