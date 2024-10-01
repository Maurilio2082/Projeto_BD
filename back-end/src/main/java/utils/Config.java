package utils;

public class Config {
    public static final String MENU_PRINCIPAL = """
            Menu Principal
            1 - Relatórios
            2 - Inserir Registros
            3 - Atualizar Registros
            4 - Remover Registros
            5 - Sair
            """;

    public static final String MENU_RELATORIOS = """
            Relatórios
            1 - Relatório de Agendamento por Prestador
            2 - Relatório de Agendamento por Doador
            3 - Relatório de Especialidades
            4 - Relatório de Prestador
            5 - Relatório de Agendamentos
            6 - Relatório de Doador
            7 - Relatório de Especialidades de Prestador
            8 - Relatório de Banco de Sangue por Prestador
            0 - Sair
            """;

    public static final String MENU_ENTIDADES = """
            Entidades
            1 - ESPECIALIDADE
            2 - PRESTADOR
            3 - BANCO DE SANGUE
            4 - AGENDAMENTOS
            5 - ESPECIALIDADE DO PRESTADOR
            6 - DOADOR
            """;

    public static void limparConsole(int tempoEspera) {
        try {
            // Aguarda por um tempo especificado em segundos
            Thread.sleep(tempoEspera * 1000);

            // Limpa o console (comando depende do sistema operacional)
            if (System.getProperty("os.name").contains("Windows")) {
                // Comando para limpar o console no Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Comando ANSI para limpar o console em outros sistemas (Linux, Mac, etc.)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Exibe o erro caso ocorra
        }
    }

}
