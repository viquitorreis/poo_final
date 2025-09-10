import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SistemaLocadora extends JFrame {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Jogo> jogos = new ArrayList<>();
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();
    private ArrayList<Locacao> locacoes = new ArrayList<>();
    private JTextArea areaTexto;

    public SistemaLocadora() {
        setTitle("Locadora 2000");
        setSize(700, 300); // Aumentei o tamanho para caber mais botões
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // JPAnel cria um layout de borda
        JPanel painel = new JPanel(new BorderLayout());
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        // JScrollPane para rolagem
        painel.add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        // Aumentei para 3x3 para caber mais botões
        JPanel painelBotoes = new JPanel(new GridLayout(3, 3));

        // Adicionei "Add Funcionário"
        String[] botoes = {
                "Add Cliente", "Add Jogo", "Add Funcionário",
                "Alugar", "Listar", "Devolver",
                "Sair", "", "" // Espaços vazios para completar a grade
        };

        for (String texto : botoes) {
            if (!texto.isEmpty()) { // Só cria botão se não for vazio
                JButton botao = new JButton(texto);
                botao.addActionListener(e -> executarAcao(texto));
                painelBotoes.add(botao);
            } else {
                painelBotoes.add(new JLabel("")); // Espaço vazio
            }
        }

        painel.add(painelBotoes, BorderLayout.SOUTH);
        add(painel);
        atualizarAreaTexto();
    }

    private void executarAcao(String acao) {
        switch (acao) {
            case "Add Cliente": adicionarCliente(); break;
            case "Add Jogo": adicionarJogo(); break;
            case "Add Funcionário": adicionarFuncionario(); break; // NOVO
            case "Alugar": fazerLocacao(); break;
            case "Listar": atualizarAreaTexto(); break;
            case "Devolver": devolverJogo(); break;
            case "Sair": System.exit(0); break;
        }
    }

    private void adicionarFuncionario() {
        String nome = JOptionPane.showInputDialog("Nome do funcionário:");
        if (nome == null || nome.trim().isEmpty()) return;

        String telefone = JOptionPane.showInputDialog("Telefone (11 números):");
        if (telefone == null) return;

        telefone = telefone.replaceAll("[^0-9]", "");
        if (telefone.length() != 11) {
            JOptionPane.showMessageDialog(this, "Telefone deve ter 11 números!");
            return;
        }

        String cargo = JOptionPane.showInputDialog("Cargo do funcionário:");
        if (cargo == null || cargo.trim().isEmpty()) return;

        funcionarios.add(new Funcionario(nome.trim(), telefone, cargo.trim()));
        JOptionPane.showMessageDialog(this, "Funcionário adicionado!");
        atualizarAreaTexto();
    }

    private void adicionarCliente() {
        String nome = JOptionPane.showInputDialog("Nome do cliente:");
        if (nome == null || nome.trim().isEmpty()) return;

        String telefone = JOptionPane.showInputDialog("Telefone (11 números):");
        if (telefone == null) return;

        telefone = telefone.replaceAll("[^0-9]", "");
        if (telefone.length() != 11) {
            JOptionPane.showMessageDialog(this, "Telefone deve ter 11 números!");
            return;
        }

        clientes.add(new Cliente(nome.trim(), telefone));
        JOptionPane.showMessageDialog(this, "Cliente adicionado!");
        atualizarAreaTexto();
    }

    private void adicionarJogo() {
        String nome = JOptionPane.showInputDialog("Nome do jogo:");
        if (nome == null || nome.trim().isEmpty()) return;

        String console = JOptionPane.showInputDialog("Console:");
        if (console == null || console.trim().isEmpty()) return;

        String precoStr = JOptionPane.showInputDialog("Preço por dia:");
        if (precoStr == null) return;

        try {
            double preco = Double.parseDouble(precoStr);
            if (preco <= 0) {
                JOptionPane.showMessageDialog(this, "Preço deve ser maior que zero!");
                return;
            }

            jogos.add(new Jogo(nome.trim(), console.trim(), preco));
            JOptionPane.showMessageDialog(this, "Jogo adicionado!");
            atualizarAreaTexto();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido!");
        }
    }

    private void fazerLocacao() {
        if (clientes.isEmpty() || jogos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre clientes e jogos primeiro!");
            return;
        }

        String[] nomesClientes = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            nomesClientes[i] = clientes.get(i).getNome();
        }

        String clienteNome = (String) JOptionPane.showInputDialog(
                this, "Escolha o cliente:", "Locação",
                JOptionPane.QUESTION_MESSAGE, null, nomesClientes, nomesClientes[0]);

        if (clienteNome == null) return;

        ArrayList<Jogo> jogosDisponiveis = new ArrayList<>();
        for (Jogo j : jogos) {
            if (j.isDisponivel()) jogosDisponiveis.add(j);
        }

        if (jogosDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum jogo disponível!");
            return;
        }

        String[] nomesJogos = new String[jogosDisponiveis.size()];
        for (int i = 0; i < jogosDisponiveis.size(); i++) {
            nomesJogos[i] = jogosDisponiveis.get(i).getNome();
        }

        String jogoNome = (String) JOptionPane.showInputDialog(
                this, "Escolha o jogo:", "Locação",
                JOptionPane.QUESTION_MESSAGE, null, nomesJogos, nomesJogos[0]);

        if (jogoNome == null) return;

        String diasStr = JOptionPane.showInputDialog("Quantos dias?");
        if (diasStr == null) return;

        try {
            int dias = Integer.parseInt(diasStr);
            if (dias <= 0) {
                JOptionPane.showMessageDialog(this, "Dias devem ser maior que zero!");
                return;
            }

            Cliente cliente = null;
            Jogo jogo = null;

            for (Cliente c : clientes) {
                if (c.getNome().equals(clienteNome)) {
                    cliente = c;
                    break;
                }
            }

            for (Jogo j : jogosDisponiveis) {
                if (j.getNome().equals(jogoNome)) {
                    jogo = j;
                    break;
                }
            }

            if (cliente != null && jogo != null) {
                Locacao locacao = new Locacao(cliente, jogo, dias);
                locacoes.add(locacao);
                JOptionPane.showMessageDialog(this, "Locação realizada!\n" + locacao);
                atualizarAreaTexto();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de dias inválido!");
        }
    }

    private void devolverJogo() {
        if (locacoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma locação ativa!");
            return;
        }

        String[] locacoesAtivas = new String[locacoes.size()];
        for (int i = 0; i < locacoes.size(); i++) {
            locacoesAtivas[i] = locacoes.get(i).toString();
        }

        String escolha = (String) JOptionPane.showInputDialog(
                this, "Escolha a locação para devolver:", "Devolução",
                JOptionPane.QUESTION_MESSAGE, null, locacoesAtivas, locacoesAtivas[0]);

        if (escolha != null) {
            double valor = locacoes.remove(0).devolver();
            JOptionPane.showMessageDialog(this, "Devolução realizada!\nValor: R$ " + valor);
            atualizarAreaTexto();
        }
    }

    private void atualizarAreaTexto() {
        StringBuilder sb = new StringBuilder();

        sb.append("CLIENTES (").append(clientes.size()).append("):\n");
        for (Cliente c : clientes) sb.append("  ").append(c).append("\n");

        sb.append("\nJOGOS (").append(jogos.size()).append("):\n");
        for (Jogo j : jogos) sb.append("  ").append(j).append("\n");

        sb.append("\nLOCAÇÕES ATIVAS (").append(locacoes.size()).append("):\n");
        for (Locacao l : locacoes) sb.append("  ").append(l).append("\n");

        sb.append("\nFUNCIONÁRIOS (").append(funcionarios.size()).append("):\n");
        for (Funcionario f : funcionarios) sb.append("  ").append(f).append("\n");

        areaTexto.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemaLocadora().setVisible(true);
        });
    }
}