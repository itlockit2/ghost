package ghost_01;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("X��ǥ�� = " + e.getX() + "Y��ǥ�� = " + e.getY());
	}

}
