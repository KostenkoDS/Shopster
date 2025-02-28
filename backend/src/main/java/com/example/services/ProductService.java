package com.example.services;

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

    public List<Product> findAllProducts(){
        return repository.findAll();
    }

    public List<Product> findProductsByCategoryId(Long id){
        return repository.findProductsByCategoryId(id);
    }

    public Product findProductById(Long id){
        return repository.findProductById(id).orElseThrow();
    }

    public List<Product> findInAllProductsWithQueries(List<String> categories, String minPrice, String maxPrice, String name){
        if(categories == null){
            try (Stream<Product> stream = repository.findAllProductsStream()){
                return findInStreamByPriceAndName(stream, minPrice, maxPrice, name);
            }
        }
        else {
            List<Long> categoryIds = categories.stream().map(Long::valueOf).distinct().toList();
            List<Product> products = findProductsByMultipleCategoryIds(categoryIds);
            return findInStreamByPriceAndName(products.stream(), minPrice, maxPrice, name);
        }
    }

    public List<Product> findProductsByMultipleCategoryIds(List<Long> ids){
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