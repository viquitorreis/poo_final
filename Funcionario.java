class Funcionario extends Pessoa {
    private String cargo;

    public Funcionario(String nome, String telefone, String cargo) {
        super(nome, telefone);
        this.cargo = cargo;
    }

    public String getCargo() { return cargo; }

    @Override
    public String getTipo() {
        return "Funcionário";
    }

    @Override
    public String toString() {
        return super.toString() + " | Cargo: " + cargo;
    }
}