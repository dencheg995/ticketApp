package com.ticket.app.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ticket.app.module.Purchase;
import com.ticket.app.repository.PurchaseRepository;
import com.ticket.app.service.interfaces.MailService;
import com.ticket.app.service.interfaces.PurchaseService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final MailService mailService;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, MailService mailService) {
        this.purchaseRepository = purchaseRepository;
        this.mailService = mailService;
    }


    @Override
    public List<Purchase> getPurchaseByTicketId(Long ticketId) {
        return purchaseRepository.getPurchaseByTicketId(ticketId);
    }

    @Override
    public List<Purchase> getPurchaseByConsumerId(Long consumerId) {
        return purchaseRepository.getPurchaseByConsumerId(consumerId);
    }

    @Override
    public Purchase getPurchase(String uniqId) {
        return purchaseRepository.getPurchaseByUniqId(uniqId);
    }

    @Override
    public void sendTicket(Purchase purchase) {
        String host = "http://localhost:8080/verify/ticket?";
        String url = host.concat("id=").concat(String.valueOf(purchase.getId())).concat("&uniq=").concat(purchase.getUniqId()).concat("&count=")
                .concat(String.valueOf(purchase.getCountBuyTicket()));
        try {
            createQRImage(url, purchase);
            mailService.sendingMailingsEmails(purchase);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.getOne(id);
    }

    @Override
    public Purchase getPurchaseByNumSale(int numSale) {
        return purchaseRepository.getPurchaseByNumSale(numSale);
    }

    @Override
    public Purchase update(Purchase purchase) {
        return purchaseRepository.saveAndFlush(purchase);
    }

    private static void createQRImage(String url, Purchase purchase) throws WriterException, IOException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 260, 260, hintMap);
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage qr = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        qr.createGraphics();
        Graphics2D graphicsQR = (Graphics2D) qr.getGraphics();
        graphicsQR.setColor(Color.WHITE);
        graphicsQR.fillRect(0, 0, matrixWidth, matrixWidth);
        graphicsQR.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphicsQR.fillRect(i, j, 1, 1);
                }
            }
        }
        BufferedImage image = new BufferedImage(648, 952, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 660, 952);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 31));
        graphics.drawString(purchase.getTicket().getEvent().getName(), 40, 60);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 20));
        graphics.setColor(Color.gray);
        graphics.drawString(purchase.getTicket().getEvent().getDate(), 42, 90);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 31));
        graphics.drawString("18+", 560, 70);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 31));
        graphics.drawString(purchase.getTicket().getEvent().getClubName(), 40, 170);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 20));
        graphics.setColor(Color.gray);
        graphics.drawString(purchase.getTicket().getEvent().getAddress(), 42, 205);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 25));
        graphics.drawString("ТИП БИЛЕТА", 40, 285);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 31));
        graphics.drawString(purchase.getTicket().getTicketType(), 40, 325);
        graphics.setColor(Color.gray);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 25));
        graphics.drawString("ПОКУПАТЕЛЬ", 40, 550);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 31));
        graphics.drawString(purchase.getConsumer().getFirstName().concat(" ").concat(purchase.getConsumer().getLastName()), 40, 590);
        graphics.setColor(Color.decode("#72D573"));
        graphics.fillRect(0, 630, 660, 322);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Avenir", Font.BOLD, 35));
        graphics.drawString("Покажите QR-код", 40, 690);
        graphics.drawString("билета кассиру", 40, 730);
        graphics.drawString("при входе на", 40, 770);
        graphics.drawString("мероприятие", 40, 810);
        graphics.setFont(new Font("Avenir", Font.PLAIN, 25));
        graphics.drawString("Служба поддержки", 40, 880);
        graphics.setFont(new Font("Avenir", Font.BOLD, 35));
        graphics.drawString("hue@example.com", 40, 920);
        graphics.drawImage(qr, null, 370,670);

        ImageIO.write(image, "png", new File("./tickets/purchase_".concat(purchase.getId().toString()).concat(".png")));
    }
}

