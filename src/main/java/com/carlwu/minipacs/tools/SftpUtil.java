package com.carlwu.minipacs.tools;

/**
 * This program will demonstrate the sftp protocol support.
 * $ CLASSPATH=.:../build javac Sftp.java
 * $ CLASSPATH=.:../build java Sftp
 * You will be asked username, host and passwd.
 * If everything works fine, you will get a prompt 'sftp>'.
 * 'help' command will show available command.
 * In current implementation, the destination path for 'get' and 'put'
 * commands must be a file, not a directory.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.ProgressMonitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpProgressMonitor;

import static java.lang.Thread.sleep;

/**
 * <pre>
 *  ----------命令集合---------------------
 *  可参考链接(官方示例程序)：http://www.jcraft.com/jsch/examples/Sftp.java
 *  ChannelSftp c = (ChannelSftp) channel;
 *  c.quit();
 *  c.exit();
 *  c.cd("/home/example");
 *  c.lcd("/home/example");
 *  c.rm("/home/example.gz");
 *  c.rmdir("/home/example");
 *  c.mkdir("/home/example");
 *  c.chgrp(777, "/home/example");
 *  c.chown(777, "/home/example");
 *  c.chmod(777, "/home/example");
 *  c.pwd();
 *  c.lpwd();
 *  c.ls("/home/example");
 *
 *  SftpProgressMonitor monitor = new MyProgressMonitor(); //显示进度
 *  //文件下载
 *  c.get("srcPath", "dstPath", monitor, ChannelSftp.OVERWRITE);
 *  c.get("srcPath", "dstPath", monitor, ChannelSftp.RESUME); //断点续传
 *  c.get("srcPath", "dstPath", monitor, ChannelSftp.APPEND);
 *  //文件上传
 *  c.put("srcPath", "dstPath", monitor, ChannelSftp.APPEND);
 *  c.put("srcPath", "dstPath", monitor, ChannelSftp.APPEND);
 *  c.put("srcPath", "dstPath", monitor, ChannelSftp.APPEND);
 *
 *  c.hardlink("oldPath", "newPath");
 *  c.rename("oldPath", "newPath");
 *  c.symlink("oldPath", "newPath");
 *  c.readlink("Path");
 *  c.realpath("Path");
 *  c.version();
 *
 *  SftpStatVFS stat = c.statVFS("path");  //df 命令
 *  long size = stat.getSize();
 *  long used = stat.getUsed();
 *  long avail = stat.getAvailForNonRoot();
 *  long root_avail = stat.getAvail();
 *  long capacity = stat.getCapacity();
 *
 *  c.stat("path");
 *  c.lstat("path");
 * ----------------------------------------------------------------------
 * </pre>
 */
public class SftpUtil {
    Session session = null;
    Channel channel = null;
    public static final String SFTP_REQ_HOST = "host";
    public static final String SFTP_REQ_PORT = "port";
    public static final String SFTP_REQ_USERNAME = "username";
    public static final String SFTP_REQ_PASSWORD = "password";
    public static final int SFTP_DEFAULT_PORT = 22;
    public static final String SFTP_REQ_LOC = "location";

    /**
     * 测试程序
     *
     * @param arg
     * @throws Exception
     */
    public static void main(String[] arg) throws Exception {
        // 设置主机ip，端口，用户名，密码
        Map<String, String> sftpDetails = new HashMap<String, String>();
        sftpDetails.put(SFTP_REQ_HOST, "139.9.157.109");
        sftpDetails.put(SFTP_REQ_USERNAME, "root");
        sftpDetails.put(SFTP_REQ_PASSWORD, "Louxia789");
        sftpDetails.put(SFTP_REQ_PORT, "22");

        //测试文件上传
        String src = "/home/burgess/dicom/lhf.zip"; // 本地文件名
        String dst = "/home/dcm/"; // 目标文件名
        uploadFile(src, dst, sftpDetails);

    }


    public static void uploadFile(String src, String dst,
                                  Map<String, String> sftpDetails) throws Exception {
        SftpUtil sftpUtil = new SftpUtil();
        ChannelSftp chSftp = sftpUtil.getChannel(sftpDetails, 60000);
        /**
         * 代码段1/代码段2/代码段3分别演示了如何使用JSch的不同的put方法来进行文件上传。这三段代码实现的功能是一样的，
         * 都是将本地的文件src上传到了服务器的dst文件
         */
        /**代码段1
         OutputStream out = chSftp.put(dst,new MyProgressMonitor2(), ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
         byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
         int read;
         if (out != null) {
         InputStream is = new FileInputStream(src);
         do {
         read = is.read(buff, 0, buff.length);
         if (read > 0) {
         out.write(buff, 0, read);
         }
         out.flush();
         } while (read >= 0);
         }
         **/

        // 使用这个方法时，dst可以是目录，当dst是目录时，上传后的目标文件名将与src文件名相同
        // ChannelSftp.RESUME：断点续传
        chSftp.put(src, dst, new MyProgressMonitor(), ChannelSftp.RESUME); // 代码段2
        // 将本地文件名为src的文件输入流上传到目标服务器，目标文件名为dst。
        // chSftp.put(new FileInputStream(src), dst,new MyProgressMonitor2(), ChannelSftp.OVERWRITE); // 代码段3

        chSftp.quit();
        sftpUtil.closeChannel();
    }

