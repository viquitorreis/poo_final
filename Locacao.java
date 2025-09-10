import java.io.Serializable;

class Locacao implements Serializable {
    private Cliente cliente;
    private Jogo jogo;
    private int dias;
    private double valorTotal;

    public Locacao(Cliente cliente, Jogo jogo, int dias) {
        this.cliente = cliente;
        this.jogo = jogo;
        this.dias = dias;
        this.valorTotal = jogo.getPreco() * dias;
        jogo.setDisponivel(false);
    }

    public double devolver() {
        jogo.setDisponivel(true);
        return valorTotal;
    }

    @Override
    public String toString() {
        return cliente.getNome() + " → " + jogo.getNome() + " (" + dias + " dias) - R$ " + valorTotal;
    }
}