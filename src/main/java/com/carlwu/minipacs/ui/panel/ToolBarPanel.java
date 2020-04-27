package com.carlwu.minipacs.ui.panel;

import com.carlwu.minipacs.App;
import com.carlwu.minipacs.ui.UiConsts;
import com.carlwu.minipacs.ui.component.MyIconButton;
import com.carlwu.minipacs.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 工具栏面板
 *
 * @author Bob
 */
public class ToolBarPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton buttonStatus;
    private static MyIconButton buttonDatabase;
    private static MyIconButton buttonBackup;
    private static MyIconButton buttonSetting;

    /**
     * 构造
     */
    public ToolBarPanel() {
        initialize();
        addButtion();
        addListener();
    }

    /**
     * 初始化
     */
    private void initialize() {
        Dimension preferredSize = new Dimension(48, UiConsts.MAIN_WINDOW_HEIGHT);
        this.setPreferredSize(preferredSize);
        this.setMaximumSize(preferredSize);
        this.setMinimumSize(preferredSize);
        this.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        this.setLayout(new GridLayout(2, 1));
    }

    /**
     * 添加工具按钮
     */
    private void addButtion() {

        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(-2, -2, -4));
        JPanel panelDown = new JPanel();
        panelDown.setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelDown.setLayout(new BorderLayout(0, 0));

        buttonStatus = new MyIconButton(UiConsts.ICON_STATUS_ENABLE, UiConsts.ICON_STATUS_ENABLE,
                UiConsts.ICON_STATUS, PropertyUtil.getProperty("ds.ui.status.title"));
        buttonDatabase = new MyIconButton(UiConsts.ICON_DATABASE, UiConsts.ICON_DATABASE_ENABLE,
                UiConsts.ICON_DATABASE, PropertyUtil.getProperty("ds.ui.database.title"));
        buttonBackup = new MyIconButton(UiConsts.ICON_BACKUP, UiConsts.ICON_BACKUP_ENABLE,
                UiConsts.ICON_BACKUP, PropertyUtil.getProperty("ds.ui.backup.title"));
        buttonSetting = new MyIconButton(UiConsts.ICON_SETTING, UiConsts.ICON_SETTING_ENABLE,
                UiConsts.ICON_SETTING, PropertyUtil.getProperty("ds.ui.setting.title"));

        panelUp.add(buttonStatus);
        panelUp.add(buttonDatabase);
        panelUp.add(buttonBackup);

        panelDown.add(buttonSetting, BorderLayout.SOUTH);
        this.add(panelUp);
        this.add(panelDown);

    }

    /**
     * 为各按钮添加事件动作监听
     */
    private void addListener() {
        buttonStatus.addActionListener(e -> {

            buttonStatus.setIcon(UiConsts.ICON_STATUS_ENABLE);
            buttonDatabase.setIcon(UiConsts.ICON_DATABASE);
            buttonBackup.setIcon(UiConsts.ICON_BACKUP);
            buttonSetting.setIcon(UiConsts.ICON_SETTING);

            App.mainPanelCenter.removeAll();
            StatusPanel.setContent();
            App.mainPanelCenter.add(App.uploadPanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });

        buttonDatabase.addActionListener(e -> {

            buttonStatus.setIcon(UiConsts.ICON_STATUS);
            buttonDatabase.setIcon(UiConsts.ICON_DATABASE_ENABLE);
            buttonBackup.setIcon(UiConsts.ICON_BACKUP);
            buttonSetting.setIcon(UiConsts.ICON_SETTING);

            App.mainPanelCenter.removeAll();
            DatabasePanelFrom.setContent();
            App.mainPanelCenter.add(App.databasePanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });

        buttonBackup.addActionListener(e -> {

            buttonStatus.setIcon(UiConsts.ICON_STATUS);
            buttonDatabase.setIcon(UiConsts.ICON_DATABASE);
            buttonBackup.setIcon(UiConsts.ICON_BACKUP_ENABLE);
            buttonSetting.setIcon(UiConsts.ICON_SETTING);

            UploadHistoryPanel.initTableData();
            UploadHistoryPanel.tableFrom.validate();


            App.mainPanelCenter.removeAll();
            App.mainPanelCenter.add(App.uploadHistoryPanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });

        buttonSetting.addActionListener(e -> {

            buttonStatus.setIcon(UiConsts.ICON_STATUS);
            buttonDatabase.setIcon(UiConsts.ICON_DATABASE);
            buttonBackup.setIcon(UiConsts.ICON_BACKUP);
            buttonSetting.setIcon(UiConsts.ICON_SETTING_ENABLE);

            App.mainPanelCenter.removeAll();
            SettingPanelOption.setCurrentOption();
            App.mainPanelCenter.add(App.settingPanel, BorderLayout.CENTER);

            App.mainPanelCenter.updateUI();

        });
    }
}
