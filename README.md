# SpringBootExampleJWTAuth
A simple Spring Boot project that shows how to implement authentication with a JWT Token.

There are 3 URLs that you can use in this project:

POST: http://localhost:8080/api/auth <br />
This one is for authentication. <br /> 
You must send an email and a password to obtain the JWT.<br />
When the server goes up, a default user is registered: [email: leonardomvs@gmail.com]  [password: 12345678]<br />
It also shows on the server log.<br />

GET: http://localhost:8080/api/hellopublic<br />
This URL is public.<br />
You can access it without sending the JWT.<br />

GET: http://localhost:8080/api/helloprivate<br />
This URL is private.<br />
You can only access it if you send a valid JWT.<br />

By default, all new URLs created will be private.
If you want to make a new URL public, you will have to add its pattern on the io.github.leonardomvs.config.SecurityConfig.configure method.