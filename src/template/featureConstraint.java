package template;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import net.miginfocom.swing.MigLayout;
import xmlfactory.Dom4jGetFeatureList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.UIManager;

public class featureConstraint extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					featureConstraint frame = new featureConstraint();
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
	public featureConstraint() {
		setClosable(true);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JLabel lblNewLabel = new JLabel("select feature");
		
		JLabel lblNewLabel_1 = new JLabel("relationship");
		
		JLabel lblNewLabel_2 = new JLabel("select feature");
		
		JList list_2 = new JList();
		list_2.setFont(new Font("宋体", Font.PLAIN, 20));
		list_2.setModel(new AbstractListModel() {
			String[] values = new String[] {"include", "exclude"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane_2.setViewportView(list_2);
		

		
		Dom4jGetFeatureList featuredatamodel = new Dom4jGetFeatureList();//从XML文件中读取所有前缀为featurename的叶子节点		
		List<String> featurenameList = new ArrayList<>();
		featurenameList = featuredatamodel.Dom4jGetFeatureList();
		Vector v = new Vector();
		for(int i = 0; i < featurenameList.size(); i++)
		{
			v.addElement(featurenameList.get(i));
		}
		JList list = new JList(v);
		
		JList list_1 = new JList(v);
		scrollPane_1.setViewportView(list_1);
		
		JButton btnNewButton = new JButton("confirm");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String firstFeature = (String) list.getSelectedValue();
				String secondFeature = (String) list_1.getSelectedValue();
				String constraintType = (String) list_2.getSelectedValue();
				String title = "Constraint";
				JDialog jdl = new JDialog();//弹出对话框
				//public JDialog(Frame frame, String title,  boolean model);
		        jdl.setSize(470, 200);//对话框大小
		        jdl.setLocation(200, 400);
		        jdl.setTitle(title);
		        String info = firstFeature+" "+constraintType+" "+secondFeature+" has been add";
				JLabel jl = new JLabel(info );
		        jdl.getContentPane().add(jl);
		        jdl.setVisible(true);//可见
			}
		});
		btnNewButton.setBackground(UIManager.getColor("activeCaption"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*try {
					Document doc = DocumentHelper.createDocument();
					
					Element root = doc.addElement("Constraint");
					//root.setText(newFeaturename);
					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("UTF-8");
					XMLWriter writer = new XMLWriter(new FileOutputStream("D:\\modelConstraint.xml"),format);
					writer.write(doc);
					writer.close();
					}catch (UnsupportedEncodingException e1) {
			            e1.printStackTrace();
			        } catch (FileNotFoundException e1) {
			            e1.printStackTrace();
			        } catch (IOException e1) {
			            e1.printStackTrace();
			        }
				*/
				String file = "D://modelConstraint.xml";
				String firstFeature = (String) list.getSelectedValue();
				String secondFeature = (String) list_1.getSelectedValue();
				String constraintType = (String) list_2.getSelectedValue();
				try{
					SAXReader saxReader=new SAXReader();
					Document doc=saxReader.read(file);
					Element rootElm = doc.getRootElement();//使用dom4j提供的API获得XML的根节点
					Element constraint = rootElm.addElement("Constraint");
					constraint.addAttribute("firstfeature", firstFeature);
					constraint.addAttribute("constraint", constraintType);
					constraint.addAttribute("secondfeature", secondFeature);
					
					OutputFormat format = OutputFormat.createPrettyPrint();
					format.setEncoding("UTF-8");
					XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
					//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
					writer.write(doc);
					writer.close();
					
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				
				
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
							.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
									.addGap(26)
									.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblNewLabel_2)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(14))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton)
							.addContainerGap())))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1))
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addGap(18)))
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		
		scrollPane.setViewportView(list);
		getContentPane().setLayout(groupLayout);
		setBounds(100, 100, 443, 239);

	}
}
