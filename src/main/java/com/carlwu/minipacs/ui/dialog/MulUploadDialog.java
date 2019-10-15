package com.carlwu.minipacs.ui.dialog;

import com.carlwu.minipacs.tools.PropertyUtil;
import com.carlwu.minipacs.tools.ConstantsTools;
import com.carlwu.minipacs.ui.UiConsts;
import lombok.extern.slf4j.Slf4j;
import org.dcm4che3.tool.storescu.StoreSCU;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.InetAddress;

/**
 * <pre>
 * 新建备份dialog
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">Zhou Bo</a>
 * @since 2019/2/27.
 */
@Slf4j
public class MulUploadDialog extends JDialog {
    public MulUploadDialog(JFrame frame, String property, boolean b) {
        super(frame, property, b);
    }

    private JTextField textFieldComment;

    public void init() {
        this.setBounds(460, 220, 400, 250);
        JPanel panelDialog = new JPanel(new BorderLayout());
        panelDialog.setBackground(UiConsts.MAIN_BACK_COLOR);
        JPanel panelDialogCenter = new JPanel(new FlowLayout(FlowLayout.LEFT, UiConsts.MAIN_H_GAP, 10));
        JPanel panelDialogDown = new JPanel(new GridLayout(1, 2));
        JPanel grid1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        JPanel grid2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));

        JLabel labelComment = new JLabel("路径:");
        JLabel labelProgress = new JLabel(PropertyUtil.getProperty("ds.ui.mainwindow.dialog.progress"));

        textFieldComment = new JTextField();
        JProgressBar progressbar = new JProgressBar();
        JButton selectBtn = new JButton("...");

        labelComment.setFont(UiConsts.FONT_NORMAL);
        labelProgress.setFont(UiConsts.FONT_NORMAL);
        textFieldComment.setFont(UiConsts.FONT_NORMAL);

        Dimension preferredSize = new Dimension(250, 30);
        textFieldComment.setPreferredSize(preferredSize);
        progressbar.setPreferredSize(preferredSize);

        panelDialogCenter.add(labelComment);
        panelDialogCenter.add(textFieldComment);
        panelDialogCenter.add(selectBtn);
        panelDialogCenter.add(labelProgress);
        panelDialogCenter.add(progressbar);

        JButton buttonSure = new JButton(PropertyUtil.getProperty("ds.ui.sure"));
        JButton buttonCancel = new JButton(PropertyUtil.getProperty("ds.ui.cancel"));
        buttonSure.setFont(UiConsts.FONT_NORMAL);
        buttonCancel.setFont(UiConsts.FONT_NORMAL);

        grid1.add(buttonSure);
        grid2.add(buttonCancel);
        panelDialogDown.add(grid1);
        panelDialogDown.add(grid2);

        panelDialog.add(panelDialogCenter, BorderLayout.CENTER);
        panelDialog.add(panelDialogDown, BorderLayout.SOUTH);

        this.add(panelDialog);

        buttonCancel.addActionListener(e -> this.setVisible(false));
        selectBtn.addActionListener(e -> {
            JFileChooser jf = new JFileChooser();
            jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);
            jf.setCurrentDirectory(new File("D:\\"));//设置默认路径
            jf.setDialogTitle("选择目标文件夹");
            if (jf.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File fi = jf.getSelectedFile();
                jf.setCurrentDirectory(fi);//设置默认路径
                textFieldComment.setText(fi.getAbsolutePath());
            }

        });
        buttonSure.addActionListener(e -> {
            try {
                String url = MulUploadDialog.class.getClassLoader().getResource("rel-sop-classes.properties").getPath();
                String directory = textFieldComment.getText();
                InetAddress addr = InetAddress.getLocalHost();
                StoreSCU.main(new String[]{"storescu", "-c", ConstantsTools.CONFIGER.getLocalPacsAeTitle() + "@" + addr.getHostAddress() + ":" + ConstantsTools.CONFIGER.getLocalPacsPort(), "--rel-sop-classes", url, directory});
            } catch (Exception e1) {
//                System.out.println(e1.getMessage());
                log.error("删除错误");
            }
        });
    }
}
