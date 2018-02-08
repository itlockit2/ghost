package ghost_01;

import java.io.*;

import javazoom.jl.player.*;

public class Music extends Thread {

	// Player는 Javazoom 에서 지원하는 라이브러리중 하나이다
	private Player player;
	// 한번만 재생이 되는지 무한정 재생이 되는지 결정하는 값
	private boolean isLoop;

	// 파일 입출력 관련된 필드값들
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private InputStream is;
	private String name;

	// 생성자를 통해서 곡의 이름과 반복결정값을 받는다
	public Music(String name, boolean isLoop) {

		try {
			// name 초기화
			this.name = name;
			// isLoop값 초기화
			this.isLoop = isLoop;
			// file을 가져온다
			//file = new File(getClass().getClassLoader().getResource("music/" + name).toURI());
			// 파일을 fis에 넣어주고
			//fis = Main.class.getClassLoader().getResourceAsStream("music/"+ name );
			
			//fis를 사용하면 Jar파일을 이용하게 될때 에러가 발생하므로 InputStream을 사용한다.
			is = getClass().getClassLoader().getResourceAsStream("music/"+name);
			// fis를 버퍼에 담아서 읽을수 있게 한다
			bis = new BufferedInputStream(is);
			// player는 이 버퍼를 담을수 있게 해준다.
			player = new Player(bis);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// 현재 음악이 어디까지 재생이 됫는지 알려준다 10초까지 재생이 되엇다면 10000이라는 값을 리턴
	// 즉 0.001초 단위까지 알려준다
	public int getTime() {
		if (player == null)
			return 0;
		return player.getPosition();
	}

	// 음악을 언제든지 종료 할 수 있다.
	public void close() {
		isLoop = false;
		player.close();
		// 쓰레드를 중지상태로 만든다.
		this.interrupt();
	}

	// Thread를 상속하면 무조건 넣어야 하는 메소드이다.
	// 곡을 재생시킨다.
	@Override
	public void run() {
		try {
			// isLoop가 true이면 곡은 무한재생된다.
			do {
				player.play();
				// 파일을 fis에 넣어주고
				//fis = new FileInputStream(file);
				is = getClass().getClassLoader().getResourceAsStream("music/"+name);
				// fis를 버퍼에 담아서 읽을수 있게 한다
				bis = new BufferedInputStream(is);
				// player는 이 버퍼를 담을수 있게 해준다.
				player = new Player(bis);
			} while (isLoop);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
