package br.com.clinica;


import br.com.clinica.view.ViewClinica;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


public class Main {
	
	public static void main( String[] args )
    {   // estilo da interface 
        try {
            UIManager.setLookAndFeel( new NimbusLookAndFeel() );
         }
         catch ( Throwable ex ) {
            
         }
         
         ViewClinica frm = new ViewClinica();
         frm.createAndShowGUI();
    }
}
