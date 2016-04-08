package com.magm.compras.model;

import org.junit.Test;
import static org.junit.Assert.*;
public class ProductTest {

	@Test
	public void indentity() {
		Product p1=new Product();
		p1.setId(1);
		p1.setDescription("Product X");
		
		Product p2=new Product();
		p2.setId(2);
		p2.setDescription("Product X");
		
		assertNotEquals("Product.equal() fail!",  p1, p2);
		p2.setId(1);
		assertEquals("Product.equal() fail!",  p1, p2);
	}
}
