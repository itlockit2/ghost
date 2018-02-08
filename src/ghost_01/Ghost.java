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

	// 더블 버퍼링을 위해서 전체 화면을 담는 필드값이다.
	private Image screenImage;
	private Graphics screenGraphic;

	// Main 클래스의 위치를 기반으로 해서 Resource를 얻어서 그것의 이미지값을 변수에 대입시켜준다.
	private Image backGroundImage = new ImageIcon(
			getClass().getClassLoader().getResource("images/mainScreenResize.png")).getImage();

	// 메뉴바 종료버튼 이미지 필드값을 만들어준다.
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

	// 종료버튼을 만들어준다.
	private JButton startButton = new JButton(startButtonBasic);
	private JButton helpButton = new JButton(helpButtonBasic);
	private JButton exitButton = new JButton(exitButtonBasic);
	private JButton menuExitButton = new JButton(menuExitButtonBasic);

	// 메뉴바를 만들어준다.
	private JLabel menuBar = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/menuBar.png")));

	// 음악 객체
	Music introMusic;

	// 현재 프로그램내에서 마우스의 X와 Y좌표를 받을수 있는 필드를 만든다.
	private int mouseX, mouseY;

	// 게임 화면인지 확인하는 값
	private boolean isGameScreen;
	private boolean isMainScreen;

	// 게임 엔딩 스크린 버튼들
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

	// 하나의 게임 객체
	private Game game;

	public Ghost() {

		introMusic = new Music("mainScreenMusic.mp3", true);

		// Test 가 다 끝나면 지우자
		addMouseListener(new MouseListener());

		// 메인스크린 확인값
		isMainScreen = true;
		// 배경음악 실행
		introMusic.start();
		// 메뉴바가 보이지 않게끔 설정
		setUndecorated(true);
		// 게임 이름 설정
		setTitle("Ghost");
		// 게임 창 크기 설정
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		// 창크기 변경 불가능 설정
		setResizable(false);
		// 게임 실행시 화면 정중앙에 뜨게끔 설정
		setLocationRelativeTo(null);
		// 게임 종료시 프로그램 종료가 되게끔 설정, 미설정시 게임이 종료되도 프로그램을 계속 실행된다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 화면 출력, 기본값은 false 이므로 설정 해줘야한다.
		setVisible(true);
		// paintComponent로 그릴때 배경색을 회색으로 지정
		setBackground(new Color(0, 0, 0, 0));
		// 컨테이너의 크기가 변경될때 컴포넌트들의 크기와 위치가 자동적으로 변경되는데 그걸 해제한다.
		setLayout(null);
		// 키보드 입력 이벤트 처리 추가
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// game이 실행되고 있지 않으면 리턴을 해준다.
				if (game == null)
					return;
				// 방향키에 대해서 이벤트 처리를 해준다.
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

		// 키보드 이벤트를 캐릭터에 맞추기 위해서 여기에 setFocusable(ture)를 넣어준다.
		setFocusable(true);

		// buttonSet 메소드를 통해서 버튼들을 넣어준다
		buttonSet(startButton, 93, 250, 339, 123);
		buttonSet(helpButton, 93, 392, 339, 123);
		buttonSet(exitButton, 93, 546, 339, 123);
		buttonSet(menuExitButton, 1215, 5, 50, 20);

		// startButton 이벤트처리
		startButton.addMouseListener(new MouseAdapter() {
			// 마우스가 버튼에 들어왓을때 이벤트 처리
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered이미지로 변경 시켜준다.
				startButton.setIcon(startButtonEntered);
				// 커서의 모양을 바꿔준다
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// 효과음 재생
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// 마우스가 버튼에 나갔을때 이벤트 처리
			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasic);
				// 커서의 모양을 바꿔준다
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// 버튼을 클릭했을때 이벤트 처리
			@Override
			public void mousePressed(MouseEvent e) {
				// 효과음 재생
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				enterGame();
			}
		});

		// helpButton 이벤트처리
		helpButton.addMouseListener(new MouseAdapter() {
			// 마우스가 버튼에 들어왓을때 이벤트 처리
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered이미지로 변경 시켜준다.
				helpButton.setIcon(helpButtonEntered);
				// 커서의 모양을 바꿔준다
				helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// 효과음 재생
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// 마우스가 버튼에 나갔을때 이벤트 처리
			@Override
			public void mouseExited(MouseEvent e) {
				helpButton.setIcon(helpButtonBasic);
				// 커서의 모양을 바꿔준다
				helpButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// 버튼을 클릭했을때 이벤트 처리
			@Override
			public void mousePressed(MouseEvent e) {
				// 효과음 재생
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// 게임 도움말 이벤트
			}
		});

		// exitButton 이벤트처리
		exitButton.addMouseListener(new MouseAdapter() {
			// 마우스가 버튼에 들어왓을때 이벤트 처리
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered이미지로 변경 시켜준다.
				exitButton.setIcon(exitButtonEntered);
				// 커서의 모양을 바꿔준다
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// 효과음 재생
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// 마우스가 버튼에 나갔을때 이벤트 처리
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasic);
				// 커서의 모양을 바꿔준다
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// 버튼을 클릭했을때 이벤트 처리
			@Override
			public void mousePressed(MouseEvent e) {
				// 효과음 재생
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// 프로그램이 바로 꺼지지 않고 1초정도 있다가 꺼지게금 설정
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});

		// exitMenuButton 이벤트 처리
		menuExitButton.addMouseListener(new MouseAdapter() {
			// 마우스가 버튼에 들어왓을때 이벤트 처리
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered이미지로 변경 시켜준다.
				menuExitButton.setIcon(menuExitButtonEntered);
				// 커서의 모양을 바꿔준다
				menuExitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// 효과음 재생
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// 마우스가 버튼에 나갔을때 이벤트 처리
			@Override
			public void mouseExited(MouseEvent e) {
				menuExitButton.setIcon(menuExitButtonBasic);
				// 커서의 모양을 바꿔준다
				menuExitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			}

			// 버튼을 클릭했을때 이벤트 처리
			@Override
			public void mousePressed(MouseEvent e) {
				// 효과음 재생
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// 프로그램이 바로 꺼지지 않고 1초정도 있다가 꺼지게금 설정
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});

		// setBounds를 통해 위치와 크기를 지정해준다.
		menuBar.setBounds(0, 0, 1280, 30);
		// add를 하면 paintComponetns를 통해서 그려진다.
		add(menuBar);
		// menuBar를 마우스를 통해서 조작할수 있도록 이벤트처리를 해준다.
		menuBar.addMouseListener(new MouseAdapter() {
			// 마우스를 입력했을때 컴포넌트내의 마우스의 x좌표와 y좌표를 가져온다
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		// menuBar를 드래그 했을때 이벤트 처리를 해준다.
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			// 마우스를 입력했을때 스크린(모니터)내의 마우스의 x좌표와 y좌표를 가져온다
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				// 스크린내의 마우스의 좌표와 컴포넌트내의 마우스의 좌표의 차가 게임창의 위치이다.
				setLocation(x - mouseX, y - mouseY);
			}
		});

		// restartGameButton 이벤트 처리
		restartGameButton.addMouseListener(new MouseAdapter() {
			// 마우스가 버튼에 들어왓을때 이벤트 처리
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered이미지로 변경 시켜준다.
				restartGameButton.setIcon(restartGameButtonEntered);
				// 커서의 모양을 바꿔준다
				restartGameButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// 효과음 재생
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// 마우스가 버튼에 나갔을때 이벤트 처리
			@Override
			public void mouseExited(MouseEvent e) {
				restartGameButton.setIcon(restartGameButtonBasic);
				// 커서의 모양을 바꿔준다
				restartGameButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// 버튼을 클릭했을때 이벤트 처리
			@Override
			public void mousePressed(MouseEvent e) {
				// 효과음 재생
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// 프로그램이 게임이 다시 시작될수 있도록 설정

				// gif파일은 리소스를 해제시켜줘야 다시 처음부터 시작한다.
				game.getGameDeathScreen().flush();
				game.getGhostDeath().flush();
				game.getGameEndImage().flush();
				if (game.getBloodEffect() != null)
					game.getBloodEffect().flush();
				if (game.getGameClearImage() != null)
					game.getGameClearImage().flush();
				// 스테이지 값은 다시 1로 설정
				game.setStageNum(1);
				enterGame();
			}
		});

		// mainMenuButton 이벤트 처리
		mainMenuButton.addMouseListener(new MouseAdapter() {
			// 마우스가 버튼에 들어왓을때 이벤트 처리
			@Override
			public void mouseEntered(MouseEvent e) {
				// Entered이미지로 변경 시켜준다.
				mainMenuButton.setIcon(mainMenuButtonEntered);
				// 커서의 모양을 바꿔준다
				mainMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// 효과음 재생
				Music menuEnteredMusic = new Music("menuEnteredMusic.mp3", false);
				menuEnteredMusic.start();
			}

			// 마우스가 버튼에 나갔을때 이벤트 처리
			@Override
			public void mouseExited(MouseEvent e) {
				mainMenuButton.setIcon(mainMenuButtonBasic);
				// 커서의 모양을 바꿔준다
				mainMenuButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// 버튼을 클릭했을때 이벤트 처리
			@Override
			public void mousePressed(MouseEvent e) {
				// 효과음 재생
				Music menuClickMusic = new Music("menuClickMusic.mp3", false);
				menuClickMusic.start();
				// gif파일은 리소스를 해제시켜줘야 다시 처음부터 시작한다.
				game.getGameDeathScreen().flush();
				game.getGhostDeath().flush();
				game.getGameEndImage().flush();
				if (game.getBloodEffect() != null)
					game.getBloodEffect().flush();
				if (game.getGameClearImage() != null)
					game.getGameClearImage().flush();
				// 스테이지 값은 다시 1로 설정
				game.setStageNum(1);

				// 메인화면으로 진입
				enterMainscreen();
			}
		});
	}

	// 버튼 셋팅 메소드 모든 버튼마다 설정값을 넣기 귀찮으므로 메소드로 만들었다.
	public void buttonSet(JButton button, int x, int y, int width, int height) {
		button.setBounds(x, y, width, height);
		// 버튼 테두리 제거
		button.setBorderPainted(false);
		// 누르는 느낌 제거
		button.setContentAreaFilled(false);
		// 글씨 테두리 제거
		button.setFocusPainted(false);
		// 버튼 추가
		add(button);
	}

	// game스크린 진입
	public void enterGame() {
		// 게임이 실행되고 있었다면 게임쓰레드를 꺼준다.
		if (game != null) {
			game.interrupt();
			game = null;
		}
		restartGameButton.setVisible(false);
		mainMenuButton.setVisible(false);
		// 메인화면 음악은 꺼주고 게임음악을 튼다.
		introMusic.close();
		// isGameScreen 값을 true로 변경시켜 준다.
		isGameScreen = true;
		isMainScreen = false;
		startButton.setVisible(false);
		helpButton.setVisible(false);
		exitButton.setVisible(false);
		backGroundImage = new ImageIcon(getClass().getClassLoader().getResource("images/gamePlayImage.png")).getImage();
		game = new Game();
		game.start();
	}

	// main스크린 진입
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

	// 하나의 약속된 즉 정의되어 있는 메소드이다.
	// paint 메소드는 JFrame을 상속한 클래스에서 가장 첫번째로 화면을 그려주는 메소드이다.
	public void paint(Graphics g) {
		// 1280 * 720 만큼의 이미지를 만들어서 변수에 대입
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		// screenImage 에서 그래픽값을 얻어서 변수에 대입
		screenGraphic = screenImage.getGraphics();
		// screenDraw 함수를 통해서 그래픽 값을 screenGraphic에 그린다.
		// Graphics2D로 변경해야 글씨가 깨지지 않는다.
		screenDraw((Graphics2D) screenGraphic);
		// screenImage 즉 screenDraw에서 그린 이미지를 화면에 띄워준다.
		g.drawImage(screenImage, 0, 0, null);
	}

	// Graphics2D로 변경해야 글씨가 깨지지 않는다.
	public void screenDraw(Graphics2D g) {
		// mainScreen을 0,0에 그려준다.
		// g.drawImage는 역동적인 그림을 그릴때 사용하고
		// 항상 존재해야하는 그림은 paintComponents를 이용해서 그린다.
		g.drawImage(backGroundImage, 0, 0, null);
		// Game화면이 아닐때는 메인화면을 그려주고 게임화면 일때는 게임화면을 그려준다.
		if (isGameScreen) {
			game.screenDraw(g);
			// 만약 죽었을 경우에 그에 해당하는 버튼을 그려준다.
			if (game.isDeathScreen()) {
				// 죽었을때 버튼들을 넣어준다.
				buttonSet(restartGameButton, 202, 477, 354, 70);
				buttonSet(mainMenuButton, 711, 477, 354, 70);
				restartGameButton.setVisible(true);
				mainMenuButton.setVisible(true);
			}
		}
		paintComponents(g);
		// 다시 paint메소드를 호출한다
		this.repaint();

	}

}
