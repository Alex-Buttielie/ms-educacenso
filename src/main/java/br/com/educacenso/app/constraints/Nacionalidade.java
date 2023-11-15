package br.com.educacenso.app.constraints;

public enum Nacionalidade {
    BRASILEIRA("1"),
    BRASILEIRA_NASCIDO_EXTERIOR_OU_NATURALIZADO("2"),
    EXTRANGEIIRO("3");


    private String valorStr;

    Nacionalidade(String valorStr) {
        this.valorStr = valorStr;
    }

    public static String getValorStrPeloCodigo (String valor) {
        for (Nacionalidade status : values())
            if (status.getValorStr().equals(valor))
                return String.valueOf(status.ordinal());

        return null;
    }


    public String getValorStr() {
        return valorStr;
    }
}
