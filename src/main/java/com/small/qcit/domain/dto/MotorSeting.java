package com.small.qcit.domain.dto;

public class MotorSeting {

	  private long cmd;
	  private long idx;
	  private long zero;
	  private long msShift;
	  private long stopEna;
	  private long emmrEna;
	  private long emmrTrigger;
	  private long rotate;
	  private long zeroSwitch;
	  private long zeroEna;
	  private long zeroTrigger;
	  private long motorEna;
	  private long motorCurrent;
	  private long motorClk;
	  private long motorForward;
	  private long phy;
	  private long max;
	  private long forward;
	  private long reverse;
	public long getCmd() {
		return cmd;
	}
	public void setCmd(long cmd) {
		this.cmd = cmd;
	}
	public long getIdx() {
		return idx;
	}
	public void setIdx(long idx) {
		this.idx = idx;
	}
	public long getZero() {
		return zero;
	}
	public void setZero(long zero) {
		this.zero = zero;
	}
	public long getMsShift() {
		return msShift;
	}
	public void setMsShift(long msShift) {
		this.msShift = msShift;
	}
	public long getStopEna() {
		return stopEna;
	}
	public void setStopEna(long stopEna) {
		this.stopEna = stopEna;
	}
	public long getEmmrEna() {
		return emmrEna;
	}
	public void setEmmrEna(long emmrEna) {
		this.emmrEna = emmrEna;
	}
	public long getEmmrTrigger() {
		return emmrTrigger;
	}
	public void setEmmrTrigger(long emmrTrigger) {
		this.emmrTrigger = emmrTrigger;
	}
	public long getRotate() {
		return rotate;
	}
	public void setRotate(long rotate) {
		this.rotate = rotate;
	}
	public long getZeroSwitch() {
		return zeroSwitch;
	}
	public void setZeroSwitch(long zeroSwitch) {
		this.zeroSwitch = zeroSwitch;
	}
	public long getZeroEna() {
		return zeroEna;
	}
	public void setZeroEna(long zeroEna) {
		this.zeroEna = zeroEna;
	}
	public long getZeroTrigger() {
		return zeroTrigger;
	}
	public void setZeroTrigger(long zeroTrigger) {
		this.zeroTrigger = zeroTrigger;
	}
	public long getMotorEna() {
		return motorEna;
	}
	public void setMotorEna(long motorEna) {
		this.motorEna = motorEna;
	}
	public long getMotorCurrent() {
		return motorCurrent;
	}
	public void setMotorCurrent(long motorCurrent) {
		this.motorCurrent = motorCurrent;
	}
	public long getMotorClk() {
		return motorClk;
	}
	public void setMotorClk(long motorClk) {
		this.motorClk = motorClk;
	}
	public long getMotorForward() {
		return motorForward;
	}
	public void setMotorForward(long motorForward) {
		this.motorForward = motorForward;
	}
	public long getPhy() {
		return phy;
	}
	public void setPhy(long phy) {
		this.phy = phy;
	}
	public long getMax() {
		return max;
	}
	public void setMax(long max) {
		this.max = max;
	}
	public long getForward() {
		return forward;
	}
	public void setForward(long forward) {
		this.forward = forward;
	}
	public long getReverse() {
		return reverse;
	}
	public void setReverse(long reverse) {
		this.reverse = reverse;
	}
	public MotorSeting(long cmd, long idx, long zero, long msShift, long stopEna, long emmrEna, long emmrTrigger,
			long rotate, long zeroSwitch, long zeroEna, long zeroTrigger, long motorEna, long motorCurrent,
			long motorClk, long motorForward, long phy, long max, long forward, long reverse) {
		super();
		this.cmd = cmd;
		this.idx = idx;
		this.zero = zero;
		this.msShift = msShift;
		this.stopEna = stopEna;
		this.emmrEna = emmrEna;
		this.emmrTrigger = emmrTrigger;
		this.rotate = rotate;
		this.zeroSwitch = zeroSwitch;
		this.zeroEna = zeroEna;
		this.zeroTrigger = zeroTrigger;
		this.motorEna = motorEna;
		this.motorCurrent = motorCurrent;
		this.motorClk = motorClk;
		this.motorForward = motorForward;
		this.phy = phy;
		this.max = max;
		this.forward = forward;
		this.reverse = reverse;
	}
	public MotorSeting() {
		super();
	}
    
}
