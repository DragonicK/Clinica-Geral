package Engine.Window;

import Engine.Patient;
import Engine.Person;
import Engine.Common.Strings;
import Engine.Communication.DependencyRepository;
import Engine.Database.DBOperationState;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationPatientDetail;

import java.awt.event.KeyEvent;
import java.util.List;

public class WindowPatientDetail extends javax.swing.JDialog {
    private NotificationPatientDetail notification;
    private DependencyRepository dependencies;
    private DBOperationState operationState;
    private List<Person> persons;
    
    private int patientId;
    private int lastPatientPersonId;
    private int lastCompanionPersonId;
    
    public WindowPatientDetail() {
        initComponents();     
        this.setModal(true);
        this.setResizable(false);
        
        RadioGroup.add(RadioName);
        RadioGroup.add(RadioDocument);
    }
    
    public void UpdateWindow() {
        ClearPersons();
        LoadPersons();
    }
    
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        notification = new NotificationPatientDetail(this);
        notification.Add(Changes.Person);
        
        dependencies.Notifier.Add(notification);
    }
    
    // Atividade atual do formulário, inserir ou atualizar.
    public void SetOperationState(DBOperationState operation) {
        operationState = operation;
        
        if (operation == DBOperationState.Insert) {
            ButtonRegister.setText("Cadastrar");
            this.setTitle("Cadastrar Novo Paciente");
        }
        else if  (operation == DBOperationState.Update) {
            ButtonRegister.setText("Atualizar");
            this.setTitle("Editar Paciente");
        }
    }
    
    public void SetPatient(Patient patient) {
        patientId = patient.Id;
        lastPatientPersonId = patient.PersonId;
        lastCompanionPersonId = patient.CompanionPersonId;
        
        if (lastPatientPersonId > 0 || lastCompanionPersonId > 0) {
            var count = persons.size();
            
            for (var i = 0; i < count; i++) {
                var patientId = persons.get(i).Id;
                
                if (lastPatientPersonId > 0) {
                    if (lastPatientPersonId == patientId) {
                        ComboPerson.setSelectedIndex(i);
                    }
                }
                
                if (lastCompanionPersonId > 0) {
                    if (lastCompanionPersonId == patientId) {
                        ComboCompanion.setSelectedIndex(i);
                    }
                }
            }
        }
    }
    
    public void Clear() {
        var empty = Strings.Empty;
        
        ComboPerson.setSelectedIndex(-1);
        ComboPerson.setSelectedItem(empty);
        ComboCompanion.setSelectedIndex(-1);
        ComboCompanion.setSelectedItem(empty);
        
        TextFind.setText(empty);
        PatientTextName.setText(empty);
        PatientTextDocument.setText(empty);
        PatientTextEmail.setText(empty);
        CompanionTextName.setText(empty);
        CompanionTextDocument.setText(empty);
        CompanionTextEmail.setText(empty);
    }
    
    public void ClearPersons() {
        ComboPerson.removeAllItems();
        ComboCompanion.removeAllItems();
    }
    
    public void LoadPersons() {
        var handler = dependencies.PersonHandler;
        
        if (handler != null) {
            persons = handler.GetPersons();
            FillPersons();
        }
    }
    
    private void FillPersons() {
        ClearPersons();
        
        if (persons != null) {
            var count = persons.size();
            
            for (var i = 0; i < count; ++i) {
                var name = persons.get(i).Name;
                
                ComboPerson.addItem(name);
                ComboCompanion.addItem(name);
            }
        }
    }
    
    private boolean CanValidateControl() {
        var index = ComboPerson.getSelectedIndex();
        
        if (index < 0) {
            ShowErrorMessage("Um paciente deve ser selecionado.");
            return false;
        }
        
        index = ComboCompanion.getSelectedIndex();
        
        if (index < 0) {
            ShowErrorMessage("Um acompanhante deve ser selecionado.");
            return false;
        }
        
        return true;
    }
    
    private Patient CreatePatient() {
        var index = ComboPerson.getSelectedIndex();
        var person = persons.get(index);     
        var patient = new Patient();     
        patient.Id = patientId;
        
        patient.PersonId = person.Id;
        
        index = ComboCompanion.getSelectedIndex();
        person = persons.get(index);
        
        patient.CompanionPersonId = person.Id;
        
        return patient;
    }
    
    private void ShowSuccessMessage(String message) {
        MessageBox.Show(this,
                message,
                "Sucesso",
                "Paciente",
                MessageBoxInformation.Information
        );
    }
    
    private void ShowErrorMessage(String message) {
        MessageBox.Show(this,
                message,
                "Houve um problema",
                "Paciente",
                MessageBoxInformation.Error
        );
    }
    
    private boolean CanSelect(int personId) {
        var handler = dependencies.PatientHandler;
        
        if (!handler.CanSelect(personId)) {
            ShowErrorMessage("Esta pessoa já foi definida como paciente.");
            return false;
        }
        
        return true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RadioGroup = new javax.swing.ButtonGroup();
        PatientTextEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        PatientTextDocument = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        PatientTextName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        CompanionTextDocument = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        CompanionTextName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        CompanionTextEmail = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        TextFind = new javax.swing.JTextField();
        RadioName = new javax.swing.JRadioButton();
        RadioDocument = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ButtonRegister = new javax.swing.JButton();
        ComboPerson = new javax.swing.JComboBox<>();
        ComboCompanion = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        PatientTextEmail.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PatientTextEmail.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setText("Email: ");

        PatientTextDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PatientTextDocument.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setText("CPF:");

        PatientTextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        PatientTextName.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setText("Nome: ");

        jLabel6.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Selecione uma pessoa como paciente ...");

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setText("Email: ");

        CompanionTextDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        CompanionTextDocument.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setText("CPF:");

        CompanionTextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        CompanionTextName.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setText("Nome: ");

        jLabel9.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Selecione uma pessoa como acompanhante ...");

        CompanionTextEmail.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        CompanionTextEmail.setEnabled(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TextFind.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextFind.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todas as pessoas.");
        TextFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFindKeyPressed(evt);
            }
        });

        RadioName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioName.setText("Nome");

        RadioDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioDocument.setSelected(true);
        RadioDocument.setText("CPF");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setText("Pesquisar Por:");

        jLabel10.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Pressione ENTER para iniciar a pesquisa.");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(125, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87)
                        .addComponent(RadioDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(RadioName, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(RadioDocument)
                    .addComponent(RadioName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        ButtonRegister.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonRegister.setText("Cadastrar");
        ButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegisterActionPerformed(evt);
            }
        });

        ComboPerson.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ComboPerson.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboPersonItemStateChanged(evt);
            }
        });

        ComboCompanion.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ComboCompanion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboCompanionItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ComboPerson, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PatientTextName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PatientTextDocument, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PatientTextEmail, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CompanionTextName)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CompanionTextDocument)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CompanionTextEmail)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 275, Short.MAX_VALUE)
                                    .addComponent(ComboCompanion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(ButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboCompanion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addComponent(PatientTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PatientTextDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PatientTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(5, 5, 5)
                        .addComponent(CompanionTextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CompanionTextDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CompanionTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(ButtonRegister)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TextFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFindKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            
            var handler = dependencies.PersonHandler;
            
            if (TextFind.getText().length() > 0) {
                if (RadioName.isSelected()) {
                    persons = handler.FindByName(TextFind.getText().trim());
                }
                
                if (RadioDocument.isSelected()) {
                    var document = TextFind.getText().trim();
                    persons = handler.FindByDocument(Strings.ConvertToDocument(document));
                }
            }
            else {
                persons = handler.GetPersons();
            }
            
            ClearPersons();
            
            FillPersons();
        }
    }//GEN-LAST:event_TextFindKeyPressed

    private void ButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegisterActionPerformed
        if (CanValidateControl()) {
            
            var patient = CreatePatient();
            var handler = dependencies.PatientHandler;
            
            if (operationState == DBOperationState.Update) {
                // Se houver alteração na pessoa.
                if (lastPatientPersonId != patient.PersonId) {
                    if (!CanSelect(patient.PersonId)) {
                        return;
                    }
                }
                
                handler.Update(patient);
                dependencies.Notifier.Notify(Changes.Patient);
                ShowSuccessMessage("Paciente atualizado.");
            }
            
            if (operationState == DBOperationState.Insert) {
                if (CanSelect(patient.PersonId)) {

                    handler.Put(patient);
                    dependencies.Notifier.Notify(Changes.Patient);
                    
                    Clear();
                    
                    ShowSuccessMessage("Paciente cadastrado.");
                }
            }
        }
    }//GEN-LAST:event_ButtonRegisterActionPerformed

    private void ComboPersonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPersonItemStateChanged
        var index = ComboPerson.getSelectedIndex();
        
        if (index >= 0 ) {
            var patient = persons.get(index);
            
            PatientTextName.setText(patient.Name);
            PatientTextDocument.setText(patient.Document);
            PatientTextEmail.setText(patient.Email);
        }
    }//GEN-LAST:event_ComboPersonItemStateChanged

    private void ComboCompanionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboCompanionItemStateChanged
        var index = ComboCompanion.getSelectedIndex();
        
        if (index >= 0 ) {
            var companion = persons.get(index);
            
            CompanionTextName.setText(companion.Name);
            CompanionTextDocument.setText(companion.Document);
            CompanionTextEmail.setText(companion.Email);
        }
    }//GEN-LAST:event_ComboCompanionItemStateChanged
    
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
    java.util.logging.Logger.getLogger(WindowPatientDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (InstantiationException ex) {
    java.util.logging.Logger.getLogger(WindowPatientDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (IllegalAccessException ex) {
    java.util.logging.Logger.getLogger(WindowPatientDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
} catch (javax.swing.UnsupportedLookAndFeelException ex) {
    java.util.logging.Logger.getLogger(WindowPatientDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
}
//</editor-fold>

/* Create and display the form */
java.awt.EventQueue.invokeLater(new Runnable() {
    public void run() {
        new WindowPatientDetail().setVisible(true);
    }
});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonRegister;
    private javax.swing.JComboBox<String> ComboCompanion;
    private javax.swing.JComboBox<String> ComboPerson;
    private javax.swing.JTextField CompanionTextDocument;
    private javax.swing.JTextField CompanionTextEmail;
    private javax.swing.JTextField CompanionTextName;
    private javax.swing.JTextField PatientTextDocument;
    private javax.swing.JTextField PatientTextEmail;
    private javax.swing.JTextField PatientTextName;
    private javax.swing.JRadioButton RadioDocument;
    private javax.swing.ButtonGroup RadioGroup;
    private javax.swing.JRadioButton RadioName;
    private javax.swing.JTextField TextFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
