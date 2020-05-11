package com.carlwu.minipacs.ui.panel;

import com.carlwu.minipacs.App;
import com.carlwu.minipacs.ui.UiConsts;
import com.carlwu.minipacs.ui.component.MyIconButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.carlwu.minipacs.tools.ConstantsTools;
import com.carlwu.minipacs.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 高级选项面板
 *
 * @author Bob
 */
public class SettingPanelOption extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonSave;

    private static JCheckBox checkBoxAutoBak;

    private static JCheckBox checkBoxDebug;

    private static JCheckBox checkBoxStrict;

    private static JTextField textFieldLocalAeTitle,textFieldLocalPort,
            textFieldRemoteAeTitle,textFieldRemotePort,textFieldRemoteUrl,
            textFieldLocalDcmPath,textFieldLocalDbPath;

    private static final Logger logger = LoggerFactory.getLogger(SettingPanelOption.class);

    /**
     * 构造
     */
    public SettingPanelOption() {
        initialize();
        addComponent();
        setCurrentOption();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     * 添加组件
     */
    private void addComponent() {

        this.add(getCenterPanel(), BorderLayout.CENTER);
        this.add(getDownPanel(), BorderLayout.SOUTH);

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
        panelCenter.setLayout(new GridLayout(1, 1));

        // 设置Grid
        JPanel panelGridOption = new JPanel();
        panelGridOption.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridOption.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        // 初始化组件
        JPanel panelItem1 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem2 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem3 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem4 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem5 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem6 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem7 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem8 = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 0));

        panelItem1.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem2.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem3.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem4.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem5.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem6.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem7.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem8.setBackground(UiConsts.MAIN_BACK_COLOR);

        panelItem1.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem2.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem3.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem4.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem5.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem6.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem7.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);
        panelItem8.setPreferredSize(UiConsts.PANEL_ITEM_SIZE);


        // 各Item
        JLabel labelLocalDbPath = new JLabel("数据存储");
        labelLocalDbPath.setPreferredSize(new Dimension(120, 26));
        textFieldLocalDbPath= new JTextField();
        labelLocalDbPath.setBackground(UiConsts.MAIN_BACK_COLOR);
        labelLocalDbPath.setFont(UiConsts.FONT_RADIO);
        textFieldLocalDbPath.setFont(UiConsts.FONT_RADIO);
        textFieldLocalDbPath.setPreferredSize(new Dimension(334, 26));
        textFieldLocalDbPath.setEditable(false);
        panelItem1.add(labelLocalDbPath);
        panelItem1.add(textFieldLocalDbPath);

        JLabel labelDcmPath = new JLabel("DICOM存储");
        labelDcmPath.setPreferredSize(new Dimension(120, 26));
        textFieldLocalDcmPath= new JTextField();
        labelDcmPath.setBackground(UiConsts.MAIN_BACK_COLOR);
        labelDcmPath.setFont(UiConsts.FONT_RADIO);
        textFieldLocalDcmPath.setFont(UiConsts.FONT_RADIO);
        textFieldLocalDcmPath.setPreferredSize(new Dimension(334, 26));
        textFieldLocalDcmPath.setEditable(false);
        panelItem2.add(labelDcmPath);
        panelItem2.add(textFieldLocalDcmPath);



        JLabel labelLocalAeTitle = new JLabel("本地AETitle");
        labelLocalAeTitle.setPreferredSize(new Dimension(120, 26));
        textFieldLocalAeTitle= new JTextField();
        labelLocalAeTitle.setBackground(UiConsts.MAIN_BACK_COLOR);
        labelLocalAeTitle.setFont(UiConsts.FONT_RADIO);
        textFieldLocalAeTitle.setFont(UiConsts.FONT_RADIO);
        textFieldLocalAeTitle.setPreferredSize(new Dimension(334, 26));
        panelItem4.add(labelLocalAeTitle);
        panelItem4.add(textFieldLocalAeTitle);



        JLabel labelLocalPort = new JLabel("本地端口");
        labelLocalPort.setPreferredSize(new Dimension(120, 26));
        textFieldLocalPort= new JTextField();
        labelLocalPort.setBackground(UiConsts.MAIN_BACK_COLOR);
        labelLocalPort.setFont(UiConsts.FONT_RADIO);
        textFieldLocalPort.setFont(UiConsts.FONT_RADIO);
        textFieldLocalPort.setPreferredSize(new Dimension(334, 26));
        panelItem5.add(labelLocalPort);
        panelItem5.add(textFieldLocalPort);



        JLabel labelRemoteAeTitle = new JLabel("远程AETitle");
        labelRemoteAeTitle.setPreferredSize(new Dimension(120, 26));
        textFieldRemoteAeTitle= new JTextField();
        labelRemoteAeTitle.setBackground(UiConsts.MAIN_BACK_COLOR);
        labelRemoteAeTitle.setFont(UiConsts.FONT_RADIO);
        textFieldRemoteAeTitle.setFont(UiConsts.FONT_RADIO);
        textFieldRemoteAeTitle.setPreferredSize(new Dimension(334, 26));
        panelItem6.add(labelRemoteAeTitle);
        panelItem6.add(textFieldRemoteAeTitle);


        JLabel labelRemotePort = new JLabel("远程端口");
        labelRemotePort.setPreferredSize(new Dimension(120, 26));
        textFieldRemotePort= new JTextField();
        labelRemotePort.setBackground(UiConsts.MAIN_BACK_COLOR);
        labelRemotePort.setFont(UiConsts.FONT_RADIO);
        textFieldRemotePort.setFont(UiConsts.FONT_RADIO);
        textFieldRemotePort.setPreferredSize(new Dimension(334, 26));
        panelItem7.add(labelRemotePort);
        panelItem7.add(textFieldRemotePort);


        JLabel labelRemoteUrl = new JLabel("远程IP地址");
        labelRemoteUrl.setPreferredSize(new Dimension(120, 26));
        textFieldRemoteUrl= new JTextField();
        labelRemoteUrl.setBackground(UiConsts.MAIN_BACK_COLOR);
        labelRemoteUrl.setFont(UiConsts.FONT_RADIO);
        textFieldRemoteUrl.setFont(UiConsts.FONT_RADIO);
        textFieldRemoteUrl.setPreferredSize(new Dimension(334, 26));
        panelItem8.add(labelRemoteUrl);
        panelItem8.add(textFieldRemoteUrl);







        // 组合元素
        panelGridOption.add(panelItem1);
        panelGridOption.add(panelItem2);
        panelGridOption.add(panelItem3);
        panelGridOption.add(panelItem4);
        panelGridOption.add(panelItem5);
        panelGridOption.add(panelItem6);
        panelGridOption.add(panelItem7);
        panelGridOption.add(panelItem8);

        panelCenter.add(panelGridOption);
        return panelCenter;
    }

    /**
     * 底部面板
     *
     * @return
     */
    private JPanel getDownPanel() {
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelDown.setLayout(new FlowLayout(FlowLayout.RIGHT, UiConsts.MAIN_H_GAP, 15));

        buttonSave = new MyIconButton(UiConsts.ICON_SAVE, UiConsts.ICON_SAVE_ENABLE,
                UiConsts.ICON_SAVE_DISABLE, "");
        panelDown.add(buttonSave);

        return panelDown;
    }

    /**
     * 设置当前combox选项状态
     */
    public static void setCurrentOption() {

        textFieldLocalPort.setText(ConstantsTools.CONFIGER.getLocalPacsPort());
        textFieldLocalAeTitle.setText(ConstantsTools.CONFIGER.getLocalPacsAeTitle());
        textFieldLocalDcmPath.setText(ConstantsTools.CONFIGER.getLocalDicomPath());
        textFieldLocalDbPath.setText(ConstantsTools.CONFIGER.getSqliteDB());
        textFieldRemotePort.setText(ConstantsTools.CONFIGER.getRemotePacsPort());
        textFieldRemoteAeTitle.setText(ConstantsTools.CONFIGER.getRemotePacsAeTitle());
        textFieldRemoteUrl.setText(ConstantsTools.CONFIGER.getRemotePacsUrl());
    }

    /**
     * 为相关组件添加事件监听
     */
    private void addListener() {
        buttonSave.addActionListener(e -> {
            try {

                ConstantsTools.CONFIGER.setLocalPacsAeTitle(textFieldLocalAeTitle.getText());
                ConstantsTools.CONFIGER.setLocalPacsPort(textFieldLocalPort.getText());
                ConstantsTools.CONFIGER.setRemotePacsAeTitle(textFieldRemoteAeTitle.getText());
                ConstantsTools.CONFIGER.setRemotePacsPort(textFieldRemotePort.getText());
                ConstantsTools.CONFIGER.setRemotePacsUrl(textFieldRemoteUrl.getText());
                JOptionPane.showMessageDialog(App.settingPanel, PropertyUtil.getProperty("ds.ui.save.success"),
                        PropertyUtil.getProperty("ds.ui.tips"), JOptionPane.PLAIN_MESSAGE);
           } catch (Exception e1) {
                JOptionPane.showMessageDialog(App.settingPanel, PropertyUtil.getProperty("ds.ui.save.fail") + e1.getMessage(),
                        PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.ERROR_MESSAGE);
                logger.error("Write to xml file error" + e1.toString());
            }

        });


    }
}
