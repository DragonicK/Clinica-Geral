package Engine.Window;

import Engine.Common.Strings;
import Engine.Treatment;
import Engine.Communication.DependencyRepository;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationTreatment;

import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class WindowTreatment extends javax.swing.JFrame {
    private WindowTreatmentDetail detail;
    private NotificationTreatment notification;
    private DependencyRepository dependencies;
    private List<Treatment> treatments;
    private int selectedRow;
    
    public WindowTreatment() {
        initComponents();
        
        this.setResizable(false);
        
        RadioGroup.add(RadioName);
        RadioGroup.add(RadioDocument);
        
        RadioPerson.add(RadioPatient);
        RadioPerson.add(RadioEmployee);
        RadioPerson.add(RadioDate);
    }
    
    public void UpdateWindow() {
        Find();
        SetEnabledButtons(false);
    }
    
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        notification = new NotificationTreatment(this);
        notification.Add(Changes.Person);
        notification.Add(Changes.Employee);
        notification.Add(Changes.Schedule);
        
        dependencies.Notifier.Add(notification);
    }
    
    public void ClearTable() {
        var model = (DefaultTableModel)TableTreatment.getModel();
        model.setRowCount(0);
        
        selectedRow = -1;
        
        TableTreatment.revalidate();
    }
    
    public void LoadTreatments() {
        var handler =  dependencies.TreatmentHandler;
        
        if (handler != null) {
            treatments = handler.GetTreatments();
            SetResultsText();
            FillTreatment();      
        }
    }
    
    public void SetEnabledButtons(boolean enabled) {
        ButtonEdit.setEnabled(enabled);
        ButtonExclude.setEnabled(enabled);
    }
    
    public void SetEnabledControls(boolean enabled) {
        TextFind.setEnabled(enabled);
        TableTreatment.setEnabled(enabled);
    }
    
    private void FillTreatment() {
        if (treatments != null) {
            var count = treatments.size();
            var model = (DefaultTableModel)TableTreatment.getModel();
            var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            
            for (var i = 0; i < count; i++) {
                var treatment = treatments.get(i);
                
                Object[] row = {
                    treatment.PatientName,
                    treatment.PatientDocument,
                    treatment.EmployeeName,
                    formatter.format(treatment.Date),
                    treatment.State == 0 ? "Aguardando" : "Finalizado"
                };
                
                model.addRow(row);
            }
        }
    }
    
    private void Find() {
        if (RadioPatient.isSelected()) {
            FindByPatient();
        }
        
        if (RadioEmployee.isSelected()) {
            FindByEmployee();
        }
        
        if (RadioDate.isSelected()) {
            FindByDate();
        }
        
        SetEnabledButtons(false);
        
        ClearTable();
        
        SetResultsText();
        
        FillTreatment();
    }
    
    private void SetResultsText() {
        var size = 0;
        
        if (treatments != null) {
            size = treatments.size();
        }
        
        LabelResults.setText("Resultados retornados: " + size);
    }
    
    private void FindByPatient() {
        var handler = dependencies.TreatmentHandler;
        
        if (TextFind.getText().length() > 0) {
            if (RadioName.isSelected()) {
                treatments = handler.FindByPatientName(TextFind.getText().trim());
            }
            
            if (RadioDocument.isSelected()) {
                var document = TextFind.getText().trim();
                treatments = handler.FindByPatientDocument(Strings.ConvertToDocument(document));
            }
        }
        else {
            treatments = handler.GetTreatments();
        }
    }
    
    private void FindByEmployee() {
        var handler = dependencies.TreatmentHandler;
        
        if (TextFind.getText().length() > 0) {
            if (RadioName.isSelected()) {
                treatments = handler.FindByEmployeeName(TextFind.getText().trim());
            }
            
            if (RadioDocument.isSelected()) {
                var document = TextFind.getText().trim();
                treatments = handler.FindByEmployeeDocument(Strings.ConvertToDocument(document));
            }
        }
        else {
            treatments = handler.GetTreatments();
        }
    }
    
    private void FindByDate() {
        var handler = dependencies.TreatmentHandler;
        
        if (TextFind.getText().length() > 0) {
            var date = TextFind.getText().trim();
            
            if (IsDateValid(date)) {
                treatments = handler.FindByDate(GetStartDate(date), GetEndDate(date));
            }else {
                // Remove os itens anteriores.
                if (treatments != null) {
                    treatments.clear();
                }
                
                ShowErrorMessage("O formato de data é inválido.\nUse por exemplo 27/12/2000.");
            }
        }
        else {
            treatments = handler.GetTreatments();
        }
    }
    
    private Date GetEndDate(String date) {
        // Adiciona o horário final do dia.
        date += " 23:59";
        
        var formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        try {
            return formatter.parse(date);
        }
        catch (ParseException ex) {
            return null;
        }
    }
    
    private Date GetStartDate(String date) {
        var formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        try {
            return formatter.parse(date);
        }
        catch (ParseException ex) {
            return null;
        }
    }
    
    private boolean IsDateValid(String date) {
        var formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        try {
            formatter.parse(date);
        }
        catch (ParseException ex) {
            return false;
        }
        
        return true;
    }
    
    private void ShowErrorMessage(String message) {
        MessageBox.Show(this,
                message,
                "Houve um problema",
                "Tratamento",
                MessageBoxInformation.Error
        );
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RadioPerson = new javax.swing.ButtonGroup();
        RadioGroup = new javax.swing.ButtonGroup();
        ButtonEdit = new javax.swing.JButton();
        ButtonExclude = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TableTreatment = new javax.swing.JTable();
        TextFind = new javax.swing.JTextField();
        RadioPatient = new javax.swing.JRadioButton();
        RadioDocument = new javax.swing.JRadioButton();
        RadioName = new javax.swing.JRadioButton();
        RadioEmployee = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        RadioDate = new javax.swing.JRadioButton();
        LabelResults = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuItemNew = new javax.swing.JMenuItem();
        MenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tratamento");

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

        TableTreatment.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TableTreatment.setModel(new javax.swing.table.DefaultTableModel(
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
        TableTreatment.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TableTreatment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableTreatmentMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TableTreatment);

        TextFind.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextFind.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todas as pessoas.");
        TextFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFindKeyPressed(evt);
            }
        });

        RadioPatient.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioPatient.setSelected(true);
        RadioPatient.setText("Paciente");

        RadioDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioDocument.setSelected(true);
        RadioDocument.setText("CPF");

        RadioName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioName.setText("Nome");

        RadioEmployee.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioEmployee.setText("Colaborador");

        jLabel2.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Pressione ENTER para iniciar a pesquisa.");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setText("Pesquisar por:");

        RadioDate.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioDate.setText("Data");

        LabelResults.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        LabelResults.setText("Resultados retornados: 0");

        jMenu1.setText("Arquivo");
        jMenu1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        MenuItemNew.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemNew.setText("Novo Agendamento");
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
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(RadioEmployee)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RadioDate)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(LabelResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(RadioEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(RadioDate, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(RadioPatient))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelResults)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonExclude, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditActionPerformed
//        if (selectedRow >= 0) {
//            var schedule = schedules.get(selectedRow);
//
//            if (detail == null) {
//                detail = new WindowScheduleDetail();
//                detail.SetDependency(dependencies);
//                detail.setLocationRelativeTo(null);
//            }
//
//            if (!detail.isVisible()) {
//                detail.Clear();
//                detail.SetOperationState(DBOperationState.Update);
//                detail.LoadPatients();
//                detail.LoadEmployees();
//                detail.SetSchedule(schedule);
//                detail.setVisible(true);
//            }
//        }
    }//GEN-LAST:event_ButtonEditActionPerformed

    private void ButtonExcludeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonExcludeActionPerformed
        if (selectedRow >= 0) {
            var handler = dependencies.TreatmentHandler;
            var treatment = treatments.get(selectedRow);
            
            handler.Delete(treatment);
            
            treatments.remove(treatment);
            selectedRow = -1;
            
            // Notifica todos os controles que o tratamento foi alterado.
            dependencies.Notifier.Notify(Changes.Treatment);
        }
    }//GEN-LAST:event_ButtonExcludeActionPerformed

    private void TableTreatmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableTreatmentMouseClicked
        var source = (JTable)evt.getSource();
        int row = source.rowAtPoint( evt.getPoint() );
        
        SetEnabledButtons(false);
        
        selectedRow = -1;
        
        if (row >= 0 && treatments.size() > 0) {
            SetEnabledButtons(true);
            selectedRow = row;
        }
    }//GEN-LAST:event_TableTreatmentMouseClicked

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
//        if (detail == null) {
//            detail = new WindowScheduleDetail();
//            detail.SetDependency(dependencies);
//            detail.setLocationRelativeTo(null);
//        }
//
//        if (!detail.isVisible()) {
//            detail.Clear();
//            detail.SetOperationState(DBOperationState.Insert);
//            detail.LoadPatients();
//            detail.LoadEmployees();
//            detail.setVisible(true);
//        }
    }//GEN-LAST:event_MenuItemNewActionPerformed

    private void MenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemExitActionPerformed
        this.setVisible(false);
        
        if (detail != null) {
            detail.setVisible(false);
        }
    }//GEN-LAST:event_MenuItemExitActionPerformed
    
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
            java.util.logging.Logger.getLogger(WindowTreatment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowTreatment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowTreatment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowTreatment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowTreatment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonEdit;
    private javax.swing.JButton ButtonExclude;
    private javax.swing.JLabel LabelResults;
    private javax.swing.JMenuItem MenuItemExit;
    private javax.swing.JMenuItem MenuItemNew;
    private javax.swing.JRadioButton RadioDate;
    private javax.swing.JRadioButton RadioDocument;
    private javax.swing.JRadioButton RadioEmployee;
    private javax.swing.ButtonGroup RadioGroup;
    private javax.swing.JRadioButton RadioName;
    private javax.swing.JRadioButton RadioPatient;
    private javax.swing.ButtonGroup RadioPerson;
    private javax.swing.JTable TableTreatment;
    private javax.swing.JTextField TextFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
