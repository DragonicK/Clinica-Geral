package Engine.Window;

import Engine.Schedule;
import Engine.Common.Strings;
import Engine.Database.DBOperationState;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationSchedule;
import Engine.Communication.DependencyRepository;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.text.SimpleDateFormat;
import java.awt.event.KeyEvent;
import java.util.List;

public class WindowSchedule extends javax.swing.JFrame {
    private DependencyRepository dependencies;
    private NotificationSchedule notification;
    private WindowScheduleDetail detail;
    private WindowTreatmentDetail treatmentDetail;
    private List<Schedule> schedules;
    private int selectedRow;
    
    public WindowSchedule() {
        initComponents();
        
        this.setResizable(false);
        
        RadioParam.add(RadioDocument);
        RadioParam.add(RadioName);
        
        RadioPerson.add(RadioPatient);
        RadioPerson.add(RadioEmployee);
        
        SetEnabledButtons(false);
        ButtonFinish.setEnabled(false);
    }
    
    public void SetDependency(DependencyRepository repository) {
        dependencies = repository;
        
        notification = new NotificationSchedule(this);
        notification.Add(Changes.Person);
        notification.Add(Changes.Patient);
        notification.Add(Changes.Employee);
        notification.Add(Changes.Schedule);
        
        dependencies.Notifier.Add(notification);
    }
    
    public void ClearTable() {
        var model = (DefaultTableModel)TableSchedule.getModel();
        model.setRowCount(0);
        
        selectedRow = -1;
        
        TableSchedule.revalidate();
    }
    
    public void LoadSchedules() {
        var handler = dependencies.ScheduleHandler;
        
        if (handler != null) {
            schedules = handler.GetSchedules();
            SetResultsText();
            FillSchedule();
        }
    }
    
    public void UpdateWindow() {
        Find();
        SetEnabledControls(true);
    }
    
    private void FillSchedule() {
        if (schedules != null) {
            var count = schedules.size();
            var model = (DefaultTableModel)TableSchedule.getModel();
            var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            
            for (var i = 0; i < count; i++) {
                var schedule = schedules.get(i);
                
                Object[] row = {
                    schedule.PatientName,
                    schedule.PatientDocument,
                    schedule.EmployeeName,
                    formatter.format(schedule.Date),
                    schedule.State == 0 ? "Aguardando" : "Finalizado"
                };
                
                model.addRow(row);
            }
        }
    }
    
    public void SetEnabledControls(boolean enabled) {
        TextFind.setEnabled(enabled);
        TableSchedule.setEnabled(enabled);
    }
    
    public void SetEnabledButtons(boolean enabled) {
        ButtonEdit.setEnabled(enabled);
        ButtonExclude.setEnabled(enabled);
    }
    
    private void Find() {
        if (RadioPatient.isSelected()){
            FindByPatient();
        }
        else if (RadioEmployee.isSelected()) {
            FindByEmployee();
        }
        
        SetEnabledButtons(false);
        ButtonFinish.setEnabled(false);
        
        ClearTable();
        
        SetResultsText();
        
        FillSchedule();
    }
    
    private void SetResultsText() {
        var size = 0;
        
        if (schedules != null) {
            size = schedules.size();
        }
        
        LabelResults.setText("Resultados retornados: " + size);
    }
    
    private void FindByPatient() {
        var handler = dependencies.ScheduleHandler;
        
        if (TextFind.getText().length() > 0) {
            if (RadioName.isSelected()) {
                schedules = handler.FindByPatientName(TextFind.getText().trim());
            }
            
            if (RadioDocument.isSelected()) {
                var document = TextFind.getText().trim();
                schedules = handler.FindByPatientDocument(Strings.ConvertToDocument(document));
            }
        }
        else {
            schedules = handler.GetSchedules();
        }
    }
    
    private void FindByEmployee() {
        var handler = dependencies.ScheduleHandler;
        
        if (TextFind.getText().length() > 0) {
            if (RadioName.isSelected()) {
                schedules = handler.FindByEmployeeName(TextFind.getText().trim());
            }
            
            if (RadioDocument.isSelected()) {
                var document = TextFind.getText().trim();
                schedules = handler.FindByEmployeeDocument(Strings.ConvertToDocument(document));
            }
        }
        else {
            schedules = handler.GetSchedules();
        }
    }
    
    private void ShowErrorMessage(String message) {
        MessageBox.Show(this,
                message,
                "Houve um problema",
                "Agendamento",
                MessageBoxInformation.Error
        );
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RadioParam = new javax.swing.ButtonGroup();
        RadioPerson = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableSchedule = new javax.swing.JTable();
        ButtonEdit = new javax.swing.JButton();
        ButtonExclude = new javax.swing.JButton();
        TextFind = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        RadioDocument = new javax.swing.JRadioButton();
        RadioName = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        RadioPatient = new javax.swing.JRadioButton();
        RadioEmployee = new javax.swing.JRadioButton();
        LabelResults = new javax.swing.JLabel();
        ButtonFinish = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuItemNew = new javax.swing.JMenuItem();
        MenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta");
        setResizable(false);

        TableSchedule.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TableSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "CPF", "Colaborador", "Data", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableSchedule.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TableSchedule.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableScheduleMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableSchedule);

