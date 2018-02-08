package ghost_01;

import java.io.*;

import javazoom.jl.player.*;

public class Music extends Thread {

	// Player�� Javazoom ���� �����ϴ� ���̺귯���� �ϳ��̴�
	private Player player;
	// �ѹ��� ����� �Ǵ��� ������ ����� �Ǵ��� �����ϴ� ��
	private boolean isLoop;

	// ���� ����� ���õ� �ʵ尪��
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private InputStream is;
	private String name;

	// �����ڸ� ���ؼ� ���� �̸��� �ݺ��������� �޴´�
	public Music(String name, boolean isLoop) {

		try {
			// name �ʱ�ȭ
			this.name = name;
			// isLoop�� �ʱ�ȭ
			this.isLoop = isLoop;
			// file�� �����´�
			//file = new File(getClass().getClassLoader().getResource("music/" + name).toURI());
			// ������ fis�� �־��ְ�
			//fis = Main.class.getClassLoader().getResourceAsStream("music/"+ name );
			
			//fis�� ����ϸ� Jar������ �̿��ϰ� �ɶ� ������ �߻��ϹǷ� InputStream�� ����Ѵ�.
			is = getClass().getClassLoader().getResourceAsStream("music/"+name);
			// fis�� ���ۿ� ��Ƽ� ������ �ְ� �Ѵ�
			bis = new BufferedInputStream(is);
			// player�� �� ���۸� ������ �ְ� ���ش�.
			player = new Player(bis);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// ���� ������ ������ ����� �̴��� �˷��ش� 10�ʱ��� ����� �Ǿ��ٸ� 10000�̶�� ���� ����
	// �� 0.001�� �������� �˷��ش�
	public int getTime() {
		if (player == null)
			return 0;
		return player.getPosition();
	}

	// ������ �������� ���� �� �� �ִ�.
	public void close() {
		isLoop = false;
		player.close();
		// �����带 �������·� �����.
		this.interrupt();
	}

	// Thread�� ����ϸ� ������ �־�� �ϴ� �޼ҵ��̴�.
	// ���� �����Ų��.
	@Override
	public void run() {
		try {
			// isLoop�� true�̸� ���� ��������ȴ�.
			do {
				player.play();
				// ������ fis�� �־��ְ�
				//fis = new FileInputStream(file);
				is = getClass().getClassLoader().getResourceAsStream("music/"+name);
				// fis�� ���ۿ� ��Ƽ� ������ �ְ� �Ѵ�
				bis = new BufferedInputStream(is);
				// player�� �� ���۸� ������ �ְ� ���ش�.
				player = new Player(bis);
			} while (isLoop);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
