package com.flavorbooking.services.impl.admin;
import com.flavorbooking.repositories.admin.InvoiceQuanlityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceQuanlitySerice {
    @Autowired
    private InvoiceQuanlityRepository invoicequanlityRepository;
    public int sumAllInvoiceQuanlity(){ return invoicequanlityRepository.sumAllInvoiceQuanlity();}
}