        ButtonEdit.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonEdit.setText("Editar");
        ButtonEdit.setEnabled(false);
        ButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditActionPerformed(evt);
            }
        });

        ButtonExclude.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonExclude.setText("Excluir");
        ButtonExclude.setEnabled(false);
        ButtonExclude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonExcludeActionPerformed(evt);
            }
        });

        TextFind.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextFind.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todas as pessoas.");
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

        RadioPatient.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioPatient.setSelected(true);
        RadioPatient.setText("Paciente");

        RadioEmployee.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioEmployee.setText("Colaborador");

        LabelResults.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        LabelResults.setText("Resultados retornados: 0");

        ButtonFinish.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonFinish.setText("Finalizar Consulta");
        ButtonFinish.setEnabled(false);
        ButtonFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonFinishActionPerformed(evt);
            }
        });

        jMenu1.setText("Arquivo");
        jMenu1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        MenuItemNew.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemNew.setText("Nova Consulta");
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ButtonFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonExclude, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                    .addComponent(TextFind)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(RadioPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RadioDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(RadioName, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))
                            .addComponent(RadioEmployee))))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(LabelResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(20, 20, 20)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(RadioDocument)
                    .addComponent(RadioName))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RadioEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RadioPatient))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonExclude, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(101, 101, 101)
                    .addComponent(LabelResults)
                    .addContainerGap(413, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableScheduleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableScheduleMouseClicked
        var source = (JTable)evt.getSource();
        int row = source.rowAtPoint( evt.getPoint() );
        
        SetEnabledButtons(false);
        ButtonFinish.setEnabled(false);
        
        selectedRow = -1;
        
        if (row >= 0 && schedules.size() > 0) {
            SetEnabledButtons(true);
            selectedRow = row;
            
            var schedule = schedules.get(selectedRow);
            
            // ScheduleState.Waiting
            if (schedule.State == 0) {
                ButtonFinish.setEnabled(true);
            }
        }
    }//GEN-LAST:event_TableScheduleMouseClicked

    private void ButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditActionPerformed
        if (selectedRow >= 0) {
            var schedule = schedules.get(selectedRow);
            
            if (detail == null) {
                detail = new WindowScheduleDetail();
                detail.SetDependency(dependencies);
                detail.setLocationRelativeTo(null);
            }
            
            if (!detail.isVisible()) {
                detail.Clear();
                detail.SetOperationState(DBOperationState.Update);
                detail.LoadPatients();
                detail.LoadEmployees();
                detail.SetSchedule(schedule);
                detail.setVisible(true);
            }
        }
    }//GEN-LAST:event_ButtonEditActionPerformed

    private void ButtonExcludeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonExcludeActionPerformed
        if (selectedRow >= 0) {
            var handler = dependencies.ScheduleHandler;
            var schedule = schedules.get(selectedRow);
            
            if (handler.CanDelete(schedule)) {
                handler.Delete(schedule);
                
                schedules.remove(schedule);
                selectedRow = -1;
                
                // Notifica todos os controles que o agendamento foi alterado.
                dependencies.Notifier.Notify(Changes.Schedule);
            }
            else {
                ShowErrorMessage("Esta consulta está vinculado com um tratamento finalizado e não pode ser excluído.");
            }
        }
    }//GEN-LAST:event_ButtonExcludeActionPerformed

    private void TextFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFindKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Fecha a janela por segurança.
            if (detail != null) {
                detail.setVisible(false);
            }
            
            Find();
        }
    }//GEN-LAST:event_TextFindKeyPressed

    private void MenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemExitActionPerformed
        this.setVisible(false);
        
        if (detail != null) {
            detail.setVisible(false);
        }
    }//GEN-LAST:event_MenuItemExitActionPerformed

    private void MenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemNewActionPerformed
        if (detail == null) {
            detail = new WindowScheduleDetail();
            detail.SetDependency(dependencies);
            detail.setLocationRelativeTo(null);
        }
        
        if (!detail.isVisible()) {
            detail.Clear();
            detail.SetOperationState(DBOperationState.Insert);
            detail.LoadPatients();
            detail.LoadEmployees();
            detail.setVisible(true);
        }
    }//GEN-LAST:event_MenuItemNewActionPerformed

    private void ButtonFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonFinishActionPerformed
        if (selectedRow >= 0){
            var schedule = schedules.get(selectedRow);
            
            if (treatmentDetail == null) {
                treatmentDetail = new WindowTreatmentDetail();
                treatmentDetail.SetDependency(dependencies);
                treatmentDetail.setLocationRelativeTo(null);
            }
            
            if (!treatmentDetail.isVisible()) {
                treatmentDetail.Clear();
                treatmentDetail.SetOperationState(DBOperationState.Insert);
                treatmentDetail.SetSchedule(schedule);
                treatmentDetail.setVisible(true);
            }
        }
    }//GEN-LAST:event_ButtonFinishActionPerformed
    
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
            java.util.logging.Logger.getLogger(WindowSchedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowSchedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowSchedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowSchedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowSchedule().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonEdit;
    private javax.swing.JButton ButtonExclude;
    private javax.swing.JButton ButtonFinish;
    private javax.swing.JLabel LabelResults;
    private javax.swing.JMenuItem MenuItemExit;
    private javax.swing.JMenuItem MenuItemNew;
    private javax.swing.JRadioButton RadioDocument;
    private javax.swing.JRadioButton RadioEmployee;
    private javax.swing.JRadioButton RadioName;
    private javax.swing.ButtonGroup RadioParam;
    private javax.swing.JRadioButton RadioPatient;
    private javax.swing.ButtonGroup RadioPerson;
    private javax.swing.JTable TableSchedule;
    private javax.swing.JTextField TextFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
