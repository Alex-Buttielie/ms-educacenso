package br.com.educacenso.app.constraints;

public enum TipoFiliacao {
    NAO_DECLARADO_IGNORADO("1"),
    FILIACAO_1_OU_2("2");

    private String valor;

    TipoFiliacao(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static String getValorStrPeloCodigo (String value) {
        for (TipoFiliacao status : values())
            if (status.getValor().equals(value))
                return String.valueOf(status.ordinal());

        return null;
    }

}
