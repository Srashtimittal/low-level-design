CREATE DATABASE IF NOT EXISTS meeting_scheduler;
USE meeting_scheduler;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS meeting_rooms (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE IF NOT EXISTS meetings (
    id VARCHAR(40) PRIMARY KEY,
    subject VARCHAR(200) NOT NULL,
    organizer_id INT NOT NULL,
    room_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS meeting_participants (
    meeting_id VARCHAR(40),
    user_id INT,
    response VARCHAR(20)
);

INSERT INTO users VALUES
(1, 'User 1', 'user1@example.com'),
(2, 'User 2', 'user2@example.com'),
(3, 'User 3', 'user3@example.com'),
(4, 'User 4', 'user4@example.com');

INSERT INTO meeting_rooms VALUES
(1, 'Room 1', 4),
(2, 'Room 2', 8),
(3, 'Room 3', 12);
