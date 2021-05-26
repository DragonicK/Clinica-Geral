package Engine.Window;

import Engine.Person;
import Engine.Common.Strings;
import Engine.Communication.DependencyRepository;
import Engine.Database.DBOperationState;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationPerson;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.awt.event.KeyEvent;
import java.util.List;
import java.text.SimpleDateFormat;

public class WindowPerson extends javax.swing.JFrame {
    // Componente de notificação da janela.
    private NotificationPerson notification;
    private DependencyRepository dependencies;
    private WindowPersonDetail detail;
    // Lista de pessoas.
    private List<Person> persons;
    // Linha selecionada na tabela.
    private int selectedRow;
    
    public WindowPerson() {
        initComponents();
        
        RadioGroup.add(RadioDocument);
        RadioGroup.add(RadioName);
        
        SetEnabledButtons(false);
    }
    
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        // A janela somente é atualizada,
        // se alguma alteração em Pessoa ocorrer.
        notification = new NotificationPerson(this);
        notification.Add(Changes.Person);
        
        dependencies.Notifier.Add(notification);
    }
    
    public void ClearTable() {
        var model = (DefaultTableModel)TablePerson.getModel();
        model.setRowCount(0);
        
        selectedRow = -1;
        
        TablePerson.revalidate();
    }
    
    public void LoadPersons() {
        var handler = dependencies.PersonHandler;
        
        if (handler != null) {
            persons = handler.GetPersons();
            FillPersons(persons);
        }
    }
    
    public void FillPersons(List<Person> list) {
        var count = list.size();
        var model = (DefaultTableModel)TablePerson.getModel();
        var formatter = new SimpleDateFormat("dd-MM-yyyy");
        
        for (var i = 0; i < count; i++) {
            var person = list.get(i);
            
            Object[] row = {
                person.Name,
                person.Document,
                person.Email,
                formatter.format(person.Birthday)
            };
            
            model.addRow(row);
        }
    }
    
    public void SetEnabledControls(boolean enabled) {
        TextFind.setEnabled(enabled);
        TablePerson.setEnabled(enabled);
    }
    
    public void SetEnabledButtons(boolean enabled) {
        ButtonEdit.setEnabled(enabled);
        ButtonExclude.setEnabled(enabled);
    }
    
    public void UpdateWindow() {
        Find();
        SetEnabledControls(true);
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
        
        SetEnabledButtons(false);
        
        ClearTable();
        
        FillPersons(persons);
    }
    
    private void ShowMessage(String message) {
        JOptionPane.showMessageDialog(this,
                "Houve um problema:\n\n" + message,
                "Pessoa",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RadioGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        TextFind = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablePerson = new javax.swing.JTable();
        ButtonEdit = new javax.swing.JButton();
        ButtonExclude = new javax.swing.JButton();
        RadioDocument = new javax.swing.JRadioButton();
        RadioName = new javax.swing.JRadioButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        MenuItemNew = new javax.swing.JMenuItem();
        MenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Pessoas");

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setText("Pesquisar por:");

        TextFind.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextFind.setToolTipText("Quando a pesquisa é feita com o campo em branco, irá retornar todas as pessoas.");
        TextFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextFindKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Verdana", 2, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Pressione ENTER para iniciar a pesquisa.");

        TablePerson.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TablePerson.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "CPF", "Email", "Data Nascimento"
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
        TablePerson.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TablePerson.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablePersonMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablePerson);

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

        RadioDocument.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioDocument.setSelected(true);
        RadioDocument.setText("CPF");

        RadioName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        RadioName.setText("Nome");

        jMenu1.setText("Arquivo");
        jMenu1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        MenuItemNew.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        MenuItemNew.setText("Nova Pessoa");
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
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ButtonExclude, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                    .addComponent(TextFind)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RadioDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RadioName, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(RadioDocument)
                    .addComponent(RadioName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TextFind, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonExclude, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TextFindKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFindKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Fecha a janela por segurança.
            if (detail != null) {
                detail.setVisible(false);
            }
            
            Find();
        }
    }//GEN-LAST:event_TextFindKeyPressed

    private void TablePersonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablePersonMouseClicked
        var source = (JTable)evt.getSource();
        int row = source.rowAtPoint( evt.getPoint() );
        
        SetEnabledButtons(false);
        
        selectedRow = -1;
        
        if (row >= 0 && persons.size() > 0) {
            SetEnabledButtons(true);
            selectedRow = row;
        }
    }//GEN-LAST:event_TablePersonMouseClicked

    private void ButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditActionPerformed
        if (selectedRow >= 0) {
            var person = persons.get(selectedRow);
            
            if (detail == null) {
                detail = new WindowPersonDetail();
                detail.SetDependency(dependencies);
                detail.SetWindowPerson(this);
                detail.setLocationRelativeTo(null);
            }
            
            if (!detail.isVisible()) {
                detail.Clear();
                detail.SetOperationState(DBOperationState.Update);
                detail.LoadCities();
                detail.SetPerson(person); 
                detail.setVisible(true);
            }
        }
    }//GEN-LAST:event_ButtonEditActionPerformed

    private void ButtonExcludeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonExcludeActionPerformed
        if (selectedRow >= 0) {
            var handler = dependencies.PersonHandler;
            var person = persons.get(selectedRow);
            
            if (handler.CanDelete(person)) {
                handler.Delete(person);
                handler.DeleteContacts(person);
                handler.DeleteAddresses(person);
                
                persons.remove(person);
                selectedRow = -1;
                
                // Notifica todos os controles que pessoas foram alteradas.
                dependencies.Notifier.Notify(Changes.Person);
            }
            else {
                ShowMessage("Esta pessoa está vinculado com um colaborador, fornecedor ou paciente e não pode ser excluído.");
            }
        }
    }//GEN-LAST:event_ButtonExcludeActionPerformed

    private void MenuItemNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemNewActionPerformed
        if (detail == null) {
            detail = new WindowPersonDetail();
            detail.SetDependency(dependencies);
            detail.SetWindowPerson(this);
            detail.setLocationRelativeTo(null);
        }
        
        if (!detail.isVisible()) {
            detail.Clear();
            detail.SetOperationState(DBOperationState.Insert);
            detail.LoadCities();
            detail.setVisible(true);
        }
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
            java.util.logging.Logger.getLogger(WindowPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowPerson.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowPerson().setVisible(true);
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
    private javax.swing.JTable TablePerson;
    private javax.swing.JTextField TextFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}