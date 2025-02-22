# Hackathon-1000devs

Software that enables management and visualization of vaccines administered to family members.
Challenge proposed for the final hackathon of the 1000devs program, developed in collaboration with other program students.

## Team Members
  - Caio Soares
  - Cauã Emanuel
  - June Castello
  - Magno Kelly
  - Mariana Bingi
  - Rennam Faria

## Features
- Track vaccines administered to family members
- View all registered patients, immunizations, and vaccines
- Manage patients and immunizations (edit, register, and delete)

## Prerequisites
Before running this project, make sure you have installed:
- Java Developer Kit (JDK)
- MySQL
- Live server (e.g., VS Code Live Server or Python's HTTP server)
- Git `(if you want to obtain the code via terminal)`

## Installation & Setup
1. Clone the repository:
```bash
git clone https://github.com/yourusername/Hackathon-1000devs.git
cd Hackathon-1000devs
```

2. Configure the Database:
   - Generate tables in MySQL, code in `script_sql_vacina.txt`
   - Configure the file `src/main/java/br/com/api/config` for your MySQL settings (Currently set to root and default password)

## Running the Application
### Backend
1. Navigate to the backend source directory:
```bash
cd src/main/java/br/com/api/
```

2. Run the Main.java file to start the backend server:
```bash
java Main.java
```

### Frontend
You have two options to serve the frontend:

1. Using VS Code Live Server:
   - Open the project in VS Code
   - Right-click on `src/main/resources/public/index.html`
   - Select "Open with Live Server"

2. Using Python's HTTP server:
```bash
cd src/main/resources/public
python3 -m http.server
```

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── br/
│   │       └── com/
│   │           └── api/
│   │               └── Main.java
│   └── resources/
│       └── public/
│           └── index.html
```

## Page Structure and Navigation
Created in Figma

https://www.figma.com/board/0S5EXIdLx1FVHbP5APVdSO/Hackaton-1000-devs---organiza%C3%A7%C3%A3o-de-telas?node-id=0-1&t=MgNuBTdxYzD2SuFD-1

## Acknowledgments
- 1000devs program for the opportunity
- All team members for their dedication and hard work
