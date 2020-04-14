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
            System.out.println(dataSet.getString(Tag.PatientName));
            System.out.println(dataSet.getString(Tag.SpecificCharacterSet));
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        DcmReader.readDcmFile("/home/burgess/pacs/dicom2/201803504/1.2.840.113619.2.278.3.839569194.406.1566954975.113/1.2.840.113619.2.80.4203691058.20895.1567046977.1.4.1/1.2.840.113619.2.80.4203691058.20895.1567046977.2.dcm");
//        DcmReader.readDcmFile("/home/burgess/pacs/dicom2/3914A955.dcm");
    }

}
