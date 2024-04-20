# Prototype de Simulateur Automate Cellulaire - FireFighter

Ce projet est un prototype de simulateur d'automate cellulaire basé sur un jeu de pompier/feu. Son objectif principal est de servir de plateforme d'apprentissage pour comprendre et appliquer les cinq principes SOLID en concevant un jeu extensible et modulaire.

## Description

Le simulateur met en scène un environnement où des incendies se propagent dans une zone. Des équipes de pompiers (Pompier, Camion de pompier, nuage...) doivent donc travailler ensemble pour contenir et éteindre les incendies avant qu'ils ne se propagent hors de contrôle. Il existe également des obstacles (Route, Roche) qui peuvent entraver la progression des pompiers et des incendies.

Le jeu est basé sur un automate cellulaire, où chaque cellule de la grille représente une unité de terrain. Les pompiers et les incendies se déplacent d'une cellule à l'autre en fonction de règles prédéfinies, et interagissent avec leur environnement pour contenir les incendies.

Il existe également des variantes du jeu (Pierre/Feuille/Ciseaux...).

## Principes SOLID

Le projet est construit en suivant les cinq principes SOLID :

  - Principe de responsabilité unique (SRP) : Chaque classe et module du jeu est conçu pour n'avoir qu'une seule responsabilité, ce qui facilite la compréhension et la maintenance du code.
  - Principe d'ouverture/fermeture (OCP) : Le jeu est conçu de manière à permettre l'extension de ses fonctionnalités sans modifier le code existant. De nouveaux types d'unités, d'incendies ou de stratégies de lutte peuvent être ajoutés facilement.
  - Principe de substitution de Liskov (LSP) : Les différentes classes du jeu peuvent être substituées par leurs sous-classes sans affecter le comportement global du système. Par exemple, différentes implémentations de pompiers ou d'incendies peuvent être utilisées sans altérer le fonctionnement du jeu.
  - Principe d'inversion de dépendance (DIP) : Le code est conçu de manière à ce que les modules de haut niveau ne dépendent pas des détails d'implémentation des modules de bas niveau. Les abstractions sont utilisées pour permettre une plus grande flexibilité et facilité de changement.
  - Principe de ségrégation d'interface (ISP) : Les interfaces du jeu sont spécifiques aux besoins des différentes parties du système, évitant ainsi la surcharge de fonctionnalités inutilisées. Chaque unité ou stratégie possède son propre ensemble d'interfaces claires et concises.

## Comment utiliser

  - Cloner le dépôt : Utilisez la commande git clone pour obtenir une copie locale du dépôt.
  - Ouvrir le projet : Ouvrez le projet dans votre IDE préféré (préferablement IntelliJ IDEA pour pouvoir faire fonctionner Gradle facilement).
  - Exécuter le jeu : Lancez le simulateur pour observer le comportement des pompiers et des incendies, et expérimentez avec différentes stratégies pour contenir les feux.