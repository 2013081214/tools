package diagrameditor;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class FormalDes extends JInternalFrame {
	private JTextField textFieldFeatureName;
	private JTextField textFieldModuleName;
	private JTextField textFieldDecomModule;
	private JTextField textFieldProcessName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormalDes frame = new FormalDes();
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
	public FormalDes() {
		getContentPane().setBackground(UIManager.getColor("controlHighlight"));
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Feature name");
		lblNewLabel.setBounds(14, 13, 122, 18);
		getContentPane().add(lblNewLabel);
		
		textFieldFeatureName = new JTextField();
		textFieldFeatureName.setBounds(170, 10, 204, 24);
		getContentPane().add(textFieldFeatureName);
		textFieldFeatureName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Module name");
		lblNewLabel_1.setBounds(14, 58, 122, 18);
		getContentPane().add(lblNewLabel_1);
		
		textFieldModuleName = new JTextField();
		textFieldModuleName.setBounds(170, 55, 204, 24);
		getContentPane().add(textFieldModuleName);
		textFieldModuleName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("consts");
		lblNewLabel_2.setBounds(64, 138, 72, 18);
		getContentPane().add(lblNewLabel_2);
		
		JTextArea textAreaConsts = new JTextArea();
		textAreaConsts.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaConsts.setBounds(170, 100, 204, 87);
		getContentPane().add(textAreaConsts);
		
		JLabel lblNewLabel_3 = new JLabel("types");
		lblNewLabel_3.setBounds(64, 245, 72, 18);
		getContentPane().add(lblNewLabel_3);
		
		JTextArea textAreaTypes = new JTextArea();
		textAreaTypes.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaTypes.setBounds(170, 213, 204, 94);
		getContentPane().add(textAreaTypes);
		
		JLabel lblNewLabel_4 = new JLabel("var");
		lblNewLabel_4.setBounds(64, 371, 72, 18);
		getContentPane().add(lblNewLabel_4);
		
		JTextArea textAreaVar = new JTextArea();
		textAreaVar.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaVar.setBounds(170, 339, 204, 94);
		getContentPane().add(textAreaVar);
		
		JLabel lblNewLabel_5 = new JLabel("Decom module name");
		lblNewLabel_5.setBounds(14, 452, 143, 18);
		getContentPane().add(lblNewLabel_5);
		
		textFieldDecomModule = new JTextField();
		textFieldDecomModule.setBounds(170, 449, 204, 24);
		getContentPane().add(textFieldDecomModule);
		textFieldDecomModule.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Process name");
		lblNewLabel_6.setBounds(425, 13, 113, 18);
		getContentPane().add(lblNewLabel_6);
		
		textFieldProcessName = new JTextField();
		textFieldProcessName.setColumns(10);
		textFieldProcessName.setBounds(552, 10, 204, 24);
		getContentPane().add(textFieldProcessName);
		
		JLabel lblNewLabel_2_1 = new JLabel("inputs");
		lblNewLabel_2_1.setBounds(466, 75, 72, 18);
		getContentPane().add(lblNewLabel_2_1);
		
		JTextArea textAreaInputs = new JTextArea();
		textAreaInputs.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaInputs.setBounds(552, 56, 204, 60);
		getContentPane().add(textAreaInputs);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("outputs");
		lblNewLabel_2_1_1.setBounds(466, 154, 72, 18);
		getContentPane().add(lblNewLabel_2_1_1);
		
		JLabel lblNewLabel_2_1_2 = new JLabel("pre");
		lblNewLabel_2_1_2.setBounds(466, 245, 72, 18);
		getContentPane().add(lblNewLabel_2_1_2);
		
		JLabel lblNewLabel_2_1_3 = new JLabel("post");
		lblNewLabel_2_1_3.setBounds(466, 341, 72, 18);
		getContentPane().add(lblNewLabel_2_1_3);
		
		JTextArea textAreaOutputs = new JTextArea();
		textAreaOutputs.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaOutputs.setBounds(552, 136, 204, 60);
		getContentPane().add(textAreaOutputs);
		
		JTextArea textAreaPre = new JTextArea();
		textAreaPre.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaPre.setBounds(552, 213, 204, 87);
		getContentPane().add(textAreaPre);
		
		JTextArea textAreaPost = new JTextArea();
		textAreaPost.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaPost.setBounds(552, 368, 204, 102);
		getContentPane().add(textAreaPost);
		
		JButton btnNewButton = new JButton("save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDataToformalFile(new File("D:\\model.formal"));
			}

			private void saveDataToformalFile(File file) {
				try{	
					Document doc = DocumentHelper.createDocument();
					
					Element root = doc.addElement("featureFormalDes");

					String featureName = textFieldFeatureName.getText();
					Element fName = root.addElement("feature").addAttribute("name", featureName);
					
					String Modulename = textFieldModuleName.getText();
					Element moduleName = fName.addElement("module").addAttribute("name", Modulename);
					
					Element consts = fName.addElement("consts");
					String Consts = textAreaConsts.getText();
					consts.setText(Consts);
					
					Element types = fName.addElement("types");
					String Types = textAreaTypes.getText();
					types.setText(Types);
					
					Element vars = fName.addElement("vars");
					String Vars = textAreaVar.getText();
					vars.setText(Vars);
					
					String Processname = textFieldProcessName.getText();
					Element processName = fName.addElement("process").addAttribute("name", Processname);;
					
					Element inputs = processName.addElement("inputs");
					String.valueOf(textAreaInputs.getText());
					String Inputs = textAreaInputs.getText();
					inputs.setText(Inputs);
			
					Element outputs = processName.addElement("outputs");
					String Outputs = textAreaOutputs.getText();
					outputs.setText(Outputs);
					
					Element pre = processName.addElement("pre");
					String Pre = textAreaPre.getText();
					pre.setText(Pre);
					
					Element post = processName.addElement("post");
					//String Post = textAreaPost.getText();
					//post.setText(Post);
					
					int linenum = textAreaPost.getLineCount();
					int num = linenum/2;
					String[] lineString = textAreaPost.getText().split("\n");
					for(int i = 0 ; i< num ; i++) {
						int j = 0;
						Element Scenarios = post.addElement("scenarios");
						Element C = Scenarios.addElement("C");
						C.setText(lineString[j]);
						j++;
						Element D = Scenarios.addElement("D");
						D.setText(lineString[j]);
						j++;
					}
					
					Element preProcess =processName.addElement("preProcess");
					
					Element postProcess = processName.addElement("postProcess");
					
					String Decompositonmodulename = textFieldDecomModule.getText();
					Element decompositonmodulename = processName.addElement("decompositonmodule").addAttribute("name", Decompositonmodulename);	
					
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
		btnNewButton.setBounds(233, 498, 113, 27);
		getContentPane().add(btnNewButton);
		
		JButton btnAddProcess = new JButton("add process");
		btnAddProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tagName = textFieldFeatureName.getText();
				try{
					String file = "D:\\model.formal";
					Document doc;
					Element root;
					SAXReader saxReader=new SAXReader(); 
		            doc=saxReader.read(file);     
		            root = doc.getRootElement();		            
		            for(Iterator i = root.elementIterator();i.hasNext();) {
		            	Element childEle = (Element) i.next();
		            	if(childEle.attributeValue("name").equals(tagName)) {   		       
		            		String Processname = textFieldProcessName.getText();
							Element processName = childEle.addElement("process").addAttribute("name", Processname);;
							
							Element inputs = processName.addElement("inputs");
							String.valueOf(textAreaInputs.getText());
							String Inputs = textAreaInputs.getText();
							inputs.setText(Inputs);
					
							Element outputs = processName.addElement("outputs");
							String Outputs = textAreaOutputs.getText();
							outputs.setText(Outputs);
							
							Element pre = processName.addElement("pre");
							String Pre = textAreaPre.getText();
							pre.setText(Pre);
							
							Element post = processName.addElement("post");
							String Post = textAreaPost.getText();
							post.setText(Post);
							
		            		OutputFormat format = OutputFormat.createPrettyPrint();
							format.setEncoding("UTF-8");
							XMLWriter writer = new XMLWriter(new FileOutputStream(file),format);
							//Writer writer = new OutputStreamWriter(new FileOutputStream(file),"format");
							writer.write(doc);
							writer.close();
		            	}
		            }
				}catch (Exception e1) {
			        e1.printStackTrace();
				}
			}
		});
		btnAddProcess.setBounds(565, 498, 150, 27);
		getContentPane().add(btnAddProcess);
		
		JButton btnNewButton_1 = new JButton("Add Post");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop = new JOptionPane();
				String m = JOptionPane.showInputDialog(null, "Please input Condition and Separated by ,:\n", "Post", JOptionPane.PLAIN_MESSAGE);
				jop.setLocation(800,500);
				jop.setVisible(true);
				String [] x = m.split(",");
				textAreaPost.append(x[0]+"\r\n");
				textAreaPost.append(x[1]+"\r\n");
		        /*System.out.println(title);
				String title = "Input Guard and Defining Condition";
				JDialog jdl = new JDialog();//弹出对话框
				//public JDialog(Frame frame, String title,  boolean model);
		        jdl.setSize(470, 200);//对话框大小
		        jdl.setLocation(800, 500);
		        jdl.setTitle(title);
		        String info1 = "Guard Condition";
		        String info2 = "Defining Condition";
				JLabel jl = new JLabel(info1);
				jl.setBounds(0,-20,100,100);
				
				JTextField t1 = new JTextField();
				t1.setBounds(100, -20, 200, 100);
				
				JLabel j2 = new JLabel(info2);
				j2.setBounds(40,40,100,100);
				
				
				JTextField t2 = new JTextField();
				t2.setBounds(40, 40, 200, 100);
				//JButton jb = new JButton("confirm");
				//jb.setBounds(60,100,60,30);
				
		        jdl.add(jl);
		        jdl.add(j2);
		        jdl.add(t1);
		        jdl.add(t2);
		       // jdl.add(jb);
		        jdl.setVisible(true);//可见
		        //jdl.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);*/
			}
		});
		btnNewButton_1.setBounds(552, 337, 113, 27);
		getContentPane().add(btnNewButton_1);
		
		setTitle("FormalDes");
		setBounds(100, 100, 896, 574);

	}
}
