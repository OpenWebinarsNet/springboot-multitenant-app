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

    private final ProductService productoServicio;

    @GetMapping("/")
    public List<Product> todos() {

        List<Product> lista = productoServicio.findAll();

        if (lista.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay productos en el catálogo");
        }

        return lista;

    }

    @GetMapping("/{id}")
    public Product porId(@PathVariable Long id) {

        return productoServicio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede encontrar el producto con id " + id));

    }

    @PostMapping("/")
    public ResponseEntity<Product> nuevo(@RequestBody Product p) {

        Product creado = productoServicio.save(p);

        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(creado.getId())
                    .toUri();

        return ResponseEntity.created(uri).body(creado);

    }

    @PutMapping("/{id}")
    public Product editar(@PathVariable Long id, @RequestBody Product p) {

        return productoServicio.findById(id)
                .map(producto -> {
                    producto.setName(p.getName());
                    producto.setPrice(p.getPrice());
                    return productoServicio.save(producto);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se puede encontrar el producto con id " + id) );


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        productoServicio.delete(id);

        return ResponseEntity.noContent().build();

    }




}
