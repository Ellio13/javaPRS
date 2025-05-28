use prsmysql;


INSERT INTO User (ID, UserName, Password, FirstName, LastName, PhoneNumber, Email, Reviewer, Admin) VALUES
(1, 'jdoe', 'password1', 'John', 'Doe', '555-123-4567', 'jdoe@example.com', 1, 1),
(2, 'asmith', 'password2', 'Alice', 'Smith', '555-987-6543', 'asmith@example.com', 1, 0),
(3, 'bwilliams', 'password3', 'Bob', 'Williams', '555-234-5678', 'bwilliams@example.com', 0, 0),
(4, 'cmiller', 'password4', 'Cindy', 'Miller', '555-345-6789', 'cmiller@example.com', 1, 0),
(5, 'dlee', 'password5', 'David', 'Lee', '555-456-7890', 'dlee@example.com', 0, 0),
(6, 'echen', 'password6', 'Emily', 'Chen', '555-567-8901', 'echen@example.com', 1, 1),
(7, 'fpatel', 'password7', 'Farah', 'Patel', '555-678-9012', 'fpatel@example.com', 0, 0),
(8, 'gmartin', 'password8', 'George', 'Martin', '555-789-0123', 'gmartin@example.com', 0, 0),
(9, 'hlopez', 'password9', 'Hannah', 'Lopez', '555-890-1234', 'hlopez@example.com', 1, 0),
(10, 'ijackson', 'password10', 'Isaac', 'Jackson', '555-901-2345', 'ijackson@example.com', 0, 1);


INSERT INTO Vendor (Id, Code, Name, Address, City, State, Zip, PhoneNumber, Email) VALUES
(1, 'VEND001', 'Acme Supplies', '123 Main St', 'Springfield', 'OH', '45503', '555-123-4567', 'contact@acmesupplies.com'),
(2, 'VEND002', 'TechNova', '456 Tech Way', 'Columbus', 'OH', '43085', '555-234-5678', 'support@technova.com'),
(3, 'VEND003', 'GreenLeaf Goods', '789 Garden Rd', 'Athens', 'OH', '45701', '555-345-6789', 'hello@greenleaf.com'),
(4, 'VEND004', 'Summit Tools', '321 Industrial Dr', 'Toledo', 'OH', '43604', '555-456-7890', 'sales@summittools.com'),
(5, 'VEND005', 'River City Paper', '98 Water St', 'Cincinnati', 'OH', '45202', '555-567-8901', 'info@rivercitypaper.com'),
(6, 'VEND006', 'Peak Audio', '654 Music Ln', 'Dayton', 'OH', '45402', '555-678-9012', 'service@peakaudio.com'),
(7, 'VEND007', 'Bright Office', '23 Sunshine Blvd', 'Cleveland', 'OH', '44101', '555-789-0123', 'orders@brightoffice.com'),
(8, 'VEND008', 'FastPrint Co', '88 Print Ave', 'Akron', 'OH', '44308', '555-890-1234', 'contact@fastprint.com'),
(9, 'VEND009', 'Northridge Equip', '12 Ridge Rd', 'Mansfield', 'OH', '44902', '555-901-2345', 'parts@northridge.com'),
(10, 'VEND010', 'Everlight Inc.', '777 Beacon St', 'Youngstown', 'OH', '44503', '555-012-3456', 'admin@everlight.com');


INSERT INTO Product (ID, VendorID, PartNumber, Name, Price, Unit, Photopath) VALUES
(1, 1, 'P-1001', 'Stapler', 12.99, 'each', ''),
(2, 2, 'TN-2001', 'Wireless Mouse', 29.95, 'each', ''),
(3, 3, 'GL-3011', 'Compostable Cups (100)', 18.50, 'pack', ''),
(4, 4, 'ST-4200', 'Hammer - 16oz', 14.99, 'each', ''),
(5, 5, 'RCP-5512', 'Printer Paper (500ct)', 6.75, 'ream', ''),
(6, 6, 'PA-6600', 'Bluetooth Speaker', 49.99, 'each', ''),
(7, 7, 'BO-7310', 'Desk Lamp', 22.49, 'each', ''),
(8, 8, 'FP-8002', 'Business Cards (500ct)', 25.00, 'box', ''),
(9, 9, 'NE-9001', 'Electric Drill', 89.99, 'each', ''),
(10, 10, 'EL-1001', 'LED Lightbulbs (4-pack)', 9.99, 'pack', '');

