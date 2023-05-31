# Readme

# ITA

## Il team

Il team di sviluppo è così composto:

- ****************Back-end****************
    - [Ernesto Rapisarda](https://github.com/Ernesto-Rapisarda)
    - [Andrea Tocci](https://github.com/AndreaYpmY)
    
- ********************Front-end********************
    - [Alessandro Monetti](https://github.com/ilveron)
    - [Simone Rotundo](https://github.com/simonerotundo)

## Descrizione del progetto

“*GoodVibes*” è una Web Application che si pone come obiettivo quello di aiutare le persone a trovare l’**evento** ideale a cui partecipare.
La pagina principale permette di **esplorare** gli eventi disponibili.
Essa si aggiorna **dinamicamente**: può mostrare gli eventi in base alle **categorie preferite**, se impostate dall’utente nel proprio profilo, o mostrarli in ordine cronologico, indipendentemente dalla categoria degli stessi.

**Ogni evento** ha a disposizione una **pagina contenente** tutte le **informazioni** necessarie (*Titolo, data e ora,  luogo, descrizione ecc…*).
Il dato sul **luogo** viene dettagliato tramite l'implementazione delle **API** di **Mapbox**, che permettono di visualizzarne la posizione sulla mappa.
Al fine di rendere più coinvolgente l’applicazione, oltre alle funzioni dell’utente registrato, è possibile **condividere** la pagina di un evento sui principali canali **social**.
Le interazioni sono lo strumento attraverso il quale è possibile farsi un’idea dell’indice di gradimento di un evento: questo dato torna utile sia agli organizzatori che agli altri utenti.

---

## Istruzioni per l’utilizzo

### Operazioni preliminari

**********Back-end**********: Path repo → “/WebApplication/BackEnd”

Prima di avviare normalmente il progetto, sarà necessario effettuare il ripristino del database, tramite il dump presente all’interno di questo repository in 

> /WebApplication/BackEnd/DatabaseDUMP/*.tar
> 

Una volta effettuato il ripristino del database, sarà inoltre necessario recarsi nel file

> /WebApplication/BackEnd/src/main/java/persistenza/DBManager.java
> 

E, alla riga 36, inserire i propri dati per la connessione al DB

---

**Front-end**:  Path repo → “/WebApplication/FrontEnd/EventBoard/”

Per una sola volta, al primo avvio, sarà necessario usare precedentemente il seguente comando, per installare tutti i moduli richiesti dall’app front-end

```bash
npm install
```

Per avviare il progetto **Angular** sarà necessario usare il seguente comando 

```bash
ng serve --proxy-config proxy.conf.json
```

Ciò è dovuto alla presenza di un proxy (***proxy.conf.json***, *appunto*), necessario per bypassare il CORS nelle richieste rivolte verso l’API di imgBB (*utilizzato per l’upload delle locandine degli eventi*)

---

## Note (Bug e Precisazioni)

- A volte può succedere che la mappa mapbox nel dialog di selezione della posizione di un nuovo evento sia inizializzata in un punto che non corrisponde esattamente a quanto richiesto nei campi Regione e Città;
- A volte può succedere (*per cause che non dipendono da noi*) che il servizio esterno per regioni e comuni italiani impieghi diverso tempo prima di rispondere, causando disagi alla UX. Ciò accade soprattutto nei form che attendono la compilazione dei suddetti dati per essere validati;
- Il sistema di notifiche dell’applicazione, come descritto nei casi d’uso (i quali sono dettagliati all’interno della relazione di Ingegneria del Software, anch’essa disponibile in questo repository) si basa sull’invio di email attraverso SMTP Gmail, servizio solitamente molto affidabile e rapido ma che non è scevro da rallentamenti, i quali potrebbero minare la qualità dell’UX.

### Tecnologie utilizzate

**Front-end**:

- [Angular 15](https://angular.io/);
- [Angular Material](https://material.angular.io/);
- [Typescript 4.8.3](https://www.typescriptlang.org/);
- [Animate.css](https://animate.style/) (******************************************Per ALCUNE delle animazioni presenti nell’applicazione);******************************************
- [API Mapbox](https://docs.mapbox.com/api/overview/) (*Per posizionamento e mappe*);
- [API ComuniITA](https://github.com/Samurai016/Comuni-ITA) (*Per raccolta dati su regioni e comuni italiani*);
- [API Imgbb](https://api.imgbb.com/) (*Per upload immagini in rete*);
- ~~[API Imgur](https://apidocs.imgur.com/)~~ (*Come sopra, non utilizzata, ma implementata, testata e pronta per sostituire eventualmente Imgbb/Thumbsnap in caso di guasti/manutenzioni*);
- ~~[API Thumbsnap](https://thumbsnap.com/api)~~ (*Come sopra, non utilizzata, ma implementata, testata e pronta per sostituire eventualmente Imgbb/Imgur in caso di guasti/manutenzioni*).

****************Back-end****************:

- [Java 17](https://openjdk.org/projects/jdk/17/);
- [Springboot 3.0.1](https://spring.io/projects/spring-boot);
- [Tomcat 10.1.4](https://tomcat.apache.org/);
- [Thymeleaf](https://www.thymeleaf.org/) e [Servlet](https://jakarta.ee/specifications/servlet/) (*Per la pagina delle statistiche del sito*);
- [Postgres](https://www.postgresql.org/);
- [Lombok](https://projectlombok.org/);
- [Spring Security](https://spring.io/projects/spring-security) (*Per l’implementazione di un sistema di autenticazione e autorizzazione basato sul JWT Token*);
- [Spring Mail](https://www.baeldung.com/spring-email) (*Per implementare il sistema di notifica e completamento registrazione*).

# ENG

## The Team

The development team is composed of:

- **Back-end**
    - [Ernesto Rapisarda](https://github.com/Ernesto-Rapisarda)
    - [Andrea Tocci](https://github.com/AndreaYpmY)
- **Front-end**
    - [Alessandro Monetti](https://github.com/ilveron)
    - [Simone Rotundo](https://github.com/simonerotundo)

## Project Description

"GoodVibes" is a Web Application that aims to help people find the ideal **event** to attend. The main page allows you to **explore** the available events. It updates **dynamically**: it can show events based on the user's **preferred categories**, if set in the user's profile, or show them in chronological order, regardless of their category.

**Each event** has a **page containing** all the **necessary information** (*Title, date and time, location, description, etc...*). The **location** data is detailed through the implementation of **Mapbox APIs**, which allow you to view its position on the map. To make the application more engaging, in addition to the functions of the registered user, it is possible to **share** an event page on the main **social channels**. Interactions are the tool through which it is possible to get an idea of the approval rating of an event: this data is useful both to organizers and to other users.

---

## Instructions for Use

### Preliminary operations

**Back-end**: Repo Path → “/WebApplication/BackEnd”

Before starting the project normally, it will be necessary to restore the database, through the dump present inside this repository at

> /WebApplication/BackEnd/DatabaseDUMP/*.tar
> 

Once the database is restored, it will also be necessary to go to the file

> /WebApplication/BackEnd/src/main/java/persistenza/DBManager.java
> 

And, at line 36, enter your data for connecting to the DB

---

**Front-end**: Repo Path → “/WebApplication/FrontEnd/EventBoard/”

For a one-time thing, at the first start-up, it will be necessary to use the following command beforehand, to install all the required modules for the front-end app

```
npm install
```

To start the **Angular** project it will be necessary to use the following command

```
ng serve --proxy-config proxy.conf.json
```

This is due to the presence of a proxy (***proxy.conf.json***, *in fact*), necessary to bypass CORS in requests directed toward the imgBB API (*used for uploading event posters*)

---

## Notes (Bugs and Clarifications)

- Sometimes the Mapbox map in the selection dialog of the position of a new event may be initialized at a point that does not exactly correspond to what is required in the Region and City fields;
- Sometimes it can happen (*for reasons that do not depend on us*) that the external service for Italian regions and municipalities takes different times to respond, causing UX discomfort. This happens especially in forms that wait for the compilation of the aforementioned data to be validated;
- The notification system of the application, as described in the use cases (which are detailed in the Software Engineering report, also available in this repository) is based on sending emails through SMTP Gmail, a service usually very reliable and fast but not free from slowdowns, which could undermine the quality of the UX.

### Technologies Used

**Front-end**:

- [Angular 15](https://angular.io/);
- [Angular Material](https://material.angular.io/);
- [Typescript 4.8.3](https://www.typescriptlang.org/);
- [Animate.css](https://animate.style/) (**For SOME of the animations present in the application);**
- [API Mapbox](https://docs.mapbox.com/api/overview/) (*For positioning and maps*);
- [API ComuniITA](https://github.com/Samurai016/Comuni-ITA) (*For collecting data on Italian regions and municipalities*);
- [API Imgbb](https://api.imgbb.com/) (*For uploading images online*);
- ~~[API Imgur](https://apidocs.imgur.com/)~~ (*As above, not used, but implemented, tested, and ready to replace Imgbb/Thumbsnap in case of malfunctions/maintenance*);
- ~~[API Thumbsnap](https://thumbsnap.com/api)~~ (*As above, not used, but implemented, tested, and ready to replace Imgbb/Imgur in case of malfunctions/maintenance*).

**Back-end**:

- [Java 17](https://openjdk.org/projects/jdk/17/);
- [Springboot 3.0.1](https://spring.io/projects/spring-boot);
- [Tomcat 10.1.4](https://tomcat.apache.org/);
- [Thymeleaf](https://www.thymeleaf.org/) and [Servlet](https://jakarta.ee/specifications/servlet/) (*For the site statistics page*);
- [Postgres](https://www.postgresql.org/);
- [Lombok](https://projectlombok.org/);
- [Spring Security](https://spring.io/projects/spring-security) (*For implementing an authentication and authorization system based on the JWT Token*);
- [Spring Mail](https://www.baeldung.com/spring-email) (*To implement the notification and completion registration system*).
