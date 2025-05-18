# 🕸️ XScraper_Automation

Automate data extraction from a dynamic website and export the results to structured JSON files using Java, Selenium WebDriver, and TestNG. This project focuses on end-to-end UI automation combined with data scraping and JSON serialization.

---

## 📌 Features

* Automated scraping of structured data across multiple paginated views.
* JSON file creation using the Jackson library.
* Use of custom wrapper methods for clean and reusable Selenium code.
* Assertion of successful data extraction and file generation.
* Use of epoch timestamps to track when each record was scraped.

---

## 📂 Test Cases

### ✅ `testCase01`: Hockey Teams Scraper

**Objective**: Extract underperforming hockey team stats.

**Steps**:

1. Navigate to the target website.
2. Click on **"Hockey Teams: Forms, Searching and Pagination"**.
3. Iterate through **4 pages**.
4. For each row with **Win % less than 40%**, extract:

   * Epoch Time of Scrape
   * Team Name
   * Year
   * Win %
   
5. Store the data in an `ArrayList<HashMap<String, Object>>`.
6. Write the data to `hockey-team-data.json`.

---

### ✅ `testCase02`: Oscar Winning Films Scraper

**Objective**: Extract top 5 films from each year and identify the best picture winner.

**Steps**:

1. Click on **"Oscar Winning Films"**.
2. Iterate over all available years.
3. For each year:

   * Click the year link.
   * Extract details of the **top 5 movies**:
     * Epoch Time of Scrape
     * Awards
     * Title
     * Best Picture Winner (true if Best Picture winner)
     * Nominations
     * Year
4. Write the results to `oscar-winner-data.json`.
5. **Assert** that the file exists and is not empty.

---

## 🛠️ Technologies Used

* Java 8+
* Selenium WebDriver
* TestNG
* Jackson (for JSON conversion)
* Gradle (build tool)
* VSCode / IntelliJ / Eclipse

---

## 📆 How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/XScraper.git
   ```
2. Open the project in your preferred IDE.
3. Ensure all dependencies are installed:

   * Jackson
   * Selenium
   * TestNG
4. Run the `TestCases.java` as a **TestNG Suite**.

---

## 📁 Output

* `hockey-team-data.json` - Contains team performance details.
* `oscar-winner-data.json` - Contains top movies and best picture flag per year.

All files are saved in the `output/` folder located in the project root.

---

## ✅ Sample JSON Structure

```json
{
  "Epoch Time of Scrape: " : 1747571350,
  "Team Name: " : "Dallas Stars",
  "Year: " : "1994",
  "Win %: " : "0.354"
}
```

```json
{
  "Epoch Time of Scrape:" : 1747571374,
  "Awards: " : 4,
  "Title: " : "The King's Speech",
  "Best Picture Winner: " : true,
  "Nomination: " : 12,
  "Year: " : 2010
}
```

---

## 📝 Notes

* Make sure you wait for page loads before interacting with elements (use explicit waits).
* Store scraped data using proper naming and timestamps for traceability.
* Run tests in headless mode for CI/CD pipelines if required.
