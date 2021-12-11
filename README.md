# Freeze-tag
Projet AlgoMob de Master2

## Explication de l'arborescence :

- src.map.**Map** :\
    Classe qui contient le main, on run le code ici. 
- src.map.**Simulator** :\
    Classe pour créer des simulations (sans la fenêtre graphique).
- src.robot.**Robot** :\
    Classe Robot, les stars de ce projet.
- src.robot.**Algorithms** :\
    Classe qui contient les méthodes correspondantes aux algorithmes implémentés.
- Dossier **simulations** :\
    Contient le script gnuplot, les fichiers .txt générés par le Simulator et les graphiques obtenus avec gnuplot.

## Notions importantes :

- Un robot réveillé est de couleur CYAN.
- Un robot endormi n'a pas de couleur.
- Un robot ciblé pour être réveillé est de couleur ORANGE.
- La cible est prochain robot à réveiller pour un robot CYAN.
- La source est le robot qui va réveiller celui endormi.
- Un robot ROUGE n'a plus besoin de chercher de nouvelle cible.
- Un robot vert est un robot qui s'est fait voler sa cible par un plus proche que lui.
- 

## Algorithmes implémentés :

- **algo1** : Les robots choisissent de réveiller le prochain robot endormi dans l'ordre de la liste des robots.
- **algo2** : Les robots choisissent aléatoirement une cible à réveiller. 
