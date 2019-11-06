package game.gfx.ui.sliders;

import game.gfx.ui.buttons.ImageButton;
import game.shapes.Shape;
import game.shapes.Shape.Geometry;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class Slider extends Observable implements Observer {

    private ImageButton sliderButton;
    private int ammountOfSectors;
    private int sectorSize;

    private Shape sliderBackground;
    private BufferedImage[] sliderBackgroundImages;

    private int currentSector;
    private boolean hovering, hoveringBackground;
    private boolean locked;

    public Slider(ImageButton sliderButton, BufferedImage[] sliderBackgroundImage, int sliderWidth, int sectors) {
        addObserver(this.sliderButton = sliderButton);
        {
            sliderBackground = new Shape();
            sliderBackground.setShape(Shape.Geometry.Shapes.RECTANGULAR);
            sliderBackground.
                    set(Geometry.X, sliderButton.shape.get(Geometry.X)).
                    set(Geometry.Y, sliderButton.shape.get(Geometry.Y)).
                    set(Geometry.WIDTH, sliderWidth).
                    set(Geometry.HEIGHT, sliderButton.shape.get(Geometry.HEIGHT) / 3);
        }
        this.sliderBackgroundImages = sliderBackgroundImage;
        this.ammountOfSectors = currentSector = sectors;
        this.sectorSize = sliderWidth / ammountOfSectors;
    }

    @Override
    public void update(Observable o, Object arg) {
        notifyObservers(arg);
        setChanged();
        if (arg instanceof MouseEvent) {
            MouseEvent event = (MouseEvent) arg;

            switch (event.getID()) {
                case MouseEvent.MOUSE_MOVED:
                    if (sliderButton.shape.contains(event.getX(), event.getY())) {
                        hoveringBackground = false;
                        if (!hovering) {
                            hovering = true;
                        }
                    } else {
                        hovering = false;
                        if (sliderBackground.contains(event.getX(), event.getY())) {
                            if (!hoveringBackground) {
                                hoveringBackground = true;
                            }
                        }
                    }
                    break;

                case MouseEvent.MOUSE_PRESSED:
                    if (SwingUtilities.isLeftMouseButton(event)) {
                        locked = true;
                        if (sliderBackground.contains(event.getX(), event.getY())) {
                            for (int i = 0; i <= ammountOfSectors; i++) {
                                if (sliderBackground.get(Geometry.X) + sectorSize * i < event.getX()) {
                                    setCurrentSector(i);
                                } else {
                                    break;
                                }
                            }
                            sliderButton.shape.set(Geometry.X, isInBounds(event.getX() - sliderButton.shape.get(Geometry.WIDTH) / 2));
                            hovering = sliderButton.shape.contains(event.getX(), event.getY());
                        }
                    }
                    break;

                case MouseEvent.MOUSE_DRAGGED:
                    if (!hovering || !locked) {
                        return;
                    }
                    for (int i = 0; i <= ammountOfSectors; i++) {
                        if (sliderBackground.get(Geometry.X) + sectorSize * i < isInBounds(event.getX())) {
                            setCurrentSector(i);
                        } else {
                            break;
                        }
                    }
                    sliderButton.shape.set(Geometry.X, isInBounds(event.getX() - sliderButton.shape.get(Geometry.WIDTH) / 2));
                    break;

                case MouseEvent.MOUSE_RELEASED:
                    if (!sliderButton.shape.contains(event.getX(), event.getY())) {
                        hovering = false;
                    }
                    locked = false;
                    break;
            }
        }
    }

    private int isInBounds(int newPosition) {
        if (newPosition < sliderBackground.get(Geometry.X) - sliderButton.shape.get(Geometry.WIDTH) / 2) {
            return sliderBackground.get(Geometry.X) - sliderButton.shape.get(Geometry.WIDTH) / 2;
        } else if (newPosition > sliderBackground.get(Geometry.X) + sliderButton.shape.get(Geometry.WIDTH) / 2) {
            return sliderBackground.get(Geometry.X) + sliderButton.shape.get(Geometry.WIDTH) / 2;
        } else {
            return newPosition;
        }
    }

    public int getCurrentSector() {
        return currentSector;
    }

    public void setCurrentSector(int currentSector) {
        if (currentSector >= 0 && currentSector <= ammountOfSectors) {
            this.currentSector = currentSector;
        }
    }

    public void moveButtonTo(int sector) {
        sector = sector <= 0 ? 0 : sector;
        sector = sector >= ammountOfSectors ? ammountOfSectors : sector;
        sliderButton.shape.set(Geometry.X, isInBounds(sliderBackground.get(Geometry.X) - sliderButton.shape.get(Geometry.WIDTH) / 2 + sectorSize * sector));
    }

    public void draw(Graphics2D g) {
        if (hovering) {
            g.drawImage(sliderBackgroundImages[1], sliderBackground.get(Geometry.X), sliderBackground.get(Geometry.Y),
                    sliderBackground.get(Geometry.WIDTH), sliderBackground.get(Geometry.HEIGHT), null);
        } else {
            g.drawImage(sliderBackgroundImages[0], sliderBackground.get(Geometry.X), sliderBackground.get(Geometry.Y),
                    sliderBackground.get(Geometry.WIDTH), sliderBackground.get(Geometry.HEIGHT), null);
        }
        sliderButton.draw(g);
    }
}
