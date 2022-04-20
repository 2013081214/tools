package diagrameditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class process extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			process dialog = new process();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public process() {
		setTitle("\u7F16\u8F91Process");
		setBounds(100, 100, 623, 444);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Process name");
			lblNewLabel.setBounds(118, 36, 107, 18);
			contentPanel.add(lblNewLabel);
		}
		{
			textField = new JTextField();
			textField.setBounds(273, 33, 247, 24);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setBounds(142, 87, 40, 18);
		contentPanel.add(lblInput);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(142, 134, 48, 18);
		contentPanel.add(lblOutput);
		
		JLabel lblPreCondition = new JLabel("Pre condition");
		lblPreCondition.setBounds(118, 203, 107, 18);
		contentPanel.add(lblPreCondition);
		
		JLabel lblPostCondition = new JLabel("Post condition");
		lblPostCondition.setBounds(118, 282, 120, 18);
		contentPanel.add(lblPostCondition);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(273, 84, 247, 24);
		contentPanel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(273, 131, 247, 24);
		contentPanel.add(textField_2);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(273, 168, 247, 76);
		contentPanel.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(273, 257, 247, 76);
		contentPanel.add(textArea_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
