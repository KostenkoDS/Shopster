package com.example.services;

import com.example.dto.ProductDTO;
import com.example.entities.Product;
import com.example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductDTO> findAllProducts(){
        return repository.findAll().stream().map(ProductDTO::new).toList();
    }

    public List<ProductDTO> findAvailableProducts(){
        try(Stream<Product> stream = repository.findAllAvailableProductsStream()){
            return stream.map(ProductDTO::new).toList();
        }
    }

    public List<ProductDTO> findProductsByCategoryId(Long id){
        return repository.findProductsByCategoryId(id).stream().map(ProductDTO::new).toList();
    }

    public ProductDTO findProductById(Long id){
        Product p = repository.findProductById(id).orElseThrow();
        return new ProductDTO(p);
    }

    public List<ProductDTO> findInAllProductsWithQueries(List<String> categories, String minPrice, String maxPrice, String name){
        if(categories == null){
            try (Stream<Product> stream = repository.findAllAvailableProductsStream()){
                return findInStreamByPriceAndName(stream, minPrice, maxPrice, name).stream().map(ProductDTO::new).toList();
            }
        }
        else {
            List<Long> categoryIds = categories.stream().map(Long::valueOf).distinct().toList();
            try (Stream<Product> stream = findProductsByMultipleCategoryIds(categoryIds)){
                return findInStreamByPriceAndName(stream, minPrice, maxPrice, name).stream().map(ProductDTO::new).toList();
            }
        }
    }

    public Stream<Product> findProductsByMultipleCategoryIds(List<Long> ids){
        return repository.findByMultipleIds(ids);
    }

    private List<Product> findInStreamByPriceAndName(Stream<Product> stream,
                                                     String minPrice,
                                                     String maxPrice,
                                                     String name){
        return stream.filter(
                p -> ((minPrice == null) || (p.getPrice().compareTo(new BigDecimal(minPrice)) >= 0)) &&
                     ((maxPrice == null) || (p.getPrice().compareTo(new BigDecimal(maxPrice)) <= 0)) &&
                     ((name == null) || p.getName().toLowerCase().contains(name.toLowerCase()))
        ).collect(Collectors.toList());
    }
}