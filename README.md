# PROJET DE CLASSIFICATION DE DONNÉES

**Réalisé par :**  
DE JESUS TEIXEIRA Lucas  
WILFART Axel  
LAMOUR Enzo  
RANDOUX Martin

*Projet réalisé pour la SAE3.02 - Développement d'une application*

---

## Description : 

Projet qui permet de développer un outil de chargement et d'affichage d'un ensemble de données. Il permet également de classer une donnée par rapport aux données déjà chargées.

---

## Attribution des tâches principales : 

**Jalon 1 :**  

Lucas :
- OpenCSV et tout le chargement de données + Test
- Documentation des fichiers
- Notice et lancement de l'application

Axel :
- Fonctionnalités de l'application
- Affichage de l'application

Enzo :   
- Fonctionnalités de l'application
- Affichage des points dans l'application

Martin :  
- Démarrage de l'application
- Affichage du graphique

**Jalon 2 :**  

## ATTENTE PROJET :
- Une fois les données 
- Le programme doit permettre de construire un modèle de classification des données pour la catégorie sélectionnée à l'aide de l'algorithme kNN pour une valeur de k et une distance, que l'utilisateur peut choisir. Cette classification doit permettre de choisir la catégorie la plus probable pour un nouveau point (saisi par l'utilisateur), dont la catégorie est inconnue. Le nouveau point doit être ajouté au nuage de points, de façon à ce qu'il apparaisse clairement, ainsi que sa catégorie
- Dans un deuxième temps, l'utilisateur pourra choisir en plus de la distance (euclidienne et de Manhattan au moins) les attributs à utiliser pour le calcul de la distance entre deux données, et permettre l'utilisation de pondérations pour augmenter l'importance de certains attributs.
- La robustesse de votre classification doit être calculée par rapport à vos données, et accessible dans votre interface, en précisant la méthode d'évaluation. Dans un deuxième temps, le logiciel permettra à l'utilisateur de trouver le meilleur k possible étant donnés une catégorie et une distance
- Sélectionner dans le nuage un point pour afficher la donnée qu’il représente (toutes les valeurs de ses attributs et sa catégorie). Optionnellement, le passage de la souris sur un point du nuage affichera en infobulle la donnée qu'il représente


Lucas :
- README.md, run.sh, Documentation
- Travail sur le K-NN
- Modification de l'IHM
- Ajout du nuage de point Pokémon

Axel :
- Rendu de Dev Efficace
- Qualité/Efficacité du programme
- Travail sur le K-NN
- Modification de l'IHM

Enzo :  
- Préparation de la Soutenance
- Implémentation du K-NN
- Nouvelles fonctionnalités (et supplémentaires) de l'IHM
- Vérification IHM

Martin :  
- Préparation de la Soutenance
- Projet en Modèle MVC
- Travail sur le K-NN
- Modification de l'IHM

---

## Comment lancer l'application :

**Voici les étapes pour lancer l'application :**

**Pour Exécuter sur Linux:**  
`chmod u+x run.sh`  
`./run.sh`  
*Si maven est installé dans votre machine, la commande devrait s'éxécuter*

**Pour Exécuter depuis VSCode :**
- Lancer run, dans le programme Main.java

**Une fois arrivé sur l'interface, vous pouvez réaliser ses différentes actions :**

![Image d'accueil de l'appli](res/ReadME/Accueil.png)

**Vous pouvez soit fournir les 2 colonnes que vous voulez vérifier, puis cliquer sur "Projection" pour afficher le résultat :**

![Image d'une projection de l'appli](res/ReadME/Projection.png)

**Soit cliquez sur "Ajouter un iris", il faudra définir les différents paramètres et la variété (vous pouvez le laisser en Default) du nouveau Iris qui sera affiché après que vous aurez valider :**

![Image ajouter un point de l'appli](res/ReadME/Ajouter.png)

**C'est tout, pour le Jalon 1 de la SAE.**

*Sinon, pour nous, aucune issue demandée n'est manquante, et les javadocs seront générés pour le Jalon2.*
