package com.nasim.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.nasim.model.Product;
import com.nasim.utils.FileUpload;

@RestController
public class FileUploadController {
	@Value("${file.upload}")
	private String defaultFilePath;

	
	@PostMapping("/product")
	public Product saveProduct(@RequestParam(value="data")String data,  @RequestParam("file") MultipartFile file) throws Exception  {
		if(file==null || file.getOriginalFilename()==null ){
			System.out.println("File not found");
		}
		System.out.println("**********************************************"+defaultFilePath);

		System.out.println(data);
		Product p = FileUpload.convertStringToProduct(data);
		FileUpload.uploadFile(file, p.getCategory(), p.getProductName() ,defaultFilePath);

		String staticPath = FileUpload.creatStaticPath(p.getCategory(), p.getProductName(),file.getOriginalFilename());
		p.setImagePath(staticPath);	
		return p;
		}
	
		@GetMapping("/{category}/{product}/{fileName:.+}")
		public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,@PathVariable("category") String category,@PathVariable("product") String product, @PathVariable("fileName") String fileName) throws  IOException {
			File file = new File(FileUpload.creatStaticURL(defaultFilePath,category,product,fileName));
			
			if (file.exists()) {
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				//eg Content-Disposition: inline; filename="filename.pdf"
				response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
				response.setContentLength((int) file.length());
				
				InputStream inputStream;
				try {
					inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
}
