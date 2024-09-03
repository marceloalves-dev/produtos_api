package marcelo.produtos_api.Produtos;

public record DTODataProdutos(
        Long id,
        String nome,
        Integer preco
) {
    public DTODataProdutos (Produtos produtos){
        this(produtos.getId(), produtos.getNome(), produtos.getPreco());
    }
}
