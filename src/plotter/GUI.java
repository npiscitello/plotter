package plotter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import org.math.plot.*;

@SuppressWarnings({ "serial" })
public class GUI extends JFrame implements ActionListener {
	
		// resources and dynamic values
	private JButton plot = new JButton("plot");
	private JRadioButton para = new JRadioButton("Parametric", true);
	private JRadioButton cart = new JRadioButton("Cartesian");
	private JTextField ax = new JTextField(3);
	private JTextField vx = new JTextField(3);
	private JTextField px = new JTextField(3);
	private JTextField ay = new JTextField(3);
	private JTextField vy = new JTextField(3);
	private JTextField py = new JTextField(3);
	private JTextField xmax = new JTextField(3);
	private JLabel zx = new JLabel();
	private JLabel mx = new JLabel();
	private JLabel mtx = new JLabel();
	private JLabel zy = new JLabel();
	private JLabel my = new JLabel();
	private JLabel mty = new JLabel();
	private ImageIcon checkmark = new ImageIcon(getClass().getResource("checkmark-icon.png"));
	private ImageIcon xmark = new ImageIcon(getClass().getResource("x-icon.png"));
	private JLabel status = new JLabel();
	private Plot2DPanel plotwindow = new Plot2DPanel();
	
		// padding
	Insets pad = new Insets(2,2,2,2);
	Insets spacing = new Insets(0,20,0,20);
	Dimension vertspace = new Dimension(0,10);

