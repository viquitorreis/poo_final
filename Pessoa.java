import java.io.Serializable;

abstract class Pessoa implements Serializable {
    private String nome;
    private String telefone;

    public Pessoa(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }

    public abstract String getTipo();

    @Override
    public String toString() {
        return getTipo() + ": " + nome + " | Tel: " + telefone;
    }
}