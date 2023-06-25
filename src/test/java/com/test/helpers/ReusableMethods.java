package com.test.helpers;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import com.test.models.CreateUserDataPOJO;

public class ReusableMethods 
{
//	public static CreateUserDataPOJO user;
//	
//	public static CreateUserDataPOJO getUserDataToAdd()
//	{
//		user = new CreateUserDataPOJO();
//		user.setAccountno("VV-NOV1234");
//		user.setDepartmentno("2");
//		user.setSalary("4000");
//		user.setPincode("190422");
//		return user;
//	}
	
//	public int getStatusCode(Response response)
//	{
//		return response.getStatusCode();
//	}
	
	public String getContentType(Response response)
	{
		return response.getContentType();
	}
	
	public long getResponseTimeIn(Response response, TimeUnit unit)
	{
		return response.getTimeIn(unit);	
	}
	
	public static void verifyStatusCodeIs(Response response, int statuscode)
	{
		response.then().statusCode(statuscode);
	}
	public static ValidatableResponse verifySchema(Response response, String schema)
	{
		 return response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schema));
	}
	
	public static String getjsonPathData(Response response, String status)
	{
		Assert.assertEquals(status, "success");
		return status;
	}
}
