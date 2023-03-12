package com.wang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.pojo.FileData;
import com.wang.pojo.User;

import java.util.List;

public interface FileService {
    boolean saveUpdateFile(FileData fileData);

    FileData findFileByMd5(String md5);

    IPage<FileData> findPage(Integer pageNum, Integer pageSize, String name);

    boolean deleteFile(Integer id);

    boolean batchDeleteFile(List<Integer> ids);

    FileData findFileByName(String filename);

    List<FileData> frontPage();

    FileData findFileById(String id);
}
