package com.test.tests;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import com.test.models.GetUserDataPOJO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import com.test.helpers.UserServiceBase;
import com.test.helpers.ReusableMethods;

public class TekarchApiTestScriptE2E extends UserServiceBase
{
	//protected Logger log;
	
	@BeforeClass
	public void init() 
	{
		RestAssured.baseURI = super.url;
	}
	
	@Test
	public void validLogin()
	{
		log.info("Begin tekarchApi_Login");
		
		//Arrange and Act are done in getToken
		String token = getToken();
		
		//Assert
		AssertJUnit.assertNotNull(token);
		
		log.info("Token is " + token);
		log.info("End tekarchApi_Login");
	}
	
	@Test(dependsOnMethods = "validLogin")
	public void createUserData()
	{
		log.info("Begin tekarchApi_CreateUser");
		
		//Arrange and Act are done in addUserData
		Response res = addUserData();
		
		//Assert
		ReusableMethods.verifyStatusCodeIs(res, 201);
		ReusableMethods.verifySchema(response,"SuccessSchema.json");
		log.info("Create user Schema validated");
		
		String status = ReusableMethods.getjsonPathData(response, "success");
		AssertJUnit.assertEquals(status, "success");
		log.info("Status message validated");
		
		log.info("End tekarchApi_CreateUser");
	}
	
	@Test(dependsOnMethods = "createUserData")
	public void getuserData()
	{
		log.info("Begin tekarchApi_getUser");
		
		//Arrange and Act and Assert are done in getUserData
		List<GetUserDataPOJO> listofUser = getUserData();
		
		log.info("First account number = " + listofUser.get(0).getAccountno());
		log.info("End tekarchApi_getUser");
	}
	
	@Test(dependsOnMethods = "getuserData")
	public void updateuserData()
	{
		log.info("Begin tekarchApi_updateUser");
		
		//Arrange and Act are done in updateUserData
		Response response = updateUserData();
		
		//Assert
		ReusableMethods.verifyStatusCodeIs(response, 200);
		ReusableMethods.verifySchema(response,"SuccessSchema.json");
		log.info("Update User Schema validated");
		log.info("Data is updated");
		
		String status = ReusableMethods.getjsonPathData(response, "success");
		AssertJUnit.assertEquals(status, "success");
		log.info("Status message validated");
		
		log.info("End tekarchApi_updateUser");
	}
	
	@Test(dependsOnMethods = "updateuserData")
	public void deleteuserData()
	{
		log.info("Begin tekarchApi_deleteUser");
		
		//Arrange and Act are done in deleteUserData
		Response response = deleteUserData();
		
		//Assert
		ReusableMethods.verifyStatusCodeIs(response, 200);
		ReusableMethods.verifySchema(response,"SuccessSchema.json");
		log.info("Delete User Schema validated");
		log.info("Deleted successfully");
		
		String status = ReusableMethods.getjsonPathData(response, "success");
		AssertJUnit.assertEquals(status, "success");
		log.info("Status message validated");
		
		log.info("End tekarchApi_deleteUser");
	}
}
