# Freeze-tag
Projet AlgoMob de Master2

## Explication de l'arborescence :

- src.map.**Map** :\
  Classe qui contient le main, on run le code ici.
- src.map.**Simulator** :\
  Classe pour créer des simulations (sans la fenêtre graphique).
- src.map.**Configurations** :\
  Classe Configurations, contient les méthodes permettant de créer des configuration.
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
- La source d'un robot est le robot qui va le réveiller.
- Un robot RED n'a plus besoin de chercher de nouvelle cible (elles sont déjà toutes prises, il s'arrête).
- Un robot GREEN est un robot qui s'est fait voler sa cible par un autre plus proche que lui.
- Quand un robot arrive à destination, il envoie un message à sa cible pour la réveiller.

## Algorithmes implémentés :

- **algo1** : Les robots choisissent de réveiller le prochain robot endormi dans l'ordre de la liste des robots.
- **algo2** : Les robots choisissent aléatoirement une cible à réveiller.
- **algo3** : Les robots choisissent comme cibles les robots les plus proches d'eux.
- **algo4** : Les robots choisissent comme cibles les robots avec le degré le plus haut (ceux avec le plus de voisins). Cet algorithme est utile quand on veut que l'ordonnanceur du choix du premier robot soit gentil.
- **algo5** : Choix de la cible selon le critère de l'algo3 combiné à cette propriété :\
  Un robot peut également choisir comme cible un robot déjà ciblé (ORANGE) par un autre si et seulement si il est plus proche de la cible que le robot devant le réveiller à l'origine (sa source). En devenant nouvelle source de la cible à réveiller, il faut également avertir l'ancien robot source qu'il doit trouver une nouvelle cible (il devient GREEN).
- **algo6** : Les robots choisissent leur cible de manière à maximiser le rapport entre le nombre de voisins du robot cible et la distance pour y arriver.
- **algo7** : On découpe la carte en plusieurs secteurs (nord-ouest, nord-est, sud-ouest et sud-est), les robots choisissent la cible en maximisant le rapport entre le nombre de robots présents dans un secteur et la distance pour atteindre le robot le plus proche de ce secteur.
- **algo8** : Les robots choisissent un secteur au hasard et vont réveiller le robot le plus proche s'y trouvant.

## Comment exécuter une topologie ?
###Mode graphique avec Map :
1. Dans le main de Map.java, il faut commenter le potentiel objet Simulator existant.
2. On crée un new Map(Topology tp, int config, int firstChoice, int algo, int nbRobots, int nbBlocs) avec comme paramètres :
- config : de 0 à 9 -> choix de la configuration à lancer.
- firstChoice : choix du premier robot à réveiller. 0=random, 1=Premier robot (id=0), 2=Meilleur choix (robot avec le meilleur degré), 3=choix libre (ctrl+clic puis start execution manuellement).
- algo : choix de l'algo (de 1 à 8)
- nbRobots : nombre de Robots sur la map, seulement pour la map avec placement random.
- nbBlocs : nombre de blocs de robots en largeur (donc nbBlocs*nbBlocs au total) si on choisit config7, 8 ou 9.
3. Une fois fini, le temps s'affiche dans le terminal.

###Mode Simulator :
Attention : la fonction writeResult ne fonctionne pas sur windows à cause de la différence de paths/gestion des noms de fichiers avec Linux. Il faut la commenter.
1. Dans le main de Map.java, il faut commenter le potential objet Map existant.
2. On crée un objet new Simulator(int config, int algo, int nbRep, int nbBlocs, int firstChoice, int[] tabSizes) avec comme paramètres :
- config, algo, nbBlocs : comme pour Map.
- nbRep : le nombre de répétitions pour une configuration de topologie (pour faire une moyenne/tester plusieurs combinaisons)
- firstChoice : 0=random, 2=Meilleur choix (robot avec le meilleur degré), 2=Pire choix (Robot de degré le plus petit).
- tabSizes : tableau contenant plusieurs quantités de robots à tester. Par exemple [10,20,50,100] pour 4 topologies avec 10 puis 20 puis 50 puis 100 Robots. Ne fonctionne que si le type de topologie est Random. Chaque valeur dans tabSizes sera lancé nbRep fois, ce qui peut être pratique quand on veut faire une moyenne en prenant le choix du premier robot aléatoirement.
3. La progression de la simulation est visible dans le terminal.
4. Une fois terminé, un fichier nommé selon les paramètres de la simulation+heure du jour et contenant les datas de la simulation est généré dans le dossier simulations.
5. Dans le fichier, le paramètre resType correspond à : 0=moyenne de temps, 1=meilleur temps, 2=pire temps.
6. Dans le dossier simulation se trouve également le script script-gp.plt qui permet de générer les graphiques à partir des datas.txt 