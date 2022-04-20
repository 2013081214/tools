package diagrameditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Combo;

import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JDialog;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;


public class Diagram extends Shell {
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Combo combo;
	protected process Process;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Diagram shell = new Diagram(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Diagram(Display display) {
		super(display, SWT.SHELL_TRIM);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new FormLayout());
		
		Button InformalButton = new Button(this, SWT.BORDER);
		FormData fd_InformalButton = new FormData();
		fd_InformalButton.bottom = new FormAttachment(0, 36);
		fd_InformalButton.right = new FormAttachment(0, 298);
		fd_InformalButton.top = new FormAttachment(0);
		fd_InformalButton.left = new FormAttachment(0, 200);
		InformalButton.setLayoutData(fd_InformalButton);
		InformalButton.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		InformalButton.setSelection(true);
		InformalButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		InformalButton.setText("informal");
		
		Button SemiformalButton = new Button(this, SWT.BORDER | SWT.TOGGLE);
		FormData fd_SemiformalButton = new FormData();
		fd_SemiformalButton.bottom = new FormAttachment(0, 36);
		fd_SemiformalButton.right = new FormAttachment(0, 498);
		fd_SemiformalButton.top = new FormAttachment(0);
		fd_SemiformalButton.left = new FormAttachment(0, 400);
		SemiformalButton.setLayoutData(fd_SemiformalButton);
		SemiformalButton.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		SemiformalButton.setText("Semiformal");
		
		Button FormalButton = new Button(this, SWT.BORDER | SWT.TOGGLE);
		FormData fd_FormalButton = new FormData();
		fd_FormalButton.bottom = new FormAttachment(0, 36);
		fd_FormalButton.right = new FormAttachment(0, 708);
		fd_FormalButton.top = new FormAttachment(0);
		fd_FormalButton.left = new FormAttachment(0, 610);
		FormalButton.setLayoutData(fd_FormalButton);
		FormalButton.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.BOLD));
		FormalButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		FormalButton.setText("formal");
		
		ComboViewer comboViewer = new ComboViewer(this, SWT.NONE);
		combo = comboViewer.getCombo();
		FormData fd_combo = new FormData();
		fd_combo.right = new FormAttachment(0, 122);
		fd_combo.top = new FormAttachment(0, 2);
		fd_combo.left = new FormAttachment(0, 10);
		combo.setLayoutData(fd_combo);
		combo.setItems(new String[] {"featrueA", "featureB", "featureC", "featureDa", "featureDb"});
		
		Button btnNewButton_1 = new Button(this, SWT.TOGGLE);
		FormData fd_btnNewButton_1 = new FormData();
		fd_btnNewButton_1.bottom = new FormAttachment(0, 463);
		fd_btnNewButton_1.right = new FormAttachment(0, 122);
		fd_btnNewButton_1.top = new FormAttachment(0, 433);
		fd_btnNewButton_1.left = new FormAttachment(0, 10);
		btnNewButton_1.setLayoutData(fd_btnNewButton_1);
		btnNewButton_1.setFont(SWTResourceManager.getFont("»ªÎÄ·ÂËÎ", 9, SWT.BOLD));
		btnNewButton_1.setText("\u4FDD\u5B58");
		
		Label label = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		FormData fd_label = new FormData();
		fd_label.bottom = new FormAttachment(0, 463);
		fd_label.top = new FormAttachment(0, 5);
		fd_label.left = new FormAttachment(0, 120);
		label.setLayoutData(fd_label);
		formToolkit.adapt(label, true, true);
		
		Label label_1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_1 = new FormData();
		fd_label_1.right = new FormAttachment(0, 774);
		fd_label_1.top = new FormAttachment(0, 36);
		fd_label_1.left = new FormAttachment(0, 120);
		label_1.setLayoutData(fd_label_1);
		formToolkit.adapt(label_1, true, true);
		
		Label label_1_1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label_1_1 = new FormData();
		fd_label_1_1.right = new FormAttachment(0, 774);
		fd_label_1_1.top = new FormAttachment(0, 461);
		fd_label_1_1.left = new FormAttachment(0, 120);
		label_1_1.setLayoutData(fd_label_1_1);
		formToolkit.adapt(label_1_1, true, true);
		
		Composite compositesemiformal = new Composite(this, SWT.BORDER);
		FormData fd_compositesemiformal = new FormData();
		fd_compositesemiformal.bottom = new FormAttachment(0, 455);
		fd_compositesemiformal.right = new FormAttachment(0, 557);
		fd_compositesemiformal.top = new FormAttachment(0, 42);
		compositesemiformal.setLayoutData(fd_compositesemiformal);
		formToolkit.adapt(compositesemiformal);
		formToolkit.paintBordersFor(compositesemiformal);
		compositesemiformal.setLayout(null);
		
		Label lblConst = new Label(compositesemiformal, SWT.NONE);
		lblConst.setBounds(10, 101, 76, 20);
		lblConst.setText("Const");
		lblConst.setAlignment(SWT.CENTER);
		formToolkit.adapt(lblConst, true, true);
		
		Label lblNewLabel_Type = new Label(compositesemiformal, SWT.NONE);
		lblNewLabel_Type.setBounds(10, 170, 76, 20);
		lblNewLabel_Type.setText("Type");
		lblNewLabel_Type.setAlignment(SWT.CENTER);
		formToolkit.adapt(lblNewLabel_Type, true, true);
		
		Label lblNewLabel = new Label(compositesemiformal, SWT.NONE);
		lblNewLabel.setBounds(10, 37, 76, 20);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setText("Module");
		
		Label lblNewLabel_Ext = new Label(compositesemiformal, SWT.NONE);
		lblNewLabel_Ext.setBounds(10, 229, 76, 20);
		lblNewLabel_Ext.setText("Ext");
		lblNewLabel_Ext.setAlignment(SWT.CENTER);
		formToolkit.adapt(lblNewLabel_Ext, true, true);
		
		Label lblNewLabel_Process = new Label(compositesemiformal, SWT.NONE);
		lblNewLabel_Process.setBounds(10, 290, 76, 20);
		lblNewLabel_Process.setText("Process name");
		lblNewLabel_Process.setAlignment(SWT.CENTER);
		formToolkit.adapt(lblNewLabel_Process, true, true);
		
		Composite compositeformal = new Composite(this, SWT.BORDER);
		FormData fd_compositeformal = new FormData();
		fd_compositeformal.top = new FormAttachment(label_1, 6);
		fd_compositeformal.left = new FormAttachment(compositesemiformal, 6);
		fd_compositeformal.bottom = new FormAttachment(0, 455);
		fd_compositeformal.right = new FormAttachment(0, 765);
		compositeformal.setLayoutData(fd_compositeformal);
		formToolkit.adapt(compositeformal);
		formToolkit.paintBordersFor(compositeformal);
		
		Label lblPre = new Label(compositeformal, SWT.NONE);
		lblPre.setText("pre");
		lblPre.setAlignment(SWT.CENTER);
		lblPre.setBounds(20, 147, 76, 20);
		formToolkit.adapt(lblPre, true, true);
		
		Label lblPost = new Label(compositeformal, SWT.NONE);
		lblPost.setText("Post");
		lblPost.setAlignment(SWT.CENTER);
		lblPost.setBounds(20, 243, 76, 20);
		formToolkit.adapt(lblPost, true, true);
		
		List Processlist = new List(compositeformal, SWT.BORDER);
		Processlist.setToolTipText("processname");
		Processlist.setBounds(20, 27, 139, 38);
		formToolkit.adapt(Processlist, true, true);
		
		StyledText styledText_postcondition = new StyledText(compositeformal, SWT.BORDER);
		styledText_postcondition.setBounds(90, 243, 69, 24);
		formToolkit.adapt(styledText_postcondition);
		formToolkit.paintBordersFor(styledText_postcondition);
		
		StyledText styledText_precondition = new StyledText(compositeformal, SWT.BORDER);
		styledText_precondition.setBounds(90, 147, 69, 24);
		formToolkit.adapt(styledText_precondition);
		formToolkit.paintBordersFor(styledText_precondition);
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem.setText("\u6587\u4EF6");
		
		Menu menu_1 = new Menu(mntmNewItem);
		mntmNewItem.setMenu(menu_1);
		
		MenuItem mntmNewItem_2 = new MenuItem(menu_1, SWT.CASCADE);
		mntmNewItem_2.setText("\u5BFC\u5165\u7279\u5F81\u6A21\u578B");
		
		Menu menu_2 = new Menu(mntmNewItem_2);
		mntmNewItem_2.setMenu(menu_2);
		
		MenuItem menuItem = new MenuItem(menu_2, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		menuItem.setText("\u9009\u62E9\u6587\u4EF6\u5939");
		
		MenuItem mntmNewItem_3 = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem_3.setText("\u5BFC\u51FA\u7279\u5F81\u6A21\u578B");
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem_1.setText("\u95EE\u9898\u68C0\u6D4B");
		
		Menu menu_3 = new Menu(mntmNewItem_1);
		mntmNewItem_1.setMenu(menu_3);
		
		MenuItem mntmSofl = new MenuItem(menu_3, SWT.NONE);
		mntmSofl.setText("SOFL\u9A8C\u8BC1");
		
		new MenuItem(menu, SWT.SEPARATOR);
		
		MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
		menuItem_1.setText("\u5E2E\u52A9");
		
		Composite compositesemiformal_1 = new Composite(this, SWT.BORDER);
		fd_compositesemiformal.left = new FormAttachment(compositesemiformal_1, 6);
		
		Button button_Processadd = new Button(compositesemiformal, SWT.NONE);
		button_Processadd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});

		button_Processadd.setBounds(107, 285, 69, 30);
		formToolkit.adapt(button_Processadd, true, true);
		button_Processadd.setText("\u6DFB\u52A0\u8FDB\u7A0B");
		
		StyledText styledText_Ext = new StyledText(compositesemiformal, SWT.BORDER);
		styledText_Ext.setBounds(107, 229, 69, 24);
		formToolkit.adapt(styledText_Ext);
		formToolkit.paintBordersFor(styledText_Ext);
		
		StyledText styledText_Modulename = new StyledText(compositesemiformal, SWT.BORDER);
		styledText_Modulename.setBounds(107, 37, 69, 24);
		formToolkit.adapt(styledText_Modulename);
		formToolkit.paintBordersFor(styledText_Modulename);
		
		StyledText styledText_Const = new StyledText(compositesemiformal, SWT.BORDER);
		styledText_Const.setBounds(107, 97, 69, 24);
		formToolkit.adapt(styledText_Const);
		formToolkit.paintBordersFor(styledText_Const);
		
		StyledText styledText_Type = new StyledText(compositesemiformal, SWT.BORDER);
		styledText_Type.setBounds(107, 170, 69, 24);
		formToolkit.adapt(styledText_Type);
		formToolkit.paintBordersFor(styledText_Type);
		compositesemiformal_1.setLayout(null);
		FormData fd_compositesemiformal_1 = new FormData();
		fd_compositesemiformal_1.right = new FormAttachment(100, -462);
		fd_compositesemiformal_1.left = new FormAttachment(btnNewButton_1, 6);
		fd_compositesemiformal_1.bottom = new FormAttachment(label_1_1, -6);
		fd_compositesemiformal_1.top = new FormAttachment(InformalButton, 6);
		compositesemiformal_1.setLayoutData(fd_compositesemiformal_1);
		formToolkit.adapt(compositesemiformal_1);
		formToolkit.paintBordersFor(compositesemiformal_1);
		
		Label lblConst_Database = new Label(compositesemiformal_1, SWT.NONE);
		lblConst_Database.setText("Database");
		lblConst_Database.setAlignment(SWT.CENTER);
		lblConst_Database.setBounds(10, 172, 76, 20);
		formToolkit.adapt(lblConst_Database, true, true);
		
		Label lblNewLabel_Operation = new Label(compositesemiformal_1, SWT.NONE);
		lblNewLabel_Operation.setText("Operation");
		lblNewLabel_Operation.setAlignment(SWT.CENTER);
		lblNewLabel_Operation.setBounds(10, 226, 76, 20);
		formToolkit.adapt(lblNewLabel_Operation, true, true);
		
		Label lblNewLabel_Featurename = new Label(compositesemiformal_1, SWT.NONE);
		lblNewLabel_Featurename.setText("Feature name");
		lblNewLabel_Featurename.setAlignment(SWT.CENTER);
		lblNewLabel_Featurename.setBounds(10, 108, 76, 20);
		formToolkit.adapt(lblNewLabel_Featurename, true, true);
		
		Label lblNewLabel_Constraint = new Label(compositesemiformal_1, SWT.NONE);
		lblNewLabel_Constraint.setText("Constraint");
		lblNewLabel_Constraint.setAlignment(SWT.CENTER);
		lblNewLabel_Constraint.setBounds(10, 278, 76, 20);
		formToolkit.adapt(lblNewLabel_Constraint, true, true);
		
		StyledText styledText_Featurename = new StyledText(compositesemiformal_1, SWT.BORDER);
		styledText_Featurename.setBounds(111, 108, 69, 24);
		formToolkit.adapt(styledText_Featurename);
		formToolkit.paintBordersFor(styledText_Featurename);
		
		StyledText styledText_Database = new StyledText(compositesemiformal_1, SWT.BORDER);
		styledText_Database.setBounds(111, 168, 69, 24);
		formToolkit.adapt(styledText_Database);
		formToolkit.paintBordersFor(styledText_Database);
		
		StyledText styledText_Operation = new StyledText(compositesemiformal_1, SWT.BORDER);
		styledText_Operation.setBounds(111, 222, 69, 24);
		formToolkit.adapt(styledText_Operation);
		formToolkit.paintBordersFor(styledText_Operation);
		
		StyledText styledText_Constraint = new StyledText(compositesemiformal_1, SWT.BORDER);
		styledText_Constraint.setBounds(111, 274, 69, 24);
		formToolkit.adapt(styledText_Constraint);
		formToolkit.paintBordersFor(styledText_Constraint);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Feature to SOFL");
		setSize(822, 542);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components 
	}
	
}


