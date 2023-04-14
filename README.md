# Todoc

## Description
Ce dépôt contient une mini-application pour le P5 du parcours “Développement d’application Android” d’Openclassrooms.

Cette application consiste à:
* Afficher une liste de taches.
* Création d'une nouvelle tache
  * et l'ajouter a une base de données
* Supprimer une une tache
* Trier une Réunion
    * par date/heure
    * par salles
## Prérequis
* Installation de [Android Studio](https://developer.android.com/studio?gclid=CjwKCAiAlp2fBhBPEiwA2Q10DylEC18SIGfmsSq9IHXwIvfDtvdeyjUUL9axVlY7wGES4gyH5kgjdxoCqlsQAvD_BwE&gclsrc=aw.ds)
* Installation du [Java sdk](https://www.oracle.com/fr/java/technologies/downloads/)
* Installation de [Git](https://git-scm.com/book/fr/v2/D%C3%A9marrage-rapide-Installation-de-Git)
* Connécence de l'architecture [MVVM](https://youtu.be/ijXjCtCXcN4)
* Connécence de [ROOM](https://developer.android.com/training/data-storage/room?hl=fr)
## Comment exécuter et compiler
* Ouvrir les fichiers avec android studio
* Synchroniser le build.gradle
    * minSdkVersion 21
    * targetSdkVersion 33
    * dépendances
        * viewmodel
        * livedata
        * [espresso](https://developer.android.com/training/testing/espresso)
        * Room
        * [Mockito](https://site.mockito.org/)
* Sélectionner le device pour l'émulateur
* Sélectionner “app” dans la configuration
* appuyer sur le bouton Run

