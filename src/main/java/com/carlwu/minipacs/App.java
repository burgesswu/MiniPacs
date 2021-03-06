package com.carlwu.minipacs;

import com.carlwu.minipacs.tools.ConstantsTools;
import com.carlwu.minipacs.tools.sqlite.ResultSetExtractor;
import com.carlwu.minipacs.tools.sqlite.SqliteHelper;
import com.carlwu.minipacs.ui.UiConsts;
import com.carlwu.minipacs.ui.dialog.DbBackUpCreateDialog;
import com.carlwu.minipacs.ui.dialog.MulUploadDialog;
import com.carlwu.minipacs.ui.panel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.carlwu.minipacs.tools.PropertyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.ResultSet;

/**
 * 程序入口，主窗口Frame
 *
 * @author Bob
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private JFrame frame;

    public static JPanel mainPanelCenter, mainPanel;
    public static ToolBarPanel toolbar;

    public static StatusPanel statusPanel;
    public static DatabasePanel databasePanel;
    public static UploadHistoryPanel uploadHistoryPanel;
    public static SettingPanel settingPanel;
    public static LoginPanel loginPanel;
    public static UploadPanel uploadPanel;
    /**
     * 新建备份dialog
     */
    public static DbBackUpCreateDialog dbBackUpCreateDialog;
    public static MulUploadDialog mulUploadDialog;

    /**
     * 程序入口main
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {

                App window = new App();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private static void initDb() {

        //初始化数据库
        try {
            String dbDirectory = ConstantsTools.CONFIGER.getSqliteDB();
            File fileDir = new File(dbDirectory);

            if(!fileDir.getParentFile().exists()){
                fileDir.getParentFile().mkdirs();
            }
            SqliteHelper dbHelper = new SqliteHelper(dbDirectory);
            String sql = "select count(*) tableCount from sqlite_master where type='table' and name = 'files_log';";
            long count=dbHelper.executeQuery(sql, (ResultSetExtractor<Long>) rs -> rs.getLong("tableCount"));
            if(count==0){
                //创建表
                sql="CREATE TABLE \"files_log\" (\n" +
                        "  \"id\" integer NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                        "  \"patient_name\" text(255),\n" +
                        "  \"age\" text(50),\n" +
                        "  \"uid\" text(120),\n" +
                        "  \"file_count\" integer(255),\n" +
                        "  \"file_size\" real,\n" +
                        "  \"upload_status\" integer(2),\n" +
                        "  \"study_no\" text(255),\n" +
                        "  \"start_time\" text,\n" +
                        "  \"end_time\" text\n" +
                        ");";
                dbHelper.executeUpdate(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 构造，创建APP
     */
    public App() {
        initialize();
        StatusPanel.buttonStartSchedule.doClick();
    }

    /**
     * 初始化frame内容
     */
    private void initialize() {
        logger.info("==================AppInitStart");
        // 设置系统默认样式
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 初始化主窗口
        frame = new JFrame();
        frame.setBounds(UiConsts.MAIN_WINDOW_X, UiConsts.MAIN_WINDOW_Y, UiConsts.MAIN_WINDOW_WIDTH,
                UiConsts.MAIN_WINDOW_HEIGHT);
        frame.setTitle(UiConsts.APP_NAME);
        frame.setIconImage(UiConsts.IMAGE_ICON);
        frame.setBackground(UiConsts.MAIN_BACK_COLOR);
        mainPanel = new JPanel(true);
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new BorderLayout());

        toolbar = new ToolBarPanel();
        statusPanel = new StatusPanel();
        databasePanel = new DatabasePanel();
        uploadHistoryPanel = new UploadHistoryPanel();
        uploadPanel = new UploadPanel();
        settingPanel = new SettingPanel();
        loginPanel = new LoginPanel();

//        mainPanel.add(toolbar, BorderLayout.WEST);   //登录成功后加载

        mainPanelCenter = new JPanel(true);
        mainPanelCenter.setLayout(new BorderLayout());
        mainPanelCenter.add(loginPanel, BorderLayout.CENTER);

        mainPanel.add(mainPanelCenter, BorderLayout.CENTER);

        // 添加数据库备份对话框
        addDialog();
        initDb();

        frame.add(mainPanel);

        frame.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (!StatusPanel.buttonStartSchedule.isEnabled()) {
                    JOptionPane.showMessageDialog(App.statusPanel,
                            PropertyUtil.getProperty("ds.ui.mainwindow.exitconfirm"), "Sorry~", JOptionPane.WARNING_MESSAGE);
                } else {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        logger.info("==================AppInitEnd");
    }

    /**
     * 数据库备份对话框
     */
    private void addDialog() {
//        // 数据库备份对话框
//        dbBackUpCreateDialog = new DbBackUpCreateDialog(frame, PropertyUtil.getProperty("ds.ui.mainwindow.dialog.newBackUp"), true);
//        dbBackUpCreateDialog.init();
        mulUploadDialog = new MulUploadDialog(frame, "选择文件", true);
        mulUploadDialog.init();
    }

}