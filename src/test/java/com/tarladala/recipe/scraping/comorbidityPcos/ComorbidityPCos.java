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

      List<WebElement> recipeElements=  driver.findElements(By.xpath("//span[@class='rcc_recipename']"));
      List<String> recipeUrls = new ArrayList<>();
      //Looping through all recipes Web elements and generating a navigation URL
      recipeElements.stream().forEach(recipeElement -> {
            recipeUrls.add("https://www.tarladalal.com/"+recipeElement.findElement(By.tagName("a")).getDomAttribute("href"));
        });

      //Looping through all URLs and navigating to
        recipeUrls.stream().forEach(recipeUrl -> {
            // Same tab open the url
            driver.navigate().to(recipeUrl);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            WebElement ingredientList = driver.findElement(By.xpath("//div[@id= 'rcpinglist']"));

            List<String> eliminators= Arrays.asList(new String []{"Cake", "Pastries", "rose"});


            if (isEliminated(ingredientList,eliminators)) {
                //driver.navigate().back();
            } else {
                WebElement recipeTitle = driver.findElement(By.xpath("//span[@id= 'ctl00_cntrightpanel_lblRecipeName']"));
                WriteExcel writeOutput = new WriteExcel();
                //public void setCellData(String sheetName, int rownum, int column, String data)
                try {
                    writeOutput.setCellData("Sheet1", 1,0, recipeTitle.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.print(recipeTitle.getText());
            }
        });



    }
    private boolean isEliminated(WebElement rcpinglist, List<String> eliminators){
        AtomicBoolean isEliminatorPresent = new AtomicBoolean(false);
        eliminators.stream().forEach(eliminator ->{
           try{
               if(null!= rcpinglist.findElement(By.xpath("//*[text() ='"+eliminator+"']"))) {
                   isEliminatorPresent.set(true);
               }
           } catch (Exception e) {
               System.out.print("No Such Element "+e.getLocalizedMessage());
           }
        });
        return isEliminatorPresent.get();
    }
}
