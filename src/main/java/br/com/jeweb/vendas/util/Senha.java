/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Gera uma sequência alfanumérica de tamanho passado por parâmetro.
 * Possíveis caracteres:
 * <p># $ % & @<br>
 * abcdefghijklmnopqrstuvwxyz<br>
 * ABCDEFGHIJKLMNOPQRSTUVYWXZ<br>
 * 0123456789</p>
 * 
 * @author Jonas Esteves
 * @see contato@jonasesteves.com
 */
public class Senha {

    public Senha() { }

    /**
     * Gera a sequência.
     * 
     * @param tamanho Tamanho da senha desejada.
     * @return A senha, do tipo String.
     */
    public String getSenha(Integer tamanho) {
        String novaSenha = "";
        String caracteres = "@#$%&@#$%&@#$%&@#$%&ABCDEFGHIJKLMNOPQRSTUVYWXZ0123456789abcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();

        int index = -1;
        for (int i = 0; i < tamanho; i++) {
            index = random.nextInt(caracteres.length());
            novaSenha += caracteres.substring(index, index + 1);
        }
        return novaSenha;
    }
    
    /**
     * Gera a sequência sem caracteres especiais.
     * 
     * @param tamanho Tamanho da senha desejada.
     * @return A senha, do tipo String.
     */
    public String getSenhaSemCaracEsp(Integer tamanho) {
        String novaSenha = "";
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVYWXZ0123456789abcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();

        int index = -1;
        for (int i = 0; i < tamanho; i++) {
            index = random.nextInt(caracteres.length());
            novaSenha += caracteres.substring(index, index + 1);
        }
        return novaSenha;
    }

    /**
     * Retorna uma nova senha em MD5.
     *
     * @param tamanho Tamanho da senha a ser criada.
     * @return Valor em MD5
     */
    public String getSenhaMd5(Integer tamanho) {
        return parseMD5(this.getSenha(tamanho));
    }

    /**
     * Converte um valor de um parâmetro passado para um hash MD5.
     *
     * @param senha Valor a ser convertido.
     * @return Valor convertido
     */
    public String parseMD5(String senha) {
        MessageDigest m;
        String md5 = null;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(senha.getBytes(), 0, senha.length());
            md5 = new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return md5;
    }
}
