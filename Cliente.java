class Cliente extends Pessoa {
    public Cliente(String nome, String telefone) {
        super(nome, telefone);
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }
}