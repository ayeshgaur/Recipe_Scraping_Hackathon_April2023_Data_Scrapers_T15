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

        driver.findElement(By.xpath("//div/a[text()= 'Recipe A To Z']")).click();
        Thread.sleep(2000);
        int rowCounter = 1;
        // run in a loop for all recipe in a page
        for (int j = 1; j <= 22; j++) {

            int pageindex = j;
            driver.navigate().to("https://www.tarladalal.com/RecipeAtoZ.aspx?pageindex=" + j);
            List<WebElement> recipeElements = driver.findElements(By.xpath("//span[@class='rcc_recipename']"));
            List<String> recipeUrls = new ArrayList<>();
            //Looping through all recipes Web elements and generating a navigation URL
            recipeElements.stream().forEach(recipeElement -> {
                recipeUrls.add("https://www.tarladalal.com/" + recipeElement.findElement(By.tagName("a")).getDomAttribute("href"));
            });

            for (int i = 0; i < recipeUrls.size(); i++) {
                String recipeUrl = recipeUrls.get(i);
                driver.navigate().to(recipeUrl);
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                WebElement ingredientList = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));

                List<String> eliminators = Arrays.asList(new String[]{"Cake", "Pastries", "White Bread", "Pizza",
                        "Burger", "ice cream", "soda", "Red Meat", "Processed Meat", "Milk", "Cheese", "Yogurt", "curd",
                        "soy", "wheat", "pasta", "cereal", "gluten", "white rice", "Doughnuts", "Fries", "coffee", "vegetable oil",
                        "soybean oil", "canola oil", "rapeseed oil", "sunflower oil", "safflower oil"});


                if (isEliminated(ingredientList, eliminators)) {
                    //driver.navigate().to("//div/a[text()= 'Recipe A To Z']");
                } else {
                    WriteExcel writeOutput = new WriteExcel();
                    //Recipe id
                    try {
                        String recipeId = recipeUrl.substring(recipeUrls.lastIndexOf('-') + 1);
                        System.out.print(recipeId);
                        writeOutput.setCellData("PCOS", rowCounter, 0, recipeId);
                    } catch (Exception I) {

                    }

                    //Recipe Name
                    try {
                        WebElement recipeTitle = driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
                        System.out.print(recipeTitle.getText());
                        writeOutput.setCellData("PCOS", rowCounter, 1, recipeTitle.getText());

                    } catch (Exception e) {

                    }
                    try {
                        WebElement recipeCategory = driver.findElement(By.xpath("//span[@itemprop= 'description']/*[contains (text(), 'breakfast') or contains (text(), 'lunch') or contains (text(), 'dinner')]"));
                        System.out.print(recipeCategory.getText());
                        writeOutput.setCellData("PCOS", rowCounter, 2, recipeCategory.getText());

                    } catch (Exception e) {

                    }
                    try {
                        WebElement foodCategory = driver.findElement(By.xpath("//a/span[text()= 'No Cooking Veg Indian']"));
                        System.out.print(foodCategory.getText());
                        writeOutput.setCellData("PCOS", rowCounter, 3, foodCategory.getText());

                    } catch (Exception e) {

                    }

                    try {
                        WebElement nameOfIngredients = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));
                        System.out.print(nameOfIngredients.getText());
                        writeOutput.setCellData("PCOS", rowCounter, 4, nameOfIngredients.getText());

                    } catch (Exception e) {

                    }

                    try {
                        WebElement preparationTime = driver.findElement(By.xpath("//p/time[@itemprop= 'prepTime']"));
                        System.out.print(preparationTime.getText());
                        writeOutput.setCellData("PCOS", rowCounter, 5, preparationTime.getText());

                    } catch (Exception e) {

                    }

                    try {
                        WebElement cookTime = driver.findElement(By.xpath("//p/time[@itemprop= 'cookTime']"));
                        System.out.print(cookTime.getText());
                        writeOutput.setCellData("PCOS", rowCounter, 6, cookTime.getText());

                    } catch (Exception e) {

                    }

                    try {
                        WebElement prepMethod = driver.findElement(By.xpath("//div[@id= 'ctl00_cntrightpanel_pnlRcpMethod']"));
                        System.out.print(prepMethod.getText());
                        writeOutput.setCellData("PCOS", rowCounter, 7, prepMethod.getText());

                    } catch (Exception e) {

                    }
                    try {
                        WebElement nutrients = driver.findElement(By.xpath("//table[@id= 'rcpnutrients']"));
                        System.out.print(nutrients.getText());
                        writeOutput.setCellData("PCOS", rowCounter, 8, nutrients.getText());

                    } catch (Exception e) {

                    }
                    try {
                        System.out.print(recipeUrl);
                        writeOutput.setCellData("PCOS", rowCounter, 9, recipeUrl);
                    } catch (Exception e) {

                    }

                }

                rowCounter++;
            }
        }

    }


    private boolean isEliminated(WebElement rcpinglist, List<String> eliminators) {
        AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);
        eliminators.parallelStream().forEach(eliminator -> {
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
