// EBOOKS LIBRARY

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.io.*;
import javax.imageio.*;
import java.awt.Desktop;

class EbooksLib
{
public static void main(String args[])
{
//Showing main screen
JFrame frame = new JFrame("EBOOKS LIBRARY");
frame.setSize(600, 600);
frame.setLocation(500,200);
frame.add(new mainPanel());
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setVisible(true);
}
}

class mainPanel extends JPanel
{
	//Main screen
	JButton btnnew, btnshow, btnexit;
	public mainPanel()
	{
	btnnew = new JButton("Add New Ebook Entry");
	btnshow = new JButton("Show and Explore Ebooks");
	btnexit = new JButton("Exit");

	setLayout(new BorderLayout());
	JPanel p = new JPanel();
	p.setBackground(Color.CYAN);
	p.add(btnnew);
	p.add(btnshow);
	p.add(btnexit);
	add(p, BorderLayout.CENTER);

	btnnew.addActionListener(new
		ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
			JFrame deframe = new JFrame("Data Entry for new eBooks");
			deframe.setSize(700, 600);
			deframe.setLocation(500,200);
			deframe.add(new panel());
			deframe.setVisible(true);
		}
		});

	btnshow.addActionListener(new
		ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
		JFrame showframe = new JFrame("Show and Explore Ebooks on various subjects");
		showframe.setSize(800, 800);
		showframe.setLocation(500,200);
		showframe.add(new showpanel());
		showframe.setVisible(true);
		}
	});

	btnexit.addActionListener(new
			ActionListener()
			{
			public void actionPerformed(ActionEvent event)
			{
			System.exit(0);
			}
	});
	}
}

class panel extends JPanel
{
	//EBOOK Data Entry screen
	JTextField txtebname, txtedes, txtepgs, txtelink, txteimg;
	JLabel lblname, lbledes, lblepgs, lblid, lblelink, lblimg;
	JComboBox lstid;
	JButton btnadd, btnsave, btnexit;

	public panel()
	{
	 lblid = new JLabel      ("EBook Type/Category: ");
	 lblname = new JLabel    ("EBook name         : ");
	 lbledes = new JLabel 	 ("Description        : ");
	 lblepgs = new JLabel	 ("No.of Pages        : ");
	 lblelink = new JLabel   ("EBook Link         : ");
	 lblimg = new JLabel     ("EBook Cover Path   : ");

	 txtebname = new JTextField(20);
	 txtedes = new JTextField(20);
	 txtepgs = new JTextField(20);
	 txtelink = new JTextField(20);
	 txteimg = new JTextField(20);
	 lstid = new JComboBox();

	 lstid.setEditable(false);
	 lstid.addItem("Arts");
	 lstid.addItem("Biography");
	 lstid.addItem("Computers and IT");
	 lstid.addItem("Fiction");
	 lstid.addItem("Literature");
	 lstid.addItem("Management");

	 btnadd = new JButton("New");
	 btnsave = new JButton("Save");
	 btnexit = new JButton("Exit");

	 setLayout(new BorderLayout());
	 JPanel p = new JPanel();
	 p.setBackground(Color.CYAN);
	 JLabel lbl = new JLabel("-----------------New EBooks Data Entry----------------");
	 lbl.setFont(new Font("Courier", Font.BOLD, 22));
	 p.add(lbl);
	 p.add(lblname);
	 p.add(txtebname);
	 p.add(lbledes);
	 p.add(txtedes);
	 p.add(lblepgs);
	 p.add(txtepgs);
	 p.add(lblelink);
	 p.add(txtelink);
	 p.add(lblimg);
	 p.add(txteimg);
	 p.add(lblid);
	 p.add(lstid);
	 add(p, BorderLayout.CENTER);

	 JPanel p1 = new JPanel();
	 p1.add(btnadd);
	 p1.add(btnsave);
	 p1.add(btnexit);
	 add(p1, BorderLayout.SOUTH);

	 btnadd.addActionListener(new
		ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
		txtebname.setText("");
		txtedes.setText("");
		txtepgs.setText("");
		txtelink.setText("");
		txteimg.setText("");
		}
		});

	btnsave.addActionListener(new
		ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
		Connection con = null;
	  	try
	  	{
		 long eid = 0;
		 int epgs;
		 String ename, edes, elink, eimg;
		 String ecat = (String) lstid.getSelectedItem();
		 if (ecat == "Computers and IT") eid = 1;
		 else if (ecat == "Literature") eid = 2;
		 else if (ecat == "Management") eid = 3;
		 else if (ecat == "Arts") eid = 4;
		 else if (ecat == "Biography") eid = 5;
		 else if (ecat == "Fiction") eid = 6;
		 ename = txtebname.getText();
		 edes =  txtedes.getText();
		 epgs = Integer.parseInt(txtepgs.getText());
		 elink = txtelink.getText();
		 eimg = txteimg.getText();
		 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con = DriverManager.getConnection("jdbc:odbc:dsnebook","","");
		 PreparedStatement pst = con.prepareStatement
		 ("INSERT INTO EBOOKS " + "VALUES (?, ?, ?, ?, ?, ?, ?)");
		 pst.setLong(0, 999);
		 pst.setString(1, ename);
		 pst.setString(2, edes);
		 pst.setLong(3, eid);
		 pst.setInt(4, epgs);
		 pst.setString(5, elink);
		 pst.setString(6, eimg);
		 pst.executeUpdate();
		 JOptionPane.showMessageDialog(null,"A new EBook is recorded...");
	   }
	  catch (SQLException e){}
	  catch (Exception e1){}
	  finally {
	    if (con != null)
	      {
		   try {
	           con.close();
	      	   }
	      catch (SQLException e2) {}

	      }
		       }
		}
		});
	btnexit.addActionListener(new
		ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
		System.exit(0);
		}
		});
	}
}

