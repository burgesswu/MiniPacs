package com.carlwu.minipacs.logic.bean;

import lombok.Data;

import java.util.Date;
@Data
public class DataBean {
    private Integer id;
    private String patient_name;
    private String age;
    private String uid;
    private Integer file_count;
    private Double file_size;
    private Integer upload_status;
    private String study_no;
    private String start_time;
    private String end_time;


}
