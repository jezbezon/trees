package com.selflearn.tree;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearn.tree.exceptions.CustomizeException;
import com.selflearn.tree.impl.ProductServiceImpl;
import com.selflearn.tree.model.Category;
import com.selflearn.tree.model.Product;
import com.selflearn.tree.repository.CategoryRepository;
import com.selflearn.tree.repository.ProductRepository;
import com.selflearn.tree.repository.RoleRepository;
import com.selflearn.tree.repository.UserRepository;
import com.selflearn.tree.resposeClass.ResponseClass;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TreeApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	private ObjectMapper objectMapper;

	@Test
	@Order(1)
	void createProduct() throws Exception {

		String auth =  Base64.getEncoder().encodeToString("pich:Pich@123".getBytes());
		Category category = categoryRepository.save(Category.builder().id(1).name("Laptop").build());
		Product product = new Product(1L,"Huawei a", new BigDecimal("999.98"),10,"Huawei","New feature", category);
		ResponseClass responseClass = new ResponseClass("00","Create product success", product);
		objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(responseClass);
		mockMvc.perform(post("/api/product/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(product.productDTO(product)))
						.header("Authorization", "Basic " + auth))
				.andExpect(status().isOk())
				.andExpect(content().json(result));

		Product savedProduct = productRepository.findById(1L).orElse(null);
		assertNotNull(savedProduct);
		assertEquals("Huawei a", savedProduct.getName());
		assertEquals(new BigDecimal("999.98"), savedProduct.getPrice());

	}

	@Test
	@Order(2)
	void getByBrand() throws Exception {
		String auth =  Base64.getEncoder().encodeToString("pich:Pich@123".getBytes());
		List<Product> product = productRepository.findByBrand("Huawei");
		ResponseClass responseClass = new ResponseClass("00","Fetch product success", product);
		objectMapper = new ObjectMapper();
		mockMvc.perform(get("/api/product/brand")
						.param("brand","Huawei")
						.header("Authorization", "Basic "+auth))
				.andExpect(content().json(objectMapper.writeValueAsString(responseClass)));
	}

	@Test
	@Order(3)
	void getByNameWithBrand() throws Exception {

		String auth =  Base64.getEncoder().encodeToString("pich:Pich@123".getBytes());
		Category category = categoryRepository.findById(1).get();
		List<Product> product = List.of(new Product(1L,"Huawei a", new BigDecimal("999.98"),10,"Huawei","New feature", category));
		System.out.println(product.size());
		ResponseClass responseClass = new ResponseClass("00","Fetch product success", product);
		objectMapper = new ObjectMapper();
		mockMvc.perform(get("/api/product/NameWithBrand")
						.param("brand","u")
						.param("name","a")
						.header("Authorization", "Basic "+auth))
				.andExpect(content().json(objectMapper.writeValueAsString(responseClass)));

		assertEquals(1,product.size());
	}


	@Test
	@Order(4)
	void updateProduct() throws Exception {

		String auth =  Base64.getEncoder().encodeToString("pich:Pich@123".getBytes());
		Category category = categoryRepository.findById(1).orElseThrow(()-> new CustomizeException.NotFoundException("Id not found"));
		Product product = new Product(1L,"Huawei", new BigDecimal("999.98"),10,"Huawei","New feature", category);

		System.out.println(productRepository.findAll().size());

		Product updated = new Product(1L,"Huawei", new BigDecimal("999.98"), 10, "Huawei", "New feature with new camera", category);
		ResponseClass responseClass = new ResponseClass("00","update product success", updated);
		objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(responseClass);
		mockMvc.perform(put("/api/product/update/{id}",1)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(product.productDTO(updated)))
						.header("Authorization", "Basic " + auth))
				.andExpect(status().isOk())
				.andExpect(content().json(result));

		assertEquals("New feature with new camera", productRepository.findById(1L).get().getDescription());

	}

}
