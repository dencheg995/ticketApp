package com.ticket.app.service.interfaces;

import com.ticket.app.module.Purchase;

public interface MailService {

   boolean sendingMailingsEmails(Purchase purchase);
}
