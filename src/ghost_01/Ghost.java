package ghost_01;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Ghost extends JFrame {

	// ���� ���۸��� ���ؼ� ��ü ȭ���� ��� �ʵ尪�̴�.
	private Image screenImage;
	private Graphics screenGraphic;

	// Main Ŭ������ ��ġ�� ������� �ؼ� Resource�� �� �װ��� �̹������� ������ ���Խ����ش�.
	private Image backGroundImage = new ImageIcon(
			getClass().getClassLoader().getResource("images/mainScreenResize.png")).getImage();

	// �޴��� �����ư �̹��� �ʵ尪�� ������ش�.
	private ImageIcon menuExitButtonBasic = new ImageIcon(
			getClass().getClassLoader().getResource("images/menuExitButton.png"));
	private ImageIcon menuExitButtonEntered = new ImageIcon(
			getClass().getClassLoader().getResource("images/menuExitButtonEntered.png"));
	private ImageIcon startButtonBasic = new ImageIcon(
			getClass().getClassLoader().getResource("images/startButton.png"));
	private ImageIcon startButtonEntered = new ImageIcon(
			getClass().getClassLoader().getResource("images/startButtonEntered.png"));
	private ImageIcon helpButtonBasic = new ImageIcon(getClass().getClassLoader().getResource("images/helpButton.png"));
	private ImageIcon helpButtonEntered = new ImageIcon(
			getClass().getClassLoader().getResource("images/helpButtonEntered.png"));
	private ImageIcon exitButtonBasic = new ImageIcon(getClass().getClassLoader().getResource("images/exitButton.png"));
	private ImageIcon exitButtonEntered = new ImageIcon(
			getClass().getClassLoader().getResource("images/exitButtonEntered.png"));

	// �����ư�� ������ش�.
	private JButton startButton = new JButton(startButtonBasic);
	private JButton helpButton = new JButton(helpButtonBasic);
	private JButton exitButton = new JButton(exitButtonBasic);
	private JButton menuExitButton = new JButton(menuExitButtonBasic);

	// �޴��ٸ� ������ش�.
	private JLabel menuBar = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/menuBar.png")));

	// ���� ��ü
	Music introMusic;

	// ���� ���α׷������� ���콺�� X�� Y��ǥ�� ������ �ִ� �ʵ带 �����.
	private int mouseX, mouseY;

	// ���� ȭ������ Ȯ���ϴ� ��
	private boolean isGameScreen;
	private boolean isMainScreen;

	// ���� ���� ��ũ�� ��ư��
	private ImageIcon restartGameButtonBasic = new ImageIcon(
			getClass().getClassLoader().getResource("images/restartGameButtonBasic.png"));
	private ImageIcon restartGameButtonEntered = new ImageIcon(
			getClass().getClassLoader().getResource("images/restartGameButtonEntered.png"));
	private ImageIcon mainMenuButtonBasic = new ImageIcon(
			getClass().getClassLoader().getResource("images/mainMenuButtonBasic.png"));
	private ImageIcon mainMenuButtonEntered = new ImageIcon(
			getClass().getClassLoader().getResource("images/mainMenuButtonEntered.png"));

	private JButton restartGameButton = new JButton(restartGameButtonBasic);
	private JButton mainMenuButton = new JButton(mainMenuButtonBasic);

	// �ϳ��� ���� ��ü
	private Game game;

	public Ghost() {

		introMusic = new Music("mainScreenMusic.mp3", true);

		// Test �� �� ������ ������
		addMouseListener(new MouseListener());

		// ���ν�ũ�� Ȯ�ΰ�
		isMainScreen = true;
		// ������� ����
		introMusic.start();
		// �޴��ٰ� ������ �ʰԲ� ����
		setUndecorated(true);
		// ���� �̸� ����
		setTitle("Ghost");
		// ���� â ũ�� ����
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		// âũ�� ���� �Ұ��� ����
		setResizable(false);
		// ���� ����� ȭ�� ���߾ӿ� �߰Բ� ����
		setLocationRelativeTo(null);
		// ���� ����� ���α׷� ���ᰡ �ǰԲ� ����, �̼����� ������ ����ǵ� ���α׷��� ��� ����ȴ�.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ȭ�� ���, �⺻���� false �̹Ƿ� ���� ������Ѵ�.
		setVisible(true);
		// paintComponent�� �׸��� ������ ȸ������ ����
		setBackground(new Color(0, 0, 0, 0));
		// �����̳��� ũ�Ⱑ ����ɶ� ������Ʈ���� ũ��� ��ġ�� �ڵ������� ����Ǵµ� �װ� �����Ѵ�.
		setLayout(null);
		// Ű���� �Է� �̺�Ʈ ó�� �߰�
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// game�� ����ǰ� ���� ������ ������ ���ش�.
				if (game == null)
					return;
				// ����Ű�� ���ؼ� �̺�Ʈ ó���� ���ش�.
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					game.mainCharacter.setLeft(true);
					game.mainCharacter.enterLeft();

				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					game.mainCharacter.setRight(true);
					game.mainCharacter.enterRight();

				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					game.mainCharacter.setUp(true);
					game.mainCharacter.enterUp();

				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					game.mainCharacter.setDown(true);
					game.mainCharacter.enterDown();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (game == null)
					return;
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					game.mainCharacter.setLeft(false);
					game.mainCharacter.releaseLeft();

				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					game.mainCharacter.setRight(false);
					game.mainCharacter.releaseRight();

				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					game.mainCharacter.setUp(false);
					game.mainCharacter.releaseUp();

				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					game.mainCharacter.setDown(false);
					game.mainCharacter.releaseDown();
				}
			}
		});

		// Ű���� �̺�Ʈ�� ĳ���Ϳ� ���߱� ���ؼ� ���⿡ setFocusable(ture)�� �־��ش�.
		setFocusable(true);

		// buttonSet �޼ҵ带 ���ؼ� ��ư���� �־��ش�
		buttonSet(startButton, 93, 250, 339, 123);
		buttonSet(helpButton, 93, 392, 339, 123);
		buttonSet(exitButton, 93, 546, 339, 123);
		buttonSet(menuExitButton, 1215, 5, 50, 20);

		// startButton �̺�Ʈó��
		startButton.addMouseListener(new MouseAdapter() {
			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered�̹����� ���� �����ش�.
				startButton.setIcon(startButtonEntered);
				// Ŀ���� ����� �ٲ��ش�
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// ȿ���� ���
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasic);
				// Ŀ���� ����� �ٲ��ش�
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// ��ư�� Ŭ�������� �̺�Ʈ ó��
			@Override
			public void mousePressed(MouseEvent e) {
				// ȿ���� ���
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				enterGame();
			}
		});

		// helpButton �̺�Ʈó��
		helpButton.addMouseListener(new MouseAdapter() {
			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered�̹����� ���� �����ش�.
				helpButton.setIcon(helpButtonEntered);
				// Ŀ���� ����� �ٲ��ش�
				helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// ȿ���� ���
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseExited(MouseEvent e) {
				helpButton.setIcon(helpButtonBasic);
				// Ŀ���� ����� �ٲ��ش�
				helpButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// ��ư�� Ŭ�������� �̺�Ʈ ó��
			@Override
			public void mousePressed(MouseEvent e) {
				// ȿ���� ���
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// ���� ���� �̺�Ʈ
			}
		});

		// exitButton �̺�Ʈó��
		exitButton.addMouseListener(new MouseAdapter() {
			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered�̹����� ���� �����ش�.
				exitButton.setIcon(exitButtonEntered);
				// Ŀ���� ����� �ٲ��ش�
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// ȿ���� ���
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasic);
				// Ŀ���� ����� �ٲ��ش�
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// ��ư�� Ŭ�������� �̺�Ʈ ó��
			@Override
			public void mousePressed(MouseEvent e) {
				// ȿ���� ���
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// ���α׷��� �ٷ� ������ �ʰ� 1������ �ִٰ� �����Ա� ����
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});

		// exitMenuButton �̺�Ʈ ó��
		menuExitButton.addMouseListener(new MouseAdapter() {
			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered�̹����� ���� �����ش�.
				menuExitButton.setIcon(menuExitButtonEntered);
				// Ŀ���� ����� �ٲ��ش�
				menuExitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// ȿ���� ���
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseExited(MouseEvent e) {
				menuExitButton.setIcon(menuExitButtonBasic);
				// Ŀ���� ����� �ٲ��ش�
				menuExitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			// ��ư�� Ŭ�������� �̺�Ʈ ó��
			@Override
			public void mousePressed(MouseEvent e) {
				// ȿ���� ���
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// ���α׷��� �ٷ� ������ �ʰ� 1������ �ִٰ� �����Ա� ����
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});

		// setBounds�� ���� ��ġ�� ũ�⸦ �������ش�.
		menuBar.setBounds(0, 0, 1280, 30);
		// add�� �ϸ� paintComponetns�� ���ؼ� �׷�����.
		add(menuBar);
		// menuBar�� ���콺�� ���ؼ� �����Ҽ� �ֵ��� �̺�Ʈó���� ���ش�.
		menuBar.addMouseListener(new MouseAdapter() {
			// ���콺�� �Է������� ������Ʈ���� ���콺�� x��ǥ�� y��ǥ�� �����´�
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		// menuBar�� �巡�� ������ �̺�Ʈ ó���� ���ش�.
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			// ���콺�� �Է������� ��ũ��(�����)���� ���콺�� x��ǥ�� y��ǥ�� �����´�
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				// ��ũ������ ���콺�� ��ǥ�� ������Ʈ���� ���콺�� ��ǥ�� ���� ����â�� ��ġ�̴�.
				setLocation(x - mouseX, y - mouseY);
			}
		});

		// restartGameButton �̺�Ʈ ó��
		restartGameButton.addMouseListener(new MouseAdapter() {
			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered�̹����� ���� �����ش�.
				restartGameButton.setIcon(restartGameButtonEntered);
				// Ŀ���� ����� �ٲ��ش�
				restartGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// ȿ���� ���
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseExited(MouseEvent e) {
				restartGameButton.setIcon(restartGameButtonBasic);
				// Ŀ���� ����� �ٲ��ش�
				restartGameButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// ��ư�� Ŭ�������� �̺�Ʈ ó��
			@Override
			public void mousePressed(MouseEvent e) {
				// ȿ���� ���
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// ���α׷��� ������ �ٽ� ���۵ɼ� �ֵ��� ����

				// gif������ ���ҽ��� ����������� �ٽ� ó������ �����Ѵ�.
				game.getGameDeathScreen().flush();
				game.getGhostDeath().flush();
				game.getGameEndImage().flush();
				if (game.getBloodEffect() != null)
					game.getBloodEffect().flush();
				if (game.getGameClearImage() != null)
					game.getGameClearImage().flush();
				// �������� ���� �ٽ� 1�� ����
				game.setStageNum(1);
				enterGame();
			}
		});

		// mainMenuButton �̺�Ʈ ó��
		mainMenuButton.addMouseListener(new MouseAdapter() {
			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered�̹����� ���� �����ش�.
				mainMenuButton.setIcon(mainMenuButtonEntered);
				// Ŀ���� ����� �ٲ��ش�
				mainMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// ȿ���� ���
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// ���콺�� ��ư�� �������� �̺�Ʈ ó��
			@Override
			public void mouseExited(MouseEvent e) {
				mainMenuButton.setIcon(mainMenuButtonBasic);
				// Ŀ���� ����� �ٲ��ش�
				mainMenuButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// ��ư�� Ŭ�������� �̺�Ʈ ó��
			@Override
			public void mousePressed(MouseEvent e) {
				// ȿ���� ���
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// gif������ ���ҽ��� ����������� �ٽ� ó������ �����Ѵ�.
				game.getGameDeathScreen().flush();
				game.getGhostDeath().flush();
				game.getGameEndImage().flush();
				if (game.getBloodEffect() != null)
					game.getBloodEffect().flush();
				if (game.getGameClearImage() != null)
					game.getGameClearImage().flush();
				// �������� ���� �ٽ� 1�� ����
				game.setStageNum(1);

				// ����ȭ������ ����
				enterMainscreen();
			}
		});
	}

	// ��ư ���� �޼ҵ� ��� ��ư���� �������� �ֱ� �������Ƿ� �޼ҵ�� �������.
	public void buttonSet(JButton button, int x, int y, int width, int height) {
		button.setBounds(x, y, width, height);
		// ��ư �׵θ� ����
		button.setBorderPainted(false);
		// ������ ���� ����
		button.setContentAreaFilled(false);
		// �۾� �׵θ� ����
		button.setFocusPainted(false);
		// ��ư �߰�
		add(button);
	}

	// game��ũ�� ����
	public void enterGame() {
		// ������ ����ǰ� �־��ٸ� ���Ӿ����带 ���ش�.
		if (game != null) {
			game.interrupt();
			game = null;
		}
		restartGameButton.setVisible(false);
		mainMenuButton.setVisible(false);
		// ����ȭ�� ������ ���ְ� ���������� ư��.
		introMusic.close();
		// isGameScreen ���� true�� ������� �ش�.
		isGameScreen = true;
		isMainScreen = false;
		startButton.setVisible(false);
		helpButton.setVisible(false);
		exitButton.setVisible(false);
		backGroundImage = new ImageIcon(getClass().getClassLoader().getResource("images/gamePlayImage.png")).getImage();
		game = new Game();
		game.start();
	}

	// main��ũ�� ����
	public void enterMainscreen() {
		restartGameButton.setVisible(false);
		mainMenuButton.setVisible(false);
		backGroundImage = new ImageIcon(getClass().getClassLoader().getResource("images/mainScreenResize.png"))
				.getImage();
		if (game != null)
			game.interrupt();
		isGameScreen = false;
		isMainScreen = true;
		startButton.setVisible(true);
		helpButton.setVisible(true);
		exitButton.setVisible(true);
		introMusic = new Music("mainScreenMusic.mp3", true);
		introMusic.start();
	}

	// �ϳ��� ��ӵ� �� ���ǵǾ� �ִ� �޼ҵ��̴�.
	// paint �޼ҵ�� JFrame�� ����� Ŭ�������� ���� ù��°�� ȭ���� �׷��ִ� �޼ҵ��̴�.
	public void paint(Graphics g) {
		// 1280 * 720 ��ŭ�� �̹����� ���� ������ ����
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		// screenImage ���� �׷��Ȱ��� �� ������ ����
		screenGraphic = screenImage.getGraphics();
		// screenDraw �Լ��� ���ؼ� �׷��� ���� screenGraphic�� �׸���.
		// Graphics2D�� �����ؾ� �۾��� ������ �ʴ´�.
		screenDraw((Graphics2D) screenGraphic);
		// screenImage �� screenDraw���� �׸� �̹����� ȭ�鿡 ����ش�.
		g.drawImage(screenImage, 0, 0, null);
	}

	// Graphics2D�� �����ؾ� �۾��� ������ �ʴ´�.
	public void screenDraw(Graphics2D g) {
		// mainScreen�� 0,0�� �׷��ش�.
		// g.drawImage�� �������� �׸��� �׸��� ����ϰ�
		// �׻� �����ؾ��ϴ� �׸��� paintComponents�� �̿��ؼ� �׸���.
		g.drawImage(backGroundImage, 0, 0, null);
		// Gameȭ���� �ƴҶ��� ����ȭ���� �׷��ְ� ����ȭ�� �϶��� ����ȭ���� �׷��ش�.
		if (isGameScreen) {
			game.screenDraw(g);
			// ���� �׾��� ��쿡 �׿� �ش��ϴ� ��ư�� �׷��ش�.
			if (game.isDeathScreen()) {
				// �׾����� ��ư���� �־��ش�.
				buttonSet(restartGameButton, 202, 477, 354, 70);
				buttonSet(mainMenuButton, 711, 477, 354, 70);
				restartGameButton.setVisible(true);
				mainMenuButton.setVisible(true);
			}
		}
		paintComponents(g);
		// �ٽ� paint�޼ҵ带 ȣ���Ѵ�
		this.repaint();

	}

}
