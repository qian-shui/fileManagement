package com.wang.controller;

/*
* 文件上传相关接口
* */

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.config.AuthAccess;
import com.wang.pojo.FileData;
import com.wang.service.FileService;
import com.wang.utils.RedisUtils;
import com.wang.vo.Result;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;

    @Autowired
    private RedisUtils redisUtils;

    public static final String FILES_KEY = "FILES_FRONT_ALL";


    @AuthAccess
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String type = FileUtil.extName(filename);
        long size = file.getSize();
        File uploadParentFile= new File(fileUploadPath);
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        String uuid = IdUtil.fastSimpleUUID();
        File uploadFile = new File(fileUploadPath + uuid + StrUtil.DOT+type);
        //把获取的文件存储到磁盘里面去
        file.transferTo(uploadFile);
        //获取文件的md5，在磁盘上存在唯一性，避免相同文件存入多个
        String md5 = SecureUtil.md5(uploadFile);
        FileData fileByMd5 = fileService.findFileByMd5(md5);
        String url;
        if(fileByMd5 != null){
            url = fileByMd5.getUrl();
            uploadFile.delete();
        }else{
            url = "http://"+serverIp+":9090/file/"+uuid+StrUtil.DOT+type;
        }
        FileData buName = fileService.findFileByName(filename);
        if(buName == null){
            //数据存入到数据库
            FileData fileData = new FileData();
            fileData.setName(filename);
            fileData.setType(type);
            fileData.setSize(size/1024);
            fileData.setUrl(url);
            fileData.setMd5(md5);
            fileService.saveUpdateFile(fileData);
            redisUtils.delete(FILES_KEY);
        }
        return url;
    }

    @AuthAccess
    @GetMapping("/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws Exception {
        //根据文件的唯一标识获取文件
        File uploadFile = new File(fileUploadPath + fileName);
        //设置输出格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
        response.setContentType("application/octet-stream");

        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    //当缓存的file数据有修改时，重新加载缓存信息
    //@CachePut(value = "files",key = "'frontPage'")
    @PostMapping("/update")
    public Result saveUpdateUser(@RequestBody FileData fileData){
        fileService.saveUpdateFile(fileData);
        return Result.success("enable修改成功",null);
    }

//    清除一条file数据时，重新加载缓存
//    @CacheEvict(value = "files",key = "'frontPage'")
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        IPage<FileData> page = fileService.findPage(pageNum, pageSize,name);
        return Result.success("分页查询成功",page);
    }


    @GetMapping("/deleteFile")
    public Result deleteFile(@RequestParam("id") Integer id){
        boolean i = fileService.deleteFile(id);
        redisUtils.delete(FILES_KEY);
        return Result.success("删除文件成功",null);
    }

    @PostMapping("/batchDeleteFile")
    public boolean batchDeleteFile(@RequestBody List<Integer> ids) {
        return fileService.batchDeleteFile(ids);
    }


    @AuthAccess
    @GetMapping("/frontFileShow")
    //@Cacheable(value = "files",key = "'frontPage'")
    public Result frontPage(){
        List<FileData> list;
        String o = (String) redisUtils.get(FILES_KEY);
        if(StrUtil.isBlank(o)){
            list = fileService.frontPage();
            redisUtils.set(FILES_KEY,JSONUtil.toJsonStr(list));
        }else{
            list = JSONUtil.toBean(o, new TypeReference<List<FileData>>() {
            },true);
        }
        return Result.success("图片获取成功",list);
    }


    @AuthAccess
    @GetMapping("/findFileById/{id}")
    public Result findFileById(@PathVariable String id) {
        FileData fileData = fileService.findFileById(id);
        return Result.success("file数据获取成功",fileData);
    }

}
