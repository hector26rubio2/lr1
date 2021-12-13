/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package front;

import back.control.ControlGramatica;
import back.persistencia.GramaticaException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.FILES_ONLY;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author hecto
 */
public class ventana extends javax.swing.JFrame {

    private ControlGramatica controlGramatica;
    /**
     * Creates new form ventana
     */
    public ventana() {
        controlGramatica = new ControlGramatica();
        initComponents();
         setLocationRelativeTo(null);
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
        jBCargarGramatica = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 700));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBCargarGramatica.setText("Cargar Gramatica");
        jBCargarGramatica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCargarGramaticaActionPerformed(evt);
            }
        });
        jPanel1.add(jBCargarGramatica, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 60, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBCargarGramaticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCargarGramaticaActionPerformed
        try {
            //        var openFile = new JFileChooser();
//      openFile.setFileSelectionMode(FILES_ONLY);
//      var selection = openFile.showOpenDialog(this);
//      if (selection == APPROVE_OPTION) {
//          var f = openFile.getSelectedFile();
//          try {
//              System.out.println(f.getPath());
//              controlGramatica.cargarGramatica("C:\\Users\\hecto\\Documents\\RepositoriosGit\\ProyectoLenguajesLR1\\gramatiaE.json");
//              showMessageDialog(
//                      this,
//                      "Se Cargo Correctamente la Gramática " + "\n" + f.getPath(),
//                      "¡Carga Exitosa!",
//                      INFORMATION_MESSAGE
//              );
//              
//          } catch (Exception e) {
//              showMessageDialog(
//                      this,
//                      e.getMessage() + "\n" + f.getPath(),
//                      "¡Error Documento!",
//                      ERROR_MESSAGE
//              );
//          }
//      }
controlGramatica.cargarGramatica("C:\\Users\\hecto\\Documents\\RepositoriosGit\\ProyectoLenguajesLR1\\gramatiaE.json");
        } catch (GramaticaException ex) {
            Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
      controlGramatica.datosGramatica();
    }//GEN-LAST:event_jBCargarGramaticaActionPerformed

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
            java.util.logging.Logger.getLogger(ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBCargarGramatica;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}