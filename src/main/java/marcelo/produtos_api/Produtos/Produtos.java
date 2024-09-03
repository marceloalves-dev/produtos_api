package marcelo.produtos_api.Produtos;

import jakarta.persistence.*;

@Entity(name = "produto")
@Table(name = "produtos")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer preco;

    public Produtos(DTOProdutos dtoProdutos) {
        this.nome = dtoProdutos.nome();
        this.preco = dtoProdutos.preco();
    }

    public Produtos() {
    }

    public void atualizarDados(DTOProdutos dtoProdutos) {
        if (dtoProdutos.nome() != null && dtoProdutos.nome() != "") this.nome = dtoProdutos.nome();

        if (dtoProdutos.preco() != null) this.preco = dtoProdutos.preco();

    }


    //------------------------- gets -------------------------
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPreco() {
        return preco;
    }


}
