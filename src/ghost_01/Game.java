package ghost_01;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class Game extends Thread {

	// ĳ���� �ϳ� ����
	Character mainCharacter;

	// ��Ʈ ���� �ϳ� ����
	Monster ghost;
	// ���� ���� �ϳ� ����
	Monster zombie;

	// �þ��̹��� ��ǥ, ĳ���� �ʱⰪ
	private int sightX = -632;
	private int sightY = -353;
	private int initCharX = 633;
	private int initCharY = 338;

	// �������� Ȯ�ΰ�
	private static int stageNum = 1;

	// Ż�ⱸ ��ǥ
	private int doorX;
	private int doorY;

	// ���� Ȯ�� ��
	private boolean isDeath;

	// ���� Ŭ���� Ȯ�ΰ�
	private boolean isGameClear;

	// ĳ���� �þ�
	private Image characterSight;
	// ���� ����
	private Image gameEndImage;
	// �ͽ� ����
	private Image ghostDeath;
	// ���� ���� ��ũ��
	private Image gameDeathScreen;
	// Ż�ⱸ
	private Image exitDoor;
	private Rectangle2D exitDoor2D;
	// ����Ŭ���� �̺�Ʈ
	private Image bloodEffect;
	private Image gameClearImage;

	// Game����
	Music gamePlayMusic;
	Music heartBeat1;
	Music heartBeat2;
	Music heartBeat3;
	Music ghostDeathSound;
	Music gameClearSound;
	Music gameClearSound2;

	public Game() {
		// ĳ���͵� �ʱ�ȭ
		mainCharacter = new Character("mainCharacter");
		// ���� �ʱ�ȭ
		ghost = new Monster("ghost");
		zombie = new Monster("zombie");
		// boolean�� �ʱ�ȭ
		isDeath = false;
		isGameClear = false;
		// �������� �ʱ�ȭ
		gamePlayMusic = new Music("gamePlayMusic.mp3", true);
		// �̹��� ���ϵ� �ʱ�ȭ
		characterSight = new ImageIcon(getClass().getClassLoader().getResource("images/characterSight.png")).getImage();
		ghostDeath = new ImageIcon(getClass().getClassLoader().getResource("images/ghostDeath.gif")).getImage();

		// Ż�ⱸ�� �׷��ش�.
		// Ż�ⱸ�� ������ġ�� ĳ���� �̵����� ������ �������ش�.
		// �ʹ� ������ ������ ���� �����Ƿ� ĳ���� �ݰ� 200�ȼ� �ȿ����� ������ �ȵǰԲ� �������ش�.
		do {
			doorX = (int) (Math.random() * 1280);
		} while (doorX < 62 || doorX > 1174 || (433 < doorX && doorX < 833));
		do {
			doorY = (int) (Math.random() * 720);
		} while (42 > doorY || doorY > 590);
		exitDoor = new ImageIcon(getClass().getClassLoader().getResource("images/exitDoor.png")).getImage();
		exitDoor2D = new Rectangle2D.Double(doorX, doorY, 32, 32);
		// ĳ���� ���� �� �������� ����
		mainCharacter.start();
		gamePlayMusic.start();

	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(ghost.getGhostImage(), ghost.getMonX(), ghost.getMonY(), null);
		// ������ �ڽ�ǥ�� �׽�Ʈ�� ����� �� ������ ������.
		g.draw(ghost.getGhost2D());
		g.drawImage(zombie.getZombieImage(), 300, 200,50,78, null);
		// ĳ���� �þ� ����
		/*
		 * g.drawImage(characterSight, sightX + (-initCharX + mainCharacter.getCharX()),
		 * sightY + (-initCharY + mainCharacter.getCharY()), null);
		 */
		g.drawImage(mainCharacter.getCharcaterImage(), mainCharacter.getCharX(), mainCharacter.getCharY(), null);
		// ĳ������ �ڽ�ǥ�� ����� �� ������ ������
		g.draw(mainCharacter.getChar2D());

		// Ż�ⱸ�� �׷��ش�.
		g.drawImage(exitDoor, doorX, doorY, 32, 32, null);
		// Ż�ⱸ�� �ڽ�ǥ�� ����� �� ������ ������
		g.draw(exitDoor2D);

		// ������ ������ ȭ���� ��Ӱ� ���ش�.
		if (isGameClear) {
			g.drawImage(gameEndImage, sightX + (-initCharX + mainCharacter.getCharX()),
					sightY + (-initCharY + mainCharacter.getCharY()), null);
			g.drawImage(bloodEffect, 0, 0, null);
			g.drawImage(gameClearImage, 0, 0, null);
		}

		// ������ ������ ȭ���� ��Ӱ� ���ش�.
		if (gameOver()) {
			g.drawImage(gameEndImage, sightX + (-initCharX + mainCharacter.getCharX()),
					sightY + (-initCharY + mainCharacter.getCharY()), null);
			g.drawImage(ghostDeath, 300, 150, null);
			g.drawImage(gameDeathScreen, 0, 0, null);
		}

	}

	// ���Ϳ� �浹�ߴ����� �������ش�.
	public boolean gameOver() {
		return mainCharacter.getChar2D().intersects(ghost.getGhost2D());
	}

	// ���� ������ Ȯ���Ѵ�.
	public boolean gameClear() {
		return mainCharacter.getChar2D().intersects(exitDoor2D);
	}

	// ���Ϳ��� �Ÿ��� �������ش�
	public double calDistance() {
		double distanceX = Math.pow(mainCharacter.getCharX() - ghost.getMonX(), 2);
		double distanceY = Math.pow(mainCharacter.getCharY() - ghost.getMonY(), 2);
		return Math.sqrt(distanceX + distanceY);
	}

	public void continueGame() {
		// ����Ŭ���� gif ���ϵ��� ���ҽ��� �����ش�.
		gameEndImage.flush();
		bloodEffect.flush();
		gameClearImage.flush();

		// ĳ���͵� �ʱ�ȭ
		mainCharacter = new Character("mainCharacter");
		// ���� �ʱ�ȭ
		ghost = new Monster("ghost");
		zombie = new Monster("zombie");
		// boolean�� �ʱ�ȭ
		isDeath = false;
		isGameClear = false;
		// �������� �ʱ�ȭ
		gamePlayMusic = new Music("gamePlayMusic.mp3", true);
		gameClearSound = null;
		gameClearSound2 = null;

		// �̹��� ���ϵ� �ʱ�ȭ
		characterSight = new ImageIcon(getClass().getClassLoader().getResource("images/characterSight.png")).getImage();

		ghostDeath = new ImageIcon(getClass().getClassLoader().getResource("images/ghostDeath.gif")).getImage();

		// Ż�ⱸ�� �׷��ش�.
		// Ż�ⱸ�� ������ġ�� ĳ���� �̵����� ������ �������ش�.
		// �ʹ� ������ ������ ���� �����Ƿ� ĳ���� �ݰ� 200�ȼ� �ȿ����� ������ �ȵǰԲ� �������ش�.
		do {
			doorX = (int) (Math.random() * 1280);
		} while (doorX < 62 || doorX > 1174 || (433 < doorX && doorX < 833));
		do {
			doorY = (int) (Math.random() * 720);
		} while (42 > doorY || doorY > 590);
		exitDoor = new ImageIcon(getClass().getClassLoader().getResource("images/exitDoor.png")).getImage();
		exitDoor2D = new Rectangle2D.Double(doorX, doorY, 32, 32);
		// ĳ���� ���� �� �������� ����
		mainCharacter.start();
		gamePlayMusic.start();
	}

	@Override
	public void run() {
		try {
			while (true) {
				// ������ Ŭ���� ������ �̺�Ʈ�� ó�����ش�.
				if (gameClear()) {
					gameEndImage = new ImageIcon(getClass().getClassLoader().getResource("images/gameEnd.gif"))
							.getImage();
					if (gameClearSound == null) {
						gameClearSound = new Music("gameClearSound.mp3", false);
						gameClearSound2 = new Music("gameClearSound2.mp3", false);
						gameClearSound.start();
						gameClearSound2.start();

					}
					// ����Ҹ��� ���ش�.
					if (heartBeat1 != null)
						heartBeat1.close();
					if (heartBeat2 != null)
						heartBeat2.close();
					if (heartBeat3 != null)
						heartBeat3.close();

					// ���ӼҸ��� ���ش�.
					gamePlayMusic.close();

					// ĳ���͸� �����带 ���ش�.
					mainCharacter.interrupt();

					// ����Ŭ���� ȭ���� ����.
					isGameClear = true;
					// �������� ���� ������Ų��.
					stageNum++;
					Thread.sleep(1000);

					bloodEffect = new ImageIcon(getClass().getClassLoader().getResource("images/bloodEffect.gif"))
							.getImage();
					gameClearImage = new ImageIcon(getClass().getClassLoader().getResource("images/stageClear.gif"))
							.getImage();

					Thread.sleep(4000);

					// gif �̹��� ���ҽ��� �����ش�.
					gameEndImage.flush();
					bloodEffect.flush();
					gameClearImage.flush();

					// ���ǵ��� ���ش�.
					gameClearSound.close();
					gameClearSound2.close();
					continueGame();

				}

				// ���Ϳ� �浹������ �̺�Ʈ�� ó�����ش�.
				if (gameOver()) {
					gameEndImage = new ImageIcon(getClass().getClassLoader().getResource("images/gameEnd.gif"))
							.getImage();
					mainCharacter.interrupt();
					gamePlayMusic.close();
					if (heartBeat3 != null)
						heartBeat3.close();
					if (ghostDeathSound == null) {
						ghostDeathSound = new Music("ghostDeath.mp3", false);
						ghostDeathSound.start();
						Thread.sleep(2110);
						ghostDeathSound.close();

						// �ð����� �ΰ� gameDeathScreen�� ������� �� �Ѵ�.
						gameDeathScreen = new ImageIcon(
								getClass().getClassLoader().getResource("images/gameDeathScreen.gif")).getImage();
						Thread.sleep(1500);
						isDeath = true;
						return;
					}
				}
				// ���Ϳ��� �Ÿ��� ���� ����Ҹ��� �ٸ��� ���ش�.
				if (calDistance() > 450 && heartBeat1 == null) {
					if (heartBeat2 != null)
						heartBeat2.close();
					if (heartBeat3 != null)
						heartBeat3.close();
					heartBeat1 = new Music("heartBeat1.mp3", true);
					heartBeat1.start();
					heartBeat2 = null;
					heartBeat3 = null;
				}

				if (250 < calDistance() && calDistance() < 450 && heartBeat2 == null) {
					if (heartBeat1 != null)
						heartBeat1.close();
					if (heartBeat3 != null)
						heartBeat3.close();
					heartBeat2 = new Music("heartBeat2.mp3", true);
					heartBeat2.start();
					heartBeat1 = null;
					heartBeat3 = null;
				}

				if (calDistance() < 150 && heartBeat3 == null) {
					if (heartBeat1 != null)
						heartBeat1.close();
					if (heartBeat2 != null)
						heartBeat2.close();
					heartBeat3 = new Music("heartBeat3.mp3", true);
					heartBeat3.start();
					heartBeat1 = null;
					heartBeat2 = null;
				}
				// ����Ŭ��� ���ӿ����� �ƴҶ��� �����̰� �����Ѵ�.
				if (!gameClear() && !gameOver())
					ghost.monsterMove(mainCharacter.getCharX(), mainCharacter.getCharY());
				Thread.sleep(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isDeathScreen() {
		return isDeath;
	}

	public void setDeathScreen(boolean isDeathScreen) {
		this.isDeath = isDeathScreen;
	}

	public Image getGameEndImage() {
		return gameEndImage;
	}

	public void setGameEndImage(Image gameEndImage) {
		this.gameEndImage = gameEndImage;
	}

	public Image getGhostDeath() {
		return ghostDeath;
	}

	public void setGhostDeath(Image ghostDeath) {
		this.ghostDeath = ghostDeath;
	}

	public Image getGameDeathScreen() {
		return gameDeathScreen;
	}

	public void setGameDeathScreen(Image gameDeathScreen) {
		this.gameDeathScreen = gameDeathScreen;
	}

	public static int getStageNum() {
		return stageNum;
	}

	public static void setStageNum(int stageNum) {
		Game.stageNum = stageNum;
	}

	public Image getBloodEffect() {
		return bloodEffect;
	}

	public void setBloodEffect(Image bloodEffect) {
		this.bloodEffect = bloodEffect;
	}

	public Image getGameClearImage() {
		return gameClearImage;
	}

	public void setGameClearImage(Image gameClearImage) {
		this.gameClearImage = gameClearImage;
	}

}