	public GUI(String name, Dimension size, Dimension minsize) {
		
			// set button action events
		plot.setActionCommand("plot"); plot.addActionListener(this);
		para.setActionCommand("para"); para.addActionListener(this);
		cart.setActionCommand("cart"); cart.addActionListener(this);
	
			// numerical inputs
		JPanel inputs = new JPanel(new GridBagLayout());
		GridBagConstraints input_const = new GridBagConstraints();
		input_const.gridx=0; input_const.gridy=0; inputs.add(new JLabel("INPUTS"),input_const);
		input_const.gridx=1; inputs.add(new JLabel("Accel"));
		input_const.gridx=2; inputs.add(new JLabel("Velo"));
		input_const.gridx=3; inputs.add(new JLabel("Pos"));
		input_const.gridx=0; input_const.gridy=1; inputs.add(new JLabel("X"),input_const);
		input_const.gridx=1; inputs.add(ax,input_const);
		input_const.gridx=2; inputs.add(vx,input_const);
		input_const.gridx=3; inputs.add(px,input_const);
		input_const.gridx=0; input_const.gridy=2; inputs.add(new JLabel("Y"),input_const);
		input_const.gridx=1; inputs.add(ay,input_const);
		input_const.gridx=2; inputs.add(vy,input_const);
		input_const.gridx=3; inputs.add(py,input_const);
		input_const.gridx=4; input_const.gridy=1; input_const.gridheight=2; inputs.add(plot,input_const);
		
			// x max selection
		JPanel xmaxpanel = new JPanel();
		xmaxpanel.setLayout(new BoxLayout(xmaxpanel, BoxLayout.PAGE_AXIS));
		JLabel max = new JLabel("Max X:");
		max.setToolTipText("Used when there are no positive x-intercepts; must be positive. Overrides auto-generated zeroes.");
		xmaxpanel.add(max);
		xmaxpanel.add(xmax);
	
			// numerical outputs
		JPanel noutputs = new JPanel(new GridBagLayout());
		GridBagConstraints noutputs_const = new GridBagConstraints();
		noutputs_const.gridy=0; noutputs_const.insets = pad;
		noutputs_const.gridx=1; noutputs.add(new JLabel("Zero"), noutputs_const);
		noutputs_const.gridx=2; noutputs.add(new JLabel("Max"), noutputs_const);
		noutputs_const.gridx=3; noutputs.add(new JLabel("Max t"), noutputs_const);
		noutputs_const.gridx=0; noutputs_const.gridy=1; noutputs.add(new JLabel("X"), noutputs_const);
		noutputs_const.gridx=1; noutputs.add(zx, noutputs_const);
		noutputs_const.gridx=2; noutputs.add(mx, noutputs_const);
		noutputs_const.gridx=3; noutputs.add(mtx, noutputs_const);
		noutputs_const.gridx=0; noutputs_const.gridy=2; noutputs.add(new JLabel("Y"), noutputs_const);
		noutputs_const.gridx=1; noutputs.add(zy, noutputs_const);
		noutputs_const.gridx=2; noutputs.add(my, noutputs_const);
		noutputs_const.gridx=3; noutputs.add(mty, noutputs_const);
		
			// selection buttons
		ButtonGroup selection = new ButtonGroup();
		selection.add(para); selection.add(cart);
		JPanel buttons = new JPanel();
		buttons.add(para); buttons.add(cart);
		
		
			// pack components into the IO panel
		JPanel numberystuff = new JPanel(new GridBagLayout());
		GridBagConstraints numberystuff_const = new GridBagConstraints();
		numberystuff_const.gridx=0; numberystuff_const.insets=spacing; numberystuff.add(inputs,numberystuff_const);
		numberystuff_const.gridx=1; numberystuff_const.insets=spacing; numberystuff.add(xmaxpanel, numberystuff_const);
		numberystuff_const.gridx=2; numberystuff.add(noutputs,numberystuff_const);
		numberystuff_const.gridy=1; numberystuff_const.gridx=0; numberystuff_const.gridwidth=3;
			numberystuff.add(buttons, numberystuff_const);
		
			// pack components into the status bar
		JPanel status_bar = new JPanel(new GridBagLayout());
		GridBagConstraints status_const = new GridBagConstraints();
		status_const.gridx=2; status_const.weightx=1; status_const.insets=pad; status_const.anchor=GridBagConstraints.WEST;
		status_bar.add(status, status_const);
		
			// pack everything into the window
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.add(plotwindow);
		content.add(Box.createRigidArea(vertspace));
		numberystuff.setMaximumSize(new Dimension(Integer.MAX_VALUE, numberystuff.getPreferredSize().height));
			content.add(numberystuff);
		content.add(Box.createRigidArea(vertspace));
		status_bar.setMaximumSize(new Dimension(Integer.MAX_VALUE, status_bar.getPreferredSize().height));
			content.add(status_bar);
		getContentPane().add(content,BorderLayout.CENTER);
		
			// window properties
		setTitle(name);
		setSize(size);
		setMinimumSize(minsize);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
		// respond to button presses
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "plot") {
			Backend.plot();
		} else if(e.getActionCommand() == "para") {
			Backend.setParametric(true);
		} else if(e.getActionCommand() == "cart") {
			Backend.setParametric(false);
		}
	}
	
	public String[] getValues() {
		String[] values = {ax.getText(), vx.getText(), px.getText(), ay.getText(), vy.getText(), py.getText()};
		return values;
	}
	
	public String getXMax() {
		return xmax.getText();
	}
	
		// update the status bar
	public void updateStatus(boolean check, String message)	{
		status.setText(message);
		if(check) {
			status.setIcon(checkmark);
		} else {
			status.setIcon(xmark);
		}
	}
	
	public void updatePlot(double[][] vals, boolean parametric) {
		plotwindow.removeAllPlots();
		if(parametric) {
			plotwindow.addLinePlot("X Position", vals[0], vals[1]);
			plotwindow.addLinePlot("Y Position", vals[2], vals[3]);
		} else {
			plotwindow.addLinePlot("Position", vals[1], vals[3]);
		}
	}
	
	public void clearPlot() {
		plotwindow.removeAllPlots();
	}
	
	public void updateOutData(double[] vals) {
		zx.setText(String.valueOf(vals[0]));
		mx.setText(String.valueOf(vals[1])); mtx.setText(String.valueOf(vals[2]));
		zy.setText(String.valueOf(vals[3]));
		my.setText(String.valueOf(vals[4])); mty.setText(String.valueOf(vals[5]));
	}
}
