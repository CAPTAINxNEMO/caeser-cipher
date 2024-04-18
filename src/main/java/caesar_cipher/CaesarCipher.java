package caesar_cipher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class CaesarCipher extends JFrame {

	private CardLayout cardLayout;
    private JPanel cards;
    private String ciphertext = "", decipheredText = "";
	public static String senderPhone = "", receiverPhone = "", ACCOUNT_SID = "", AUTH_TOKEN = "";

    public CaesarCipher() {

        setTitle("Caesar Cipher");
        setSize(800, 600);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
		setAlwaysOnTop(true);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        add(cards);

        JPanel modeSelectionPanel = createModeSelectionPanel();
		JPanel credentialsPanel = createCredentialsPanel();
        JPanel encryptionPanel = createEncryptionPanel();
        JPanel decryptionPanel = createDecryptionPanel();

        cards.add(modeSelectionPanel, "Mode Selection");
		cards.add(credentialsPanel, "Credentials");
        cards.add(encryptionPanel, "Encryption");
        cards.add(decryptionPanel, "Decryption");

        cardLayout.show(cards, "Mode Selection");
    }

    private JPanel createModeSelectionPanel() {

        JPanel panel = new JPanel(null);

        JLabel label = new JLabel("Select Mode");
        label.setBounds(180, 180, 440, 60);
        label.setFont(new Font("Courier New", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label);

        JButton encryptionButton = new JButton("Encryption");
        encryptionButton.setBounds(120, 300, 200, 60);
        encryptionButton.setFont(new Font("Courier New", Font.BOLD, 25));
        encryptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Credentials");
            }
        });
        panel.add(encryptionButton);

        JButton decryptionButton = new JButton("Decryption");
        decryptionButton.setBounds(460, 300, 200, 60);
        decryptionButton.setFont(new Font("Courier New", Font.BOLD, 25));
        decryptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Decryption");
            }
        });
        panel.add(decryptionButton);

        return panel;
    }

	private JPanel createCredentialsPanel() {       // https://console.twilio.com/

        JPanel panel = new JPanel(null);

        JLabel label = new JLabel("Enter Credentials");
        label.setBounds(180, 40, 440, 60);
        label.setFont(new Font("Courier New", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label);

		JLabel senderPhoneLabel = new JLabel("Sender Phone No.");
        senderPhoneLabel.setBounds(60, 160, 240, 40);
        senderPhoneLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        senderPhoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        senderPhoneLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(senderPhoneLabel);

		JLabel senderCountryCodeLabel = new JLabel("+");
		senderCountryCodeLabel.setBounds(320, 160, 40, 40);
		senderCountryCodeLabel.setFont(new Font("Courier New", Font.BOLD, 18));
		senderCountryCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		senderCountryCodeLabel.setVerticalAlignment(SwingConstants.CENTER);
		panel.add(senderCountryCodeLabel);

		JTextField senderCountryCodeInput = new JTextField("1");                        // Twilio Phone NUmber Country Code
		senderCountryCodeInput.setBounds(360, 160, 40, 40);
		senderCountryCodeInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(senderCountryCodeInput);

        JTextField senderPhoneInput = new JTextField();                                     // Twilio Phone Number
        senderPhoneInput.setBounds(420, 160, 320, 40);
        senderPhoneInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(senderPhoneInput);

        JLabel accountSIDLabel = new JLabel("Account SID");
		accountSIDLabel.setBounds(60, 240, 240, 40);
		accountSIDLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        accountSIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        accountSIDLabel.setVerticalAlignment(SwingConstants.CENTER);
		panel.add(accountSIDLabel);

		JTextField accountSIDInput = new JTextField();
		accountSIDInput.setBounds(320, 240, 420, 40);
        accountSIDInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(accountSIDInput);

		JLabel tokenLabel = new JLabel("Authentication Token");
		tokenLabel.setBounds(60, 320, 240, 40);
		tokenLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        tokenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tokenLabel.setVerticalAlignment(SwingConstants.CENTER);
		panel.add(tokenLabel);

		JTextField tokenInput = new JTextField();
		tokenInput.setBounds(320, 320, 420, 40);
        tokenInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(tokenInput);

        JButton backButton = new JButton("Back");
        backButton.setBounds(120, 440, 200, 60);
        backButton.setFont(new Font("Courier New", Font.BOLD, 35));
		backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Mode Selection");
            }
        });
        panel.add(backButton);

		JButton nextButton = new JButton("Next");
        nextButton.setBounds(460, 440, 200, 60);
        nextButton.setFont(new Font("Courier New", Font.BOLD, 35));
		nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				if (!senderCountryCodeInput.getText().isEmpty() &&
                    !senderPhoneInput.getText().isEmpty() &&
                    !accountSIDInput.getText().isEmpty() &&
                    !tokenInput.getText().isEmpty()) {
					senderPhone = "+" + senderCountryCodeInput.getText() + senderPhoneInput.getText();
					ACCOUNT_SID = accountSIDInput.getText();
					AUTH_TOKEN = tokenInput.getText();
					cardLayout.show(cards, "Encryption");
				}
            }
        });
        panel.add(nextButton);

        return panel;
    }

    private JPanel createEncryptionPanel() {

        JPanel panel = new JPanel(null);

        JLabel label = new JLabel("Encryption Mode");
        label.setBounds(180, 40, 440, 60);
        label.setFont(new Font("Courier New", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label);

		JLabel receiverPhoneLabel = new JLabel("Receiver Phone No.");
        receiverPhoneLabel.setBounds(60, 140, 240, 40);
        receiverPhoneLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        receiverPhoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        receiverPhoneLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(receiverPhoneLabel);

		JLabel receiverCountryCodeLabel = new JLabel("+");
		receiverCountryCodeLabel.setBounds(320, 140, 40, 40);
		receiverCountryCodeLabel.setFont(new Font("Courier New", Font.BOLD, 18));
		receiverCountryCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		receiverCountryCodeLabel.setVerticalAlignment(SwingConstants.CENTER);
		panel.add(receiverCountryCodeLabel);
		
		JTextField receiverCountryCodeInput = new JTextField("91");
		receiverCountryCodeInput.setBounds(360, 140, 40, 40);
		receiverCountryCodeInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(receiverCountryCodeInput);

        JTextField receiverPhoneInput = new JTextField();
        receiverPhoneInput.setBounds(420, 140, 320, 40);
        receiverPhoneInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(receiverPhoneInput);

        JLabel plaintextLabel = new JLabel("Plaintext");
        plaintextLabel.setBounds(60, 200, 120, 40);
        plaintextLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        plaintextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        plaintextLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(plaintextLabel);

        JTextField plaintextInput = new JTextField();
        plaintextInput.setBounds(200, 200, 540, 40);
        plaintextInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(plaintextInput);

        JLabel shiftLabel = new JLabel("Shift");
        shiftLabel.setBounds(60, 260, 120, 40);
        shiftLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        shiftLabel.setHorizontalAlignment(SwingConstants.CENTER);
        shiftLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(shiftLabel);

        JTextField encryptionShiftInput = new JTextField();
        encryptionShiftInput.setBounds(200, 260, 540, 40);
        encryptionShiftInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(encryptionShiftInput);

        JTextField encryptedOutput = new JTextField();
        encryptedOutput.setBounds(240, 380, 500, 40);
        encryptedOutput.setFont(new Font("Courier New", Font.PLAIN, 18));
        encryptedOutput.setEditable(false);
        panel.add(encryptedOutput);

		JButton encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(320, 320, 160, 40);
        encryptButton.setFont(new Font("Courier New", Font.BOLD, 18));
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				if (!plaintextInput.getText().isEmpty() && !encryptionShiftInput.getText().isEmpty()) {
					String plaintext = plaintextInput.getText();
					int shift = Integer.parseInt(encryptionShiftInput.getText());
					int shiftAlpha = shift % 26;
					ciphertext = "";

					for (int i = 0; i < plaintext.length(); i++) {
						char alphabet = plaintext.charAt(i);
						if (alphabet >= 'a' && alphabet <= 'z') {
							alphabet = (char) (alphabet + shiftAlpha);
							if (alphabet > 'z') {
								alphabet = (char) (alphabet - 'z' + 'a' - 1);
							}
							ciphertext += alphabet;
						} else if (alphabet >= 'A' && alphabet <= 'Z') {
							alphabet = (char) (alphabet + shiftAlpha);
							if (alphabet > 'Z') {
								alphabet = (char) (alphabet - 'Z' + 'A' - 1);
							}
							ciphertext += alphabet;
						} else if (alphabet >= '0' && alphabet <= '9') {
							alphabet = (char) (alphabet + shift);
							if (alphabet > '9') {
								alphabet = (char) (alphabet - '9' + '0' - 1);
							}
							ciphertext += alphabet;
						} else {
							ciphertext += alphabet;
						}
					}
					encryptedOutput.setText(ciphertext);
				}
            }
        });
		panel.add(encryptButton);

        JLabel encryptedLabel = new JLabel("Encrypted Text");
        encryptedLabel.setBounds(60, 380, 160, 40);
        encryptedLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        encryptedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        encryptedLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(encryptedLabel);

        JButton backButton = new JButton("Back");
        backButton.setBounds(120, 440, 200, 60);
        backButton.setFont(new Font("Courier New", Font.BOLD, 35));
		backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Credentials");
            }
        });
        panel.add(backButton);

		JButton sendButton = new JButton("Send");
        sendButton.setBounds(460, 440, 200, 60);
        sendButton.setFont(new Font("Courier New", Font.BOLD, 35));
		sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				if (!receiverCountryCodeInput.getText().isEmpty() &&
                    !receiverPhoneInput.getText().isEmpty() &&
                    !encryptedOutput.getText().isEmpty()) {
					receiverPhone = "+" + receiverCountryCodeInput.getText() + receiverPhoneInput.getText();

					Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
					Message.creator(
						new PhoneNumber(receiverPhone),
						new PhoneNumber(senderPhone),
						"Ciphertext: " + ciphertext + "\nShift: " + encryptionShiftInput.getText()
					).create();
				}
            }
        });
        panel.add(sendButton);

        return panel;
    }

    private JPanel createDecryptionPanel() {

        JPanel panel = new JPanel(null);

        JLabel label = new JLabel("Decryption Mode");
        label.setBounds(180, 40, 440, 60);
        label.setFont(new Font("Courier New", Font.BOLD, 40));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label);

        JLabel ciphertextLabel = new JLabel("Text");
        ciphertextLabel.setBounds(60, 160, 120, 40);
        ciphertextLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        ciphertextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ciphertextLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(ciphertextLabel);

        JTextField ciphertextInput = new JTextField();
        ciphertextInput.setBounds(200, 160, 540, 40);
        ciphertextInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(ciphertextInput);

        JLabel shiftLabel = new JLabel("Shift");
        shiftLabel.setBounds(60, 220, 120, 40);
        shiftLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        shiftLabel.setHorizontalAlignment(SwingConstants.CENTER);
        shiftLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(shiftLabel);

        JTextField decryptionShiftInput = new JTextField();
        decryptionShiftInput.setBounds(200, 220, 540, 40);
        decryptionShiftInput.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel.add(decryptionShiftInput);

        JTextField decryptedOutput = new JTextField();
        decryptedOutput.setBounds(240, 340, 500, 40);
        decryptedOutput.setFont(new Font("Courier New", Font.PLAIN, 18));
        decryptedOutput.setEditable(false);
        panel.add(decryptedOutput);

		JButton decryptButton = new JButton("Decrypt");
        decryptButton.setBounds(320, 280, 160, 40);
        decryptButton.setFont(new Font("Courier New", Font.BOLD, 18));
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				if (!ciphertextInput.getText().isEmpty() && !decryptionShiftInput.getText().isEmpty()) {
					String ciphertext = ciphertextInput.getText();
					int shift = Integer.parseInt(decryptionShiftInput.getText());
					int shiftAlpha = shift % 26;
					decipheredText = "";

					for (int i = 0; i < ciphertext.length(); i++) {
						char alphabet = ciphertext.charAt(i);
						if (alphabet >= 'a' && alphabet <= 'z') {
							alphabet = (char) (alphabet - shiftAlpha);
							if (alphabet < 'a') {
								alphabet = (char) (alphabet - 'a' + 'z' + 1);
							}
							decipheredText += alphabet;
						} else if (alphabet >= 'A' && alphabet <= 'Z') {
							alphabet = (char) (alphabet - shiftAlpha);
							if (alphabet < 'A') {
								alphabet = (char) (alphabet - 'A' + 'Z' + 1);
							}
							decipheredText += alphabet;
						} else if (alphabet >= '0' && alphabet <= '9') {
							alphabet = (char) (alphabet - shift);
							if (alphabet < '0') {
								alphabet = (char) (alphabet - '0' + '9' + 1);
							}
							decipheredText += alphabet;
						} else {
							decipheredText += alphabet;
						}
					}
					decryptedOutput.setText(decipheredText);
				}
            }
        });
		panel.add(decryptButton);

        JLabel decryptedLabel = new JLabel("Decrypted Text");
        decryptedLabel.setBounds(60, 340, 160, 40);
        decryptedLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        decryptedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        decryptedLabel.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(decryptedLabel);

        JButton backButton = new JButton("Back");
        backButton.setBounds(300, 420, 200, 60);
        backButton.setFont(new Font("Courier New", Font.BOLD, 35));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "Mode Selection");
            }
        });
        panel.add(backButton);
        return panel;
    }
    public static void main( String[] args ) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CaesarCipher().setVisible(true);
            }
        });
    }
}