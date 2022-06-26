package net.openwebinars.springboot.multitenant.app.service;


import lombok.RequiredArgsConstructor;
import net.openwebinars.springboot.multitenant.app.model.Product;
import net.openwebinars.springboot.multitenant.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;


    public List<Product> findAll() {
        return repository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    public Product save(Product p) {
        return repository.save(p);
    }

    public void delete(Product p) {
        repository.delete(p);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }




}
