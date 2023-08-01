package com.mindhub.homebanking.controllers;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class PdfController {
    @PostMapping("/pdfGenerator")
    public void createPDF(HttpServletResponse response) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Paragraph paragraph = new Paragraph("Soy un desarrollador web fullStack JAVA");
        document.add(paragraph);
        document.close();
    }
}