    /**
     * 根据ip，用户名及密码得到一个SFTP
     * channel对象，即ChannelSftp的实例对象，在应用程序中就可以使用该对象来调用SFTP的各种操作方法
     *
     * @param sftpDetails
     * @param timeout
     * @return
     * @throws JSchException
     */
    public ChannelSftp getChannel(Map<String, String> sftpDetails, int timeout)
            throws JSchException {
        String ftpHost = sftpDetails.get(SFTP_REQ_HOST);
        String port = sftpDetails.get(SFTP_REQ_PORT);
        String ftpUserName = sftpDetails.get(SFTP_REQ_USERNAME);
        String ftpPassword = sftpDetails.get(SFTP_REQ_PASSWORD);
        int ftpPort = SFTP_DEFAULT_PORT;
        if (port != null && !port.equals("")) {
            ftpPort = Integer.valueOf(port);
        }
        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(5000); // 通过Session建立链接
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        return (ChannelSftp) channel;
    }

    public void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    /**
     * 进度监控器-JSch每次传输一个数据块，就会调用count方法来实现主动进度通知
     */
    public static class MyProgressMonitor implements SftpProgressMonitor {
        private long count = 0;     //当前接收的总字节数
        private long max = 0;       //最终文件大小
        private long percent = -1;  //进度
        private String dicomSopuid = "";

        /**
         * 当每次传输了一个数据块后，调用count方法，count方法的参数为这一次传输的数据块大小
         */
        @Override
        public boolean count(long count) {
            this.count += count;
            if (percent >= this.count * 100 / max) {
                return true;
            }
            percent = this.count * 100 / max;
            System.out.println("Completed " + this.count + "(" + percent
                    + "%) out of " + max + ".");
            return true;
        }

        /**
         * 当传输结束时，调用end方法
         */
        @Override
        public void end() {
            System.out.println("Transferring done.");
            //修改状态
            Map updateDataMap = new HashMap<String, String>();
            updateDataMap.put("dicomSopuid", dicomSopuid);
            String updateRes = HttpUtil.put(ConstantsTools.CONFIGER.getBaseUrl() + "/client/imageology-dicom", updateDataMap, ConstantsTools.CONFIGER.getToken());
            JSONObject updateResData = JSON.parseObject(updateRes);


        }

        /**
         * 当文件开始传输时，调用init方法
         */
        @Override
        public void init(int op, String src, String dest, long max) {
            System.out.println("Transferring begin.");
            System.out.println("开始上传文件：" + src + "至远程：" + dest + "文件总大小:" + max / 1024 + "KB");
            String[] arr = src.split("_");
            this.dicomSopuid = arr[1];
            this.max = max;
            this.count = 0;
            this.percent = -1;
        }
    }

    /**
     * 官方提供的进度监控器
     */
    public static class DemoProgressMonitor implements SftpProgressMonitor {
        ProgressMonitor monitor;
        long count = 0;
        long max = 0;

        /**
         * 当文件开始传输时，调用init方法。
         */
        public void init(int op, String src, String dest, long max) {
            this.max = max;
            monitor = new ProgressMonitor(null,
                    ((op == SftpProgressMonitor.PUT) ? "put" : "get") + ": "
                            + src, "", 0, (int) max);
            count = 0;
            percent = -1;
            monitor.setProgress((int) this.count);
            monitor.setMillisToDecideToPopup(1000);
        }

        private long percent = -1;

        /**
         * 当每次传输了一个数据块后，调用count方法，count方法的参数为这一次传输的数据块大小。
         */
        public boolean count(long count) {
            this.count += count;
            if (percent >= this.count * 100 / max) {
                return true;
            }
            percent = this.count * 100 / max;
            monitor.setNote("Completed " + this.count + "(" + percent
                    + "%) out of " + max + ".");
            monitor.setProgress((int) this.count);
            return !(monitor.isCanceled());
        }

        /**
         * 当传输结束时，调用end方法。
         */
        public void end() {
            monitor.close();
        }
    }
}

