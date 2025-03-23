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
import java.util.Set;
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

    public ProductDTO findProductById(Long id){
        Product p = repository.findProductById(id).orElseThrow();
        return new ProductDTO(p);
    }

    public Map<Long,String> findProductNamesFromListOfOrders(List<Order> orderList){
        Set<Long> productIdsInOrders = orderList
                .stream()
                .flatMap(order -> order.getOrderDetails().stream())
                .map(OrderDetails::getProductId)
                .collect(Collectors.toSet());
        List<Product> products = repository.findByMultipleProductIds(productIdsInOrders);
        return products.stream().collect(Collectors.toMap(Product::getId, Product::getName));
    }

    public List<ProductDTO> findProductsByIds(Set<Long> ids){
        return repository.findByMultipleProductIds(ids).stream().map(ProductDTO::new).toList();
    }


    public Map<Long,String> findProductNamesFromOrder(Order order){
        Set<Long> productIdsInOrder = order.getOrderDetails().stream().map(OrderDetails::getProductId).collect(Collectors.toSet());
        return findProductsByIds(productIdsInOrder).stream().collect(Collectors.toMap(ProductDTO::getId, ProductDTO::getName));
    }


    /*
    Methods in this section return only data about available products, that is where in-stock is above zero
    */

    public List<ProductDTO> findAvailableProducts(){
        try(Stream<Product> stream = repository.findAllAvailableProductsStream()){
            return stream.map(ProductDTO::new).toList();
        }
    }

    public List<ProductDTO> findAvailableProductsByCategoryId(Long id){
        return repository.findAvailableProductByCategoryId(id).stream().map(ProductDTO::new).toList();
    }

    public List<ProductDTO> findAvailableProductsByCategoryIds(Set<Long> ids){
        return repository.findAvailableProductsByMultipleCategoryIds(ids).map(ProductDTO::new).toList();
    }

    public List<ProductDTO> findAvailableProductsWithQueries(Set<Long> categories, Long minPrice, Long maxPrice, String name){
        if(categories == null){
            try (Stream<Product> stream = repository.findAllAvailableProductsStream()){
                return findInStreamByPriceAndName(stream, minPrice, maxPrice, name).stream().map(ProductDTO::new).toList();
            }
        }
        else {
            try (Stream<Product> stream = repository.findAvailableProductsByMultipleCategoryIds(categories)){
                return findInStreamByPriceAndName(stream, minPrice, maxPrice, name).stream().map(ProductDTO::new).toList();
            }
        }
    }


    /*
    This section is for the API for categories, methods here don't return product DTOs
    */

    public List<CategoryDTO> findAllCategories(){
        return repository.findAllCategories().stream().map(CategoryDTO::new).toList();
    }

    /*
    Below are private to this class helper methods
    */

    private List<Product> findInStreamByPriceAndName(Stream<Product> stream,
                                                     Long minPrice,
                                                     Long maxPrice,
                                                     String name){
        return stream.filter(
                p -> ((minPrice == null) || (p.getPrice().compareTo(new BigDecimal(minPrice)) >= 0)) &&
                     ((maxPrice == null) || (p.getPrice().compareTo(new BigDecimal(maxPrice)) <= 0)) &&
                     ((name == null) || p.getName().toLowerCase().contains(name.toLowerCase()))
        ).collect(Collectors.toList());
    }
}