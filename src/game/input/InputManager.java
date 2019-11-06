package game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;

public class InputManager extends Observable implements MouseListener, MouseMotionListener, KeyListener {

    static int mouseXpos;
    static int mouseYpos;

    @Override
    public void mouseDragged(MouseEvent e) {
        setChanged();
        notifyObservers(InputAction.MOUSE_DRAGGED);
        setChanged();
        notifyObservers(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseXpos = e.getX();
        mouseYpos = e.getY();
        setChanged();
        notifyObservers(InputAction.MOUSE_MOVED);
        setChanged();
        notifyObservers(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        setChanged();
        notifyObservers(InputAction.MOUSE_CLICKED);
        setChanged();
        notifyObservers(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setChanged();
        notifyObservers(InputAction.MOUSE_PRESSED);
        setChanged();
        notifyObservers(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setChanged();
        notifyObservers(InputAction.MOUSE_RELEASED);
        setChanged();
        notifyObservers(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setChanged();
        notifyObservers(InputAction.MOUSE_ENTERED);
        setChanged();
        notifyObservers(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setChanged();
        notifyObservers(InputAction.MOUSE_EXITED);
        setChanged();
        notifyObservers(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        setChanged();
        notifyObservers(InputAction.KEY_TYPED);
        setChanged();
        notifyObservers(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        setChanged();
        notifyObservers(InputAction.KEY_PRESSED);
        setChanged();
        notifyObservers(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        setChanged();
        notifyObservers(InputAction.KEY_RELEASED);
        setChanged();
        notifyObservers(e);
    }

}
