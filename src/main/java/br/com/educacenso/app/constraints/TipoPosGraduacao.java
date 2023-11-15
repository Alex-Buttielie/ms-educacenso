package br.com.educacenso.app.constraints;

public enum TipoPosGraduacao {
    ESPECIALIZACAO("1"),
    MESTRADO("2"),
    DOUTORADO("3");

    private String valor;

    TipoPosGraduacao(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static String getValorStrPeloCodigo (String valor) {
        for (TipoPosGraduacao status : values())
            if (status.getValor().equals(valor))
                return String.valueOf(status.ordinal());

        return null;
    }


}
