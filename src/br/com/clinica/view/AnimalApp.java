package br.com.clinica.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import br.com.clinica.dao.AnimalDAO;
import br.com.clinica.dao.EspecieDAO;
import br.com.clinica.dao.RacaDAO;
import br.com.clinica.model.Animal;
import br.com.clinica.model.Especie;
import br.com.clinica.model.Raca;
import br.com.clinica.model.Servico;

public class AnimalApp {
	private JComboBox cbxEsp;
	private JComboBox cbxRaca;
	private JComboBox<String> cbxSexo;
	private JTextField txtNome;
	private JRadioButton rbsexoM;
	private JRadioButton rbsexoF;
	private JFrame frame;
	private JButton cadastrar;
	private JButton cancelar;

	protected void createAndShowGUI() {

		frame = new JFrame("Cadastro de Animal");
		frame.getContentPane().setLayout(new BorderLayout());
		JPanel panel = buildPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	
	private JPanel buildPanel() {
		
		
		JPanel panel = new JPanel();
		
		JLabel espLabel = new JLabel("Espécie:");
		cbxEsp = new JComboBox<>();
		cbxEsp.setPreferredSize(new Dimension(100, 30));
		
		JLabel racaLabel = new JLabel("Raça:");
		cbxRaca = new JComboBox<>();
		cbxRaca.setPreferredSize(new Dimension(100, 30));
		
		JLabel nomeLabel = new JLabel("Nome:");
		txtNome = new JTextField();
		txtNome.setColumns(15);
		
		JLabel sexoLabel = new JLabel("Sexo:");
		cbxSexo = new JComboBox<>();
		cbxSexo.setPreferredSize(new Dimension(100, 30));
		
		cadastrar = new JButton("Cadastrar");
		cadastrar.setPreferredSize(new Dimension(95, 30));
		cadastrar.addActionListener(this::onCadastrarClick);
		
		cancelar = new JButton("Cancelar");
		cancelar.setPreferredSize(new Dimension(95, 30));
		cancelar.addActionListener((ActionEvent e) -> {confirmExit();});
		
		cbxSexo.addItem("Femêa");
		cbxSexo.addItem("Macho");
		comboEsp();
		comboRaca();
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc;
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(espLabel, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 100);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cbxEsp, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(racaLabel, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 100);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cbxRaca, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(nomeLabel, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 100);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(txtNome, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(sexoLabel, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 100);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cbxSexo, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, 0, 15, 10);		
		panel.add(cadastrar, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, -60, 15, 10);
		panel.add(cancelar, gbc);
		
		return panel;
	}
	
	
	public void comboEsp() {

		EspecieDAO dao = new EspecieDAO();
		List<Especie> listesp = dao.listarEspecie();
		cbxEsp.removeAllItems();
		
		for (Especie esp : listesp) {
			cbxEsp.addItem(esp);
		}
		
	}
	
	public void comboRaca() {

		RacaDAO dao = new RacaDAO();
		List<Raca> listarac = dao.listarRaca();
		cbxRaca.removeAllItems();
		
		for (Raca rc : listarac) {
			cbxRaca.addItem(rc);
		}
		
	}
	
	
	public void onCadastrarClick(ActionEvent e) {
			
		String nome = txtNome.getText();
		String sexo = (String) cbxSexo.getSelectedItem();
		Especie especie = new Especie();
		especie = (Especie) cbxEsp.getSelectedItem();
		Raca raca = new Raca();
		raca = (Raca) cbxRaca.getSelectedItem();
		
		
		if(nome.equals("")) {
			JOptionPane.showMessageDialog(frame, "Todos os campos devem ser preenchidos", "Aviso", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Animal animal = new Animal();
		//animal.setNome(nome);
		animal.setSexo(sexo);
		animal.setEspecie(especie);
		animal.setRaca(raca);
		
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				// TODO Auto-generated method stub
				AnimalDAO dao = new AnimalDAO();
				dao.cadastrar(animal);
				return null;
			}
			
			protected void done() {
				try {
					get();
					JOptionPane.showMessageDialog(frame, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				} catch (InterruptedException | ExecutionException e) {
					JOptionPane.showMessageDialog(frame, e, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			};
		};
		
		worker.execute();
		
		
	}
	
	
	private void confirmExit() {
		int answer = JOptionPane.showConfirmDialog(frame,
				"Se você sair do aplicativo, o cadastro será cancelado. Deseja continuar?", "Cancelar",
				JOptionPane.YES_NO_OPTION);

		if (answer == JOptionPane.YES_OPTION) {
			frame.setVisible(false);
			// releaseConnection();
			frame.dispose();
		}
	}
	
	public static void main(String[] args) {
		AnimalApp app = new AnimalApp();
		app.createAndShowGUI();
	}

}
