package mohan.inventorycheckor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


/* 
 * 45342,45071,45164,45505,45373,45304,47375,47030,47060,45211,45103,45142,43160,41061,45661,43103,43017,43345,45819,47326,47303,46186,47272,47023,47020,45390,45367,43009,43151,45135,45171,47030,47335,47390,47324 
 * 
 * https://www.freemaptools.com/find-zip-codes-inside-radius.htm
 * 
 * mvn clean compile assembly:single
 */
/**
 * Hello world!
 *
 */
public class Application 
{
    public static void main( String[] args ) throws IOException
    {
    	
	 System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Welcome to Simple Inventory locator!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     System.out.println("Enter item number(with comma separated):");
     //System.out.println("Enter zip codes with comma separated:");
     //String zipcodes = br.readLine();
     String zipcodes = "45342,45071,45164,45505,45373,45304,47375,47030,47060,45211,45103,45142,43160,41061,45661,43103,43017,43345,45819,47326,47303,46186,47272,47023,47020,45390,45367,43009,43151,45135,45171,47030,47335,47390,47324";
     //String zipcodes = "45342,45071,45164";
     String itemIdsString = br.readLine();
     if(itemIdsString == null || itemIdsString.trim() == ""){
    	 System.err.println("Item ids cannot be empty!! Sorry :)");
         System.exit(-1);
     }
     
     String overirde = "";
     System.out.println("Would you like to override this zipcodes?(ex:'yes' or 'no') :"+zipcodes);
     BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	 overirde = bufferRead.readLine();
	 while(overirde == null || (!overirde.equalsIgnoreCase("yes") && !overirde.equalsIgnoreCase("no"))){
		 System.out.println("Entered value is invalid, you must enter 'yes' or 'no' :");
		 overirde = bufferRead.readLine();
	 }
	 if(overirde.equalsIgnoreCase("yes")){
		 System.out.println("please enter your zipcodes:");
		 zipcodes = bufferRead.readLine();
		 if(zipcodes == null || zipcodes.trim() == ""){
	    	 System.err.println("Zipcodes cannot be empty!! Sorry :)");
	         System.exit(-1);
	     }
	 }
     
	 String [] itemIdsArray = itemIdsString.split(",");
	 System.out.println("Processing...");
     if(itemIdsArray != null && itemIdsArray.length>0){
    	 WebDriver driver = new FirefoxDriver();
   	  	 driver.get("http://brickseek.com/walmart-inventory-checker");
    	 XSSFWorkbook workbook = new XSSFWorkbook(); 
    	 for(int jj =0 ; jj<itemIdsArray.length; jj++){
    		long  itemId =0;
    	     try{
    	    	 itemId = Long.parseLong(itemIdsArray[jj]);
    	     }catch(NumberFormatException nfe){
    	         System.err.println("Invalid item number Format = "+itemIdsArray[jj]);
    	         //System.exit(-1);
    	     }
    	     
    	     String [] zipCodeArray = zipcodes.split(",");
    	     if(zipCodeArray != null && zipCodeArray.length>0){
    	    	 Map<String, WalmartInventoryItem> map = new HashMap<String, WalmartInventoryItem>();
    	   	  	 for(int ii=0; ii<zipCodeArray.length;ii++){
    	   	  		 if(zipCodeArray[ii] != null){
    	   	  			 WebElement itemnumber = driver.findElement(By.xpath("//input[@name='item_id']"));
    	   	  			 itemnumber.clear();
    	   	  			 itemnumber.sendKeys(Long.toString(itemId));
    	   	  			 WebElement zip = driver.findElement(By.xpath("//input[@name='zip']"));
    	   	  			 zip.clear();
    	   	  			 zip.sendKeys(zipCodeArray[ii]);
    	   	  			 driver.findElement(By.xpath("//input[@name='zip']")).submit();
    	   	  			 
        	   	  		 WebElement itemNameElement = driver.findElement(By.xpath("//section[@id='main']/div[5]/a"));
            	   	  	 String itemName = null;
            	   	  	 if(itemNameElement != null){
            	   	  		 itemName = itemNameElement.getText();
            	   	  	 }
    	   	  			 
    	   	  			 List<WebElement> tr_collection =   driver.findElements(By.xpath("*//section[@id='main']//table//tbody//tr"));
    	   	  			 //System.out.println("NUMBER OF results = " + tr_collection.size());
    	   	  			 int col_num;
    	   	  			 
    	   	  			 if( tr_collection.size() > 0 ){
    	   	  				 for(WebElement trElement : tr_collection){
    		   		            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
    		   		            col_num = 1;
    		   		            //System.out.println("NUMBER OF COLUMNS="+td_collection.size());
    		   		            
    		   		            if(td_collection.size() > 0 && td_collection.size() == 2){
    		   		            	WalmartInventoryItem inventoryItem = new WalmartInventoryItem();
    		   		            	inventoryItem.setItemId(Long.toString(itemId));
    		   		            	inventoryItem.setItemName(itemName);
    		   		            	for(WebElement tdElement : td_collection)
    		   	    	            {
    		   		            		if(col_num == 1){
    		   		            			String storeDetails = tdElement.getText();
    		   		            			String lines[] = storeDetails.split("\\r?\\n");
    		   		            			inventoryItem.setStoreName(lines[0]);
    		   		            			inventoryItem.setStoreAddress(lines[1]+", "+lines[2]);
    		   		            			inventoryItem.setPhone(lines[3]);
    		   		            			String [] distanceArray = lines[4].split(" ");
    		   		            			inventoryItem.setDistance(Double.parseDouble(distanceArray[0].trim()));
    		   		            		}else if(col_num == 2){
    		   		            			String stockDetails = tdElement.getText();
    		   		            			String lines[] = stockDetails.split("\\r?\\n");
    		   		            			String[] priceString = lines[0].split(" ");
    		   		            			inventoryItem.setPrice(Double.parseDouble(priceString[1].trim()));
    		   		            			inventoryItem.setStatus(lines[1]);
    		   		            			inventoryItem.setEstQuantity(lines[2]);
    		   		            		}
    		   		            		col_num++;
    		   	    	            }
    		   		            	
    		   		            	map.put(inventoryItem.getStoreName(), inventoryItem);
    		   		            }
    		   		            
    	   	  				 } 
    	   	  			 }
    	   	  		 }
    	   	  	 }
    	   	  	 
    	   	  	 System.out.println("*************************************************************************************************************************");
    	   	  	 System.out.println("********************************************** Search Complete for item id = "+itemId+"**********************************");
    	   	  	 System.out.println("*************************************************************************************************************************");
    	   	  	 
    	   	  	 
    	   	  	List<WalmartInventoryItem> items = new ArrayList<WalmartInventoryItem>();
    			for (Map.Entry<String, WalmartInventoryItem> entry : map.entrySet())
    			{
    			    //System.out.println(entry.getValue().toString());
    			    items.add(entry.getValue());
    			}
    			Collections.sort(items,new PriceComparator());
    			
    			for(WalmartInventoryItem item : items){
    		  		 System.out.println(item.toString());
    		  	}
    			prepareExcelSheet(workbook, items);
    			System.out.println("*************************************************************************************************************************");
    			System.out.println("*************************************************************************************************************************");
    			System.out.println("*************************************************************************************************************************");
    			System.out.println("*************************************************************************************************************************");
    	     }
    	     
    	     
    	}
    	try
        {
            //Write the workbook in file system
        	String fileName = "WM_Inventory_"+new Date().getTime()+".xlsx";
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            System.out.println(fileName+" written successfully on disk.");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    	driver.close();
     }
     
     }
    
    private static void prepareExcelSheet(XSSFWorkbook workbook, List<WalmartInventoryItem> items){
    	XSSFSheet sheet = workbook.createSheet(items.get(0).getItemId());
        int rownum = 0;
        int cellnum = 0;
        Row row = sheet.createRow(rownum++);	
        Cell cell = row.createCell(cellnum++);
        cell.setCellValue("S.No");
        cell = row.createCell(cellnum++);
        cell.setCellValue("Store Details");
        cell = row.createCell(cellnum++);
        cell.setCellValue("Price");
        cell = row.createCell(cellnum++);
        cell.setCellValue("Status");
        cell = row.createCell(cellnum++);
        cell.setCellValue("Quantity");
        cell = row.createCell(cellnum++);
        cell.setCellValue("Distance");
        cell = row.createCell(cellnum++);
        cell.setCellValue("Item Details");
        
        for(WalmartInventoryItem item : items){
        	cellnum = 0;
        	Row rowData = sheet.createRow(rownum++);	
        	Cell cellData = rowData.createCell(cellnum++);
        	cellData.setCellValue(rownum);
        	cellData = rowData.createCell(cellnum++);
        	cellData.setCellValue(item.getStoreName()+" " + item.getStoreAddress()+"");
        	cellData = rowData.createCell(cellnum++);
        	cellData.setCellValue(item.getPrice());
        	cellData = rowData.createCell(cellnum++);
        	cellData.setCellValue(item.getStatus());
        	cellData = rowData.createCell(cellnum++);
        	cellData.setCellValue(item.getEstQuantity());
        	cellData = rowData.createCell(cellnum++);
        	cellData.setCellValue(item.getDistance());
        	cellData = rowData.createCell(cellnum++);
        	cellData.setCellValue(item.getItemId()+"|"+item.getItemName());
	  	}
        
    }
}
