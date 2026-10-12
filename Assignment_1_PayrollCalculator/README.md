# APAN 5175 Introduction to Programming Using Java

Author: Hanqing Wang (hw3146)

## Assignment 1 - Java Basics Lab: Syntax & Data Types

The program:
- Prompts the user for their name, hourly wage, and hours worked
- Calculates gross salary (hourly wage × hours worked)
- Deducts a flat 20% tax and calculates net salary
- Validates user input (rejects empty names, non-numeric values, and values ≤ 0)

### How to Run


```bash
cd Assignment_1_PayrollCalculator
javac PayrollCalculator.java
java PayrollCalculator
```

### Sample Output

```
Enter employee name: Hanqing Wang
Enter hourly wage: 25.5
Enter hours worked: 40

Payroll Summary for Hanqing Wang
-----------------------------------
Hours Worked: 40
Hourly Wage: $25.50
Gross Salary: $1020.00
Taxes Deducted: $204.00
Net Salary: $816.00
```