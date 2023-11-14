package br.com.educacenso.app.constraints;

public enum LocalizacaoZonaResidencia {
    URBANA("1"),
    RURAL("2");

    private String valor;

    LocalizacaoZonaResidencia(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }

    public static String getValorStrPeloCodigo (String value) {
        for (LocalizacaoZonaResidencia status : values())
            if (status.getValor().equals(value))
                return String.valueOf(status.ordinal());

        return null;
    }

}