class showpanel extends JPanel
{
	//Showing choices of categories and Ebooks and allow SEARCH
	String sql1 = "";
	public static String selected_ebook = "";
	JComboBox lstcat, lstbook;
	JLabel lblcat, lblbook, lblhead;
	JButton btnshow, btnexit;
	public showpanel()
	{
	btnshow = new JButton("Show Selected Book...");
	btnexit = new JButton("Exit from EBooks Library...");
	lstcat = new JComboBox();
	lstcat.setEditable(false);
	lstbook = new JComboBox();
	lstbook.setEditable(false);
   	lblcat = new JLabel ("---------------------Select Category----------------");
	lblbook = new JLabel("------------------------Select EBook-----------------------");
	lblcat.setFont(new Font("Courier", Font.BOLD, 22));
    lblbook.setFont(new Font("Courier", Font.BOLD, 22));
	setLayout(new BorderLayout());
	JPanel p1 = new JPanel();
	p1.setBackground(Color.CYAN);
	p1.add(btnshow);
	p1.add(btnexit);
	add(p1, BorderLayout.SOUTH);
	Connection con = null;
	Statement stmt = null;
	try
	  	{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con = DriverManager.getConnection("jdbc:odbc:dsnebook","","");
		stmt = con.createStatement();
		String sql = "SELECT CNAME FROM CATEGORY";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next())
			lstcat.addItem(rs.getString("CNAME"));
	    rs.close();
		JPanel p2 = new JPanel();
		p2.setBackground(Color.CYAN);
		p2.add(lblcat);
		p2.add(lstcat);
		p2.add(lblbook);
		p2.add(lstbook);
		add(p2, BorderLayout.CENTER);
	    }
 	catch (Exception e1){}
    finally
	  	{
		 if (con != null)
		  {
		  try
		  {
		  con.close();
		  }
		  catch (SQLException e2) {}
		  }
		 }

	lstcat.addActionListener(new
		ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
		String cat = (String) lstcat.getSelectedItem();
		if (cat.compareTo("COMPUTERS AND INFORMATION TECHNOLOGY") == 0)
			sql1 = "SELECT EBOOK_NAME FROM EBOOKS WHERE ID = (SELECT ID FROM CATEGORY WHERE CNAME = 'COMPUTERS AND INFORMATION TECHNOLOGY')";
		else if (cat.compareTo("LITERATURE") == 0)
			sql1 = "SELECT EBOOK_NAME FROM EBOOKS WHERE ID = (SELECT ID FROM CATEGORY WHERE CNAME = 'LITERATURE')";
		else if (cat.compareTo("MANAGEMENT") == 0)
			sql1 = "SELECT EBOOK_NAME FROM EBOOKS WHERE ID = (SELECT ID FROM CATEGORY WHERE CNAME = 'MANAGEMENT')";
		else if (cat.compareTo("ARTS") == 0)
			sql1 = "SELECT EBOOK_NAME FROM EBOOKS WHERE ID = (SELECT ID FROM CATEGORY WHERE CNAME = 'ARTS')";
		else if (cat.compareTo("BIOGRAPHY") == 0)
			sql1 = "SELECT EBOOK_NAME FROM EBOOKS WHERE ID = (SELECT ID FROM CATEGORY WHERE CNAME = 'BIOGRAPHY')";
		else if (cat.compareTo("FICTIONS") == 0)
			sql1 = "SELECT EBOOK_NAME FROM EBOOKS WHERE ID = (SELECT ID FROM CATEGORY WHERE CNAME = 'FICTIONS')";
		Connection con = null;
		Statement stmt = null;
		try
			{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:dsnebook","","");
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql1);
			lstbook.removeAllItems();
			//lstbook.setEnabled(false);
			while (rs.next())
				lstbook.addItem(rs.getString("EBOOK_NAME"));
			rs.close();
			}
		catch (Exception e1){}
		finally
			{
			 if (con != null)
			  {
			  try
			  {
			  con.close();
			  }
			  catch (SQLException e2) {}
			  }
			 }
	}
	});

	btnshow.addActionListener(new
			ActionListener()
			{
			public void actionPerformed(ActionEvent event)
			{
			selected_ebook = (String) lstbook.getSelectedItem();
			JFrame readframe = new JFrame("Start reading selected EBook");
			readframe.setSize(800, 800);
			readframe.setLocation(500,200);
			readframe.add(new readpanel());
			readframe.setVisible(true);
			}
		});

	btnexit.addActionListener(new
		ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
		System.exit(0);
		}
		});
	}
}

