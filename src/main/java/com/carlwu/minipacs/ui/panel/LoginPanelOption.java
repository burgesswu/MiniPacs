package com.carlwu.minipacs.ui.panel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.carlwu.minipacs.tools.CheckDcmJob;
import com.carlwu.minipacs.tools.PropertyUtil;
import com.carlwu.minipacs.tools.QuartzUtil;
import com.carlwu.minipacs.tools.UploadDcmJob;
import com.carlwu.minipacs.App;
import com.carlwu.minipacs.PACSServer;
import com.carlwu.minipacs.tools.*;
import com.carlwu.minipacs.ui.UiConsts;
import com.carlwu.minipacs.ui.component.MyIconButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 高级选项面板
 *
 * @author Bob
 */
public class LoginPanelOption extends JPanel {

    private static final long serialVersionUID = 1L;

    private static MyIconButton exitButton, loginButton;

    private static JCheckBox checkBoxDebug;

    private static JTextField userNameTextField, passwordTextField;

    private static final Logger logger = LoggerFactory.getLogger(LoginPanelOption.class);

    /**
     * 构造
     */
    public LoginPanelOption() {
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
        panelGridOption.setLayout(new FlowLayout(FlowLayout.CENTER, UiConsts.MAIN_H_GAP, 0));

        // 初始化组件

        JPanel panelItem5 = new JPanel(new FlowLayout(FlowLayout.CENTER, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem6 = new JPanel(new FlowLayout(FlowLayout.CENTER, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem7 = new JPanel(new FlowLayout(FlowLayout.CENTER, UiConsts.MAIN_H_GAP, 0));
        JPanel panelItem8 = new JPanel(new FlowLayout(FlowLayout.CENTER, UiConsts.MAIN_H_GAP, 0));


        panelItem5.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem6.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem7.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelItem8.setBackground(UiConsts.MAIN_BACK_COLOR);


        panelItem5.setPreferredSize(UiConsts.PANEL_ITEM_SIZE_CUSTOMER);
        panelItem6.setPreferredSize(UiConsts.PANEL_ITEM_SIZE_CUSTOMER);
        panelItem7.setPreferredSize(UiConsts.PANEL_ITEM_SIZE_CUSTOMER);
        panelItem8.setPreferredSize(UiConsts.PANEL_ITEM_SIZE_CUSTOMER);


        JLabel userNameLabel = new JLabel(PropertyUtil.getProperty("ds.ui.login.username"));
        userNameTextField = new JTextField();
        userNameLabel.setBackground(UiConsts.MAIN_BACK_COLOR);
        userNameLabel.setFont(UiConsts.FONT_RADIO);
        userNameTextField.setFont(UiConsts.FONT_RADIO);
        Dimension udm = new Dimension(334, 40);
        userNameTextField.setPreferredSize(udm);
        panelItem5.add(userNameLabel);
        panelItem5.add(userNameTextField);

        JLabel passLabel = new JLabel(PropertyUtil.getProperty("ds.ui.login.userpass"));
        passwordTextField = new JPasswordField();
        passLabel.setBackground(UiConsts.MAIN_BACK_COLOR);
        passLabel.setFont(UiConsts.FONT_RADIO);
        passwordTextField.setFont(UiConsts.FONT_RADIO);
        Dimension pdm = new Dimension(334, 40);
        passwordTextField.setPreferredSize(pdm);
        panelItem6.add(passLabel);
        panelItem6.add(passwordTextField);
        checkBoxDebug = new JCheckBox(PropertyUtil.getProperty("ds.ui.login.saveMode"));
        checkBoxDebug.setBackground(UiConsts.MAIN_BACK_COLOR);
        checkBoxDebug.setFont(UiConsts.FONT_RADIO);
        panelItem7.add(checkBoxDebug);


//        exitButton = new MyIconButton(UiConsts.ICON_STOP, UiConsts.ICON_STOP_ENABLE,
//                UiConsts.ICON_STOP_DISABLE, "");
//        panelItem8.add(exitButton);

        loginButton = new MyIconButton(UiConsts.ICON_START, UiConsts.ICON_START_ENABLE,
                UiConsts.ICON_START_DISABLE, "登录");
        panelItem8.add(loginButton);

        // 组合元素

        panelGridOption.add(panelItem5);
        panelGridOption.add(panelItem6);
        panelGridOption.add(panelItem7);
        panelGridOption.add(panelItem8);

        panelCenter.add(panelGridOption);
        return panelCenter;
    }


    /**
     * 设置当前combox选项状态
     */
    public static void setCurrentOption() {
        checkBoxDebug.setSelected(Boolean.parseBoolean(ConstantsTools.CONFIGER.getDebugMode()));
        userNameTextField.setText(ConstantsTools.CONFIGER.getUserName());
        passwordTextField.setText(ConstantsTools.CONFIGER.getUserPass());
    }

    /**
     * 为相关组件添加事件监听
     */
    private void addListener() {
        loginButton.addActionListener(e -> {
            try {
                ConstantsTools.CONFIGER.setDebugMode(String.valueOf(checkBoxDebug.isSelected()));

//                ConstantsTools.CONFIGER.setMysqlPath(textField.getText());


                Map dataMap = new HashMap<String, String>();
                String pass = passwordTextField.getText();
                String name = userNameTextField.getText();
                dataMap.put("username", name);
                dataMap.put("password", pass);

                String res = HttpUtil.post(ConstantsTools.CONFIGER.getBaseUrl() + "/login", dataMap, "");
                JSONObject resData = JSON.parseObject(res);
                if (resData.containsKey("data")) {
                    //登录成功加载主页面 {"message":"系统内部异常"}
                    JSONObject userInfo = resData.getJSONObject("data");
                    String token = userInfo.getString("token");
                    ConstantsTools.CONFIGER.setToken(token);
                    ConstantsTools.CONFIGER.setUserName(name);
                    ConstantsTools.CONFIGER.setUserPass(pass);

                    App.mainPanel.add(App.toolbar, BorderLayout.WEST);
                    App.mainPanelCenter.removeAll();
                    App.mainPanelCenter.add(App.uploadPanel, BorderLayout.CENTER);

                    App.mainPanelCenter.updateUI();
                    try {
                        //启动任务
                        QuartzUtil.addJob("job1", "trigger1", CheckDcmJob.class, 20);

                    } catch (Exception e1) {
                        logger.error(e1.getMessage());
                    }
                    try {
                        //启动任务
                        QuartzUtil.addJob("job2", "trigger2", UploadDcmJob.class, 30);

                    } catch (Exception e1) {
                        logger.error(e1.getMessage());
                    }

                    try {
                        //启动pacs服务
                        new PACSServer().run();
                    } catch (Exception e1) {
                        logger.error(e1.getMessage());
                    }

                } else {
                    JOptionPane.showMessageDialog(App.settingPanel, resData.getString("message"),
                            PropertyUtil.getProperty("ds.ui.tips"), JOptionPane.PLAIN_MESSAGE);
                }


            } catch (Exception e1) {
                JOptionPane.showMessageDialog(App.settingPanel, "登录失败",
                        PropertyUtil.getProperty("ds.ui.tips"),
                        JOptionPane.ERROR_MESSAGE);
                logger.error("Write to xml file error" + e1.toString());
            }

        });


    }
}
