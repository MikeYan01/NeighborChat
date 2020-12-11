-- Live in the same building, different nRange
INSERT INTO Users
VALUES (1, "user01", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Justin",
        "Bieber", "343 Gold Street, Brooklyn", "Apt 4001", "Hello World!", "../static/plugins/images/users/1.png", 0,
        "2019-01-01 12:00:00", TRUE);
INSERT INTO Users
VALUES (2, "user02", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Donald",
        "Trump", "343 Gold Street, Brooklyn", "Apt 4002", "Hello World!", "../static/plugins/images/users/2.png ", 1,
        "2019-01-01 12:00:00", TRUE);
INSERT INTO Users
VALUES (3, "user03", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Chris",
        "Martin", "343 Gold Street, Brooklyn", "Apt 4201", "Hello World!", "../static/plugins/images/users/3.png ", 2,
        "2019-01-01 12:00:00", TRUE);


-- Live in the same block, different nRange
INSERT INTO Users
VALUES (4, "user04", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Lady",
        "Gaga", "270 Jay Street, Brooklyn", "", "Hello World!", "../static/plugins/images/users/4.png ", 0,
        "2019-01-01 12:00:00", TRUE);
INSERT INTO Users
VALUES (5, "user05", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Anne",
        "Hathaway", "320 Jay Street, Brooklyn", "", "Hello World!", "../static/plugins/images/users/5.png ", 1,
        "2019-01-01 12:00:00", TRUE);
INSERT INTO Users
VALUES (6, "user06", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Leonardo",
        "Dicaprio", "370 Jay Street, Brooklyn", "", "Hello World!", "../static/plugins/images/users/6.png ", 2,
        "2019-01-01 12:00:00", TRUE);


-- Live in the same hood, different nRange
INSERT INTO Users
VALUES (7, "user07", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Billie",
        "Ellish", "500 5th Avenue, New York", "", "Hello World!", "../static/plugins/images/users/7.png", 2,
        "2019-01-01 12:00:00", TRUE);
INSERT INTO Users
VALUES (8, "user08", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "James",
        "Bond", "1100 6th Avenue, New York", "", "Hello World!", "../static/plugins/images/users/8.png ", 1,
        "2019-01-01 12:00:00", TRUE);
INSERT INTO Users
VALUES (9, "user09", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Adam",
        "Levine", "1166 6th Avenue, New York", "", "Hello World!", "../static/plugins/images/users/9.png ", 2,
        "2019-01-01 12:00:00", TRUE);
INSERT INTO Users
VALUES (10, "user10", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Bruno",
        "Mars", "1167 6th Avenue, New York", "", "Hello World!", "../static/plugins/images/users/10.png ", 0,
        "2019-01-01 12:00:00", TRUE);
INSERT INTO Users
VALUES (11, "user11", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Scarlett",
        "Johansson", "1167 6th Avenue, New York", "", "Hello World!", "../static/plugins/images/users/11.png ", 1,
        "2019-01-01 12:00:00", TRUE);
INSERT INTO Users
VALUES (12, "user12", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f", "test@gmail.com", "Robert",
        "Downey", "1170 6th Avenue, New York", "", "Hello World!", "../static/plugins/images/users/12.png ", 2,
        "2019-01-01 12:00:00", TRUE);


-- Live in different hoods
INSERT INTO Hoods
VALUES (1, "Downtown Brooklyn", "(40.6902, -73.9943)", "(40.7059, -73.9809)");
INSERT INTO Hoods
VALUES (2, "Midtown Manhattan", "(40.7477, -73.9929)", "(40.7647, -73.9739)");
INSERT INTO Hoods
VALUES (3, "Uptown Bronx", "(40.8113, -73.9315)", "(40.8830, -73.7945)");


-- Live in different blocks
INSERT INTO Blocks
VALUES (1, "Jay Street", 1, "(40.6962, -73.9872)", "(40.6998, -73.9868)");
INSERT INTO Blocks
VALUES (2, "Gold Street", 1, "(40.6922, -73.9834)", "(40.7056, -73.9823)");
INSERT INTO Blocks
VALUES (3, "5th Avenue From 34th Street to 59th Street", 2, "(40.7485, -73.9846)", "(40.7644, -73.9730)");
INSERT INTO Blocks
VALUES (4, "6th Avenue From 34th Street to 59th Street", 2, "(40.7498, -73.9878)", "(40.7657, -73.9761)");


