CREATE TABLE IF NOT EXISTS Users (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    area VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Tickets (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id LONG NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    area VARCHAR(50) NOT NULL,
    ticket_date DATETIME NOT NULL,
    title VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    solution TEXT,
    solved_by VARCHAR(50),
    solved_date DATETIME,
    image BLOB,
    closed BOOLEAN DEFAULT FALSE,
    important BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE IF NOT EXISTS Works (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id LONG NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    work_date DATETIME NOT NULL,
    title VARCHAR(50) NOT NULL,
    description LONGTEXT NOT NULL,
    image BLOB,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

CREATE TABLE IF NOT EXISTS Pending (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    pending_date DATETIME NOT NULL,
    notes VARCHAR(255) NOT NULL,
    done BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS Guides (
    id LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
    guide_title VARCHAR(50) NOT NULL,
    guide LONGTEXT NOT NULL,
    admin_only BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS UserTickets (
    user_id LONG NOT NULL,
    ticket_id LONG NOT NULL,
    PRIMARY KEY (user_id, ticket_id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (ticket_id) REFERENCES Tickets(id)
);

CREATE TABLE IF NOT EXISTS UserWorks (
    user_id LONG NOT NULL,
    work_id LONG NOT NULL,
    PRIMARY KEY (user_id, work_id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (work_id) REFERENCES Works(id)
);