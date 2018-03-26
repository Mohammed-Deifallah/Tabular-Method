package tabularMethod;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI_Tabular {

	Table solver = new Table();
	private JFrame frame;
	JTextField minTerms;
	JTextField dontCares;
	JTextArea output;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI_Tabular window = new GUI_Tabular();
				window.frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_Tabular() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(30, 20, 1200, 710);
		frame.getContentPane().setLayout(null);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		minTerms = new JTextField();
		minTerms.setBounds(150, 80, 400, 50);
		minTerms.setFont(new Font("math", Font.BOLD, 20));
		minTerms.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(minTerms);

		dontCares = new JTextField();
		dontCares.setText(" ");
		dontCares.setBounds(150, 200, 400, 50);
		dontCares.setFont(new Font("math", Font.BOLD, 20));
		dontCares.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(dontCares);

		output = new JTextArea();
		output.setBounds(150, 310, 1000, 350);
		output.setFont(new Font("math", Font.BOLD, 20));
		output.setAlignmentX(SwingConstants.CENTER);
		output.setAlignmentY(SwingConstants.CENTER);
		frame.getContentPane().add(output);

		JButton solve = new JButton("Solve");
		solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Table solver = new Table();
				try {
					if (minTerms.getText().isEmpty())
						throw new RuntimeException();
					else if (minTerms.getText().equals("0"))
						output.setText("0");
					else if (dontCares.getText().isEmpty()) {
						solver.initialize(minTerms.getText(), " ");
						solver.minimizeMyTable();
					} else {
						solver.initialize(minTerms.getText(), dontCares.getText());
						solver.minimizeMyTable();
					}

					output.setText(solver.printMyTable());

				} catch (RuntimeException e) {
					JOptionPane.showMessageDialog(null, "Invalid Input!");
				}
			}

		});
		solve.setBounds(850, 190, 90, 50);
		frame.getContentPane().add(solve);

		JLabel minTerms = new JLabel("minTerms:");
		minTerms.setHorizontalAlignment(SwingConstants.CENTER);
		minTerms.setFont(new Font("math", Font.PLAIN, 15));
		minTerms.setBounds(30, 60, 74, 104);
		minTerms.setForeground(Color.black);
		frame.getContentPane().add(minTerms);

		JLabel dontCares = new JLabel("don't cares:");
		dontCares.setHorizontalAlignment(SwingConstants.CENTER);
		dontCares.setFont(new Font("math", Font.PLAIN, 15));
		dontCares.setBounds(30, 170, 74, 104);
		dontCares.setForeground(Color.black);
		frame.getContentPane().add(dontCares);

		JLabel result = new JLabel("result:");
		result.setHorizontalAlignment(SwingConstants.CENTER);
		result.setFont(new Font("math", Font.PLAIN, 15));
		result.setBounds(30, 280, 74, 104);
		result.setForeground(Color.black);
		frame.getContentPane().add(result);
	}

}