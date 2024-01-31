
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.JLabel;
import java.awt.Color;

import java.awt.Font;
import java.awt.FontFormatException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel backgroundLabel;
	private JButton yellowBtn;
	private JButton greenBtn;
	private JButton blueBtn;
	private JButton redBtn;
	private JButton joystick;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	private StyledDocument doc;
	private Thread currentTaskThread;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 * @throws FontFormatException
	 */
	public Gui() throws FontFormatException, IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 780);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(30, 30, 30));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		ImageIcon icon = new ImageIcon(Gui.class.getResource("/img/arcadeMachine.png"));
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(960, 960, Image.SCALE_SMOOTH);
		ImageIcon backgroundImg = new ImageIcon(changeImg);
		contentPane.setLayout(null);
		
		yellowBtn = createButton("Yellow", 450, 561, new Color(255, 255, 0));
		greenBtn = createButton("Green", 495, 546, new Color(0, 130, 0));
		blueBtn = createButton("Blue", 540, 561, new Color(0, 0, 255));
		redBtn = createButton("Red", 495, 576, new Color(255, 0, 0));

		contentPane.add(yellowBtn);
		contentPane.add(greenBtn);
		contentPane.add(blueBtn);
		contentPane.add(redBtn);

		joystick = new JButton();
		joystick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleButtonClick("joystick");

			}
		});
		joystick.setBounds(145, 470, 53, 53);
		joystick.setBackground(new Color(148, 17, 0));
		joystick.setOpaque(true);
		joystick.setBorderPainted(false);
		contentPane.add(joystick);

		textPane = new JTextPane();
		textPane.setBackground(Color.BLACK);
		textPane.setBounds(150, 220, 390, 270);
		textPane.setEditorKit(new StyledEditorKit());

		doc = textPane.getStyledDocument();
		Style defaultStyle = doc.addStyle("DefaultStyle", null);
		StyleConstants.setForeground(defaultStyle, Color.white);

		Style computerStyle = doc.addStyle("ComputerStyle", defaultStyle);
		StyleConstants.setForeground(computerStyle, new Color(0, 170, 170));

		scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(150, 185, 390, 272);
		scrollPane.setBackground(Color.BLACK);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		contentPane.add(scrollPane);

		InputStream is = Gui.class.getResourceAsStream("/font/neodgm.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, is);
		Font sizedFont = font.deriveFont(15f);
		textPane.setFont(sizedFont);

		PrintMessage printMessage = new PrintMessage(textPane, doc);
		printMessage.displayMessages(Messages.MSG_DEFAULT);

		// background Image
		backgroundLabel = new JLabel(backgroundImg);
		backgroundLabel.setBounds(-135, -139, 960, 1070);
		contentPane.add(backgroundLabel);
		backgroundLabel.setOpaque(false);

	}

	private JButton createButton(String text, int x, int y, Color background) {
		JButton button = new JButton();
		button.setBounds(x, y, 30, 15);
		button.setBackground(background);
		button.setOpaque(true);
		button.setBorderPainted(false);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleButtonClick(text);
			}
		});

		return button;
	}

	private void handleButtonClick(String buttonName) {
		PrintMessage printMessage = new PrintMessage(textPane, doc);
		if (currentTaskThread != null && currentTaskThread.isAlive()) {
			currentTaskThread.interrupt();
		}
		switch (buttonName.toLowerCase()) {
		case "yellow":
			printMessage.displayMessages(Messages.MSG_YELLOW);
			break;
		case "green":
			printMessage.displayMessages(Messages.MSG_GREEN);
			break;
		case "blue":
			printMessage.displayMessages(Messages.MSG_BLUE);
			break;
		case "red":
			printMessage.displayMessages(Messages.MSG_RED);
			break;
		case "joystick":
			printMessage.displayMessages(Messages.MSG_JOYSTICK);

			Timer exitTimer = new Timer(5000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			exitTimer.setRepeats(false);
			exitTimer.start();
			break;

		}

	}

}
