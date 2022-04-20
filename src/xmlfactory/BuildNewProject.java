package xmlfactory;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BuildNewProject extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_featurename;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BuildNewProject dialog = new BuildNewProject();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BuildNewProject() {
		setTitle(" Create a new project");
		setBounds(100, 100, 447, 197);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Dictionary");
			lblNewLabel.setBounds(14, 32, 96, 18);
			contentPanel.add(lblNewLabel);
		}
		
		JLabel lblNewLabel = new JLabel("Product name");
		lblNewLabel.setBounds(14, 82, 96, 18);
		contentPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(113, 29, 194, 24);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		textField_featurename = new JTextField();
		textField_featurename.setColumns(10);
		textField_featurename.setBounds(112, 79, 303, 24);
		contentPanel.add(textField_featurename);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser("D:\\");
				FileSystemView fsv = FileSystemView.getFileSystemView();
				chooser.setCurrentDirectory(fsv.getHomeDirectory());
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setDialogTitle("Select folder");
				int result = chooser.showOpenDialog(chooser);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					String filepath = file.getAbsolutePath();
					textField.setText(filepath);// 将文件路径设到JTextField
				}
			}
		});
		btnNewButton.setBounds(307, 28, 108, 27);
		contentPanel.add(btnNewButton);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{				
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textField_featurename != null) {
							JTree tree = new JTree();
							tree.getModel();
							String newFeaturename = textField_featurename.getText();
							tree.setModel(new DefaultTreeModel(
								new DefaultMutableTreeNode(newFeaturename) {
									{
										
									}
								}
							));
							String filepath = textField.getText();
							saveDataTofeaturFile(new File("D:\\model.xml"));
							try {
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
							}
							//saveToXmlFile(tree.getModel().getRoot(),textField);
						
					}

					private void saveDataTofeaturFile(File file) {
						try {
						Document doc = DocumentHelper.createDocument();
						String newFeaturename = textField_featurename.getText();
						String type = "Multiple";
						
						Element root = doc.addElement("feature").addAttribute("NodeName", newFeaturename);
						root.addAttribute("NodeType", type);
						//root.setText(newFeaturename);
						OutputFormat format = OutputFormat.createPrettyPrint();
						format.setEncoding("UTF-8");
						XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
						writer.write(doc);
						writer.close();
						}catch (UnsupportedEncodingException e) {
				            e.printStackTrace();
				        } catch (FileNotFoundException e) {
				            e.printStackTrace();
				        } catch (IOException e) {
				            e.printStackTrace();
				        }
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BuildNewProject.setDefaultLookAndFeelDecorated(isDefaultLookAndFeelDecorated());;
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	public static void saveToXmlFile(DefaultMutableTreeNode rootNode, FileWriter fw)
    {
        try {
            String space = "";
            //做自动缩进
            for(int i = rootNode.getPath().length; i>1; i --)
            {
                space = space+"    ";
            }
            if(rootNode.isLeaf()) 
                {
                    //如果是叶子节点，则直接写入文件； leaftype是我自己的需要指定叶子节点在xml中的属性，不需要的可以删除该参数；
                    //fw.write(space+"<"+leafType+">\r\n");
                    String[]testCase = rootNode.toString().split("\n");
                    for(int i =0;i<testCase.length;i++)
                    {
                        fw.write(space+"    "+testCase[i]+"\r\n");
                    }
                    //fw.write(space+"</"+leafType+">\r\n");
                }
            else{
                //不是叶子节点，则递归遍历；
                fw.write(space + "<"+rootNode.toString()+">\r\n");
                for(int i = 0; i<rootNode.getChildCount(); i++)
                {
                    DefaultMutableTreeNode childeNode = (DefaultMutableTreeNode)rootNode.getChildAt(i);
                    //saveToXmlFile(childeNode, leafType, fw);
                }
                fw.write(space + "</"+rootNode.toString()+">\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
