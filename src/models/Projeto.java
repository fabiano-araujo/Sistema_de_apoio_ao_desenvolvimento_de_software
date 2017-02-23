/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;


/**
 *
 * @author Fabiano
 */
public class Projeto {
    public int idProjeto = -1;
    public int idEquipe;
    public Usuario usuario;
    public String nome;
    public String descricao;    
    public String dateInicio;
    public String dateFim;
}
