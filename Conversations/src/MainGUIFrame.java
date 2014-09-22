

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Main UI and Event driven part of the demo conversation app
 *
 * @author David Boivin (Spit)
 */
@SuppressWarnings("serial")
public class MainGUIFrame extends JFrame{
	
// Constants --------------------------------------------------------------- //
	
	public final String CHARACTER_ONE = "Boy";
	public final String CHARACTER_TWO = "Girl";
	public final String ENTER = "Send";
	
	private static final String FRAME_TITLE = "Conversation";
	private static final int FRAME_HEIGHT = 800;
	private static final int FRAME_WIDTH = 400;
	private static final int PADDING = 10;
	private static final Dimension PREF_MESSAGE_DIMEN = new Dimension(350, 500);
	private static final Dimension PREF_BUTTON_DIMEN = new Dimension(75, 20);
	private static final Dimension PREF_TEXT_DIMEN = new Dimension(200, 100);
	
// Members ----------------------------------------------------------------- //
	
	private JButton mCharacter1;
	private JButton mCharacter2;
	private JButton mEnter;
	private JTextArea mMessageArea;
	private JTextArea mCharacterInputText;
	private JLabel mCharacterLabel;
	private JPanel mSelectCharacterPanel;
	
// Constructors ------------------------------------------------------------ //
	
	/**
	 * Constructor which builds the entire UI within this instance frame.
	 */
	public MainGUIFrame(){
		super(FRAME_TITLE);
		
		//set up the basics of the frame
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//set layout manager
		this.setLayout(new GridBagLayout());
		
		//set a key dispatcher to allow for a frame wide send operation
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new Dispatcher());
		
		//setting up the UI components
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(PADDING, PADDING, PADDING, PADDING);
		
		//***********************************MessageArea
		mMessageArea = new JTextArea();
		mMessageArea.setEditable(false);
		mMessageArea.setPreferredSize(PREF_MESSAGE_DIMEN);
		mMessageArea.setLineWrap(true);
		
		//gridbag settings
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.CENTER;
		
		this.add(new JScrollPane(mMessageArea), c);
		
		//reset grid width to default value
		c.gridwidth = 1;
		
		//************************************SelectionPanel, Buttons
		mSelectCharacterPanel = new JPanel(new GridLayout(2,1, PADDING, PADDING));
		
		mCharacter1 = new JButton(CHARACTER_ONE);
		mCharacter1.setPreferredSize(PREF_BUTTON_DIMEN);
		mCharacter1.addActionListener(new ChangeCharacterListener());
		mSelectCharacterPanel.add(mCharacter1, 0);
		mCharacter2 = new JButton(CHARACTER_TWO);
		mCharacter2.setPreferredSize(PREF_BUTTON_DIMEN);
		mCharacter2.addActionListener(new ChangeCharacterListener());
		mSelectCharacterPanel.add(mCharacter2, 1);
		
		//gridbag settings
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.CENTER;
		
		this.add(mSelectCharacterPanel, c);
		
		//reset grid height to default
		c.gridheight = 1;
		
		//*************************************Character Label
		mCharacterLabel = new JLabel(CHARACTER_ONE);	//default characters
		
		//gridbag settings
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.PAGE_START;
		
		this.add(mCharacterLabel, c);
		
		//**********************************Enter Button
		mEnter = new JButton(ENTER);
		mEnter.setPreferredSize(PREF_BUTTON_DIMEN);
		mEnter.addActionListener(new SendListener());
		
		//gridbag settings
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		
		this.add(mEnter, c);
		
		//**********************************Temporary Text Area
		mCharacterInputText = new JTextArea();
		mCharacterInputText.setEditable(true);
		mCharacterInputText.setPreferredSize(PREF_TEXT_DIMEN);
		mCharacterInputText.setLineWrap(true);
		
		//gridbag settings
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 2;
		c.anchor = GridBagConstraints.LINE_END;
		
		this.add(new JScrollPane(mCharacterInputText), c);
		
		//reset grid height to default
		c.gridheight = 1;
		
		this.pack();
		this.setVisible(true);
	}
	
// Private Methods --------------------------------------------------------- //
	
	/**
	 * Method which takes the message in the Temporary Text Area and parses it
	 * and places it in the Message Board.
	 */
	private void sendMessage(){
		//get and empty the temporary text area
		String message = mCharacterInputText.getText().trim();
		mCharacterInputText.setText("");
		
		//exit out of program if the text is empty
		if(message.isEmpty()){
			return;
		}
		
		//get the character name
		String character = mCharacterLabel.getText();
		
		//enter the message into the message area
		mMessageArea.append(character + " :\n" + message + "\n\n");
	}
	
// Listener Sub-classes ---------------------------------------------------- //
	
	/**
	 * Listener used to take input from the temporary message area
	 *  and put in the message board.
	 *
	 * @author David Boivin (Spit)
	 */
	class SendListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sendMessage();
		}
		
	}
	
	/**
	 * Listener used to switch out the text within a label with 
	 * the text within whatever button is using this listener.
	 *
	 * @author David Boivin (Spit)
	 */
	class ChangeCharacterListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			mCharacterLabel.setText(((JButton) e.getSource()).getText());
		}
		
	}
	
// Dispatcher -------------------------------------------------------------- //
	
	/**
	 * Class used as a work around. This class allows the KeyEvent enter to be
	 * caught by the frame rather than the individual components, therefore allowing
	 * focus to be irrelevant when pressing enter, the action will be performed.
	 *
	 * @author David Boivin (Spit)
	 */
	class Dispatcher implements KeyEventDispatcher{

		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				sendMessage();
				return true;
			}
			return false;
		}
		
	}
}
