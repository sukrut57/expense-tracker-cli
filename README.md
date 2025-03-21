# Expense Tracker CLI

## Overview

The Expense Tracker CLI is a command-line tool that allows users to efficiently track their expenses. It enables users to add, view, update, and delete expenses while providing insights into spending patterns.

Sample solution for the [expense-tracker](https://roadmap.sh/projects/expense-tracker) challenge from [roadmap.sh](https://roadmap.sh/)

## Features

- Add Expenses: Log new expenses with category, amount, and date.

- View Expenses: List all recorded expenses.

- Update Expenses: Modify existing expense records.

- Delete Expenses: Remove expenses from the tracker.

- Category-based Summaries: View total expenses categorized.


## Installation

### Prerequisites

Ensure you have the following installed on your system:

- Java 17+

- Maven (for dependency management)

### Steps

```bash
1. Clone the repository:

git clone https://github.com/sukrut57/expense-tracker-cli
cd expense-tracker-cli
```

2. Build the project:
```bash
mvn clean install
```

3. Run the CLI application:

```bash
java -jar target/expense-tracker-cli.jar
```

## Usage

Once the application is running, you can perform the following actions:

### Add an Expense

```bash
add "Groceries" 50.75 "2025-03-21"
```

### View All Expenses

```bash
list
```

### Update an Expense

```bash
update 1 "Transport" 15.00 "2025-03-20"
```
### Delete an Expense

```bash
delete 2
```
### View Summary by Category

```bash
summary
```

Configuration

Configuration settings (such as database file path) can be adjusted in the config.properties file.

### Contributing

Contributions are welcome! Follow these steps:

1. Fork the repository.

2. Create a feature branch (git checkout -b feature-branch).

3. Commit changes (git commit -m "Add feature").

4. Push to the branch (git push origin feature-branch).

5. Open a Pull Request.

### License

This project is licensed under the MIT License. See the LICENSE file for details.

### Contact

For any issues or suggestions, open an issue on GitHub or contact the maintainer at [sukrut57@github.com]()