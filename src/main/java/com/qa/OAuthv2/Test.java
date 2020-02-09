package com.qa.OAuthv2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

import java.util.Iterator;
import java.util.List;
public class Test {

	public static void main(String args[]) {
		
		/*WebDriverManager.chromedriver().setup();
		
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
		
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("warang.rohan.11et1067@gmail.com");
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("googlerocks@123");
		driver.findElement(By.xpath("//span[text()='Next']")).click();*/
		
		String authUrl = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4/wQGRN6AFuMPpX82Kmr_syFSAeggu7XuulTNE2HDwoOVanK81f2Uo3eKZ0Jey1OCM0CGHn8EQ_vwTFQzmIgejt24&scope=email+https://www.googleapis.com/auth/userinfo.email+openid&authuser=0&prompt=none&session_state=59269ba0ce92b13f40d34b920e9e17d5f6ce787d..3f71";
		String code = (authUrl.split("=")[2]).split("&")[0];
		
		//String code = "4%2FwQF_8hsFUupwVZzFqFinkFONTUwnBBQ37JiTsVYf2qo-MK9eKQvbN_FY_zA7JJEmdbtOA1qI3AyjNH3EEOKcJ9g";
		
		String resp = given().urlEncodingEnabled(false)
		.queryParams("code",code)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		
		System.out.println(resp);
		
		JsonPath js = new JsonPath(resp);
		
		String accessToken = js.getString("access_token");
		
		
		
		
		GetCourses gc = given().
		queryParam("access_token",accessToken)
		.expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);
		
		
		
		System.out.println(gc.getExpertise());
		
		System.out.println(gc.getLinkedIn());
		
		
		
		List<Mobile> mbList = gc.getCourses().getMobile();
		Iterator<Mobile> it = mbList.iterator();
		while(it.hasNext()) {
			
			Mobile mb = it.next();
			System.out.println(mb.getPrice());
			System.out.println(mb.getCourseTitle());
			
		}
		//System.out.println(apiResp);
		
		
		
		
		
	}
	
	
}
