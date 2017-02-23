/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.de.apoio.ao.desenvolvimento.de.software;

import Interfaces.Notifica;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.Usuario;

/**
 *
 * @author Fabiano
 */
public class AddEquipe extends javax.swing.JFrame {
    private Connection conn;
    private Statement stm;
    public static Notifica notifica;
    /**
     * Creates new form AddEquipe
     */
    public AddEquipe(Usuario usuario) {
        initComponents();
        try {         
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ads", "root","");            
            stm = (Statement) conn.createStatement();   
        } catch (Exception ex) {
            Logger.getLogger(AddEquipe.class.getName()).log(Level.SEVERE, null, ex);
        }        
            
        pMembro1.setVisible(false);
        pMembro2.setVisible(false);
        pMembro3.setVisible(false);
        
        pAddMembro.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){                  
                if (!pMembro1.isVisible()) {
                    pMembro1.setVisible(true);
                }else if(!pMembro2.isVisible()){
                    pMembro2.setVisible(true);
                }else if(!pMembro3.isVisible()){
                    pMembro3.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null,"No máximo 5 membros contando com você!");
                }                
            }  
        });   
        
        close.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){                  
               pMembro1.setVisible(false);
            }  
        });   
        close2.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){                  
               pMembro2.setVisible(false);
            }  
        });   
        close3.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){                  
               pMembro3.setVisible(false);
            }  
        });   
        pVoltarInicio.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){  
                Projetos projetos = new Projetos(usuario);
                projetos.setLocationRelativeTo( AddEquipe.this );
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
        
        PCriarEquipe.addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){  
                try {
                    ResultSet cursor = stm.executeQuery("select * from equipe where id_usuario = "+usuario.id+" and nome = '"+edtNome.getText()+"';");                    
                    if (!cursor.next()) {
                        stm.close();                                                
                        stm = (Statement) conn.createStatement();
                        stm.executeUpdate("insert into equipe (nome, id_usuario) values('"+edtNome.getText()+"',"+usuario.id+");");                                            
                        
                        ResultSet c = stm.executeQuery("select * from equipe where id_usuario = "+usuario.id+" and nome = '"+edtNome.getText()+"';");                        
                        if (c.next()) {
                            insertMembros(c.getInt("id_equipe"));
                        }
                        c.close();                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Você já tem uma equipe com esse nome!");
                    }            
                    cursor.close();                       
                } catch (Exception ex) {
                    Logger.getLogger(AddEquipe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }  
        }); 
    }
    public void insertMembros(int id) throws Exception{
        stm.close();
        stm = (Statement) conn.createStatement();
        stm.executeUpdate("insert into membro (nome, cargo,id_equipe) values('"+tfNome.getText()+"','"+ftCargo.getText()+"',"+id+");");
        if (pMembro1.isVisible()) {
            stm.close();
            stm = (Statement) conn.createStatement();
            stm.executeUpdate("insert into membro (nome, cargo,id_equipe) values('"+tfNome1.getText()+"','"+ftCargo1.getText()+"',"+id+");");
        }
        if (pMembro2.isVisible()) {
            stm.close();
            stm = (Statement) conn.createStatement();
            stm.executeUpdate("insert into membro (nome, cargo,id_equipe) values('"+tfNome2.getText()+"','"+ftCargo2.getText()+"',"+id+");");
        }
        if (pMembro3.isVisible()) {
            stm.close();
            stm = (Statement) conn.createStatement();
            stm.executeUpdate("insert into membro (nome, cargo,id_equipe) values('"+tfNome3.getText()+"','"+ftCargo3.getText()+"',"+id+");");
        }        
        notifica.terminou();
        dispose();
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
        PCriarEquipe = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        edtNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pVoltarInicio = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pMembros = new javax.swing.JPanel();
        pMembro = new javax.swing.JPanel();
        tfNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ftCargo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pMembro1 = new javax.swing.JPanel();
        tfNome1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        ftCargo1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        pMembro3 = new javax.swing.JPanel();
        tfNome3 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        ftCargo3 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        close3 = new javax.swing.JLabel();
        pMembro2 = new javax.swing.JPanel();
        tfNome2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        ftCargo2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        close2 = new javax.swing.JLabel();
        pAddMembro = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 150, 136));

        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtEmail.setText("Nova equipe");

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

        PCriarEquipe.setBackground(new java.awt.Color(255, 0, 102));
        PCriarEquipe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Criar");
        PCriarEquipe.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 0, 102));
        jLabel3.setText("Nome da Equipe");

        jLabel6.setForeground(new java.awt.Color(255, 0, 102));
        jLabel6.setText("Membros");

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
                .addGap(0, 110, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(0, 111, Short.MAX_VALUE))
        );
        pVoltarInicioLayout.setVerticalGroup(
            pVoltarInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVoltarInicioLayout.createSequentialGroup()
                .addGap(0, 21, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jLabel4.setText("Nome");

        jLabel5.setText("Cargo");

        javax.swing.GroupLayout pMembroLayout = new javax.swing.GroupLayout(pMembro);
        pMembro.setLayout(pMembroLayout);
        pMembroLayout.setHorizontalGroup(
            pMembroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMembroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMembroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pMembroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ftCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        pMembroLayout.setVerticalGroup(
            pMembroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pMembroLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pMembroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pMembroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setText("Nome");

        jLabel9.setText("Cargo");

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/close.png"))); // NOI18N

        javax.swing.GroupLayout pMembro1Layout = new javax.swing.GroupLayout(pMembro1);
        pMembro1.setLayout(pMembro1Layout);
        pMembro1Layout.setHorizontalGroup(
            pMembro1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMembro1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMembro1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(tfNome1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pMembro1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pMembro1Layout.createSequentialGroup()
                        .addComponent(ftCargo1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(close))
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pMembro1Layout.setVerticalGroup(
            pMembro1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pMembro1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pMembro1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGroup(pMembro1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pMembro1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pMembro1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfNome1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftCargo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pMembro1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(close)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel12.setText("Nome");

        jLabel13.setText("Cargo");

        close3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/close.png"))); // NOI18N

        javax.swing.GroupLayout pMembro3Layout = new javax.swing.GroupLayout(pMembro3);
        pMembro3.setLayout(pMembro3Layout);
        pMembro3Layout.setHorizontalGroup(
            pMembro3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMembro3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMembro3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(tfNome3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pMembro3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ftCargo3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(close3)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pMembro3Layout.setVerticalGroup(
            pMembro3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMembro3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pMembro3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(close3)
                    .addGroup(pMembro3Layout.createSequentialGroup()
                        .addGroup(pMembro3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pMembro3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNome3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftCargo3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel16.setText("Nome");

        jLabel17.setText("Cargo");

        close2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/close.png"))); // NOI18N

        javax.swing.GroupLayout pMembro2Layout = new javax.swing.GroupLayout(pMembro2);
        pMembro2.setLayout(pMembro2Layout);
        pMembro2Layout.setHorizontalGroup(
            pMembro2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMembro2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMembro2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(tfNome2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pMembro2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ftCargo2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(close2)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pMembro2Layout.setVerticalGroup(
            pMembro2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMembro2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pMembro2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(close2)
                    .addGroup(pMembro2Layout.createSequentialGroup()
                        .addGroup(pMembro2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pMembro2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfNome2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftCargo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pMembrosLayout = new javax.swing.GroupLayout(pMembros);
        pMembros.setLayout(pMembrosLayout);
        pMembrosLayout.setHorizontalGroup(
            pMembrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMembrosLayout.createSequentialGroup()
                .addGroup(pMembrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pMembro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pMembro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pMembro3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pMembro2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );
        pMembrosLayout.setVerticalGroup(
            pMembrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMembrosLayout.createSequentialGroup()
                .addComponent(pMembro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pMembro1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pMembro2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pMembro3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(pMembros);

        pAddMembro.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setBackground(new java.awt.Color(255, 0, 102));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 150, 136));
        jLabel7.setText("Adicionar outro membro");

        javax.swing.GroupLayout pAddMembroLayout = new javax.swing.GroupLayout(pAddMembro);
        pAddMembro.setLayout(pAddMembroLayout);
        pAddMembroLayout.setHorizontalGroup(
            pAddMembroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pAddMembroLayout.createSequentialGroup()
                .addContainerGap(163, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(38, 38, 38))
        );
        pAddMembroLayout.setVerticalGroup(
            pAddMembroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pAddMembroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pVoltarInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(PCriarEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(pAddMembro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(edtNome, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))))
                .addContainerGap(29, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(pAddMembro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pVoltarInicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PCriarEquipe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(AddEquipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddEquipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddEquipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddEquipe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddEquipe(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PCriarEquipe;
    private javax.swing.JLabel close;
    private javax.swing.JLabel close2;
    private javax.swing.JLabel close3;
    private javax.swing.JTextField edtNome;
    private javax.swing.JTextField ftCargo;
    private javax.swing.JTextField ftCargo1;
    private javax.swing.JTextField ftCargo2;
    private javax.swing.JTextField ftCargo3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JPanel pAddMembro;
    private javax.swing.JPanel pMembro;
    private javax.swing.JPanel pMembro1;
    private javax.swing.JPanel pMembro2;
    private javax.swing.JPanel pMembro3;
    private javax.swing.JPanel pMembros;
    private javax.swing.JPanel pVoltarInicio;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfNome1;
    private javax.swing.JTextField tfNome2;
    private javax.swing.JTextField tfNome3;
    private javax.swing.JLabel txtEmail;
    // End of variables declaration//GEN-END:variables
}