-- For block 1, all users in this area have joined the block (3 accepts required)
INSERT INTO UserBlock
VALUES (1, "2019-01-01 12:00:01", 1, TRUE);
INSERT INTO UserBlock
VALUES (2, "2019-01-01 12:00:01", 1, TRUE);
INSERT INTO UserBlock
VALUES (3, "2019-01-01 12:00:01", 1, TRUE);

-- For block 2, two out of three users have joined, and the third one is applying to join (all members accepts required)
INSERT INTO UserBlock
VALUES (4, "2019-01-01 12:00:01", 2, TRUE);
INSERT INTO UserBlock
VALUES (5, "2019-01-01 12:00:01", 2, TRUE);

-- For block 3, someone is the first person to join the block (automatically join) (already joined now)
INSERT INTO UserBlock
VALUES (7, "2019-01-01 12:00:01", 3, TRUE);

-- For block 4, four out of five users have joined, and the fifth one is applying to join (3 accepts required)
INSERT INTO UserBlock
VALUES (8, "2019-01-01 12:00:01", 4, TRUE);
INSERT INTO UserBlock
VALUES (9, "2019-01-01 12:00:01", 4, TRUE);
INSERT INTO UserBlock
VALUES (10, "2019-01-01 12:00:01", 4, TRUE);
INSERT INTO UserBlock
VALUES (11, "2019-01-01 12:00:01", 4, TRUE);

-- For block 2, members < 3, all members agreement required
INSERT INTO BlockApplication
VALUES (6, "2019-01-01 12:00:02", 2, "I am your new neighbor", 0, 0);

-- For block 3, member was 0, applicant 7 immediately joined, so there is no record

-- For block 4, member > 3, only 3 accepts required
INSERT INTO BlockApplication
VALUES (12, "2019-01-01 12:00:02", 4, "I am your new neighbor", 0, 0);


-- Two people already in the same block group
INSERT INTO Friends
VALUES (1, 2, "2019-01-01 12:00:03");
INSERT INTO Friends
VALUES (4, 5, "2019-01-01 12:00:03");

-- Two people in the same hood, but not the same block
INSERT INTO Friends
VALUES (7, 8, "2019-01-01 12:00:03");
INSERT INTO Friends
VALUES (7, 10, "2019-01-01 12:00:03");

-- One move to a new place, but still keeps old friendship, and make new friends in new group
INSERT INTO Friends
VALUES (2, 11, "2019-02-01 12:00:03");


-- Two people already in the same block group
INSERT INTO FriendApplication
VALUES (2, 3, "I wanna be your friend", "2019-01-01 12:00:03", -1);
INSERT INTO FriendApplication
VALUES (8, 9, "I wanna be your friend", "2019-01-01 12:00:03", -1);

-- Two people live in the same hood, but not the same block
INSERT INTO FriendApplication
VALUES (1, 4, "I wanna be your friend", "2019-01-01 12:00:03", -1);
INSERT INTO FriendApplication
VALUES (7, 11, "I wanna be your friend", "2019-01-01 12:00:03", -1);


