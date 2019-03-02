package Quatro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
/*Interficie grafica, aqui es definiran les accions dels botons de la interficie
 * d'usuari
 */

/**
 * @author Llorenç
 */
public class UI extends javax.swing.JFrame {

    private Tauler escena;
    
    //timer que executara l'animació seguida
    public Timer timer = new Timer(300, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            boolean end;
            if (escena != null) {
                end = escena.Step();
                initable();
                pinta();
                
                //si hem acabat parem el timer
                if (end) {
                    timer.stop();
                }
            }
        }
    });

    //inicialització i posem l'icona d'exit
    public UI() {
        initComponents();
        escena = new Tauler();
        initable();
        pinta();
        //Posem icona eXiT a la finestra
        String dir = System.getProperty("user.dir") + "\\Imatges\\icon.png";
        this.setIconImage(new ImageIcon(dir).getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("eXiT Simulator");
        setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setToolTipText("Es començara la simulació");
        jButton1.setLabel("Step");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.setEnabled(false);
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jTable2.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable2.setRowHeight(50);
        jTable2.setShowHorizontalLines(false);
        jTable2.setShowVerticalLines(false);
        jScrollPane2.setViewportView(jTable2);

        jButton2.setText("Restart");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Run");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Stop");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(4, 4, 4)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //step
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /* Callback del Boto Start. Inicia el timer i si no hi ha més simulació 
         * mostrara la taula de resultats*/
       
           
            escena.Step();
            initable();
            pinta();
        

    }//GEN-LAST:event_jButton1ActionPerformed

    //reload
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        /*callback boto load, carregara l'escenari i eliminara l'actual si ni ha 
         * algun d'executant-se*/
        
        //parem el timer si esta actiu!
        timer.stop();
        escena = new Tauler();
         //L'escenari esta creat, inicialitsem la taula
         initable();
         pinta();
    }//GEN-LAST:event_jButton2ActionPerformed

    //run
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        /*inicia el timer*/
        timer.start();
    }//GEN-LAST:event_jButton3ActionPerformed

    //stop
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       /*para el timer*/
        timer.stop();
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables

    
    //mostra graficament l'escenari a la taula
    public void pinta() {
        /*Posa a la taula tots els icones a la posició corresponent*/
        for (int i = 0; i != escena.getX(); i++) {
            for (int j = 0; j != escena.getY(); j++) {
                int Valor=escena.getpos(i, j);
                
                if (Valor == -1){
                    String dir = System.getProperty("user.dir") + "\\Imatges\\im0.jpg";
                    ImageIcon icon= new ImageIcon(dir);
                    jTable2.setValueAt(icon, (int) escena.getY() - j -1, (int) i);//la visualitzacio s'ha de girar
                }else{
                    int Color=(int) (Valor/1000);
                    Valor = Valor - Color * 1000;
                    int Forma=(int) (Valor%1000/100);
                    Valor = Valor - Forma * 100;
                    int Forat=(int) (Valor%1000/10);
                    Valor = Valor - Forat * 10;
                    int Tamany=(int) (Valor%1000/1);
                    String dir = System.getProperty("user.dir") + "\\Imatges\\im" + Integer.toString(Color) + Integer.toString(Forma) + Integer.toString(Forat) + Integer.toString(Tamany) + ".jpg";
                    ImageIcon icon= new ImageIcon(dir);
                    jTable2.setValueAt(icon, (int) escena.getY() - j -1, (int) i);//la visualitzacio s'ha de girar
                }
            }
        }

    }

    //inicialitza la taula 
    public void initable() {
        /*inicialitza la taula segons l'escenari i canvia el default renderer 
         * per poder visualitzar icones a les caselles*/
        int x, y;
        y = (int) escena.getX();//la visualitzacio va al reves
        x = (int) escena.getY();
        DefaultTableModel Tmodel = new DefaultTableModel(x, y);
        jTable2.setModel(Tmodel);
        for (int i = 0; i != y; i++) {
            jTable2.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());

            //posem amplada preferida i actual a 50
            jTable2.getColumnModel().getColumn(i).setPreferredWidth(75);
            jTable2.getColumnModel().getColumn(i).setWidth(75);
        }

        //posem el color
       // jTable2.setBackground(Color.white);

        //posem les mides a les celles
        jTable2.setRowHeight(75);
    }
}
