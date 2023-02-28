package be.crydust.spiketimer;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
				 UnsupportedLookAndFeelException ex) {
			// ignore
		}
		final Model model = new Model();
		final Controller controller = new Controller(model);
		final View view = new View(controller);
		model.addPropertyChangeListener(view);
		SwingUtilities.invokeLater(() -> {
			JFrame app = new JFrame("Timer");
			JPanel panel = view.getPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			JScrollPane scrollPane = new JScrollPane(panel);
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			app.getContentPane().add(scrollPane);
			app.setMinimumSize(new Dimension(350, 200));
			app.setIconImages(getIconImages());
			app.pack();
			app.setLocationRelativeTo(null);
			app.setVisible(true);
			app.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			app.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent windowEvent) {
					controller.stop();
					System.exit(0);
				}
			});
		});
	}

	private static List<Image> getIconImages() {
		Class<?> c = Main.class;
		return Arrays.asList(
				new ImageIcon(c.getResource("/icon_256x256.png")).getImage(),
				new ImageIcon(c.getResource("/icon_128x128.png")).getImage(),
				new ImageIcon(c.getResource("/icon_64x64.png")).getImage(),
				new ImageIcon(c.getResource("/icon_48x48.png")).getImage(),
				new ImageIcon(c.getResource("/icon_32x32.png")).getImage(),
				new ImageIcon(c.getResource("/icon_16x16.png")).getImage()
		);
	}
}