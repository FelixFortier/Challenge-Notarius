# Challenge-Notarius


Application web Url Shortener réalisée dans le cadre du Challenge pour nouveaux développeurs Notarius.

Réalisé avec les outils suivants :
- IntelliJ IDEA avec le plugin Docker
- Docker Desktop
- MongoDB
- Spring Boot
- Maven
- Thymeleaf

Instructions pour faire fonctionner l'application :

- Démarrer Docker Desktop pour le laisser fonctionner en arrière-plan.
- Lancer les services pour la base de données à partir du fichier "docker-compose.yaml" (Le plugin Docker pour IntelliJ devrait afficher une flèche verte à cliquer à gauche de la 2e ligne "services:"). S'il s'agit de la première exécution de l'application sur une machine donnée, les services peuvent prendrent du temps à s'initialiser.
- Lorsque les services sont bien initialisés, lancer la méthode main de la classe de démarrage ShortUrlApp.
- (Facultatif) Entrer http://localhost:8081/ dans un navigateur web pour avoir accès la page de la base de donnée. Les URLs se trouvent dans : demodatabase (View) >  urls (View).
- Entrer http://localhost:8080/ dans un navigateur web pour avoir accès la page de l'application web.