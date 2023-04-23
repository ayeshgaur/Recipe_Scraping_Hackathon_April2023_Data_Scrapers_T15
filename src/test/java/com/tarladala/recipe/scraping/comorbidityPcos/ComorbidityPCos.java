package com.tarladala.recipe.scraping.comorbidityPcos;

import com.tarladala.recipe.scraping.base.BaseClass;
import com.tarladala.recipe.scraping.utilities.WriteExcel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class ComorbidityPCos extends BaseClass {


    @Test
    public void extractRecipe() throws InterruptedException, IOException {

        //JavascriptExecutor js = (JavascriptExecutor)driver;
        driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
        Thread.sleep(2000);

        // run in a loop for all recipe in a page

        List<WebElement> recipeElements = driver.findElements(By.xpath("//span[@class='rcc_recipename']"));
        List<String> recipeUrls = new ArrayList<>();
        //Looping through all recipes Web elements and generating a navigation URL
        recipeElements.stream().forEach(recipeElement -> {
            recipeUrls.add("https://www.tarladalal.com/" + recipeElement.findElement(By.tagName("a")).getDomAttribute("href"));
        });

        for(int i=0; i<1; i++) {
            driver.navigate().to(recipeUrls.get(i));
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            WebElement ingredientList = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));

            List<String> eliminators = Arrays.asList(new String[]{"Cake", "Pastries", "White Bread", "Pizza",
                    "Burger", "ice cream", "soda", "Red Meat", "Processed Meat", "Milk", "Cheese", "Yogurt", "curd",
                    "soy", "wheat", "pasta", "cereal", "gluten", "white rice", "Doughnuts", "Fries", "coffee", "vegetable oil",
                    "soybean oil", "canola oil", "rapeseed oil", "sunflower oil", "safflower oil"});


            if (isEliminated(ingredientList, eliminators)) {
                driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
            } else {
               // WebElement recipeTitle = getElementText();
               // WebElement recipeCategory = null;
                WriteExcel writeOutput = new WriteExcel();
                try {
                   WebElement recipeTitle = driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
                    writeOutput.setCellData("PCOS", i+1, 2, recipeTitle.getText());
                    System.out.print(recipeTitle.getText());
                } catch (Exception e) {

                }
                try {
                   WebElement recipeCategory = driver.findElement(By.xpath("//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));
                    writeOutput.setCellData("PCOS", i+1, 3, recipeCategory.getText());
                    System.out.print(recipeCategory.getText());
                } catch (Exception e) {

                }
                try {
                    WebElement foodCategory = driver.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));
                    writeOutput.setCellData("PCOS", i+1, 4, foodCategory.getText());
                    System.out.print(foodCategory.getText());
                } catch (Exception e) {

                }

                try {
                    WebElement nameOfIngredients = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
                    writeOutput.setCellData("PCOS", i+1, 5, nameOfIngredients.getText());
                    System.out.print(nameOfIngredients.getText());
                } catch (Exception e) {

                }

                try {
                    WebElement preparationTime = driver.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
                    writeOutput.setCellData("PCOS", i+1, 6, preparationTime.getText());
                    System.out.print(preparationTime.getText());
                } catch (Exception e) {

                }

                try {
                    WebElement cookTime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
                    writeOutput.setCellData("PCOS", i+1, 7, cookTime.getText());
                    System.out.print(cookTime.getText());
                } catch (Exception e) {

                }

                try {
                    WebElement prepMethod = driver.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
                    writeOutput.setCellData("PCOS", i+1, 8, prepMethod.getText());
                    System.out.print(prepMethod.getText());
                } catch (Exception e) {

                }
                try {
                    WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
                    writeOutput.setCellData("PCOS", i+1, 9, nutrients.getText());
                    System.out.print(nutrients.getText());
                } catch (Exception e) {

                }

               /* //public void setCellData(String sheetName, int rownum, int column, String data)
                try {
                    writeOutput.setCellData("Sheet1", i+1, 0, recipeTitle.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/


            }
        }
    }

    /*private static WebElement getElementText() {
        return driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
    }*/

    private boolean isEliminated(WebElement rcpinglist, List<String> eliminators) {
        AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);
        eliminators.stream().forEach(eliminator -> {
            try {
                if (null != rcpinglist.findElement(By.xpath("//*[text() ='" + eliminator + "']"))) {
                    isEliminatorPresent.set(true);
                }
            } catch (Exception e) {
                System.out.print("No Such Element " + e.getLocalizedMessage());
            }
        });
        return isEliminatorPresent.get();
    }
}
