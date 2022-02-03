package com.dbxprts.ferro_msc_edi.configuration;

import com.dbxprts.ferro_msc_edi.edi_envelope.FTPConnectionClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FTPConfiguration {
    @Value("${ftpsettings.server}")
    String ftpServer;

    @Value("${ftpsettings.username}")
    String ftpUsername;

    @Value("${ftpsettings.password}")
    String ftpPassword;

    @Value("${ftpsettings.port}")
    int ftpPort;

    @Bean
    public FTPConnectionClient createFtpConnectionClient() {
        FTPConnectionClient ftpConnectionClient = new FTPConnectionClient();

        ftpConnectionClient.setServer(ftpServer);
        ftpConnectionClient.setUser(ftpUsername);
        ftpConnectionClient.setPassword(ftpPassword);
        ftpConnectionClient.setPort(ftpPort);

        return ftpConnectionClient;
    }

}
