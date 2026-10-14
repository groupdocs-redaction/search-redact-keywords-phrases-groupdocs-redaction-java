package com.groupdocs.redaction.examples;

import com.groupdocs.redaction.Redaction;
import com.groupdocs.redaction.RedactionStatus;
import com.groupdocs.redaction.Redactor;
import com.groupdocs.redaction.RedactorChangeLog;
import com.groupdocs.redaction.RedactorLogEntry;
import com.groupdocs.redaction.exceptions.TrialLimitationsException;
import com.groupdocs.redaction.licensing.License;
import com.groupdocs.redaction.options.RasterizationOptions;
import com.groupdocs.redaction.redactions.RegexRedaction;
import com.groupdocs.redaction.redactions.ReplacementOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Demonstrates how to search a document for a phrase and an email address and redact both matches using GroupDocs Redaction.
 * <p>
 * The example expects a Word file (<code>sample.docx</code>) placed in the <code>resources/input/</code>
 * directory (project root). The redacted document is saved to <code>resources/output/</code> in the original format.
 * </p>
 */
public class SearchAndRedactKeywordsExample {

    private static final String INPUT_FILE = "resources/input/sample.docx";
    private static final String OUTPUT_DIR = "resources/output/";
    private static final String LICENSE_FILE = "GroupDocs.Redaction.Java.lic";
    private static final String SEARCH = "(?i)INFO@FABRIKAM\\.COM|FABRIKAM TECHNOLOGY";
    private static final String REPLACEMENT = "[REDACTED]";

    /**
     * Loads the GroupDocs Redaction license if the license file exists.
     * <p>
     * To get a temporary license, visit:
     * <a href="https://purchase.groupdocs.com/temporary-license/">https://purchase.groupdocs.com/temporary-license/</a>.
     * Place the downloaded <code>GroupDocs.Redaction.Java.lic</code> file in the project root directory.
     * Without a license the library works in evaluation mode with a trial badge and a limit of one opened document per process.
     * </p>
     *
     * @param licensePath path to the license file relative to the project root
     */
    public static void loadLicense(String licensePath) {
        File licenseFile = new File(licensePath);
        if (licenseFile.exists()) {
            try {
                License license = new License();
                license.setLicense(licensePath);
                System.out.println("GroupDocs Redaction license loaded successfully.");
            } catch (Exception e) {
                System.err.println("Failed to load GroupDocs Redaction license: " + e.getMessage());
            }
        } else {
            System.out.println("License file not found at " + licensePath + ". Running in evaluation mode.");
        }
    }

    /**
     * Replaces <code>INFO@FABRIKAM.COM</code> and <code>FABRIKAM TECHNOLOGY</code> with <code>[REDACTED]</code>.
     * <p>
     * {@link RegexRedaction} searches both terms in one expression. The <code>(?i)</code> flag makes the match
     * case-insensitive, and the dot in the email address is escaped. Rasterization is disabled, so the document
     * is not written as PDF. The output file is <code>sample_redacted.docx</code>.
     * </p>
     */
    public static void redactKeywords() {
        Path outDirPath = Paths.get(OUTPUT_DIR);
        try {
            if (!Files.exists(outDirPath)) {
                Files.createDirectories(outDirPath);
                System.out.println("Created output directory: " + outDirPath.toAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Failed to create output directory: " + e.getMessage());
            return;
        }

        File inputFile = new File(INPUT_FILE);
        if (!inputFile.exists()) {
            System.err.println("Input file not found: " + INPUT_FILE);
            return;
        }

        // One expression covers the email and the company phrase. Evaluation mode applies only one redaction.
        Redaction redaction = new RegexRedaction(SEARCH, new ReplacementOptions(REPLACEMENT));
        String outputFile = outDirPath.resolve("sample_redacted.docx").toString();

        // save() with no arguments writes a rasterized PDF. Disable rasterization to keep the original format.
        RasterizationOptions rasterOptions = new RasterizationOptions();
        rasterOptions.setEnabled(false);

        try (Redactor redactor = new Redactor(INPUT_FILE)) {
            RedactorChangeLog result = redactor.apply(redaction);
            if (result.getStatus() == RedactionStatus.Failed) {
                System.err.println("Redaction failed for " + INPUT_FILE + ": " + result.getStatus());
                printLog(result);
                return;
            }

            try (FileOutputStream stream = new FileOutputStream(outputFile)) {
                redactor.save(stream, rasterOptions);
            }
            System.out.println("Redacted file saved: " + Paths.get(outputFile).toAbsolutePath()
                    + " (status: " + result.getStatus() + ")");
            printLog(result);
        } catch (TrialLimitationsException e) {
            System.out.println("Evaluation mode allows only one document in a process. "
                    + "Add " + LICENSE_FILE + " to the project root and run the example again.");
        } catch (Exception e) {
            System.err.println("Error processing " + INPUT_FILE + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Prints the status of each redaction from the change log.
     */
    private static void printLog(RedactorChangeLog result) {
        for (RedactorLogEntry logEntry : result.getRedactionLog()) {
            System.out.println(logEntry.getRedaction().getClass().getSimpleName()
                    + ": " + logEntry.getResult().getStatus());
        }
    }

    public static void main(String[] args) {
        // Load license if available
        loadLicense(LICENSE_FILE);
        // Perform the redaction demonstration
        redactKeywords();
    }
}
