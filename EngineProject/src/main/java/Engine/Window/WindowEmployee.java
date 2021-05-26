package Engine.Window;

import Engine.Employee;
import Engine.Common.Strings;
import Engine.Database.DBOperationState;
import Engine.Communication.DependencyRepository;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationEmployee;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.awt.event.KeyEvent;
import javax.swing.JTable;
import java.util.List;

public class WindowEmployee extends javax.swing.JFrame {
    // Componente de notificação da janela.
    private NotificationEmployee notification;
    private WindowEmployeeDetail detail;
    private DependencyRepository dependencies;
    private List<Employee> employees;
    private int selectedRow;
    
    public WindowEmployee() {
        initComponents();
        
        RadioGroup.add(RadioDocument);
        RadioGroup.add(RadioName);
        
        SetEnabledButtons(false);
    }
    
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        notification = new NotificationEmployee(this);
        notification.Add(Changes.Person);
        notification.Add(Changes.Employee);
        
        dependencies.Notifier.Add(notification);
    }
    
    public void SetEnabledControls(boolean enabled) {
        TextFind.setEnabled(enabled);
        TableEmployee.setEnabled(enabled);
    }
    
    public void SetEnabledButtons(boolean enabled) {
        ButtonEdit.setEnabled(enabled);
        ButtonExclude.setEnabled(enabled);
    }
    
    public void UpdateWindow() {
        Find();
        SetEnabledControls(true);
    }
    
    public void ClearTable() {
        var model = (DefaultTableModel)TableEmployee.getModel();
        model.setRowCount(0);
        
        selectedRow = -1;
        
        TableEmployee.revalidate();
    }
    
    public void LoadEmployees() {
        var handler = dependencies.EmployeeHandler;
        
        if (handler != null) {
            employees = handler.GetEmplyees();
            FillEmployees(employees);
        }
    }
    
    public void FillEmployees(List<Employee> list) {
        var count = list.size();
        var model = (DefaultTableModel)TableEmployee.getModel();
        var formatter = new SimpleDateFormat("dd-MM-yyyy");
        
        for (var i = 0; i < count; i++) {
            var employee = list.get(i);
            
            Object[] row = {
                employee.Name,
                employee.Document,
                formatter.format(employee.AdmissionDate),
                employee.Role,
            };
            
            model.addRow(row);
        }
    }
    
    private void Find() {
        var handler = dependencies.EmployeeHandler;
        
        if (TextFind.getText().length() > 0) {
            if (RadioName.isSelected()) {
                employees = handler.FindByName(TextFind.getText().trim());
            }
            
            if (RadioDocument.isSelected()) {
                var document = TextFind.getText().trim();
                employees = handler.FindByDocument(Strings.ConvertToDocument(document));
            }
        }
        else {
            employees = handler.GetEmplyees();
        }
        
        ClearTable();
        
        FillEmployees(employees);
    }
    
    private void ShowErrorMessage(String message) {
        MessageBox.Show(this,
                message,
                "Houve um problema",
                "Fornecedor",
                MessageBoxInformation.Error
        );
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RadioGroup = new javax.swing.ButtonGroup();
        ButtonEdit = new javax.swing.JButton();
        ButtonExclude = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableEmployee = new javax.swing.JTable();
        TextFind = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        RadioDocument = new javax.swing.JRadioButton();
        RadioName = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuItemNew = new javax.swing.JMenuItem();
        MenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ButtonEdit.setText("Editar");
        ButtonEdit.setEnabled(false);
        ButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditActionPerformed(evt);
            }
        });

        ButtonExclude.setText("Excluir");
        ButtonExclude.setEnabled(false);
        ButtonExclude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonExcludeActionPerformed(evt);
            }
        });

        TableEmployee.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TableEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "CPF", "Data Admissão", "Cargo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableEmployee.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TableEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableEmployeeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableEmployee);

        TextFind.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextFind.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todos os pacientes.");
        TextFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFindKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setText("Pesquisar por:");

        RadioDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioDocument.setSelected(true);
        RadioDocument.setText("CPF");

        RadioName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioName.setText("Nome");

        jLabel2.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Pressione ENTER para iniciar a pesquisa.");

        jMenu1.setText("Arquivo");
        jMenu1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        MenuItemNew.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemNew.setText("Novo Colaborador");
        MenuItemNew.setToolTipText("");
        MenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemNewActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItemNew);

        MenuItemExit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemExit.setText("Sair");
        MenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(MenuItemExit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButtonExclude, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(TextFind)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(RadioDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(RadioName, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(132, 132, 132)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(RadioDocument)
                    .addComponent(RadioName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ButtonExclude, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditActionPerformed
        if (selectedRow >= 0) {
            var employee = employees.get(selectedRow);

            if (detail == null) {
                detail = new WindowEmployeeDetail();
                detail.SetDependency(dependencies);
                detail.setLocationRelativeTo(null);
            }

            if (!detail.isVisible()) {
                detail.Clear();
                detail.ClearPersons();
                detail.LoadPersons();
                detail.SetOperationState(DBOperationState.Update);
                detail.SetEmployee(employee);
                detail.setVisible(true);
            }
        }
    }//GEN-LAST:event_ButtonEditActionPerformed

    private void ButtonExcludeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonExcludeActionPerformed
        if (selectedRow >= 0) {
            var handler = dependencies.EmployeeHandler;
            var employee = employees.get(selectedRow);
            
            if (handler.CanDelete(employee)) {
                handler.Delete(employee);
                
                employees.remove(employee);
                selectedRow = -1;
                
                dependencies.Notifier.Notify(Changes.Employee);
            }
            else {
                ShowErrorMessage("Este colaborador está vinaculado à uma consulta e não pode ser excluído.");
            }
        }
    }//GEN-LAST:event_ButtonExcludeActionPerformed

    private void TableEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableEmployeeMouseClicked
        var source = (JTable)evt.getSource();
        int row = source.rowAtPoint(evt.getPoint());
        
        SetEnabledButtons(false);
        
        selectedRow = -1;
        
        if (row >= 0 && employees.size() > 0) {
            SetEnabledButtons(true);
            selectedRow = row;
        }
    }//GEN-LAST:event_TableEmployeeMouseClicked

    private void TextFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFindKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Fecha a janela por segurança.
            if (detail != null) {
                detail.setVisible(false);
            }
            
            Find();
        }
    }//GEN-LAST:event_TextFindKeyPressed

    private void MenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemNewActionPerformed
        if (detail == null) {
            detail = new WindowEmployeeDetail();
            detail.SetDependency(dependencies);
            detail.setLocationRelativeTo(null);
        }
        
        if (!detail.isVisible()) {
            detail.Clear();
            detail.ClearPersons();
            detail.LoadPersons();
            detail.SetOperationState(DBOperationState.Insert);    
            detail.setVisible(true);
        }
    }//GEN-LAST:event_MenuItemNewActionPerformed

    private void MenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemExitActionPerformed
        this.setVisible(false);
        
        if (detail != null) {
            detail.setVisible(false);
        }
    }//GEN-LAST:event_MenuItemExitActionPerformed
    
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
            java.util.logging.Logger.getLogger(WindowEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowEmployee().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonEdit;
    private javax.swing.JButton ButtonExclude;
    private javax.swing.JMenuItem MenuItemExit;
    private javax.swing.JMenuItem MenuItemNew;
    private javax.swing.JRadioButton RadioDocument;
    private javax.swing.ButtonGroup RadioGroup;
    private javax.swing.JRadioButton RadioName;
    private javax.swing.JTable TableEmployee;
    private javax.swing.JTextField TextFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
