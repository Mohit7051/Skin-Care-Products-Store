package com.itvedant.skincareproducts_store.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itvedant.skincareproducts_store.repository.ProductRepository;
import com.itvedant.skincareproducts_store.dao.AddProductDao;
import com.itvedant.skincareproducts_store.dao.UpdateProductDao;
import com.itvedant.skincareproducts_store.entity.Product;
import com.itvedant.skincareproducts_store.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private com.itvedant.skincareproducts_store.repository.ProductRepository productRepository;
	
	@PostMapping("/{id}/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile skincareproducts_store, @PathVariable Integer id){
		return ResponseEntity.ok(this.productService.storeFile(id, skincareproducts_store));
	}
	
	
//	@GetMapping("/download/{filename}")
	public ResponseEntity<?> download(@PathVariable String filename){
		Resource resource = this.productService.loadGameAsResource(filename);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(resource);
	}
	
	@GetMapping("/savestudent")
	public String create(Model model) {
		model.addAttribute("product", new Product());
		return "addproduct";
	}
	
	@PostMapping("/savestudent")
	public String saveProduct(@ModelAttribute("product") AddProductDao addProductDao, Model model) {
		model.addAttribute("product", addProductDao);
		this.productService.createProduct(addProductDao);
//		return "product saved";
		return "index";
	}
	
	@GetMapping("/show")
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView("showproducts");
		mav.addObject("products", this.productRepository.findAll());
		return mav;
	}
	
	@GetMapping("/{id}")
	public String delete(@PathVariable("id") Integer id) {
		this.productService.delteProduct(id);
		return "showproducts";
	}
	
	@GetMapping("/editproduct/{id}")
	public String edit(Model model,@PathVariable("id") Integer id) {
		model.addAttribute("product", this.productRepository.findById(id));
		return "editproduct";
	}
	
	@PostMapping("/editproduct/{id}")
	public String saveProduct(@ModelAttribute("product") UpdateProductDao updateProductDao, Model model,
			@PathVariable("id") Integer id) {
		this.productService.updateProduct(updateProductDao, id);
		Product product = this.productRepository.findById(id).orElse(null);
		model.addAttribute("product", product);
//		this.productService.updateProduct(updateProductDao, id);
        return "index";
	}
}








