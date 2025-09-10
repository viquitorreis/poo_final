import java.io.Serializable;

class Jogo implements Serializable {
    private String nome;
    private String console;
    private double preco;
    private boolean disponivel;

    public Jogo(String nome, String console, double preco) {
        this.nome = nome;
        this.console = console;
        this.preco = preco;
        this.disponivel = true;
    }

    public String getNome() { return nome; }
    public String getConsole() { return console; }
    public double getPreco() { return preco; }
    public boolean isDisponivel() { return disponivel; }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return nome + " (" + console + ") - R$ " + preco + " - " +
                (disponivel ? "Disponível" : "Alugado");
    }
}





