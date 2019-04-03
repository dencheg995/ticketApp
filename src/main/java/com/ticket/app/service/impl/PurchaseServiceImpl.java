package com.ticket.app.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ticket.app.module.Purchase;
import com.ticket.app.repository.PurchaseRepository;
import com.ticket.app.service.interfaces.PurchaseService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
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
        String host = "http://localhost:8080?";
        String url = host.concat("uniq=").concat(purchase.getUniqId()).concat("&count=")
                .concat(String.valueOf(purchase.getCountBuyTicket()));
        try {
            createQRImage(url);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    private static void createQRImage(String url) throws WriterException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 400, 400, hintMap);
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
    }
}

