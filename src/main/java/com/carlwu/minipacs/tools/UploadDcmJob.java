package com.carlwu.minipacs.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.carlwu.minipacs.PACSClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.tool.storescu.StoreSCU;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                //读取文件信息
                String file_str = FileUtils.getFilesOfOne(filePath);
                Attributes dataSet = DcmReader.readDcmFile(file_str);
                if (dataSet != null) {
                    String modality = dataSet.getString(Tag.Modality);


                }
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
                                storeMap.put("data", dataSet);
                                uploadQueue.put(fileKey, storeMap);
                                new Thread(new Runnable() {
                                    @SneakyThrows
                                    @Override
                                    public void run() {
                                        log.info("上传" + filePath + ".zip");
                                        startNoticeToServiceSys(dataSet);
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
                        try {
                            addInLocalDb(dataSet);
                        }catch (Exception e){
                            log.error(e.getMessage());
                        }

                        if (!uploadQueue.containsKey(fileKey)) {
                            String onDcmPath = FileUtils.getFilesOfOne(filePath);
                            //读取文件信息
                            Map<String, Object> storeMap = new HashMap<>();
                            storeMap.put("filePath", filePath);
                            storeMap.put("data", dataSet);
                            uploadQueue.put(fileKey, storeMap);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        log.info("开始上传.....");
                                        startNoticeToServiceSys(dataSet);
                                        String root = System.getProperty("user.dir");
                                        String url = root + ("/config/rel-sop-classes.properties");
                                        StoreSCU.main(new String[]{"storescu", "-c", ConstantsTools.CONFIGER.getRemotePacsAeTitle() + "@" + ConstantsTools.CONFIGER.getRemotePacsUrl() + ":" + ConstantsTools.CONFIGER.getRemotePacsPort(), "--rel-sop-classes", url, filePath});
                                        log.info("上传完成......");
                                        updateNoticeToServiceSys(dataSet, 1);
                                        updateLocalDb(dataSet);
                                        log.info("删除本地文件");
                                        FileUtils.delFile(new File(filePath));

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

    private void startNoticeToServiceSys(Attributes dataset) throws IOException {
        Map dataMap = new HashMap<String, String>();
        dataMap.put("dicomSopuid", dataset.getString(Tag.StudyInstanceUID));
        dataMap.put("type", dataset.getString(Tag.Modality));
        dataMap.put("dicomPatientName", new String(dataset.getBytes(Tag.PatientName),"GB18030"));
        dataMap.put("dicomSex", (dataset.getString(Tag.PatientSex).equals("F") ? 2 : 1) + "");
        String res = HttpUtil.post(ConstantsTools.CONFIGER.getBaseUrl() + "/client/imageology-dicom", dataMap, ConstantsTools.CONFIGER.getToken());
        log.info(res);
        if (StringUtils.isNotBlank(res)) {
            JSONObject rjson = JSON.parseObject(res);
            if (rjson.getString("message").indexOf("重复") > -1) {
                updateNoticeToServiceSys(dataset, 0);
            }
        }

    }


    private void addInLocalDb(Attributes dataset) throws SQLException, IOException {
        DbUtilMySQL instance = DbUtilMySQL.getInstance();
        String sql = "INSERT INTO `files_log` (`patient_name`, `age`, `uid`, `file_count`, `file_size`, `upload_status`, `study_no`, `start_time`) VALUES ('" + new String(dataset.getBytes(Tag.PatientName),"GB18030" )+ "', '" + dataset.getString(Tag.PatientAge) + "', '" + dataset.getString(Tag.StudyInstanceUID) + "', '1', '1', '1', '" + dataset.getString(Tag.PatientID) + "', '" + LocalDateTime.now() + "')";
        System.out.println(sql);
        instance.executeUpdate(sql);
        instance.getConnection().commit();
    }


    private void updateLocalDb(Attributes dataset) throws SQLException {
        try {
            DbUtilMySQL instance = DbUtilMySQL.getInstance();
            String sql = "UPDATE `files_log` SET end_time='" + LocalDateTime.now() + "' ,upload_status=3  WHERE uid='" + dataset.getString(Tag.StudyInstanceUID) + "'";
            instance.executeUpdate(sql);
            instance.getConnection().commit();
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

    private void updateNoticeToServiceSys(Attributes dataset, Integer uploadOk) {
        //修改状态
        Map updateDataMap = new HashMap<String, String>();
        String StudyInstanceUID=dataset.getString(Tag.StudyInstanceUID);
        log.info(StudyInstanceUID);
        updateDataMap.put("dicomSopuid",StudyInstanceUID );
        updateDataMap.put("uploadOk", uploadOk + "");
        String updateRes = HttpUtil.put(ConstantsTools.CONFIGER.getBaseUrl() + "/client/imageology-dicom", updateDataMap, ConstantsTools.CONFIGER.getToken());
        log.info("---------------------状态更新完成------------------");


    }


}
