/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.apoio.ao.desenvolvimento.de.software;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import models.Projeto;

/**
 *
 * @author Fabiano
 */
public class VerProjeto extends javax.swing.JFrame {

    /**
     * Creates new form VerProjeto
     */
    private Connection conn;
    private Statement stm;
    Projeto projeto;
    public VerProjeto(Projeto projeto) {
        initComponents();                
        try {
            Date now = new Date();
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(projeto.dateInicio);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(now.getTime() - date.getTime());
            Date tempo = calendar.getTime();            
            
            String dataFormatada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tempo);
            System.out.println(dataFormatada);
        } catch (ParseException ex) {
            Logger.getLogger(VerProjeto.class.getName()).log(Level.SEVERE, null, ex);
        }        
        this.projeto = projeto;
        txtNomeProjeto.setText(projeto.nome);
        String inicio[] = projeto.dateInicio.split(" ");
        String fim[] = projeto.dateFim.split("-");
        
        String dataInicio[] = inicio[0].split("-");
        
        txtInicio.setText("Trabalho iniciado em "+dataInicio[2]+"/"+dataInicio[1]+"/"+dataInicio[0]+" as "+inicio[1]);
        txtDescricao.setText(getQuebraDeLinha(projeto.descricao));        
        txtTermino.setText(fim[2]+"/"+fim[1]+"/"+fim[0]);
        
        
        try {
            Class.forName("com.mysql.jdbc.Driver");         
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ads", "root","");            
            stm = (Statement) conn.createStatement();   
            
            ResultSet cursor = stm.executeQuery("select equipe.nome, id_membro,cargo, membro.nome from equipe,membro where equipe.id_equipe = membro.id_equipe and equipe.id_equipe = "+projeto.idEquipe+";");            
                        
            DefaultListModel nome = new DefaultListModel();            
            while (cursor.next()) {                                
                txtNomeDaEquipe.setText(cursor.getString("equipe.nome"));                                
                nome.add(nome.getSize(),cursor.getString("membro.nome")+" | Cargo: "+cursor.getString("membro.cargo"));                                                
            }                           
            lMembros.setModel(nome);            
            cursor.close();  
        } catch (Exception ex) {
            Logger.getLogger(AddProjeto.class.getName()).log(Level.SEVERE, null, ex);
        }
        pVoltarInicio.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){  
                Projetos projetos = new Projetos(projeto.usuario);
                projetos.setLocationRelativeTo( VerProjeto.this );
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
        pEditarProjeto.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){  
                AddProjeto addProjeto = new AddProjeto(projeto);
                addProjeto.setLocationRelativeTo( VerProjeto.this );
                addProjeto.setVisible(true);
                try {
                    conn.close();
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AddEquipe.class.getName()).log(Level.SEVERE, null, ex);
                }               
                dispose();
            }  
        }); 
        pExcluirProjeto.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){  
                System.out.println();
                if (JOptionPane.showConfirmDialog(null, "Excluir projeto?") == 0) {
                    try {
                        stm.executeUpdate("delete from projeto where id_projeto = "+projeto.idProjeto+";");
                        Projetos projetos = new Projetos(projeto.usuario);
                        projetos.setLocationRelativeTo( VerProjeto.this );
                        projetos.setVisible(true);
                        try {
                            conn.close();
                            stm.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(AddEquipe.class.getName()).log(Level.SEVERE, null, ex);
                        }               
                        dispose();              
                    } catch (SQLException ex) {
                        Logger.getLogger(VerProjeto.class.getName()).log(Level.SEVERE, null, ex);
                    }                                
                }                                
            }  
        });         
    }
    public String getQuebraDeLinha(String desc){
        String newDesc = "<html>";
        String p[] = desc.split(" ");
        for (int i = 0; i < p.length; i++) {
            if (i%10 == 0) {
                newDesc += p[i]+"<br>";
            }else{
                newDesc += p[i]+" ";
            }
        }
        newDesc += "</html>";
        return newDesc;
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
        pEditarProjeto = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtInicio = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JLabel();
        txtNomeProjeto = new javax.swing.JLabel();
        mams = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtNomeDaEquipe = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lMembros = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtTermino = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        pVoltarInicio = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        pExcluirProjeto = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(235, 235, 235));

        jPanel4.setBackground(new java.awt.Color(0, 150, 136));

        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtEmail.setText("Meu projeto");

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

        pEditarProjeto.setBackground(new java.awt.Color(255, 0, 102));
        pEditarProjeto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Editar Projeto");
        pEditarProjeto.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setForeground(new java.awt.Color(153, 153, 153));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Equipe responsável pelo projeto");

        txtInicio.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtInicio.setForeground(new java.awt.Color(102, 102, 102));
        txtInicio.setText("Trabalho iniciado em dd/mm/yyy as hh/mm/ss");

        txtDescricao.setText("ldffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffa");
        txtDescricao.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        txtNomeProjeto.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        txtNomeProjeto.setForeground(new java.awt.Color(255, 0, 102));
        txtNomeProjeto.setText("Nome Do Projeto");

        mams.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mams.setForeground(new java.awt.Color(102, 102, 102));
        mams.setText("Nome da equipe :");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 102));
        jLabel6.setText("Membros");

        txtNomeDaEquipe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNomeDaEquipe.setForeground(new java.awt.Color(102, 102, 102));
        txtNomeDaEquipe.setText("Nome");

        jScrollPane2.setViewportView(lMembros);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(mams)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNomeDaEquipe))
                    .addComponent(txtNomeProjeto)
                    .addComponent(jLabel2)
                    .addComponent(txtInicio)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(txtNomeProjeto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mams)
                    .addComponent(txtNomeDaEquipe))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 150, 136));
        jLabel8.setText("Tempo de projeto");

        txtTermino.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtTermino.setForeground(new java.awt.Color(102, 102, 102));
        txtTermino.setText("01/12/2017");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("7h 51m 01s");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 150, 136));
        jLabel12.setText("Data de término");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 102));
        jLabel13.setText("Registros");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel8)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtTermino))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel13)))
                .addContainerGap(279, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(49, 49, 49)
                    .addComponent(jLabel11)
                    .addContainerGap(348, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel8)
                .addGap(86, 86, 86)
                .addComponent(jLabel12)
                .addGap(26, 26, 26)
                .addComponent(txtTermino)
                .addGap(27, 27, 27)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addContainerGap(211, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(100, 100, 100)
                    .addComponent(jLabel11)
                    .addContainerGap(391, Short.MAX_VALUE)))
        );

        pVoltarInicio.setBackground(new java.awt.Color(255, 255, 255));
        pVoltarInicio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setBackground(new java.awt.Color(255, 0, 102));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 150, 136));
        jLabel3.setText("Voltar");

        javax.swing.GroupLayout pVoltarInicioLayout = new javax.swing.GroupLayout(pVoltarInicio);
        pVoltarInicio.setLayout(pVoltarInicioLayout);
        pVoltarInicioLayout.setHorizontalGroup(
            pVoltarInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVoltarInicioLayout.createSequentialGroup()
                .addGap(0, 77, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(0, 78, Short.MAX_VALUE))
        );
        pVoltarInicioLayout.setVerticalGroup(
            pVoltarInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVoltarInicioLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pExcluirProjeto.setBackground(new java.awt.Color(0, 150, 136));
        pExcluirProjeto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Excluir Projeto");
        pExcluirProjeto.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pVoltarInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pEditarProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pExcluirProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pEditarProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(pVoltarInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pExcluirProjeto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(VerProjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerProjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerProjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerProjeto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerProjeto(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JList<String> lMembros;
    private javax.swing.JLabel mams;
    private javax.swing.JPanel pEditarProjeto;
    private javax.swing.JPanel pExcluirProjeto;
    private javax.swing.JPanel pVoltarInicio;
    private javax.swing.JLabel txtDescricao;
    private javax.swing.JLabel txtEmail;
    private javax.swing.JLabel txtInicio;
    private javax.swing.JLabel txtNomeDaEquipe;
    private javax.swing.JLabel txtNomeProjeto;
    private javax.swing.JLabel txtTermino;
    // End of variables declaration//GEN-END:variables
}
