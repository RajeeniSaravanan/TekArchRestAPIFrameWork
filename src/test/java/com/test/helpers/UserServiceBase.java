package com.test.helpers;

import java.util.Arrays;
import java.util.List;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import com.test.constants.EndPoints;
import com.test.models.LoginDataPOJO;
import com.test.tests.TekarchApiTestScriptE2E;
import com.test.models.CreateUserDataPOJO;
import com.test.models.GetUserDataPOJO;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.utils.*;

public class UserServiceBase 
{
	protected Response response;
	protected String token = null;
	protected Properties appProp = null;
	protected String url = null;
	protected String userId = null;
	protected String password = null;
	protected String accountNo = null;
	protected String department = null;
	protected String salary = null;
	protected String pincode = null;
	protected String updatedSalary = null;
	protected String updatedDepartmentNo = null;
	protected Logger log;
	
	public UserServiceBase()
	{
		Utils pro=new Utils();
		this.appProp= pro.loadFile("configProperties");
		this.url=appProp.getProperty("baseUrl");
		this.userId = appProp.getProperty("username");
		this.password=appProp.getProperty("password");
		this.accountNo=appProp.getProperty("accountno");
		this.department=appProp.getProperty("departmentno");
		this.salary=appProp.getProperty("Salary");
		this.pincode=appProp.getProperty("Pincode");
		this.updatedSalary=appProp.getProperty("updatedsalary");
		this.updatedDepartmentNo=appProp.getProperty("updatedDepartmentno");
		this.log = LogManager.getLogger(TekarchApiTestScriptE2E.class.getName());
		
	}
	
	protected Response loginToApplication()
	{
		//Arrange
		LoginDataPOJO data = new LoginDataPOJO();
		
		data.setUsername(this.userId);
		data.setPassword(this.password);
		
		//Act
		response=RestAssured.given()
		.contentType(ContentType.JSON)
		.body(data)
		.when()
		.post(EndPoints.LOGIN);
		return response;
	}
	
	protected String getToken()
	{
		response = loginToApplication();
		token = response.jsonPath().get("[0].token");
		return token;	
	}
	
	protected Response addUserData()
	{
		CreateUserDataPOJO user = new CreateUserDataPOJO();
		user.setAccountno(this.accountNo);
		user.setDepartmentno(this.department);
		user.setSalary(this.salary);
		user.setPincode(this.pincode);
		
		Header ob=new Header("token", token);
		
		response=RestAssured.given()
		.log().all()
		.header(ob)
		.contentType(ContentType.JSON)
		.body(user)
		.when()
		.post(EndPoints.ADD_DATA);
		return response;	
	}
	
	public List<GetUserDataPOJO> getUserData()
	{
		System.out.println("token = " );
		System.out.println(token);
		//System.out.println();("inside getUsers token="+token);
		Header ob=new Header("token", token);
		response=RestAssured.given()
		.header(ob)
		.when()
		.get(EndPoints.GET_DATA);
		
		GetUserDataPOJO[] userArray = response.as(GetUserDataPOJO[].class);
		List<GetUserDataPOJO> list = Arrays.asList(userArray);
		response.then().statusCode(200);
		ReusableMethods.verifySchema(response,"GetAllUsersSchema.json");
		log.info("GetUser Schema validated");
		log.info("Number of records = " + response.jsonPath().get("size()"));
		return list;	
	}
	
	public Response updateUserData()
	{
		GetUserDataPOJO updateUser = new GetUserDataPOJO();
		List<GetUserDataPOJO> listOfUsers = getUserData();
		for(GetUserDataPOJO userToBeUpdated : listOfUsers)
		{
			if(userToBeUpdated.getAccountno().equals(this.accountNo))
			{
				updateUser = userToBeUpdated;
				updateUser.setSalary(this.updatedSalary);
				updateUser.setDepartmentno(updatedDepartmentNo);
			}
		}
		
		Header ob=new Header("token", token);
		response=RestAssured.given()
		.log().all()
		.header(ob)
		.contentType(ContentType.JSON)
		.body(updateUser)
		.when()
		.put(EndPoints.UPDATE_DATA);
		return response;	
	}
	
	public Response deleteUserData()
	{
		GetUserDataPOJO deleteUser = new GetUserDataPOJO();
		List<GetUserDataPOJO> listOfUsers = getUserData();
		for(GetUserDataPOJO userToBeDeleted : listOfUsers)
			if(userToBeDeleted.getAccountno().equals(this.accountNo))
			{
				deleteUser = userToBeDeleted;
				deleteUser.getId();
				deleteUser.getUserid();
			}
		
		Header ob1=new Header("token", token);
		
		response=RestAssured.given()
				.log().all()
				.header(ob1)
				.contentType(ContentType.JSON)
				.body(deleteUser)
				.when()
				.delete(EndPoints.DELETE_DATA);
		return response;
	}	
}
