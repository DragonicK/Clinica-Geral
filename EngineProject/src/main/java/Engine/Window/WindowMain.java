package Engine.Window;


import Engine.Handler.PersonHandler;
import Engine.Handler.RegionHandler;
import Engine.Handler.ProductHandler;
import Engine.Handler.PatientHandler;
import Engine.Handler.SupplierHandler;
import Engine.Handler.EmployeeHandler;
import Engine.Database.DBConfiguration;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.Notifier;
import Engine.Window.Observer.NotificationMain;
import Engine.Communication.DependencyRepository;

public class WindowMain extends javax.swing.JFrame {
    private NotificationMain notification;
    private WindowProduct windowProduct;
    private WindowRegion windowRegion;
    private WindowPerson windowPerson;
    private WindowSupplier windowSupplier;
    private WindowPatient windowPatient;
    private WindowEmployee windowEmployee;
    private DependencyRepository dependencies;
    
    public WindowMain() {
        initComponents();
        
        var config = new DBConfiguration();
        config.Database = "engine";
        config.Host = "localhost";
        config.Port = 3306;
        config.User = "root";
        config.Passphrase = "dwHandle";
        
        notification = new NotificationMain(this);
        notification.Add(Changes.Patient);
        notification.Add(Changes.Person);
        
        var notifier = new Notifier();
        notifier.Add(notification);
        
        dependencies = new DependencyRepository();
        dependencies.DBConfiguration = config;
        dependencies.Notifier = notifier;
        dependencies.EmployeeHandler = new EmployeeHandler(config);
        dependencies.PatientHandler = new PatientHandler(config);
        dependencies.ProductHandler = new ProductHandler(config);
        dependencies.SupplierHandler = new SupplierHandler(config);
        dependencies.RegionHandler = new RegionHandler(config);
        dependencies.PersonHandler = new PersonHandler(config);
    }
    
    public void UpdateWindow() {
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuItemExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        MenuItemProduct = new javax.swing.JMenuItem();
        MenuItemPerson = new javax.swing.JMenuItem();
        MenuItemSupplier = new javax.swing.JMenuItem();
        MenuItemPatient = new javax.swing.JMenuItem();
        MenuItemEmployee = new javax.swing.JMenuItem();
        MenuItemRegion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciamento de Clínica");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jMenu1.setText("Arquivo");
        jMenu1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        MenuItemExit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemExit.setText("Sair");
        MenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItemExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Janela");
        jMenu2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        MenuItemProduct.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemProduct.setText("Produto");
        MenuItemProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemProductActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemProduct);

        MenuItemPerson.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemPerson.setText("Pessoa");
        MenuItemPerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemPersonActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemPerson);

        MenuItemSupplier.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemSupplier.setText("Fornecedor");
        MenuItemSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemSupplierActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemSupplier);

        MenuItemPatient.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemPatient.setText("Paciente");
        MenuItemPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemPatientActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemPatient);

        MenuItemEmployee.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemEmployee.setText("Colaborador");
        MenuItemEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemEmployeeActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemEmployee);

        MenuItemRegion.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemRegion.setText("Cidade e País");
        MenuItemRegion.setActionCommand("");
        MenuItemRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemRegionActionPerformed(evt);
            }
        });
        jMenu2.add(MenuItemRegion);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuItemProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemProductActionPerformed
        if (windowProduct == null) {
            windowProduct = new WindowProduct();
            windowProduct.SetDependency(dependencies);
            windowProduct.setLocationRelativeTo(null);
        }
        
        if (!windowProduct.isVisible()) {
            windowProduct.ClearTable();
            windowProduct.LoadProducts();
            windowProduct.setVisible(true);
        }
    }//GEN-LAST:event_MenuItemProductActionPerformed

    private void MenuItemRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemRegionActionPerformed
        if (windowRegion == null) {
            windowRegion = new WindowRegion();
            windowRegion.SetDependency(dependencies);
            windowRegion.setLocationRelativeTo(null);
        }
        
        if (!windowRegion.isVisible()) {
            windowRegion.ClearCountries();
            windowRegion.ClearCities();
            windowRegion.LoadCountries();
            windowRegion.LoadCities();
            windowRegion.FillCountries();
            windowRegion.FillCities();
            windowRegion.ClearCityCountry();
            windowRegion.FillCityCountry();
            windowRegion.setVisible(true);
        }
    }//GEN-LAST:event_MenuItemRegionActionPerformed

    private void MenuItemPersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemPersonActionPerformed
        if (windowPerson == null) {
            windowPerson = new WindowPerson();
            windowPerson.SetDependency(dependencies);
            windowPerson.setLocationRelativeTo(null);
        }
        
        if (!windowPerson.isVisible()) {
            windowPerson.ClearTable();
            windowPerson.LoadPersons();
            windowPerson.setVisible(true);
        }
    }//GEN-LAST:event_MenuItemPersonActionPerformed

    private void MenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemExitActionPerformed
        var EXIT_SUCESS = 0;
        
        System.exit(EXIT_SUCESS);
    }//GEN-LAST:event_MenuItemExitActionPerformed

    private void MenuItemSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemSupplierActionPerformed
        if (windowSupplier == null) {
            windowSupplier = new WindowSupplier();
            windowSupplier.SetDependency(dependencies);
            windowSupplier.setLocationRelativeTo(null);
        }
        
        if (!windowSupplier.isVisible()) {
            windowSupplier.ClearTable();
            windowSupplier.LoadSuppliers();
            windowSupplier.setVisible(true);
        }
        
    }//GEN-LAST:event_MenuItemSupplierActionPerformed

    private void MenuItemPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemPatientActionPerformed
        if (windowPatient == null) {
            windowPatient = new WindowPatient();
            windowPatient.SetDependency(dependencies);
            windowPatient.setLocationRelativeTo(null);
        }
        
        if (!windowPatient.isVisible()) {
            windowPatient.ClearTable();
            windowPatient.LoadPatients();
            windowPatient.setVisible(true);
        }
    }//GEN-LAST:event_MenuItemPatientActionPerformed

    private void MenuItemEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemEmployeeActionPerformed
        if (windowEmployee == null) {
            windowEmployee = new WindowEmployee();
            windowEmployee.SetDependency(dependencies);
            windowEmployee.setLocationRelativeTo(null);
        }
        
        if (!windowEmployee.isVisible()) {
            windowEmployee.ClearTable();
            windowEmployee.LoadEmployees();
            windowEmployee.setVisible(true);
        }  
    }//GEN-LAST:event_MenuItemEmployeeActionPerformed
    
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
            java.util.logging.Logger.getLogger(WindowMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuItemEmployee;
    private javax.swing.JMenuItem MenuItemExit;
    private javax.swing.JMenuItem MenuItemPatient;
    private javax.swing.JMenuItem MenuItemPerson;
    private javax.swing.JMenuItem MenuItemProduct;
    private javax.swing.JMenuItem MenuItemRegion;
    private javax.swing.JMenuItem MenuItemSupplier;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
