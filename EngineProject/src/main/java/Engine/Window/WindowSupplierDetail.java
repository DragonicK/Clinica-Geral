package Engine.Window;

import Engine.Person;
import Engine.Supplier;
import Engine.Common.Strings;
import Engine.Database.DBOperationState;
import Engine.Communication.DependencyRepository;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationSupplierDetail;

import java.awt.event.KeyEvent;
import java.util.List;

public class WindowSupplierDetail extends javax.swing.JDialog  {
    private NotificationSupplierDetail notification;
    private DependencyRepository dependencies;
    private DBOperationState operationState;
    private List<Person> persons;
    private int supplierId;
    // Ultima pessoa selecionada para este fornecedor.
    // Antes da alteração.
    private int lastPersonId;
    
    public WindowSupplierDetail() {
        initComponents();
        this.setModal(true);
            
        RadioGroup.add(RadioDocument);
        RadioGroup.add(RadioName);
    }
    
    public void UpdateWindow() {
        
    }
    
    // As dependências são injetadas.
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        notification = new NotificationSupplierDetail(this);
        notification.Add(Changes.Person);
        
        dependencies.Notifier.Add(notification);
    }
    
    // Atividade atual do formulário, inserir ou atualizar.
    public void SetOperationState(DBOperationState operation) {
        operationState = operation;
        
        if (operationState == DBOperationState.Insert) {
            ButtonRegister.setText("Cadastrar");
            this.setTitle("Cadastrar Novo Fornecedor");
        }
        else if (operationState == DBOperationState.Update) {
            ButtonRegister.setText("Atualizar");
            this.setTitle("Editar Fornecedor");
        }
    }
        
    public void SetSupplier(Supplier supplier) {
        supplierId = supplier.Id;
        lastPersonId = supplier.PersonId;
        
        SelectPersonInCombo(supplier.PersonId);
        var person = GetPerson(supplier.PersonId);
        
        if (person != null) {
            TextName.setText(person.Name);
            TextDocument.setText(person.Document);
            TextEmail.setText(person.Email);
        }
        
        TextFantasyName.setText(supplier.FantasyName);
    }
    
    public void Clear() {
        TextFind.setText(Strings.Empty);
        TextName.setText(Strings.Empty);
        TextDocument.setText(Strings.Empty);
        TextEmail.setText(Strings.Empty);
        TextFantasyName.setText(Strings.Empty);
        
        ComboPerson.setSelectedIndex(-1);
        ComboPerson.setSelectedItem(Strings.Empty);
    }
    
    public void LoadPersons() {
        var handler = dependencies.PersonHandler;
        
        if (handler != null) {
            persons = handler.GetPersons();
            FillComboPerson();
        }
    }
    
    public void ClearPersons() {
        ComboPerson.setSelectedIndex(-1);
        ComboPerson.setSelectedItem(Strings.Empty);
        ComboPerson.removeAllItems();
    }
    
    private void FillComboPerson() {
        if (persons != null) {
            
            var count = persons.size();
            
            for (var i = 0; i < count; ++i) {
                ComboPerson.addItem(persons.get(i).Name);
            }
        }
    }
    
    private void SelectPersonInCombo(int personId) {
        var index = GetPersonIndex(personId);
        
        if (index >= 0) {
            ComboPerson.setSelectedIndex(index);
        }
    }
    
    private Person GetPerson(int personId) {
        var index = GetPersonIndex(personId);
        
        if (index >= 0) {
            return persons.get(index);
        }
        
        return null;
    }
    
    private int GetPersonIndex(int personId) {
        var count = persons.size();
        
        for (var i = 0; i < count; ++i) {
            var person = persons.get(i);
            
            if (person.Id == personId) {
                return i;
            }
        }
        
        return -1;
    }
    
    private boolean CanValidateControl() {
        if (ComboPerson.getSelectedIndex() < 0) {
            ShowErrorMessage("Uma pessoa deve ser selecionada.");
            return false;
        }
        
        return true;
    }
    
    private Supplier CreateSupplier() {
        var person = persons.get(ComboPerson.getSelectedIndex());
        var supplier = new Supplier();
        
        supplier.Id = supplierId;
        supplier.FantasyName = TextFantasyName.getText().trim();
        supplier.PersonId = person.Id;
        
        return supplier;
    }
    
    private boolean IsPersonAlreadyUsed(int personId) {
        var handler = dependencies.SupplierHandler;
        
        // Se a pessoa já existe para um fornecedor.
        if (!handler.CanSelectPerson(personId)) {
            ShowErrorMessage("Esta pessoa já está vinculada à outro fornecedor.");
            return true;
        }
        
        return false;
    }
    
    private void ShowSuccessMessage(String message) {
        MessageBox.Show(this,
                message,
                "Sucesso",
                "Fornecedor",
                MessageBoxInformation.Information
        );
    }
    
    private void ShowErrorMessage(String message) {
        MessageBox.Show(this,
                message,
                "Houve um problema",
                "Fornecedor",
                MessageBoxInformation.Error
        );
    }
    
    private void FillPerson(Person person) {
        if (person != null) {
            TextName.setText(person.Name);
            TextDocument.setText(person.Document);
            TextEmail.setText(person.Email);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RadioGroup = new javax.swing.ButtonGroup();
        ComboPerson = new javax.swing.JComboBox<>();
        ButtonRegister = new javax.swing.JButton();
        TextDocument = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TextFind = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TextFantasyName = new javax.swing.JTextField();
        TextName = new javax.swing.JTextField();
        TextEmail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        RadioDocument = new javax.swing.JRadioButton();
        RadioName = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Fornecedor");
        setResizable(false);

        ComboPerson.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ComboPerson.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboPersonItemStateChanged(evt);
            }
        });

        ButtonRegister.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonRegister.setText("Cadastrar");
        ButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegisterActionPerformed(evt);
            }
        });

        TextDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextDocument.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setText("Pesquisar Por:");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setText("CPF:");

        TextFind.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextFind.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todas as pessoas.");
        TextFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFindKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setText("Nome Fantasia:");

        TextFantasyName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        TextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextName.setEnabled(false);

        TextEmail.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextEmail.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setText("Email: ");

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setText("Nome: ");

        jLabel6.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Selecione uma pessoa como fornecedor ...");

        RadioDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioDocument.setSelected(true);
        RadioDocument.setText("CPF");

        RadioName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioName.setText("Nome");

        jLabel7.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Pressione ENTER para iniciar a pesquisa.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(ComboPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextName, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TextFantasyName, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(ButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(67, 67, 67))
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(TextFind, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(RadioDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(RadioName, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(RadioDocument)
                    .addComponent(RadioName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextFantasyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ButtonRegister)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegisterActionPerformed
        if (CanValidateControl()) {
            
            var supplier = CreateSupplier();
            var handler = dependencies.SupplierHandler;
            
            if (operationState == DBOperationState.Update) {
                // Se houve alteração na pessoa.
                if (lastPersonId != supplier.PersonId) {
                    if (IsPersonAlreadyUsed(supplier.PersonId)) {
                        return;
                    }
                }
                
                handler.Update(supplier);
                dependencies.Notifier.Notify(Changes.Supplier);
                ShowSuccessMessage("Fornecedor atualizado.");
            }
            
            if (operationState == DBOperationState.Insert) {
                if (!IsPersonAlreadyUsed(supplier.PersonId)) {
                    
                    handler.Put(supplier);
                    dependencies.Notifier.Notify(Changes.Supplier);
                    
                    Clear();
                    
                    ShowSuccessMessage("Fornecedor cadastrado.");
                }
            }
        }   
    }//GEN-LAST:event_ButtonRegisterActionPerformed

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
            
            FillComboPerson();
        }
    }//GEN-LAST:event_TextFindKeyPressed

    private void ComboPersonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboPersonItemStateChanged
        var index = ComboPerson.getSelectedIndex();
        
        if (index >= 0) {
            FillPerson(persons.get(index));
        }
    }//GEN-LAST:event_ComboPersonItemStateChanged
    
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
            java.util.logging.Logger.getLogger(WindowSupplierDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowSupplierDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowSupplierDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowSupplierDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowSupplierDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonRegister;
    private javax.swing.JComboBox<String> ComboPerson;
    private javax.swing.JRadioButton RadioDocument;
    private javax.swing.ButtonGroup RadioGroup;
    private javax.swing.JRadioButton RadioName;
    private javax.swing.JTextField TextDocument;
    private javax.swing.JTextField TextEmail;
    private javax.swing.JTextField TextFantasyName;
    private javax.swing.JTextField TextFind;
    private javax.swing.JTextField TextName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}