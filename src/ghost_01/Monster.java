package ghost_01;

import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class Monster extends Thread {

	private Image zombieImage;
	private Image ghostImage;

	// ���� ���ǵ�, ���� , ���� ũ��
	private final int MON_SPEED = 1;
	private final int GHOST_WIDTH = 38;
	private final int GHOST_HEIGHT = 54;

	// �̵���
	private double moveX;
	private double moveY;

	// ���� ��ǥ
	private int monX;
	private int monY;

	// ���� ��
	private double incline;

	private Rectangle2D mon2D;
	private int mon2DX;
	private int mon2DY;

	public Monster(String name) {
		if (name.equals("zombie")) {
			zombieImage = new ImageIcon(getClass().getClassLoader().getResource("images/zombieRightWalk.gif")).getImage();
			// ������ ������ġ�� �ٱ������� ���ش�.
			do {
				monX = (int) (Math.random() * 1280);
			} while (62 < monX && monX < 1206);
			do {
				monY = (int) (Math.random() * 720);
			} while (42 < monY && monY < 622);
			System.out.println(monX + " " + monY);
			// �ǰ� ���������� ���������ش�.
			mon2DX = monX;
			mon2DY = monY;
			mon2D = new Rectangle2D.Double(mon2DX, mon2DY, GHOST_WIDTH, GHOST_HEIGHT);
		}
		if (name.equals("ghost")) {
			ghostImage = new ImageIcon(getClass().getClassLoader().getResource("images/ghostMove.gif")).getImage();
			// ������ ������ġ�� �ٱ������� ���ش�.
			do {
				monX = (int) (Math.random() * 1280);
			} while (62 < monX && monX < 1206);
			do {
				monY = (int) (Math.random() * 720);
			} while (42 < monY && monY < 622);
			System.out.println(monX + " " + monY);
			// �ǰ� ���������� ���������ش�.
			mon2DX = monX + 55;
			mon2DY = monY + 36;
			mon2D = new Rectangle2D.Double(mon2DX, mon2DY, GHOST_WIDTH, GHOST_HEIGHT);
		}

	}

	public void monsterMove(int mainX, int mainY) {
		try {
			// ���Ͱ� �� �ۿ� �������� ���� ���� ���ؼ� ĳ���Ϳ��� �ٰ�����.
			if (62 > monX || monX > 1206 || 42 > monY || monY > 622) {
				// ĳ���Ϳ� ���� ������ ���⸦ ���ؼ� �̵� ����� �����Ѵ�.
				// ���� = Y��ǥ���� / X��ǥ����
				incline = (double) (mainY - monY) / (mainX - monX);

				// ���Ͱ� ĳ���ͺ��� �����ʿ� ������� �������� �����ϼ� �ְ� �����Ѵ�.
				if (mainX < monX) {
					moveX = -1 * MON_SPEED;
				} else {
					moveX = MON_SPEED;
				}
			}
			monX += moveX;
			mon2DX = monX + 55;
			if (moveY > 1 || moveY < -1) {
				monY += moveY;
				moveY = 0;
			} else {
				moveY += (moveX * incline);
				mon2DY = monY + 36;
			}
			mon2D.setRect(mon2DX, mon2DY, GHOST_WIDTH, GHOST_HEIGHT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Image getZombieImage() {
		return zombieImage;
	}

	public void setZombieImage(Image jombieImage) {
		this.zombieImage = jombieImage;
	}

	public Image getGhostImage() {
		return ghostImage;
	}

	public void setGhostImage(Image ghostImage) {
		this.ghostImage = ghostImage;
	}

	public int getMonX() {
		return monX;
	}

	public void setMonX(int monX) {
		monX = monX;
	}

	public int getMonY() {
		return monY;
	}

	public void setMonY(int monY) {
		monY = monY;
	}

	public Rectangle2D getGhost2D() {
		return mon2D;
	}

	public void setGhost2D(Rectangle2D ghost2d) {
		mon2D = ghost2d;
	}

}
