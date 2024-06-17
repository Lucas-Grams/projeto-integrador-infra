package br.com.pdsars.notificationapi.api.sender;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class NotificationMessage {
    private UUID requestId;
    private String titulo;
    private String corpo;
    private Tipo tipo;
    private Prioridade prioridade;
    private Integer idGranja;

    public NotificationMessage(String titulo, String corpo, Tipo tipo, Prioridade prioridade, Integer idGranja) {
        this.titulo = titulo;
        this.corpo = corpo;
        this.prioridade = prioridade;
        this.tipo = tipo;
        this.idGranja = idGranja;
    }

    public NotificationMessage(String titulo, String corpo, Tipo tipo, Prioridade prioridade) {
        this.titulo = titulo;
        this.corpo = corpo;
        this.prioridade = prioridade;
        this.tipo = tipo;
    }

    public NotificationMessage(String titulo, String corpo, Tipo tipo) {
        this(titulo, corpo, tipo, tipo.prioridadePadrao);
    }

    public UUID getRequestId() {
        return requestId;
    }

    protected void setRequestId(UUID id) {
        this.requestId = id;
    }

    public enum Tipo {
        COLHEITA_EXTRAORDINARIA(Prioridade.NORMAL, "Colheitas extraordinárias", new String[]{"PESA", "ADMIN"}, "mapa-representante", "colaborador-mapa", "rt", "representante", "mvo", "mvo-representante"),
        NOTIFICACAO_MAPA(Prioridade.NORMAL, "Notificações Mapa"), NOTIFICACAO_PESA(Prioridade.NORMAL, "Notificações PESA"), LAUDO_NEGATIVO(Prioridade.NORMAL, "Laudo disponível", "rt", "representante"), GRANJAS_ADM_ATUALIZADAS(Prioridade.NORMAL,
            "RT atualizar granjas disponíveis",
            "administrativo-empresa"), LAUDO_POSITIVO(Prioridade.NORMAL,
            "Laudo com resultado positivo",
            new String[]{"PESA", "ADMIN"},
            "mvo-representante", "mvo", "mapa-representante",
            "colaborador-mapa", "rt", "representante",
            "administrativo-empresa", "admin"),
        CERTIFICACAO_SUSPENSA(Prioridade.NORMAL, "Certificado Suspenso", new String[]{"PESA", "ADMIN"}, "mapa-representante", "colaborador-mapa", "rt", "representante", "mvo", "mvo-representante"),
        CERTIFICACAO_INDEFERIDA(Prioridade.NORMAL, "Certificação Indeferida", new String[]{"PESA", "ADMIN"}, "mapa-representante", "colaborador-mapa", "rt", "representante", "mvo", "mvo-representante"),
        SOLICITACAO_CERTIFICACAO(Prioridade.NORMAL, "Solicitação de Certificação", "mapa-representante", "colaborador-mapa", "mvo-representante", "mvo"), LAB_CORRIGIR_LAUDO(Prioridade.HIGH, "Laboratório corrigindo laudo", "rt", "representante"),
        CERTIFICADO_A_VENCER(Prioridade.NORMAL, "Certificado com Vencimento Próximo", "rt", "representante", "administrativo-empresa"),
        CERTIFICADO_VENCIDO_SEM_ACAO(Prioridade.NORMAL, "Certificado Vencido sem nenhuma ação realizada", "mapa-representante", "admin"),
        CERTIFICACAO_EMITIDA(Prioridade.NORMAL, "Certificado Emitido", new String[]{"PESA", "ADMIN"}, "mapa-representante", "colaborador-mapa", "rt", "representante", "mvo", "mvo-representante"),
        AVEFEM(Prioridade.NORMAL, "Lembrete AVE-FEM", "rt", "representante"),
        DEVS(Prioridade.NORMAL, "Aviso de problemas para os Desenvolvedores", "admin"),
        ESTOQUE_PENDENTE(Prioridade.NORMAL, "Estoque Pendente", new String[]{"PESA", "ESTOQUE"}, "mvo-representante"),
        VINCULADO_UNIDADE(Prioridade.NORMAL, "Vínculo a uma nova unidade"),
        VINCULADO_ESTABELECIMENTO(Prioridade.NORMAL, "Vínculo a um novo estabelecimento"),
        ESTABELECIMENTO_CADASTRADO(Prioridade.NORMAL, "Novo estabelecimento cadastrado"),
        INTERDICAO(Prioridade.NORMAL, "Interdição Realizada", new String[]{"DOENCAS_DE_CONTROLE"},
            "representante", "rt"),
        DESINTERDICAO(Prioridade.NORMAL, "Desinterdição Realizada", new String[]{"DOENCAS_DE_CONTROLE"},
            "representante", "rt"),
        DESINTERDICAO_SOLICITADA(Prioridade.NORMAL, "Desinterdição Solicitada", new String[]{"DOENCAS_DE_CONTROLE"},
            "representante", "rt", "mvo-representante"),
        ;

        public final String[] permissoes;
        public final String[] modulos;
        public final String descricao;
        public final Prioridade prioridadePadrao;

        Tipo(Prioridade prioridadePadrao, String descricao, String[] modulos, String... permissoes) {
            this.prioridadePadrao = prioridadePadrao;
            this.descricao = descricao;
            this.permissoes = permissoes;
            this.modulos = modulos;
        }

        Tipo(Prioridade prioridadePadrao, String descricao, String... permissoes) {
            this(prioridadePadrao, descricao, new String[]{}, permissoes);
        }
    }

    public enum Prioridade {
        LOW, NORMAL, HIGH
    }
}
