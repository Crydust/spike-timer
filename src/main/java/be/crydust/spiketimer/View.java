package be.crydust.spiketimer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Instant;
import java.util.Locale;

public class View implements PropertyChangeListener {
	private JTextField howLongTextField;
	private JButton startButton;
	private JPanel guiFormPanel;
	private JLabel howLongLabel;
	private JProgressBar elapsedProgressBar;

	private final Taskbar taskbar;

	public View(Controller controller) {
		if (Taskbar.isTaskbarSupported()) {
			taskbar = Taskbar.getTaskbar();
		} else {
			taskbar = null;
		}
		startButton.addActionListener(e -> {
			final String text = howLongTextField.getText();
			new SwingWorker<Void, Void>() {
				@Override
				protected Void doInBackground() {
					controller.start(text, Instant.now());
					return null;
				}
			}.execute();
		});
	}

	public JPanel getPanel() {
		return guiFormPanel;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (PropertyName.valueOf(evt.getPropertyName())) {
			case RUNNING -> {
				Boolean oldValue = (Boolean) evt.getOldValue();
				Boolean newValue = (Boolean) evt.getNewValue();
				if (oldValue && !newValue) {
					donePopup();
					doneSound();
				}
			}
			case PERCENT -> {
				int percent = (int) evt.getNewValue();
				SwingUtilities.invokeLater(() -> {
					final JFrame jFrame = getJFrame();
					if (jFrame != null && taskbar != null) {
						if (percent <= 0 || percent >= 100) {
							taskbar.setWindowProgressState(jFrame, Taskbar.State.OFF);
						} else {
							taskbar.setWindowProgressState(jFrame, Taskbar.State.NORMAL);
							taskbar.setWindowProgressValue(jFrame, percent);
						}
					}
				});
			}
			case PER_MILLE -> {
				int perMille = (int) evt.getNewValue();
				SwingUtilities.invokeLater(() -> elapsedProgressBar.setValue(perMille));
			}
			case REMAINING_FORMATTED -> {
				String remainingFormatted = (String) evt.getNewValue();
				SwingUtilities.invokeLater(() -> elapsedProgressBar.setString(remainingFormatted));
			}
		}
	}

	private void donePopup() {
		final JFrame jFrame = getJFrame();
		if (jFrame != null) {
			if (jFrame.getState() != Frame.NORMAL) {
				jFrame.setState(Frame.NORMAL);
			}
			jFrame.toFront();
			jFrame.repaint();
		}
	}

	private static void doneSound() {
		Toolkit.getDefaultToolkit().beep();
	}

	private JFrame getJFrame() {
		final Container topLevelAncestor = this.getPanel().getTopLevelAncestor();
		final JFrame jFrame;
		if (topLevelAncestor instanceof JFrame) {
			jFrame = (JFrame) topLevelAncestor;
		} else {
			jFrame = null;
		}
		return jFrame;
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		guiFormPanel = new JPanel();
		guiFormPanel.setLayout(new GridBagLayout());
		howLongLabel = new JLabel();
		howLongLabel.setText("How long?");
		GridBagConstraints gbc;
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.anchor = GridBagConstraints.WEST;
		guiFormPanel.add(howLongLabel, gbc);
		howLongTextField = new JTextField();
		howLongTextField.setText("30");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		guiFormPanel.add(howLongTextField, gbc);
		startButton = new JButton();
		startButton.setText("Start");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		guiFormPanel.add(startButton, gbc);
		final JPanel spacer1 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.VERTICAL;
		guiFormPanel.add(spacer1, gbc);
		final JPanel spacer2 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.VERTICAL;
		guiFormPanel.add(spacer2, gbc);
		final JPanel spacer3 = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.fill = GridBagConstraints.VERTICAL;
		guiFormPanel.add(spacer3, gbc);
		elapsedProgressBar = new JProgressBar();
		Font elapsedProgressBarFont = this.$$$getFont$$$(null, -1, 36, elapsedProgressBar.getFont());
		if (elapsedProgressBarFont != null) elapsedProgressBar.setFont(elapsedProgressBarFont);
		elapsedProgressBar.setMaximum(1000);
		elapsedProgressBar.setString("0h 0m 0s");
		elapsedProgressBar.setStringPainted(true);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		guiFormPanel.add(elapsedProgressBar, gbc);
	}

	/**
	 * @noinspection ALL
	 */
	private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
		if (currentFont == null) return null;
		String resultName;
		if (fontName == null) {
			resultName = currentFont.getName();
		} else {
			Font testFont = new Font(fontName, Font.PLAIN, 10);
			if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
				resultName = fontName;
			} else {
				resultName = currentFont.getName();
			}
		}
		Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
		boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
		Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
		return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return guiFormPanel;
	}

}
