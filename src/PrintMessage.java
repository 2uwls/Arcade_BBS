import java.util.List;
import java.util.Random;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class PrintMessage {
	private JTextPane messagePane;
	private StyledDocument doc;

	public PrintMessage(JTextPane messagePane, StyledDocument doc) {
		this.messagePane = messagePane;
		this.doc = doc;
	}

	public void displayMessages(String... messages) {
		new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				for (String message : messages) {
					displayMessage(message);
				}
				return null;
			}

			@Override
			protected void process(List<String> chunks) {
				for (String word : chunks) {
					String trimmedWord = word.trim();

					if (!trimmedWord.isEmpty()) {
						appendStyledText(trimmedWord, "DefaultStyle");
						appendStyledText(" ", "DefaultStyle");
					}
				}
				appendStyledText("\n", "DefaultStyle");
			}

			@Override
			protected void done() {
				appendStyledText("\n\n", "DefaultStyle");
			}
		}.execute();
	}

	private void displayMessage(String message) {
		Random random = new Random();
		try {
			if (message.startsWith("[COM]")) {

				String[] words = message.split(" ");

				for (String word : words) {
					int randNum = random.nextInt(500) + 150;
					Thread.sleep(randNum);
					publish(word, true);
					publish(" ", true);
					Thread.sleep(400);
				}
				publish("\n\n", false);
			} else {

				String[] words = message.split(" ");
				for (String word : words) {
					int randNum = random.nextInt(500) + 150;
					Thread.sleep(randNum);
					publish(word, false);
					publish(" ", false);
				}
				publish("\n\n", false);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void appendStyledText(String text, String styleName) {
		try {
			doc.insertString(doc.getLength(), text, doc.getStyle(styleName));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void publish(String word, boolean com) {
		SwingUtilities.invokeLater(() -> {
			if (com) {
				appendStyledText(word, "ComputerStyle");
			} else {
				appendStyledText(word, "DefaultStyle");
			}
		});
	}
}
