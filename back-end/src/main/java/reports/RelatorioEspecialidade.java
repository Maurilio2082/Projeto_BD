package reports;

import java.util.List;

import controller.EspecialidadeController;
import model.Especialidade;

public class RelatorioEspecialidade {

    public static void gerarRelatorio() {
        EspecialidadeController controller = new EspecialidadeController();
        List<Especialidade> especialidades = controller.listarProdutos();

        System.out.println("Relatório de Produtos:");
        for (Especialidade especialidade : especialidades) {
            System.out.println("Código: " + especialidade.getIdEspecialidade() + ", Nome: "
                    + especialidade.getNomeEspecialidade());
        }
    }

}
