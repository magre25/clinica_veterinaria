package br.com.clinica.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

import br.com.clinica.controller.ServicoController;
import br.com.clinica.dao.ServicoDAO;
import br.com.clinica.model.Servico;

public class ServicoApp {
	private static final Locale BRAZIL = new Locale("pt", "br");
	private JTextField codigoText;
	private JTextField descricaoText;
	private JFormattedTextField precoText;
	private JTextField buscaIdText;
	private JFrame frame;
	private JButton cadastrar;
	private JButton cancelar;
	private JButton buscar;
	private JButton alterar;
	private JButton excluir;

	private final ServicoController controladorServ = new ServicoController();

	protected void createAndShowGUI() {

		frame = new JFrame("Cadastro de Serviços");

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

		JLabel codigoLabel = new JLabel("Código:");
		codigoText = new JTextField();
		codigoText.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel buscaLabel = new JLabel("BuscaId:");
		buscaIdText = new JTextField();
		buscaIdText.setColumns(4);
		buscaIdText.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel descricaoLabel = new JLabel("Descrição:");
		descricaoText = new JTextField();
		descricaoText.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel precoLabel = new JLabel("Preço: ");
		NumberFormat formato = NumberFormat.getIntegerInstance(BRAZIL);
		precoText = new JFormattedTextField(formato);
		precoText.setColumns(5);
		precoText.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel limparLabel = new JLabel();
		ImageIcon imagem = new ImageIcon(getClass().getResource("/br/com/clinica/icons/limpar.png"));
		imagem.setImage(imagem.getImage().getScaledInstance(45, 45, 45));
		limparLabel.setIcon(imagem);
		
		limparBusca(limparLabel); // chamando funcão passando a Label como parametro, que vai receber as ações do mouse na função.

		// Configurações botões
		cadastrar = new JButton("Cadastrar");
		cadastrar.addActionListener(this::onCadastrarClick);
		cadastrar.setPreferredSize(new Dimension(95, 30));
		cancelar = new JButton("Cancelar");
		cancelar.setPreferredSize(new Dimension(95, 30));
		cancelar.addActionListener((ActionEvent e) -> {
			confirmExit();
		});

		buscar = new JButton();
		buscar.setPreferredSize(new Dimension(47, 30));
		ImageIcon imgbuscar = new ImageIcon(getClass().getResource("/br/com/clinica/icons/pesquisar.png"));
		imgbuscar.setImage(imgbuscar.getImage().getScaledInstance(20, 20, 20));
		buscar.setIcon(imgbuscar);
		buscar.addActionListener(this::onBuscarClick);

		alterar = new JButton("Alterar");
		alterar.setPreferredSize(new Dimension(95, 30));
		alterar.addActionListener(this::onAlterarClick);
		excluir = new JButton("Excluir");
		excluir.setPreferredSize(new Dimension(95, 30));
		excluir.addActionListener(this::onExcluirClick);

		alterar.setEnabled(false);
		excluir.setEnabled(false);

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
		panel.add(codigoLabel, gbc);

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
		panel.add(codigoText, gbc);

		// label buscar

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(descricaoLabel, gbc);

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
		panel.add(descricaoText, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 0);
		panel.add(buscaLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(buscaIdText, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 5;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(buscar, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 5, 0, 10);
		panel.add(precoLabel, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, -80, 0, 200);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		panel.add(precoText, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 20, 0, 10);
		panel.add(limparLabel, gbc);

		// botões

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, 50, 10, 15);
		panel.add(cadastrar, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, 80, 10, 10);
		panel.add(alterar, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, 80, 10, 0);
		panel.add(excluir, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(30, 80, 10, -35);
		panel.add(cancelar, gbc);

		return panel;
	}

	private void limparBusca(JLabel limpar) {

			limpar.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				codigoText.setText("");
				codigoText.setEnabled(true);
				descricaoText.setText("");
				precoText.setText("");
				buscaIdText.setText("");
				cadastrar.setEnabled(true);
				;
				alterar.setEnabled(false);
				excluir.setEnabled(false);

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// Método mouseReleased é chamado quando o botão do mouse é liberado.

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// Método mousePressed é chamado quando o mouse é pressionado.

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// Método mouseExited é chamado quando o mouse passa fora do quadro i.e. JFrame

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// Método mouseEntered é chamado quando o mouse vem de uma janela exterior ao quadro.

			}

		});
	}

	private void onExcluirClick(ActionEvent e) {
		String codigo = codigoText.getText();
		Servico servico = new Servico();
		servico.setCodigo(codigo);

		int answer = JOptionPane.showConfirmDialog(frame, "Deseja realmente excluir o serviço?", "Excluir",
				JOptionPane.YES_NO_OPTION);

		if (answer == JOptionPane.YES_OPTION) {
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

				@Override
				protected Void doInBackground() throws Exception {
					ServicoDAO dao = new ServicoDAO();
					dao.excluir(servico);
					return null;
				}

				@Override
				protected void done() {
					try {
						get();
						JOptionPane.showMessageDialog(frame, "Serviço excluido com sucesso!", "Sucesso",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (InterruptedException | ExecutionException e) {
						JOptionPane.showMessageDialog(frame, "Serviço não excluido!", "Erro",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			};

			worker.execute();
			cadastrar.setEnabled(true);
			codigoText.setEnabled(true);
			alterar.setEnabled(false);
			excluir.setEnabled(false);
			codigoText.setText("");
			descricaoText.setText("");
			precoText.setText("");
			buscaIdText.setText("");
		}

	}

	private void onAlterarClick(ActionEvent e) {

		String codigo = codigoText.getText();
		String descricao = descricaoText.getText();
		Number preco = (Number) precoText.getValue();

		Servico servico = new Servico();
		servico.setCodigo(codigo);
		servico.setDescricao(descricao);
		servico.setPreco(preco.doubleValue());

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				ServicoDAO dao = new ServicoDAO();
				dao.atualizar(servico);
				return null;
			}

			@Override
			protected void done() {
				try {
					get();
					JOptionPane.showMessageDialog(frame, "Serviço atualizado com sucesso!", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (InterruptedException | ExecutionException e) {
					JOptionPane.showMessageDialog(frame, "Serviço não realizado!", "Erro", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		};

		worker.execute();
		cadastrar.setEnabled(true);
		codigoText.setEnabled(true);
		alterar.setEnabled(false);
		excluir.setEnabled(false);
		codigoText.setText("");
		descricaoText.setText("");
		precoText.setText("");
		buscaIdText.setText("");

	}

	private void onBuscarClick(ActionEvent e) {

		try {
			String codServ = buscaIdText.getText();

			if (codServ.equals("")) {
				JOptionPane.showMessageDialog(frame, "Campo 'BuscaId' deve ser preenchido", "Aviso",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			Servico servico = new Servico();

			ServicoDAO dao = new ServicoDAO();
			servico = dao.listaCodServico(codServ);
			codigoText.setText(servico.getCodigo());
			descricaoText.setText(servico.getDescricao());
			precoText.setText(String.valueOf(servico.getPreco()));

		} catch (NullPointerException e2) {
			JOptionPane.showMessageDialog(frame, "Código Inválido", "Aviso", JOptionPane.ERROR_MESSAGE);
			return;
		}

		codigoText.setEnabled(false);
		cadastrar.setEnabled(false);
		alterar.setEnabled(true);
		excluir.setEnabled(true);
	}

	private void onCadastrarClick(ActionEvent e) {

		// retornando valores lidos nos campos
		String codigo = codigoText.getText();
		String descricao = descricaoText.getText();
		Number preco = (Number) precoText.getValue();

		// verificacao de campos nulos
		if (codigo.equals("") || descricao.equals("") || preco == null) {
			JOptionPane.showMessageDialog(frame, "Todos os campos devem ser preenchidos", "Aviso",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Servico servico = new Servico();
		servico.setCodigo(codigo);
		servico.setDescricao(descricao);
		servico.setPreco(preco.doubleValue());

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// ServicoDAO dao = new ServicoDAO();
				// dao.salvar(servico);
				controladorServ.cadastrar(servico); // chamando controlador para cadastrar um serviço
				return null;
			}

			@Override
			protected void done() {
				try {
					get();
					JOptionPane.showMessageDialog(frame, "Cadastro realizado com sucesso!", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);

					codigoText.setText("");
					descricaoText.setText("");
					precoText.setText("");

				} catch (InterruptedException | ExecutionException e) {
					JOptionPane.showMessageDialog(frame, "Cadastro não realizado!", "Erro", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		};

		worker.execute();

	}

	private void confirmExit() {
		int answer = JOptionPane.showConfirmDialog(frame,
				"Se você sair do aplicativo, o cadastro será cancelado. Deseja continuar?", "Cancelar",
				JOptionPane.YES_NO_OPTION);

		if (answer == JOptionPane.YES_OPTION) {
			frame.setVisible(false);
			frame.dispose();
		}
	}
	

}
