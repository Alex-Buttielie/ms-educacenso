package br.com.educacenso.app.constraints;

public enum CorRaca {
    NAO_DECLARADA("1"),
    BRANCA("2"),
    PRETA("3"),
    PARDA("4"),
    AMARELA("5"),
    INDIGENA("6");

    private String valor;

    CorRaca(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }

    public static String getValorStrPeloCodigo (String value) {
        for (CorRaca status : values())
            if (status.getValor().equals(value))
                return String.valueOf(status.ordinal());

        return null;
    }
}
