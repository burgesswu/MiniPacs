package com.carlwu.minipacs.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.carlwu.minipacs.PACSClient;
import lombok.extern.slf4j.Slf4j;
import org.dcm4che3.tool.storescu.StoreSCU;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * 上传任务
 */
@Slf4j
public class UploadDcmJob implements Job {
    public static HashMap<String, Object> uploadQueue = new HashMap<>();


    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            log.info("文件上传任务检查....");
            String path = ConstantsTools.CONFIGER.getLocalDicomPath();//dicom 文件地址
            List<String> files = FileUtils.getDcmDir(path);
            for (String fName : files) {
                String filePath = path + "/" + fName;
                String[] fNameArr = fName.split("_");
                File dirFile = new File(filePath + ".zip");
                String fileKey = MD5Util.getMD5(fName);
                if ("ZIP".equalsIgnoreCase(ConstantsTools.CONFIGER.getUploadMode())) {
                    File zipFile = new File(filePath + ".zip");
                    if (zipFile.exists()) {
                        long lastTime = (zipFile.lastModified() / 1000);
                        long currTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                        if ((currTime - lastTime) > (60 * 5)) {
                            if (!uploadQueue.containsKey(fileKey)) {
                                Map<String, Object> storeMap = new HashMap<>();
                                storeMap.put("filePath", filePath);
                                uploadQueue.put(fileKey, storeMap);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        log.info("上传" + filePath + ".zip");
                                        startNoticeToServiceSys(fName);
                                        String dst = "/home/dcm/"; // 目标文件名
                                        Map<String, String> sftpDetails = new HashMap<String, String>();
                                        sftpDetails.put(SftpUtil.SFTP_REQ_HOST, ConstantsTools.CONFIGER.getRemotePacsUrl());
                                        sftpDetails.put(SftpUtil.SFTP_REQ_USERNAME, "root");
                                        sftpDetails.put(SftpUtil.SFTP_REQ_PASSWORD, "Louxia789");
                                        sftpDetails.put(SftpUtil.SFTP_REQ_PORT, "22");
                                        try {
                                            SftpUtil.uploadFile(zipFile.getAbsolutePath(), dst, sftpDetails);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }).start();
                            } else {
                                System.out.println("已经在上传列表中");
                            }


                        }
                    }
                } else {
                    //直接上传
                    long lastTime = (dirFile.lastModified() / 1000);
                    long currTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                    if ((currTime - lastTime) > (60 * 5)) {
                        if (!uploadQueue.containsKey(fileKey)) {
                            String onDcmPath = FileUtils.getFilesOfOne(filePath);
                            //读取文件信息
                            Map<String, Object> storeMap = new HashMap<>();
                            storeMap.put("filePath", filePath);
                            uploadQueue.put(fileKey, storeMap);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        log.info("开始上传.....");
                                        startNoticeToServiceSys(fName);
                                        String url = PACSClient.class.getClassLoader().getResource("rel-sop-classes.properties").getPath();
                                        StoreSCU.main(new String[]{"storescu", "-c", ConstantsTools.CONFIGER.getRemotePacsAeTitle() + "@" + ConstantsTools.CONFIGER.getRemotePacsUrl() + ":" + ConstantsTools.CONFIGER.getRemotePacsPort(), "--rel-sop-classes", url, filePath});
                                        log.info("上传完成.....");
                                        updateNoticeToServiceSys(fName);
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }).start();
                        } else {
                            System.out.println("已经在上传列表中");
                        }
                    }
                }


            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void startNoticeToServiceSys(String fName) {
        String[] arr = fName.split("_");
        Map dataMap = new HashMap<String, String>();
        dataMap.put("dicomSopuid", arr[1]);
        dataMap.put("type", "CT");
        dataMap.put("dicomPatientName", arr[2]);
        dataMap.put("dicomSex", ("F".equals("F") ? 2 : 1) + "");
        String res = HttpUtil.post(ConstantsTools.CONFIGER.getBaseUrl() + "/client/imageology-dicom", dataMap, ConstantsTools.CONFIGER.getToken());
        log.info(res);
        JSONObject resData = JSON.parseObject(res);
    }



    private void updateNoticeToServiceSys(String fName) {
        //修改状态
        String[] arr = fName.split("_");
        Map updateDataMap = new HashMap<String, String>();
        updateDataMap.put("dicomSopuid", arr[1]);
        String updateRes = HttpUtil.put(ConstantsTools.CONFIGER.getBaseUrl() + "/client/imageology-dicom", updateDataMap, ConstantsTools.CONFIGER.getToken());
        log.info("---------------------状态更新完成------------------");
        log.info(updateRes);
        JSONObject updateResData = JSON.parseObject(updateRes);

    }


}
