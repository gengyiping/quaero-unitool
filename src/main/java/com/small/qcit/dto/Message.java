package com.small.qcit.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Message implements Serializable{
	private static final long serialVersionUID = 9206706412147822992L;
	private int motorId;
    private long acce;
    private long speed;
    private long  coord;
    private int sensorId;
    private long resetSpeed;
    private long resetAcc;
}
