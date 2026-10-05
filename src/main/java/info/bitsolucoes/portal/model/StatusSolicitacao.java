package info.bitsolucoes.portal.model;

public enum StatusSolicitacao {
    ABERTO,
    EM_ATENDIMENTO,
    CONCLUIDO;

    public boolean podeTransicionarPara(StatusSolicitacao novoStatus) {
        if (this == ABERTO) {
            return novoStatus == EM_ATENDIMENTO || novoStatus == CONCLUIDO;
        }
        if (this == EM_ATENDIMENTO) {
            return novoStatus == CONCLUIDO;
        }
        return false;
    }
}