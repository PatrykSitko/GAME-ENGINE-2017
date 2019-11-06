/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.gfx.ui.buttons;

import game.gfx.ui.ClickListener;
import game.shapes.Shape;
import game.shapes.Shape.Geometry.Shapes;
import game.sound.SoundPlayer;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingUtilities;

/**
 *
 * @author Patryk Sitko
 */
public abstract class Button implements Observer {

    public Shape shape;
    protected boolean allowedToPlayTheHoveringSound = true;
    protected ClickListener clickListener;
    private final SoundPlayer hoveringSound;
    private final SoundPlayer clickSound;

    public boolean changeContentOnClick;
    protected boolean forceContentChange;

    public Button(Shapes s, ClickListener clickListener, SoundPlayer hoveringSound, SoundPlayer clickSound) {
        shape = new Shape().setShape(s);
        this.clickListener = clickListener;
        this.hoveringSound = hoveringSound;
        this.clickSound = clickSound;
    }

    public abstract void draw(Graphics2D g);

    @Override
    public void update(Observable o, Object event) {
        if (event instanceof MouseEvent) {
            shape.contains(((MouseEvent) event).getX(), ((MouseEvent) event).getY());
            switch (((MouseEvent) event).getID()) {
                default:
                    break;
                case MouseEvent.MOUSE_MOVED:
                    if (allowedToPlayTheHoveringSound == true && shape.hasContained()) {
                        allowedToPlayTheHoveringSound = false;
                        hoveringSound.play();
                    } else if (allowedToPlayTheHoveringSound == false && !shape.hasContained()) {
                        allowedToPlayTheHoveringSound = true;
                        hoveringSound.play();
                    }
                    break;
                case MouseEvent.MOUSE_CLICKED:
                    if (clickListener != null && shape.hasContained() && SwingUtilities.isLeftMouseButton((MouseEvent) event)) {
                        clickSound.play();
                        if (changeContentOnClick) {
                            forceContentChange = !forceContentChange;
                        }
                        clickListener.onClick();
                    }
            }
        }

    }

}
