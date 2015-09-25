/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.art.iai.bean;

import java.util.Date;

/**
 *
 * @author Paulo
 */
public class Usuario {
    public String email;
    public String senha;
    public String nome;
    public Date dataNascimento;

    public Usuario() {
    }

    public Usuario(String email, String senha, String nome, Date dataNascimento) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return nome + "(" + email + ")";
    }

}
