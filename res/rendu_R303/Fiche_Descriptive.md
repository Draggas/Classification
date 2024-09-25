Fiche Descriptive 1
-------------------

- **Système**: Système de visualisation et classification
- **Cas d'utilisation**: Charger l'ensemble de données

- **Acteur principal**: Client
- **Déclencheur**: /
- **Autre acteurs**: /

- **Préconditions**: Le client doit fournir les données sous forme de fichiers CSV.
- **Garanties en cas de succès**: Les données sont enregistrées. Le modèle est construit à partir de ces données.
- **Garanties minimales**: Si le scénario ne finit pas, aucune donnée sera enregistré et le modèle restera vide.

- **Scénario nominal**: 
1. Le client sélectionne la fonctionnalité "Charger ses données"
2. Le système lui demande le nom de son fichier CSV
3. Le client donne le nom du fichier CSV correspondant aux données
4. Le système enregistre les données en les récupérant dans ce fichier CSV et met à jour le modèle.

- **Scénarios alternatifs**:
    + **A** : A l'étape 4, le fichier n'a pas été trouvé ou n'est pas au bon format.
    - 4(A) : Le système avertit le client avec un message d'erreur et lui demande de rentrer un nouveau nom.
    - 5(A) : Retour à l'étape 3

-------------------
Fiche Descriptive 2
-------------------

- **Système**: Système de visualisation et classification
- **Cas d'utilisation**: Ajouter une donnée

- **Acteur principal**: Client
- **Déclencheur**: /
- **Autre acteurs**: /

- **Préconditions**: Le client doit avoir déjà fourni des données.
- **Garanties en cas de succès**: La donnée est enregistré. Le modèle est reconstruit avec cette nouvelle donnée.
- **Garanties minimales**: Si le scénario ne finit pas, cette donnée ne sera pas enregistré et le modèle restera comme il était.

- **Scénario nominal**: 

1.
2.
3.

- **Scénarios alternatifs**:
    + **A** : /
    - ?(A) : /
    - ?(A) : /

-------------------
Fiche Descriptive 3
-------------------

- **Système**: Système de visualisation et classification
- **Cas d'utilisation**: Classifier la donnée non classifiée

- **Acteur principal**: Client
- **Déclencheur**: /
- **Autre acteurs**: /

- **Préconditions**: Avoir ajouté une donnée
- **Garanties en cas de succès**: Cette donnée a été classifié dans la catégorie la plus probable pour cette donnée.
- **Garanties minimales**: Aucune catégorie lui convient donc elle est supprimée.

- **Scénario nominal**: 

1.
2.
3.

- **Scénarios alternatifs**:
    + **A** : /
    - ?(A) : /
    - ?(A) : /