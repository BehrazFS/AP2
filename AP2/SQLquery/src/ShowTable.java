
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShowTable extends JFrame{
	JTable mytable;
	public ShowTable(ArrayList<ArrayList<String>> newtable) {
		// TODO Auto-generated constructor stub
		try {
			String[] head = new String[newtable.size()];
			for(int i = 0;i<head.length;i++) {
				head[i] = "<< "+newtable.get(i).get(0)+" >>";
			}
			String[][] body = new String[newtable.get(0).size()-1][newtable.size()];
			for(int i = 0;i<newtable.size();i++) {
				for(int j = 0;j<newtable.get(i).size()-1;j++) {
					 body[j][i] = " " + newtable.get(i).get(j+1);
				}
			}
			mytable = new JTable(body,head);
			mytable.setLayout(new FlowLayout());
			mytable.setGridColor(Color.yellow);
			mytable.setBackground(Color.black);
			mytable.setForeground(Color.yellow);
			mytable.getTableHeader().setForeground(Color.yellow);
			mytable.getTableHeader().setBackground(Color.black);
			mytable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.yellow, 1, true));;
			mytable.getTableHeader().setFont(new Font("MV Boli", Font.PLAIN,14));
			JPanel panel = new JPanel();
			panel.setBackground(Color.black);
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS ));
			panel.add(mytable.getTableHeader());
			panel.add(mytable);
			JScrollPane scroll = new JScrollPane(panel);
			this.setTitle("Table");
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.add(scroll);
			this.pack();
			int maxSize = 500 , minSize = head.length * 120;
			this.setSize(Math.min(maxSize, Math.max(this.getWidth(),minSize)),Math.min(maxSize, this.getHeight()));
			this.setVisible(true);
		}
		catch (Exception e) {
		// TODO: handle exception
			QueryRunnerRegexBase.exception(e);
		}
	}
}
