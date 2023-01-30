# Readme

# ITA

## Descrizione del progetto

“*GoodVibes*” è una Web Application che si pone come obiettivo quello di aiutare le persone a trovare l’**evento** ideale a cui partecipare.
La pagina principale permette di **esplorare** gli eventi disponibili.
Essa si aggiorna **dinamicamente**: può mostrare gli eventi in base alle **categorie preferite**, se impostate dall’utente nel proprio profilo, o mostrarli in ordine cronologico, indipendentemente dalla categoria degli stessi.

**Ogni evento** ha a disposizione una **pagina contenente** tutte le **informazioni** necessarie (*Titolo, data e ora,  luogo, descrizione ecc…*).
Il dato sul **luogo** viene dettagliato tramite l'implementazione delle **API** di **Mapbox**, che permettono di visualizzarne la posizione sulla mappa.
Al fine di rendere più coinvolgente l’applicazione, oltre alle funzioni dell’utente registrato, è possibile **condividere** la pagina di un evento sui principali canali **social**.
Le interazioni sono lo strumento attraverso il quale è possibile farsi un’idea dell’indice di gradimento di un evento: questo dato torna utile sia agli organizzatori che agli altri utenti.

## Istruzioni per l’utilizzo

### Operazioni preliminari

**********Back-end**********: Path repo → “/WebApplication/BackEnd”

Prima di avviare normalmente il progetto, sarà necessario effettuare il ripristino del database, tramite il dump presente all’interno di questo repository in 

> /WebApplication/BackEnd/DatabaseDUMP/*.tar
> 

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

## Note

### Tecnologie utilizzate

**Front-end**:

- [Angular 15](https://angular.io/)
- [Angular Material](https://material.angular.io/)
- Typescript 4.8.3
- [API Mapbox](https://docs.mapbox.com/api/overview/) (Per posizionamento e mappe)
- [API Imgbb](https://api.imgbb.com/) (Per upload immagini in rete)
- [Api ComuniITA](https://github.com/Samurai016/Comuni-ITA) (Per raccolta dati su regioni e comuni italiani)

****************Back-end****************:

- Java 17
- Springboot 3.0.1
- Tomcat 10.1.4
- Thymeleaf
- Postgres
- Lombok
- Spring Security
- Spring Mail

# ENG (To be written)