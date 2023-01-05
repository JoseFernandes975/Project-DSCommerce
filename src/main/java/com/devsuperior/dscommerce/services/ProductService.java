package com.devsuperior.dscommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
	 Product product = repository.findById(id).get();
	 ProductDTO dto = new ProductDTO(product);
	 return dto;
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable page){
		Page<Product> products = repository.findAll(page);
		return products.map(x -> new ProductDTO(x));
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEnt(entity, dto);
	    repository.save(entity);
	    return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		Product entity = repository.getReferenceById(id);
		copyDtoToEnt(entity, dto);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	private Product  copyDtoToEnt(Product product, ProductDTO dto) {
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setImgUrl(dto.getImgUrl());
		product.setPrice(dto.getPrice());
		return product;
	}
}
