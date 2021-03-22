package com.small.qcit.service;

import javax.servlet.http.HttpServletResponse;

import com.small.qcit.upload.Chunk;

public interface BigFileServiceInterface {

	String fileUploadPost(Chunk chunk, HttpServletResponse response);

}
