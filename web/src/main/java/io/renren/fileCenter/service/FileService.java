package io.renren.fileCenter.service;

import java.io.File;

public interface FileService {

    /**
     *  通过SFTP上传文件
     * @param file
     * @return
     */
    public boolean uploadToFtp(File file);

    /**
     *  通过SFTP下载文件
     * @param file_dir 服务器文件路径
     * @param fileName 文件名称
     * @param local_dir 本地文件路径
     * @return
     */
    public boolean downloadToFtp(String file_dir,String fileName,String local_dir);
}
