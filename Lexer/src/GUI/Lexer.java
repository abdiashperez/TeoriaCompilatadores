package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import Logica.LexerLogic;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;


public class Lexer {

	private JFrame frame;
	private LexerLogic myLexer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lexer window = new Lexer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Lexer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		myLexer= new LexerLogic();
		frame = new JFrame();
		frame.setBounds(100, 100, 392, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		final JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(10, 11, 240, 239);
		panel.add(editorPane);
		
		JButton btnNewButton = new JButton("Lexer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myLexer.substringText(editorPane.getText());
			}
		});
		btnNewButton.setBounds(264, 11, 99, 23);
		panel.add(btnNewButton);
		
	}
}
