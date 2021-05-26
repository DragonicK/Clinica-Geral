package Engine.Window;

import Engine.Common.Strings;

import javax.swing.JOptionPane;
import java.awt.Component;

public class MessageBox {
    
    public static void Show(Component parent, String message) {
        Show(parent, Strings.Empty, message, "Mensagem", MessageBoxInformation.Plain);
    }
    
    public static void Show(Component parent, String message, String title) {
        Show(parent, message, title, "Mensagem", MessageBoxInformation.Plain);
    }
    
    public static void Show(Component parent, String message, String title, MessageBoxInformation information) {
        Show(parent, message, title, "Mensagem", information);
    }
    
    public static void Show(Component parent, String message, String title, String windowCaption, MessageBoxInformation information) {
        var _info = JOptionPane.INFORMATION_MESSAGE;
        
        switch (information) {
            case Warning -> _info = JOptionPane.WARNING_MESSAGE;
            case Question -> _info = JOptionPane.QUESTION_MESSAGE;
            case Plain -> _info = JOptionPane.PLAIN_MESSAGE;
            case Error -> _info = JOptionPane.ERROR_MESSAGE;
            case Information -> _info = JOptionPane.INFORMATION_MESSAGE;
        }
        
        JOptionPane.showMessageDialog(parent,
                title + ":\n\n" + message,
                windowCaption,
                _info);
    }
    
}