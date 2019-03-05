package com.softvision.spring.dao;

import java.util.List;

import com.softvision.spring.model.ProductBo;

public interface ProductDAO {

	public List<ProductBo> getProduct();

	public List<ProductBo> getProductByCategory(String category);
}
