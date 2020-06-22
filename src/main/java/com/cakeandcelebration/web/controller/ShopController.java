package com.cakeandcelebration.web.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.Row;

@RestController
public class ShopController {
	  @CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/api/v1/orderDetails")
	public String displayIndexPage() {		  
		  
		  //return "[{ \"shopName\": \"Poona Central\", \"productName\" :\"cake\", \"quantity\" : \"3\" }, { \"shopName\": \"Poona Central2\" , \"productName\" :\"Roll\", \"quantity\" : \"5\"}]";
		  return "[{\"id\":\"1\", \"shopName\": \"Poona Central \",\"productList\": [{\"pestry\": \"2\"}, {\"cake\": \"2\"}]}, {\"id\":\"2\",\"shopName\": \"Poona Central 2\",\"productList\": [{\"item2\": \"2\"}, {\"item5\": \"2\"}]}]";
	}
	  @CrossOrigin(origins = "*", allowedHeaders = "*")
	  @GetMapping("/api/v1/getPdf")
	  public ResponseEntity<ByteArrayResource> getPdf() throws IOException {
		  String filename = "C:\\Users\\user\\Desktop\\testfile.pdf";
		  createPdf(filename);		  
		  
		  
		  Path path = Paths.get(filename);
	        byte[] data = Files.readAllBytes(path);
	        ByteArrayResource resource = new ByteArrayResource(data);
	        
	        return ResponseEntity.ok()
	                // Content-Disposition
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
	                // Content-Type
	                .contentType(MediaType.APPLICATION_PDF) //
	                // Content-Lengh
	                .contentLength(data.length) //
	                .body(resource);
	  }
	private void createPdf(String fileLoc) throws IOException {
		PDPage myPage = new PDPage(PDRectangle.A4);
			PDDocument mainDocument = new PDDocument();

			// Dummy Table
			float margin = 50;
			// starting y position is whole page height subtracted by top and bottom margin
			float yStartNewPage = myPage.getMediaBox().getHeight() - (2 * margin);
			// we want table across whole page width (subtracted by left and right margin
			// ofcourse)
			float tableWidth = myPage.getMediaBox().getWidth() - (2 * margin);

			boolean drawContent = true;
			float yStart = yStartNewPage;
			float bottomMargin = 70;
			// y position is your coordinate of top left corner of the table
			float yPosition = 550;

			BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument,
					myPage, true, drawContent);
			String[] str = { "ShopName", "ProductName", "Quantity" };
			String[] val = { "Poona Central", "Pestry", "2", "Poona Central 2", "Pestry", "3" };

		
			 Row<PDPage> headerRow = table.createRow(15f);
			    table.addHeaderRow(headerRow);
			    
			for (int i = 0; i < 3; i++) {
				Cell<PDPage> cell = headerRow.createCell(20, str[i]);
			}

			    int count = 0;
			    for (int j = 0; j < 2; j++) {
			    	Row<PDPage> row = table.createRow(12);
			    	for(int k = 0; k<3; k++) {
			    		Cell<PDPage> cell = row.createCell(20, val[count++]);
			    		
			    	}
			    	
			    }
			table.draw();
			mainDocument.addPage(myPage);
			mainDocument.save(fileLoc);
			mainDocument.close();
	}
}
