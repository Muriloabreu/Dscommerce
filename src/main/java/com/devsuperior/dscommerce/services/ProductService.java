package com.devsuperior.dscommerce.services;

import java.util.Optional;

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
	private ProductRepository productRepository;
	
	
	//Método para retornar um Product
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {		
		//Product product = productRepository.findById(id).get();
		//return new ProductDTO(product);		
		Optional<Product> result = productRepository.findById(id);		
		Product product = result.get();			
		ProductDTO dto = new ProductDTO(product);
		
		return dto;
		
	}
	
		//Método para retornar lista de Product
		@Transactional(readOnly = true)
		public Page<ProductDTO> findAll(Pageable pageable ) {
			
			Page<Product> result = productRepository.findAll(pageable);
			
			return result.map(x -> new ProductDTO(x));
			
		}
		
		@Transactional
		public ProductDTO insert(ProductDTO dto) {
			
			Product entity = new Product();
			//chamando Metdo auxiliar
			copyDtoToEntity(dto, entity);			
			entity = productRepository.save(entity);
			
			return new ProductDTO(entity);			
		}
		
		@Transactional
		public ProductDTO update(Long id, ProductDTO dto) {
			
			Product entity = productRepository.getReferenceById(id);
			//chamando Metdo auxiliar
			copyDtoToEntity(dto, entity);			
			entity = productRepository.save(entity);
			
			return new ProductDTO(entity);			
		}
		
		@Transactional
		public void delete(Long id) {		
			productRepository.deleteById(id);			
		}

		//Metodo auxiliar
		private void copyDtoToEntity(ProductDTO dto, Product entity) {
			
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setPrice(dto.getPrice());
			entity.setImgUrl(dto.getImgUrl());
			
			
		}

}
