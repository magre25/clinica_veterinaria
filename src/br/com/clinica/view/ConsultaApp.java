package br.com.clinica.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import br.com.clinica.controller.ConsultaController;
import br.com.clinica.dao.AnimalDAO;
import br.com.clinica.dao.ConsultaDAO;
import br.com.clinica.dao.EspecieDAO;
import br.com.clinica.dao.ServicoDAO;
import br.com.clinica.dao.VeterinarioDAO;
import br.com.clinica.model.Animal;
import br.com.clinica.model.Consulta;
import br.com.clinica.model.Especie;
import br.com.clinica.model.Servico;
import br.com.clinica.model.Veterinario;

public class ConsultaApp {
	private static final Locale BRAZIL = new Locale("pt", "br");
	private JComboBox cbxAnimal;
	private JComboBox cbxVeterinario;
	private JComboBox cbxServico;
	private JTextField txtTotal; 
	private JFormattedTextField txtQntd;
	private JTextField txtEspecie;
	private JTextField txtCRMV;
	private JFrame frame;
	private JButton cadastrar;
	private JButton cancelar;
	private JButton calcular;
	private JButton novo;
	private double precototal;

	private final ConsultaController contralorConsu = new ConsultaController();

	protected void createAndShowGUI() {

		frame = new JFrame("Tela Consulta de Animal");

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

		JLabel animalLabel = new JLabel("Animal:");
		cbxAnimal = new JComboBox<>();
		cbxAnimal.setPreferredSize(new Dimension(150, 30));

		JLabel espLabel = new JLabel("Especie: ");
		txtEspecie = new JTextField();
		txtEspecie.setColumns(10);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
		Calendar data = Calendar.getInstance();

		JLabel dataLabel = new JLabel(dateFormat.format(data.getTime()));
		JLabel horasLabel = new JLabel(dateFormat2.format(data.getTime()));

		JLabel vetLabel = new JLabel("Veterinario:");
		JLabel crmvLabel = new JLabel("CRMV:");
		cbxVeterinario = new JComboBox<>();
		cbxVeterinario.setPreferredSize(new Dimension(150, 30));
		txtCRMV = new JTextField();
		txtCRMV.setColumns(6);

		JLabel servLabel = new JLabel("Servico:");
		JLabel qntLabel = new JLabel("QntHoras:");

		JLabel totalLabel = new JLabel("Preço Total:");
		cbxServico = new JComboBox<>();
		cbxServico.setPreferredSize(new Dimension(150, 30));

		NumberFormat formato = NumberFormat.getIntegerInstance(BRAZIL);
		txtQntd = new JFormattedTextField(formato);
		txtQntd.setColumns(6);

		txtTotal = new JTextField();
		txtTotal.setColumns(6);
		
		novo = new JButton("Novo");
		novo.setPreferredSize(new Dimension(95, 30));
		novo.addActionListener(this::onNovoClick);
		cadastrar = new JButton("Cadastrar");
		cadastrar.setPreferredSize(new Dimension(95, 30));
		cadastrar.addActionListener(this::onCadastrarClick);
	
		calcular = new JButton();
		calcular.setPreferredSize(new Dimension(47, 30));
		ImageIcon imagem = new ImageIcon(getClass().getResource("/br/com/clinica/icons/calculator.png"));
        imagem.setImage(imagem.getImage().getScaledInstance(20, 20, 20));
        calcular.setIcon(imagem);
        calcular.setBackground(Color.LIGHT_GRAY);
        calcular.setFocusPainted(false); // retirando borda em volta do texto do botao
		calcular.addActionListener(this::onCalcularClick);
		
		cancelar = new JButton("Cancelar");
		cancelar.setPreferredSize(new Dimension(95, 30));
		cancelar.addActionListener((ActionEvent e) -> {
			confirmExit();
		});

		// desabilitando todos os campos 
		desabilitar();
		
		// carregando metodos de acao para cada combobox 
		//actionComboBoxs();

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
		panel.add(animalLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 80);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cbxAnimal, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(espLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 0, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(txtEspecie, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(vetLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(cbxVeterinario, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 5, 0, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(crmvLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, -40, 0, 50);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(txtCRMV, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(servLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(cbxServico, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(qntLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(txtQntd, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 30);
		panel.add(calcular, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		panel.add(totalLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		panel.add(txtTotal, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, -60, 15, 20);
		panel.add(novo, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, 0, 15, 20);
		panel.add(cadastrar, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, -60, 15, 10);
		panel.add(cancelar, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(20, 0, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(dataLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 5;
		gbc.gridwidth = 0;
		gbc.gridheight = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(-15, 15, 15, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(horasLabel, gbc);

		return panel;
	}

	// retornando metodos de ação ao clicar em algum item do combobox 
	private void actionComboBoxs() {
		
		cbxAnimal.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				cbAnimalActionPerformed(evt);
			}
		});

		cbxVeterinario.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				cbVeterinarioActionPerfomed(evt);
			}
		});
	}
	
	
	private void cbAnimalActionPerformed(ActionEvent evt) {

		if (cbxAnimal.getItemCount() > 1) {
			Animal animal = (Animal) cbxAnimal.getSelectedItem();
			txtEspecie.setText(String.valueOf(animal.getEspecie().getDescEsp()));
		}
	}

	private void cbVeterinarioActionPerfomed(ActionEvent evt) {
		if (cbxVeterinario.getItemCount() > 1) {
			Veterinario vete = (Veterinario) cbxVeterinario.getSelectedItem();
			txtCRMV.setText(vete.getCRVM());
		}
	}
	
	private void desabilitar() {
		cbxAnimal.setEnabled(false);
		txtEspecie.setEnabled(false);
		cbxVeterinario.setEnabled(false);
		txtCRMV.setEnabled(false);
		cbxServico.setEnabled(false);
		txtQntd.setEnabled(false);
		txtTotal.setEnabled(false);
	}
	
	private void habilitar() {
		cbxAnimal.setEnabled(true);
		txtEspecie.setEnabled(true);
		cbxVeterinario.setEnabled(true);
		txtCRMV.setEnabled(true);
		cbxServico.setEnabled(true);
		txtQntd.setEnabled(true);
		txtTotal.setEnabled(true);
		contralorConsu.carregarAnimal(cbxAnimal);
		contralorConsu.carregarVeterinario(cbxVeterinario);
		contralorConsu.carregarServico(cbxServico);
	}
	
	private void onNovoClick(ActionEvent e) {
		habilitar();
		actionComboBoxs();
	}
	
	private void onCalcularClick(ActionEvent e) {

		Consulta consulta = new Consulta();
		Servico servico = new Servico();
		servico = (Servico) cbxServico.getSelectedItem();
		consulta.setServico(servico);

		try {

			int qntHoras = Integer.parseInt(txtQntd.getText());
			consulta.setQntd(qntHoras);

			if (qntHoras < 0 || qntHoras == 0) {
				JOptionPane.showMessageDialog(frame, "Numero Inválido! Digite apenas numeros positivos", "Aviso",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(frame, "Informe apenas números inteiros", "Aviso", JOptionPane.ERROR_MESSAGE);
			// TODO: handle exception
		}

		double precototal = consulta.getQntd() * consulta.getServico().getPreco();
		txtTotal.setText(String.valueOf(precototal));

	}

	private void onCadastrarClick(ActionEvent e) {
		Animal animal = new Animal();
		animal = (Animal) cbxAnimal.getSelectedItem();
		Veterinario veterinario = new Veterinario();
		veterinario = (Veterinario) cbxVeterinario.getSelectedItem();
		Servico servico = new Servico();
		servico = (Servico) cbxServico.getSelectedItem();
		Number qntd = (Number) txtQntd.getValue();

		if (qntd == null) {
			JOptionPane.showMessageDialog(frame, "Todos os campos devem ser preenchidos", "Aviso",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (qntd.intValue() < 0 || qntd.intValue() == 0) {
			JOptionPane.showMessageDialog(frame, "Numero Invádio", "Aviso", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Consulta consulta = new Consulta();
		consulta.setAnimal(animal);
		consulta.setVeterinario(veterinario);
		consulta.setServico(servico);
		consulta.setQntd(qntd.intValue());
		
		// criando um processo para evitar o travamento da interface 
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				
				// ConsultaDAO dao = new ConsultaDAO();
				// dao.cadastrar(consulta);
				contralorConsu.cadastrar(consulta);
				return null;
			}

			protected void done() {
				try {
					get(); // pegando o resultado 
					JOptionPane.showMessageDialog(frame, "Cadastro realizado com sucesso!", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
					
					// deixando campos em brancos apos o cadastro realizado
					txtEspecie.setText("");
					txtCRMV.setText("");
					txtQntd.setText("");
					txtTotal.setText("");
					// desabilitando botões
					desabilitar();

				} catch (InterruptedException | ExecutionException e) {
					JOptionPane.showMessageDialog(frame, e, "Erro", JOptionPane.ERROR_MESSAGE);
				}
			};
		};

		worker.execute();

	}

	private void confirmExit() {
		int answer = JOptionPane.showConfirmDialog(frame, "Deseja realmente sair da consulta?", "Cancelar",
				JOptionPane.YES_NO_OPTION);

		if (answer == JOptionPane.YES_OPTION) {
			frame.setVisible(false);
			frame.dispose();
		}
	}

	public static void main(String[] args) {
		ConsultaApp app = new ConsultaApp();
		app.createAndShowGUI();
	}

}
