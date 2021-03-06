DROP TABLE Item IF EXISTS;
DROP TABLE Invoice IF EXISTS;
DROP TABLE Product IF EXISTS;
DROP TABLE Customer IF EXISTS;
CREATE TABLE Customer(ID INTEGER PRIMARY KEY,FirstName VARCHAR(20),LastName VARCHAR(30),Street VARCHAR(50),City VARCHAR(25));
CREATE TABLE Product(ID INTEGER PRIMARY KEY,Name VARCHAR(30),Price DECIMAL);
CREATE TABLE Invoice(ID INTEGER PRIMARY KEY,CustomerID INTEGER,Total DECIMAL, FOREIGN KEY (CustomerId) REFERENCES Customer(ID) ON DELETE CASCADE);
CREATE TABLE Item(InvoiceID INTEGER,Item INTEGER,ProductID INTEGER,Quantity INTEGER,Cost DECIMAL,PRIMARY KEY(InvoiceID,Item), FOREIGN KEY (InvoiceId) REFERENCES Invoice (ID) ON DELETE CASCADE, FOREIGN KEY (ProductId) REFERENCES Product(ID) ON DELETE CASCADE);
CREATE TABLE dual(dummy VARCHAR(1));
/;
insert into dual (dummy) values ('X')
/;

CREATE PROCEDURE GET_CUSTOMER(in_id int)
   READS SQL DATA DYNAMIC RESULT SETS 1
   BEGIN ATOMIC
     DECLARE result CURSOR FOR SELECT * FROM CUSTOMER WHERE ID = in_id;
     OPEN result;
   END
/;

CREATE PROCEDURE GET_CUSTOMERS(in_firstname VARCHAR(20), in_lastname VARCHAR(30))
   READS SQL DATA DYNAMIC RESULT SETS 1
   BEGIN ATOMIC
     DECLARE result CURSOR FOR 
     SELECT * FROM CUSTOMER
     WHERE (ISNULL(RTRIM(LTRIM(FirstName)),'') IS NULL OR UPPER(RTRIM(LTRIM(FirstName))) LIKE '%' || UPPER(in_firstname) || '%')
     AND (ISNULL(RTRIM(LTRIM(LastName)),'') IS NULL OR UPPER(RTRIM(LTRIM(LastName))) LIKE '%' || UPPER(in_lastname) || '%'); 
     OPEN result;
   END
/;

CREATE PROCEDURE GET_FIRSTNAMES(in_ids INT ARRAY)
   READS SQL DATA DYNAMIC RESULT SETS 1
   BEGIN ATOMIC
     DECLARE result CURSOR FOR
     SELECT ARRAY_AGG(FIRSTNAME ORDER BY FIRSTNAME) AS firstnames
	   FROM CUSTOMER
	  WHERE ID IN ( UNNEST(in_ids) );
     OPEN result;
   END
     
/;

CREATE PROCEDURE SP_GET_INVOICE_DETAIL(in_ids INT ARRAY)
   READS SQL DATA DYNAMIC RESULT SETS 1
   BEGIN ATOMIC
     DECLARE result CURSOR FOR
     SELECT A.id, A.customerId, (C.firstName || ' ' || C.lastName) AS customerName, A.total, B.item, B.productId, D.name AS productName, B.quantity, B.cost
	   FROM Invoice A
 INNER JOIN Item B
 		 ON A.id = B.invoiceId
 INNER JOIN Customer C
         ON A.customerId = C.id
 INNER JOIN Product D
 		 ON B.productId = D.id  
	  WHERE A.ID IN ( UNNEST(in_ids) );
     OPEN result;
   END
     
/;