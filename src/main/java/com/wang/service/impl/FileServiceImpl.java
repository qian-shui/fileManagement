package com.wang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.common.Constants;
import com.wang.exception.ServiceException;
import com.wang.mapper.FileMapper;
import com.wang.pojo.FileData;
import com.wang.pojo.User;
import com.wang.service.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl  extends ServiceImpl<FileMapper, FileData> implements FileService {
    @Override
    public boolean saveUpdateFile(FileData fileData) {
        try {
            return saveOrUpdate(fileData);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public FileData findFileByMd5(String md5) {
        QueryWrapper<FileData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<FileData> list = list(queryWrapper);
        return list.size() == 0 ? null :list.get(0);
    }


    @Override
    public FileData findFileByName(String filename) {
        QueryWrapper<FileData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",filename);
        return getOne(queryWrapper);
    }

    @Override
    public List<FileData> frontPage() {
        return list();
    }

    @Override
    public FileData findFileById(String id) {
        return getById(id);
    }


    @Override
    public IPage<FileData> findPage(Integer pageNum, Integer pageSize, String name) {
        Page<FileData> page = new Page<>(pageNum, pageSize);
        QueryWrapper<FileData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",false);
        if(!name.isEmpty()) queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        return page(page,queryWrapper);
    }

    @Override
    public boolean deleteFile(Integer id) {
        FileData fileData = getById(id);
        fileData.setIsDelete(true);
        return updateById(fileData);
    }

    @Override
    public boolean batchDeleteFile(List<Integer> ids) {
        List<FileData> fileData = listByIds(ids);
        for (FileData fileDatum : fileData) {
            fileDatum.setEnable(true);
        }
        return updateBatchById(fileData);
    }


}
