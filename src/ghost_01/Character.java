package ghost_01;

import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class Character extends Thread {

	// 캐릭터 하나 생성
	private Image charcaterImage;
	private final int CHAR_SPEED = 3;

	// 캐릭터의 좌표 생성
	private int charX;
	private int charY;

	// 캐릭터의 크기 생성
	private final int CHAR_WIDTH = 18;
	private final int CHAR_HEIGHT = 40;

	// 캐릭터의 이동방향 설정
	private boolean isLeft = false;
	private boolean isRight = false;
	private boolean isUp = false;
	private boolean isDown = false;

	//
	private Rectangle2D char2D;

	Music leftFootSound;
	Music rightFootSound;
	Music upFootSound;
	Music downFootSound;

	public Character(String characterName) {
		charcaterImage = new ImageIcon(getClass().getClassLoader().getResource("images/" + characterName + ".png"))
				.getImage();
		charX = 633;
		charY = 338;
		char2D = new Rectangle2D.Double(charX, charY, CHAR_WIDTH, CHAR_HEIGHT);
	}

	public void moveLeft() {
		charX -= CHAR_SPEED;

	}

	public void moveRight() {
		charX += CHAR_SPEED;
	}

	public void moveUp() {
		charY -= CHAR_SPEED;
	}

	public void moveDown() {
		charY += CHAR_SPEED;
	}
	
	public void enterLeft() {
		charcaterImage = new ImageIcon(getClass().getClassLoader().getResource("images/mainCharaterLeftMove.gif"))
				.getImage();
		if (leftFootSound == null) {
			leftFootSound = new Music("footSound.mp3", true);
			leftFootSound.start();
		}
	}

	public void enterRight() {
		charcaterImage = new ImageIcon(getClass().getClassLoader().getResource("images/mainCharaterRightMove.gif"))
				.getImage();
		if (rightFootSound == null) {
			rightFootSound = new Music("footSound.mp3", true);
			rightFootSound.start();
		}
	}

	public void enterUp() {
		charcaterImage = new ImageIcon(getClass().getClassLoader().getResource("images/mainCharaterUpMove.gif"))
				.getImage();
		if (upFootSound == null) {
			upFootSound = new Music("footSound.mp3", true);
			upFootSound.start();
		}
	}

	public void enterDown() {
		charcaterImage = new ImageIcon(getClass().getClassLoader().getResource("images/mainCharaterDownMove.gif"))
				.getImage();
		if (downFootSound == null) {
			downFootSound = new Music("footSound.mp3", true);
			downFootSound.start();
		}
	}

	public void releaseLeft() {
		charcaterImage = new ImageIcon(getClass().getClassLoader().getResource("images/mainCharaterLeft1.png"))
				.getImage();
		if (leftFootSound != null) {
		leftFootSound.close();
		leftFootSound = null;
		}
	}

	public void releaseRight() {

		charcaterImage = new ImageIcon(getClass().getClassLoader().getResource("images/mainCharaterRight1.png"))
				.getImage();
		if (rightFootSound != null) {
			rightFootSound.close();
			rightFootSound = null;
			}
	}

	public void releaseUp() {
		charcaterImage = new ImageIcon(getClass().getClassLoader().getResource("images/mainCharaterUp1.png"))
				.getImage();
		if (upFootSound != null) {
			upFootSound.close();
			upFootSound = null;
			}
	}

	public void releaseDown() {
		charcaterImage = new ImageIcon(getClass().getClassLoader().getResource("images/mainCharaterDown1.png"))
				.getImage();
		if (downFootSound != null) {
			downFootSound.close();
			downFootSound = null;
			}
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (isLeft && charX > 66)
					moveLeft();
				if (isRight && charX < 1206)
					moveRight();
				if (isUp && charY > 42)
					moveUp();
				if (isDown && charY < 622)
					moveDown();
				char2D.setRect(charX, charY, CHAR_WIDTH, CHAR_HEIGHT);
				Thread.sleep(10);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public boolean isUp() {
		return isUp;
	}

	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}

	public boolean isDown() {
		return isDown;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public Image getCharcaterImage() {
		return charcaterImage;
	}

	public void setCharcaterImage(Image charcaterImage) {
		this.charcaterImage = charcaterImage;
	}

	public int getCharacterSpeed() {
		return CHAR_SPEED;
	}

	public int getCharX() {
		return charX;
	}

	public void setCharX(int charX) {
		this.charX = charX;
	}

	public int getCharY() {
		return charY;
	}

	public void setCharY(int charY) {
		this.charY = charY;
	}

	public Rectangle2D getChar2D() {
		return char2D;
	}

	public void setChar2D(Rectangle2D char2d) {
		char2D = char2d;
	}

}
