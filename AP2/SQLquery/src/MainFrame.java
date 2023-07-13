import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame implements MouseListener{
	JPanel panel;
	JButton button1,button2;
	JTextField text1,text2,temptext;
	JLabel label1,label2;
	public MainFrame() {
		try {
		// TODO Auto-generated constructor stub
			label1 = new JLabel();
			label1.setText("SQL Query Runner");
			label1.setForeground(Color.yellow);
			label1.setFont(new Font(null, Font.BOLD,30));
			label1.setHorizontalAlignment(JLabel.CENTER);
			label1.setBounds(100 , 50 , 300 , 80);
			label1.setOpaque(true);
			label1.setBackground(Color.black);
			label1.setBorder(BorderFactory.createLineBorder(Color.yellow, 4, true));
			
			text1 = new JTextField("command : ");
			text1.setBounds(30, 160, 300, 25);
			text1.setBorder(BorderFactory.createLineBorder(Color.yellow, 1, true));
			text1.setBackground(Color.black);
			text1.setForeground(Color.lightGray);
			text1.addMouseListener(this);
			
			temptext = new JTextField();
			temptext.setBounds(0, 0, 0, 0);
			temptext.setBackground(Color.black);
			temptext.setForeground(Color.black);
			temptext.grabFocus();
			
			button1 = new JButton();
			button1.setBounds(350,160,100,25);
			button1.setBorder(BorderFactory.createLineBorder(Color.yellow, 1, true));
			button1.setFocusable(false);
			button1.setText("Run");
			button1.setFont(new Font(null,Font.BOLD,10));
			button1.setBackground(Color.black);
			button1.setForeground(Color.yellow);
			button1.addMouseListener(this);
			
			text2 = new JTextField("file\\commands.txt");
			text2.setBounds(30,200, 300, 25);
			text2.setBorder(BorderFactory.createLineBorder(Color.yellow, 1, true));
			text2.setBackground(Color.black);
			text2.setForeground(Color.lightGray);
			text2.addMouseListener(this);
			
			button2 = new JButton();
			button2.setBounds(350,200,100,25);
			button2.setBorder(BorderFactory.createLineBorder(Color.yellow, 1, true));
			button2.setFocusable(false);
			button2.setText("Submit");
			button2.setFont(new Font(null,Font.BOLD,10));
			button2.setBackground(Color.black);
			button2.setForeground(Color.yellow);
			button2.addMouseListener(this);
			
			panel = new JPanel();
			panel.setBounds(0,0,500,500);
			panel.setOpaque(true);
			panel.setBackground(Color.black);
			panel.setLayout(null);
			panel.add(button1);
			panel.add(text1);
			panel.add(button2);
			panel.add(text2);
			panel.add(label1);
			panel.add(temptext);
			this.setTitle("SQLQueryRunner");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLayout(null);
			this.setSize(500,300);
			this.setResizable(false);
			this.add(panel);
			this.setVisible(true);
		}
		catch (Exception e) {
			// TODO: handle exception
			QueryRunnerRegexBase.exception(e);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			if(e.getSource() == button1) {
				QueryRunnerRegexBase.setQuery(text1.getText());
				QueryRunnerRegexBase.run();
			}
			if(e.getSource() == button2) {
				button1.setEnabled(false);
				File myFile = new File(text2.getText());
				Scanner fReader = new Scanner(myFile);
				String command = "";
				while(fReader.hasNextLine()) {
					command+=fReader.nextLine();
				}
				String[] commands = command.split(";");
				for(String cmd : commands) {
					QueryRunnerRegexBase.setQuery(cmd +";");
					QueryRunnerRegexBase.run();
				}
				fReader.close();
				button1.setEnabled(true);
			}
			if(e.getSource() == text1) {
				text1.setText("");
				text1.setForeground(Color.cyan);
			}
			if(e.getSource() == text2) {
				text2.setText("");
				text2.setForeground(Color.cyan);
			}
			
		}
		catch (Exception ex) {
			// TODO: handle exception
			QueryRunnerRegexBase.exception(ex);
			button1.setEnabled(true);
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			if(e.getSource() == button1) {
				button1.setBackground(Color.lightGray);
				button1.setForeground(Color.red);
			}
			if(e.getSource() == button2) {
				button2.setBackground(Color.lightGray);
				button2.setForeground(Color.red);
			}
			
		}
		catch (Exception ex) {
			// TODO: handle exception
			QueryRunnerRegexBase.exception(ex);
			
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			if(e.getSource() == button1) {
				button1.setBackground(Color.black);
				button1.setForeground(Color.yellow);
			}
			if(e.getSource() == button2) {
				button2.setBackground(Color.black);
				button2.setForeground(Color.yellow);
			}
		}
		catch (Exception ex) {
			// TODO: handle exception
			QueryRunnerRegexBase.exception(ex);
			
		}
	}
}