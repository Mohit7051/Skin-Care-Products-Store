package com.itvedant.skincareproducts_store.service;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itvedant.skincareproducts_store.SkinCareProductsStoreStorageProperties;
import com.itvedant.skincareproducts_store.dao.AddProductDao;
import com.itvedant.skincareproducts_store.dao.UpdateProductDao;
import com.itvedant.skincareproducts_store.entity.Product;
import com.itvedant.skincareproducts_store.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	private final Path rootLocation;
	
	public ProductService(SkinCareProductsStoreStorageProperties properties) {
		this.rootLocation = Paths.get(properties.getUploadDir());
		
		try {
			Files.createDirectories(rootLocation);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	// For Uploading game/image on database    
	
	public String storeFile(Integer id, MultipartFile file) {
		try {
			
			if(file.isEmpty()) {
				System.out.println("Empty file");
			}
			
			Path destinationFile = this.rootLocation.resolve(Paths.get(file.getOriginalFilename()));
			
			try (InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
				
				Product product = this.productRepository.findById(id).orElseThrow();
				
				String fileuploadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
						              .path("/products/download/")
						              .path(file.getOriginalFilename())
						              .toUriString();
				
				product.setImageUrl(fileuploadUri);
				
				this.productRepository.save(product);
				
				return "file Uploaded Successfully";
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return "Error";
	}
	
	
	
	// when we upload game/file automatically It will be Downloaded
	
	
	
	public Resource loadGameAsResource(String filename) {
	    Path file = rootLocation.resolve(filename);
	    
	    try {
			Resource resource = new UrlResource(file.toUri());
			
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}else {
				System.out.println("File Not Found");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	    return null;
	}
	
	
	
	public String createProduct(AddProductDao addProductDao) {
		Product product = new Product();
		
		product.setProductName(addProductDao.getProductName());
		product.setDescription(addProductDao.getDescription());
		product.setManufacturer(addProductDao.getManufacturer());
		product.setPrice(addProductDao.getPrice());
		product.setImageUrl(addProductDao.getImageUrl());
		
		this.productRepository.save(product);
		
		return "Product Saved";
	}
	
	public void delteProduct(Integer id) {    // for delete
		this.productRepository.deleteById(id);
	}
	
	public void updateProduct(UpdateProductDao updateProductDao, Integer id) {   // for update
		Product product = this.productRepository.findById(id).orElse(null);
		
		if(updateProductDao.getProductName() != null) {
			product.setProductName(updateProductDao.getProductName());
		}
		
		if(updateProductDao.getManufacturer() != null) {
			product.setManufacturer(updateProductDao.getManufacturer());
		}
		
		if(updateProductDao.getPrice() != null) {
			product.setPrice(updateProductDao.getPrice());
		}
		
		if(updateProductDao.getDescription() != null) {
			product.setDescription(updateProductDao.getDescription());
		}
		
		this.productRepository.save(product);
	}


}


