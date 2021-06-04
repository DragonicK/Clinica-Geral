package Engine.Window;

import Engine.Person;
import Engine.Employee;
import Engine.Common.Strings;
import Engine.Database.DBOperationState;
import Engine.Communication.DependencyRepository;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationEmployeeDetail;

import java.text.SimpleDateFormat;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.List;

public class WindowEmployeeDetail extends javax.swing.JFrame {
    private NotificationEmployeeDetail notification;
    private DependencyRepository dependencies;
    private DBOperationState operationState;
    private List<Person> persons;
    private int employeeId;
    private int lastPersonId;
    
    public WindowEmployeeDetail() {
        initComponents();
                
        RadioGroup.add(RadioName);
        RadioGroup.add(RadioDocument);
    }
    
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        notification = new NotificationEmployeeDetail(this);
        notification.Add(Changes.Person);
        
        dependencies.Notifier.Add(notification);
    }
    
    public void UpdateWindow() {
        Find();
    }
    
    public void Clear() {
        var empty = Strings.Empty;
        
        ComboPerson.setSelectedIndex(-1);
        ComboPerson.setSelectedItem(empty);
        
        TextFind.setText(empty);
        TextName.setText(empty);
        TextDocument.setText(empty);
        TextEmail.setText(empty);
        TextRole.setText(empty);
        TextAdmission.setText("  /  /    ");
    }
    
    public void ClearPersons() {
        ComboPerson.removeAllItems();
    }
    
    public void LoadPersons() {
        ClearPersons();
        
        var handler = dependencies.PersonHandler;
        
        if (handler != null) {
            persons = handler.GetPersons();
            FillComboPerson();
        }
    }
    
    public void SetEmployee(Employee employee) {
        var formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        employeeId = employee.Id;
        lastPersonId = employee.PersonId;
        
        var person = GetPerson(employeeId);
        
        TextName.setText(employee.Name);
        TextDocument.setText(employee.Document);
        
        if (person != null) {
            TextEmail.setText(person.Email);
        }
        else {
            TextEmail.setText(Strings.Empty);
        }
        
        TextRole.setText(employee.Role);
        TextAdmission.setText(formatter.format(employee.AdmissionDate));
    }
    
    public void SetOperationState(DBOperationState operation) {
        operationState = operation;
        
        if (operationState == DBOperationState.Insert) {
            ButtonRegister.setText("Cadastrar");
            this.setTitle("Cadastrar Novo Colaborador");
        }
        else if (operationState == DBOperationState.Update) {
            ButtonRegister.setText("Atualizar");
            this.setTitle("Editar Colaborador");
        }
    }
    
    private void FillComboPerson() {
        if (persons != null) {
            var count = persons.size();
            
            for (var i = 0; i < count; ++i) {
                ComboPerson.addItem(persons.get(i).Name);
            }
        }
    }
    
    private void Find() {
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
        FillComboPerson();
    }
    
    private Person GetPerson(int id) {
        var count = persons.size();
        
        for (var i= 0; i < count; i++) {
            var person = persons.get(i);
            
            if (person.Id == id) {
                return person;
            }
        }
        
        return null;
    }
    
    private boolean CanValidateControl() {
        if (ComboPerson.getSelectedIndex() < 0) {
            ShowErrorMessage("Uma pessoa deve ser selecionada.");
            return false;
        }
        
        if (TextRole.getText().length() == 0) {
            ShowErrorMessage("O campo cargo não pode estar vazio.");
            return false;
        }
        
        if (TextAdmission.getText().length() == 0 || TextAdmission.getText().equals("  /  /    ")) {
            ShowErrorMessage("A data de admissão não pode estar vazio.");
            return false;
        }
        
        return true;
    }
    
    private Employee CreateEmployee() {
        var formatter = new SimpleDateFormat("dd/MM/yyyy");
        var index = ComboPerson.getSelectedIndex();
        var date = TextAdmission.getText();
        var person = persons.get(index);  
        var employee = new Employee();
        
        try {
            employee.Id = employeeId;
            employee.PersonId = person.Id;
            employee.Role = TextRole.getText().trim();
            employee.AdmissionDate = formatter.parse(date);
        }
        catch (ParseException ex) {
            return null;
        }
        
        return employee;
    }
    
    private boolean CanSelect(int personId) {
        var handler = dependencies.EmployeeHandler;
        
        if (!handler.CanSelect(personId)) {
            ShowErrorMessage("Esta pessoa já está vinculada como colaborador.");
            return false;
        }
        
        return true;
    }
    
    private void ShowErrorMessage(String message) {
        MessageBox.Show(this,
                message,
                "Houve um problema",
                "Colaborador",
                MessageBoxInformation.Error
        );
    }
    
    private void ShowSuccessMessage(String message) {
        MessageBox.Show(this,
                message,
                "Sucesso",
                "Colaborador",
                MessageBoxInformation.Information
        );
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RadioGroup = new javax.swing.ButtonGroup();
        ComboPerson = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TextName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        TextDocument = new javax.swing.JTextField();
        TextEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ButtonRegister = new javax.swing.JButton();
        TextRole = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TextAdmission = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        TextFind = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        RadioDocument = new javax.swing.JRadioButton();
        RadioName = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        ComboPerson.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ComboPerson.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboPersonItemStateChanged(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Selecione uma pessoa como colaborador...");

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setText("Nome: ");

        TextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextName.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setText("CPF:");

        TextDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextDocument.setEnabled(false);

        TextEmail.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextEmail.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setText("Email: ");

        ButtonRegister.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonRegister.setText("Cadastrar");
        ButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegisterActionPerformed(evt);
            }
        });

        TextRole.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setText("Cargo:");

        try {
            TextAdmission.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TextAdmission.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Data de Admissão:");

        TextFind.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextFind.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todas as pessoas.");
        TextFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFindKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setText("Pesquisar Por:");

        RadioDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioDocument.setSelected(true);
        RadioDocument.setText("CPF");

        RadioName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioName.setText("Nome");

        jLabel9.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Pressione ENTER para iniciar a pesquisa.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RadioDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RadioName, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextRole)
                    .addComponent(TextAdmission, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(TextFind)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ComboPerson, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextName)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextDocument)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadioDocument)
                    .addComponent(RadioName)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(TextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextAdmission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ButtonRegister)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ComboPersonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPersonItemStateChanged
        var index = ComboPerson.getSelectedIndex();
        
        if (index >= 0 ) {
            var person = persons.get(index);
            
            TextName.setText(person.Name);
            TextDocument.setText(person.Document);
            TextEmail.setText(person.Email);
        }
    }//GEN-LAST:event_ComboPersonItemStateChanged

    private void ButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegisterActionPerformed
        if (CanValidateControl()) {
            var employee = CreateEmployee();
            
            if (employee == null) {
                ShowErrorMessage("Verifique a data de admissão e tente novamente.");
                return;
            }
            
            var handler = dependencies.EmployeeHandler;
            
            if (operationState == DBOperationState.Update) {
                // Se houver alteração na pessoa.
                if (lastPersonId != employee.PersonId) {
                    if (!CanSelect(employee.PersonId)) {
                        return;
                    }
                }
                
                handler.Update(employee);
                dependencies.Notifier.Notify(Changes.Employee);
                ShowSuccessMessage("Colaborador atualizado.");
            }
            
            if (operationState == DBOperationState.Insert) {
                if (CanSelect(employee.PersonId)) {
                    
                    handler.Put(employee);
                    dependencies.Notifier.Notify(Changes.Employee);
                    
                    Clear();
                    
                    ShowSuccessMessage("Colaborador cadastrado.");
                }
            }
        }
    }//GEN-LAST:event_ButtonRegisterActionPerformed

    private void TextFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFindKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Find();
        }
    }//GEN-LAST:event_TextFindKeyPressed
    
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
            java.util.logging.Logger.getLogger(WindowEmployeeDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowEmployeeDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowEmployeeDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowEmployeeDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowEmployeeDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonRegister;
    private javax.swing.JComboBox<String> ComboPerson;
    private javax.swing.JRadioButton RadioDocument;
    private javax.swing.ButtonGroup RadioGroup;
    private javax.swing.JRadioButton RadioName;
    private javax.swing.JFormattedTextField TextAdmission;
    private javax.swing.JTextField TextDocument;
    private javax.swing.JTextField TextEmail;
    private javax.swing.JTextField TextFind;
    private javax.swing.JTextField TextName;
    private javax.swing.JTextField TextRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
