package com.ecommerce.microservices.controller;

import com.ecommerce.microservices.dao.ProductDAO;
import com.ecommerce.microservices.exceptions.ProduitIntrouvableException;
import com.ecommerce.microservices.model.Product;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/library/rest/product/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    @Autowired
    private ProductDAO dao;

    /*@GetMapping("/Produits")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = dao.findAll();
        if(!CollectionUtils.isEmpty(products)) {
            ResponseEntity responseEntity =new ResponseEntity <List<Product>>(products, HttpStatus.OK);
            return responseEntity;
        }
        return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
    }*/

   @RequestMapping(value="/Produits", method= RequestMethod.GET)
    public List<Product> listeProduits() {
        return dao.findAll();
    }

    @RequestMapping(value="/Produits/{id}", method = RequestMethod.GET)
    public Product afficherUnProduit(@PathVariable int id){
        Product product = dao.findById(id);

        if(product==null)
            throw new ProduitIntrouvableException("Le produit avec est introuvable!");
        return product;
    }

    @CrossOrigin(origins = {"http://localhost:4200"})
    @PostMapping(value = "/AddProduits")
    public ResponseEntity<Void> ajouterUnProduit(@Valid @RequestBody Product product){
        Product productAdd = dao.save(product);

        if(productAdd == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdd.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }



   @GetMapping(value="test/produits/{recherche}")
    public List<Product> testDeRequetes(@PathVariable String recherche){
        return dao.findByNomLike("%"+recherche+"%");
    }

    @DeleteMapping(value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id){
       dao.deleteById(id);
    }

    @PutMapping(value = "/Produits")
    public void updateProduit(@RequestBody Product product){
       dao.save(product);
    }

    //essai denis git 02.09.2021 19h43 branche sprint 1 ha ha

}
