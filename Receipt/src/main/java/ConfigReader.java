import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.*;
import java.util.*;

import static org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA_BOLD;

public class ConfigReader {
    public static void main(String[] args) throws IOException {
        File file = new File("config.properties");
        Scanner scanner = new Scanner(file);

        String acquirer = "";
        String issuer = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().startsWith("[BANK]")) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.trim().startsWith("ACQUIRER=")) {
                        acquirer = line.split("=")[1].trim();

                    }
                    else if (line.trim().startsWith("ISSUER=")) {
                        issuer = line.split("=")[1].trim();
                        break;
                    }

                }
            }
        }

        scanner.close();
        String acquirerBank = null;

        for (int i = 0;i<acquirer.split(",").length;i++) {
                acquirerBank = acquirer.split(",")[i].trim();
                String directory = "Pdf/"+acquirerBank;
                File theDir = new File(directory);
                if (!theDir.exists()){
                    theDir.mkdirs();
                }
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);
                PDFont pdfFont=  new PDType1Font(HELVETICA_BOLD);
                int fontSize = 8;
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(pdfFont,fontSize);
                contentStream.beginText();
                contentStream.newLineAtOffset(5, 700);
                contentStream.showText("1   RETNSI  : RA.1B/6B/2T");
                contentStream.newLineAtOffset(170, -1);
                contentStream.showText("LAPORAN BIAYA TRANSAKSI EFT SWITCHING (ATM-PAYMENT)");
                contentStream.newLineAtOffset(320, -1);
                contentStream.showText("FREKWENSI: HARIAN");
                contentStream.newLineAtOffset(-487, -23);
                contentStream.showText("   LAPORAN  : P102 ");
                contentStream.newLineAtOffset(148, 0);
                contentStream.showText("        ACQUIRER  :   "+acquirerBank);
                contentStream.newLineAtOffset(340, 0);
                contentStream.showText("TANGGAL:"+ java.time.LocalDate.now());
                contentStream.endText();
                contentStream.close();
                String pdfDirectory = "Pdf/"+acquirerBank+"/"+acquirerBank+java.time.LocalDate.now()+".pdf";
                document.save(pdfDirectory);
                document.close();


            }
    }
}
