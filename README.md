## Personal Project: OCA Trainer

The application made for this project is the culmination of the experience gained during the rest of the Software Engineer traineeship at Sogyo.

<i>Technologies used: Java, Vaadin, Maven, Jetty, MySQL, SQL, CSS, and JavaScript</i>

The challenge I set for myself while making this application was to learn how to use Vaadin, which, for me at the time, was an entirely new framework. As stated on their website: "Vaadin is an open-source platform for web application development. The Vaadin platform includes a set of web components, a Java web framework, and a set of tools and application starters. Its flagship product, Vaadin Platform allows the implementation of HTML5 web user interfaces using the Java Programming Language." The main reason why I decided to use it was because it indeed allowed me to focus solely on using Java. By implementing Vaadin components, the JavaScript elements are handled in the background. Although you could arguably still use JavaScript or even create your own Vaadin components, the free-to-use compenents provided by Vaadin were more than sufficient.

The picture below provides an idea of how an application written in Vaadin works. When a client uses the application, a session is made for that particular client. Any interactions the client then makes send low-level events to the server where the UI logic responds to the client's actions. If needed, calls are made to the business logic and possibly then also to the database. The results of the calls are then presented to the client who can then continue interacting with the application whose state has now been changed.

![alt-text](https://phauer.com/blog/2015/0216-evaluating-vaadin-strengths-weaknesses/Vaadin-architecture-overview.png)
<i>Source: https://phauer.com/2015/evaluating-vaadin-strengths-weaknesses/</i>

All-in-all, designing and creating this application was an excellent exercise in learning how to work with something new and combining the various techniques learned previously into one cohesive whole.
