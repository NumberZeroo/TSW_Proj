package com.tswproject.tswproj;
import model.invoice.InvoiceBean;
import model.invoice.InvoiceDAO;
import model.orderItem.OrderItemBean;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class Invoice {
    public void generate(long orderId, OutputStream output) throws IOException, SQLException {
        PDDocument doc = new PDDocument();
        PDPage newpage = new PDPage(); //Create Blank Page
        doc.addPage(newpage); //Add the blank page

        double totaleFatturaIvaEsclusa = 0;
        double totaleFatturaIvaInclusa = 0;

        PDPage page = (PDPage)doc.getPage(0);
        PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

        InvoiceBean invoiceBean;
        try(InvoiceDAO invoiceDAO = new InvoiceDAO()) {
            invoiceBean = invoiceDAO.doRetrieveByOrderId(orderId);
        }

        try {
            PDPageContentStream cs = new PDPageContentStream(doc, page);
            cs.beginText();
            cs.setFont(font, 20);
            cs.newLineAtOffset(250, 750);
            cs.showText("Darwin's Shop"); //Shop name
            cs.endText();

            //Writing the customer details
            cs.beginText();
            cs.setFont(font, 10);
            cs.setLeading(20f);
            cs.newLineAtOffset(60, 610);
            cs.showText("Cliente: ");
            cs.newLine();
            cs.showText("Indirizzo: ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.setLeading(20f);
            cs.newLineAtOffset(110, 610);
            cs.showText(invoiceBean.getCustomer());

            cs.newLine();
            StringBuilder address = new StringBuilder();
            address.append(invoiceBean.getInfoConsegna().getCitta())
                    .append(", ")
                    .append(invoiceBean.getInfoConsegna().getCap())
                    .append(", ")
                    .append(invoiceBean.getInfoConsegna().getVia());
            cs.showText(address.toString());
            cs.endText();

            cs.beginText();
            cs.newLineAtOffset(440, 610);
            cs.showText("Data ordine: ");
            cs.newLine();
            cs.showText("Codice ordine: ");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.setLeading(20f);
            cs.newLineAtOffset(510, 610);
            cs.showText(invoiceBean.getDate().toString());
            cs.newLine();
            cs.showText(Long.toString(invoiceBean.getOrderId()));
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(60, 540);
            cs.showText("Prodotto");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(300, 540);
            cs.showText("Prezzo unitario");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(300, 530);
            cs.showText("(IVA eclusa)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(370, 540);
            cs.showText("IVA %");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(410, 540);
            cs.showText("Prezzo unitario");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(410, 530);
            cs.showText("(IVA inclusa)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(480, 540);
            cs.showText("Quantità");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(530, 540);
            cs.showText("Prezzo totale");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 10);
            cs.newLineAtOffset(530, 530);
            cs.showText("(IVA inclusa)");
            cs.endText();

            cs.beginText();
            cs.setFont(font, 8);
            cs.setLeading(20f);
            cs.newLineAtOffset(60, 510);

            for(OrderItemBean orderItemBean : invoiceBean.getOrderItems()) {
                cs.showText(orderItemBean.getNome());
                cs.setFont(font, 6);
                cs.setLeading(10f);
                cs.newLine();
                cs.showText("Codice articolo: " + orderItemBean.getIdProdotto());
                cs.setLeading(20f);
                cs.setFont(font, 8);
                cs.newLine();
            }

            cs.endText();

            cs.beginText();
            cs.setFont(font, 8);
            cs.setLeading(30f);
            cs.newLineAtOffset(338, 510);

            double totaleIvaEsclusa = 0;
            for(OrderItemBean orderItemBean : invoiceBean.getOrderItems()) {
                totaleIvaEsclusa = (orderItemBean.getPrezzo() * orderItemBean.getQuantita()) ;
                totaleFatturaIvaEsclusa += totaleIvaEsclusa;
                cs.showText(new DecimalFormat("0.00").format(totaleIvaEsclusa) + "€");
                cs.newLine();
            }

            cs.endText();

            cs.beginText();
            cs.setFont(font, 8);
            cs.setLeading(30f);
            cs.newLineAtOffset(375, 510);

            for(OrderItemBean orderItemBean : invoiceBean.getOrderItems()) {
                cs.showText(orderItemBean.getIva() + "%");
                cs.newLine();
            }

            cs.endText();

            cs.beginText();
            cs.setFont(font, 8);
            cs.setLeading(30f);
            cs.newLineAtOffset(448, 510);
            for(OrderItemBean orderItemBean : invoiceBean.getOrderItems()) {
                double prezzoIvaInclusa = orderItemBean.getPrezzo() + (orderItemBean.getPrezzo() * orderItemBean.getIva()/100.0);
                cs.showText(new DecimalFormat("0.00").format(prezzoIvaInclusa) + "€");
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(font, 8);
            cs.setLeading(30f);
            cs.newLineAtOffset(500, 510);
            for(OrderItemBean orderItemBean : invoiceBean.getOrderItems()) {
                cs.showText(orderItemBean.getQuantita() + "pz");
                cs.newLine();
            }
            cs.endText();

            cs.beginText();
            cs.setFont(font, 8);
            cs.setLeading(30f);
            cs.newLineAtOffset(560, 510);
            for(OrderItemBean orderItemBean : invoiceBean.getOrderItems()) {
                double prezzoIvaInclusa = orderItemBean.getPrezzo() + (orderItemBean.getPrezzo() * orderItemBean.getIva()/100.0);
                double totale = prezzoIvaInclusa * orderItemBean.getQuantita();
                totaleFatturaIvaInclusa += totale;
                cs.showText(new DecimalFormat("0.00").format(totale) + "€");
                cs.newLine();
            }
            cs.endText();

            int k = 500-(25);

            cs.beginText();
            cs.newLine();
            cs.newLine();
            cs.setFont(font, 8);
            cs.setLeading(20f);
            cs.newLineAtOffset(300, k);
//            cs.showText("Spedizione: ");
//            cs.setLeading(25f);
            cs.newLine();
            cs.setFont(font, 10);
            cs.showText("Totale fattura: ");
            cs.setLeading(20f);
            cs.newLine();
            cs.setFont(font, 8);
            cs.showText("Totale IVA esclusa: ");
            cs.endText();

            cs.beginText();
            cs.newLine();
            cs.newLine();
            cs.newLine();
            cs.setFont(font, 8);
            cs.setLeading(20f);
            cs.newLineAtOffset(560, k);
//            cs.showText(new DecimalFormat("0.00").format(5) + "€");
            cs.setFont(font, 10);
            cs.setLeading(25f);
            cs.newLine();
            cs.showText(new DecimalFormat("0.00").format(totaleFatturaIvaInclusa) + "€");
            cs.setFont(font, 8);
            cs.setLeading(20f);
            cs.newLine();
            cs.showText(new DecimalFormat("0.00").format(totaleFatturaIvaEsclusa) + "€");
            cs.endText();

            cs.close();
            doc.save(output); //Save the PDF
        } finally {
            doc.close();
        }
    }
}
