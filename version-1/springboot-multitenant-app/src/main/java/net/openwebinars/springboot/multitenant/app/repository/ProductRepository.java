package net.openwebinars.springboot.multitenant.app.repository;

import net.openwebinars.springboot.multitenant.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
