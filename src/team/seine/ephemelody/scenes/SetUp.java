package team.seine.ephemelody.scenes;

import team.seine.ephemelody.data.Data;
import team.seine.ephemelody.utils.Load;
import team.seine.ephemelody.utils.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class SetUp extends JPanel implements Scenes, MouseMotionListener, MouseListener {
    public Image setupBackground;
    public Image[] upButton;
    public Image[] downButton;
    public Image[] finishButton;
    int upButtonStatus[], downButtonStatus[], finishButtonStatus;
    public SetUp() {
        upButtonStatus = new int[4];
        downButtonStatus = new int[4];
        for (int i = 0; i < 4; i++) {
            upButtonStatus[i] = downButtonStatus[i] = 0;
        }
        finishButtonStatus = 0;
        setBounds(0, 0, Data.WIDTH, Data.HEIGHT);
        setVisible(true);
        setOpaque(false);
        setupBackground = Load.image("setup/设置背景1.png");
        upButton = new Image[] {
                Load.image("setup/上.png"), Load.image("setup/上_经过.png"), Load.image("setup/上_按下.png")
        };
        downButton = new Image[] {
                Load.image("setup/下.png"), Load.image("setup/下_经过.png"), Load.image("setup/下_按下.png")
        };
        finishButton = new Image[] {
                Load.image("setup/完成.png"), Load.image("setup/完成_经过.png"), Load.image("setup/完成_按下.png")
        };
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        onMouse(e.getX(), e.getY(), Scenes.MOUSE_DOWN);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        onMouse(e.getX(), e.getY(), Scenes.MOUSE_MOVED);
    }

    @Override
    public void onKeyDown(int keyCode) {

    }

    @Override
    public void onKeyUp(int keyCode) {

    }

    @Override
    public void onMouse(int x, int y, int struts) {
        for (int i = 0; i < 4; i++) {
            upButtonStatus[i] = downButtonStatus[i] = 0;
        }
        finishButtonStatus = 0;
        int buttonStruts = struts == Scenes.MOUSE_MOVED ? 1 : struts == Scenes.MOUSE_DOWN ? 2 : 0;
        if(Rect.isInternal(x, y, 233, 360, 78, 57)) {
            upButtonStatus[0] = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                if (Data.offset < 1000) {
                    Data.offset += 1;
                }
            }
        } else if (Rect.isInternal(x, y, 478, 360, 78, 57)) {
            upButtonStatus[1] = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                if (Data.noteSpeed < 6.0) {
                    Data.noteSpeed += 0.1;
                }
            }
        } else if (Rect.isInternal(x, y, 723, 360, 78, 57)) {
            upButtonStatus[2] = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                if (Data.noteVolume < 10) {
                    Data.noteVolume += 1;
                }
            }
        } else if (Rect.isInternal(x, y, 968, 360, 78, 57)) {
            upButtonStatus[3] = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                if (Data.volume < 10) {
                    Data.volume += 1;
                }
            }
        } else if (Rect.isInternal(x, y, 233, 650, 78, 57)) {
            downButtonStatus[0] = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                if (Data.offset > -1000) {
                    Data.offset -= 1;
                }
            }
        } else if (Rect.isInternal(x, y, 478, 650, 78, 57)) {
            downButtonStatus[1] = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                if (Data.noteSpeed > 1.0) {
                    Data.noteSpeed -= 0.1;
                }
            }
        } else if (Rect.isInternal(x, y, 723, 650, 78, 57)) {
            downButtonStatus[2] = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                if (Data.noteVolume > 0) {
                    Data.noteVolume -= 1;
                }
            }
        } else if (Rect.isInternal(x, y, 968, 650, 78, 57)) {
            downButtonStatus[3] = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                if (Data.volume > 0) {
                    Data.volume -= 1;
                }
            }
        } else if (Rect.isInternal(x, y, (Data.WIDTH - 988) / 2, 738, 980, 42)) {
            finishButtonStatus = buttonStruts;
            if (struts == Scenes.MOUSE_DOWN) {
                Data.canvas.switchScenes("Home");
            }
        }
    }

    public void paint(Graphics g) {
        g.drawImage(setupBackground, (Data.WIDTH - 988) / 2, (Data.HEIGHT - 605) / 2, null);
        int buttonX = 233;
        for (int i = 0; i < 4; i++) {
            g.drawImage(upButton[upButtonStatus[i]], buttonX, 360, null);
            g.drawImage(downButton[downButtonStatus[i]], buttonX, 650, null);
            buttonX += 245;
        }
        g.drawImage(finishButton[finishButtonStatus], (Data.WIDTH - 988) / 2, 738, null);
//        Data.canvas.paintString(String.valueOf(Data.offset), g, 250, 505, 50);
        g.setFont(new Font("黑体", Font.PLAIN, 80));
        g.setColor(new Color(119, 97, 125));
        g.drawString(String.valueOf(Data.offset), 253, 550);
        g.drawString(String.format("%.1f", Data.noteSpeed), 460, 550);
        g.drawString(String.valueOf(Data.noteVolume), 740, 550);
        g.drawString(String.valueOf(Data.volume), 990, 550);
    }
}
