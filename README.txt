I have screenshots of my postman calls under data directory.Also, there is a json sample for send email call and an image from h2 db and samples of sent emails.
I can store uuid and byte array format of file to db. When sending the email, i can form the file back from db .
Then, i can send the email and i can view the inline attachment in Windows mail application (sample sent mail are under data directory) I have learnt that Gmail does not support embedded images
(https://stackoverflow.com/questions/12655764/base64-not-being-decoded-in-gmail)
Unit tests are working.
User and password should be changed in application.yml file to send an email