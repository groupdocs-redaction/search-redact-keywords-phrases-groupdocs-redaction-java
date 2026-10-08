package com.groupdocs.redaction.examples;

import com.groupdocs.redaction.Redactor;
import com.groupdocs.redaction.redactions.ExactPhraseRedaction;
import com.groupdocs.redaction.options.SaveOptions;
import com.groupdocs.redaction.licensing.License;

import java.io.File;
import java.io.IOException;

/**
 * Demonstrates how to search and redact specific keywords or phrases globally across multiple document formats
 * using GroupDocs Redaction for Java.
 * <p>
 * This example:
 * <ul>
 *   <li>Loads a document from the input folder</li>
 *   <li>Applies redaction for multiple exact phrases (e.g., "John Doe", "Confidential")</li>
 *   <li>Saves the redacted document to the output folder</li>
 * </ul>
 * <p>
 * Supported formats: PDF, DOCX, XLSX, PPTX, images, and more.
 * <p>
 * See: https://docs.groupdocs.com/redaction/java/
 */
public class SearchAndRedactKeywordsExample {

    /**
     * Sets up a temporary license (if available) and runs the redaction demo.
     */
    public static void main(String[] args) {
        setupLicense();
        run();
    }

    /**
     * Applies a temporary license if available; otherwise, runs in evaluation mode.
     * <p>
     * To obtain a temporary license, visit: https://purchase.groupdocs.com/temporary-license
     */
    private static void setupLicense() {
        License license = new License();
        String licensePath = "groupdocs.redaction.lic";
        File licenseFile = new File(licensePath);
        if (licenseFile.exists()) {
            try {
                license.setLicense(licensePath);
            } catch (Exception e) {
                System.out.println("Failed to set license: " + e.getMessage() + ". Running in evaluation mode.");
            }
        } else {
            System.out.println("No license file found. Running in evaluation mode.");
        }
    }

    /**
     * Main demo method: searches for and redacts specified keywords/phrases in a document.
     */
    public static void run() {
        // -----------------------------------------------------------------
        // Step 1. Define input and output paths.
        // -----------------------------------------------------------------
        String inputPath = "src/main/resources/input/sample.docx";
        String outputPath = "src/main/resources/output/redacted_sample.docx";

        // Ensure output directory exists
        new File("src/main/resources/output").mkdirs();

        // -----------------------------------------------------------------
        // Step 2. Initialize Redactor with the input document path.
        // -----------------------------------------------------------------
        Redactor redactor = new Redactor(inputPath);
        try {
            // -----------------------------------------------------------------
            // Step 3. Apply redaction for exact phrase "John Doe".
            // -----------------------------------------------------------------
            redactor.apply(new ExactPhraseRedaction("John Doe", "[REDACTED]"));

            // -----------------------------------------------------------------
            // Step 4. Apply redaction for exact phrase "Confidential" (case-insensitive by default).
            // -----------------------------------------------------------------
            redactor.apply(new ExactPhraseRedaction("Confidential", "[REDACTED]"));

            // -----------------------------------------------------------------
            // Step 5. Save the redacted document to the output path.
            // -----------------------------------------------------------------
            redactor.save(outputPath);

            System.out.println("Redaction completed. Output saved to: " + outputPath);
        } finally {
            // -----------------------------------------------------------------
            // Step 6. Dispose the Redactor to release resources.
            // -----------------------------------------------------------------
            redactor.close();
        }
    }
}
