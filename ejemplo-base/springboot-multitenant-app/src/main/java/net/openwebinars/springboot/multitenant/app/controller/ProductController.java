package net.openwebinars.springboot.multitenant.app.controller;


import lombok.RequiredArgsConstructor;
import net.openwebinars.springboot.multitenant.app.model.Product;
import net.openwebinars.springboot.multitenant.app.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public List<Product> findAll() {

        List<Product> lista = productService.findAll();

        if (lista.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay productos en el catálogo");
        }

        return lista;

    }

    @GetMapping("/{id}")
    public Product byId(@PathVariable Long id) {

        return productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede encontrar el producto con id " + id));

    }

    @PostMapping("/")
    public ResponseEntity<Product> newProduct(@RequestBody Product p) {

        Product creado = productService.save(p);

        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(creado.getId())
                    .toUri();

        return ResponseEntity.created(uri).body(creado);

    }

    @PutMapping("/{id}")
    public Product edit(@PathVariable Long id, @RequestBody Product p) {

        return productService.findById(id)
                .map(producto -> {
                    producto.setName(p.getName());
                    producto.setPrice(p.getPrice());
                    return productService.save(producto);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede encontrar el producto con id " + id) );


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        productService.delete(id);

        return ResponseEntity.noContent().build();

    }




}
