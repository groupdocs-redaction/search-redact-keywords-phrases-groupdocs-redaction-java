# GroupDocs Redaction Java – Search and Redact Keywords and Phrases

## Overview
This showcase project demonstrates how to use **GroupDocs Redaction** (Java) to search a Word document for an email address and a company phrase and replace both matches. It is a minimal, runnable Maven project that helps developers understand how to redact several terms with one regular expression and keep the original file format.

## Prerequisites
- **Java Development Kit (JDK) 8** or higher
- **Apache Maven** 3.5+ installed and added to your `PATH`
- An **Internet connection** for Maven to download dependencies

## License
GroupDocs Redaction requires a license file to work without evaluation limitations (trial badge, one opened document per process, and so on).

1. **Obtain a temporary license** – Visit https://purchase.groupdocs.com/temporary-license/ to get a free 30-day temporary license.
2. **Place the license file** – Save the downloaded `GroupDocs.Redaction.Java.lic` file in the **project root directory** (the same level where `pom.xml` resides).
3. **Without a license** – The library runs in evaluation mode, which adds a trial badge and opens only one document per process.

## Project Structure
```
search-redact-keywords-phrases-groupdocs-redaction-java/
│   pom.xml
│   README.md
│   GroupDocs.Redaction.Java.lic   (optional – place your license here)
│
├───src
│   └───main
│       └───java
│           └───com
│               └───groupdocs
│                   └───redaction
│                       └───examples
│                           └───SearchAndRedactKeywordsExample.java
│
├───resources
│   ├───input
│   │   └───sample.docx            (sample Word document – replace with your own)
│   └───output                     (the redacted document will be saved here)
```

## Setup & Run
1. **Clone or copy the project**
   ```bash
   git clone https://github.com/your-repo/search-redact-keywords-phrases-groupdocs-redaction-java.git
   cd search-redact-keywords-phrases-groupdocs-redaction-java
   ```
2. **Add your license file** (optional but recommended) – copy `GroupDocs.Redaction.Java.lic` into the project root.
3. **Place a Word document** you want to redact inside `resources/input/`. The project already contains `sample.docx`, which you can replace.
4. **Build the project**
   ```bash
   mvn clean compile
   ```
5. **Run the example**
   - Using Maven Exec plugin:
     ```bash
     mvn exec:java
     ```

## What Happens When You Run It?
- The program loads the license (if present).
- It checks `resources/input/sample.docx`. If the file is missing, execution stops with an error message.
- An output directory `resources/output/` is created if it does not already exist.
- `RegexRedaction` replaces `INFO@FABRIKAM.COM` and `FABRIKAM TECHNOLOGY` with `[REDACTED]`. The pattern `(?i)INFO@FABRIKAM\.COM|FABRIKAM TECHNOLOGY` ignores case.
- Rasterization is turned off, so the file is saved as `resources/output/sample_redacted.docx`.
- Console output informs you about the progress and the status of each redaction.

## Customization
- **Redact other terms** – change `SEARCH`. Separate alternatives with `|`, and keep `(?i)` when the match should ignore case.
- **Redact one exact phrase** – replace the `RegexRedaction` with `new ExactPhraseRedaction("FABRIKAM TECHNOLOGY", new ReplacementOptions(REPLACEMENT))`.
- **Make that phrase case-sensitive** – use `new ExactPhraseRedaction("FABRIKAM TECHNOLOGY", true, new ReplacementOptions(REPLACEMENT))`.
- **Hide a match with a color box** – pass `new ReplacementOptions(java.awt.Color.BLACK)` instead of a replacement string.
- **Redact another document** – replace `resources/input/sample.docx` with another supported file, or change `INPUT_FILE` in the source.

## Notes
- The included `sample.docx` is an annual review for Fabrikam. It contains `INFO@FABRIKAM.COM` and `FABRIKAM TECHNOLOGY`, so both replacements can be checked immediately.
- Evaluation mode applies only one redaction. Keep additional terms inside the same expression. A second redaction object in the same run is skipped.
- `Redactor.save()` with no arguments writes a rasterized PDF. This example disables rasterization so the output keeps the `.docx` extension.
- Ensure the `resources/input/` and `resources/output/` directories are at the same level as `pom.xml` as shown in the structure above.

---

*This is a showcase project for GroupDocs Redaction. It is intended for demonstration and educational purposes.*
