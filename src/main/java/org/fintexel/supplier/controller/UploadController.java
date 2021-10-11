package org.fintexel.supplier.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.amazonaws.services.s3.model.Bucket;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.fintexel.supplier.customerentity.CustomerRegister;
import org.fintexel.supplier.customerentity.PdfGenStrachingClass;
import org.fintexel.supplier.customerentity.SupplierInvoiceStraching;
import org.fintexel.supplier.customerrepository.CustomerRegisterRepo;
import org.fintexel.supplier.entity.BulkUploadSuccessError;
import org.fintexel.supplier.entity.CustomeResponseEntity;
import org.fintexel.supplier.entity.FileUploadResponse;
import org.fintexel.supplier.entity.VendorRegister;
import org.fintexel.supplier.exceptions.VendorErrorResponse;
import org.fintexel.supplier.exceptions.VendorNotFoundException;
import org.fintexel.supplier.helper.FileUploadHelper;
import org.fintexel.supplier.helper.JwtUtil;
import org.fintexel.supplier.helper.LoginUserDetails;
import org.fintexel.supplier.repository.VendorRegisterRepo;
import org.fintexel.supplier.service.S3Service;
import org.fintexel.supplier.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
public class UploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	private S3Service s3Factory;
	 
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private FileUploadHelper fileUploadHelper;
	
	@Autowired
	private VendorRegisterRepo registerRepo;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomerRegisterRepo customerRegisterRepo;

	
	
	@PostMapping("/upload")
	@ResponseBody
	public List<BulkUploadSuccessError> upload(@RequestParam("file") MultipartFile uploadFile) {
		LOGGER.info(uploadFile.getContentType());
		LOGGER.info(uploadFile.getName());
		LOGGER.info(uploadFile.getOriginalFilename());
		
		LOGGER.info("Inside - UploadController.upload()");
		try {
			byte[] bytes;
			if(uploadFile.isEmpty()) {
				LOGGER.info("File is Empty");
				throw new VendorNotFoundException("File is Empty");
				
			}else {
				String originalFilename = uploadFile.getOriginalFilename();
				String substring = originalFilename.substring(uploadFile.getOriginalFilename().lastIndexOf(".")+1, uploadFile.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				
				
//				LOGGER.info("File name is - "+uploadFile.getName());
			}
			
			
			Timestamp currentTimeStamp = new Timestamp(new Date().getTime());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date fetchNueva = format.parse(currentTimeStamp.toString());
			format = new SimpleDateFormat("dd MMM YYY HH:mm");
			
			LOGGER.info("Time Stamp - "+fetchNueva);
			
			String uploadRefID = uploadFile.getOriginalFilename()+"_"+format.format(fetchNueva);
			LOGGER.info("Ref ID  - "+uploadRefID);
			
			List<BulkUploadSuccessError> flag = uploadService.upload(uploadFile);
			LOGGER.info("return Flag  - "+flag);
			
			
			return flag;
		} catch (Exception e) {
			// TODO: handle exception
			throw new VendorNotFoundException(e.getMessage());
		}
		
		

	}
	
	
	@PostMapping("/update")
	public List<BulkUploadSuccessError> update(@RequestPart("file") final MultipartFile file) {
		LOGGER.info("Inside - UploadController.update()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.update(file);
			LOGGER.info("return Flag  - "+flag);
			return flag;
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@PostMapping("/uploadSupplierProof")
	public FileUploadResponse uploadSupplierProof(@RequestParam("file") MultipartFile file,
			@RequestHeader(name = "Authorization") String token) {
		LOGGER.info("Inside - UploadController.uploadSupplierProof()");
		try {
			if (token != null && token.startsWith("Bearer ")) {
				String jwtToken = token.substring(7);
				String userName = jwtUtil.extractUsername(jwtToken);
				Optional<VendorRegister> findByUsername = registerRepo.findByUsername(userName);
				if (findByUsername.isPresent()) {
					if (file.getSize() < 1) {
						throw new VendorNotFoundException("File is Empty");
					}
					Date currentDate = new Date();
					long milliSeconds = currentDate.getTime();
					FileUploadResponse uploadFile = s3Factory.uploadFile(milliSeconds+"_"+file.getOriginalFilename(),file.getBytes());
					return uploadFile;
//			        Map<String,String> result = new HashMap<>();
//			        result.put("key",file.getOriginalFilename());
			     //   return result;

//					FileUploadResponse uploadFile = fileUploadHelper.uploadFile(file);
//					if (!uploadFile.equals(null)) {
//						return uploadFile;
//					} else {
//						throw new VendorNotFoundException("Something went wrong !! Please try again");
//					}
				} else {
					Optional<CustomerRegister> findByCustomerUsername = customerRegisterRepo.findByUsername(userName);
					if (findByCustomerUsername.isPresent()) {
						if (file.getSize() < 1) {
							throw new VendorNotFoundException("File is Empty");
						}

						Date currentDate = new Date();
						long milliSeconds = currentDate.getTime();
						FileUploadResponse uploadFile = s3Factory.uploadFile(milliSeconds+"_"+file.getOriginalFilename(),file.getBytes());
						return uploadFile;
//						FileUploadResponse uploadFile = fileUploadHelper.uploadFile(file);
//						if (!uploadFile.equals(null)) {
//							return uploadFile;
//						} else {
//							throw new VendorNotFoundException("Something went wrong !! Please try again");
//						}
					} else {
						throw new VendorNotFoundException("We can't find your details");
					}
				}
			}
			else {
				throw new VendorNotFoundException("Token note found");
			}
//			String loginSupplierCode = loginUserDetails.getLoginSupplierCode(token);
//			if (!loginSupplierCode.equals(null)) {
//				if (file.getSize() < 1) {
//					throw new VendorNotFoundException("File is Empty");
//				}
//
//				FileUploadResponse uploadFile = fileUploadHelper.uploadFile(file);
//				if (!uploadFile.equals(null)) {
//					return uploadFile;
//				} else {
//					throw new VendorNotFoundException("Something went wrong !! Please try again");
//				}
//			} else {
//				throw new VendorNotFoundException("Token not valid");
//			}
			
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			throw new VendorNotFoundException(e.getMessage());
		}

	}
	
	
	
	
	@PostMapping("/uploadCurrencyType")
	public List<BulkUploadSuccessError> uploadCurrencyType(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadCurrencyType(file);
			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
	}
	
	@PostMapping("/uploadRegType")
	public List<BulkUploadSuccessError> uploadRegType(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadRegType(file);
			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		
	}
	
	
	@PostMapping("/uploadDept")
	public List<BulkUploadSuccessError> uploadDept(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadDept(file);
			LOGGER.info("return Flag  - "+flag);
			return flag;
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		
	}

	
	@PostMapping("/uploadRole")
	public List<BulkUploadSuccessError> uploadRole(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					
					
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadRole(file);
			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
		
	}
	
	@PostMapping("/uploadFunc")
	public List<BulkUploadSuccessError> uploadFunc(@RequestParam("file") MultipartFile file) {
		
		LOGGER.info("Inside - UploadController.uploadRegType()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					System.out.println("file type  _____---------  " + substring);
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.uploadFunc(file);
			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
		
	
	}
	
	@PostMapping("/uploadRegionCountry")
	public List<BulkUploadSuccessError> uploadRegionCountry(@RequestParam("file") MultipartFile file){
		
		LOGGER.info("Inside - UploadController.uploadRegionCountry()");
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					System.out.println("file type  _____---------  " + substring);
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.bulkUploadRegionCountry(file);
//			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
		
	}
	
	@PostMapping("/uploadContractAndAddressType")
	public List<BulkUploadSuccessError> uploadContractAndAddressType(@RequestParam("file") MultipartFile file , @RequestHeader(name = "Authorization") String token) {
		
		LOGGER.info("Inside - UploadController.uploadContractAndAddress()");
		
		try {
			
			if(file.isEmpty()) {
				LOGGER.info("File is Empty");
			}else {
				String originalFilename = file.getOriginalFilename();
				String substring = originalFilename.substring(file.getOriginalFilename().lastIndexOf(".")+1, file.getOriginalFilename().length());
				if(substring.toLowerCase().equals("xls") || (substring.toLowerCase().equals("xlsb")) || substring.toLowerCase().equals("xlsm") || substring.toLowerCase().equals("xlsx") || substring.toLowerCase().equals("ods"))  {

					
				}else {
					System.out.println("file type  _____---------  " + substring);
					throw new VendorNotFoundException("File Type Should be xls/xlsb/xlsm/xlsx/ods");
				}
				LOGGER.info("File name is - "+file.getName());
			}
			
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strDate= formatter.format(date);		
			String uploadRefID = file.getOriginalFilename()+"_"+strDate.toString();
			LOGGER.info("Ref ID  - "+uploadRefID);

			
			List<BulkUploadSuccessError> flag = uploadService.bulkUploadContractAndAddressType(file,token);
//			LOGGER.info("return Flag  - "+flag);
//			return new CustomeResponseEntity("SUCCESS", "Valid Data are Added");
			return flag;
			
		}catch(Exception e) {
			throw new VendorNotFoundException(e.getMessage());
		}
	}
	
	
	
	 @PostMapping(path = "/uploadfile")
	    public Map<String,String> uploadFile(@RequestPart(value = "file", required = false) MultipartFile files , @RequestHeader("Authorization") String token) {
		 try {
			  s3Factory.uploadFile(files.getOriginalFilename(),files.getBytes());
		        Map<String,String> result = new HashMap<>();
		        result.put("key",files.getOriginalFilename());
		        return result;
		 }catch(Exception e) {
			 throw new VendorNotFoundException(e.getMessage());
		 }
	      
	    }
	    @GetMapping(path = "/buckets")
	    public List<Bucket> listBuckets( @RequestHeader("Authorization") String token){
	        return s3Factory.getAllBuckets();
	    }
	    
	    @GetMapping("/list/files")
	    public ResponseEntity<List<String>> getListOfFiles(@RequestHeader("Authorization") String token) {
	        return new ResponseEntity<>(s3Factory.listFiles(), HttpStatus.OK);
	    }
	    @GetMapping(path = "/download/{file}")
	    public ByteArrayResource uploadFile(@PathVariable(name = "file") String file) {
	        byte[] data = s3Factory.getFile(file);
	        ByteArrayResource resource = new ByteArrayResource(data);
	        return resource;
//	        return ResponseEntity
//	                .ok()
//	                .contentLength(data.length)
//	                .header("Content-type", "application/octet-stream")
//	                .header("Content-disposition", "attachment; filename=\"" + file + "\"")
//	                .body(resource);

	    }
	    @GetMapping(path = "/getUrl/{file}")
	    public ResponseEntity<?> getUrl(@PathVariable(name = "file") String fileName , @RequestHeader("Authorization") String token) {
	        return s3Factory.getPublicUrl(fileName);
	       

	    }
	    
	    
	    @PostMapping("/genpdf")
		HttpEntity<byte[]> createPdf(@RequestBody PdfGenStrachingClass pdfGenStrachingClass) throws IOException {
			System.out.println("ok");

			/* first, get and initialize an engine */
			VelocityEngine ve = new VelocityEngine();
			

			/* next, get the Template */
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class",
					ClasspathResourceLoader.class.getName());
			ve.init();
			Template t = ve.getTemplate("templates/helloworld.vm");
			/* create a context and add data */
			VelocityContext context = new VelocityContext();
			context.put("pdfGenStrachingClass", pdfGenStrachingClass);
//			context.put("genDateTime", LocalDateTime.now().toString());
			/* now render the template into a StringWriter */
			StringWriter writer = new StringWriter();
			t.merge(context, writer);
			/* show the World */
			System.out.println(writer.toString());
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			baos = generatePdf(writer.toString());

			HttpHeaders header = new HttpHeaders();
		    header.setContentType(MediaType.APPLICATION_PDF);
		    header.set(HttpHeaders.CONTENT_DISPOSITION,
		                   "attachment; filename=" + "soumen");
		    header.setContentLength(baos.toByteArray().length);

		    return new HttpEntity<byte[]>(baos.toByteArray(), header);

		}
		
		
		@PostMapping("/genpdf/supplier")
		HttpEntity<byte[]> createPdfSupplier(@RequestBody SupplierInvoiceStraching supplierInvoiceStraching) throws IOException {
			System.out.println("ok");

			/* first, get and initialize an engine */
			VelocityEngine ve = new VelocityEngine();
			

			/* next, get the Template */
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class",
					ClasspathResourceLoader.class.getName());
			ve.init();
			Template t = ve.getTemplate("templates/invoice.vm");
			/* create a context and add data */
			VelocityContext context = new VelocityContext();
			context.put("supplierInvoiceStraching", supplierInvoiceStraching);
//			context.put("genDateTime", LocalDateTime.now().toString());
			/* now render the template into a StringWriter */
			StringWriter writer = new StringWriter();
			t.merge(context, writer);
			/* show the World */
			System.out.println(writer.toString());
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			baos = generatePdf(writer.toString());

			HttpHeaders header = new HttpHeaders();
		    header.setContentType(MediaType.APPLICATION_PDF);
		    header.set(HttpHeaders.CONTENT_DISPOSITION,
		                   "attachment; filename=" + "soumen");
		    header.setContentLength(baos.toByteArray().length);

		    return new HttpEntity<byte[]>(baos.toByteArray(), header);

		}


		public ByteArrayOutputStream generatePdf(String html) {

			String pdfFilePath = "";
			PdfWriter pdfWriter=null;

			// create a new document
			Document document = new Document();
			try {

				document = new Document();
				// document header attributes
				document.addAuthor("Kinns");
				document.addAuthor("Kinns123");
				document.addCreationDate();
				document.addProducer();
				document.addCreator("kinns123.github.io");
				document.addTitle("HTML to PDF using itext");
				document.setPageSize(PageSize.LETTER);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter.getInstance(document, baos);

				// open document
				document.open();

				XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
				xmlWorkerHelper.getDefaultCssResolver(true);
				StringReader stringReader = new StringReader(html);
				xmlWorkerHelper.parseXHtml(pdfWriter, document, stringReader);
				// close the document
				document.close();
				System.out.println("PDF generated successfully");

				return baos;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}


	
	


}
