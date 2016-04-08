package com.magm.web.services.compras;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.magm.compras.model.Product;
import com.magm.web.services.Constants;
import com.magm.web.services.TestUtil;
import com.magm.web.spring.config.RootConfig;
import com.magm.web.spring.config.ServletInitializer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class, ServletInitializer.class })
@WebAppConfiguration
public class ProductsRSControllerTest {

	@Autowired
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void listProducts() throws Exception {
		mockMvc.perform(get(Constants.URL_PRODUCTS + "/list").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@Test
	public void loadProductNotFound() throws Exception {
		mockMvc.perform(get(Constants.URL_PRODUCTS + "/-999").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@Test
	public void deleteProductNotFound() throws Exception {
		mockMvc.perform(delete(Constants.URL_PRODUCTS + "/-999").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@Test
	public void updateProductNotFound() throws Exception {
		Product p = new Product();
		p.setId(-999);
		mockMvc.perform(put(Constants.URL_PRODUCTS + "/").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(p)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@Test
	public void performCicle() throws Exception {
		Product p = new Product();
		p.setDescription("Producto Test");
		List<String> tags = new ArrayList<String>();
		tags.add("test 1");
		tags.add("test 2");
		tags.add("test 3");
		p.setTags(tags);

		// Add
		MvcResult mvcResult = mockMvc
				.perform(post(Constants.URL_PRODUCTS + "/").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(p)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.description", is(p.getDescription())))
				.andExpect(jsonPath("$.tags", Matchers.containsInAnyOrder(tags.get(0), tags.get(1), tags.get(2))))
				.andExpect(jsonPath("$.tags", hasSize(tags.size()))).andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		int id = TestUtil.getIntValueFromJson(content, "id");

		// Update
		p.setId(id);
		p.setDescription("Producto Test updated");
		p.getTags().add("test 4");
		p.getTags().remove(0);
		p.getTags().remove(0);
		mockMvc.perform(put(Constants.URL_PRODUCTS + "/").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(p)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.description", is(p.getDescription())))
				.andExpect(jsonPath("$.tags", Matchers.containsInAnyOrder(tags.get(0), tags.get(1))))
				.andExpect(jsonPath("$.tags", hasSize(tags.size())));

		// Load
		mockMvc.perform(get(Constants.URL_PRODUCTS + "/" + id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));

		// Delete
		mockMvc.perform(delete(Constants.URL_PRODUCTS + "/" + id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// Load Not Found
		mockMvc.perform(get(Constants.URL_PRODUCTS + "/" + id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(content().contentType("application/json;charset=UTF-8"));
	}

	@Configuration
	@EnableWebMvc
	public static class TestConfiguration {

		@Bean
		public ProductsRSController productsRSController() {
			return new ProductsRSController();
		}

	}
}
