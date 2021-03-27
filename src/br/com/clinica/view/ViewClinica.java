package br.com.clinica.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ViewClinica {
	
	private JFrame frame;
	private JButton btnConsulta;
	private JButton btnAnimal;
	private JButton btnServico;

	public void createAndShowGUI() {

		frame = new JFrame("Clinica Veterinaria");
		frame.getContentPane().setLayout(new BorderLayout());
		JPanel panel = buildPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmExit();
			}
		});

		frame.setSize(350, 200);
		frame.setVisible(true);

	}

	private JPanel buildPanel() {

		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		btnConsulta = new JButton("Consultas");
		btnConsulta.setPreferredSize(new Dimension(95, 30));
		// btnConsulta.setBorder(null); // retirando borda do botao
		btnConsulta.setFocusPainted(false); // retirando borda em volta do texto do botao
		btnConsulta.setBackground(Color.LIGHT_GRAY);
		btnConsulta.addActionListener(this::onConsultaClick);

		btnAnimal = new JButton("Animais");
		btnAnimal.setPreferredSize(new Dimension(95, 30));
		btnAnimal.setFocusPainted(false); // retirando borda em volta do texto do botao
		btnAnimal.setBackground(Color.LIGHT_GRAY);
		btnAnimal.addActionListener(this::onAnimalClick);

		btnServico = new JButton("Serviços");
		btnServico.setPreferredSize(new Dimension(95, 30));
		btnServico.setFocusPainted(false); // retirando borda em volta do texto do botao
		btnServico.setBackground(Color.LIGHT_GRAY);
		btnServico.addActionListener(this::onServicoClick);

		JLabel label = new JLabel();
		// ImageIcon imagem = new ImageIcon("logovet.jpg"); // pegando imagem dentro do
		// diretorio raiz do projeto
		ImageIcon imagem = new ImageIcon(getClass().getResource("/br/com/clinica/icons/logovet.jpg")); // pegando objeto da
																									// URL e atribuindo
																									// ao objeto imagem,
																									// getClass. para
																									// executação no jar
		imagem.setImage(imagem.getImage().getScaledInstance(110, 110, 110));
		label.setIcon(imagem);

		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc;

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 5, 0, 0);
		panel.add(btnConsulta, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(-20, 5, 0, 0);
		panel.add(btnAnimal, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(-20, 5, 0, 10);
		panel.add(btnServico, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(-20, 50, 0, 0);
		panel.add(label, gbc);

		return panel;
	}

	private void onConsultaClick(ActionEvent e) {
		ConsultaApp cpp = new ConsultaApp();
		cpp.createAndShowGUI();
	}

	private void onAnimalClick(ActionEvent e) {
		AnimalApp anpp = new AnimalApp();
		anpp.createAndShowGUI();
	}

	private void onServicoClick(ActionEvent e) {
		ServicoApp spp = new ServicoApp();
		spp.createAndShowGUI();
	}

	private void confirmExit() {
		int answer = JOptionPane.showConfirmDialog(frame, "Deseja realmente sair do aplicativo?", "Cancelar",
				JOptionPane.YES_NO_OPTION);

		if (answer == JOptionPane.YES_OPTION) {
			frame.setVisible(false);
			// releaseConnection();
			frame.dispose();
		}
	}

	public static void main(String[] args) {
		ViewClinica app = new ViewClinica();
		app.createAndShowGUI();
	}

}
