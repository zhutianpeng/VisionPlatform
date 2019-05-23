package io.renren.fileCenter.service.impl;

import com.jcraft.jsch.SftpException;
import io.renren.fileCenter.entity.User;
import io.renren.fileCenter.service.FileService;
import io.renren.fileCenter.utils.SftpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private User user;

    @Override
    public boolean uploadToFtp(File file) {
        SftpUtil sftp = new SftpUtil(user.getUsername(), user.getPassword(), user.getAddress(), user.getPort());
        sftp.login();
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            sftp.upload(user.getBasepath(), file.getName(), is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (SftpException e) {
            e.printStackTrace();
            return false;
        }

        sftp.logout();
        return true;
    }

    @Override
    public boolean downloadToFtp(String file_dir,String fileName,String local_dir) {
        SftpUtil sftp = new SftpUtil(user.getUsername(), user.getPassword(), user.getAddress(), user.getPort());
        sftp.login();
        try{
            sftp.download(file_dir,fileName,local_dir);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (SftpException e) {
            e.printStackTrace();
            return false;
        }

        sftp.logout();
        return true;
    }
}
