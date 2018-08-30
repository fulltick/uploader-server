package org.fanti.uploader.server.controller;

import com.alibaba.fastjson.JSON;
import org.fanti.uploader.server.bean.FileInfo;
import org.fanti.uploader.server.constant.BasicConstants;
import org.fanti.uploader.server.constant.ControllerConstants;
import org.fanti.uploader.server.controller.base.BaseController;
import org.fanti.uploader.server.dto.FileDTO;
import org.fanti.uploader.server.dto.ResultDTO;
import org.fanti.uploader.server.service.download.DownloadService;
import org.fanti.uploader.server.service.upload.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */

@Controller
@RequestMapping("/download")
public class DownloadController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadController.class);

    @Autowired
    DownloadService downloadService;

    @Value("${upload.folder}")
    private String uploadFolder;

    @ResponseBody
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResultDTO download (HttpServletRequest request) {


        return ajaxDoneSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "/listFiles")
    public ResultDTO listFiles () {
        File rootDir = new File(uploadFolder);
        //如果file代表的不是一个文件，而是一个目录
        if(rootDir.isDirectory()){
            //列出该目录下的所有文件和目录
            List<FileDTO> fileList = new ArrayList<>();
            File files[] = rootDir.listFiles();
            //遍历files[]数组
            for(File file : files){
                if (file == null) {
                    continue;
                }

                FileDTO fileDTO = new FileDTO();
                fileDTO.setName(file.getName());
                if(file.isDirectory()){
                    fileDTO.setType(BasicConstants.FILE_TYPE_IS_DIR);
                } else {
                    fileDTO.setType(BasicConstants.FILE_TYPE_IS_FILE);
                }

                fileList.add(fileDTO);
            }
            return ajaxDoneSuccess(fileList);
        }

        return ajaxDoneFail("当前目录不存在!");
    }


}