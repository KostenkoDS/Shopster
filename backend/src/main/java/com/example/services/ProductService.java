package com.example.services;

import com.example.dto.CategoryDTO;
import com.example.dto.ProductDTO;
import com.example.entities.Order;
import com.example.entities.OrderDetails;
import com.example.entities.Product;
import com.example.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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

    public List<ProductDTO> findProductsByIds(List<Long> ids){
        return repository.findByMultipleProductIds(ids).stream().map(ProductDTO::new).toList();
    }

    public Map<Long,String> findProductNamesFromOrder(Order order){
        List<Long> productIdsInOrder = order.getOrderDetails().stream().map(OrderDetails::getProductId).toList();
        return findProductsByIds(productIdsInOrder).stream().collect(Collectors.toMap(ProductDTO::getId, ProductDTO::getName));
    }

    public Map<Long,String> findProductNamesFromListOfOrders(List<Order> orderList){
        List<Long> productIdsInOrders = orderList
                .stream()
                .flatMap(order -> order.getOrderDetails().stream())
                .map(OrderDetails::getProductId)
                .toList();
        List<Product> products = repository.findByMultipleProductIds(productIdsInOrders);
        return products.stream().collect(Collectors.toMap(Product::getId, Product::getName));
    }

    public List<ProductDTO> findInAllProductsWithQueries(List<String> categories, String minPrice, String maxPrice, String name){
        if(categories == null){
            try (Stream<Product> stream = repository.findAllAvailableProductsStream()){
                return findInStreamByPriceAndName(stream, minPrice, maxPrice, name).stream().map(ProductDTO::new).toList();
            }
        }
        else {
            List<Long> categoryIds = categories.stream().map(Long::valueOf).distinct().toList();
            try (Stream<Product> stream = repository.findByMultipleCategoryIds(categoryIds)){
                return findInStreamByPriceAndName(stream, minPrice, maxPrice, name).stream().map(ProductDTO::new).toList();
            }
        }
    }

    public List<CategoryDTO> findAllCategories(){
        return repository.findAllCategories().stream().map(CategoryDTO::new).toList();
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