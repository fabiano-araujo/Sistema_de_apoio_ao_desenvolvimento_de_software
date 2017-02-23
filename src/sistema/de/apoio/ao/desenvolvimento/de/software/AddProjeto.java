/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.apoio.ao.desenvolvimento.de.software;

import Interfaces.Notifica;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.Projeto;
import models.Usuario;

/**
 *
 * @author Fabiano
 */
public class AddProjeto extends javax.swing.JFrame {
    private Connection conn;
    private Statement stm;
    HashMap<String,String> equipes;
    Usuario usuario;
    /**
     * Creates new form Projeto
     */
    public AddProjeto(Projeto projeto) {
        initComponents();        
        this.usuario = projeto.usuario;                     
        equipes = new HashMap();                                        
        dcTermino.setMinSelectableDate(new Date());
        if (projeto.idProjeto != -1) {
            edtNome.setText(projeto.nome);
            taDescricao.setText(projeto.descricao);
            
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(projeto.dateFim);
                dcTermino.setDate(date);
            } catch (ParseException ex) {
                Logger.getLogger(AddProjeto.class.getName()).log(Level.SEVERE, null, ex);
            }            
            
        }        
        try {
            Class.forName("com.mysql.jdbc.Driver");         
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ads", "root","");            
            stm = (Statement) conn.createStatement();   
            
            ResultSet cursor = stm.executeQuery("select * from equipe where id_usuario = "+usuario.id+" order by nome;");
            if (cursor.next()) {
                cursor.beforeFirst();   
                int i = 0;
                while (cursor.next()) {
                    equipes.put(cursor.getString("nome"), cursor.getString("id_equipe"));                    
                    cbEquipe.addItem(cursor.getString("nome"));                    
                    if (projeto.idProjeto != -1 && cursor.getString("id_equipe").equals(projeto.idEquipe+"")) {
                        cbEquipe.setSelectedIndex(i);
                    }
                    i++;
                }                            
            }else{
                JOptionPane.showMessageDialog(null, "Você ainda não tem nenhuma equipe, crie uma primeiro!");
            }            
            cursor.close();  
        } catch (Exception ex) {
            Logger.getLogger(AddProjeto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PProjeto.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){                  
                try{        
                    if (taDescricao.getText().length() > 500) {
                        JOptionPane.showMessageDialog(null, "Sua descrição está muito longa no máximo 500 caracteres!");
                    }else{
                        Projeto projeto = new Projeto();
                        projeto.nome = edtNome.getText();        
                        projeto.usuario = usuario;
                        projeto.descricao = taDescricao.getText();
                        projeto.idEquipe = Integer.parseInt(equipes.get(cbEquipe.getSelectedItem().toString()));                                                                     

                        String termino = String.format("%1$tY/%1$tm/%1$td", dcTermino.getDate());
                        projeto.dateFim = termino;                                      
                        if (projeto.nome.length() > 0 && termino.length() > 3) {    

                            ResultSet cursor = stm.executeQuery("select * from projeto where id_usuario = "+usuario.id+" and nome = '"+projeto.nome+"';");
                            if (!cursor.next()) {
                                stm.close();
                                stm = (Statement) conn.createStatement();
                                stm.executeUpdate("INSERT INTO projeto(descricao,id_equipe, nome, dt_inicio, dt_fim,id_usuario) VALUES ('"
                                    +projeto.descricao+"',"
                                    +projeto.idEquipe+",'"
                                    +projeto.nome+"', NOW( ), '"+termino+"',"
                                    +projeto.usuario.id+");");                                     
                                projeto = getTodasInformacoesProjeto(projeto.nome);
                                VerProjeto verProjeto = new VerProjeto(projeto);
                                verProjeto.setLocationRelativeTo( AddProjeto.this );
                                verProjeto.setVisible(true);                            
                                try {
                                    conn.close();
                                    stm.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(AddEquipe.class.getName()).log(Level.SEVERE, null, ex);
                                }               
                                dispose();
                            }else{
                                JOptionPane.showMessageDialog(null, "Você já tem um projeto com esse nome!");
                            }            
                            cursor.close();                                                                                                  
                        }else{
                            JOptionPane.showMessageDialog(null, "Informe todos os dados corretamente!");
                        }                    
                    }                    
                }catch(Exception e1){e1.printStackTrace();}                
                //dispose();
            }  
        }); 
        pVoltarInicio.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){  
                Projetos projetos = new Projetos(usuario);
                projetos.setLocationRelativeTo( AddProjeto.this );
                projetos.setVisible(true);
                try {
                    conn.close();
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddEquipe.class.getName()).log(Level.SEVERE, null, ex);
                }               
                dispose();
            }  
        });         
        pNovaEquipe.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){  
                AddEquipe addEquipe = new AddEquipe(usuario);
                addEquipe.setLocationRelativeTo( AddProjeto.this );
                addEquipe.setVisible(true);                
            }  
        });        
        AddEquipe.notifica = new Notifica() {
            @Override
            public void terminou() {
                try {
                    JOptionPane.showMessageDialog(null,"Equipe inserida com sucesso!");
                    cbEquipe.removeAllItems();
                    stm.close();
                    stm = (Statement) conn.createStatement();
                    ResultSet cursor;
                    try {
                        cursor = stm.executeQuery("select * from equipe where id_usuario = "+usuario.id+" order by nome;");
                        if (cursor.next()) {
                            cursor.beforeFirst();            
                            while (cursor.next()) {
                                equipes.put(cursor.getString("nome"), cursor.getString("id_equipe"));
                                cbEquipe.addItem(cursor.getString("nome"));                                                    
                            }                            
                        }else{
                            JOptionPane.showMessageDialog(null, "Você ainda não tem nenhuma equipe, crie uma primeiro!");
                        }            
                        cursor.close();  
                    } catch (SQLException ex) {
                        Logger.getLogger(AddProjeto.class.getName()).log(Level.SEVERE, null, ex);
                    }                                
                } catch (Exception ex) {
                    Logger.getLogger(AddProjeto.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
        };               
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtEmail = new javax.swing.JLabel();
        PProjeto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        edtNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbEquipe = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        dcTermino = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        taDescricao = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        pVoltarInicio = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pNovaEquipe = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 150, 136));

        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtEmail.setText("Novo projeto");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PProjeto.setBackground(new java.awt.Color(255, 0, 102));
        PProjeto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Criar");
        PProjeto.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 0, 102));
        jLabel3.setText("Nome do projeto");

        jLabel4.setForeground(new java.awt.Color(255, 0, 102));
        jLabel4.setText("Previsão de término");

        jLabel5.setForeground(new java.awt.Color(255, 0, 102));
        jLabel5.setText("Equipe");

        taDescricao.setColumns(20);
        taDescricao.setRows(5);
        jScrollPane1.setViewportView(taDescricao);

        jLabel6.setForeground(new java.awt.Color(255, 0, 102));
        jLabel6.setText("Descrição");

        pVoltarInicio.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 0, 102));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 150, 136));
        jLabel2.setText("Voltar");

        javax.swing.GroupLayout pVoltarInicioLayout = new javax.swing.GroupLayout(pVoltarInicio);
        pVoltarInicio.setLayout(pVoltarInicioLayout);
        pVoltarInicioLayout.setHorizontalGroup(
            pVoltarInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVoltarInicioLayout.createSequentialGroup()
                .addGap(0, 100, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(0, 101, Short.MAX_VALUE))
        );
        pVoltarInicioLayout.setVerticalGroup(
            pVoltarInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVoltarInicioLayout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pNovaEquipe.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(255, 0, 102));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 150, 136));
        jLabel9.setText("Nova equipe");

        javax.swing.GroupLayout pNovaEquipeLayout = new javax.swing.GroupLayout(pNovaEquipe);
        pNovaEquipe.setLayout(pNovaEquipeLayout);
        pNovaEquipeLayout.setHorizontalGroup(
            pNovaEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pNovaEquipeLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(28, 28, 28))
        );
        pNovaEquipeLayout.setVerticalGroup(
            pNovaEquipeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pNovaEquipeLayout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(pVoltarInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(PProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(edtNome, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                        .addComponent(dcTermino, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbEquipe, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pNovaEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(edtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pNovaEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pVoltarInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public Projeto getTodasInformacoesProjeto(String nome) throws Exception{
        Projeto projeto = new Projeto(); 
        stm.close();
        stm = (Statement) conn.createStatement();
        ResultSet cursor = stm.executeQuery("select * from projeto where id_usuario = "+usuario.id+" and nome = '"+nome+"';");
        if (cursor.next()) {
                projeto.nome = cursor.getString("nome");                                    
                projeto.descricao = taDescricao.getText();
                projeto.idEquipe = cursor.getInt("id_equipe");
                projeto.dateFim = cursor.getString("dt_fim");                
                projeto.dateInicio = cursor.getString("dt_inicio");
                projeto.idProjeto = cursor.getInt("id_projeto");                
                projeto.usuario = usuario;
        }        
        return projeto;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddProjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddProjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddProjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddProjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddProjeto(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PProjeto;
    private javax.swing.JComboBox<String> cbEquipe;
    private com.toedter.calendar.JDateChooser dcTermino;
    private javax.swing.JTextField edtNome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pNovaEquipe;
    private javax.swing.JPanel pVoltar;
    private javax.swing.JPanel pVoltar1;
    private javax.swing.JPanel pVoltarInicio;
    private javax.swing.JTextArea taDescricao;
    private javax.swing.JLabel txtEmail;
    // End of variables declaration//GEN-END:variables
}
