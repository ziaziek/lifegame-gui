package com.pncomp.lifegamegui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField {

    public MyTextField(){
        init();
    }

    public MyTextField(final String text){
        super(text);
        init();
    }

    private void init(){
        addFocusListener(new MyFocusListener());
        setPreferredSize(new Dimension(100, 20));
    }

    class MyFocusListener implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {
            setBackground(Color.YELLOW);
        }

        @Override
        public void focusLost(FocusEvent e) {
            setBackground(Color.white);
        }
    }


}
