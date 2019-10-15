package com.carlwu.minipacs.ui.panel;

import com.carlwu.minipacs.App;
import com.carlwu.minipacs.tools.PropertyUtil;
import com.carlwu.minipacs.ui.UiConsts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 设置面板
 *
 * @author Bob
 */
public class LoginPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static JPanel panelOption;
    public static JPanel panelMain;
    private static JPanel loginPanelOption;

    /**
     * 构造
     */
    public LoginPanel() {
        initialize();
        addComponent();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
        loginPanelOption = new LoginPanelOption();
    }

    /**
     * 添加组件
     */
    private void addComponent() {

        this.add(getUpPanel(), BorderLayout.NORTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);

    }

    /**
     * 上部面板
     *
     * @return
     */
    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.CENTER, UiConsts.MAIN_H_GAP, 5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.login.title"));
        labelTitle.setFont(UiConsts.FONT_TITLE);
        labelTitle.setForeground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelUp.add(labelTitle);

        return panelUp;
    }

    /**
     * 中部面板
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // 中间面板
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelCenter.setLayout(new BorderLayout());
        panelOption = new JPanel();
        panelOption.setBackground(new Color(69, 186, 121));
        panelOption.setLayout(new BorderLayout());
        Dimension preferredSizeListItem = new Dimension(245, 48);
        panelOption.setPreferredSize(preferredSizeListItem);


        // 设置Panel
        panelMain = new JPanel();
        panelMain.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelMain.setLayout(new BorderLayout());
        panelMain.add(loginPanelOption);

//        panelCenter.add(panelList, BorderLayout.WEST);
        panelCenter.add(panelMain, BorderLayout.CENTER);

        return panelCenter;
    }

    /**
     * 为相关组件添加事件监听
     */
    private void addListener() {
        panelOption.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                panelOption.setBackground(new Color(69, 186, 121));

                LoginPanel.panelMain.removeAll();
                SettingPanelOption.setCurrentOption();
                LoginPanel.panelMain.add(loginPanelOption);
                App.settingPanel.updateUI();

            }
        });


    }
}