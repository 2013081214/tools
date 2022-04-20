package diagrameditor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import java.awt.event.InputMethodListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.InputMethodEvent;
import javax.swing.event.ListSelectionListener;

import xmlfactory.Featuredatamodel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.JTree;

public class source extends JFrame {

	private JPanel contentPane;
	JDesktopPane table = new JDesktopPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Diagrammain frame = new Diagrammain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public source() {
		setTitle("SPLCombineSOFL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1124, 816);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\u6587\u4EF6");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_1 = new JMenu("\u5BFC\u5165\u7279\u5F81\u6A21\u578B");
		mnNewMenu.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("\u9009\u62E9\u6587\u4EF6\u4F4D\u7F6E");
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_2 = new JMenu("\u5BFC\u51FA\u7279\u5F81\u6A21\u578B");
		mnNewMenu.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("\u9009\u62E9\u4F4D\u7F6E");
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_3 = new JMenu("\u51B2\u7A81\u68C0\u6D4B");
		menuBar.add(mnNewMenu_3);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("\u68C0\u6D4B");
		mnNewMenu_3.add(mntmNewMenuItem);
		
		JMenu mnNewMenu_4 = new JMenu("\u5E2E\u52A9");
		menuBar.add(mnNewMenu_4);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("\u6253\u5F00\u5E2E\u52A9\u6587\u6863");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_3);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
				
		table = new JDesktopPane();
		table.setBackground(new Color(240, 255, 255));
		
		JButton InformalButton = new JButton("Informal");
		InformalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Informaldescription informaldescription = new Informaldescription();
				informaldescription.setVisible(true);
				table.add(informaldescription);
			}
		});
		
		JButton SemiformalButton = new JButton("Semiformal");
		SemiformalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Semiformaldescription semiformaldescription = new Semiformaldescription();
				semiformaldescription.setVisible(true);
				table.add(semiformaldescription);
			}
		});
		
		JButton FormalButton = new JButton("Formal");
		FormalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Formaldescription formaldescription = new Formaldescription();
				formaldescription.setVisible(true);
				table.add(formaldescription);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JTree tree = new JTree();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addGap(144)
							.addComponent(InformalButton)
							.addPreferredGap(ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
							.addComponent(SemiformalButton)
							.addGap(176)
							.addComponent(FormalButton, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
							.addGap(152))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(tree, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(table, GroupLayout.PREFERRED_SIZE, 919, GroupLayout.PREFERRED_SIZE))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(SemiformalButton, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
							.addComponent(FormalButton, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addComponent(InformalButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(tree, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(table, GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		Featuredatamodel featuredatamodel = new Featuredatamodel();		
		List<String> featurenameList = new ArrayList<>();
		featurenameList = featuredatamodel.Featuredatamodel();
		//System.out.print(featurenameList.size());
		Vector v = new Vector();
		for(int i = 0; i < featurenameList.size(); i++)
		{
			v.addElement(featurenameList.get(i));
		}
		JList listFeaturename = new JList(v);
		/*listFeaturename.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					Informaldescription.textFieldfeaturename.getText() = (String)listFeaturename.getSelectedValue();
				}
				
			}
		});*/
		
		listFeaturename.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(listFeaturename);
		listFeaturename.setFont(new Font("ËÎÌו", Font.PLAIN, 22));
		listFeaturename.setValueIsAdjusting(true);
		contentPane.setLayout(gl_contentPane);
		
	}
}
