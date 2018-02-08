package ghost_01;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class Game extends Thread {

	// 캐릭터 하나 생성
	Character mainCharacter;

	// 고스트 몬스터 하나 생성
	Monster ghost;
	// 좀비 몬스터 하나 생성
	Monster zombie;

	// 시야이미지 좌표, 캐릭터 초기값
	private int sightX = -632;
	private int sightY = -353;
	private int initCharX = 633;
	private int initCharY = 338;

	// 스테이지 확인값
	private static int stageNum = 1;

	// 탈출구 좌표
	private int doorX;
	private int doorY;

	// 죽음 확인 값
	private boolean isDeath;

	// 게임 클리어 확인값
	private boolean isGameClear;

	// 캐릭터 시야
	private Image characterSight;
	// 게임 엔딩
	private Image gameEndImage;
	// 귀신 죽음
	private Image ghostDeath;
	// 게임 엔딩 스크린
	private Image gameDeathScreen;
	// 탈출구
	private Image exitDoor;
	private Rectangle2D exitDoor2D;
	// 게임클리어 이벤트
	private Image bloodEffect;
	private Image gameClearImage;

	// Game음악
	Music gamePlayMusic;
	Music heartBeat1;
	Music heartBeat2;
	Music heartBeat3;
	Music ghostDeathSound;
	Music gameClearSound;
	Music gameClearSound2;

	public Game() {
		// 캐릭터들 초기화
		mainCharacter = new Character("mainCharacter");
		// 몬스터 초기화
		ghost = new Monster("ghost");
		zombie = new Monster("zombie");
		// boolean값 초기화
		isDeath = false;
		isGameClear = false;
		// 게임음악 초기화
		gamePlayMusic = new Music("gamePlayMusic.mp3", true);
		// 이미지 파일들 초기화
		characterSight = new ImageIcon(getClass().getClassLoader().getResource("images/characterSight.png")).getImage();
		ghostDeath = new ImageIcon(getClass().getClassLoader().getResource("images/ghostDeath.gif")).getImage();

		// 탈출구를 그려준다.
		// 탈출구의 생성위치를 캐릭터 이동가능 범위로 변경해준다.
		// 너무 가까우면 게임이 빨리 끝나므로 캐릭터 반경 200픽셀 안에서는 생성이 안되게끔 설정해준다.
		do {
			doorX = (int) (Math.random() * 1280);
		} while (doorX < 62 || doorX > 1174 || (433 < doorX && doorX < 833));
		do {
			doorY = (int) (Math.random() * 720);
		} while (42 > doorY || doorY > 590);
		exitDoor = new ImageIcon(getClass().getClassLoader().getResource("images/exitDoor.png")).getImage();
		exitDoor2D = new Rectangle2D.Double(doorX, doorY, 32, 32);
		// 캐릭터 실행 및 게임음악 실행
		mainCharacter.start();
		gamePlayMusic.start();

	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(ghost.getGhostImage(), ghost.getMonX(), ghost.getMonY(), null);
		// 몬스터의 박스표시 테스트용 사용이 다 끝나면 지우자.
		g.draw(ghost.getGhost2D());
		g.drawImage(zombie.getZombieImage(), 300, 200,50,78, null);
		// 캐릭터 시야 제한
		/*
		 * g.drawImage(characterSight, sightX + (-initCharX + mainCharacter.getCharX()),
		 * sightY + (-initCharY + mainCharacter.getCharY()), null);
		 */
		g.drawImage(mainCharacter.getCharcaterImage(), mainCharacter.getCharX(), mainCharacter.getCharY(), null);
		// 캐릭터의 박스표시 사용이 다 끝나면 지우자
		g.draw(mainCharacter.getChar2D());

		// 탈출구를 그려준다.
		g.drawImage(exitDoor, doorX, doorY, 32, 32, null);
		// 탈출구의 박스표시 사용이 다 끝나면 지우자
		g.draw(exitDoor2D);

		// 게임이 끝나면 화면을 어둡게 해준다.
		if (isGameClear) {
			g.drawImage(gameEndImage, sightX + (-initCharX + mainCharacter.getCharX()),
					sightY + (-initCharY + mainCharacter.getCharY()), null);
			g.drawImage(bloodEffect, 0, 0, null);
			g.drawImage(gameClearImage, 0, 0, null);
		}

		// 게임이 끝나면 화면을 어둡게 해준다.
		if (gameOver()) {
			g.drawImage(gameEndImage, sightX + (-initCharX + mainCharacter.getCharX()),
					sightY + (-initCharY + mainCharacter.getCharY()), null);
			g.drawImage(ghostDeath, 300, 150, null);
			g.drawImage(gameDeathScreen, 0, 0, null);
		}

	}

	// 몬스터와 충돌했는지를 리턴해준다.
	public boolean gameOver() {
		return mainCharacter.getChar2D().intersects(ghost.getGhost2D());
	}

	// 문에 들어갔는지 확인한다.
	public boolean gameClear() {
		return mainCharacter.getChar2D().intersects(exitDoor2D);
	}

	// 몬스터와의 거리를 리턴해준다
	public double calDistance() {
		double distanceX = Math.pow(mainCharacter.getCharX() - ghost.getMonX(), 2);
		double distanceY = Math.pow(mainCharacter.getCharY() - ghost.getMonY(), 2);
		return Math.sqrt(distanceX + distanceY);
	}

	public void continueGame() {
		// 게임클리어 gif 파일들을 리소스를 날려준다.
		gameEndImage.flush();
		bloodEffect.flush();
		gameClearImage.flush();

		// 캐릭터들 초기화
		mainCharacter = new Character("mainCharacter");
		// 몬스터 초기화
		ghost = new Monster("ghost");
		zombie = new Monster("zombie");
		// boolean값 초기화
		isDeath = false;
		isGameClear = false;
		// 게임음악 초기화
		gamePlayMusic = new Music("gamePlayMusic.mp3", true);
		gameClearSound = null;
		gameClearSound2 = null;

		// 이미지 파일들 초기화
		characterSight = new ImageIcon(getClass().getClassLoader().getResource("images/characterSight.png")).getImage();

		ghostDeath = new ImageIcon(getClass().getClassLoader().getResource("images/ghostDeath.gif")).getImage();

		// 탈출구를 그려준다.
		// 탈출구의 생성위치를 캐릭터 이동가능 범위로 변경해준다.
		// 너무 가까우면 게임이 빨리 끝나므로 캐릭터 반경 200픽셀 안에서는 생성이 안되게끔 설정해준다.
		do {
			doorX = (int) (Math.random() * 1280);
		} while (doorX < 62 || doorX > 1174 || (433 < doorX && doorX < 833));
		do {
			doorY = (int) (Math.random() * 720);
		} while (42 > doorY || doorY > 590);
		exitDoor = new ImageIcon(getClass().getClassLoader().getResource("images/exitDoor.png")).getImage();
		exitDoor2D = new Rectangle2D.Double(doorX, doorY, 32, 32);
		// 캐릭터 실행 및 게임음악 실행
		mainCharacter.start();
		gamePlayMusic.start();
	}

	@Override
	public void run() {
		try {
			while (true) {
				// 게임을 클리어 햇을떄 이벤트를 처리해준다.
				if (gameClear()) {
					gameEndImage = new ImageIcon(getClass().getClassLoader().getResource("images/gameEnd.gif"))
							.getImage();
					if (gameClearSound == null) {
						gameClearSound = new Music("gameClearSound.mp3", false);
						gameClearSound2 = new Music("gameClearSound2.mp3", false);
						gameClearSound.start();
						gameClearSound2.start();

					}
					// 심장소리를 꺼준다.
					if (heartBeat1 != null)
						heartBeat1.close();
					if (heartBeat2 != null)
						heartBeat2.close();
					if (heartBeat3 != null)
						heartBeat3.close();

					// 게임소리도 꺼준다.
					gamePlayMusic.close();

					// 캐릭터를 쓰레드를 꺼준다.
					mainCharacter.interrupt();

					// 게임클리어 화면을 띄운다.
					isGameClear = true;
					// 스테이지 수를 증가시킨다.
					stageNum++;
					Thread.sleep(1000);

					bloodEffect = new ImageIcon(getClass().getClassLoader().getResource("images/bloodEffect.gif"))
							.getImage();
					gameClearImage = new ImageIcon(getClass().getClassLoader().getResource("images/stageClear.gif"))
							.getImage();

					Thread.sleep(4000);

					// gif 이미지 리소스를 날려준다.
					gameEndImage.flush();
					bloodEffect.flush();
					gameClearImage.flush();

					// 음악들을 꺼준다.
					gameClearSound.close();
					gameClearSound2.close();
					continueGame();

				}

				// 몬스터와 충돌했을때 이벤트를 처리해준다.
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

						// 시간차를 두고 gameDeathScreen이 띄워지게 끔 한다.
						gameDeathScreen = new ImageIcon(
								getClass().getClassLoader().getResource("images/gameDeathScreen.gif")).getImage();
						Thread.sleep(1500);
						isDeath = true;
						return;
					}
				}
				// 몬스터와의 거리에 따라 심장소리를 다르게 해준다.
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
				// 게임클리어나 게임오버가 아닐때만 움직이게 설정한다.
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
