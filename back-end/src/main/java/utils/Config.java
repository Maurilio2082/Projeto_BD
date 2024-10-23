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
            1 - Relatório de Especialidades
            2 - Relatório de Hospitais
            3 - Relatório de Pacientes
            4 - Relatório de Medicos
            5 - Relatório de Historicos
            6 - Relatório de Medicos por Especialidades
            7 - Relatório de Medicos por Hospitais
            0 - Sair
            """;

    public static final String MENU_ENTIDADES = """
            Entidades
            1 - ESPECIALIDADE
            2 - HOSPITAL
            3 - PACIENTE
            4 - MEDICO
            5 - HISTORICO
            6 - MEDICOS X ESPECIALIDADE
            7 - MEDICOS X HOSPITAL
            """;

    public static void limparConsole(int tempoEspera) {
        try {
            Thread.sleep(tempoEspera * 1000);
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
