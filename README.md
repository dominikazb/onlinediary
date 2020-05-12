<h2>Online Diary Application</h2>

--------------------------------------------------------------

<h3>Visit online: <a href="https://onlinediary.appspot.com">https://onlinediary.appspot.com</a></h3>

--------------------------------------------------------------

<p>Online application to store your personal thoughts. The user can create new account, log in, create virtual diary notes, edit, or delete them, and log out. Application stores data in mysql database hosted on a personal zenbox server. Relations between tables are created using @ManyToMany and @OneToMany annotations. Application is deployed to Google App Engine.</p> <p>Technologies used:</p>
  
  <ul>
    <li>java</li>
    <li>springframework</li>
    <li>mysql</li>
    <li>springsecurity</li>
    <li>thymeleaf</li>
    <li>springdatajpa</li>
    <li>hibernate</li>
    <li>beanvalidation</li>  
  </ul>

<br>


<br>

<b>Screenshots:</b>

<img src="https://github.com/dominikazb/onlinediary/blob/master/screenshots/onlinediary1.jpg">
<img src="https://github.com/dominikazb/onlinediary/blob/master/screenshots/onlinediary2.jpg">
<img src="https://github.com/dominikazb/onlinediary/blob/master/screenshots/onlinediary3.jpg">
<img src="https://github.com/dominikazb/onlinediary/blob/master/screenshots/onlinediary4.jpg">
<img src="https://github.com/dominikazb/onlinediary/blob/master/screenshots/onlinediary5.jpg">
<img src="https://github.com/dominikazb/onlinediary/blob/master/screenshots/onlinediary6.jpg">
<img src="https://github.com/dominikazb/onlinediary/blob/master/screenshots/onlinediary7.jpg">





--------------------------------------------------------------
This application has one additional file, which has not been uploaded to github repository.
Under src/main/resources/ there is a file <b>application.properties</b> with the following content:

spring.jpa.hibernate.ddl-auto=update<br>
spring.datasource.url=hostname<br>
spring.datasource.username=username<br>
spring.datasource.password=password

I did not include it, to not expose database details to public.

--------------------------------------------------------------




<b>Bibliography:</b>
1. "Spring w Akcji. Wydanie V" <i>Craig Walls</i>. 2020 Helion S.A.
2. "Czysty Kod. Podręcznik dobrego programisty." <i>Robert C. Martin</i>. 2014 Helion S.A.
3. https://www.baeldung.com
