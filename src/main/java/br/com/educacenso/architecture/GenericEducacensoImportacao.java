package br.com.educacenso.architecture;

import br.com.educacenso.app.constraints.TipoRegistro;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static java.util.Optional.ofNullable;

public abstract class  GenericEducacensoImportacao {


    protected GenericEducacensoImportacao() {
    }

    public static Date stringToDate(String[] conteudoLinha, int posicaoConteudo) {
        try {
           return getValorDate(conteudoLinha, posicaoConteudo);
        }catch (ParseException | NullPointerException e) {
            return null;
        }
    }

    protected static Date getValorDate(String[] conteudoLinha, int posicaoConteudo) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
        return formatter.parse(valorString(conteudoLinha, posicaoConteudo).replace("/", "-"));
    }

    public static String valorString(String[] conteudoLinha, int posicaoConteudo) {
        try{
            return getValorString(conteudoLinha, posicaoConteudo);
        }catch (NullPointerException | ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

    protected static String getValorString(String[] conteudoLinha, int posicaoConteudo) {
        return !conteudoLinha[posicaoConteudo].isEmpty() ? conteudoLinha[posicaoConteudo] : null;
    }


    public static Boolean stringToBoolean(String[] conteudoLinha, int posicaoConteudo) {
        String valorConteudo = valorString(conteudoLinha, posicaoConteudo);
        String strBool = Objects.nonNull(valorConteudo) &&  valorConteudo.equals("1") ? "true" : "false";
        return java.lang.Boolean.parseBoolean(strBool);
    }

    public static Integer stringToInteger(String[] conteudoLinha, int posicaoConteudo) {
        try{
            return getValorInteger(conteudoLinha, posicaoConteudo);
        } catch (NumberFormatException e){
            return null;
        }

    }

    public static Integer getValorInteger(String[] conteudoLinha, int posicaoConteudo) throws NumberFormatException {
        return Integer.parseInt(valorString(conteudoLinha, posicaoConteudo));
    }

    public static Long stringToLong(String[] conteudoLinha, int posicaoConteudo) {
        try{
            return Long.parseLong(valorString(conteudoLinha, posicaoConteudo));
        }catch (NumberFormatException e){
            return null;
        }
    }

    protected static TipoRegistro getTipoRegistro(String conteudo) {
        return TipoRegistro.getTipoPorDescricao(conteudo);
    }

    protected static void atualizarPropriedadesObjeto(Object objetoAtualizado, Object objetoParaAtualizar) {
        BeanUtils.copyProperties(objetoAtualizado, objetoParaAtualizar);
    }

    protected static Object atualizarPropriedadesObjetoReturn(Object objeto, Object objetoParaAtualizar) {
        atualizarPropriedadesObjeto(objeto, objetoParaAtualizar);
        return objetoParaAtualizar;
    }

    protected static Object buscaRegistroConteudoLido(String conteudo, Object[] enumParaBusca) {
        try {
            return ofNullable(conteudo)
                    .filter(con -> isConteudoValido(con))
                    .map(cont -> enumParaBusca[Integer.parseInt(cont)])
                    .orElse(null);
        } catch (NumberFormatException |NullPointerException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private static Boolean isConteudoValido(String conteudo) {
        return !conteudo.isEmpty();
    }

}
