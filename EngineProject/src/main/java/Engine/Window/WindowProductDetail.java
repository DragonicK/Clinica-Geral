package Engine.Window;

import Engine.Product;
import Engine.Supplier;
import Engine.Common.Strings;
import Engine.Database.DBOperationState;
import Engine.Communication.DependencyRepository;
import Engine.Window.Observer.Changes;
import Engine.Window.Observer.NotificationProductDetail;

import javax.swing.JOptionPane;
import java.util.List;

public class WindowProductDetail extends javax.swing.JDialog {
    private NotificationProductDetail notification;
    private DependencyRepository dependencies;
    // Lista de todos os fornecedores.
    private List<Supplier> suppliers;
    // Atividade atual do formulário, inserir ou atualizar.
    private DBOperationState operationState;
    // Id do produto para atualização.
    private int productId;
    private int lastCode;
    
    public WindowProductDetail() {
        initComponents();
        this.setModal(true);
    }
    
    public void UpdateWindow() {
        ComboSupplier.setSelectedIndex(-1);
        LoadSuppliers();
    }
    
    // As dependências são injetadas.
    public void SetDependency(DependencyRepository dependency) {
        dependencies = dependency;
        
        notification = new NotificationProductDetail(this);
        notification.Add(Changes.Supplier);
        
        dependencies.Notifier.Add(notification);
    }
    
    // Atividade atual do formulário, inserir ou atualizar.
    public void SetOperationState(DBOperationState operation) {
        operationState = operation;
        
        if (operation == DBOperationState.Insert) {
            ButtonRegister.setText("Cadastrar");
            this.setTitle("Cadastrar Novo Produto");
        }
        else if  (operation == DBOperationState.Update) {
            ButtonRegister.setText("Atualizar");
            this.setTitle("Editar Produto");
        }
    }
    
    // Adiciona o produto ao formulário como para atualização.
    public void SetProduct(Product product) {
        var supplier = GetSupplier(product.SupplierId);
        
        if (supplier != null) {
            SelectSupplier(supplier.Id);
        }
        else {
            ComboSupplier.setSelectedIndex(-1);
            ComboSupplier.setSelectedItem(Strings.Empty);
        }
        
        lastCode = product.Code;
        productId = product.Id;
        TextCode.setText(Integer.toString(product.Code));
        TextName.setText(product.Name);
        TextPrice.setText(Double.toString(product.Price));
        TextQuantity.setText(Integer.toString(product.Quantity));
    }
    
    private Supplier GetSupplier(int id) {
        if (suppliers != null) {
            var count = suppliers.size();
            
            for (var i = 0; i < count; ++i){
                if (suppliers.get(i).Id == id){
                    return suppliers.get(i);
                }
            }
        }
        
        return null;
    }
    
    private void SelectSupplier(int id) {
        if (suppliers != null) {
            var count = suppliers.size();
            
            for (var i = 0; i < count; ++i){
                if (suppliers.get(i).Id == id){
                    ComboSupplier.setSelectedIndex(i);
                }
            }
        }
    }
    
    public void LoadSuppliers() {
        var handler = dependencies.SupplierHandler;
        suppliers = handler.GetSuppliers();
        
        ClearSupplierBox();
        
        if (suppliers != null) {
            FillSupplierBox();
        }
    }
    
    private void ClearSupplierBox() {
        ComboSupplier.removeAllItems();
    }
    
