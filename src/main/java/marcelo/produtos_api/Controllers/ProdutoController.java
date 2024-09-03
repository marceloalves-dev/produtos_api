package marcelo.produtos_api.Controllers;

import jakarta.transaction.Transactional;
import marcelo.produtos_api.Produtos.DTODataProdutos;
import marcelo.produtos_api.Produtos.DTOProdutos;
import marcelo.produtos_api.Produtos.ProdutoRepository;
import marcelo.produtos_api.Produtos.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<Produtos> adicionarProdutos(@RequestBody DTOProdutos dtoProdutos, UriComponentsBuilder uriBuild) {
        var produtos = new Produtos(dtoProdutos);
        repository.save(produtos);

        var uri = uriBuild.path("/produtos/{id}").buildAndExpand(produtos.getId()).toUri();
        return ResponseEntity.created(uri).body(produtos);
    }

    @GetMapping
    public ResponseEntity<List<DTODataProdutos>> listarProdutos() {
        var list = repository.findAll().stream().map(DTODataProdutos::new).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DTODataProdutos> atualizarProdutos(@PathVariable long id, @RequestBody DTOProdutos dtoProdutos) {
        var produtos = repository.getReferenceById(id);
        produtos.atualizarDados(dtoProdutos);

        return ResponseEntity.ok(new DTODataProdutos(produtos));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();

    }
}
