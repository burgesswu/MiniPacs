package com.carlwu.minipacs.tools;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
public class CheckDcmJob implements Job {


    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        try {
            String path = ConstantsTools.CONFIGER.getLocalDicomPath();//dicom 文件地址
            List<String> files = FileUtils.getDcmDir(path);
            for (String fName : files) {
                System.out.println(fName);
                String filePath = path + "/" + fName;
                File file = new File(path + "/" + fName);
                long lastTime = (file.lastModified() / 1000);
                long currTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                if ((currTime - lastTime) > (60 * 5)) {
                    if ("ZIP".equalsIgnoreCase(ConstantsTools.CONFIGER.getUploadMode())) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    //判断文件是否存在
                                    File zipFile = new File(filePath + ".zip");
                                    if (!zipFile.exists()) {
                                        ZipUtils.toZip(filePath, new FileOutputStream(zipFile), true);
                                    }

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
