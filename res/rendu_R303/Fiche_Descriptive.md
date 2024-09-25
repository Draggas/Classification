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



- **Scénarios alternatifs**:


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