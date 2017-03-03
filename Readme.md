# TP Architecture Logicielle / Inf4043 - 2017 - Jeux de lettres
## Letter Game by Syndrome5 - Praddaude

## Nous attendons aussi une description de votre architecture (Quel responsabilité à chaque package, ..).
- Il y a 3 packages sur ce programme :
  - "Dictionary" qui correspond à toute la partie du dictionnaire. Grâce à ce package, on peut consulter si un mot existe dans le fichier concerné.
  - "UI" qui correspond à toute la partie graphique du programme.
  - "Game" qui correspond à toutes les classes importantes dépendantes pour le jeu en lui-même.
    - Game qui est la classe coeur du jeu
	- Player qui permet de créer un joueur avec toutes ses spécificités
	- Bag qui représente le sachet de lettres dans lequel chaque joueur pioche ses lettres. Il respecte la fréquence d'apparition des lettres de la langue française avec une marge d'erreur de 0,2%.


## De plus, vous devrez illustrer trois principes SOLID ou design pattern en utilisant vos propres classes. 
## pourquoi avez-vous utilisé ce design pattern / principe ? Qu'est-ce que cela vous a apporté ? Comment l'avez-vous appliqué ?
- Chaque fonction possède une utilité unique.
- Le système d'interface graphique est totalement dissocié du jeu, ce qui permet une indépendance et donc une portabilité totale du jeu pour une autre interface graphique si nécessaire.
- En dissociant les classes de cette façon, elles sont indépendantes et peuvent être réutilisés dans d'autres projets (player, bag, dictionary...).

## Commentaires supplémentaires
- Pas de bug connu à ce jour
- IA fonctionnelle et plutôt forte mais améliorable (utiliser les lettres que l'on obtient quand elle fait des mots, et voler des mots)
- Pouvant être joué sur le même ordinateur de 2 à 8 joueurs (étendable en adaptant l'interface graphique) (joueur ou ordinateur)
- Pas de grosse latence connu (mise à part le chargement du dictionnaire, déjà optimisé)