package com.mindhub.homebanking.controllers;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class PdfController {
    @PostMapping(value = "/transactions/generate-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestBody Map<String, List<Map<String, String>>> requestData) throws IOException {
        List<Map<String, String>> tableData = requestData.get("tableData");

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float y = page.getMediaBox().getHeight() - 50;
        for (Map<String, String> row : tableData) {
            float x = 50;
            for (String key : row.keySet()) {
                String value = row.get(key);
                contentStream.beginText();
                contentStream.newLineAtOffset(x, y);
                contentStream.showText(value);
                contentStream.endText();
                x += 100;
            }
            y += 20;
        }
        contentStream.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();
        byte[] pdfbytes = baos.toByteArray();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdfbytes);
    }
}