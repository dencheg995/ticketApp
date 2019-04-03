package com.ticket.app.service.impl;

import com.ticket.app.module.Purchase;
import com.ticket.app.service.interfaces.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MailServiceImpl implements MailService {

    private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private boolean sendingMailingsEmails(Purchase purchase) {
        boolean result = false;
        StringBuilder br = new StringBuilder();
        br.append("Поздравляем вас с покупкой билетов на мероприятие ").append("\n")
                .append(purchase.getTicket().getEvent().getName()).append("Номер заказа: ")
                .append(purchase.getTicket().getEvent().getId()).append("Дата и время :").append(purchase.getTicket().getEvent()).append("\n")
                .append("Место проведени: ").append(purchase.getTicket().getEvent()).append("\n")
                .append("По адресу: ").append(purchase.getTicket().getEvent().getAddress()).append("\n\n")
                .append("Электронные билеты прикреплены к этому сообщению . ").append(
                        "Копия этого письма уже ждет вас на почте .").append(
                        "Вы можете распечатать их или просто предъявить с экрана ").append(
                        "мобильного телефона при входе на мероприятие . ").append("\n\n")
                .append("QR - код на билете является гарантом его уникальности!").append("\n\n")
                .append("Служба поддержки клиентов : email").append("\n\n").append("Желаем приятно провести время !");
        try {
            final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom("Пока хз");
            mimeMessageHelper.setTo(purchase.getConsumer().getEmail());
            mimeMessageHelper.setSubject("Билеты на " + purchase.getTicket().getEvent().getName());
            File file = new File("/");
            if (file.exists()) {
                InputStreamSource inputStreamSource = new FileSystemResource(file);
                mimeMessageHelper.addInline("image", inputStreamSource, "image/jpeg");
            } else {
                logger.error("Can not send message! Attachment file {} not found. Reimport file.", file.getCanonicalPath());
                return false;
            }
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.info("Message no sent.", e);
        } catch (NullPointerException e) {
            logger.info("No recipients found, clientData is empty.", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
