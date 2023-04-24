package com.tarladala.recipe.scraping.Allergy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.tarladala.recipe.scraping.base.BaseClass;
import com.tarladala.recipe.scraping.comorbidityPcos.WebElementObjects;
import com.tarladala.recipe.scraping.utilities.WriteExcel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Allager extends BaseClass {

	@Test
	public void allrecipe () throws InterruptedException, IOException
	{
		String xpathstralpha;
		int setCellvalue = 1;
		//WebElementObjects eliminateObjects = new WebElementObjects();
		driver.findElement(By.xpath("//a[@href='RecipeAtoZ.aspx']")).click(); 
		Thread.sleep(1000);

		for(int i = 3; i <=5; i++)  // Recipe Pagination A(3) to Z(53) 
		{
			if (i>= 5) // 
			{

				xpathstralpha = "//div/div[1]/div[1]/table[1]/tbody/tr/td["+i+"]";
				driver.findElement(By.xpath(xpathstralpha)).click();
			} 
			for(int j = 1; j <=2; j++)  // Recipe Pagination 1 to 100000000
			{
				if(i%2!=0 && j>1)
				{

					String xpathstr="//div[1]/div[2]/a[text()='"+j+"']";
					driver.findElement(By.xpath(xpathstr)).click();
					//driver.findElement(By.xpath("(//span[1]/a)[1]")).click();
					List<String> eliminatorsList= Arrays.asList(new String []{"Milk","Soy","Egg","Sesame","Peanuts","walnut","almond","hazelnut","pecan","cashew","pistachio","Shell fish","Seafood"});
					//WebElement ingrendientList = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
					//ingrendientList = ingrendientList.toLowerCase();

					List<WebElement> recipeElements = driver.findElements(By.xpath("//span[@class='rcc_recipename']"));
					List<String> recipeUrls = new ArrayList<>();
					//Looping through all recipes Web elements and generating a navigation URL
					recipeElements.stream().forEach(recipeElement -> {
						recipeUrls.add("https://www.tarladalal.com/" + recipeElement.findElement(By.tagName("a")).getDomAttribute("href"));
					});
					for (int recipTrav = 0; recipTrav < recipeUrls.size(); recipTrav++) 
					{
						String recipeUrl = recipeUrls.get(recipTrav);
						driver.navigate().to(recipeUrl);
						//driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
						Thread.sleep(1000);

						WebElement ingrendientList = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));

						if (isEliminated(ingrendientList, eliminatorsList)) {

						}

						else {

							WriteExcel writeOutput = new WriteExcel();
							WebElement recipeTitle = driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
							System.out.println(recipeTitle.getText());
							writeOutput.setCellData("ALLERGIES", setCellvalue,1, recipeTitle.getText());
							Thread.sleep(2000);

							//preparation time
							try {
							WebElement preprationtime = driver.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
							((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", preprationtime);
							System.out.println("Preparation Time:"+" "+preprationtime.getText());
							writeOutput.setCellData("ALLERGIES", setCellvalue,5, preprationtime.getText());
							} catch (Exception e) {
								// foodCategory	 
							}
							
							// recipeCategory
							try {
								WebElement recipeCategory = driver.findElement(By.xpath("//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));
								writeOutput.setCellData("ALLERGIES", setCellvalue, 2, recipeCategory.getText());
								System.out.print(recipeCategory.getText());
							} catch (Exception e) {
								// foodCategory	 
							}
							try {
								WebElement foodCategory = driver.findElement(By.xpath("//a/span[text() = 'No Cooking Veg Indian']"));
								writeOutput.setCellData("ALLERGIES", setCellvalue, 3, foodCategory.getText());
								System.out.print(foodCategory.getText());
							} catch (Exception e) {}

							//cooking time
							try {
							WebElement cookingtime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
							System.out.println("Cooking Time:"+" "+cookingtime.getText());
							writeOutput.setCellData("ALLERGIES", setCellvalue,6, cookingtime.getText());
							} catch (Exception e) {
								// foodCategory	 
							}
							
							try {
							//Preparation Method
							WebElement preprationMethod = driver.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
							System.out.println("Preparation Method:"+" "+preprationMethod.getText());
							writeOutput.setCellData("ALLERGIES", setCellvalue,7, preprationMethod.getText());
							} catch (Exception e) {
								// foodCategory	 
							}

							// print`Ingredients list
							try {
							WebElement extractIngrendientlist = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
							System.out.println("Ingredients List:"+" "+extractIngrendientlist.getText());
							writeOutput.setCellData("ALLERGIES", setCellvalue,4, extractIngrendientlist.getText());
							} catch (Exception e) {
								// foodCategory	 
							}
							//Nutrients value of recipe


							try {
								WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
								writeOutput.setCellData("ALLERGIES", setCellvalue, 8, nutrients.getText());
								System.out.print(nutrients.getText());
							} catch (Exception e) {

							}

							//Recipe Current URL
							try {
							String strUrl = driver.getCurrentUrl();
							System.out.println("Recipe Url is:"+ strUrl);
							writeOutput.setCellData("ALLERGIES", setCellvalue,9, strUrl);
						
							//Recipe ID from Current URL

							String regex = "\\d+";
							Pattern pattern = Pattern.compile(regex);
							Matcher matcher = pattern.matcher(strUrl);
							if (matcher.find()) {
								int Receipeid = Integer.parseInt(matcher.group());
								String Receipeidstr = Integer.toString(Receipeid);
								writeOutput.setCellData("ALLERGIES", setCellvalue,0, Receipeidstr);
							}
							} catch (Exception e) {
								// foodCategory	 
							}

							setCellvalue++;
							driver.navigate().back();
							Thread.sleep(1000);

						}

					}
				}
			}

		}
	}

	private boolean isEliminated(WebElement rcpinglist, List<String> eliminators) {
		AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);
		/*	eliminators.parallelStream().forEach(eliminator -> {
			try {
				if (null != rcpinglist.findElement(By.xpath("//*[text() ='" + eliminator + "']"))) {
					isEliminatorPresent.set(true);
				}
			} catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}
		});*/
		for (String expectedValue : eliminators) {
			try {
				if (rcpinglist.getText().equalsIgnoreCase(expectedValue)) 
					isEliminatorPresent.set(true);
			}
			catch (Exception e) {
				System.out.print("No Such Element " + e.getLocalizedMessage());
			}

		}   
		return isEliminatorPresent.get();
	}
}