INSERT INTO Request (ID, UserID, RequestNumber, Description, Justification, DateNeeded, DeliveryMode, Status, Total, SubmittedDate, ReasonForRejection) VALUES
(1, 1, 'R2505200001', 'Office supplies', 'Restocking paper and pens', '2025-05-25', 'Mail', 'NEW', 75.50, '2025-05-20', NULL),
(2, 2, 'R2505200002', 'IT Equipment', 'New mouse and keyboard', '2025-05-27', 'Pickup', 'REVIEW', 49.99, '2025-05-20', NULL),
(3, 3, 'R2505200003', 'Workshop Tools', 'Replacement hammer and drill bits', '2025-05-26', 'Mail', 'APPROVED', 89.00, '2025-05-20', NULL),
(4, 4, 'R2505200004', 'Client giveaway', 'Branded mugs for clients', '2025-05-30', 'Pickup', 'REJECTED', 150.00, '2025-05-20', NULL),
(5, 5, 'R2505200005', 'Team celebration', 'Snacks and drinks', '2025-05-25', 'Pickup', 'NEW', 45.75, '2025-05-20', NULL),
(6, 6, 'R2505200006', 'Training materials', 'Manuals and pens', '2025-05-28', 'Mail', 'REVIEW', 35.25, '2025-05-20', NULL),
(7, 7, 'R2505200007', 'Maintenance supplies', 'Gloves and cleaner', '2025-05-29', 'Pickup', 'APPROVED', 60.10, '2025-05-20', NULL),
(8, 8, 'R2505200008', 'Cables and chargers', 'Extra phone chargers', '2025-05-30', 'Mail', 'REJECTED', 22.90, '2025-05-20', NULL),
(9, 9, 'R2505200009', 'Event setup', 'Tablecloths and signage', '2025-06-01', 'Pickup', 'NEW', 90.00, '2025-05-20', NULL),
(10, 10, 'R2505200010', 'Replacement bulbs', 'LED lights for office', '2025-05-27', 'Mail', 'APPROVED', 14.00, '2025-05-20', NULL),
(11, 1, 'R2505200011', 'Printer ink', 'All colors low', '2025-05-30', 'Pickup', 'NEW', 67.89, '2025-05-20', NULL),
(12, 2, 'R2505200012', 'Extension cords', 'Safety setup', '2025-05-28', 'Mail', 'REVIEW', 19.99, '2025-05-20', NULL),
(13, 3, 'R2505200013', 'Promotional flyers', 'Upcoming sale', '2025-06-03', 'Pickup', 'REJECTED', 85.00, '2025-05-20', NULL),
(14, 4, 'R2505200014', 'Employee gifts', 'Welcome packages', '2025-06-05', 'Mail', 'APPROVED', 200.00, '2025-05-20', NULL),
(15, 5, 'R2505200015', 'Cleaning products', 'Weekly supplies', '2025-06-01', 'Pickup', 'NEW', 38.45, '2025-05-20', NULL);


INSERT INTO LineItem (ID, RequestID, ProductID, Quantity) VALUES
(1, 1, 1, 2),  -- Office supplies: 2 Staplers
(2, 2, 2, 1),  -- IT equipment: 1 Wireless Mouse
(3, 3, 4, 1),  -- Workshop Tools: 1 Hammer
(4, 4, 3, 3),  -- Client giveaway: 3 Packs of Compostable Cups
(5, 5, 5, 5),  -- Team celebration: 5 Reams of Paper
(6, 6, 6, 2),  -- Training materials: 2 Bluetooth Speakers
(7, 7, 7, 4),  -- Maintenance supplies: 4 Desk Lamps
(8, 8, 8, 1),  -- Cables/chargers: 1 Set of Business Cards
(9, 9, 9, 1),  -- Event setup: 1 Electric Drill
(10, 10, 10, 6);  -- Replacement bulbs: 6 packs of LED Lightbulbs



SET FOREIGN_KEY_CHECKS = 1;

SET FOREIGN_KEY_CHECKS = 0;
Drop Table if Exists Lineitem;
CREATE TABLE LineItem (
ID int AUTO_INCREMENT PRIMARY KEY,
RequestID int NOT NULL,
ProductID int NOT NULL,
Quantity int NOT NULL,
FOREIGN KEY (RequestID) REFERENCES Request(ID),
FOREIGN KEY (ProductID) REFERENCES Product(ID),
CONSTRAINT req_pdt UNIQUE(RequestID, ProductID));

