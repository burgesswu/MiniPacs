package com.carlwu.minipacs.tools;

import com.alibaba.fastjson.JSON;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;

import java.io.File;
import java.io.IOException;

public class DcmReader {
    public static Attributes readDcmFile(String filePath) {
        DicomInputStream dis = null;
        try {
            dis = new DicomInputStream(new File(filePath));
            Attributes dataSet = dis.readDataset(-1, Tag.PixelData);
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (dis != null)
                    dis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (dis != null)
                    dis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


    public static Attributes readDcmFile(String filePath, String newFilePath) {
        try {
            DicomInputStream dis = new DicomInputStream(new File(filePath));
            Attributes dataSet = dis.readDataset(-1, Tag.PixelData);
            System.out.println(new String(dataSet.getBytes(Tag.PatientName), "GB18030"));
            System.out.println(new String(dataSet.getBytes(Tag.BodyPartExamined), "GB18030"));
            System.out.println(new String(dataSet.getBytes(Tag.Modality), "GB18030"));
            System.out.println(new String(dataSet.getBytes(Tag.StudyDescription), "GB18030"));
            System.out.println(dataSet.getString(Tag.PatientID));
            return dataSet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        String newPath = "C:\\Users\\Burgess\\Desktop\\nn.dcm";
//        DcmReader.readDcmFile("C:\\Users\\Burgess\\Desktop\\20205132443\\12755\\1.2.826.0.1.3680043.1.2.119.12010158.20200513.93117.0.0.3.dcm", newPath);
        DcmReader.readDcmFile("C:\\Users\\Burgess\\Desktop\\刘春梅\\AT0866_77768206100.15583987.dcm","");
    }

}
