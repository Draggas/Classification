## 1. Implémentation de k-NN

a. Classe implémentant l'algorithme
La classe methodeKnn implémente l'algorithme de classification k-NN. Elle comprend plusieurs méthodes pour effectuer les calculs nécessaires à la classification des données :

- Méthode de calcul de la distance :
  La distance entre les points de données est calculée en utilisant deux méthodes principales :
    1. Distance Euclidienne : Cette méthode mesure la distance entre deux points dans un espace à n dimensions.
       ​

    2. Distance de Manhattan : Elle calcule la somme des différences absolues des coordonnées des points dans chaque dimension.


- Méthode de normalisation des données :
  Avant de calculer la distance, une normalisation est effectuée sur les attributs des données. Cela garantit que toutes les dimensions ont une échelle comparable, ce qui évite qu'une caractéristique dominante influence le calcul de la distance. La normalisation peut être effectuée via des méthodes telles que la mise à l'échelle (max-min) ou la standardisation (centrage et réduction).

- Méthode de classification :
  L'algorithme classe un point en fonction des k plus proches voisins. Une fois les distances calculées, les k voisins les plus proches sont identifiés et la classe la plus fréquente parmi ces voisins est attribuée au point.

- Évaluation de la robustesse :
  La robustesse de l'algorithme est évaluée en utilisant la validation croisée et en analysant les résultats pour différents choix de k. Cette analyse permet de déterminer la performance de l'algorithme en fonction de la taille du voisinage et de la distance choisie.

## 2. Validation croisée
   La validation croisée est utilisée pour évaluer la performance de l'algorithme. La procédure se déroule comme suit :
1. Les données sont divisées en k sous-ensembles.
2. L'algorithme est entraîné sur k-1 sous-ensembles et testé sur le sous-ensemble restant.
3. Ce processus est répété plusieurs fois en faisant varier le sous-ensemble de test, et les résultats sont moyennés pour obtenir une évaluation robuste de la précision du modèle.

Les pourcentages de performance sont calculés en mesurant la proportion des prédictions correctes par rapport au total des prédictions effectuées sur l'ensemble de test.

## 3. Choix du meilleur k
   Nous avons testé l'algorithme avec différentes valeurs de k et pour deux types de distance : Manhattan et Euclidienne. Les résultats sont présentés pour deux ensembles de données distincts : iris et pokémons.

- Iris :
  Pour les données Iris, les performances sont comparées pour différentes valeurs de k et pour les deux distances. Les résultats montrent que pour des valeurs plus petites de k (k=1 à k=5), l'algorithme peut surajuster les données, tandis que pour des valeurs plus grandes de k, il devient plus robuste.

- Pokémons :
  Les performances sont également évaluées pour les données Pokémon, en explorant différentes catégories. La robustesse de l'algorithme est testée pour plusieurs classifications différentes.

a. Résultats et conclusion sur le choix de k :
Les meilleurs résultats pour les deux ensembles de données sont obtenus avec des valeurs de k, en particulier avec la distance Euclidienne. Cependant, un k trop élevé (par exemple, k=15 ou plus) peut entraîner un sous-ajustement, où l'algorithme perd sa capacité à distinguer correctement les classes.

## 4. Efficacité
   L'efficacité de l'implémentation dépend de la structure de données utilisée pour gérer les voisins et les calculs de distance :

- Structures de données utilisées :
  L'algorithme utilise une structure de données de type liste pour stocker les points d'apprentissage et une structure de données de type heap ou priority queue pour récupérer les k plus proches voisins de manière efficace. Cela permet de réduire la complexité de la recherche des voisins les plus proches.

- Complexité algorithmique :
  La complexité de l'algorithme k-NN est principalement dictée par le calcul des distances entre le point à classer et tous les autres points des données. Cela donne une complexité de O(n) pour un seul calcul de distance, où n est le nombre de points dans les données.

  Cependant, l'optimisation de la recherche des k voisins les plus proches (par exemple, à l'aide de structures comme des arbres KD ou des balles) peut réduire cette complexité pour des données plus volumineux.

Conclusion
L'implémentation de l'algorithme k-NN présentée est robuste et efficace, avec une gestion appropriée de la normalisation des données, un choix optimal de k, et des structures de données adaptées. L'algorithme fonctionne bien pour les deux ensembles de données, Iris et Pokémon, et permet de tester différentes distances et catégories pour évaluer sa performance.