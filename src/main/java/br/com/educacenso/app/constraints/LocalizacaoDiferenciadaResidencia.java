package br.com.educacenso.app.constraints;

public enum LocalizacaoDiferenciadaResidencia {
    AREA_ASSENTAMENTO("1"),
    TERRA_INDIGENA("2"),
    AREA_REMANECENTE_QUILOMBO("3"),
    NAO_ESTA_EM_LOCALIZACAO_DIFERENCIADA("4");

    private String valor;

    LocalizacaoDiferenciadaResidencia(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static String getValorStrPeloCodigo (String value) {
        for (LocalizacaoDiferenciadaResidencia status : values())
            if (status.getValor().equals(value))
                return String.valueOf(status.ordinal());

        return null;
    }
}
