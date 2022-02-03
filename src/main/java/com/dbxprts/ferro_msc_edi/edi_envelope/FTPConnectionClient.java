package com.dbxprts.ferro_msc_edi.edi_envelope;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FTPConnectionClient {

    private String server;
    private int port;
    private String user;
    private String password;
    private ChannelSftp sftpChannel;
    private Session jschSession;
    //private String keyPath = "/home/dbxapps/.ssh/ferro_maersk.key";
    private String keyPath = "/root/.ssh/id_rsa";
//private String keyPath = "C:\\Users\\Diego\\.ssh\\id_rsa";

    public void open() throws IOException, JSchException {
        JSch jSch = new JSch();

//        jSch.addIdentity(keyPath);
        jschSession = jSch.getSession(user, server, port);
//        jschSession.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.setPassword(password);
        jschSession.connect();

        sftpChannel = (ChannelSftp) jschSession.openChannel("sftp");
        sftpChannel.connect();
    }

    public void close() throws IOException {
        sftpChannel.disconnect();
        jschSession.disconnect();
    }

    public void putFileToPath(File file, String path) throws IOException, SftpException {
//        sftpChannel.storeFile(path, new FileInputStream(file));

        for (Object entry : sftpChannel.ls("/")) {
            System.out.println(entry);
        }
        FileInputStream fileIS = new FileInputStream(file);
        try {
            sftpChannel.put(fileIS, path);
        } catch (Exception e ){
            System.out.println(e.getLocalizedMessage());
        }
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ChannelSftp getSftpChannel() {
        return sftpChannel;
    }

    public void setSftpChannel(ChannelSftp sftpChannel) {
        this.sftpChannel = sftpChannel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