    private void FillSupplierBox() {
        var count = suppliers.size();
        
        for (var i = 0; i < count; ++i) {
            ComboSupplier.addItem(suppliers.get(i).Name);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ButtonRegister = new javax.swing.JButton();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        TextPrice = new javax.swing.JFormattedTextField();
        label4 = new java.awt.Label();
        ComboSupplier = new javax.swing.JComboBox<>();
        label5 = new java.awt.Label();
        TextCode = new javax.swing.JTextField();
        TextName = new javax.swing.JTextField();
        TextQuantity = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Produto");
        setAlwaysOnTop(true);
        setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        setResizable(false);

        ButtonRegister.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        ButtonRegister.setText("Cadastrar");
        ButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegisterActionPerformed(evt);
            }
        });

        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        label1.setText("Código:");

        label2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        label2.setText("Nome:");

        label3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        label3.setText("Preço:");

        TextPrice.setBackground(new java.awt.Color(255, 255, 255));
        TextPrice.setForeground(new java.awt.Color(0, 0, 0));
        TextPrice.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        TextPrice.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        label4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        label4.setText("Fornecedor:");

        ComboSupplier.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        label5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        label5.setText("Quantidade:");

        TextCode.setBackground(new java.awt.Color(255, 255, 255));
        TextCode.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextCode.setForeground(new java.awt.Color(0, 0, 0));

        TextName.setBackground(new java.awt.Color(255, 255, 255));
        TextName.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextName.setForeground(new java.awt.Color(0, 0, 0));

        TextQuantity.setBackground(new java.awt.Color(255, 255, 255));
        TextQuantity.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        TextQuantity.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TextQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(TextName, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                                .addComponent(TextCode)
                                .addComponent(TextPrice)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(ButtonRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(TextCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(TextName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(TextPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(TextQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ButtonRegister)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegisterActionPerformed
        // Verifica se os dados dos controles estão certos.
        if (ValidateControls()) {
            var product = CreateProductFromControl();
            var handler = dependencies.ProductHandler;
            
            if (operationState == DBOperationState.Insert) {
                if (handler.CodeExists(product.Code)) {
                    ShowMessage("Este código está sendo usado por outro produto.");
                }
                else {
                    handler.Put(product);
                    Clear();
                    
                    ShowSuccessMessage("O produto foi cadastrado.");
                    
                    dependencies.Notifier.Notify(Changes.Product);
                }
            }
            else if (operationState == DBOperationState.Update) {
                if (lastCode != product.Code) {
                    if (handler.CodeExists(product.Code)) {
                        ShowMessage("Este código está sendo usado por outro produto.");
                        return;
                    }
                }
                
                handler.Update(product);
                ShowSuccessMessage("Este produto foi atualizado.");
                
                dependencies.Notifier.Notify(Changes.Product);
            }
        }
    }//GEN-LAST:event_ButtonRegisterActionPerformed
    
    private Product CreateProductFromControl() {
        var product = new Product();
        var supplier = suppliers.get(ComboSupplier.getSelectedIndex());
        
        // Guarda o id para a atualização.
        // Quando estiver no modo de operação para update.
        product.Id = productId;
        product.Code = Integer.parseInt(TextCode.getText().trim());
        product.Name = TextName.getText().trim();
        product.Price = Double.parseDouble(TextPrice.getText().trim());
        product.Quantity = Integer.parseInt(TextQuantity.getText().trim());
        product.SupplierId = supplier.Id;
        
        return product;
    }
    
    private boolean ValidateControls() {
        if (TextCode.getText().length() == 0){
            ShowMessage("O campo código não pode estar vazio.");
            return false;
        }
        
        if (Strings.HasLetters(TextCode.getText())) {
            ShowMessage("O campo código deve ter somente números.");
            return false;
        }
        
        if (TextName.getText().length() == 0){
            ShowMessage("O campo nome não pode estar vazio.");
            return false;
        }
        
        if (TextPrice.getText().length() == 0){
            ShowMessage("O campo preço não pode estar vazio.");
            return false;
        }
        
        if (Strings.HasLetters(TextPrice.getText())) {
            ShowMessage("O campo preço deve ter somente números.");
            return false;
        }
        
        if (TextQuantity.getText().length() == 0){
            ShowMessage("O campo quantidade não pode estar vazio.");
            return false;
        }
        
        if (Strings.HasLetters(TextQuantity.getText())) {
            ShowMessage("O campo quantidade deve ter somente números.");
            return false;
        }
        
        if (ComboSupplier.getSelectedIndex() < 0) {
            ShowMessage("Um fornecedor deve ser indicado.\nEm qualquer caso um fornecedor deve ser cadastrado.");
            return false;
        }
        
        return true;
    }
    
    private void ShowMessage(String message) {
        JOptionPane.showMessageDialog(this,
                "Houve um problema:\n\n" + message,
                "Produto",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void ShowSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this,
                "Sucesso:\n\n" + message,
                "Produto",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void Clear() {
        var empty = Strings.Empty;
        
        TextCode.setText(empty);
        TextName.setText(empty);
        TextPrice.setText(empty);
        TextQuantity.setText(empty);
        ComboSupplier.setSelectedIndex(-1);
        ComboSupplier.setSelectedItem(empty);
    }
    
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
            java.util.logging.Logger.getLogger(WindowProductDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WindowProductDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WindowProductDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WindowProductDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WindowProductDetail().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonRegister;
    private javax.swing.JComboBox<String> ComboSupplier;
    private javax.swing.JTextField TextCode;
    private javax.swing.JTextField TextName;
    private javax.swing.JFormattedTextField TextPrice;
    private javax.swing.JTextField TextQuantity;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    // End of variables declaration//GEN-END:variables
}