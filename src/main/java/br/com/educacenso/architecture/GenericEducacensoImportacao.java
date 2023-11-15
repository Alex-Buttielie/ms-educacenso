package br.com.educacenso.architecture;

import br.com.educacenso.contraints.TipoRegistro;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.util.Optional.ofNullable;

public abstract class  GenericEducacensoImportacao {


    protected GenericEducacensoImportacao() {
    }

    public static Date stringToDate(String[] conteudoLinha, int posicaoConteudo) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
            return formatter.parse(conteudoLinha[posicaoConteudo].replace("/", "-"));
        }catch (ParseException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public static String valorString(String[] conteudoLinha, int posicaoConteudo) {
        try{
            return !conteudoLinha[posicaoConteudo].isEmpty() ? conteudoLinha[posicaoConteudo] : null;
        }catch (IndexOutOfBoundsException e){
            return null;
        }

    }

    public static Boolean stringToBoolean(String[] conteudoLinha, int posicaoConteudo) {
        try{
            String strBool = conteudoLinha[posicaoConteudo].equals("1") ? "true" : "false";
            return java.lang.Boolean.parseBoolean(strBool);
        }catch (IndexOutOfBoundsException e){
            return false;
        }

    }
    public static Integer stringToInteger(String[] conteudoLinha, int posicaoConteudo) {
        try{
            return Integer.parseInt(conteudoLinha[posicaoConteudo]);
        } catch (IndexOutOfBoundsException | NumberFormatException | NullPointerException e){
            return null;
        }

    }

    public static Long stringToLong(String[] conteudoLinha, int posicaoConteudo) {
        try{
            return !conteudoLinha[posicaoConteudo].isEmpty() ? Long.parseLong(conteudoLinha[posicaoConteudo]) : null;
        }catch (IndexOutOfBoundsException | NumberFormatException e){
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

    protected Object buscaRegistroConteudoLido(String conteudo, Object[] enumParaBusca) {
        return ofNullable(conteudo)
                .filter(this::isConteudoValido)
                .map(cont -> enumParaBusca[Integer.parseInt(cont)])
                .orElse(null);
    }

    private Boolean isConteudoValido(String conteudo) {
        return !conteudo.isEmpty();
    }

}
