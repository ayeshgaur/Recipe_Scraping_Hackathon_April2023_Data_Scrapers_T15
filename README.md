# Recipe_Scraping_Hackathon_April2023_Data_Scrapers_T15
## Purpose

Recipe scraping project holds scripts for scraping the recipes from tarladalal website which contains more than 14,000 recipes. 
The purpose of the hackathon is to extract as many recipes as possible for the patients who are suffering from different medical conditions such as diabetes, hypertension, hypothyriodism and pcos.

## Project Structure

```mermaid
flowchart 
A[Recipe Scraping Hackathon] --> B[src]--> C[test] -->D[java]
C--> R[Resources]
R-->driver
R--> outPutDataExcel
R-->Ingredients&ComorbidityExcel
R-->configFile
D-->E{Packages}
E --> F[Base]
F-->BaseClass
E-->allergy -->Allergy
E -->diabetes-->ComorbidityDiabetes
E-->hypertesnsion-->ComorbidityHypertension
E -->hypothyroidism-->ComorbidityHypothyroidism
E-->pcos-->PCOS
E-->G[utilities]
G-->ReadExcel
G-->WriteExcel
G-->ReadConfig

```
