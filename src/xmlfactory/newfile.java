package xmlfactory;

import java.io.File;

import javax.swing.JFileChooser;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class newfile extends Dialog {
	private Text text;
	private Text text_1;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public newfile(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 12;
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("Dictionary");
		new Label(container, SWT.NONE);
		
		text_1 = new Text(container, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		
		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setDialogTitle("Select folder");
				int result = chooser.showOpenDialog(chooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filepath = file.getAbsolutePath();
					text_1.setText(filepath);// 将文件路径设到JTextField
				}
			}
		});
		btnNewButton.setText("Browse");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setText("Product Name");
		new Label(container, SWT.NONE);
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 7, 1));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(452, 250);
	}

}
