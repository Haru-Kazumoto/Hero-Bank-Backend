package dev.pack.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender{

    private final JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Async
    public void send(String to, String email) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Activate your email to Hero Bank!");

            logger.info("Email sending. . . ");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Email has been roll back due to error!",e);
            throw new RuntimeException("Email has been roll back due to error!");
        }
    }
}
