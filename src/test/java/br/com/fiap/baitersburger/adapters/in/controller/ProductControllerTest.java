package br.com.fiap.baitersburger.adapters.in.controller;

import br.com.fiap.baitersburger.adapters.in.controller.ProductController;
import br.com.fiap.baitersburger.adapters.in.controller.dto.product.ProductRequestDTO;
import br.com.fiap.baitersburger.adapters.in.controller.dto.product.ProductResponseDTO;
import br.com.fiap.baitersburger.adapters.in.controller.mapper.ProductMapper;
import br.com.fiap.baitersburger.core.application.ports.in.product.*;
import br.com.fiap.baitersburger.core.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductMapper productMapper;
    @Mock
    private InsertProductInputPort insertProductInputPort;
    @Mock
    private FindProductByCategoryInputPort findProductByCategoryInputPort;
    @Mock
    private UpdateProductInputPort updateProductInputPort;
    @Mock
    private DeleteProductInputPort deleteProductInputPort;
    @Mock
    private FindProductByIdInputPort findProductByIdInputPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindProductsByCategory() {
        Product product = new Product();
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        when(findProductByCategoryInputPort.findByCategory("burger")).thenReturn(List.of(product));
        when(productMapper.toProductResponseDTO(product)).thenReturn(responseDTO);

        ResponseEntity<List<ProductResponseDTO>> response = productController.findProductsByCategory("burger");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testFindProductsById() {
        Product product = new Product();
        ProductResponseDTO responseDTO = new ProductResponseDTO();
        when(findProductByIdInputPort.findById("1")).thenReturn(product);
        when(productMapper.toProductResponseDTO(product)).thenReturn(responseDTO);

        ResponseEntity<ProductResponseDTO> response = productController.findProductsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testInsert() {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        Product product = new Product();
        when(productMapper.toProduct(requestDTO)).thenReturn(product);

        ResponseEntity<Void> response = productController.insert(requestDTO);

        verify(insertProductInputPort).insert(product);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdate() {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        Product product = new Product();
        when(productMapper.toProduct(requestDTO)).thenReturn(product);

        ResponseEntity<Void> response = productController.update("1", requestDTO);

        verify(updateProductInputPort).update(product);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDelete() {
        ResponseEntity<Void> response = productController.delete("1");

        verify(deleteProductInputPort).delete("1");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}