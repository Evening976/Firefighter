Christopher SLAYBI

Quentin LEFEVRE

Projet programmation : proto simulator d'automate cellulaire


Le projet semblait initialement accessible, mais au fil du 
développement, notamment lors de l'optimisation et du respect 
des principes SOLID, il est devenu de plus en plus exigeant. 
Nous avons constaté que la recherche de solutions optimales 
et conformes aux principes de conception nécessitait une 
révision fréquente de notre code. Parfois, après avoir corrigé 
une erreur, nous réalisions le lendemain qu'il existait une 
meilleure approche. Ainsi, le parcours du développement de ce 
projet a été jalonné de multiples ajustements et révisions, 
témoignant de notre engagement à atteindre des standards élevés 
dans la qualité du code.

Nous avons entamé le développement du projet en 
réorganisant les fonctions de la classe Board et en les distribuant 
dans des classes dédiées. Après avoir testé le code de base et l'avoir 
reformulé, nous avons commencé à enchaîner les tâches de manière 
séquentielle sans accorder une attention particulière aux principes 
SOLID. En conséquence, bien que nous ayons achevé la première tâche,
le code n'était ni esthétique ni optimal.

L'ajout de la fonctionnalité Cloud a été relativement 
simple et similaire à celle de FireFighter. En revanche, 
l'implémentation de FireTruck s'est avérée un peu plus complexe, 
car nous n'avons pas immédiatement trouvé la méthode permettant 
au FireTruck de se déplacer de deux cases par tour. Finalement, 
nous avons adopté la même logique que pour FireFighter, en créant
une fonction 2-step qui utilise les fonctions de FireFighter.

En ce qui concerne l'ajout des obstacles, bien que cela semblait 
simple, cela s'est révélé trompeur. La gestion du feu était relativement 
facile, mais pour les FireFighters, nous avons rencontré de nombreux 
bugs et difficultés liés à leurs déplacements, en particulier pour FireTruck,
qui, en sautant une case, se retrouvait parfois sur une montagne. 
Nous avons également résolu un bug où les FireFighters ne pouvaient 
pas éteindre le feu s'il était adjacent à une montagne, parmi d'autres
petits problèmes.

Pour les rochers, la modification du code en utilisant 
step % 4 a résolu les problèmes. Après quelques heures de développement,
nous avons réussi à compléter la tâche 1 avec un FirefighterBoard 
fonctionnel. Avant d'entamer la tâche 2, nous avons réalisé qu'il était
nécessaire d'améliorer notre code pour le rendre plus optimal et 
respectueux des principes SOLID.

Donc, nous avons commencé par factoriser le code et 
le rendre plus lisible. Nous avons initié la création 
de classes abstraites regroupant des fonctions communes
entre différentes parties du code. Ensuite, nous avons 
progressé en créant toutes les interfaces nécessaires, 
telles que Entity, Obstacle, Board, ModelElement. Par la 
suite, nous avons élaboré les classes manager, comme
EntityManager et ObstacleManager, qui sont des classes 
abstraites générales utilisées dans tous les jeux. Ensuite,
nous avons introduit les entités qui étendent l'entité générale, 
telles que Fire, Cloud, etc. Chacune de ces entités avait son propre
manager qui étendait bien sûr EntityManager. Nous avons utilisé
un ModelElement pour chaque jeu, implémentant l'interface GameElement.
Enfin, nous avons travaillé sur les boards.

Nous avons apporté de nombreux changements pour permettre 
la coloration en utilisant un seul Painter qui fonctionne 
avec tous les jeux. Les méthodes getState et setState ont 
été modifiées à plusieurs reprises, car nous cherchions 
constamment de nouvelles manières d'améliorer le code. Avec 
toutes ces bases en place, le développement de la tâche 2 est 
devenu relativement simple.

Avec ces bases solides, le développement de la tâche 2, 
consistant à créer le jeu Pierre-Papier-Ciseaux, était relativement 
simple. Le défi principal résidait dans la définition de la logique 
pour le mouvement des entités et la dynamique du jeu. Cependant, avec
les managers en place et tout prêt à faciliter notre travail,
le processus a été considérablement accéléré, surtout compte tenu 
de la similitude avec FirefighterBoard.

En conclusion, ce projet s'est révélé très intéressant, 
nous permettant de maîtriser les principes SOLID, qui ont rendu 
notre code plus lisible, optimal et facile à étendre. Il nous a 
également permis de découvrir des méthodes de débogage et de 
trouver des failles. Enfin, il a souligné l'importance du travail 
en groupe et de la répartition efficace des tâches.