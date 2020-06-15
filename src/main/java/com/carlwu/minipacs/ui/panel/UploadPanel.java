package com.carlwu.minipacs.ui.panel;

import com.carlwu.minipacs.tools.FileUtils;
import com.carlwu.minipacs.tools.PropertyUtil;
import com.carlwu.minipacs.App;
import com.carlwu.minipacs.tools.ConstantsTools;
import com.carlwu.minipacs.ui.UiConsts;
import com.carlwu.minipacs.ui.component.MyIconButton;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 备份面板
 *
 * @author Bob
 */
@Slf4j
public class UploadPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public static JTable tableFrom;

    private static JButton buttonNewBakFrom;

    private static Object[][] tableDatas;

    private static    // 初始化表格组件
            String[] header = new String[]{PropertyUtil.getProperty("ds.ui.backup.table.head0"), PropertyUtil.getProperty("ds.ui.backup.table.head1"), PropertyUtil.getProperty("ds.ui.backup.table.head2"), PropertyUtil.getProperty("ds.ui.backup.table.head3"), PropertyUtil.getProperty("ds.ui.backup.table.head4"), PropertyUtil.getProperty("ds.ui.backup.table.head5"), PropertyUtil.getProperty("ds.ui.backup.table.head6")};

    /**
     * 构造
     */
    public UploadPanel() {
        initialize();
        initTableData();
        addComponent();
        addListener();
    }

    /**
     * 初始化面板
     */
    private void initialize() {
        this.setBackground(UiConsts.MAIN_BACK_COLOR);
        this.setLayout(new BorderLayout());
    }

    /**
     * 为面板添加组件
     */
    private void addComponent() {

//        this.add(getUpPanel(), BorderLayout.NORTH);
        this.add(getCenterPanel(), BorderLayout.CENTER);
    }

    /**
     * 面板上部
     *
     * @return
     */
    private JPanel getUpPanel() {
        JPanel panelUp = new JPanel();
        panelUp.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelUp.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));

        JLabel labelTitle = new JLabel(PropertyUtil.getProperty("ds.ui.backup.title"));
        labelTitle.setFont(UiConsts.FONT_TITLE);
        labelTitle.setForeground(UiConsts.TOOL_BAR_BACK_COLOR);
        panelUp.add(labelTitle);

        return panelUp;
    }

    /**
     * 面板中部
     *
     * @return
     */
    private JPanel getCenterPanel() {
        // 中间面板
        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelCenter.setLayout(new GridLayout(1, 1));

        panelCenter.add(getPanelGridBakFrom());

        return panelCenter;
    }

    /**
     * 数据库来源Grid面板
     *
     * @return
     */
    private JPanel getPanelGridBakFrom() {
        JPanel panelGridBakFrom = new JPanel();
        panelGridBakFrom.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridBakFrom.setLayout(new BorderLayout());

        JPanel panelFromControl = new JPanel();
        panelFromControl.setLayout(new GridLayout(1, 2));
        JPanel panelFromTable = new JPanel();
        panelFromTable.setLayout(new BorderLayout());

        // 初始化控制组件
        JPanel panelFromControlLeft = new JPanel();
        panelFromControlLeft.setLayout(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 5));
        panelFromControlLeft.setBackground(UiConsts.MAIN_BACK_COLOR);
        JPanel panelFromControlRight = new JPanel();
        panelFromControlRight.setLayout(new FlowLayout(FlowLayout.RIGHT, UiConsts.MAIN_H_GAP, 5));
        panelFromControlRight.setBackground(UiConsts.MAIN_BACK_COLOR);

        JLabel labelFrom = new JLabel("上传列表");
        labelFrom.setFont(new Font(PropertyUtil.getProperty("ds.ui.font.family"), 0, 18));
        labelFrom.setForeground(Color.gray);
        panelFromControlLeft.add(labelFrom);

        buttonNewBakFrom = new MyIconButton(UiConsts.ICON_MU_UPLOAD,UiConsts.ICON_MU_UPLOAD,UiConsts.ICON_MU_UPLOAD,"手动上传");
        buttonNewBakFrom.setRolloverEnabled(true);

        panelFromControlRight.add(buttonNewBakFrom);

        panelFromControl.add(panelFromControlLeft);
        panelFromControl.add(panelFromControlRight);

        panelGridBakFrom.add(panelFromControl, BorderLayout.NORTH);


        tableFrom = new JTable(new DefaultTableModel(tableDatas, header));
        tableFrom.setFont(UiConsts.FONT_NORMAL);
        tableFrom.getTableHeader().setFont(UiConsts.FONT_NORMAL);
        tableFrom.getTableHeader().setBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        tableFrom.setRowHeight(31);
        tableFrom.setGridColor(UiConsts.TABLE_LINE_COLOR);
        tableFrom.setSelectionBackground(UiConsts.TOOL_BAR_BACK_COLOR);
        // 设置列宽
        tableFrom.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableFrom.getColumnModel().getColumn(0).setMaxWidth(50);
        tableFrom.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableFrom.getColumnModel().getColumn(2).setMaxWidth(150);

        JScrollPane panelScroll = new JScrollPane(tableFrom);
        panelScroll.setBackground(UiConsts.MAIN_BACK_COLOR);
        panelGridBakFrom.add(panelScroll, BorderLayout.CENTER);

        return panelGridBakFrom;
    }

    private static void getTableData() {
        log.info("重新获取数据");
        String path = ConstantsTools.CONFIGER.getLocalDicomPath();//dicom 文件地址
        java.util.List<String> files = FileUtils.getDcmDir(path);
        tableDatas = new Object[files.size()][7];
        for (int i = 0; i < files.size(); i++) {
            String fileName = files.get(i);
            String[] arr = fileName.split("_");
            String filePath = path + "/" + fileName;
            File currFile = new File(filePath);
            String status = "数据接收待压缩";
            File zipFile = new File(filePath + ".zip");
            if (zipFile.exists()) {
                if ((LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) - (zipFile.lastModified() / 1000)) > (60 * 3)) {
                    status = "压缩完成待上传";
                } else {
                    status = "文件压缩中";
                }

            }
            long fileCount = FileUtils.getlist(currFile);
            tableDatas[i] = new Object[]{i + 1, arr[0], arr[2], arr[1], fileCount, org.apache.commons.io.FileUtils.sizeOfDirectory(currFile), status};
        }

    }

    public static void initTableData() {

        int delay = 10000;//milliseconds
        getTableData();

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                getTableData();
                ((DefaultTableModel) tableFrom.getModel()).getDataVector().clear();
                tableFrom.setModel(new DefaultTableModel(tableDatas, header));
                tableFrom.updateUI();
            }

        };

        new Timer(delay, taskPerformer).start();

    }

    private void addListener() {
        buttonNewBakFrom.addActionListener(e -> App.mulUploadDialog.setVisible(true));
    }

}