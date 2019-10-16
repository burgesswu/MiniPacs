package com.carlwu.minipacs.tools;

import com.alibaba.fastjson.JSON;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;

import java.io.File;
import java.io.IOException;

public class DcmReader {

    public static Attributes readDcmFile(String filePath) {
        try {
            DicomInputStream dis = new DicomInputStream(new File(filePath));
            Attributes dataSet = dis.readDataset(-1, Tag.PixelData);
            System.out.println(dataSet.getString(Tag.PatientID));
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        DcmReader.readDcmFile("/home/burgess/dicom/lhf/154974990/AT000001.1145 122.dcm");
    }

}