class readpanel extends JPanel
{
	//This code shows cover of selected EBook and allows to read it upon clicking the button.
	JButton btn, btnmore, btnexit;
	Image img;
	public void paintComponent(Graphics g)
	{
	super.paintComponent(g);
	g.drawImage(img, 200, 80, 400, 500, null);
	}
	public readpanel()
	{
	btn = new JButton("View Cover Page...");
	btnmore = new JButton("More info about this book...");
	btnexit = new JButton("Exit from EBook Library...");
	setLayout(new BorderLayout());
	JPanel p1 = new JPanel();
	setBackground(Color.CYAN);
	p1.add(btn);
	p1.add(btnmore);
	p1.add(btnexit);
	add(p1, BorderLayout.SOUTH);
	btn.addActionListener(new
		ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
		Connection con = null;
		Statement stmt = null;
	  	try
	  	{
		 String imgpath = "";
		 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		 con = DriverManager.getConnection("jdbc:odbc:dsnebook","","");
		 stmt = con.createStatement();
		 String sql = "SELECT * FROM EBOOKS WHERE EBOOK_NAME = '" + showpanel.selected_ebook + "'";
		 ResultSet rs = stmt.executeQuery(sql);
	     if (rs.next())
	     {
			/*ebookdes = rs.getString("DESCRN");
			ebookpath = rs.getString("EBOOK_LINK");
			pages = rs.getInt("NO_OF_PAGES");*/
		    imgpath = rs.getString("IMAGE1");
		 	img = ImageIO.read(new File(imgpath));
			repaint();
	     }
	     rs.close();
	     }
         catch (SQLException e){}
	  	 catch (Exception e1){}

	  	 finally
	  	 {
	         if (con != null)
	    	  {
		 	  try
		 	  {
	          con.close();
	      	  }
	      	  catch (SQLException e2) {}
	      	  }
		 }
		}
		});

	btnmore.addActionListener(new
		ActionListener()
		{
		public void actionPerformed(ActionEvent event)
		{
		Connection con = null;
		Statement stmt = null;
	  	File file;
	  	Desktop desktop;
	  	try
	  	{
   			String ebookdes, ebookpath ;
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			con = DriverManager.getConnection("jdbc:odbc:dsnebook","","");
		 	stmt = con.createStatement();
		 	String sql = "SELECT * FROM EBOOKS WHERE EBOOK_NAME = '" + showpanel.selected_ebook + "'";
		 	ResultSet rs = stmt.executeQuery(sql);
		 	if (rs.next())
	     	{
			ebookdes = rs.getString("DESCRN");
			ebookpath = rs.getString("EBOOK_LINK");
			//JOptionPane.showMessageDialog(null,showpanel.selected_ebook + ", " + ebookdes);
			JOptionPane.showMessageDialog(null,showpanel.selected_ebook + ", " + ebookdes,"More info...", JOptionPane.PLAIN_MESSAGE);

			file = new File(ebookpath);
			/*if(!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not
			{
			JOptionPane.showMessageDialog(null,"not supported");
			System.exit(0);
			}*/
			desktop = Desktop.getDesktop();
			if(file.exists())         //checks file exists or not
			desktop.open(file);              //opens the specified file
	     	}
	     	rs.close();
	     }
         catch (SQLException e){}
	  	 catch (Exception e1){}

	  	 finally
	  	 {
	         if (con != null)
	    	  {
		 	  try
		 	  {
	          con.close();
	      	  }
	      	  catch (SQLException e2) {}
	      	  }
		 }
		}
		});

		btnexit.addActionListener(new
			ActionListener()
			{
			public void actionPerformed(ActionEvent event)
			{
			System.exit(0);
			}
		});
}
}