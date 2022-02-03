package com.dbxprts.ferro_msc_edi.edi_envelope;

import com.dbxprts.ferro_msc_edi.email.EmailService;
import com.dbxprts.ferro_msc_edi.model.EdiEnvelope;
import com.dbxprts.ferro_msc_edi.model.Mail;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

@Service
public class EdiEnvelopeService {
    @Autowired
    EdiEnvelopeRepository ediEnvelopeRepository;

    @Autowired
    FTPConnectionClient ftpConnectionClient;

    @Autowired
    EmailService emailService;

    Logger logger = LoggerFactory.getLogger(EdiEnvelopeService.class);

    //900000 = 15 mins
    @Scheduled(fixedRate = 900000)
//    @EventListener(ApplicationReadyEvent.class)
    public void processEdiEnvelopes() {
        try {
            ftpConnectionClient.open();

            List<EdiEnvelope> unprocessedEnvelopes = ediEnvelopeRepository.getUnprocessedEnvelopes();

            logger.info("{} unprocessed envelopes", unprocessedEnvelopes.size());

            for (EdiEnvelope envelope : unprocessedEnvelopes) {
                if (envelope.getEnvelopeContent() == null) {
                    logger.error("Envelope has null content! {}", envelope.toString());
                    return;
                }

                Path tempFilePath = createFile(envelope);
                File tempFile = tempFilePath.toFile();

                String path = String.format("/TO_MSC/CODECO_COARRI/%s", tempFile.getName());
                ftpConnectionClient.putFileToPath(tempFile, path);

                Mail mail = new Mail();

                String emailReceipients = "sintermodal@ferroservicios.com," +
                        "israel.neira@msc.com," +
                        "fbarrena@dbxprts.com";

                mail.setFrom("logistics.server@ferroservicios.com");
                mail.setTo(InternetAddress.parse(emailReceipients));

                mail.setContent("Most recent EDI envelope for MSC is attached");
                mail.setSubject("EDI MESSAGES FOR MSC - " + envelope.getEdiEnvelopeID());

                emailService.sendMessageWithAttachment(mail, tempFile);

                logger.info("Sent email to: {}", emailReceipients);

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                envelope.setProcesado("1");
                envelope.setFechaProcesado(timestamp);

                ediEnvelopeRepository.save(envelope);

                Boolean result = tempFile.delete();

                logger.info("Temp file deleted: {}", result);
            }

            ftpConnectionClient.close();

        } catch (IOException | MessagingException | JSchException | SftpException ex) {
            ex.printStackTrace();

            try {
                ftpConnectionClient.close();
            } catch (IOException e) {
                e.printStackTrace();
//                MaerskEdiApplication.restart();
            }
//            MaerskEdiApplication.restart();
        }
    }

    private Path createFile(EdiEnvelope envelope) throws IOException {
        String path = String.format("ferroservicios_edi_%s.edi", envelope.getEdiEnvelopeID());
        Path file = Paths.get(path);
        byte[] content = envelope.getEnvelopeContent().getBytes(StandardCharsets.UTF_8);
        Files.write(file, content);
        return file;
    }
}