-- Insert Neighbor according to neighbor range (here we assume all users add all their neighbors)
INSERT INTO Neighbors
VALUES (1, 2, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (1, 3, "2019-01-01 12:00:03");

INSERT INTO Neighbors
VALUES (2, 1, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (2, 3, "2019-01-01 12:00:03");

INSERT INTO Neighbors
VALUES (3, 1, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (3, 2, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (3, 4, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (3, 5, "2019-01-01 12:00:03");

INSERT INTO Neighbors
VALUES (5, 4, "2019-01-01 12:00:03");

INSERT INTO Neighbors
VALUES (7, 8, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (7, 9, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (7, 10, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (7, 11, "2019-01-01 12:00:03");

INSERT INTO Neighbors
VALUES (8, 9, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (8, 10, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (8, 11, "2019-01-01 12:00:03");

INSERT INTO Neighbors
VALUES (9, 7, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (9, 8, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (9, 10, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (9, 11, "2019-01-01 12:00:03");

INSERT INTO Neighbors
VALUES (10, 11, "2019-01-01 12:00:03");

INSERT INTO Neighbors
VALUES (11, 8, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (11, 9, "2019-01-01 12:00:03");
INSERT INTO Neighbors
VALUES (11, 10, "2019-01-01 12:00:03");


-- Someone sends message to friends
INSERT INTO Message
VALUES (1, 1, 1, "2019-01-01 12:00:04", "LOL", "Life", "I am happy", "(40.6962, -73.9872)");

-- Someone sends message to neighbors, and coordinates is null
INSERT INTO Message
VALUES (2, 5, 2, "2019-01-01 12:00:04", "???", "Life", "Stop using my Wi-Fi", "");

-- Someone sends message to block
INSERT INTO Message
VALUES (3, 8, 3, "2019-01-01 12:00:04", "Help", "Work", "Can someone help me with my school work?",
        "(40.7498, -73.9878)");

-- Someone sends message to hood, and coordinates is null
INSERT INTO Message
VALUES (4, 7, 4, "2019-01-01 12:00:04", "Vote time", "Food", "Which one do you prefer, medium rare or medium steak?",
        "");

-- Someone sends message to particular person from friends
INSERT INTO Message
VALUES (5, 7, 0, "2019-01-01 12:00:04", "Hello", "Life", "How''s your weekend?", "(40.7657, -73.9761)");

-- Someone sends message to particular person from neighbors
INSERT INTO Message
VALUES (6, 9, 0, "2019-01-01 12:00:04", "Hi", "Life", "I love you", "(40.7657, -73.9761)");
INSERT INTO Message
VALUES (7, 2, 0, "2019-01-01 12:00:04", "Invitation", "Life", "Have dinner with me?", "(40.7056, -73.9823)");

-- Specifically for bicycle accident
---- Keyword in title, in neighbor feed
INSERT INTO Message
VALUES (8, 9, 2, "2019-01-01 12:00:04", "Bicycle Accident", "Emergency", "Somebody is hit by a bicycle in the block",
        "(40.7498, -73.9878)");
---- Keyword in subject, in block feed
INSERT INTO Message
VALUES (9, 10, 3, "2019-01-01 12:00:04", "Terrible!", "Bicycle Accident", "I was hit by a bicycle...",
        "(40.7498, -73.9878)");
---- Keyword in text, in hood feed
INSERT INTO Message
VALUES (10, 11, 4, "2019-01-01 12:00:04", "Accident Report", "Accident", "There is a bicycle accident in the block!",
        "(40.7498, -73.9878)");


-- Recipient data based on test data in Message
INSERT INTO Recipient
VALUES (5, 8);
INSERT INTO Recipient
VALUES (6, 10);
INSERT INTO Recipient
VALUES (7, 3);


-- General data
INSERT INTO MailBox
VALUES (2, 1, false);
INSERT INTO MailBox
VALUES (4, 2, true);
INSERT INTO MailBox
VALUES (9, 3, false);
INSERT INTO MailBox
VALUES (10, 3, true);
INSERT INTO MailBox
VALUES (11, 3, true);
INSERT INTO MailBox
VALUES (3, 7, true);
INSERT INTO MailBox
VALUES (8, 5, false);


-- They need to reply, so they must have read the message
INSERT INTO MailBox
VALUES (8, 4, true);
INSERT INTO MailBox
VALUES (9, 4, true);
INSERT INTO MailBox
VALUES (10, 4, true);
INSERT INTO MailBox
VALUES (11, 4, true);
INSERT INTO MailBox
VALUES (10, 6, true);

-- After reply, message''s author will get updated in his/her mailbox
INSERT INTO MailBox
VALUES (7, 4, false);
INSERT INTO MailBox
VALUES (9, 6, false);

-- Specifically for bicycle accident
INSERT INTO MailBox
VALUES (7, 8, false);
INSERT INTO MailBox
VALUES (8, 8, false);
INSERT INTO MailBox
VALUES (10, 8, false);
INSERT INTO MailBox
VALUES (11, 8, false);

INSERT INTO MailBox
VALUES (8, 9, false);
INSERT INTO MailBox
VALUES (9, 9, false);
INSERT INTO MailBox
VALUES (11, 9, false);

INSERT INTO MailBox
VALUES (7, 10, false);
INSERT INTO MailBox
VALUES (8, 10, false);
INSERT INTO MailBox
VALUES (9, 10, false);
INSERT INTO MailBox
VALUES (10, 10, false);


-- Reply to particular message
INSERT INTO Reply
VALUES (6, 10, "2019-01-01 13:00:04", "I love you too", "");

-- Reply to group message
INSERT INTO Reply
VALUES (4, 8, "2019-01-01 12:00:06", "Medium Rare", "");
INSERT INTO Reply
VALUES (4, 9, "2019-01-01 12:00:06", "Medium", "(40.7583, -73.9815)");
INSERT INTO Reply
VALUES (4, 10, "2019-01-01 12:00:06", "Medium Rare", "");
INSERT INTO Reply
VALUES (4, 11, "2019-01-01 12:00:06", "Medium", "(40.7630, -73.9781)");